package runners.run_data_generator.year_2026;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthMay2026 {
    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("May", 2026);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("May", 2026);
    }
}
