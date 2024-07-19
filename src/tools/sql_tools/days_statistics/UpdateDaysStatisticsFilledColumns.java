package tools.sql_tools.days_statistics;

import tools.sql_tools.CheckIfRowExist;
import tools.sql_tools.general.RunQuery;

import java.sql.SQLException;

public class UpdateDaysStatisticsFilledColumns {

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
    public static String prepareQueryForUpdateAmountOfFilledPointsFromNotepad(String day_date) {
        String updateQuery = "UPDATE `diet_tracker_schema`.`days_statistics_test`\n"
                + "SET\n"
                + "`amount_of_filled_points_from_notepad` = (SELECT COUNT(day_date) FROM calendar WHERE day_date ="
                + "'" + day_date + "')\n"
                + "WHERE day_date ="
                + "'" + day_date + "'";
        return updateQuery;
    }

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
