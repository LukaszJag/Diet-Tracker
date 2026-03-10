package tools.sql_tools.general;

import tools.sql_tools.general.get.GetConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLCount {

    public static int getCountFromTable(String tableName, String dayDate) {

        ResultSet resultSet;
        Statement statement;

        int countValue = -1;

        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE day_date = " +  "\"" + dayDate + "\"" + ";";
        System.out.println(sql);
        String result = "";


        Connection connection = null;
        try {

            connection = GetConnection.getConnectionWithLocalHost();

        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            countValue = Integer.valueOf(resultSet.getString(1));
        }

        resultSet.close();
        statement.close();
        connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return countValue;
    }
}
