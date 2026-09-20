package tools.sql_tools.calendar;

import tools.calendar_tools.DayInCalendar;
import configuration.Config;
import tools.sql_tools.general.get.GetConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertToCalendarDayTable {

    // Keep original method for compatibility
    public static void addRowToCalendarTable(DayInCalendar dayInCalendar) throws SQLException {
        addRowToCalendarTable(dayInCalendar, java.util.UUID.randomUUID().toString());
    }

    // NEW Overloaded Method: Inserts locally with rowId and sets is_synced = 0
    public static void addRowToCalendarTable(DayInCalendar dayInCalendar, String rowId) throws SQLException {
        String sqlStatement = createInsertSQLQueryForCalendarDay(dayInCalendar, rowId, true);
        try (Connection connection = GetConnection.getConnectionWithLocalHost();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.execute();
        }
    }

    // Keep original method for compatibility
    public static String createInsertSQLQueryForCalendarDay(DayInCalendar dayToInsert) {
        return createInsertSQLQueryForCalendarDay(dayToInsert, java.util.UUID.randomUUID().toString(), false);
    }

    // NEW Overloaded Method: Dynamically appends row_id and is_synced
    public static String createInsertSQLQueryForCalendarDay(DayInCalendar dayToInsert, String rowId, boolean includeIsSynced) {
        String sqlStatement = "INSERT INTO `diet_tracker_schema`." + "`" + Config.CURRENT_DATABASE_TABLE_CALENDAR + "`\n";
        sqlStatement += "(";

        for (int i = 0; i < Config.SQL_COLUMNS_CALENDAR.length; i++) {
            sqlStatement += "`" + Config.SQL_COLUMNS_CALENDAR[i] + "`,\n";
        }

        sqlStatement += "`row_id`";
        if (includeIsSynced) {
            sqlStatement += ",\n`is_synced`";
        }
        sqlStatement += ")";

        sqlStatement += "\nValues\n(";
        String[] dayDataInArray = dayToInsert.dayDataInStringArray(dayToInsert);

        for (int i = 0; i < Config.SQL_COLUMNS_CALENDAR.length; i++) {
            if (i <= 10) {
                if (i == 0 || i == 1 || i == 2 || i == 4 || i == 9 || i == 10) {
                    sqlStatement += "'" + dayDataInArray[i] + "'";
                } else {
                    sqlStatement += dayDataInArray[i];
                }
            } else {
                if (i == 11) {
                    sqlStatement += String.valueOf(dayToInsert.getConsumedMacro().getKcal());
                }
                if (i == 12) {
                    sqlStatement += String.valueOf(dayToInsert.getConsumedMacro().getCarbs());
                }
                if (i == 13) {
                    sqlStatement += String.valueOf(dayToInsert.getConsumedMacro().getFat());
                }
                if (i == 14) {
                    sqlStatement += String.valueOf(dayToInsert.getConsumedMacro().getProtein());
                }
            }
            sqlStatement += ",\n";
        }

        sqlStatement += "'" + rowId + "'";
        if (includeIsSynced) {
            sqlStatement += ",\n0"; // 0 = Unsynced in Local MySQL
        }
        sqlStatement += ");";

        return sqlStatement;
    }
}