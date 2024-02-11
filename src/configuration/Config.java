package configuration;
public class Config {

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

    // Add Product To Day Windows Configuration
    public static final int ADD_PRODUCT_TO_DAY_WINDOWS_WIDTH = 600;
    public static final int ADD_PRODUCT_TO_DAY_WINDOWS_HEIGHT = 400;
    public static final int ADD_PRODUCT_TO_DAY_TEXT_FIELD_SIZE = 10;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE = 50;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_CENTER = 20;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE = 80;
    public static final int ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE = 100;

    // File R/W Configuration
    public static final String DESTINATION_FOR_TEXT_FILE_PRODUCTS = "src/text_files/products/";
    public static final String DESTINATION_FOR_TEXT_FILE_DAYS = "src/text_files/days/";
    public static final String DESTINATION_FOR_SQL_QUERIES_TO_TEXT_FILE = "src/text_files/sql_queries/";

    // SQL Configuration
    public static final String[] SQL_COLUMNS_FOR_INSERT_INTO_PRODUCT_TABLE = {"`product_name`", "`product_brand`", "`product_package_has`",
            "`product_macro_for`", "`product_kcal`", "`product_protein`", "`product_fat`", "`product_carbs`"};

    // Product configuration
    public static final int ALL_PRODUCT_VALUES_FIELD_COUNT = 8;
    /*

    public static final String[] options = {"Create new account", "Withdraw money","Pay in" ,"Close account"};
    public static final String[] money = {"Euro", "Dollars", "Zloty"};


    // Name, LastName, status of account, amount of euro, amount of dollars, amount of zloty

    public static int users = 0;
    public static double totalMoney = 0;

    public static final int DEPOSIT_WINDOWS_WIDTH = 800;
    public static final int DEPOSIT_WINDOWS_HEIGHT = 200;
    public static final int DEPOSIT_TEXT_FIELD_SIZE = 14;

    public static final int  DEPOSIT_PANEL_NORTH_SIZE = 50;
    public static final int  DEPOSIT_PANEL_WEST_SIZE = 20;
    public static final int  DEPOSIT_PANEL_MAIN_SIZE = 100;
    public static final int  DEPOSIT_PANEL_EAST_SIZE = 20;
    public static final int  DEPOSIT_PANEL_SOUTH_SIZE = 50;

    public static final int WITHDRAW_MONEY_WINDOW_WIDTH = 800;
    public static final int WITHDRAW_MONEY_WINDOW_HEIGHT = 200;

    public static final int  WITHDRAW_PANEL_NORTH_SIZE = 50;
    public static final int  WITHDRAW_PANEL_WEST_SIZE = 20;
    public static final int  WITHDRAW_PANEL_MAIN_SIZE = 100;
    public static final int  WITHDRAW_PANEL_EAST_SIZE = 20;
    public static final int  WITHDRAW_PANEL_SOUTH_SIZE = 50;

    public static final int  CLOSE_PANEL_NORTH_SIZE = 50;
    public static final int  CLOSE_PANEL_WEST_SIZE = 20;
    public static final int  CLOSE_PANEL_MAIN_SIZE = 100;
    public static final int  CLOSE_PANEL_EAST_SIZE = 20;
    public static final int  CLOSE_PANEL_SOUTH_SIZE = 50;
*/

}
