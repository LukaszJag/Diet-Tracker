package tools.charts_tools.charts_type;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import tools.debug_tools.Debug;
import tools.sql_tools.general.Table;
import tools.sql_tools.general.statements.Select;

public class DailyMacroChart {
    //<editor-fold desc="Global variables">
    //<editor-fold desc="String variables">
    String chartName;
    String dateInSQLFormat;
    //</editor-fold>

    ChartPanel chartPanel;
    JFreeChart jFreeChart;

    //<editor-fold desc="Colors">

    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="KeyListener">
    //</editor-fold>


    public DailyMacroChart(String chartName, String dateInSQLFormat) {
        this.chartName = chartName;
        this.dateInSQLFormat = dateInSQLFormat;
    }

    private void getDataForSelectedDay() {
        if (dateInSQLFormat == null) {
            Debug.printRedSystemPrintln("Data in SQL format is null");
        } else if (dateInSQLFormat.isEmpty()) {
            Debug.printRedSystemPrintln("Data in SQL format is empty");
        } else if (dateInSQLFormat.equals("")) {
            Debug.printRedSystemPrintln("Data in SQL format is \"\"");
        }

        Table fullTableData;
        fullTableData = Select.getDataToTable(dateInSQLFormat);
        fullTableData.printTable();

    }
}
