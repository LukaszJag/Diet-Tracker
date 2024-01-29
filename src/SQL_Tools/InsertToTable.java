package SQL_Tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InsertToTable {
    Connection connection;

    private void insertProductTestOnSimpleTable(){
        setConnectionBySchema("test_simple_table");
    }

    private void setConnectionBySchema(String nameOfSchema){
        try {
            this.connection = DriverManager.getConnection(
                    ("jdbc:mysql://localhost:3306/" + nameOfSchema),
                    "root",
                    "sword444"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
