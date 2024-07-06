package tools.raports_tools;

import configuration.Config;
import tools.sql_tools.general.GetConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetDayStatistics {
    public static void getDataForDay(String dateInSQLFriendlyFormat) throws SQLException {
        String sqlQueryToGetDataFromCalendarTable = "SELECT * FROM calendar WHERE day_date = " + dateInSQLFriendlyFormat + ";";
        String sqlQueryToGetDataFromDaysStatisticsTable = "SELECT * FROM days_statistics_test WHERE day_date = " + dateInSQLFriendlyFormat + ";";

        ResultSet resultSet;
        Statement statement;
        Connection connection = GetConnection.getConnectionWithLocalHost();
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sqlQueryToGetDataFromDaysStatisticsTable);

        while(resultSet.next()){
            for (int i = 0; i < Config.SQL_COLUMNS_PRODUCT.length; i++) {
                //result[i] = resultSet.getString(Config.SQL_COLUMNS_PRODUCT[i].replace("`",""));
            }
        }
    }
}
