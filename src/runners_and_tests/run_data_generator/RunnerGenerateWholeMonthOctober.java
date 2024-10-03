package runners_and_tests.run_data_generator;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthOctober {
    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("October" , 2024);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("October", 2024);
    }
}
