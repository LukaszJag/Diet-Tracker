package com.lukaszjag.diet_tracker_android.tools.sql_tools;

import java.util.Locale;

public class QueryMaker {
    public static String getAllProductTableLikeProductNameCaseInsensitive(String productData) {
        return "SELECT * FROM [diet_tracker_schema].[product_table] WHERE LOWER(product_name) LIKE '%" + productData + "%'";
    }

    public static String getAllProductsFromCalendarTableLikeDate(String date) {
        return "SELECT * FROM [diet_tracker_schema].[calendar] WHERE day_date LIKE '%" + date + "%'";
    }

    public static String insertMealToCalendarTable(
            String dayDate, String dayName, String mealName, double amountOfProduct,
            String productName, double kcal, double protein, double fat, double carbs,
            String timeOptional, String commentOptional, double kcalConsume,
            double carbsConsume, double fatConsume, double proteinConsume) {

        String query =
                "INSERT INTO `diet_tracker_schema`.`calendar`\n" +
                        "(`day_date`, `day_name`, `meal_name`, `amount_of_product`, `product_name`,\n" +
                        "`kcal`, `protein`, `fat`, `carbs`, `time_optional`, `comment_optional`,\n" +
                        "`kcal_consume`, `carbs_consume`, `fat_consume`, `protein_consume`)\n" +
                        "VALUES (\n" +
                        "'" + dayDate + "',\n" +
                        "'" + dayName + "',\n" +
                        "'" + mealName + "',\n" +
                        amountOfProduct + ",\n" +
                        "'" + productName + "',\n" +
                        kcal + ",\n" +
                        protein + ",\n" +
                        fat + ",\n" +
                        carbs + ",\n" +
                        "'" + timeOptional + "',\n" +
                        "'" + commentOptional + "',\n" +
                        kcalConsume + ",\n" +
                        carbsConsume + ",\n" +
                        fatConsume + ",\n" +
                        proteinConsume + "\n" +
                        ");";

        // Using Locale.US to ensure decimal points are '.' and not ','
        return String.format(Locale.US, query,
                dayDate, dayName, mealName, amountOfProduct, productName,
                kcal, protein, fat, carbs, timeOptional, commentOptional,
                kcalConsume, carbsConsume, fatConsume, proteinConsume);
    }
}
