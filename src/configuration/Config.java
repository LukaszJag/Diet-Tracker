package configuration;

import tools.products_tools.Macro;

import java.awt.*;
import java.util.Date;

public class Config {


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

    //</editor-fold>

    //<editor-fold desc="GUI Configuration">

    //<editor-fold desc="DaysStatisticsViewer - configuration">
    public static final int DAYS_STATISTICS_VIEWER_WIDTH = 1200;
    public static final int DAYS_STATISTICS_VIEWER_HEIGHT = 800;

    //</editor-fold>

    //<editor-fold desc="Start windows - configuration">
    public static final int START_WINDOWS_WIDTH = 700;
    public static final int START_WINDOWS_HEIGHT = 500;
    //</editor-fold>

    //<editor-fold desc="BMR window - configuration">
    public static final int BMR_WINDOW_WINDOWS_WIDTH = 900;
    public static final int BMR_WINDOW_WINDOWS_HEIGHT = 600;
    public static final int BMR_WINDOW_PANELS_NORTH_SIZE = 50;
    public static final int BMR_WINDOW_PANELS_CENTER = 20;
    public static final int BMR_WINDOW_PANELS_WEST_EAST_SIZE = 200;
    public static final int BMR_WINDOW_PANELS_SOUTH_SIZE = 100;
    //</editor-fold>

    //<editor-fold desc="Add New Product Window Configuration">
    public static final int ADD_PRODUCT_WINDOWS_WIDTH = 600;
    public static final int ADD_PRODUCT_WINDOWS_HEIGHT = 400;
    public static final int ADD_PRODUCT_TEXT_FIELD_SIZE = 10;
    public static final int ADD_PRODUCT_PANELS_NORTH_SIZE = 50;
    public static final int ADD_PRODUCT_PANELS_CENTER = 20;
    public static final int ADD_PRODUCT_PANELS_WEST_EAST_SIZE = 80;
    public static final int ADD_PRODUCT_PANELS_SOUTH_SIZE = 100;
    //</editor-fold>

    //<editor-fold desc="Add Product To Day Windows Configuration - Window, Panels - size">
    public static final int ADD_PRODUCT_TO_DAY_WINDOWS_WIDTH = 1300;
    public static final int ADD_PRODUCT_TO_DAY_WINDOWS_HEIGHT = 800;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE = 50;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_CENTER = 20;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_EAST_SIZE = 170;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_WEST__SIZE = 400;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE = 50;
    public static final Color mainWindowDataBaseProductTableLabelColor = Color.MAGENTA;
    public static final Color mainWindowDataBaseCalendarTableLabelColor = Color.RED;
    public static final Color dateTimeLabels = Color.WHITE;
    public static final Color addProductToDayCurrentDateLabelColor = Color.WHITE;
    public static final Dimension CHECK_DAYS_STATISTIC_FILLED_TABLE_BUTTON_WINDOW_FRAME_SIZE = new Dimension(1100, 600);
    public static final Dimension CHECK_CALENDAR_TABLE_BUTTON_WINDOW_FRAME_SIZE = new Dimension(1200, 600);

    public static final Color CHOSE_TABLE_TO_INSERT_DATA = Color.GREEN;
    //</editor-fold>

    //<editor-fold desc="Calendar Month Stats View - GUI">
    public static final int CALENDAR_MONTH_STATS_VIEW_WINDOWS_WIDTH = 900;
    public static final int CALENDAR_MONTH_STATS_VIEW_WINDOWS_HEIGHT = 600;
    public static final int CALENDAR_MONTH_STATS_VIEW_PANELS_NORTH_SIZE = 100;
    public static final int CALENDAR_MONTH_STATS_VIEW_PANELS_CENTER = 60;
    public static final int CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE = 200;
    public static final int CALENDAR_MONTH_STATS_VIEW_PANELS_SOUTH_SIZE = 100;
    public static final Dimension CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION = new Dimension(5, 5);
    //</editor-fold>

    //<editor-fold desc="AddWorkoutToTableForWorkout window - configuration">
    public static final int ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_WINDOWS_WIDTH = 900;
    public static final int ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_WINDOWS_HEIGHT = 600;
    public static final int ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_PANELS_NORTH_SIZE = 50;
    public static final int ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_PANELS_CENTER = 20;
    public static final int ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_PANELS_WEST_EAST_SIZE = 200;
    public static final int ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_PANELS_SOUTH_SIZE = 100;
    //</editor-fold>

    //<editor-fold desc="SimpleInputWindowGUI">
    public static final Dimension SIMPLE_INPUT_WINDOW_GUI_WINDOW_SIZE = new Dimension(1000, 800);
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
