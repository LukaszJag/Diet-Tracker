package runners_and_tests.tests.all_tests;

import tools.sql_tools.CheckIfRowExist;
import tools.sql_tools.SQLSelect;
import tools.sql_tools.days_statistics.SelectFromDaysStatistics;
import tools.sql_tools.general.GetResultSet;

import java.sql.SQLException;

public class SQLTests {
    public static void main(String[] args) throws SQLException {
        //test1();
        //test2();
        //test3();
        test4();
    }

    private static void test1() {
        String sqlStatement = "SELECT COUNT(product_name)\n" +
                "FROM product_table\n" +
                "WHERE product_name = \"" + "Burak" + "\";";
        System.out.println(sqlStatement);
    }
    private static void test2() throws SQLException {
        System.out.println(CheckIfRowExist.isProductNameExistInProductTable("Burak"));
        System.out.println(CheckIfRowExist.isProductNameExistInProductTable("Bagietka - BBQ Strips"));
    }
    private static void test3() {
        try {
            SQLSelect.getAllProductNamesFromProductTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static void test4() {
        SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate("2024-05-30");
    }
}
