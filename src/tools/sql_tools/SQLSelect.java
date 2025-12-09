package tools.sql_tools;

import configuration.Config;
import tools.sql_tools.general.GetConnection;

import java.sql.*;
import java.util.HashMap;

public class SQLSelect {
    //<editor-fold desc="Product Table SELECT">
    public static String[] getAllProductNamesFromProductTable() throws SQLException {
        ResultSet resultSet;
        Statement statement;

        String[] productsArray = new String[500];

        String sql = "SELECT product_name FROM diet_tracker_schema.product_table";

        Connection connection = GetConnection.getConnectionWithLocalHost();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        int counter = 0;
        while (resultSet.next()) {
            productsArray[counter] = resultSet.getString(1);
            counter++;
        }

        resultSet.close();
        statement.close();
        connection.close();

        return productsArray;
    }

    public static HashMap<String, String> getAllProductAndBrandNamesFromProductTable() {
        ResultSet resultSet;
        Statement statement;

        HashMap<String, String> allProductnameBrandnameHashMap = new HashMap<String, String>();

        String SQLStatement = "SELECT product_name, product_brand FROM diet_tracker_schema.product_table;";

        String result = "";
        Connection connection = null;

        try {
            connection = GetConnection.getConnectionWithLocalHost();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQLStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String brandFieldSQl = "";
        try {
            while (resultSet.next()) {
                if ((resultSet.getString(2)).equals(" ")) {
                    brandFieldSQl = "none";
                } else {
                    brandFieldSQl = resultSet.getString(2);
                }

                allProductnameBrandnameHashMap.put(resultSet.getString(1), brandFieldSQl);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allProductnameBrandnameHashMap;
    }

    public static String getRowFromProductTableByProductNameGetString(String productName) throws SQLException {
        ResultSet resultSet;
        Statement statement;

        String sql = "SELECT * FROM diet_tracker_schema.product_table WHERE product_name=\"" + productName + "\";";
        String result = "";


        Connection connection = GetConnection.getConnectionWithLocalHost();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            for (int i = 0; i < Config.SQL_COLUMNS_PRODUCT.length; i++) {
                result += resultSet.getString(Config.SQL_COLUMNS_PRODUCT[i].replace("`", "")) + " ";
            }
        }

        return result;
    }

    public static String[] getRowFromProductTableByProductNameGetArray(String productName) throws SQLException {
        ResultSet resultSet;
        Statement statement;

        String sql = "SELECT * FROM diet_tracker_schema.product_table WHERE product_name=\"" + productName + "\";";
        String[] result = new String[Config.SQL_COLUMNS_PRODUCT.length];

        Connection connection = GetConnection.getConnectionWithLocalHost();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            for (int i = 0; i < Config.SQL_COLUMNS_PRODUCT.length; i++) {
                result[i] = resultSet.getString(Config.SQL_COLUMNS_PRODUCT[i].replace("`", ""));
            }
        }
        connection.close();
        resultSet.close();
        statement.close();

        return result;
    }

    public static String getRowFromProductTableByProductNameWithColumnName(String productName) throws SQLException {
        ResultSet resultSet;
        Statement statement;
        ResultSetMetaData resultSetMetaData;

        String sql = "SELECT * FROM diet_tracker_schema.product_table WHERE product_name=\"" + productName + "\";";
        String result = "";


        Connection connection = GetConnection.getConnectionWithLocalHost();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        resultSetMetaData = resultSet.getMetaData();
        int amountOfColumns = resultSetMetaData.getColumnCount();

        while (resultSet.next()) {
            for (int i = 1; i <= amountOfColumns; i++) {
                result += resultSetMetaData.getColumnName(i) + " : ";
                result += resultSet.getString(i) + "\n";
            }
        }

        return result;
    }

    public static String[] getRowsInCalendarByProductName(String productName) throws SQLException {
        // Hard code array length
        String[] allRowsArray = new String[50];
        ResultSet resultSet;
        Statement statement;
        ResultSetMetaData resultSetMetaData;

        String sql = "SELECT * FROM calendar WHERE product_name = " + "'" + productName + "'";

        Connection connection = GetConnection.getConnectionWithLocalHost();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        resultSetMetaData = resultSet.getMetaData();
        int amountOfColumns = resultSetMetaData.getColumnCount();

        String rowInString = "";
        int counter = 0;
        while (resultSet.next()) {
            for (int i = 1; i <= amountOfColumns; i++) {
                rowInString += resultSetMetaData.getColumnName(i) + " : ";
                rowInString += resultSet.getString(i) + "\n";
            }
            allRowsArray[counter] = rowInString;
            rowInString = "";
            counter++;
        }

        resultSet.close();
        connection.close();
        statement.close();

        return allRowsArray;
    }

    public static String[] getAllWrongProductByNameFromProductTable() throws SQLException {
        // Hard code array length
        String[] allRowsArray = new String[200];
        ResultSet resultSet;
        Statement statement;
        ResultSetMetaData resultSetMetaData;

        String sql = "SELECT * FROM product_table WHERE product_macro_for != 0 AND product_macro_for != 100";

        Connection connection = GetConnection.getConnectionWithLocalHost();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        resultSetMetaData = resultSet.getMetaData();
        int amountOfColumns = resultSetMetaData.getColumnCount();

        String rowInString = "";
        int counter = 0;
        while (resultSet.next()) {
            for (int i = 1; i <= amountOfColumns; i++) {
                rowInString += resultSetMetaData.getColumnName(i) + " : ";
                rowInString += resultSet.getString(i) + "\n";
            }
            allRowsArray[counter] = rowInString;
            rowInString = "";
            counter++;
        }

        resultSet.close();
        connection.close();
        statement.close();

        return allRowsArray;
    }
    //</editor-fold>

    public static String[] getAllProductDataByNameFromCalendarTable(String productName) throws SQLException {
        // Hard code array length
        String[] allRowsArray = new String[100];
        ResultSet resultSet;
        Statement statement;
        ResultSetMetaData resultSetMetaData;

        String sql = "SELECT * FROM calendar WHERE product_name = " + "'" + productName + "'";

        Connection connection = GetConnection.getConnectionWithLocalHost();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        resultSetMetaData = resultSet.getMetaData();
        int amountOfColumns = resultSetMetaData.getColumnCount();

        String rowInString = "";
        int counter = 0;
        while (resultSet.next()) {
            for (int i = 1; i <= amountOfColumns; i++) {
                // Temporary modification
                rowInString += resultSetMetaData.getColumnName(i) + " : ";
                rowInString += resultSet.getString(i) + "\n";
            }
            allRowsArray[counter] = rowInString;
            rowInString = "";
            counter++;
        }

        resultSet.close();
        connection.close();
        statement.close();

        return allRowsArray;
    }

    public static String[] getAllValuesInColumn(String tableName, int column) {
        try {
            ResultSet resultSet;
            Statement statement;

            String sql = "SELECT * FROM " + tableName;

            int amountOfRows = 0;

            Connection connection = GetConnection.getConnectionWithLocalHostWithoutTryCatch();

            statement = connection.createStatement();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            amountOfRows = resultSet.getRow();

            String[] columnValues = new String[amountOfRows];

            int counter = 0;
            while (resultSet.next()) {
                columnValues[counter] = resultSet.getString(column);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String[] getAllValuesInColumn(String tableName, String columnName) {
        try {
            ResultSet resultSet;
            Statement statement;

            String sql = "SELECT * FROM " + tableName;

            int amountOfRows = 0;

            Connection connection = GetConnection.getConnectionWithLocalHostWithoutTryCatch();

            statement = connection.createStatement();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            amountOfRows = resultSet.getRow();

            String[] columnValues = new String[amountOfRows];

            int counter = 0;
            while (resultSet.next()) {
                columnValues[counter] = resultSet.getString(columnName);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
