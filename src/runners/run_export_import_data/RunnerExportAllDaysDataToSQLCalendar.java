package runners.run_export_import_data;

import tools.sql_tools.calendar.ExportAllDaysDataToSQLCalendar;

public class RunnerExportAllDaysDataToSQLCalendar {
    public static void main(String[] args) {
        ExportAllDaysDataToSQLCalendar.exportDataFromTxtToSQLCalendarTable();
    }
}
