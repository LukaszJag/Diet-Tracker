package sql_tools;

import configuration.Config;

import java.sql.Connection;
import java.sql.SQLException;

public class GenerateSLQTableForDaysStatistics {
    public static void generateDaysInTable() throws SQLException {
        Connection connection = GetConnection.getConnectionWithLocalHost();
        // String sqlStatement = createInsertSQLQueryForCalendarDay(dayInCalendar);

        // PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        // preparedStatement.execute(sqlStatement);
    }

    public static String createInsertSQLQueryForDaysStatistics() {
        String sqlStatement = "INSERT INTO `diet_tracker_schema`." + "`" + "days_statistics_test" + "`\n";
        sqlStatement += "(";

        for (int i = 0; i < Config.SQL_COLUMNS_DAYS_STATISTICS_TEST.length; i++) {

            sqlStatement += "`" + Config.SQL_COLUMNS_DAYS_STATISTICS_TEST[i] + "`";

            if (i == Config.SQL_COLUMNS_CALENDAR.length - 1) {
                sqlStatement += ")";
            } else {
                sqlStatement += ",\n";
            }
        }
        sqlStatement += "\nValues\n(";
        String[] dataForDaysStatistics = {"2001-01-20", "0", "0", "0", "0", "0", "0", "Monday"};
        String dateInString = "2001-01-20";
        String dayNameInString = "Monday";
        //dayNameInString = new SimpleDateFormat("dd-MM-yyyy").format(Config.date) + " " + dayNameCurrentDateOnStartWindow;

        for (int i = 0; i < Config.SQL_COLUMNS_CALENDAR.length; i++) {

            // Take care to float value ends with .f
            if (i == 0 || i == 7) {
                sqlStatement += "'" + dataForDaysStatistics[i] + "'";
            } else {
                sqlStatement += dataForDaysStatistics[i];
            }

            if (i != Config.SQL_COLUMNS_CALENDAR.length - 1) {
                sqlStatement += ",\n";
            } else {
                sqlStatement += "\n";
            }
        }

        sqlStatement += ");";

        return sqlStatement;
    }
}
