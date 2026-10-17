package tools.azure;

import tools.debug_tools.Debug;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.time.Duration;

public class AzureSqlSync {

    private static final String AZURE_FUNCTION_URL = "https://diettrackerandroidversionapi-grcbhva9e5gqhzhz.polandcentral-01.azurewebsites.net/api/AndroidAzure";

    /**
     * Legacy method wrapper to maintain compatibility with other classes.
     */
    public static void syncQueryToAzure(String mysqlQuery) {
        syncQueryToAzure(mysqlQuery, null);
    }

    /**
     * Main sync executor. If a rowId is provided and the upload is successful,
     * it marks is_synced = 1 in the local MySQL database.
     */
    public static void syncQueryToAzure(String mysqlQuery, String rowId) {
        if (mysqlQuery == null || mysqlQuery.trim().isEmpty()) {
            return;
        }

        // Before sending, attempt to sync any previously failed (unsynced) rows
        syncUnsyncedRowsToAzure();

        try {
            String mssqlQuery = cleanQueryForAzure(mysqlQuery);
            String escapedQuery = mssqlQuery.replace("\\", "\\\\").replace("\"", "\\\"");
            String jsonPayload = "{\"sql_query\":\"" + escapedQuery + "\"}";

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(AZURE_FUNCTION_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        if (response.statusCode() == 200) {
                            Debug.printGreenSystemPrintln("Successfully synced to Azure SQL.");
                            if (rowId != null) {
                                markRowAsSyncedLocally(rowId);
                            }
                        } else {
                            System.err.println("Azure SQL sync failed. Status: "
                                    + response.statusCode() + " Body: " + response.body());
                        }
                    })
                    .exceptionally(ex -> {
                        System.err.println("Network error during Azure sync: " + ex.getMessage());
                        return null;
                    });

        } catch (Exception e) {
            System.err.println("Failed to process Azure SQL sync: " + e.getMessage());
        }
    }

    /**
     * Scans local MySQL for unsynced rows and uploads them to Azure.
     */
    public static void syncUnsyncedRowsToAzure() {
        new Thread(() -> {
            String selectQuery = "SELECT * FROM calendar WHERE is_synced = 0";
            try (Connection conn = tools.sql_tools.general.get.GetConnection.getConnectionWithLocalHost();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectQuery)) {

                while (rs.next()) {
                    String rowId = rs.getString("row_id");
                    String insertQuery = rebuildInsertQueryFromResultSet(rs);
                    sendQueryAndMarkSynced(insertQuery, rowId);
                }
            } catch (SQLException e) {
                System.err.println("Error reading local unsynced rows: " + e.getMessage());
            }
        }).start();
    }

    private static String rebuildInsertQueryFromResultSet(ResultSet rs) throws SQLException {
        String[] cols = {
                "day_date", "day_name", "product_name", "amount_of_product", "kcal", "protein", "fat", "carbs",
                "time_optional", "comment_optional", "kcal_consume", "carbs_consume", "fat_consume", "protein_consume", "meal_name", "row_id"
        };
        StringBuilder sql = new StringBuilder("INSERT INTO diet_tracker_schema.calendar (");
        for (int i = 0; i < cols.length; i++) {
            sql.append("`").append(cols[i]).append("`");
            if (i < cols.length - 1) sql.append(", ");
        }
        sql.append(") VALUES (");
        for (int i = 0; i < cols.length; i++) {
            Object val = rs.getObject(cols[i]);
            if (val == null) {
                sql.append("NULL");
            } else if (val instanceof Number) {
                sql.append(val);
            } else {
                String strVal = val.toString().replace("'", "''");

                // NEW: Ensure time_optional does not contain the strict T character
                if (cols[i].equals("time_optional") && strVal.contains("T")) {
                    strVal = strVal.replace("T", " ");
                }

                sql.append("'").append(strVal).append("'");
            }
            if (i < cols.length - 1) sql.append(", ");
        }
        sql.append(");");
        return sql.toString();
    }

    private static void sendQueryAndMarkSynced(String mssqlQuery, String rowId) {
        try {
            String cleanQuery = cleanQueryForAzure(mssqlQuery);
            String escapedQuery = cleanQuery.replace("\\", "\\\\").replace("\"", "\\\"");
            String jsonPayload = "{\"sql_query\":\"" + escapedQuery + "\"}";

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(AZURE_FUNCTION_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        if (response.statusCode() == 200) {
                            markRowAsSyncedLocally(rowId);
                        } else {
                            // NEW: Print the exact SQL Server rejection error to your console
                            System.err.println("\n[SYNC ERROR] Row " + rowId + " rejected. " +
                                    "Status: " + response.statusCode() +
                                    "\nAzure Response: " + response.body() +
                                    "\nAttempted Query: " + mssqlQuery + "\n");
                        }
                    })
                    .exceptionally(ex -> {
                        System.err.println("Network error for row " + rowId + ": " + ex.getMessage());
                        return null;
                    });
        } catch (Exception e) {
            System.err.println("Failed to initiate sync request: " + e.getMessage());
        }
    }

    private static void markRowAsSyncedLocally(String rowId) {
        String updateSql = "UPDATE calendar SET is_synced = 1 WHERE row_id = ?";
        try (Connection conn = tools.sql_tools.general.get.GetConnection.getConnectionWithLocalHost();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setString(1, rowId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to update sync flag locally: " + e.getMessage());
        }
    }

    private static String cleanQueryForAzure(String mysqlQuery) {
        if (mysqlQuery == null) return "";
        String cleanQuery = mysqlQuery.replace("`", " ");
        cleanQuery = cleanQuery.replace("\n", " ").replace("\r", " ");
        cleanQuery = cleanQuery.replaceAll("\\s+", " ");
        cleanQuery = cleanQuery.replace("\\'", "''");

        // NEW: Replace 'YYYY-MM-DDTHH:MM' format with standard space-separated datetime format
        cleanQuery = cleanQuery.replaceAll("'(\\d{4}-\\d{2}-\\d{2})T(\\d{2}:\\d{2}(:\\d{2})?)'", "'$1 $2'");

        return cleanQuery.trim();
    }
}