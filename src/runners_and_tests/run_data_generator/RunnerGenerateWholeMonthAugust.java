package runners_and_tests.run_data_generator;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthAugust {
    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("August", 2024);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("August", 2024);
    }
}
