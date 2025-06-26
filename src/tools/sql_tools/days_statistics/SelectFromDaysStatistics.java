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
        if (getResultSet.resultSetNextReturnValue(resultSet) == false) {
        } else {
            if (GetResultSet.getFromResultSetGetString("amount_of_filled_points_from_notepad", resultSet) != null) {
                amountOfFilledPointsFromNotepad = Integer.valueOf(GetResultSet.getFromResultSetGetString("amount_of_filled_points_from_notepad", resultSet));
            }
        }
        return amountOfFilledPointsFromNotepad;
    }

    public static Macro getAverageMacroForMonth(int year, int month, int beginFromDayNumber, int endToDayNumber) {
        //<editor-fold desc="Setup variables">
        Macro averageMacro = new Macro(0, 0, 0, 0);
        Macro sumMacro = new Macro(0, 0, 0, 0);

        int amountOfDays = endToDayNumber - beginFromDayNumber;
        String[] dayInSQLFriendlyFormat = new String[endToDayNumber - beginFromDayNumber];
        //</editor-fold>


        //<editor-fold desc="Fill String array with SQL friendly days">
        int beginFromDayNumberTMP = beginFromDayNumber;
        for (int i = 0; i < dayInSQLFriendlyFormat.length; i++) {
            beginFromDayNumberTMP += 1;
            if (beginFromDayNumberTMP < 10) {
                dayInSQLFriendlyFormat[i] = "0" + beginFromDayNumberTMP;
            }else {
                dayInSQLFriendlyFormat[i] ="" + beginFromDayNumberTMP;
            }
        }
        //</editor-fold>


        //<editor-fold desc="Prepare correct SQL month format ">
        String monthString = "" + month;
        if (monthString.length() == 1) {
            monthString = "0" + monthString;
        }
        //</editor-fold>

        //<editor-fold desc="Sum of all days Macro">
        String yearAndMonthInSQLFriendlyFormat = year + "-" + monthString + "-";
        String dateSQLFormat;
        for (int i = 0; i < dayInSQLFriendlyFormat.length; i++) {
            dateSQLFormat =yearAndMonthInSQLFriendlyFormat + dayInSQLFriendlyFormat[i];
            sumMacro = Macro.sumOfTwoMacros(sumMacro, SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(
                    dateSQLFormat));
        }
        //</editor-fold>

        averageMacro = Macro.divisionMacroByValue(sumMacro, amountOfDays);
        System.out.println(Macro.getShortMacroInformationPrettyFormat(sumMacro));
        System.out.println(Macro.getShortMacroInformationPrettyFormat(averageMacro));
        return averageMacro;
    }
}