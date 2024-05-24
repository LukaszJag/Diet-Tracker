package sql_tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RunQuery {
    public static void runQuery(String SQLStatement) throws SQLException {
        if (SQLStatement == null){
            System.out.println("SQLStatement is null");
            return;
        }

        Connection connection = GetConnection.getConnectionWithLocalHost();
        PreparedStatement preparedStatement = connection.prepareStatement(SQLStatement);
        preparedStatement.execute(SQLStatement);
    }
}
