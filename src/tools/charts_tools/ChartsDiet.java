package tools.charts_tools;

import configuration.Config;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import tools.calendar_tools.MyDate;
import tools.debug_tools.Debug;
import tools.sql_tools.days_statistics.SelectFromDaysStatistics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChartsDiet {
    //<editor-fold desc="Global variables">
    String[] daysNumbers;
    String chartName;

    float[] valuesKcal;

    int monthToDisplay = MyDate.getCurrentMonthNumber();

    int yearToDisplay = MyDate.getCurrentYear();
    int monthIntervalForChart = 0;

    ChartPanel chartPanel;
    JFreeChart jFreeChart;
    //</editor-fold>

    //<editor-fold desc="Swing components">
    JFrame chartFrame;
    JMenuBar menuBar;
    JMenu menu;

    JLabel dateLabel = new JLabel(MyDate.getCurrentMonthNumber() + "-" + MyDate.getCurrentMonthNumber());

    JButton previousMonthButton = new JButton("Previous");
    JButton currentMonthButton = new JButton("Current");
    JButton nextMonthButton = new JButton("Next");
    //</editor-fold>

    //<editor-fold desc="Colors">
    float greenKcalLimit = Config.BMRActual.getKcal();
    float yellowKcalLimit = 4500;
    float redKcalLimit = 5500;

    Color greenMarkerColor = new Color(43, 191, 36);
    Color yellowMarkerColor = new Color(255, 229, 46);
    Color redMarkerColor = new Color(214, 23, 23);
    Color blackAverageMarkerColor = new Color(0, 0, 0);
    //</editor-fold>


    //<editor-fold desc="Charts - methods">
    public void prepareDataForCharts() {
        chartName = MyDate.getNameOfMonthFromNumber(monthToDisplay)+ " stats";
        
        dateLabel = new JLabel("" + monthToDisplay + "-" + yearToDisplay);

        valuesKcal = new float[MyDate.getAmountOfDaysInMonth(monthToDisplay)];

        daysNumbers = MyDate.getAllDaysInMonthAndYearSQLFriendlyFormat(monthToDisplay, yearToDisplay);

        for (int i = 0; i < valuesKcal.length; i++) {
            valuesKcal[i] = SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(daysNumbers[i]).getKcal();
        }
    }

    public void showMonthChart() {
        prepareDataForCharts();

        chartName = monthToDisplay + "-" + yearToDisplay;
        jFreeChart = DisplayChart.createChartPanel(chartName, "Days", "Kcal",
                valuesKcal, "Kcal", daysNumbers);
        DisplayChart.showChart(jFreeChart);
    }

    public void prepareMonthBarChart() {
        //<editor-fold desc="Kcal Limits - float">
        float greenKcalLimit = Config.BMRActual.getKcal();
        float yellowKcalLimit = 4500;
        float redKcalLimit = 5500;
        //</editor-fold>

        //<editor-fold desc="Setup chart fields">

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String[] dayInSQLFormat = new String[MyDate.getAmountOfDaysInMonth(monthToDisplay)];
        for (int i = 0; i < dayInSQLFormat.length; i++) {
            if ((i + 1) >= 10) {
                dayInSQLFormat[i] = yearToDisplay + "-" + MyDate.getNameOfMonthFromNumberSQLFormat(monthToDisplay) + "-" + (i + 1);
            } else {
                dayInSQLFormat[i] = yearToDisplay + "-" + MyDate.getNameOfMonthFromNumberSQLFormat(monthToDisplay) + "-" + "0" + (i + 1);
            }
        }

        for (int i = 0; i < daysNumbers.length; i++) {

            dataset.addValue(valuesKcal[i], ("" + (i + 1) + "-" + SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(dayInSQLFormat[i]).getKcal()), "kcal");
        }

        jFreeChart = ChartFactory.createBarChart(chartName, "Kcal", "Kcal",
                dataset);

        CategoryPlot categoryPlot = jFreeChart.getCategoryPlot();
        //</editor-fold>

        //<editor-fold desc="Set float value and colors - Marker">
        float greenMarker = Config.BMRActual.getKcal();
        float yellowMarker = 4500;
        float redMarker = 5500;
        float blackAverageMarker = 0;

        int count = 0;
        float sum = 0;
        for (int i = 0; i < valuesKcal.length; i++) {
            if (valuesKcal[i] > 0) {
                sum += valuesKcal[i];
                count++;
            }
        }
        blackAverageMarker = (sum / count);
        //</editor-fold>

        //<editor-fold desc="Add color of bars depend on value">
        BarRenderer renderer = new BarRenderer() {

            @Override
            public Paint getItemPaint(int row, int column) {
                CategoryDataset dataset = getPlot().getDataset();
                Number value = dataset.getValue(row, column);

                if (value == null) {
                    return Color.GRAY;
                }

                double v = value.doubleValue();

                if (v < greenKcalLimit) {
                    return Color.GREEN;
                } else if (v >= greenKcalLimit && v < yellowKcalLimit) {
                    return Color.YELLOW;
                } else if (v >= yellowKcalLimit && v < redKcalLimit) {
                    return Color.ORANGE;
                } else {
                    return Color.RED;
                }
            }
        };
        // Optional: flat colors (no gradient)
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());
        renderer.setDrawBarOutline(false);

        categoryPlot.setRenderer(renderer);
        //</editor-fold>

        //<editor-fold desc="Add marker to chart">

        //<editor-fold desc="greenMarkerValueMarker">
        ValueMarker greenMarkerValueMarker = new ValueMarker(greenMarker);
        greenMarkerValueMarker.setPaint(greenMarkerColor);
        greenMarkerValueMarker.setStroke(new
                BasicStroke(2.0f));

        greenMarkerValueMarker.setLabel(String.valueOf(Config.BMRActual.getKcal()));
        greenMarkerValueMarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
        greenMarkerValueMarker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
        //</editor-fold>

        //<editor-fold desc="yellowMarkerValueMarker">
        ValueMarker yellowMarkerValueMarker = new ValueMarker(yellowMarker);
        yellowMarkerValueMarker.setPaint(yellowMarkerColor);
        yellowMarkerValueMarker.setStroke(new BasicStroke(2.0f));

        yellowMarkerValueMarker.setLabel(String.valueOf(yellowMarker));
        yellowMarkerValueMarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
        yellowMarkerValueMarker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
        //</editor-fold>

        //<editor-fold desc="blackAverageMarkerValueMarker">
        ValueMarker blackAverageMarkerValueMarker = new ValueMarker(blackAverageMarker);
        blackAverageMarkerValueMarker.setPaint(blackAverageMarkerColor);
        blackAverageMarkerValueMarker.setStroke(new BasicStroke(2.0f));

        blackAverageMarkerValueMarker.setLabel(String.valueOf(blackAverageMarker));
        blackAverageMarkerValueMarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
        blackAverageMarkerValueMarker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
        //</editor-fold>

        //<editor-fold desc="redMarkerValueMarker">
        ValueMarker redMarkerValueMarker = new ValueMarker(redMarker);
        redMarkerValueMarker.setPaint(redMarkerColor);
        redMarkerValueMarker.setStroke(new

                BasicStroke(2.0f));

        redMarkerValueMarker.setLabel(String.valueOf(redMarker));
        redMarkerValueMarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
        redMarkerValueMarker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);


        categoryPlot.addRangeMarker(greenMarkerValueMarker);
        categoryPlot.addRangeMarker(yellowMarkerValueMarker);
        // categoryPlot.addRangeMarker(orangeMarkerValueMarker);
        categoryPlot.addRangeMarker(redMarkerValueMarker);
        categoryPlot.addRangeMarker(blackAverageMarkerValueMarker);
        //</editor-fold>

        //</editor-fold>

        //<editor-fold desc="Set Range of displayed Y-Axis">
        NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
        rangeAxis.setRange(0, 11000);
        //</editor-fold>

    }

    public void clearAllData() {
        SwingUtilities.updateComponentTreeUI(chartFrame);

        chartFrame.remove(chartPanel);
        chartFrame.remove(dateLabel);

        menuBar.removeAll();

        chartFrame.invalidate();
        chartFrame.validate();
        chartFrame.repaint();
    }

    public void displayCharBar() {
        prepareDataForCharts();
        prepareMonthBarChart();
        menuBar = new JMenuBar();
        menu = new JMenu("Month");
        chartFrame = new JFrame();

        chartPanel = new ChartPanel(jFreeChart);

        menuBar.add(previousMonthButton);
        menuBar.add(nextMonthButton);
        menuBar.add(currentMonthButton);
        menuBar.add(dateLabel);

        chartFrame.setJMenuBar(menuBar);

        chartFrame.setFocusable(true);
        chartFrame.add(chartPanel);

        chartFrame.addKeyListener(new TestFrameKeyListener());

        chartFrame.setSize(new Dimension(1000, 900));
        chartFrame.setVisible(true);
        chartFrame.setResizable(false);
        chartFrame.setLocationRelativeTo(null);
    }

    public void updateJFrameForCharBar() {
        clearAllData();
        prepareDataForCharts();
        prepareMonthBarChart();

        menuBar.add(previousMonthButton);
        menuBar.add(nextMonthButton);
        menuBar.add(currentMonthButton);
        menuBar.add(dateLabel);

        chartFrame.setJMenuBar(menuBar);

        chartPanel = new ChartPanel(jFreeChart);

        chartFrame.setFocusable(true);
        chartFrame.add(chartPanel);

        chartFrame.setSize(new Dimension(900, 700));
        chartFrame.setVisible(true);
        chartFrame.setResizable(false);
        chartFrame.setLocationRelativeTo(null);

        chartFrame.revalidate();
        chartFrame.repaint();
    }


    private class TestFrameKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                String date = MyDate.getNextDateFromMontAndYearSQLFriendlyFormat(getMonthToDisplay(), getYearToDisplay());


                setYearToDisplay(MyDate.getYearFromSQLFriendlyFormatDateToInt(date));
                setMonthToDisplay(MyDate.getMonthFromSQLFriendlyFormatDateToInt(date));

                updateJFrameForCharBar();
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                String date = MyDate.getPreviousDateFromMontAndYearSQLFriendlyFormat(getMonthToDisplay(), getYearToDisplay());

                setYearToDisplay(MyDate.getYearFromSQLFriendlyFormatDateToInt(date));
                setMonthToDisplay(MyDate.getMonthFromSQLFriendlyFormatDateToInt(date));

                updateJFrameForCharBar();
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                System.out.println("Down pressed");
            }

            if (e.getKeyCode() == KeyEvent.VK_H) {
                System.out.println("H pressed");
            }


        }
    }

    public int getMonthToDisplay() {
        return monthToDisplay;
    }

    public void setMonthToDisplay(int monthToDisplay) {
        this.monthToDisplay = monthToDisplay;
    }

    public int getYearToDisplay() {
        return yearToDisplay;
    }

    public void setYearToDisplay(int yearToDisplay) {
        this.yearToDisplay = yearToDisplay;
    }

    //</editor-fold>
}
