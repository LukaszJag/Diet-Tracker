package runners.run_data_generator.year_2026;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthMarch2026 {
    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("March", 2026);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("March", 2026);
    }
}
