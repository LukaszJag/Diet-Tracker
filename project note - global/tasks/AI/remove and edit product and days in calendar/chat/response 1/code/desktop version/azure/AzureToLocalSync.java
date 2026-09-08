package tools.azure;

import tools.sql_tools.general.get.GetConnection;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AzureToLocalSync {

    private static final String AZURE_FUNCTION_URL = "https://diettrackerandroidversionapi-grcbhva9e5gqhzhz.polandcentral-01.azurewebsites.net/api/AndroidAzure";

    /**
     * Downloads the product library from Azure and merges it into the local MySQL database.
     */
    public static void pullProductsFromAzure() {
        try {
            String jsonPayload = "{\"sql_query\":\"SELECT * FROM diet_tracker_schema.product_table\"}";

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(15))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(AZURE_FUNCTION_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            System.out.println("Downloading database from Azure...");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.err.println("Pull failed. HTTP Status code: " + response.statusCode());
                return;
            }

            List<Map<String, String>> products = parseJsonArray(response.body());
            if (products.isEmpty()) {
                System.out.println("No products found on Azure SQL to sync.");
                return;
            }

            System.out.println("Processing " + products.size() + " products for local import...");

            Connection localConn = GetConnection.getConnectionWithLocalHost();
            localConn.setAutoCommit(false);

            String localSql = "REPLACE INTO diet_tracker_schema.product_table " +
                    "(product_name, product_brand, product_package_has, product_macro_for, " +
                    "product_kcal, product_protein, product_fat, product_carbs, comment_optional) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = localConn.prepareStatement(localSql);

            int count = 0;
            for (Map<String, String> item : products) {
                pstmt.setString(1, item.getOrDefault("product_name", "Unknown Product"));
                pstmt.setString(2, item.getOrDefault("product_brand", ""));
                pstmt.setFloat(3, getFloatSafe(item, "product_pack_weight"));
                pstmt.setFloat(4, getFloatSafe(item, "product_macro_for"));
                pstmt.setFloat(5, getFloatSafe(item, "product_kcal"));
                pstmt.setFloat(6, getFloatSafe(item, "product_protein"));
                pstmt.setFloat(7, getFloatSafe(item, "product_fat"));
                pstmt.setFloat(8, getFloatSafe(item, "product_carbs"));
                pstmt.setString(9, truncateSafe(item.getOrDefault("product_comment", ""), 255));
                pstmt.addBatch();

                if (++count % 1000 == 0) {
                    pstmt.executeBatch();
                }
            }

            pstmt.executeBatch();
            localConn.commit();
            pstmt.close();
            localConn.close();

            System.out.println("Local MySQL database is now fully updated from Azure SQL!");

        } catch (Exception e) {
            System.err.println("Synchronization error: " + e.getMessage());
        }
    }

    private static float getFloatSafe(Map<String, String> map, String key) {
        String val = map.get(key);
        if (val == null || val.trim().equalsIgnoreCase("null") || val.trim().isEmpty()) {
            return 0.0f;
        }
        try {
            return Float.parseFloat(val);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    private static List<Map<String, String>> parseJsonArray(String json) {
        List<Map<String, String>> list = new ArrayList<>();
        if (json == null || json.trim().isEmpty()) {
            return list;
        }

        int len = json.length();
        int i = 0;

        while (i < len) {
            char c = json.charAt(i);
            if (c == '{') {
                Map<String, String> map = new HashMap<>();
                i++;

                while (i < len) {
                    while (i < len && json.charAt(i) != '"' && json.charAt(i) != '}') {
                        i++;
                    }
                    if (i >= len || json.charAt(i) == '}') {
                        if (i < len) i++;
                        break;
                    }

                    i++;
                    int keyStart = i;
                    while (i < len && json.charAt(i) != '"') {
                        if (json.charAt(i) == '\\') i++;
                        i++;
                    }
                    String key = json.substring(keyStart, i);
                    i++;

                    while (i < len && json.charAt(i) != ':') {
                        i++;
                    }
                    i++;

                    while (i < len && Character.isWhitespace(json.charAt(i))) {
                        i++;
                    }

                    String value = null;
                    if (i < len) {
                        if (json.charAt(i) == '"') {
                            i++;
                            int valStart = i;
                            while (i < len && json.charAt(i) != '"') {
                                if (json.charAt(i) == '\\') i++;
                                i++;
                            }
                            value = json.substring(valStart, i);
                            i++;
                        } else {
                            int valStart = i;
                            while (i < len && json.charAt(i) != ',' && json.charAt(i) != '}') {
                                i++;
                            }
                            value = json.substring(valStart, i).trim();
                            if (value.equalsIgnoreCase("null")) {
                                value = null;
                            }
                        }
                    }

                    if (value != null) {
                        map.put(key.trim(), value.trim());
                    }
                }
                list.add(map);
            } else {
                i++;
            }
        }
        return list;
    }

    /**
     * Downloads the calendar entries from Azure and merges them into the local MySQL database.
     */
    public static void pullCalendarFromAzure() {
        try {
            String jsonPayload = "{\"sql_query\":\"SELECT * FROM diet_tracker_schema.calendar\"}";

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(15))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(AZURE_FUNCTION_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            System.out.println("Downloading calendar from Azure...");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.err.println("Calendar Pull failed. HTTP Status code: " + response.statusCode());
                return;
            }

            List<Map<String, String>> entries = parseJsonArray(response.body());
            if (entries.isEmpty()) {
                System.out.println("No calendar entries found on Azure SQL to sync.");
                return;
            }

            System.out.println("Processing " + entries.size() + " calendar entries for local import...");

            Connection localConn = GetConnection.getConnectionWithLocalHost();
            localConn.setAutoCommit(false);

            String localSql = "REPLACE INTO diet_tracker_schema.calendar " +
                    "(day_date, day_name, product_name, amount_of_product, kcal, protein, fat, carbs, " +
                    "time_optional, comment_optional, kcal_consume, carbs_consume, fat_consume, " +
                    "protein_consume, meal_name, row_id, is_synced) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";

            PreparedStatement pstmt = localConn.prepareStatement(localSql);

            // Collect unique dates involved in the pull payload
            java.util.Set<String> uniqueDates = new java.util.HashSet<>();

            int count = 0;
            for (Map<String, String> item : entries) {
                String rawDate = getStringSafe(item, "day_date");
                if (rawDate != null && rawDate.length() >= 10) {
                    rawDate = rawDate.substring(0, 10);
                    uniqueDates.add(rawDate);
                }
                pstmt.setString(1, rawDate);

                pstmt.setString(2, item.getOrDefault("day_name", ""));
                pstmt.setString(3, item.getOrDefault("product_name", "Unknown"));
                pstmt.setFloat(4, getFloatSafe(item, "amount_of_product"));
                pstmt.setFloat(5, getFloatSafe(item, "kcal"));
                pstmt.setFloat(6, getFloatSafe(item, "protein"));
                pstmt.setFloat(7, getFloatSafe(item, "fat"));
                pstmt.setFloat(8, getFloatSafe(item, "carbs"));

                String rawTime = getStringSafe(item, "time_optional");
                if (rawTime != null) {
                    if (rawTime.contains("T")) {
                        rawTime = rawTime.replace("T", " ");
                        if (rawTime.contains(".")) {
                            rawTime = rawTime.substring(0, rawTime.indexOf('.'));
                        } else if (rawTime.endsWith("Z")) {
                            rawTime = rawTime.substring(0, rawTime.length() - 1);
                        }
                    }
                }
                pstmt.setString(9, rawTime);

                pstmt.setString(10, truncateSafe(getStringSafe(item, "comment_optional"), 255));
                pstmt.setFloat(11, getFloatSafe(item, "kcal_consume"));
                pstmt.setFloat(12, getFloatSafe(item, "carbs_consume"));
                pstmt.setFloat(13, getFloatSafe(item, "fat_consume"));
                pstmt.setFloat(14, getFloatSafe(item, "protein_consume"));
                pstmt.setString(15, item.getOrDefault("meal_name", "None"));
                pstmt.setString(16, item.getOrDefault("row_id", ""));
                pstmt.addBatch();

                if (++count % 1000 == 0) {
                    pstmt.executeBatch();
                }
            }

            pstmt.executeBatch();
            localConn.commit();
            pstmt.close();
            localConn.close();
            System.out.println("Calendar pulled and synced locally!");

            // Trigger the local aggregate updates for days_statistics_test
            updateLocalStatistics(uniqueDates);

        } catch (Exception e) {
            System.err.println("Calendar synchronization error: " + e.getMessage());
        }
    }

    /**
     * Iterates over pulled dates to locally check, create, and update entries inside days_statistics_test.
     */
    private static void updateLocalStatistics(java.util.Set<String> uniqueDates) {
        if (uniqueDates == null || uniqueDates.isEmpty()) {
            return;
        }

        System.out.println("Refreshing days_statistics_test for " + uniqueDates.size() + " unique dates...");
        for (String date : uniqueDates) {
            try {
                // 1. If row does not exist locally in days_statistics_test, insert standard empty placeholder
                if (!tools.sql_tools.general.get_check_data.CheckIfRowExist.isDaysStatisticRowExistInTableCheckByDate(date)) {
                    String insertSql = tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics.createInsertSQLQueryForDaysStatistics(date);
                    tools.sql_tools.general.run.RunQuery.runQuery(insertSql);
                }

                // 2. Re-calculate and write consumed macro sums from local calendar database
                String updateMacrosSql = tools.sql_tools.days_statistics.UpdateDaysStatisticsFilledData.prepareQueryForFillConsumedMacro(date);
                tools.sql_tools.general.run.RunQuery.runQuery(updateMacrosSql);

                // 3. Re-calculate and write count of entries (amount_of_filled_points_from_notepad)
                String updatePointsSql = tools.sql_tools.days_statistics.UpdateDaysStatisticsFilledData.prepareQueryForUpdateAmountOfFilledPointsFromNotepad(date);
                tools.sql_tools.general.run.RunQuery.runQuery(updatePointsSql);

            } catch (Exception e) {
                System.err.println("Failed to update statistics for date " + date + ": " + e.getMessage());
            }
        }
        System.out.println("Local days_statistics_test update complete!");
    }

    private static String getStringSafe(Map<String, String> map, String key) {
        String val = map.get(key);
        if (val == null || val.trim().isEmpty() || val.trim().equalsIgnoreCase("null")) {
            return null;
        }
        return val.trim();
    }

    private static String truncateSafe(String value, int maxLength) {
        if (value == null) {
            return null;
        }
        if (value.length() > maxLength) {
            return value.substring(0, maxLength);
        }
        return value;
    }
}