package tests.tools_tests.sql_tools;

import configuration.Config;
import tools.sql_tools.general.statements.Select;
import tools.sql_tools.general.get.GetConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SQLTests {
    public static void main(String[] args) throws SQLException {
        //test1();
        test2();
    }

    private static void test2() {
        System.out.println("Start test SQLSelect.java -> getAllProductAndBrandNamesFromProductTable");
        HashMap<String, String> hashMap = Select.getAllProductAndBrandNamesFromProductTable();

        Set<Map.Entry<String, String>> set = hashMap.entrySet();

        for(Map.Entry<String, String> me : set) {
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }
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
