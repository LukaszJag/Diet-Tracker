package com.lukaszjag.diet_tracker_android.tools.sql_tools;

public class QueryMaker {
    public static String getAllProductTableLikeProductNameCaseInsensitive(String productData) {
        // If productData contains a single quote, we escape it to prevent SQL syntax errors.
        String escapedData = productData != null ? productData.replace("'", "''") : "";
        return "SELECT * FROM [diet_tracker_schema].[product_table] WHERE LOWER(product_name) LIKE '%" + escapedData + "%'";
    }

    public static String getAllProductsFromCalendarTableLikeDate(String date) {
        String escapedDate = date != null ? date.replace("'", "''") : "";
        return "SELECT * FROM [diet_tracker_schema].[calendar] WHERE day_date LIKE '%" + escapedDate + "%'";
    }

    public static String insertMealToCalendarTable(
            String dayDate, String dayName, String mealName, double amountOfProduct,
            String productName, double kcal, double protein, double fat, double carbs,
            String timeOptional, String commentOptional, double kcalConsume,
            double carbsConsume, double fatConsume, double proteinConsume) {

        // Helper to format Date/Time or Text fields to raw NULL if they are "none", null, or empty
        String sqlDayDate = (dayDate == null || dayDate.trim().isEmpty())
                ? "NULL" : "'" + dayDate.replace("'", "''") + "'";

        String sqlDayName = (dayName == null || dayName.trim().isEmpty())
                ? "NULL" : "'" + dayName.replace("'", "''") + "'";

        String sqlMealName = (mealName == null || mealName.trim().isEmpty())
                ? "NULL" : "'" + mealName.replace("'", "''") + "'";

        String sqlProductName = (productName == null || productName.trim().isEmpty())
                ? "NULL" : "'" + productName.replace("'", "''") + "'";

        // If time is "none", convert to raw SQL NULL so Azure SQL doesn't fail parsing it
        String sqlTime = (timeOptional == null || timeOptional.equalsIgnoreCase("none") || timeOptional.trim().isEmpty())
                ? "NULL" : "'" + timeOptional.replace("'", "''") + "'";

        String sqlComment = (commentOptional == null || commentOptional.equalsIgnoreCase("none") || commentOptional.trim().isEmpty())
                ? "NULL" : "'" + commentOptional.replace("'", "''") + "'";

        // Returns the built query directly.
        // Java's implicit double-to-string conversion always uses the dot decimal separator.
        return "INSERT INTO [diet_tracker_schema].[calendar]\n" +
                "([day_date], [day_name], [meal_name], [amount_of_product], [product_name],\n" +
                "[kcal], [protein], [fat], [carbs], [time_optional], [comment_optional],\n" +
                "[kcal_consume], [carbs_consume], [fat_consume], [protein_consume])\n" +
                "VALUES (\n" +
                sqlDayDate + ",\n" +
                sqlDayName + ",\n" +
                sqlMealName + ",\n" +
                amountOfProduct + ",\n" +
                sqlProductName + ",\n" +
                kcal + ",\n" +
                protein + ",\n" +
                fat + ",\n" +
                carbs + ",\n" +
                sqlTime + ",\n" +
                sqlComment + ",\n" +
                kcalConsume + ",\n" +
                carbsConsume + ",\n" +
                fatConsume + ",\n" +
                proteinConsume + "\n" +
                ");\n" +
                "SELECT 1 AS success;";
    }
}