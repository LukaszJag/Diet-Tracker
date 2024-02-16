package sql_tools;

import calendar_tools.DayOneProduct;
import configuration.Config;
import products_tools.Product;

import java.sql.Connection;
import java.sql.SQLException;

public class InsertToCalendarDayTable {
    public static void insertLineToCalendarDayTable(Product product) throws SQLException {
        Connection connection = GetConnection.getConnectionWithLocalHost();
        // String sql = createInsertSQLQueryForProductTable(product);
       // PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // preparedStatement.execute(sql);
    }
    public static String createInsertSQLQueryForCalendarDay(DayOneProduct dayToInsert){
        // Set head of query
        String sqlStatement = "INSERT INTO `diet_tracker_schema`.`calendar_test`\n";
        sqlStatement += "(";

        // Set columns name in query
        for (int i = 0; i < Config.SQK_COLUMNS_FOR_INSERT_INTO_CALENDAR_DAY_TABLE.length; i++) {

            sqlStatement += Config.SQK_COLUMNS_FOR_INSERT_INTO_CALENDAR_DAY_TABLE[i];

            if (i == Config.SQK_COLUMNS_FOR_INSERT_INTO_CALENDAR_DAY_TABLE.length - 1){
                sqlStatement += ")";
            }else {
                sqlStatement += ",\n";
            }
        }

        // Set Values verse
        sqlStatement += "\nValues\n(";

        //String[] productDataInArray = //To write

        /*
        ALL TO REFACTOR
        for (int i = 0; i < Config.howManyParametersToAddProduct; i++) {

            // Take care to float value ends with .f
            if(i == 0 || i == 1) {
                sqlStatement += "'" + productDataInArray[i] + "'";
            }else{
                sqlStatement += productDataInArray[i];
            }

            if (i != Config.SQL_COLUMNS_FOR_INSERT_INTO_PRODUCT_TABLE.length - 1){
                sqlStatement += ",\n";
            }
        }
*/
        sqlStatement += ");";
        return  sqlStatement;
    }
}
