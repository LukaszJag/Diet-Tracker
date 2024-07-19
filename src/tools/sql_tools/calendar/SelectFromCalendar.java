package tools.sql_tools.calendar;

import tools.sql_tools.general.GetResultSet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectFromCalendar {
    public static String[][] selectAllDataFromCalendarTableForDay(String dayDateSQLFriendlyFormat) throws SQLException {
        // Hard code sizes may cause problem
        // 26 row each with 15 fields of data
        String [][] allRows = new String[26][15];

        String sqlStatement = "SELECT * FROM calendar WHERE day_date=" + "'" + dayDateSQLFriendlyFormat + "'" + ";";
        System.out.println(sqlStatement);
        ResultSet resultSet;
        GetResultSet getResultSet = new GetResultSet();
        resultSet = getResultSet.getResultSetFromSQL(sqlStatement);

        int realAmountOfRows = 0;
        int counter = 0;
        while(resultSet.next()){
            if (resultSet.getString(1).equals("null")){
                break;
            }
            for (int i = 0; i < 15; i++) {
                allRows[counter][i] = resultSet.getString(i+1);
            }
            realAmountOfRows++;
            counter++;
        }

        getResultSet.closeResultSet(resultSet);
        getResultSet.closeAllVariables();

        String [][] resultArray = new String[realAmountOfRows][15];

        for (int i = 0; i < realAmountOfRows; i++) {
            for (int j = 0; j < resultArray[i].length; j++) {
                resultArray[i][j] = allRows[i][j];
            }
        }


        return resultArray;
    }
}
