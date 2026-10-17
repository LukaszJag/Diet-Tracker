package tools.sql_tools.calendar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CloseAllSQLVariables {
    public static void closeAllSQLVariables(Connection connection, ResultSet resultSet, Statement statementSQL) {
        try {
            resultSet.close();
            statementSQL.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeSQLVariablesConnectionStatement(Connection connection, Statement statementSQL) {
        try {
            connection.close();
            statementSQL.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
