package runners.run_data_generator.year_2026;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthNovember2026 {
    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("November", 2026);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("November", 2026);
    }
}
