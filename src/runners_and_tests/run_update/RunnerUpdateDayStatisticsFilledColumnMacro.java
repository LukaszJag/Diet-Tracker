package runners_and_tests.run_update;

import tools.sql_tools.days_statistics.UpdateDaysStatisticsFilledColumns;

import java.sql.SQLException;

public class RunnerUpdateDayStatisticsFilledColumnMacro {
    public static void main(String[] args) throws SQLException {
        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSumMay();
    }
}
