package sql_tools;

import java.sql.*;

public class CheckIfRowExist {
    public static boolean isProductNameExistInProductTable(String productName) throws SQLException {
        Connection connection = GetConnection.getConnectionWithLocalHost();
        ResultSet resultSet;
        Statement statement;
        PreparedStatement preparedStatement;

        String sqlStatement = "SELECT COUNT(product_name) AS bool\n" +
                "FROM product_table\n" +
                "WHERE product_name = '" + productName  + "';";

        statement = connection.createStatement();
        resultSet = statement.executeQuery(sqlStatement);
        resultSet.next();

        int result = resultSet.getInt("bool");

        if (result == 1) {
            return true;
        }else{
            return false;
        }
    }

    public static boolean isCalendarRowExistInProductTable(String dayDate, String productName, String productAmount, String timeOptional) throws SQLException {
        Connection connection = GetConnection.getConnectionWithLocalHost();
        ResultSet resultSet;
        Statement statement;
        PreparedStatement preparedStatement;

        String sqlStatement = "SELECT COUNT(product_name) AS bool\n" +
        "FROM calendar\n" +
        "WHERE day_date =\'" + dayDate + "\'  && " +
        "product_name = \'" + productName + "\' && " +
        "amount_of_product = \'" + productAmount + "\' && " +
        "time_optional = \'" + timeOptional + "\';";

        statement = connection.createStatement();
        resultSet = statement.executeQuery(sqlStatement);
        resultSet.next();

        int result = resultSet.getInt("bool");

        if (result == 1) {
            return true;
        }else{
            return false;
        }
    }
}
