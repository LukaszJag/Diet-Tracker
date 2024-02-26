package sql_tools;

import configuration.Config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLSelect {
    public static String getRowFromProductTableByProductName(String productName) throws SQLException {
        ResultSet resultSet;
        Statement statement;

        String sql = "SELECT * FROM diet_tracker_schema.product_table WHERE product_name=\"" + productName + "\";";
        String result = "";


        Connection connection = GetConnection.getConnectionWithLocalHost();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        while(resultSet.next()){
            for (int i = 0; i < Config.SQL_COLUMNS_FOR_INSERT_INTO_PRODUCT_TABLE.length; i++) {
                result += resultSet.getString(Config.SQL_COLUMNS_FOR_INSERT_INTO_PRODUCT_TABLE[i].replace("`","")) + " ";
            }
            System.out.println("Result of Query" + result + "\n");
        }
        return result;
    }
}
