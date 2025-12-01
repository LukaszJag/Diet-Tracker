package runners_and_tests.run_data_generator.year_2025;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthJuly2025 {

    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("July", 2025);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("July", 2025);
    }
}
