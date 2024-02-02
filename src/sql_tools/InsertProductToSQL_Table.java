package sql_tools;

import configuration.Config;
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


    public static String createInsertSQLQueryForProductTable(Product productToInsert){
        // Set head of query
        String sqlStatement = "INSERT INTO `diet_tracker_schema`.`product_table`\n";
        sqlStatement += "(";

        // Set columns name in query
        for (int i = 0; i < Config.SQL_COLUMNS_FOR_INSERT_INTO_PRODUCT_TABLE.length; i++) {
            sqlStatement += Config.SQL_COLUMNS_FOR_INSERT_INTO_PRODUCT_TABLE[i] + ",\n";
        }

        // Set Values verse
        sqlStatement += "Values\n(";

        for (int i = 0; i < ; i++) {
            
        }
                
        return  sqlStatement;
    }

    public static String makeInsertSQLQueryForProduct(Product product){
        String insertSQLQuerry =" INSERT INTO `products`.`dietproducts`" +
                "(`Product_Name`,`Product_Brand`,`Package_Has`,`Macro_For`,`Kcal`,`Protein`,`Fat`,`Carbs`)"
        + "VALUES" +"('" + product.getProductName() + "',\"'" + product.getProductBrand() + "`,\"" + product.getProductPackWeight() + "f,\"" + "100.0f,\""
        + product.getProductMacroForItsSetMeasure().getKcal() +",\"" + product.getProductMacroForItsSetMeasure().getProtein()+ ",\""
                + product.getProductMacroForItsSetMeasure().getFat()+ ",\"" + product.getProductMacroForItsSetMeasure().getCarbs() + ");";
        return insertSQLQuerry;
    }

}
