package tools.sql_tools.days_statistics;

import tools.products_tools.Macro;
import tools.sql_tools.general.GetResultSet;

import java.sql.ResultSet;

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


        getResultSet.closeResultSet(resultSet);
        getResultSet.closeAllVariables();

        return resultMacro;
    }

    public static int getAmountOfPointsFromNotepad(String SQLFriendlyDateFormat) {
        ResultSet resultSet;
        int amountOfPointsFromNotepad = -1;

        String sql = "SELECT * FROM days_statistics_test WHERE day_date = " + "\"" + SQLFriendlyDateFormat + "\"" + ";";

        GetResultSet getResultSet = new GetResultSet();
        resultSet = getResultSet.getResultSetFromSQL(sql);
        if (getResultSet.resultSetNextReturnValue(resultSet) == false) {
        } else {
            if (GetResultSet.getFromResultSetGetString("amount_of_points_from_notepad", resultSet) != null) {
                amountOfPointsFromNotepad = Integer.valueOf(GetResultSet.getFromResultSetGetString("amount_of_points_from_notepad", resultSet));
                System.out.println("amount_of_points_from_notepad" + amountOfPointsFromNotepad);
            }
        }

        return amountOfPointsFromNotepad;
    }

    public static int getAmountOfFilledPointsFromNotepad(String SQLFriendlyDateFormat) {
        ResultSet resultSet;
        int amountOfFilledPointsFromNotepad = -1;

        String sql = "SELECT * FROM days_statistics_test WHERE day_date = " + "\"" + SQLFriendlyDateFormat + "\"" + ";";

        GetResultSet getResultSet = new GetResultSet();
        resultSet = getResultSet.getResultSetFromSQL(sql);
        if (getResultSet.resultSetNextReturnValue(resultSet) == false) {}
        else {
            if (GetResultSet.getFromResultSetGetString("amount_of_filled_points_from_notepad", resultSet) != null) {
                amountOfFilledPointsFromNotepad = Integer.valueOf(GetResultSet.getFromResultSetGetString("amount_of_filled_points_from_notepad", resultSet));
            }
        }
        return amountOfFilledPointsFromNotepad;
    }
}