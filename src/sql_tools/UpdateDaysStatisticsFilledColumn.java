package sql_tools;

import java.sql.Connection;
import java.sql.SQLException;

public class UpdateDaysStatisticsFilledColumn {

    public static void updateWholeMonthMay() throws SQLException {
        String dayDate = "2024-05-";

        for (int i = 1; i <= 31; i++) {
            if (String.valueOf(i).length() == 1) {
                dayDate = dayDate + "0" + String.valueOf(i);
            } else {
                dayDate = dayDate + String.valueOf(i);
            }

            RunQuery.runQueryOnCalendarTable(prepareQuery(dayDate));
            dayDate = "2024-05-";
        }
    }

    public static String prepareQuery(String day_date) {
        String updateQuery = "UPDATE `diet_tracker_schema`.`days_statistics_test`\n"
                + "SET\n"
                + "`amount_of_filled_points_from_notepad` = (SELECT COUNT(day_date) FROM calendar WHERE day_date ="
                + "'" + day_date + "')\n"
                + "WHERE day_date ="
                + "'" + day_date + "'";
        return updateQuery;
    }
}
