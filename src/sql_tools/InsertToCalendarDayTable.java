package sql_tools;

import calendar_tools.DayInCalendar;
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
    public static String createInsertSQLQueryForCalendarDay(DayInCalendar dayToInsert){
        // Set head of query
        String sqlStatement = "INSERT INTO `diet_tracker_schema`.`calendar_test`\n";
        sqlStatement += "(";

        // Set columns name in query
        for (int i = 0; i < Config.SQL_COLUMNS_CALENDAR.length; i++) {

            sqlStatement += Config.SQL_COLUMNS_CALENDAR[i];

            if (i == Config.SQL_COLUMNS_CALENDAR.length - 1){
                sqlStatement += ")";
            }else {
                sqlStatement += ",\n";
            }
        }

        // Set Values verse
        sqlStatement += "\nValues\n(";
        String[] dayDataInArray = dayToInsert.dayDataInStringArray(dayToInsert);

        for (int i = 0; i < Config.SQL_COLUMNS_CALENDAR.length; i++) {

            // Take care to float value ends with .f
            if(i == 0 || i == 1 || i == 3) {
                sqlStatement += "'" + dayDataInArray[i] + "'";
            }else{
                sqlStatement += dayDataInArray[i];
            }

            if (i != Config.SQL_COLUMNS_CALENDAR.length - 1){
                sqlStatement += ",\n";
            }
        }
        sqlStatement += ");";
        return  sqlStatement;
    }

}
