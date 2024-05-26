package runners_and_tests;

import tools.sql_tools.days_statistics.UpdateDaysStatisticsFilledColumns;

import java.sql.SQLException;

public class RunnerUpdateDayStatisticsFilledColumn {
    public static void main(String[] args) throws SQLException {
        UpdateDaysStatisticsFilledColumns.updateWholeMonthMay();
    }
}
