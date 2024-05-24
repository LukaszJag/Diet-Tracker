package runners_and_tests;

import sql_tools.GenerateSLQTableForDaysStatistics;
import sql_tools.UpdateDaysStatisticsFilledColumns;

import java.sql.SQLException;

public class RunnerFullUpdateDayStatistics {
    public static void main(String[] args) throws SQLException {
        System.out.println("Start: full update - table days_statistics_test");

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepadMAY();
        System.out.println("generateWholeMonthAndFillAmountOfPointsFromNotepadMAY : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMay();
        System.out.println("updateWholeMonthMay : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSumMay();
        System.out.println("updateWholeMonthMacroSumMay : PASS");
    }
}
