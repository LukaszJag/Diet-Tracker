package tools.azure;

import tools.debug_tools.Debug;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class AzureSqlSync {

    // Replace with your actual deployed Azure Function URL
    private static final String AZURE_FUNCTION_URL = "https://diettrackerandroidversionapi-grcbhva9e5gqhzhz.polandcentral-01.azurewebsites.net/api/AndroidAzure";

    /**
     * Entry point to send a query to the Azure Function.
     * This method handles cleaning the query and shipping it asynchronously.
     */
    public static void syncQueryToAzure(String mysqlQuery) {
        if (mysqlQuery == null || mysqlQuery.trim().isEmpty()) {
            return;
        }

        try {
            // 1. Clean the SQL dialect differences
            String mssqlQuery = cleanQueryForAzure(mysqlQuery);

            // 2. Escape backslashes and double quotes to construct valid JSON
            String escapedQuery = mssqlQuery.replace("\\", "\\\\").replace("\"", "\\\"");
            String jsonPayload = "{\"sql_query\":\"" + escapedQuery + "\"}";

            // 3. Initialize Java's HttpClient
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            // 4. Build HTTP POST Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(AZURE_FUNCTION_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // 5. Send asynchronously so your Swing GUI does not freeze
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        if (response.statusCode() == 200) {
                            Debug.printGreenSystemPrintln("Successfully synced to Azure SQL.");
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

    private static String cleanQueryForAzure(String mysqlQuery) {
        if (mysqlQuery == null) return "";

        // 1. DO NOT remove "diet_tracker_schema." here, as it matches your Azure SQL schema structure.

        // 2. Replace backticks with a SPACE to keep SQL keywords separate.
        String cleanQuery = mysqlQuery.replace("`", " ");

        // 3. Replace actual line breaks (\n and \r) with spaces to prevent bad JSON characters.
        cleanQuery = cleanQuery.replace("\n", " ").replace("\r", " ");

        // 4. Shrink multiple consecutive spaces into a single space
        cleanQuery = cleanQuery.replaceAll("\\s+", " ");

        // 5. Fix string escaping: Replace backslash-escaped quotes (\') with standard SQL doubled single quotes ('')
        cleanQuery = cleanQuery.replace("\\'", "''");

        return cleanQuery.trim();
    }
}