package tools.charts_tools.charts_type;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import tools.sql_tools.days_statistics.SelectFromDaysStatistics;

import javax.swing.*;
import java.awt.*;

public class OldChartForMonthsKcalCompare {

    //<editor-fold desc="String - components">
    String chartName;
    String[] monthsKcal;
    String[] monthsToDisplayInSQLFriendlyFormat = {
            "01-2025",
            "02-2025",
            "03-2025",
            "04-2025",
            "05-2025",
            "06-2025",
            "07-2025",
            "08-2025",
            "09-2025",
            "10-2025",
            "11-2025",
            "12-2025",

            "01-2026",
            "02-2026",
            "03-2026"
    };
    //</editor-fold>


    //<editor-fold desc="JFree - components">
    ChartPanel chartPanel;
    JFreeChart jFreeChart;
    DefaultCategoryDataset dataset;
    //</editor-fold>

    //<editor-fold desc="Swing components">
    JFrame chartFrame;
    //</editor-fold>

    public void displayBarChart() {
        System.out.println("displayBarChart - ChartForMonthsKcalCompare");
        prepareDataForBarChart();
        chartPanel = new ChartPanel(jFreeChart);
        chartFrame.add(chartPanel);
        chartFrame.setVisible(true);
    }

    public void prepareDataForBarChart() {
        prepareSwingComponents();
        prepareDataForMonthAverageMacro();
        prepareJFreeChart();
        prepareDataForChart();
    }

    public void prepareSwingComponents() {
        chartFrame = new JFrame("ChartForMonthsKcalCompare");
        chartFrame.setSize(new Dimension(1000, 800));
        chartFrame.setResizable(true);
        chartFrame.setLocationRelativeTo(null);
        chartFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void prepareJFreeChart() {
        dataset = new DefaultCategoryDataset();
        for (int i = 0; i < monthsKcal.length; i++) {

            dataset.addValue(Double.valueOf(monthsKcal[i]), ("" + monthsToDisplayInSQLFriendlyFormat[i]), "kcal");
        }

        jFreeChart = ChartFactory.createBarChart(chartName, "kcal", "Kcal",
                dataset);

    }

    public void prepareDataForChart() {


    }

    public void prepareDataForMonthAverageMacro() {
        monthsKcal = new String[monthsToDisplayInSQLFriendlyFormat.length];

        int year, month;
        for (int i = 0; i < monthsKcal.length; i++) {
            year = Integer.valueOf(monthsToDisplayInSQLFriendlyFormat[i].substring(3));
            month = Integer.valueOf(monthsToDisplayInSQLFriendlyFormat[i].substring(0, 2));

            monthsKcal[i] = String.valueOf(SelectFromDaysStatistics.getAverageMacroForMonth(year, month).getKcal());
        }
    }
}
