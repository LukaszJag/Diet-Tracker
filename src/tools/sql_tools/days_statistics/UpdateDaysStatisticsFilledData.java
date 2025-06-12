package tools.sql_tools.days_statistics;

import tools.calendar_tools.MyDate;
import tools.sql_tools.general.RunQuery;

import java.sql.SQLException;

public class UpdateDaysStatisticsFilledData {

    public static void updateWholeMonthMacroSum(String month, int year) throws SQLException {
        int monthInt = MyDate.getNumberOfMonthInYear(month);
        String dayDate = year + "-";

        if (monthInt < 10) {
            dayDate = dayDate + "0" + monthInt + "-";

        }

        if (monthInt >= 10) {
            dayDate += monthInt + "-";
        }

        String dayDateBuffor = dayDate;

        for (int i = 1; i <= MyDate.getAmountOfDaysInMonth(month); i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForFillConsumedMacro(dayDate));
            dayDate = dayDateBuffor;
        }
    }

    public static void updateWholeMonthMacroSum(int numberOfDay, String month, int year) throws SQLException {
        int monthInt = MyDate.getNumberOfMonthInYear(month);
        String dayDate = year + "-";

        if (monthInt < 10) {
            dayDate = dayDate + "0" + monthInt + "-";

        }

        if (monthInt >= 10) {
            dayDate += monthInt + "-";
        }

        String dayDateBuffor = dayDate;


        if (numberOfDay < 10) {
            dayDate = dayDate + "0" + String.valueOf(numberOfDay);
        } else {
            dayDate = dayDate + String.valueOf(numberOfDay);
        }

        RunQuery.runQuery(prepareQueryForFillConsumedMacro(dayDate));
        dayDate = dayDateBuffor;
    }

    public static void updateAmountOfFilledPointsFromNotepad(String month, int year) throws SQLException {
        int monthInt = MyDate.getNumberOfMonthInYear(month);
        String dayDate = year + "-";

        if (monthInt < 10) {
            dayDate = dayDate + "0" + monthInt + "-";

        }

        if (monthInt >= 10) {
            dayDate += monthInt + "-";
        }

        String dayDateBuffor = dayDate;

        for (int i = 1; i <= MyDate.getAmountOfDaysInMonth(month); i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForUpdateAmountOfFilledPointsFromNotepad(dayDate));
            dayDate = dayDateBuffor;
        }
    }

    public static void updateAmountOfFilledPointsFromNotepad(int numberOfDay, String month, int year) throws SQLException {
        int monthInt = MyDate.getNumberOfMonthInYear(month);
        String dayDate = year + "-";

        if (monthInt < 10) {
            dayDate = dayDate + "0" + monthInt + "-";

        }

        if (monthInt >= 10) {
            dayDate += monthInt + "-";
        }

        String dayDateBuffor = dayDate;


        if (numberOfDay < 10) {
            dayDate = dayDate + "0" + String.valueOf(numberOfDay);
        } else {
            dayDate = dayDate + String.valueOf(numberOfDay);
        }

        RunQuery.runQuery(prepareQueryForUpdateAmountOfFilledPointsFromNotepad(dayDate));
        dayDate = dayDateBuffor;

    }

    public static String prepareQueryForUpdateAmountOfFilledPointsFromNotepad(String day_date) {
        String updateQuery = "UPDATE `diet_tracker_schema`.`days_statistics_test`\n"
                + "SET\n"
                + "`amount_of_filled_points_from_notepad` = (SELECT COUNT(day_date) FROM calendar WHERE day_date ="
                + "'" + day_date + "')\n"
                + "WHERE day_date ="
                + "'" + day_date + "'";
        return updateQuery;
    }

    public static String prepareQueryForFillConsumedMacro(String day_date) {
        String query = "UPDATE `diet_tracker_schema`.`days_statistics_test`\n"
                + "SET\n"
                + "kcal_consume = (SELECT SUM(kcal_consume) FROM calendar WHERE day_date = '" + day_date + "'),\n"
                + "protein_consume = (SELECT SUM(protein_consume) FROM calendar WHERE day_date = '" + day_date + "'),\n"
                + "fat_consume = (SELECT SUM(fat_consume) FROM calendar WHERE day_date = '" + day_date + "'),\n"
                + "carbs_consume = (SELECT SUM(carbs_consume) FROM calendar WHERE day_date = '" + day_date + "')\n"
                + "WHERE day_date = '" + day_date + "';";
        return query;
    }
}
