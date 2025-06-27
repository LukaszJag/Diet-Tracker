package tools.sql_tools.products;

import tools.sql_tools.general.GetResultSet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectFromProductTable {
    public static String selectProductsBrandFromProductName(String productName) throws SQLException {
        String sqlStatement = "SELECT product_brand FROM product_table WHERE product_name='"  + productName + "'" + ";";
        String productsBrand = "-1";

        ResultSet resultSet;
        GetResultSet getResultSet = new GetResultSet();
        resultSet = getResultSet.getResultSetFromSQL(sqlStatement);

        resultSet.next();
        productsBrand = resultSet.getString(1);

        return productsBrand;
    }
}
