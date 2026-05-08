package runners.run_data_generator;

import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;

public class RunnerGenerateWholeMonthNovember {
    public static void main(String[] args) {
        GenerateSLQTableForDaysStatistics.generateWholeMonth("November" , 2024);
        GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("November", 2024);
    }
}
