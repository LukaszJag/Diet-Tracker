package tools.charts_tools.charts_type;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import tools.calendar_tools.MyDate;
import tools.debug_tools.Debug;
import tools.products_tools.Macro;
import tools.products_tools.Product;
import tools.products_tools.ProductConsumed;
import tools.sql_tools.general.RowInTable;
import tools.sql_tools.general.Table;
import tools.sql_tools.general.statements.QueryMaker;
import tools.sql_tools.general.statements.Select;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.System.exit;

public class DailyMacroChart {
    //<editor-fold desc="Global variables">
    //<editor-fold desc="String variables">
    String chartName;
    String dateInSQLFormat;
    //</editor-fold>

    //<editor-fold desc="Jfree charts - components">
    JFreeChart jFreeBarChart;
    JFreeChart combinedChart;
    JFreeChart jFreeLineChart;

    CategoryPlot categoryPlot;

    ChartPanel panelBarChart;
    ChartPanel panelLineChart;

    DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
    DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();
    //</editor-fold>

    ArrayList<ProductConsumed> productFromSQLDatabase = new ArrayList<>();
    //<editor-fold desc="Colors">

    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Constructors">
    public DailyMacroChart(String chartName, String dateInSQLFormat) {
        this.chartName = chartName;
        this.dateInSQLFormat = dateInSQLFormat;
        categoryPlot = new CategoryPlot();
        getDataForSelectedDay();
        createBarChart();
        createLineChart();
        combineTwoCharts();

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
            ProductConsumed productConsumed;

            RowInTable rowInTable = fullTableData.getRowInTable(i);

            // Parse the string values into floats for the Macro object
            float kcal = Float.parseFloat(rowInTable.getValue("kcal"));
            float protein = Float.parseFloat(rowInTable.getValue("protein"));
            float carbs = Float.parseFloat(rowInTable.getValue("carbs"));
            float fat = Float.parseFloat(rowInTable.getValue("fat"));

            // Assuming your Macro class constructor takes (kcal, protein, carbs, fat)
            Macro productMacro = new Macro(kcal, protein, carbs, fat);


            float kcal_consume = Float.parseFloat(rowInTable.getValue("kcal_consume"));
            float protein_consume = Float.parseFloat(rowInTable.getValue("protein_consume"));
            float carbs_consume = Float.parseFloat(rowInTable.getValue("carbs_consume"));
            float fat_consume = Float.parseFloat(rowInTable.getValue("fat_consume"));

            Macro consumedMacro = new Macro(kcal_consume,
                    carbs_consume,
                    fat_consume,
                    protein_consume);

            Product product = new Product(
                    rowInTable.getValue("product_name"), // name
                    "", // brand (Missing in map -> default to empty string or null)
                    100.0f, // productMeasureOfProductWeightToCalculateMacro (Defaults to 100)
                    productMacro, // product_macro
                    0.0f, // weight_of_pack (Missing in map -> default to 0.0f)
                    rowInTable.getValue("comment_optional") // commentOptional
            );

            float amountOfProduct = Float.parseFloat(rowInTable.getValue("amount_of_product"));

            productFromSQLDatabase.add(new ProductConsumed(product, amountOfProduct,consumedMacro));

        }
    }

    public void createBarChart(){

        String[] labelsForColumns = new String[productFromSQLDatabase.size()];
        for (int i = 0; i <productFromSQLDatabase.size(); i++) {
            labelsForColumns[i] = productFromSQLDatabase.get(i).getProduct().getProductName();
        }

        for (int i = 0; i < labelsForColumns.length; i++) {

            barDataset.addValue(productFromSQLDatabase.get(i).getConsumedMacro().getKcal(), ("" + (i + 1) + "-" + productFromSQLDatabase.get(i).getProduct().getProductName()), "kcal");
        }

        jFreeBarChart = ChartFactory.createBarChart(chartName, "Kcal", "Kcal",
                barDataset);

        panelBarChart = new ChartPanel(jFreeBarChart);
    }

    public void createLineChart(){

        float kcalSum = 0;

        String valueAxisLabel = "kcal";
        String categoryAxisLabel = "products";

        for (int i = 0; i < productFromSQLDatabase.size(); i++) {
            kcalSum += productFromSQLDatabase.get(i).getConsumedMacro().getKcal();
            lineDataset.addValue(kcalSum, "Kcal", productFromSQLDatabase.get(i).getProduct().getProductName());
        }

        jFreeLineChart = ChartFactory.createLineChart(
                chartName,
                categoryAxisLabel,
                valueAxisLabel,
                lineDataset);

        panelBarChart = new ChartPanel(jFreeLineChart);
    }

    public void combineTwoCharts(){

        CategoryPlot plot = jFreeBarChart.getCategoryPlot();

        plot.setDataset(1, lineDataset);

        LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
        lineRenderer.setSeriesPaint(0, Color.RED); // Make the line red so it stands out
        lineRenderer.setSeriesStroke(0, new BasicStroke(3.0f)); // Make the line thicker

        plot.setRenderer(1, lineRenderer);

        // (Optional but good practice) Map the line dataset to the first Y-axis
        plot.mapDatasetToRangeAxis(1, 0);

        combinedChart = new JFreeChart(
                chartName, // Your chart title
                JFreeChart.DEFAULT_TITLE_FONT,
                plot,
                true // Show legend
        );
    }
    //<editor-fold desc="Getters and Setters">
    public JFreeChart getjFreeBarChart() {
        return jFreeBarChart;
    }

    public void setjFreeBarChart(JFreeChart jFreeBarChart) {
        this.jFreeBarChart = jFreeBarChart;
    }

    public ChartPanel getPanelBarChart() {
        return panelBarChart;
    }

    public void setPanelBarChart(ChartPanel panelBarChart) {
        this.panelBarChart = panelBarChart;
    }

    public ChartPanel getPanelLineChart() {
        return panelLineChart;
    }

    public void setPanelLineChart(ChartPanel panelLineChart) {
        this.panelLineChart = panelLineChart;
    }

    public JFreeChart getCombinedChart() {
        return combinedChart;
    }

    public void setCombinedChart(JFreeChart combinedChart) {
        this.combinedChart = combinedChart;
    }
    //</editor-fold>


}
