package runners_and_tests.run_data_generator.year_2025;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthJune2025 {
    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("June", 2025);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("June", 2025);
    }
}
