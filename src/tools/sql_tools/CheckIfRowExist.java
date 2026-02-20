package tools.sql_tools;

import tools.sql_tools.general.get.GetConnection;

import java.sql.*;

public class CheckIfRowExist {
    Connection connection;
    ResultSet resultSet;
    Statement statement;

    public CheckIfRowExist(){
        try {
            connection = GetConnection.getConnectionWithLocalHost();
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isProductNameExistInProductTable(String productName) throws SQLException {
        Connection connection = GetConnection.getConnectionWithLocalHost();
        ResultSet resultSet;
        Statement statement;
        PreparedStatement preparedStatement;

        String sqlStatement = "SELECT COUNT(product_name) AS bool\n" +
                "FROM product_table\n" +
                "WHERE product_name = '" + productName + "';";

        statement = connection.createStatement();
        resultSet = statement.executeQuery(sqlStatement);
        resultSet.next();

        int result = resultSet.getInt("bool");

        // Close unnecessary connections
        connection.close();
        resultSet.close();

        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCalendarRowExistInProductTable(String dayDate, String productName, String productAmount) throws SQLException {
        Connection connection = GetConnection.getConnectionWithLocalHost();
        ResultSet resultSet;
        Statement statement;

        String sqlStatement = "SELECT COUNT(product_name) AS bool\n" +
                "FROM calendar\n" +
                "WHERE day_date =\'" + dayDate + "\'  && " +
                "product_name = \'" + productName + "\' && " +
                "amount_of_product = \'" + productAmount + "\'" + ";";

        statement = connection.createStatement();
        resultSet = statement.executeQuery(sqlStatement);
        resultSet.next();

        int result = resultSet.getInt("bool");

        // Close unnecessary connections
        connection.close();
        resultSet.close();
        statement.close();

        if (result >= 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCalendarRowExistInProductTable_local_variables(String dayDate, String productName, String productAmount) throws SQLException {
        String sqlStatement = "SELECT COUNT(product_name) AS bool\n" +
                "FROM calendar\n" +
                "WHERE day_date =\'" + dayDate + "\'  && " +
                "product_name = \'" + productName + "\' && " +
                "amount_of_product = \'" + productAmount + "\'" + ";";

        resultSet = statement.executeQuery(sqlStatement);
        resultSet.next();

        int result = resultSet.getInt("bool");

        // Close unnecessary connections
        resultSet.close();

        if (result >= 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isDaysStatisticRowExistInTableCheckByDate(String day_date) throws SQLException {
        Connection connection = GetConnection.getConnectionWithLocalHost();
        ResultSet resultSet;
        Statement statement;
        PreparedStatement preparedStatement;

        String sqlStatement = "SELECT COUNT(day_date) AS bool\n" +
                "FROM days_statistics_test\n" +
                "WHERE day_date = '" + day_date + "';";

        statement = connection.createStatement();
        resultSet = statement.executeQuery(sqlStatement);
        resultSet.next();

        int result = resultSet.getInt("bool");

        // Close unnecessary connections
        connection.close();
        resultSet.close();

        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }
}
