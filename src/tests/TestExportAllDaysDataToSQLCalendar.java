package tests;

import sql_tools.ExportAllDaysDataToSQLCalendar;

public class TestExportAllDaysDataToSQLCalendar {
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        ExportAllDaysDataToSQLCalendar.exportDataFromTxtToSQLCalendarTable();
    }
}
