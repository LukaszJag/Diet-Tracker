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
import tools.products_tools.Product;
import tools.sql_tools.days_statistics.SelectFromDaysStatistics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Charts {
    public static void main(String[] args) {
        Charts obj = new Charts();
        Charts.ChartForMonthsKcalCompare obj2 = obj.new ChartForMonthsKcalCompare();
        obj2.prepareDataForChart();
    }

    //<editor-fold desc="Global - components, variables">

    //<editor-fold desc="String, String[] - variables">
    String[] daysNumbers;
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

    //<editor-fold desc="Numeric values - variables">
    float[] valuesKcal;
    int yearToDisplay = MyDate.getCurrentYear();
    int monthIntervalForChart = 0;
    //</editor-fold>

    //<editor-fold desc="JFree - components">
    CategoryPlot categoryPlot;
    ChartPanel chartPanel;
    JFreeChart jFreeChart;
    DefaultCategoryDataset dataset;
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

    //</editor-fold>

    public class ChartForMonthsKcalCompare {

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

    public class ChartsDiet{
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

        //<editor-fold desc="Prepare, clear - data">
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
        public void prepareDataForCharts() {
            chartName = MyDate.getNameOfMonthFromNumber(monthToDisplay)+ " stats";

            dateLabel = new JLabel("" + monthToDisplay + "-" + yearToDisplay);

            valuesKcal = new float[MyDate.getAmountOfDaysInMonth(monthToDisplay)];

            daysNumbers = MyDate.getAllDaysInMonthAndYearSQLFriendlyFormat(monthToDisplay, yearToDisplay);

            for (int i = 0; i < valuesKcal.length; i++) {
                valuesKcal[i] = SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(daysNumbers[i]).getKcal();
            }
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
        //</editor-fold>

        //<editor-fold desc="display - BarChart, LineChart">
        public void displayChartBar() {
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
            chartFrame.setResizable(true);
            chartFrame.setLocationRelativeTo(null);
        }
        public void displayLineChart() {
            prepareDataForCharts();

            chartName = monthToDisplay + "-" + yearToDisplay;
            jFreeChart = DisplayChart.createChartPanel(chartName, "Days", "Kcal",
                    valuesKcal, "Kcal", daysNumbers);
            DisplayChart.showChart(jFreeChart);
        }
        //</editor-fold>

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

            chartFrame.setSize(new Dimension(1000, 900));
            chartFrame.setVisible(true);
            chartFrame.setResizable(true);
            chartFrame.setLocationRelativeTo(null);

            chartFrame.revalidate();
            chartFrame.repaint();
        }

        //<editor-fold desc="Getters, Setters">
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

        //</editor-fold>

        //<editor-fold desc="KeyListener">
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
        //</editor-fold>
    }

    public class DailyMacroChart{

        ArrayList<Product.ProductInCalendar> namesOfProducts;
        String dayDateInSQLFriendlyFormat = "0000-00-00";



        public void getDaysProductsData(){
            String SQLQuery = "SELECT * FROM calendar WHERE day_date=\"2026-02-27\";";

        }

    }
}
