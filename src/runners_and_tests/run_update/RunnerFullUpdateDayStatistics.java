package runners_and_tests.run_update;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;
import tools.sql_tools.days_statistics.UpdateDaysStatisticsFilledColumns;

import java.sql.SQLException;

public class RunnerFullUpdateDayStatistics {
    public static void main(String[] args) throws SQLException {
        updateDaysStatisticsMay();
        updateDaysStatisticsJune();
        updateDaysStatisticsJuly();
        updateDaysStatisticsAugust();
    }

    private static void updateDaysStatisticsJune() throws SQLException {
        System.out.println("Start: full update - table days_statistics_test June");

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepadJUNE();
        System.out.println("generateWholeMonthAndFillAmountOfPointsFromNotepadJune : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthJune();
        System.out.println("updateWholeMonthJune : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSumJune();
        System.out.println("updateWholeMonthMacroSumJune : PASS");
    }

    private static void updateDaysStatisticsMay() throws SQLException {
        System.out.println("Start: full update - table days_statistics_test - May");

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepadMAY();
        System.out.println("generateWholeMonthAndFillAmountOfPointsFromNotepadMAY : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMay();
        System.out.println("updateWholeMonthMay : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSumMay();
        System.out.println("updateWholeMonthMacroSumMay : PASS");
    }

    public static void updateDaysStatisticsJuly() throws SQLException {
        System.out.println("Start: full update - table days_statistics_test - July");

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepadJULY();



        System.out.println("generateWholeMonthAndFillAmountOfPointsFromNotepadJULY : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthJuly();
        System.out.println("updateWholeMonthJuly : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSumJuly();
        System.out.println("updateWholeMonthMacroSumMay : PASS");
    }

    public static void updateDaysStatisticsAugust() throws SQLException {
        System.out.println("Start: full update - table days_statistics_test - August");

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepadAUGUST();



        System.out.println("generateWholeMonthAndFillAmountOfPointsFromNotepadAugust : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthAugust();
        System.out.println("updateWholeMonthAugust : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSumAugust();
        System.out.println("updateWholeMonthMacroSumMay : PASS");
    }

    public static void runFullUpdateForDayStatistics() throws SQLException {
        updateDaysStatisticsMay();
        updateDaysStatisticsJune();
        updateDaysStatisticsJuly();
    }
}
