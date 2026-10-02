package runners.run_data_generator.year_2026;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthOctober2026 {

    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("October", 2026);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("October ", 2026);
    }
}
