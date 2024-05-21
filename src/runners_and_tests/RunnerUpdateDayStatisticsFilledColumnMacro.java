package runners_and_tests;

import sql_tools.UpdateDaysStatisticsFilledColumns;

import java.sql.SQLException;

public class RunnerUpdateDayStatisticsFilledColumnMacro {
    public static void main(String[] args) throws SQLException {
        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSumMay();
    }
}
