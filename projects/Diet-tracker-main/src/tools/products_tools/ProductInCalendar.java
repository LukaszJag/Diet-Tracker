package tools.products_tools;

import java.util.ArrayList;

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
