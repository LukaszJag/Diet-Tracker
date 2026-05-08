package runners.run_data_generator.year_2026;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthApril2026 {
    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("April", 2026);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("April", 2026);
    }
}
