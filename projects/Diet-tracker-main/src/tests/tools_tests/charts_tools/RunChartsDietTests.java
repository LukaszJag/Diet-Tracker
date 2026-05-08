package tests.tools_tests.charts_tools;

import tools.charts_tools.Charts;
import tools.charts_tools.ChartsDiet;

public class RunChartsDietTests {
    public static void main(String[] args) {
        displayMacroChartForDay();
    }

    public static void runChartsDiet(){
        new ChartsDiet().displayChartBar();
    }

    public static void displayMacroChartForDay(){
        Charts charts = new Charts();
        Charts.DailyMacroChart dailyMacroChart = charts.new DailyMacroChart();
        dailyMacroChart.displayBarChart();
    }
}
