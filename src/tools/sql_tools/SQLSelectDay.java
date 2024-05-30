package tools.sql_tools;

import configuration.Config;
import tools.sql_tools.general.GetConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLSelectDay {
    public static String[] getAllRowFromDay(int day, int month, int year) throws SQLException {
        String [] rowInTable = new String[30];

        String date = String.valueOf(year);

        if (String.valueOf(month).length() == 1){
            date += "-0" + String.valueOf(month) + "-";
        }else{
            date += String.valueOf(month) + "-";
        }

        if (String.valueOf(day).length() == 1){
            date += "-0" + String.valueOf(day);
        }else{
            date += String.valueOf(day);
        }

        ResultSet resultSet;
        Statement statement;
        String sql = "SELECT * FROM calendar WHERE day_date = '" + date + "';";

        Connection connection = GetConnection.getConnectionWithLocalHost();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        int counter = 0;
        String lineToArray = "";
        while(resultSet.next()) {
            for (int i = 0; i < Config.SQL_COLUMNS_CALENDAR.length; i++) {
                lineToArray += resultSet.getString(i+1) + "::";
            }
            rowInTable[counter] = lineToArray;
            lineToArray="";
            counter++;
        }

        return rowInTable;
    }
}
