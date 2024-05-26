package runners_and_tests.tests;

import tools.sql_tools.calendar.ExportAllDaysDataToSQLCalendar;

public class TestExportAllDaysDataToSQLCalendar {
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        ExportAllDaysDataToSQLCalendar.exportDataFromTxtToSQLCalendarTable();
    }
}
