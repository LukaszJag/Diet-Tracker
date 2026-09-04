package tools.azure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ManualSyncRunner {

    // Your local MySQL connection details from GetConnection.java
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/diet_tracker_schema";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASS = "sword444";

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("Starting manual synchronization to Azure Cloud...");
        System.out.println("=================================================");

        // 1. Trigger the background sync process
        AzureSqlSync.syncUnsyncedRowsToAzure();

        // 2. Keep this application alive and monitor the progress in real-time
        try {
            while (true) {
                int unsyncedCount = 0;

                try (Connection conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASS);
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM calendar WHERE is_synced = 0")) {

                    if (rs.next()) {
                        unsyncedCount = rs.getInt(1);
                    }
                }

                System.out.println("Unsynced rows remaining: " + unsyncedCount);

                if (unsyncedCount == 0) {
                    System.out.println("\nSuccess! All rows have been synchronized to Azure SQL.");
                    break;
                }

                // Wait 3 seconds before checking the database status again
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            System.err.println("Progress monitor error: " + e.getMessage());
        }
    }
}