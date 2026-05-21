package tools.charts_tools;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class DisplayChart {

    ChartPanel chartPanel;

    //<editor-fold desc="Swing components">
    //<editor-fold desc="Swing components  - size">
    Dimension frameSize = new  Dimension(1000, 700);
    Dimension westPanelSize;
    Dimension mainPanelSize;
    Dimension northPanelSize;
    Dimension eastPanelSize;
    //</editor-fold>
    //<editor-fold desc="Frames">
    JFrame frame = new JFrame();
    //</editor-fold>
    //<editor-fold desc="Buttons">
    JButton acceptButton;
    JButton backToMainWindowButton;
    JButton exitProgramProductWindowButton = new JButton();
    JButton goToNextDayButton;
    JButton goToPreviousDayButton;
    //</editor-fold>
    //<editor-fold desc="Panels">
    JPanel chartDietMainPanel = new JPanel();
    JPanel chartDietPanelNorth = new JPanel();
    JPanel chartDietPanelWest = new JPanel();
    JPanel chartDietPanelEast = new JPanel();
    JPanel chartDietPanelSouth = new JPanel();
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="showChart methods">
    public  void showChart(JFreeChart jFreeChart){
        setupAndShowFrame(jFreeChart);
        frame.setSize(1000, 700);
    }

    public  void showChart(JFreeChart jFreeChart, int width, int height){
        setupAndShowFrame(jFreeChart);
        frame.setSize(width, height);
    }
    //</editor-fold>

    public void setupAndShowFrame(JFreeChart jFreeChart){
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        frame.setContentPane(chartPanel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static JFreeChart createAreaChartPanel(String chartName, String categoryAxisLabel, String valueAxisLabel, float[] values, String rowKey, String[] columnsKeys){
        if (values.length != columnsKeys.length){
            System.out.println("Invalid data arrays have different sizes");
            return null;
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < values.length; i++) {
            dataset.addValue(values[i], rowKey, columnsKeys[i]);
        }

        JFreeChart chart = ChartFactory.createAreaChart(
                chartName,
                categoryAxisLabel,
                valueAxisLabel,
                dataset);

        // BMR value line
        ValueMarker marker = new ValueMarker(3531);  // position is the value on the axis
        marker.setPaint(Color.GREEN);

        //marker.setLabel("here"); // see JavaDoc for labels, colors, strokes


        CategoryPlot plot = chart.getCategoryPlot();
        plot.addRangeMarker(marker);
        return chart;
    }

    public static JFreeChart createLineChartPanel(String chartName, String categoryAxisLabel, String valueAxisLabel, float[] values, String rowKey, String[] columnsKeys){
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

    //<editor-fold desc="Getters and Setters">
    public Dimension getWestPanelSize() {
        return westPanelSize;
    }

    public void setWestPanelSize(Dimension westPanelSize) {
        this.westPanelSize = westPanelSize;
    }

    public Dimension getMainPanelSize() {
        return mainPanelSize;
    }

    public void setMainPanelSize(Dimension mainPanelSize) {
        this.mainPanelSize = mainPanelSize;
    }

    public Dimension getNorthPanelSize() {
        return northPanelSize;
    }

    public void setNorthPanelSize(Dimension northPanelSize) {
        this.northPanelSize = northPanelSize;
    }

    public Dimension getEastPanelSize() {
        return eastPanelSize;
    }

    public void setEastPanelSize(Dimension eastPanelSize) {
        this.eastPanelSize = eastPanelSize;
    }

    public Dimension getSouthPanelSize() {
        return southPanelSize;
    }

    public void setSouthPanelSize(Dimension southPanelSize) {
        this.southPanelSize = southPanelSize;
    }

    Dimension southPanelSize;
    public Dimension getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(Dimension frameSize) {
        this.frameSize = frameSize;
    }
    //</editor-fold>
}
