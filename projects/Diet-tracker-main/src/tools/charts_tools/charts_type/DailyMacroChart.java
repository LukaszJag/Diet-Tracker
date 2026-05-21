package tools.charts_tools.charts_type;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import tools.calendar_tools.MyDate;
import tools.debug_tools.Debug;
import tools.products_tools.Macro;
import tools.products_tools.Product;
import tools.sql_tools.general.RowInTable;
import tools.sql_tools.general.Table;
import tools.sql_tools.general.statements.QueryMaker;
import tools.sql_tools.general.statements.Select;

import java.util.ArrayList;

import static java.lang.System.exit;

public class DailyMacroChart {
    //<editor-fold desc="Global variables">
    //<editor-fold desc="String variables">
    String chartName;
    String dateInSQLFormat;
    //</editor-fold>

    ChartPanel chartPanel;
    JFreeChart jFreeChart;

    ArrayList<Product> productArrayList = new ArrayList<>();

    //<editor-fold desc="Colors">

    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Constructors">
    public DailyMacroChart(String chartName, String dateInSQLFormat) {
        this.chartName = chartName;
        this.dateInSQLFormat = dateInSQLFormat;
    }
    //</editor-fold>

    //<editor-fold desc="KeyListener">
    //</editor-fold>

    public void getDataForSelectedDay() {
        if (!MyDate.checkSQLFormat(dateInSQLFormat)) {
            Debug.printRedSystemPrintln("Data in SQL format is incorrect");
            exit(-1);
        }

        Table fullTableData;
        fullTableData = Select.getDataToTable(QueryMaker.selectCalendarQueryWithDay(dateInSQLFormat));
        fullTableData.printTable();

        for (int i = 0; i < fullTableData.getAmountOfRowsInTable(); i++) {
            RowInTable rowInTable = fullTableData.getRowInTable(i);

            // Parse the string values into floats for the Macro object
            float kcal = Float.parseFloat(rowInTable.getValue("kcal"));
            float protein = Float.parseFloat(rowInTable.getValue("protein"));
            float carbs = Float.parseFloat(rowInTable.getValue("carbs"));
            float fat = Float.parseFloat(rowInTable.getValue("fat"));

            // Assuming your Macro class constructor takes (kcal, protein, carbs, fat)
            Macro productMacro = new Macro(kcal, protein, carbs, fat);

            Product product = new Product(
                    rowInTable.getValue("product_name"), // name
                    "", // brand (Missing in map -> default to empty string or null)
                    100.0f, // productMeasureOfProductWeightToCalculateMacro (Defaults to 100)
                    productMacro, // product_macro
                    0.0f, // weight_of_pack (Missing in map -> default to 0.0f)
                    rowInTable.getValue("comment_optional") // commentOptional
            );

            productArrayList.add(product);
        }
        for (int i = 0; i < productArrayList.size(); i++) {
            System.out.print("\n\n");
            Debug.printBlueSystemPrintln("Product number: " + i);
            System.out.println(productArrayList.get(i).toString());
        }
    }

}
