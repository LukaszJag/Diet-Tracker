package runners_and_tests.run_export_inport_data;

import tools.sql_tools.calendar.ExportAllDaysDataToSQLCalendar;

public class RunnerExportAllDaysDataToSQLCalendar {
    public static void main(String[] args) {
        ExportAllDaysDataToSQLCalendar.exportDataFromTxtToSQLCalendarTable();
    }
}
