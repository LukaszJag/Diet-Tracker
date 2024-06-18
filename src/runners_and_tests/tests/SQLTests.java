package runners_and_tests.tests;

import configuration.Config;
import tools.sql_tools.general.GetConnection;

import java.sql.*;

public class SQLTests {
    public static void main(String[] args) throws SQLException {
        //test1();
    }
    public static void test1() throws SQLException {
        ResultSet resultSet;
        Statement statement;
        //String sql = "SELECT * FROM diet_tracker_schema.product_table Where product_name=\"Burak\";";
        String sql = "SELECT * FROM diet_tracker_schema.product_table WHERE product_name=\"Burak\";";
        String result = "";


        Connection connection = GetConnection.getConnectionWithLocalHost();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        while(resultSet.next()){
            for (int i = 0; i < Config.SQL_COLUMNS_PRODUCT.length; i++) {
                result += resultSet.getString(Config.SQL_COLUMNS_PRODUCT[i].replace("`","")) + " ";
            }
            System.out.println(result);
            result = "";
        }

    }
}
