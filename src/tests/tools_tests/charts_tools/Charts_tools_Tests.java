package tests.tools_tests.charts_tools;

import org.junit.jupiter.api.Test;
import tools.charts_tools.Charts;
import tools.charts_tools.ChartsDiet;

public class Charts_tools_Tests {
    public static void main(String[] args) {
        runChartForMonthsKcalCompare();
    }

    @Test
    public void runConstructorToDisplayChartBar() {
        ChartsDiet chartsDiet = new ChartsDiet();
        System.out.println("I'm out");
    }

    @Test
    public void runChartsDiet(){
        new ChartsDiet().displayChartBar();
    }

    public static void runChartForMonthsKcalCompare(){
        Charts charts = new Charts();
        Charts.ChartForMonthsKcalCompare chartForMonthsKcalCompare = charts.new ChartForMonthsKcalCompare();

        chartForMonthsKcalCompare.displayBarChart();
    }

    @Test
    public void displayMacroChartForDay(){
        Charts charts = new Charts();
        Charts.DailyMacroChart dailyMacroChart = charts.new DailyMacroChart();

    }

}
