package tools.charts_tools;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class DisplayChart {
    public static void showChart(JFreeChart chart){
        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setSize(1000, 700);
        frame.setContentPane(chartPanel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static JFreeChart createChartPanel(String chartName, String categoryAxisLabel, String valueAxisLabel, float[] values, String rowKey, String[] columnsKeys){
        if (values.length != columnsKeys.length){
            System.out.println("Invalid data arrays have different sizes");
            return null;
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < values.length; i++) {
            dataset.addValue(values[i], rowKey, columnsKeys[i]);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                chartName,
                categoryAxisLabel,
                valueAxisLabel,
                dataset);

        return chart;
    }
}
