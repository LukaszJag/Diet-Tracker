package runners_and_tests.run_data_generator;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthMay {
    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("May", 2024);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("May", 2024);
    }
}
