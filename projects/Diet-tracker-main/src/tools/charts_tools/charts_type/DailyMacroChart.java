package tools.charts_tools.charts_type;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
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

    JFreeChart jFreeGeneralChart;
    JFreeChart jFreeBarChart;
    JFreeChart jFreeLineChart;
    CategoryPlot categoryPlot;

    ChartPanel panelBarChart;
    ChartPanel panelLineChart;

    XYPlot plot;

    DefaultCategoryDataset datasetBarChart = new DefaultCategoryDataset();

    ArrayList<TMPProduct> productFromSQLDatabase = new ArrayList<>();
    //<editor-fold desc="Colors">

    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Constructors">
    public DailyMacroChart(String chartName, String dateInSQLFormat) {
        this.chartName = chartName;
        this.dateInSQLFormat = dateInSQLFormat;
        categoryPlot = new CategoryPlot();
        getDataForSelectedDay();
        prepareBarChart();
        createLineChartPanel();

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
            TMPProduct tmpProduct;

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

            productFromSQLDatabase.add(new TMPProduct(product, amountOfProduct,consumedMacro));

        }
        for (int i = 0; i < productFromSQLDatabase.size(); i++) {
            System.out.print("\n\n");
            Debug.printBlueSystemPrintln("Product number: " + i);
            System.out.println(productFromSQLDatabase.get(i).getProduct().getProductName());
            System.out.println(productFromSQLDatabase.get(i).getConsumedMacro().getShortMacroInformation());
        }
    }

    public void prepareBarChart(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String[] labelsForColumns = new String[productFromSQLDatabase.size()];
        for (int i = 0; i <productFromSQLDatabase.size(); i++) {
            labelsForColumns[i] = productFromSQLDatabase.get(i).product.getProductName();
        }

        for (int i = 0; i < labelsForColumns.length; i++) {

            dataset.addValue(productFromSQLDatabase.get(i).consumedMacro.getKcal(), ("" + (i + 1) + "-" + productFromSQLDatabase.get(i).getProduct().getProductName()), "kcal");
        }

        jFreeBarChart = ChartFactory.createBarChart(chartName, "Kcal", "Kcal",
                dataset);

        panelBarChart = new ChartPanel(jFreeBarChart);
    }

    public void createLineChartPanel(){

        float kcalSum = 0;

        String valueAxisLabel = "kcal";
        String categoryAxisLabel = "products";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < productFromSQLDatabase.size(); i++) {
            kcalSum += productFromSQLDatabase.get(i).getConsumedMacro().getKcal();
            dataset.addValue(kcalSum, "Kcal", productFromSQLDatabase.get(i).getProduct().getProductName());
        }

        jFreeLineChart = ChartFactory.createLineChart(
                chartName,
                categoryAxisLabel,
                valueAxisLabel,
                dataset);

        panelBarChart = new ChartPanel(jFreeLineChart);
    }

    public void combineTwoCharts(){
        XYPlot plot = jFreeGeneralChart.getXYPlot();

        plot.setDataset(0, da);
        plot.setRenderer(0, renderer1);

        plot.setDataset(1, dataset2);
        plot.setRenderer(1, renderer2);
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
    //</editor-fold>

    class TMPProduct{


        Product product = new Product();
        Macro consumedMacro = new Macro();
        float amountOfProduct;

        @Override
        public String toString() {
            return "TMPProduct{" +
                    "productArrayList=" + product +
                    ", consumedMacro=" + consumedMacro +
                    ", amountOfProduct=" + amountOfProduct +
                    '}';
        }

        //<editor-fold desc="Constructors">
        public TMPProduct(){}

        public TMPProduct(Product productArrayList, float amountOfProduct, Macro consumedMacro) {
            this.product = productArrayList;
            this.amountOfProduct = amountOfProduct;
            this.consumedMacro = consumedMacro;
        }
        //</editor-fold>

        //<editor-fold desc="Getters and Setters">
        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public Macro getConsumedMacro() {
            return consumedMacro;
        }

        public void setConsumedMacro(Macro consumedMacro) {
            this.consumedMacro = consumedMacro;
        }

        public float getAmountOfProduct() {
            return amountOfProduct;
        }

        public void setAmountOfProduct(float amountOfProduct) {
            this.amountOfProduct = amountOfProduct;
        }


        //</editor-fold>
    }
}
