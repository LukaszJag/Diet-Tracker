package sql_tools;

import calendar_tools.DayInCalendar;
import configuration.Config;
import products_tools.Macro;
import products_tools.Product;
import text_files_tools.FilesTools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class InsertToCalendarDayTable {

    public static void addRowToCalendarTable(DayInCalendar dayInCalendar) throws SQLException {
        Connection connection = GetConnection.getConnectionWithLocalHost();
        String sqlStatement = createInsertSQLQueryForCalendarDay(dayInCalendar);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.execute(sqlStatement);
    }

    public static String createInsertSQLQueryForCalendarDay(DayInCalendar dayToInsert) {
        // Set head of query
        String sqlStatement = "INSERT INTO `diet_tracker_schema`." + Config.CURRENT_DATABASE_TABLE_CALENDAR + "\n";
        sqlStatement += "(";

        // Set columns name in query
        for (int i = 0; i < Config.SQL_COLUMNS_CALENDAR.length; i++) {

            sqlStatement += "`" + Config.SQL_COLUMNS_CALENDAR[i] + "`";

            if (i == Config.SQL_COLUMNS_CALENDAR.length - 1) {
                sqlStatement += ")";
            } else {
                sqlStatement += ",\n";
            }
        }

        // Set Values verse
        sqlStatement += "\nValues\n(";
        String[] dayDataInArray = dayToInsert.dayDataInStringArray(dayToInsert);

        for (int i = 0; i < Config.SQL_COLUMNS_CALENDAR.length; i++) {

            // Take care to float value ends with .f
            if (i <= 9) {

                if (i == 0 || i == 1 || i == 3 || i == 8 || i == 9) {
                    sqlStatement += "'" + dayDataInArray[i] + "'";
                } else {
                    sqlStatement += dayDataInArray[i];
                }
            }else {
                // hard code manual add consume macro
                if (i == 10) {
                    sqlStatement += String.valueOf(dayToInsert.getConsumedMacro().getKcal());
                }
                if (i == 11) {
                    sqlStatement += String.valueOf(dayToInsert.getConsumedMacro().getCarbs());
                }
                if (i == 12) {
                    sqlStatement += String.valueOf(dayToInsert.getConsumedMacro().getFat());
                }
                if (i == 13) {
                    sqlStatement += String.valueOf(dayToInsert.getConsumedMacro().getProtein());
                }
            }

            if (i != Config.SQL_COLUMNS_CALENDAR.length - 1) {
                sqlStatement += ",\n";
            } else {
                //sqlStatement += "\n";
            }
        }
        sqlStatement += ");";

        System.out.println();
        System.out.println("InsertToCalendarDayTable -> createInsertSQLQueryForCalendarDay");
        System.out.println("SQL Statement: " + sqlStatement);

        return sqlStatement;
    }
}
