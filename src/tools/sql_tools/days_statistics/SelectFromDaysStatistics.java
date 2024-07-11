package tools.sql_tools.days_statistics;

import tools.products_tools.Macro;
import tools.sql_tools.general.GetConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectFromDaysStatistics {
    public Macro getMacroFromDaysStatisticsByDate(String SQLFriendlyDateFormat){
        Connection connection;
        ResultSet resultSet;
        Statement statementSQL;

        Macro resultMacro = new Macro(-1,-1,-1,-1);
        String sql = "SELECT * FROM days_statistics_test WHERE day_date = " + SQLFriendlyDateFormat + ";";
        String result = "";

        try {
            connection = GetConnection.getConnectionWithLocalHost();
            statementSQL = connection.createStatement();
            resultSet = statementSQL.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {


            resultMacro.setKcal(Float.valueOf(resultSet.getString("kcal_consume")));

            resultMacro.setKcal(Float.valueOf(resultSet.getString("kcal_consume")));

            resultMacro.setKcal(Float.valueOf(resultSet.getString("kcal_consume")));

            resultMacro.setKcal(Float.valueOf(resultSet.getString("kcal_consume")));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            resultSet.close();
            statementSQL.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
