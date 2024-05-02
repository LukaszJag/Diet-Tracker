package configuration;

import java.awt.*;
import java.util.Date;

public class Config {


    //<editor-fold desc="GUI Configuration">
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

    // Add Product To Day Windows Configuration - Window, Panels - size
    public static final int ADD_PRODUCT_TO_DAY_WINDOWS_WIDTH = 900;
    public static final int ADD_PRODUCT_TO_DAY_WINDOWS_HEIGHT = 600;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE = 50;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_CENTER = 20;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE = 200;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE = 100;
    public static final Color mainWindowDataBaseProductTableLabelColor = Color.MAGENTA;
    public static final Color mainWindowDataBaseCalendarTableLabelColor = Color.RED;
    //</editor-fold>

    //<editor-fold desc="Add Product To Day Windows Configuration - Panel, Label - Colors">
    public static final Color dateTimeLabels = Color.WHITE;
    public static final Color addProductToDayCurrentDateLabelColor = Color.WHITE;

    public static final Color CHOSE_TABLE_TO_INSERT_DATA = Color.GREEN;
    public static final Date date = new Date();
    //</editor-fold>


    //<editor-fold desc="SQL CONFIGURATION AND OTHER DATA">

    // SQL Configuration - Calendar fields
    public static final String[] SQL_COLUMNS_CALENDAR = {"day_date", "day_name", "meal_name","amount_of_product", "product_name",
            "kcal", "protein", "fat", "carbs", "time_optional", "comment_optional", "kcal_consume", "carbs_consume", "fat_consume", "protein_consume"};

    public static final String[] SQL_COLUMNS_CALENDAR_WITH_KCAL_CONSUME = {"day_date", "day_name", "amount_of_product", "product_name",
            "kcal", "protein", "fat", "carbs", "time_optional", "comment_optional", "kcal_consume"};

    //SQL Configuration - To insert product to table
    public static final String[] SQL_COLUMNS_PRODUCT = {"`product_name`", "`product_brand`", "`product_package_has`",
            "`product_macro_for`", "`product_kcal`", "`product_protein`", "`product_fat`", "`product_carbs`", "`comment_optional`"};

    //</editor-fold>

    //<editor-fold desc="DAY IN CALENDAR - Configuration">
    public static final int ALL_PRODUCT_VALUES_FIELD_COUNT = Config.SQL_COLUMNS_PRODUCT.length;

    // Day in calendar configuration - arrays length ect.
    public static final int DAY_IN_CALENDAR_STRING_ARRAY_LENGTH = 11;
    public static final int DAY_IN_CALENDAR_STRING_ARRAY_LENGTH_WITH_KCAL_CONSUME_LENGTH = SQL_COLUMNS_CALENDAR_WITH_KCAL_CONSUME.length;
    public static final int howManyParametersToAddProduct = Config.SQL_COLUMNS_PRODUCT.length;
    //</editor-fold>

    //<editor-fold desc="File R/W Configuration">

    public static final String DESTINATION_FOR_TEXT_FILE_PRODUCTS = "src/data_store_and_backup/text_files/products/";
    public static final String DESTINATION_FOR_SQL_TEXT_FILE_PRODUCTS = "src/data_store_and_backup/text_files/products_sql_query/";
    public static final String DESTINATION_FOR_TEXT_FILE_DAYS = "src/data_store_and_backup/text_files/days/";
    public static final String DESTINATION_FOR_SQL_QUERIES_TO_TEXT_FILE = "src/data_store_and_backup/text_files/sql_queries/";
    //</editor-fold>

    // On run configuration

    public static String CURRENT_DATABASE_TABLE_PRODUCT =  "product_table";
    public static String CURRENT_DATABASE_TABLE_CALENDAR = "calendar";



    // Product configuration

}
