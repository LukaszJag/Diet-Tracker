package runners_and_tests.run_data_generator;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthApril2025 {
    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("April", 2025);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("April", 2025);
    }
}
