package SQL_Tools;

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
        + "VALUES" +"('" + product.getName() + "',\"'" + product.getBrand() + "`,\"" + product.getWeight_of_pack() + "f,\"" + "100.0f,\""
        + product.getProduct_macro().getKcal() +",\"" + product.getProduct_macro().getProtein()+ ",\""
                + product.getProduct_macro().getFat()+ ",\"" + product.getProduct_macro().getCarbs() + ");";
        return insertSQLQuerry;
    }

}
