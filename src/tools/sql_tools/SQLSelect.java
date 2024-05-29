package tools.sql_tools;

import configuration.Config;
import tools.sql_tools.general.GetConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLSelect {
    public static String[] getAllProductNamesFromProductTable() throws SQLException {
        ResultSet resultSet;
        Statement statement;

        String[] productsArray = new String[200];

        String sql = "SELECT product_name FROM diet_tracker_schema.product_table";
        String result = "";


        Connection connection = GetConnection.getConnectionWithLocalHost();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        int counter = 0;
        while(resultSet.next()){
            productsArray[counter] = resultSet.getString(1);
            counter++;
        }

        return productsArray;
    }

    public static String getRowFromProductTableByProductNameGetString(String productName) throws SQLException {
        ResultSet resultSet;
        Statement statement;

        String sql = "SELECT * FROM diet_tracker_schema.product_table WHERE product_name=\"" + productName + "\";";
        String result = "";


        Connection connection = GetConnection.getConnectionWithLocalHost();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        while(resultSet.next()){
            for (int i = 0; i < Config.SQL_COLUMNS_PRODUCT.length; i++) {
                result += resultSet.getString(Config.SQL_COLUMNS_PRODUCT[i].replace("`","")) + " ";
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

        while(resultSet.next()){
            for (int i = 0; i < Config.SQL_COLUMNS_PRODUCT.length; i++) {
                result[i] = resultSet.getString(Config.SQL_COLUMNS_PRODUCT[i].replace("`",""));
            }
        }

        return result;
    }
}
