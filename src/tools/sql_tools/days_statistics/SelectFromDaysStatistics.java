package tools.sql_tools.days_statistics;

import tools.products_tools.Macro;
import tools.sql_tools.general.GetConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectFromDaysStatistics {
    public static Macro getMacroFromDaysStatisticsByDate(String SQLFriendlyDateFormat) throws SQLException {
        Connection connection;
        ResultSet resultSet;
        Statement statementSQL;

        String sql = "SELECT * FROM days_statistics_test WHERE day_date = " + "\"" + SQLFriendlyDateFormat + "\"" + ";";

        connection = GetConnection.getConnectionWithLocalHost();
        statementSQL = connection.createStatement();
        resultSet = statementSQL.executeQuery(sql);

        float kcalConsumeFloat = -2;
        float proteinConsumeFloat = -2;
        float fatConsumeFloat = -2;
        float carbsConsumeFloat= -2;

        resultSet.next();

        if (resultSet.getString("kcal_consume") != null) {
            kcalConsumeFloat = Float.valueOf(resultSet.getString("kcal_consume"));
        }

        if (resultSet.getString("protein_consume") != null) {
            proteinConsumeFloat = Float.valueOf(resultSet.getString("protein_consume"));
        }

        if (resultSet.getString("fat_consume") != null) {
            fatConsumeFloat = Float.valueOf(resultSet.getString("fat_consume"));
        }

        if (resultSet.getString("carbs_consume") != null) {
            carbsConsumeFloat = Float.valueOf(resultSet.getString("carbs_consume"));
        }

        Macro resultMacro = new Macro(-1, -1, -1, -1);
        resultMacro = new Macro(kcalConsumeFloat, proteinConsumeFloat, fatConsumeFloat, carbsConsumeFloat);

        resultSet.close();
        statementSQL.close();
        connection.close();


        return resultMacro;
    }
}
