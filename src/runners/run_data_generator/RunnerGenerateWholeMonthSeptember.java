package runners.run_data_generator;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthSeptember {
    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("September", 2024);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("September", 2024);
    }
}