package runners_and_tests;

import sql_tools.UpdateDaysStatisticsFilledColumn;

import java.sql.SQLException;

public class RunnerUpdateDayStatisticsFilledColumn {
    public static void main(String[] args) throws SQLException {
        UpdateDaysStatisticsFilledColumn.updateWholeMonthMay();
    }
}
