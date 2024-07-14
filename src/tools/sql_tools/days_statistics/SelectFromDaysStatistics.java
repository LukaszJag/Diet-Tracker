package tools.sql_tools.days_statistics;

import tools.products_tools.Macro;
import tools.sql_tools.general.GetConnection;
import tools.sql_tools.general.GetResultSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectFromDaysStatistics {
    public static Macro getMacroFromDaysStatisticsByDate(String SQLFriendlyDateFormat) {
        String sql = "SELECT * FROM days_statistics_test WHERE day_date = " + "\"" + SQLFriendlyDateFormat + "\"" + ";";

        Macro resultMacro = new Macro(-2, -2, -2, -2);

        Connection connection = null;
        Statement statementSQL;
        ResultSet resultSet;

        try {
            connection = GetConnection.getConnectionWithLocalHost();
            connection = GetConnection.getConnectionWithLocalHost();
            statementSQL = connection.createStatement();
            resultSet = statementSQL.executeQuery(sql);

            float kcalConsumeFloat = -2;
            float proteinConsumeFloat = -2;
            float fatConsumeFloat = -2;
            float carbsConsumeFloat = -2;

            if (resultSet.next() == false) {
                System.out.println("ResultSet .next() -> return: false");
            } else {

                if (resultSet.getString("kcal_consume") != null) {
                    kcalConsumeFloat = Float.valueOf(resultSet.getString("kcal_consume"));
                }

                if (resultSet.getString("protein_consume") != null) {
                    kcalConsumeFloat = Float.valueOf(resultSet.getString("protein_consume"));
                }

                if (resultSet.getString("fat_consume") != null) {
                    kcalConsumeFloat = Float.valueOf(resultSet.getString("fat_consume"));
                }

                if (resultSet.getString("carbs_consume") != null) {
                    kcalConsumeFloat = Float.valueOf(resultSet.getString("carbs_consume"));
                }

                resultMacro = new Macro(kcalConsumeFloat, proteinConsumeFloat, fatConsumeFloat, carbsConsumeFloat);
            }

            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            connection.close();
            resultSet.close();
            statementSQL.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultMacro;
    }
}
