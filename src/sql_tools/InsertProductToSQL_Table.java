package sql_tools;

import configuration.Config;
import products_tools.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertProductToSQL_Table {
    public static void insertProduct(Product product) throws SQLException {
        Connection connection = GetConnection.getConnectionWithLocalHost();
        String sql = createInsertSQLQueryForProductTable(product);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute(sql);
    }


    public static String createInsertSQLQueryForProductTable(Product productToInsert){
        // Set head of query
        String sqlStatement = "INSERT INTO `diet_tracker_schema`." + Config.CURRENT_DATABASE_TABLE_CALENDAR + "\n";
        sqlStatement += "(";

        // Set columns name in query
        for (int i = 0; i < Config.SQL_COLUMNS_FOR_INSERT_INTO_PRODUCT_TABLE.length; i++) {

            sqlStatement += Config.SQL_COLUMNS_FOR_INSERT_INTO_PRODUCT_TABLE[i];

            if (i == Config.SQL_COLUMNS_FOR_INSERT_INTO_PRODUCT_TABLE.length - 1){
                sqlStatement += ")";
            }else {
                sqlStatement += ",\n";
            }
        }

        // Set Values verse
        sqlStatement += "\nValues\n(";

        String[] productDataInArray = productToInsert.productDataInStringArray(productToInsert);
        // Useless loop
        for (int i = 0; i < Config.howManyParametersToAddProduct; i++) {

            // Take care to float value ends with .f
            if(i == 0 || i == 1 || i == 8) {
                sqlStatement += "'" + productDataInArray[i] + "'";
            }else{
                sqlStatement += productDataInArray[i];
            }

            if (i != Config.SQL_COLUMNS_FOR_INSERT_INTO_PRODUCT_TABLE.length - 1){
                sqlStatement += ",\n";
            }
        }

        sqlStatement += ");";
        return  sqlStatement;
    }
}
