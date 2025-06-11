package runners_and_tests.run_update;

import gui.LoadingBarGUI;
import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;
import tools.sql_tools.days_statistics.UpdateDaysStatisticsFilledData;

import java.sql.SQLException;

public class RunnerFullUpdateDayStatistics {
    public static void main(String[] args) throws SQLException {
        runFullUpdateForAllMonthInDayStatistics();
    }

    public static void updateMonth(String month, int year) throws SQLException {
        System.out.println("Start: full update - table days_statistics_test -\t" + month + " - - " + year);

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad(month, year);
        UpdateDaysStatisticsFilledData.updateWholeMonthMacroSum(month, year);
        UpdateDaysStatisticsFilledData.updateAmountOfFilledPointsFromNotepad(month, year);

        System.out.println("Finish: full update - table days_statistics_test -\t" + month + " - - " + year);
    }


    public static void runFullUpdateForAllMonthInDayStatistics() throws SQLException {

        LoadingBarGUI loadingBarGUI = new LoadingBarGUI();
        String[] monthsFrom2024 = {"May", "June", "July", "August", "September", "October", "November", "December"};
        String[] monthsFrom2025 = {"January", "February", "March", "April", "May", "June"};

        int amountOfMonths = monthsFrom2024.length + monthsFrom2025.length;
        int progressForProgressBar = 100 / amountOfMonths;
        for (int i = 0; i < monthsFrom2024.length; i++) {
            System.out.println("Start: full update - table days_statistics_test -\t" + monthsFrom2024[i] + " - - " + 2024);

            GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad(monthsFrom2024[i], 2024);

            UpdateDaysStatisticsFilledData.updateWholeMonthMacroSum(monthsFrom2024[i], 2024);

            UpdateDaysStatisticsFilledData.updateAmountOfFilledPointsFromNotepad(monthsFrom2024[i], 2024);

            System.out.println("Finish: full update - table days_statistics_test -\t" + monthsFrom2024[i] + " - - " + 2024);
        }

        for (int i = 0; i < monthsFrom2025.length; i++) {
            System.out.println("Start: full update - table days_statistics_test -\t" + monthsFrom2025[i] + " - - " + 2025);

            GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad(monthsFrom2025[i], 2025);
            UpdateDaysStatisticsFilledData.updateWholeMonthMacroSum(monthsFrom2025[i], 2025);
            UpdateDaysStatisticsFilledData.updateAmountOfFilledPointsFromNotepad(monthsFrom2025[i], 2025);

            System.out.println("Finish: full update - table days_statistics_test -\t" + monthsFrom2025[i] + " - - " + 2025);
        }
    }

}
