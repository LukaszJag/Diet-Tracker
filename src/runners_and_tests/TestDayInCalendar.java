package runners_and_tests;

import calendar_tools.DayInCalendar;
import sql_tools.InsertToCalendarDayTable;

public class TestDayInCalendar {
    public static void main(String[] args) {
        DayInCalendar dayInCalendar = new DayInCalendar(null,"2024-02-21", "wensday", 80, ProductFactoryToMakeTests.productBarExample(),
                ProductFactoryToMakeTests.productBarExample().getProductMacroForItsSetMeasure(),null, null);
        System.out.println(InsertToCalendarDayTable.createInsertSQLQueryForCalendarDay(dayInCalendar));
    }
}
