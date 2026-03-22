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
        System.out.println("-1");
        Charts charts = new Charts();
        System.out.println("0");
        Charts.DailyMacroChart dailyMacroChart = charts.new DailyMacroChart();
        System.out.println("1");
        dailyMacroChart.displayBarChart();
        System.out.println("2");
    }
}
