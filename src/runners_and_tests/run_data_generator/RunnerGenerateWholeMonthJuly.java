package runners_and_tests.run_data_generator;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthJuly {
    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("July", 2024);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("July", 2024);
    }
}
