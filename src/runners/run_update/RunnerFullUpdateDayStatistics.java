package runners.run_update;

import tools.calendar_tools.MyDate;
import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;
import tools.sql_tools.days_statistics.UpdateDaysStatisticsFilledData;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class RunnerFullUpdateDayStatistics {

    static String[] monthsFrom2024 = {"May", "June", "July", "August", "September", "October", "November", "December"};
    static String[] monthsFrom2025 = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    static String[] monthsFrom2026 = {"January", "February", "March", "April", "May"};

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
        long start = System.nanoTime();
        for (int i = 0; i < monthsFrom2024.length; i++) {
            System.out.println("Start: full update - table days_statistics_test -\t" + monthsFrom2024[i] + " - - " + 2024);

            GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad(monthsFrom2024[i], 2024);

            UpdateDaysStatisticsFilledData.updateWholeMonthMacroSum(monthsFrom2024[i], 2024);

            UpdateDaysStatisticsFilledData.updateAmountOfFilledPointsFromNotepad(monthsFrom2024[i], 2024);

            System.out.println("Finish: full update - table days_statistics_test -\t" + monthsFrom2024[i] + " - - " + 2024);

        }

        System.out.println("Refrashed 2024: " + "\t" + (TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start)));
        for (int i = 0; i < monthsFrom2025.length; i++) {
            System.out.println("Start: full update - table days_statistics_test -\t" + monthsFrom2025[i] + " - - " + 2025);

            GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad(monthsFrom2025[i], 2025);
            UpdateDaysStatisticsFilledData.updateWholeMonthMacroSum(monthsFrom2025[i], 2025);
            UpdateDaysStatisticsFilledData.updateAmountOfFilledPointsFromNotepad(monthsFrom2025[i], 2025);

            System.out.println("Finish: full update - table days_statistics_test -\t" + monthsFrom2025[i] + " - - " + 2025);
        }

        System.out.println("Refrashed 2025: " + "\t" + (TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start)));

        for (int i = 0; i < monthsFrom2026.length; i++) {
            System.out.println("Start: full update - table days_statistics_test -\t" + monthsFrom2026[i] + " - - " + 2026);

            GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad(monthsFrom2026[i], 2026);
            UpdateDaysStatisticsFilledData.updateWholeMonthMacroSum(monthsFrom2026[i], 2026);
            UpdateDaysStatisticsFilledData.updateAmountOfFilledPointsFromNotepad(monthsFrom2026[i], 2026);

            System.out.println("Finish: full update - table days_statistics_test -\t" + monthsFrom2026[i] + " - - " + 2026);
        }
    }

    public static void runFullUpdateForOneMonthInDayStatistics(int numberOfDay, int numberOfMonth, int year) {
        String nameOfMonth = MyDate.getNameOfMonthFromNumber(numberOfMonth);

        System.out.println("Start: full update - table days_statistics_test -\t" + nameOfMonth + " - - " + year);

        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad(numberOfDay, nameOfMonth, year);
        try {
            UpdateDaysStatisticsFilledData.updateWholeMonthMacroSum(numberOfDay, nameOfMonth, year);
            UpdateDaysStatisticsFilledData.updateAmountOfFilledPointsFromNotepad(numberOfDay, nameOfMonth, year);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Finish: full update - table days_statistics_test -\t" + nameOfMonth + " - - " + year);
    }

}
