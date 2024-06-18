package runners_and_tests.tests;

import runners_and_tests.tests.get_simple_data_to_test.DayInCalendarFactoryToMakeTest;
import runners_and_tests.tests.get_simple_data_to_test.ProductFactoryToMakeTests;
import tools.calendar_tools.DayInCalendar;
import tools.sql_tools.calendar.InsertToCalendarDayTable;
import tools.sql_tools.SearchForKey;

import java.sql.Date;
import java.sql.SQLException;

public class TestDayInCalendar {
    public static void main(String[] args) throws SQLException {
        //test1();
        //test2();
        //test3();
        //test4();
        //test5();
    }

    private static void test1(){
        DayInCalendar dayInCalendar = new DayInCalendar(Date.valueOf("2024-02-29"), "wensday", 80, ProductFactoryToMakeTests.productBarExample(),
                ProductFactoryToMakeTests.productBarExample().getProductMacroForItsSetMeasure(),"", "");
        System.out.println(InsertToCalendarDayTable.createInsertSQLQueryForCalendarDay(dayInCalendar));
    }

    private static void test2(){
        System.out.println(InsertToCalendarDayTable.createInsertSQLQueryForCalendarDay(DayInCalendarFactoryToMakeTest.getExampleDayInCalendar()));
    }

    private static void test3(){
        String[] dayDataInStringArray = DayInCalendarFactoryToMakeTest.getExampleDayInCalendar().dayDataInStringArray(DayInCalendarFactoryToMakeTest.getExampleDayInCalendar());
        for (int i = 0; i < dayDataInStringArray.length; i++) {
            System.out.println("[" + i + "]: " + dayDataInStringArray[i]);
        }
        System.out.println("Length of array: " + dayDataInStringArray.length);
    }

    private static void test4() throws SQLException {
        InsertToCalendarDayTable.addRowToCalendarTable(DayInCalendarFactoryToMakeTest.getExampleDayInCalendar());
    }

    private static void test5() throws SQLException {
        SearchForKey.searchForKeyInCalendarTable("Bar");
        SearchForKey.searchForKeyInCalendarTable("Rower");

    }
}
