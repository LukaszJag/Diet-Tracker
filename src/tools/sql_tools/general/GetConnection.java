package tools.sql_tools.general;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {
    public static Connection getConnectionWithLocalHost() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/diet_tracker_schema",
                "root",
                "sword444");
        return connection;
    }

    public static Connection getConnectionWithLocalHostWithoutTryCatch(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/diet_tracker_schema",
                    "root",
                    "sword444");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
