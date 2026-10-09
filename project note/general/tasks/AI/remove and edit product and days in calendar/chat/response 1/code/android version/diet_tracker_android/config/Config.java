package com.lukaszjag.diet_tracker_android.config;

import com.lukaszjag.diet_tracker_android.tools.products_tools.Macro;

import java.util.Date;

public class Config {
    public static final int ALL_PRODUCT_VALUES_FIELD_COUNT = Config.SQL_COLUMNS_PRODUCT.length;

    //<editor-fold desc="global variables">

    //<editor-fold desc="Console colors">
    //<editor-fold desc="Color for font in console">
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    //<editor-fold desc="Colors for background in console">
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    //</editor-fold>
    //</editor-fold>
    //</editor-fold>


    //<editor-fold desc="SQL CONFIGURATION AND OTHER DATA">

    // SQL Configuration - Calendar fields
    public static final String[] SQL_COLUMNS_CALENDAR = {"day_date", "day_name", "meal_name", "amount_of_product", "product_name",
            "kcal", "protein", "fat", "carbs", "time_optional", "comment_optional", "kcal_consume", "carbs_consume", "fat_consume", "protein_consume"};

    public static final String[] SQL_COLUMNS_CALENDAR_WITH_KCAL_CONSUME = {"day_date", "day_name", "amount_of_product", "product_name",
            "kcal", "protein", "fat", "carbs", "time_optional", "comment_optional", "kcal_consume"};

    //SQL Configuration - To insert product to table
    public static final String[] SQL_COLUMNS_PRODUCT = {"`product_name`", "`product_brand`", "`product_package_has`",
            "`product_macro_for`", "`product_kcal`", "`product_protein`", "`product_fat`", "`product_carbs`", "`comment_optional`"};
    public static final String[] SQL_COLUMNS_DAYS_STATISTICS_TEST = {"`day_date`", "`amount_of_points_from_notepad`",
            "`amount_of_filled_points_from_notepad`", "`kcal_consume`", "`protein_consume`", "`fat_consume`", "`carbs_consume`", "`day_name`"};
    public static final String[] gym_workoutTable = {"workout_ID", "day_date", "day_name", "location_of_workout",
            "general_type_of_workout", "type_of_workout", "name_of_exercise", "number_of_set",
            "weight_in_kilograms", "type_of_weight", "reps", "duration_of_break_seconds",
            "duration_in_seconds", "distance_in_meters", "amount_of_sets", "comment"};

    public static String CURRENT_DATABASE_TABLE_PRODUCT = "product_table";
    public static String CURRENT_DATABASE_TABLE_CALENDAR = "calendar";
    //</editor-fold>

    //<editor-fold desc="Date configuration">
    String[] MONTHS2024 = {"May", "June", "July", "August", "September", "October", "November", "December"};
    String[] MONTHS2025 = {"January", "February", "March", "April"};
    public static final Date date = new Date();
    //</editor-fold>

    //<editor-fold desc="BMR values">
    public static Macro BMR_07_10_2025 = new Macro(3531, 227, 151, 318);
    public static Macro BMRActual = BMR_07_10_2025;
    //</editor-fold>
}
