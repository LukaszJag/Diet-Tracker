package tools.sql_tools.days_statistics;

import tools.calendar_tools.MyDate;
import tools.products_tools.Macro;
import tools.sql_tools.general.GetResultSet;
import tools.sql_tools.general.SumTable;

import java.sql.ResultSet;
import java.util.Hashtable;

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
        if (getResultSet.resultSetNextReturnValue(resultSet) == false) {
        } else {
            if (GetResultSet.getFromResultSetGetString("amount_of_filled_points_from_notepad", resultSet) != null) {
                amountOfFilledPointsFromNotepad = Integer.valueOf(GetResultSet.getFromResultSetGetString("amount_of_filled_points_from_notepad", resultSet));
            }
        }
        return amountOfFilledPointsFromNotepad;
    }

    public static Macro getAverageMacroForMonth(int year, int month, int beginFromDayNumber, int endToDayNumber) {
        String tableName = "days_statistics_test";
        String[] fieldsNamesToSum = {"kcal_consume", "protein_consume", "fat_consume", "carbs_consume"};
        String whereColumnName = "day_date";

        String monthInSQLFormat = "";
        if (month < 10) {
            monthInSQLFormat = year + "-" + "0" + month + "%";
        } else {
            monthInSQLFormat = year + "-" + month + "%";
        }
        Hashtable<String, Float> macroTable = SumTable.sumRowsInTableWhereMonthOfTime(tableName, fieldsNamesToSum, whereColumnName, monthInSQLFormat);

        Macro sumMacro = new Macro(
                macroTable.get("kcal_consume"),
                macroTable.get("protein_consume"),
                macroTable.get("fat_consume"),
                macroTable.get("carbs_consume"));

        int amountOfDays = endToDayNumber - beginFromDayNumber + 1;

        Macro averageMacro = new Macro(
                sumMacro.getKcal(),
                sumMacro.getProtein(),
                sumMacro.getFat(),
                sumMacro.getCarbs());

        averageMacro = Macro.divisionMacroByValue(averageMacro, amountOfDays);

        return averageMacro;
    }

    public static Macro getAverageMacroForMonth(int year, int month) {
        String tableName = "days_statistics_test";
        String[] fieldsNamesToSum = {"kcal_consume", "protein_consume", "fat_consume", "carbs_consume"};
        String whereColumnName = "day_date";

        String monthInSQLFormat = "";
        if (month < 10) {
            monthInSQLFormat = year + "-" + "0" + month + "%";
        } else {
            monthInSQLFormat = year + "-" + month + "%";
        }
        Hashtable<String, Float> macroTable = SumTable.sumRowsInTableWhereMonthOfTime(tableName, fieldsNamesToSum, whereColumnName, monthInSQLFormat);

        if (!macroTable.isEmpty()) {
            Macro sumMacro = new Macro(
                    macroTable.get("kcal_consume"),
                    macroTable.get("protein_consume"),
                    macroTable.get("fat_consume"),
                    macroTable.get("carbs_consume"));


            int amountOfDays = MyDate.getAmountOfDaysInCurrentMonthOPassedMonth(year, month);

            Macro averageMacro = new Macro(
                    sumMacro.getKcal(),
                    sumMacro.getProtein(),
                    sumMacro.getFat(),
                    sumMacro.getCarbs());

            averageMacro = Macro.divisionMacroByValue(averageMacro, amountOfDays);

            return averageMacro;
        }
        return new Macro(0,0,0,0);
    }
}