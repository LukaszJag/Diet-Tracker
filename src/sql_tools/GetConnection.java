package sql_tools;

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
}
