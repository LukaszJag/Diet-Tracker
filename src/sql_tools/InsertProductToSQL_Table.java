package sql_tools;

import products_tools.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertProductToSQL_Table {
    public static void insertProduct(Product product) throws SQLException {
        Connection connection = GetConnection.getConnectionWithLocalHost();
        String sql = makeInsertSQLQueryForProduct(product);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute(sql);
    }


    public static String makeInsertSQLQueryForProduct(Product product){
        String insertSQLQuerry =" INSERT INTO `products`.`dietproducts`" +
                "(`Product_Name`,`Product_Brand`,`Package_Has`,`Macro_For`,`Kcal`,`Protein`,`Fat`,`Carbs`)"
        + "VALUES" +"('" + product.getProductName() + "',\"'" + product.getProductBrand() + "`,\"" + product.getProductPackWeight() + "f,\"" + "100.0f,\""
        + product.getProductMacroForItsMeasure().getKcal() +",\"" + product.getProductMacroForItsMeasure().getProtein()+ ",\""
                + product.getProductMacroForItsMeasure().getFat()+ ",\"" + product.getProductMacroForItsMeasure().getCarbs() + ");";
        return insertSQLQuerry;
    }

}
