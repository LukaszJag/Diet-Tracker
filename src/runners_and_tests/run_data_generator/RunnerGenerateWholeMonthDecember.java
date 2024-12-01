package runners_and_tests.run_data_generator;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthDecember {
    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("December" , 2024);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("December", 2024);
    }
}
