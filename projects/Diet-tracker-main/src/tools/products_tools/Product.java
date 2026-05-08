package tools.products_tools;

import configuration.Config;

import java.util.ArrayList;

public class Product {

    // Values

    //<editor-fold desc="Fundamental values">
    private String productName;
    private float productMeasureOfProductWeightToCalculateMacro;
    private Macro productMacroForItsSetMeasure;
    private String productBrand;
    private float productPackWeight;
    private String commentOptional;
    //</editor-fold>



    // Constructors
    public Product(String name, String brand, float productMeasureOfProductWeightToCalculateMacro, Macro product_macro, float weight_of_pack, String commentOptional){
        this.productName = name;
        this.productBrand = brand;
        this.productMeasureOfProductWeightToCalculateMacro = productMeasureOfProductWeightToCalculateMacro;
        this.productMacroForItsSetMeasure = product_macro;
        this.productPackWeight = weight_of_pack;
        this.commentOptional = commentOptional;
    }

    public static boolean isProductEqual(Product productOne, Product productTwo){
        String[] productOneInArray = productOne.productDataInStringArray(productOne);
        String[] productTwoInArray = productOne.productDataInStringArray(productTwo);

        for (int i = 0; i < productOneInArray.length; i++) {
            if(!productOneInArray[i].equals(productTwoInArray[i])){
                return false;
            }
        }
        return true;
    }

    public String[] productDataInStringArray(Product productWithData){
        String[] productDataStringArray = new String[Config.ALL_PRODUCT_VALUES_FIELD_COUNT];

        // Set values to array - no numeric
        productDataStringArray[0] = productWithData.getProductName();
        productDataStringArray[1] = productWithData.getProductBrand();

        // Set values to array - numeric
        productDataStringArray[2] = String.valueOf(productWithData.getProductPackWeight());
        productDataStringArray[3] = String.valueOf(productWithData.getProductMeasureOfProductWeightToCalculateMacro());
        productDataStringArray[4] = String.valueOf(productWithData.getProductMacroForItsSetMeasure().getKcal());
        productDataStringArray[5] = String.valueOf(productWithData.getProductMacroForItsSetMeasure().getProtein());
        productDataStringArray[6] = String.valueOf(productWithData.getProductMacroForItsSetMeasure().getFat());
        productDataStringArray[7] = String.valueOf(productWithData.getProductMacroForItsSetMeasure().getCarbs());
        productDataStringArray[8] =  productWithData.getCommentOptional();
        return productDataStringArray;
    }

    //<editor-fold desc="Getter and Setters">
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductMeasureOfProductWeightToCalculateMacro() {
        return productMeasureOfProductWeightToCalculateMacro;
    }

    public void setProductMeasureOfProductWeightToCalculateMacro(float productMeasureOfProductWeightToCalculateMacro) {
        this.productMeasureOfProductWeightToCalculateMacro = productMeasureOfProductWeightToCalculateMacro;
    }

    public Macro getProductMacroForItsSetMeasure() {
        return productMacroForItsSetMeasure;
    }

    public void setProductMacroForItsSetMeasure(Macro productMacroForItsSetMeasure) {
        this.productMacroForItsSetMeasure = productMacroForItsSetMeasure;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public float getProductPackWeight() {
        return productPackWeight;
    }

    public void setProductPackWeight(float productPackWeight) {
        this.productPackWeight = productPackWeight;
    }

    public String getCommentOptional() {
        return commentOptional;
    }

    public void setCommentOptional(String commentOptional) {
        this.commentOptional = commentOptional;
    }
    //</editor-fold>

    public class ProductInCalendar{
        // Product data in calendar table
        /*
            day_date,
            day_name,
            product_name,                               ---> Fundamental values
            amount_of_product,
            kcal,                                       ---> Fundamental values
            protein,                                    ---> Fundamental values
            fat,                                        ---> Fundamental values
            carbs,                                      ---> Fundamental values
            time_optional,
            comment_optional(calendarTable)    - in  calendar table,
            kcal_consume,
            carbs_consume,
            fat_consume,
            protein_consume,
            meal_name
         */

        String day_date;
        String day_name;


        String amount_of_product;
        String time_optional;
        String comment_optionalInCalendarTable;
        String meal_name;

        Macro consumedMacro;

        //    private Macro productMacroForItsSetMeasure; cover this values:
        //    String kcal;
        //    String protein;
        //    String fat;
        //    String carbs;

        //  Macro consumedMacro; cover this values:
        //  String kcal_consume;
        //  String carbs_consume;
        //  String fat_consume;
        //  String protein_consume;
        public ProductInCalendar(){}

        public ProductInCalendar(String day_date, String day_name, String amount_of_product, String time_optional, String comment_optionalInCalendarTable, Macro consumedMacro, String meal_name) {
            this.day_date = day_date;
            this.day_name = day_name;
            this.amount_of_product = amount_of_product;
            this.time_optional = time_optional;
            this.comment_optionalInCalendarTable = comment_optionalInCalendarTable;
            this.consumedMacro = consumedMacro;
            this.meal_name = meal_name;
        }

        public ArrayList<ProductInCalendar> getProductInCalendar(String dateInSQLFriendlyFormat){

            String SQLQuery ="" +
                    "SELECT " +
                    "* " +
                    "FROM " +
                    "calendar " +
                    "WHERE " +
                    "day_date" +
                    "=" +
                    "\"" +
                    dateInSQLFriendlyFormat +
                    "\"" +
                    ";";

            return  null;
        }

        //<editor-fold desc="Getters and Setters">
        public String getDay_date() {
            return day_date;
        }

        public void setDay_date(String day_date) {
            this.day_date = day_date;
        }

        public String getDay_name() {
            return day_name;
        }

        public void setDay_name(String day_name) {
            this.day_name = day_name;
        }

        public String getAmount_of_product() {
            return amount_of_product;
        }

        public void setAmount_of_product(String amount_of_product) {
            this.amount_of_product = amount_of_product;
        }

        public String getTime_optional() {
            return time_optional;
        }

        public void setTime_optional(String time_optional) {
            this.time_optional = time_optional;
        }

        public String getComment_optionalInCalendarTable() {
            return comment_optionalInCalendarTable;
        }

        public void setComment_optionalInCalendarTable(String comment_optionalInCalendarTable) {
            this.comment_optionalInCalendarTable = comment_optionalInCalendarTable;
        }

        public String getMeal_name() {
            return meal_name;
        }

        public void setMeal_name(String meal_name) {
            this.meal_name = meal_name;
        }

        public Macro getConsumedMacro() {
            return consumedMacro;
        }

        public void setConsumedMacro(Macro consumedMacro) {
            this.consumedMacro = consumedMacro;
        }
        //</editor-fold>
    }
}
