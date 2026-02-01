package runners_and_tests.run_data_generator.year_2026;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthFebruary2026 {
    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("February", 2026);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("February", 2026);
    }
}
