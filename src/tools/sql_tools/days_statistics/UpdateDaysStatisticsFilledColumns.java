package tools.sql_tools.days_statistics;

import tools.calendar_tools.MyDate;
import tools.sql_tools.general.RunQuery;

import java.sql.SQLException;

public class UpdateDaysStatisticsFilledColumns {

    //<editor-fold desc="updateWholeMonth">
    public static void updateWholeMonthMay() throws SQLException {
        String dayDate = "2024-05-";

        for (int i = 1; i <= 31; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForUpdateAmountOfFilledPointsFromNotepad(dayDate));


            dayDate = "2024-05-";
        }
    }
    public static void updateWholeMonthJune() throws SQLException {
        String dayDate = "2024-06-";

        for (int i = 1; i <= 30; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForUpdateAmountOfFilledPointsFromNotepad(dayDate));

            dayDate = "2024-06-";
        }
    }
    public static void updateWholeMonthJuly() throws SQLException {
        String dayDate = "2024-07-";

        for (int i = 1; i <= 31; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForUpdateAmountOfFilledPointsFromNotepad(dayDate));


            dayDate = "2024-07-";
        }
    }
    public static void updateWholeMonthAugust() throws SQLException {
        String dayDate = "2024-08-";

        for (int i = 1; i <= 31; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForUpdateAmountOfFilledPointsFromNotepad(dayDate));


            dayDate = "2024-08-";
        }
    }
    public static void updateWholeMonthSeptember() throws SQLException {
        String dayDate = "2024-09-";

        for (int i = 1; i <= 30; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForUpdateAmountOfFilledPointsFromNotepad(dayDate));


            dayDate = "2024-09-";
        }
    }
    public static void updateWholeMonthOctober() throws SQLException {
        String dayDate = "2024-10-";

        for (int i = 1; i <= 31; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForUpdateAmountOfFilledPointsFromNotepad(dayDate));


            dayDate = "2024-10-";
        }
    }
    public static void updateWholeMonthNovember() throws SQLException {
        String dayDate = "2024-11-";

        for (int i = 1; i <= 30; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForUpdateAmountOfFilledPointsFromNotepad(dayDate));


            dayDate = "2024-11-";
        }
    }
    public static void updateWholeMonthDecember() throws SQLException {
        String dayDate = "2024-12-";

        for (int i = 1; i <= 31; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForUpdateAmountOfFilledPointsFromNotepad(dayDate));


            dayDate = "2024-12-";
        }
    }

    //</editor-fold>

    //<editor-fold desc="updateWholeMonthMacroSum">
    public static void updateWholeMonthMacroSumMay() throws SQLException {
        String dayDate = "2024-05-";

        for (int i = 1; i <= 31; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForFillConsumedMacro(dayDate));
            dayDate = "2024-05-";
        }
    }
    public static void updateWholeMonthMacroSumJune() throws SQLException {
        String dayDate = "2024-06-";

        for (int i = 1; i <= 30; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForFillConsumedMacro(dayDate));
            dayDate = "2024-06-";
        }
    }
    public static void updateWholeMonthMacroSumJuly() throws SQLException {
        String dayDate = "2024-07-";

        for (int i = 1; i <= 31; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForFillConsumedMacro(dayDate));
            dayDate = "2024-07-";
        }
    }
    public static void updateWholeMonthMacroSumAugust() throws SQLException {
        String dayDate = "2024-08-";

        for (int i = 1; i <= 31; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForFillConsumedMacro(dayDate));
            dayDate = "2024-08-";
        }
    }
    public static void updateWholeMonthMacroSumSeptember() throws SQLException {
        String dayDate = "2024-09-";

        for (int i = 1; i <= 31; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForFillConsumedMacro(dayDate));
            dayDate = "2024-09-";
        }
    }
    public static void updateWholeMonthMacroSumOctober() throws SQLException {
        String dayDate = "2024-10-";

        for (int i = 1; i <= 31; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForFillConsumedMacro(dayDate));
            dayDate = "2024-10-";
        }
    }
    public static void updateWholeMonthMacroSumNovember() throws SQLException {
        String dayDate = "2024-11-";

        for (int i = 1; i <= 30; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForFillConsumedMacro(dayDate));
            dayDate = "2024-11-";
        }
    }

    public static void updateWholeMonthMacroSumDecember() throws SQLException {
        String dayDate = "2024-12-";

        for (int i = 1; i <= 31; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForFillConsumedMacro(dayDate));
            dayDate = "2024-12-";
        }
    }


    //</editor-fold>
    public static void updateWholeMonthMacroSum(String month, int year) throws SQLException {
        int monthInt = MyDate.getNumberOfMonthInYear(month);
        String dayDate = year + "-";

        if (monthInt < 10){
            dayDate =  dayDate  + "0" +  monthInt + "-";

        }

        if (monthInt >= 10){
            dayDate += month + "-";
        }

        String dayDateBuffor = dayDate;

        for (int i = 1; i <= 31; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQuery(prepareQueryForFillConsumedMacro(dayDate));
            dayDate = dayDateBuffor;
        }
    }

    public static void updateAmountOfFilledPointsFromNotepad(String month, int year) throws SQLException {
        int monthInt = MyDate.getNumberOfMonthInYear(month);
        String dayDate = year + "-";

        if (monthInt < 10){
            dayDate =  dayDate  + "0" +  monthInt + "-";

        }

        if (monthInt >= 10){
            dayDate += month + "-";
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
