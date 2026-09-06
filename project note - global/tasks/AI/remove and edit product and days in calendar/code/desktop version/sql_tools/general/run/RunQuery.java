package tools.sql_tools.general.run;

import configuration.Config;
import tools.sql_tools.general.get.GetConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RunQuery {
    public static void runQuery(String SQLStatement) throws SQLException {
        if (SQLStatement == null) {
            System.out.println("SQLStatement is null");
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = GetConnection.getConnectionWithLocalHost();
            preparedStatement = connection.prepareStatement(SQLStatement);
            preparedStatement.execute(SQLStatement);
        } catch (SQLException e) {
            System.out.println(Config.ANSI_RED + "SQL Exception in: RunQuery.runQuery()" + Config.ANSI_RESET);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }
}
