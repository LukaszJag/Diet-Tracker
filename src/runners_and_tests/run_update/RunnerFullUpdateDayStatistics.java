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
        updateDaysStatisticsSeptember();
        updateDaysStatisticsNovember();
    }

    public static void updateMonth(String month) throws SQLException {
        if (month.equalsIgnoreCase("november")) {
            updateDaysStatisticsNovember();
        }

        if (month.equalsIgnoreCase("september")) {
            updateDaysStatisticsSeptember();
        }

        if (month.equalsIgnoreCase("august")) {
            updateDaysStatisticsAugust();
        }

        if (month.equalsIgnoreCase("july")) {
            updateDaysStatisticsJuly();
        }
        if (month.equalsIgnoreCase("june")) {
            updateDaysStatisticsJune();
        }

        if (month.equalsIgnoreCase("may")) {
            updateDaysStatisticsMay();
        }
    }


    //<editor-fold desc="updateDaysStatistics">
    private static void updateDaysStatisticsJune() throws SQLException {
        System.out.println("Start: full update - table days_statistics_test June");

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("June", 2024);
        System.out.println("generateWholeMonthAndFillAmountOfPointsFromNotepadJune : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthJune();
        System.out.println("updateWholeMonthJune : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSumJune();
        System.out.println("updateWholeMonthMacroSumJune : PASS");
    }

    private static void updateDaysStatisticsMay() throws SQLException {
        System.out.println("Start: full update - table days_statistics_test - May");

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("May", 2024);
        System.out.println("generateWholeMonthAndFillAmountOfPointsFromNotepadMAY : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMay();
        System.out.println("updateWholeMonthMay : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSumMay();
        System.out.println("updateWholeMonthMacroSumMay : PASS");
    }

    public static void updateDaysStatisticsJuly() throws SQLException {
        System.out.println("Start: full update - table days_statistics_test - July");

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("July", 2024);


        System.out.println("generateWholeMonthAndFillAmountOfPointsFromNotepadJULY : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthJuly();
        System.out.println("updateWholeMonthJuly : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSumJuly();
        System.out.println("updateWholeMonthMacroSumMay : PASS");
    }

    public static void updateDaysStatisticsAugust() throws SQLException {
        System.out.println("Start: full update - table days_statistics_test - August");

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("August", 2024);


        System.out.println("generateWholeMonthAndFillAmountOfPointsFromNotepadAugust : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthAugust();
        System.out.println("updateWholeMonthAugust : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSumAugust();
        System.out.println("updateWholeMonthMacroSumMay : PASS");
    }

    public static void updateDaysStatisticsSeptember() throws SQLException {
        System.out.println("Start: full update - table days_statistics_test - September");

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("September", 2024);


        System.out.println("generateWholeMonthAndFillAmountOfPointsFromNotepadSeptember : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthSeptember();
        System.out.println("updateWholeMonthSeptember : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSumSeptember();
        System.out.println("updateWholeMonthMacroSumMay : PASS");
    }

    public static void updateDaysStatisticsOctober() throws SQLException {
        System.out.println("Start: full update - table days_statistics_test - October");

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("October", 2024);


        System.out.println("generateWholeMonthAndFillAmountOfPointsFromNotepadOctober : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthOctober();
        System.out.println("updateWholeMonthOctober : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSumOctober();
        System.out.println("updateWholeMonthMacroSumMay : PASS");
    }

    private static void updateDaysStatisticsNovember() throws SQLException {
        System.out.println("Start: full update - table days_statistics_test - November");

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("November", 2024);


        System.out.println("generateWholeMonthAndFillAmountOfPointsFromNotepadOctober : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthNovember();
        System.out.println("updateWholeMonthOctober : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSumNovember();
        System.out.println("updateWholeMonthMacroSumMay : PASS");
    }

    private static void updateDaysStatisticsDecember() throws SQLException {
        System.out.println("Start: full update - table days_statistics_test - November");

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("December", 2024);


        System.out.println("generateWholeMonthAndFillAmountOfPointsFromNotepadDecember : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthDecember();
        System.out.println("updateWholeMonthDecember : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSumDecember();
        System.out.println("updateWholeMonthMacroSumMay : PASS");
    }

    private static void updateDaysStatisticsJanuary2025() throws SQLException {
        System.out.println("Start: full update - table days_statistics_test - November");

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("January", 2025);


        System.out.println("generateWholeMonthAndFillAmountOfPointsFromNotepadJanuary : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSum("January", 2025);
        System.out.println("updateWholeMonthJanuary : PASS");

        UpdateDaysStatisticsFilledColumns.updateWholeMonthMacroSum("January", 2025);
        System.out.println("updateWholeMonthMacroSumMay : PASS");
    }

    //</editor-fold>

    public static void runFullUpdateForDayStatistics() throws SQLException {
        updateDaysStatisticsMay();
        updateDaysStatisticsJune();
        updateDaysStatisticsJuly();
        updateDaysStatisticsAugust();
        updateDaysStatisticsSeptember();
        updateDaysStatisticsOctober();
        updateDaysStatisticsNovember();
        updateDaysStatisticsDecember();
        updateDaysStatisticsJanuary2025();
    }
}
