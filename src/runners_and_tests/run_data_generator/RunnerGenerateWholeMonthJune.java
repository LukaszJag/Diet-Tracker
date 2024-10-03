package runners_and_tests.run_data_generator;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthJune {
    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("June", 2024);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("June", 2024);
    }
}
