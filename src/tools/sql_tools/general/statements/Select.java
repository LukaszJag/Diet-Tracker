package tools.sql_tools.general.statements;

import com.mysql.cj.conf.ConnectionUrlParser;
import configuration.Config;
import tools.sql_tools.general.RowInTable;
import tools.sql_tools.general.Table;
import tools.sql_tools.general.get.GetConnection;
import tools.sql_tools.general.get.GetResultSet;
import tools.sql_tools.general.get_check_data.GetAmountOfRows;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Select {
    // TODO clean up code
    // TODO only selectAllDataFromTable AND selectOneRowDataFromTable - method is finish - finish rest of methods

    //<editor-fold desc="Select All Data From Query">
    public static LinkedHashMap<String, String> selectAllDataFromTable(String tableName, String key, String operator, String value) {
        String sqlStatement = "SELECT";
/*
example:
SELECT * from table WHERE key = value ??? -> =
SELECT * from table WHERE key LIKE value ??? -> LIKE
SELECT * from table WHERE key != value ??? -> !=

how to handle this upper examples
 */
        return null;
    }

    public static LinkedHashMap<String, String> selectAllDataFromTable(String tableName, String selectedColumn1, String selectedColumn2,
                                                                       String key, String operator, String value) {
        LinkedHashMap<String, String> resultLinkedHashMap = new LinkedHashMap<>();

        String sqlStatement = "SELECT " +
                selectedColumn1 + " , " + selectedColumn2 +
                " FROM " + tableName +
                " WHERE " +
                " " + key +
                " " + operator +
                " " + "\"" + value + "\"" + ";";

        ResultSet resultSet;
        GetResultSet getResultSet = new GetResultSet();
        resultSet = getResultSet.getResultSetFromSQL(sqlStatement);


        try {
            while (resultSet.next()) {
                resultLinkedHashMap.put(resultSet.getString(1), resultSet.getString(2));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultLinkedHashMap;
        /*
example:
SELECT * from table WHERE key = value ??? -> =
SELECT * from table WHERE key LIKE value ??? -> LIKE
SELECT * from table WHERE key != value ??? -> !=

how to handle this upper examples
 */

    }

    public static ArrayList<ArrayList<HashMap<String, String>>> selectAllDataFromQuery(String SQLQuery) {
        //<editor-fold desc="Values">
        ResultSet resultSet;
        ResultSetMetaData resultSetMetaData;

        ArrayList<String> columnsNames;
        //</editor-fold>

        resultSet = GetResultSet.getResultSetFromSQL(SQLQuery);

        int amountOfColumns = GetResultSet.getAmountColumnsInResultSet(resultSet);

        ArrayList<ArrayList<HashMap<String, String>>> rows = new ArrayList<ArrayList<HashMap<String, String>>>();

        ArrayList<HashMap<String, String>> row = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> valuesAndKeysOfRow = new HashMap<>();


        int counter = 0;
        int rowNumber = 0;

        while (GetResultSet.isResultSetHasNext(resultSet)) {
            for (int i = 1; i < amountOfColumns; i++) {
                valuesAndKeysOfRow.put(GetResultSet.getColumnName(resultSet, i), GetResultSet.getValueOfString(resultSet, i));
            }
            row.add(counter, valuesAndKeysOfRow);
            valuesAndKeysOfRow.clear();
            counter++;

            rowNumber++;
        }

        rows.add(0, row);

        return rows;
    }

    public static ArrayList<HashMap<String, String>> selectAllDataFromQuery2(String SQLQuery) {
        //<editor-fold desc="Values">
        ResultSet resultSet;
        ResultSetMetaData resultSetMetaData;

        ArrayList<String> columnsNames;
        //</editor-fold>

        resultSet = GetResultSet.getResultSetFromSQL(SQLQuery);

        int amountOfColumns = GetResultSet.getAmountColumnsInResultSet(resultSet);

        ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> valuesAndKeysOfRow = new HashMap<>();


        int counter = 0;
        int rowNumber = 0;

        while (GetResultSet.isResultSetHasNext(resultSet)) {
            for (int i = 1; i < amountOfColumns; i++) {
                valuesAndKeysOfRow.put(GetResultSet.getColumnName(resultSet, i), GetResultSet.getValueOfString(resultSet, i));
            }
            rows.add(counter, valuesAndKeysOfRow);
            valuesAndKeysOfRow.clear();
            counter++;

            rowNumber++;
        }
        return rows;
    }
    //</editor-fold>

    //<editor-fold desc="Rows in SQL table - method">
    public static HashMap<String, String> selectOneRowDataFromQuery(String SQLQuery) {
        //<editor-fold desc="Values">
        ResultSet resultSet;
        ResultSetMetaData resultSetMetaData;

        ArrayList<String> columnsNames;
        //</editor-fold>

        resultSet = GetResultSet.getResultSetFromSQL(SQLQuery);

        int amountOfColumns = GetResultSet.getAmountColumnsInResultSet(resultSet);

        HashMap<String, String> rows = new HashMap<>();

        int counter = 0;
        int rowNumber = 0;

        while (GetResultSet.isResultSetHasNext(resultSet)) {
            for (int i = 1; i < amountOfColumns; i++) {
                rows.put(GetResultSet.getColumnName(resultSet, i), GetResultSet.getValueOfString(resultSet, i));
            }
            counter++;

            rowNumber++;
        }
        return rows;
    }

    public static ArrayList<RowInTable> allRowsDataFromQuery(String SQLQuery) {
        //<editor-fold desc="Values">
        ResultSet resultSet = GetResultSet.getResultSetFromSQL(SQLQuery);
        ResultSetMetaData resultSetMetaData = GetResultSet.getResultSetMetaData(resultSet);

        int amountOfColumns = GetResultSet.getAmountColumnsInResultSet(resultSet);
        int amountOfRows = GetAmountOfRows.getAmountOfRows(SQLQuery);

        Table table = new Table();
        ArrayList<RowInTable> rowsInTable = new ArrayList<RowInTable>();
        RowInTable[] rows = new RowInTable[amountOfRows];

        for (int i = 0; i < amountOfRows; i++) {
            rows[i] = new RowInTable();
        }

        //</editor-fold>

        RowInTable rowInTabletmp;
        for (int i = 0; i < amountOfRows; i++) {
            rowInTabletmp = new RowInTable();
            try {
                resultSet.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            for (int j = 1; j < amountOfColumns - 1; j++) {

                String value = null;
                value = GetResultSet.getValueOfString(resultSet, j);
                //System.out.println(value);
                rows[i].putKeyAndValueToRow(GetResultSet.getColumnName(resultSet, j), value);

            }
            //rowInTabletmp.printAlLValuesAndKey();
            table.putRowToTable(rowInTabletmp);
        }
        System.out.println("\n\n\nTable: ");
        table.printTable();
        //rowsInTable = table.getRows();
        return rowsInTable;
    }

    public static ConnectionUrlParser.Pair<String, String> selectOneRowDataFromTable(String tableName, String selectedColumn1, String selectedColumn2,
                                                                                     String key, String operator, String value) {
        ConnectionUrlParser.Pair<String, String> resultPair;

        String sqlStatement = "SELECT " +
                selectedColumn1 + " , " + selectedColumn2 +
                " FROM " + tableName +
                " WHERE " +
                " " + key +
                " " + operator +
                " " + "\"" + value + "\"" + ";";


        ResultSet resultSet;
        GetResultSet getResultSet = new GetResultSet();
        resultSet = getResultSet.getResultSetFromSQL(sqlStatement);

        String left = "";
        String right = "";

        try {
            if (resultSet.next()) {
                left = resultSet.getString(1);

                right = resultSet.getString(2);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultPair = new ConnectionUrlParser.Pair<>(left, right);
        /*
example:
SELECT * from table WHERE key = value ??? -> =
SELECT * from table WHERE key LIKE value ??? -> LIKE
SELECT * from table WHERE key != value ??? -> !=

how to handle this upper examples
 */

    }
    //</editor-fold>

    //<editor-fold desc="Columns in SQL table - method">
    public static String[] getAllValuesInColumn(String tableName, int column) {
        String[] columnValues;
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

            columnValues = new String[amountOfRows];

            int counter = 0;
            while (resultSet.next()) {
                columnValues[counter] = resultSet.getString(column);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return columnValues;
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
    //</editor-fold>

    //<editor-fold desc="Product related methods">
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

    public static String[] getAllProductNamesFromProductTable() throws SQLException {
        ResultSet resultSet;
        Statement statement;

        String[] productsArray = new String[600];

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
    //</editor-fold>

}
