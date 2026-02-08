package tools.charts_tools;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import tools.calendar_tools.MyDate;

import javax.swing.*;

public class Charts {
    //<editor-fold desc="Global variables">
    String[] daysNumbers;
    String chartName;

    float[] valuesKcal;

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

    int yearToDisplay = MyDate.getCurrentYear();
    int monthIntervalForChart = 0;

    ChartPanel chartPanel;
    JFreeChart jFreeChart;
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
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


    class ChartForMonthsKcalCompare {

        public void prepareDataForChart() {
        }

        public void getMonthAverageMacro(){

        }
    }


}
