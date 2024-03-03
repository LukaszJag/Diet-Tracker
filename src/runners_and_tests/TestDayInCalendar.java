package runners_and_tests;

import calendar_tools.DayInCalendar;
import sql_tools.InsertToCalendarDayTable;
import sql_tools.SearchForKey;

import java.sql.Date;
import java.sql.SQLException;

public class TestDayInCalendar {
    public static void main(String[] args) {
        try {
            test5();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void test1(){
        DayInCalendar dayInCalendar = new DayInCalendar(Date.valueOf("2024-02-29"),"2024-02-21", "wensday", 80, ProductFactoryToMakeTests.productBarExample(),
                ProductFactoryToMakeTests.productBarExample().getProductMacroForItsSetMeasure(),"", "");
        System.out.println(InsertToCalendarDayTable.createInsertSQLQueryForCalendarDay(dayInCalendar));
    }

    private static void test2(){
        System.out.println(InsertToCalendarDayTable.createInsertSQLQueryForCalendarDay(DayFactoryToMakeTest.getExampleDayInCalendar()));
    }

    private static void test3(){
        String[] dayDataInStringArray = DayFactoryToMakeTest.getExampleDayInCalendar().dayDataInStringArray(DayFactoryToMakeTest.getExampleDayInCalendar());
        for (int i = 0; i < dayDataInStringArray.length; i++) {
            System.out.println("[" + i + "]: " + dayDataInStringArray[i]);
        }
        System.out.println("Length of array: " + dayDataInStringArray.length);
    }

    private static void test4() throws SQLException {
        InsertToCalendarDayTable.addRowToCalendarTable(DayFactoryToMakeTest.getExampleDayInCalendar());
    }

    private static void test5() throws SQLException {
        SearchForKey.searchForKeyInCalendarTable("Bar");
        SearchForKey.searchForKeyInCalendarTable("Rower");

    }
}
