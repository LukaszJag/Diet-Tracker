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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AzureToLocalSync {

    // Use your actual Azure Function URL here
    private static final String AZURE_FUNCTION_URL = "https://diettrackerandroidversionapi-grcbhva9e5gqhzhz.polandcentral-01.azurewebsites.net/api/AndroidAzure";

    /**
     * Downloads the product library from Azure and merges it into the local MySQL database.
     */
    public static void pullProductsFromAzure() {
        try {
            // 1. Prepare JSON query payload to fetch all products
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

            // 2. Parse the JSON response
            List<Map<String, String>> products = parseJsonArray(response.body());
            if (products.isEmpty()) {
                System.out.println("No products found on Azure SQL to sync.");
                return;
            }

            System.out.println("Processing " + products.size() + " products for local import...");

            // 3. Connect to local MySQL and write in a batch transaction
            Connection localConn = GetConnection.getConnectionWithLocalHost();
            localConn.setAutoCommit(false); // Enable transaction for performance

            // REPLACE INTO inserts new products or updates existing ones based on primary key (product_name)
            String localSql = "REPLACE INTO diet_tracker_schema.product_table " +
                    "(product_name, product_brand, product_package_has, product_macro_for, " +
                    "product_kcal, product_protein, product_fat, product_carbs, comment_optional) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = localConn.prepareStatement(localSql);

            for (Map<String, String> item : products) {
                pstmt.setString(1, item.getOrDefault("product_name", "Unknown Product"));
                pstmt.setString(2, item.getOrDefault("product_brand", ""));
                pstmt.setFloat(3, getFloatSafe(item, "product_pack_weight"));
                pstmt.setFloat(4, getFloatSafe(item, "product_macro_for"));
                pstmt.setFloat(5, getFloatSafe(item, "product_kcal"));
                pstmt.setFloat(6, getFloatSafe(item, "product_protein"));
                pstmt.setFloat(7, getFloatSafe(item, "product_fat"));
                pstmt.setFloat(8, getFloatSafe(item, "product_carbs"));
                pstmt.setString(9, item.getOrDefault("product_comment", ""));
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            localConn.commit(); // Commit all records
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

    /**
     * A lightweight, dependency-free regex JSON parser to parse flat JSON arrays
     * returned from the Node.js Azure function.
     */
    private static List<Map<String, String>> parseJsonArray(String json) {
        List<Map<String, String>> list = new ArrayList<>();

        // Match individual object blocks: { ... }
        Pattern objectPattern = Pattern.compile("\\{[^{}]+\\}");
        Matcher objectMatcher = objectPattern.matcher(json);

        while (objectMatcher.find()) {
            String objectContent = objectMatcher.group();
            Map<String, String> map = new HashMap<>();

            // Match key-value pairs (e.g. "key": "value" or "key": number)
            Pattern keyValuePattern = Pattern.compile("\"([^\"]+)\"\\s*:\\s*(?:\"([^\"]*)\"|([^,{}]+))");
            Matcher kvMatcher = keyValuePattern.matcher(objectContent);

            while (kvMatcher.find()) {
                String key = kvMatcher.group(1);
                String value = kvMatcher.group(2) != null ? kvMatcher.group(2) : kvMatcher.group(3);
                if (value != null) {
                    map.put(key.trim(), value.trim().replace("\"", ""));
                }
            }
            list.add(map);
        }
        return list;
    }

    /**
     * Downloads the calendar entries from Azure and merges them into the local MySQL database.
     */
    /**
     * Downloads the calendar entries from Azure and merges them into the local MySQL database.
     */
    /**
     * Downloads the calendar entries from Azure and merges them into the local MySQL database.
     */
    public static void pullCalendarFromAzure() {
        try {
            // 1. Query to fetch all calendar records from the custom schema
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

            // 2. Parse the JSON response
            List<Map<String, String>> entries = parseJsonArray(response.body());
            if (entries.isEmpty()) {
                System.out.println("No calendar entries found on Azure SQL to sync.");
                return;
            }

            System.out.println("Processing " + entries.size() + " calendar entries for local import...");

            // 3. Establish local transaction
            Connection localConn = GetConnection.getConnectionWithLocalHost();
            localConn.setAutoCommit(false);

            // SQL using REPLACE INTO matching all 17 local columns
            String localSql = "REPLACE INTO diet_tracker_schema.calendar " +
                    "(day_date, day_name, product_name, amount_of_product, kcal, protein, fat, carbs, " +
                    "time_optional, comment_optional, kcal_consume, carbs_consume, fat_consume, " +
                    "protein_consume, meal_name, row_id, is_synced) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)"; // 1 = already synced

            PreparedStatement pstmt = localConn.prepareStatement(localSql);

            for (Map<String, String> item : entries) {
                // Clean day_date
                String rawDate = getStringSafe(item, "day_date");
                if (rawDate != null && rawDate.length() >= 10) {
                    rawDate = rawDate.substring(0, 10);
                }
                pstmt.setString(1, rawDate);

                pstmt.setString(2, item.getOrDefault("day_name", ""));
                pstmt.setString(3, item.getOrDefault("product_name", "Unknown"));
                pstmt.setFloat(4, getFloatSafe(item, "amount_of_product"));
                pstmt.setFloat(5, getFloatSafe(item, "kcal"));
                pstmt.setFloat(6, getFloatSafe(item, "protein"));
                pstmt.setFloat(7, getFloatSafe(item, "fat"));
                pstmt.setFloat(8, getFloatSafe(item, "carbs"));

                // Clean time_optional
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
                pstmt.setString(9, rawTime); // Safely sets database SQL NULL if rawTime is null

                pstmt.setString(10, getStringSafe(item, "comment_optional"));
                pstmt.setFloat(11, getFloatSafe(item, "kcal_consume"));
                pstmt.setFloat(12, getFloatSafe(item, "carbs_consume"));
                pstmt.setFloat(13, getFloatSafe(item, "fat_consume"));
                pstmt.setFloat(14, getFloatSafe(item, "protein_consume"));
                pstmt.setString(15, item.getOrDefault("meal_name", "None"));

                // Fetch the unique row ID
                pstmt.setString(16, item.getOrDefault("row_id", ""));
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            localConn.commit();
            pstmt.close();
            localConn.close();
            System.out.println("Calendar pulled and synced locally!");

        } catch (Exception e) {
            System.err.println("Calendar synchronization error: " + e.getMessage());
        }
    }


    private static String getStringSafe(Map<String, String> map, String key) {
        String val = map.get(key);
        if (val == null || val.trim().isEmpty() || val.trim().equalsIgnoreCase("null")) {
            return null;
        }
        return val.trim();
    }
}