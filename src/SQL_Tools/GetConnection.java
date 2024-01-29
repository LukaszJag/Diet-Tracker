package SQL_Tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {
    public static Connection getConnectionWithLocalHost() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/products",
                "root",
                "sword444");
        return connection;
    }
}
