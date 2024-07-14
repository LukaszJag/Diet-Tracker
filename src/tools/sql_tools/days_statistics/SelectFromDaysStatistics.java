package tools.sql_tools.days_statistics;

import tools.products_tools.Macro;
import tools.sql_tools.general.GetResultSet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectFromDaysStatistics {
    public static Macro getMacroFromDaysStatisticsByDate(String SQLFriendlyDateFormat) {
        ResultSet resultSet;

        String sql = "SELECT * FROM days_statistics_test WHERE day_date = " + "\"" + SQLFriendlyDateFormat + "\"" + ";";

        GetResultSet getResultSet = new GetResultSet();
        resultSet = getResultSet.getResultSetFromSQL(sql);

        Macro resultMacro = new Macro(-2, -2, -2, -2);
        float kcalConsumeFloat = -2;
        float proteinConsumeFloat = -2;
        float fatConsumeFloat = -2;
        float carbsConsumeFloat = -2;

        if (getResultSet.resultSetNextReturnValue(resultSet) == false) {
            System.out.println("ResultSet .next() -> return: false");
        } else {

            if (GetResultSet.getFromResultSetGetString("kcal_consume", resultSet) != null) {
                kcalConsumeFloat = Float.valueOf(GetResultSet.getFromResultSetGetString("kcal_consume", resultSet));
            }

            if (GetResultSet.getFromResultSetGetString("protein_consume", resultSet) != null) {
                proteinConsumeFloat = Float.valueOf(GetResultSet.getFromResultSetGetString("protein_consume", resultSet));
            }

            if (GetResultSet.getFromResultSetGetString("fat_consume", resultSet) != null) {
                fatConsumeFloat = Float.valueOf(GetResultSet.getFromResultSetGetString("fat_consume", resultSet));
            }

            if (GetResultSet.getFromResultSetGetString("carbs_consume", resultSet) != null) {
                carbsConsumeFloat = Float.valueOf(GetResultSet.getFromResultSetGetString("carbs_consume", resultSet));
            }
            resultMacro = new Macro(kcalConsumeFloat, proteinConsumeFloat, fatConsumeFloat, carbsConsumeFloat);
        }

        try {
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Before close");
        getResultSet.checkGetResultSetStatus();

        getResultSet.closeResultSet(resultSet);
        getResultSet.closeAllVariables();

        System.out.println("After close");
        getResultSet.checkGetResultSetStatus();
        return resultMacro;
    }
}
