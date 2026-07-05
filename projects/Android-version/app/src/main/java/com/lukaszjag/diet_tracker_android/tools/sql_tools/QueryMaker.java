package com.lukaszjag.diet_tracker_android.tools.sql_tools;

public class QueryMaker {
    public static String getAllProductTableLikeProductNameCaseInsensitive(String productData) {
        return "SELECT * FROM [diet_tracker_schema].[product_table] WHERE LOWER(product_name) LIKE '%" + productData + "%'";
    }
}
