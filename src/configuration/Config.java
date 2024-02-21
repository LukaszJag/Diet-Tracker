package configuration;

import java.awt.*;
import java.util.Date;

public class Config {

    /*
        --------------------------------------
        GUI CONFIGURATION AND OTHER DATA - START
     */

    // GUI Configuration
    public static final int START_WINDOWS_WIDTH = 700;
    public static final int START_WINDOWS_HEIGHT = 500;

    // Add New Product Window Configuration
    public static final int ADD_PRODUCT_WINDOWS_WIDTH = 600;
    public static final int ADD_PRODUCT_WINDOWS_HEIGHT = 400;
    public static final int ADD_PRODUCT_TEXT_FIELD_SIZE = 10;
    public static final int ADD_PRODUCT_PANELS_NORTH_SIZE = 50;
    public static final int ADD_PRODUCT_PANELS_CENTER = 20;
    public static final int ADD_PRODUCT_PANELS_WEST_EAST_SIZE = 80;
    public static final int ADD_PRODUCT_PANELS_SOUTH_SIZE = 100;
    public static final int howManyParametersToAddProduct = 8;

    // Add Product To Day Windows Configuration - Window, Panels - size
    public static final int ADD_PRODUCT_TO_DAY_WINDOWS_WIDTH = 600;
    public static final int ADD_PRODUCT_TO_DAY_WINDOWS_HEIGHT = 400;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE = 50;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_CENTER = 20;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE = 80;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE = 100;

    // Add Product To Day Windows Configuration - Panel, Label - Colors
    public static final Color dateTimeLabels = Color.WHITE;
    // Add Product To Day Windows Configuration - Get Date
    public static final Date date = new Date();
    public static final int ADD_PRODUCT_TO_DAY_TEXT_FIELD_SIZE = 10;


    /*
        GUI CONFIGURATION AND OTHER DATA - END
        --------------------------------------
     */


    // File R/W Configuration
    public static final String DESTINATION_FOR_TEXT_FILE_PRODUCTS = "src/text_files/products/";
    public static final String DESTINATION_FOR_TEXT_FILE_DAYS = "src/text_files/days/";
    public static final String DESTINATION_FOR_SQL_QUERIES_TO_TEXT_FILE = "src/text_files/sql_queries/";


    /*
        --------------------------------------
        SQL CONFIGURATION AND OTHER DATA - START
     */

    // SQL Configuration - Calendar fields
    public static final String[] SQL_COLUMNS_CALENDAR = {"day_date", "day_name", "amount_of_product", "product_name",
           "kcal", "protein", "fat", "carbs", "time_optional", "comment_optional"};


    //SQL Configuration - To insert product to table
    public static final String[] SQL_COLUMNS_FOR_INSERT_INTO_PRODUCT_TABLE = {"`product_name`", "`product_brand`", "`product_package_has`",
            "`product_macro_for`", "`product_kcal`", "`product_protein`", "`product_fat`", "`product_carbs`"};


    /*
        SQL CONFIGURATION AND OTHER DATA - END
        --------------------------------------
     */


    // Product configuration
    public static final int ALL_PRODUCT_VALUES_FIELD_COUNT = 8;

    // Day in calendar configuration
    public static final int DAY_IN_CALENDAR_STRING_ARRAY_LENGTH = Config.SQL_COLUMNS_CALENDAR.length;



}
