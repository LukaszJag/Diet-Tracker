package sql_tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RunQuery {
    public static void runQueryOnCalendarTable(String SQLStatement) throws SQLException {
        Connection connection = GetConnection.getConnectionWithLocalHost();
        PreparedStatement preparedStatement = connection.prepareStatement(SQLStatement);

        System.out.println("SQLStatement after execute: \n\n" + SQLStatement);
        preparedStatement.execute(SQLStatement);
    }
}
