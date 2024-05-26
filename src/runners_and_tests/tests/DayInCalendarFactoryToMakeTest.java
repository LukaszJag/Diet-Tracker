package runners_and_tests.tests;

import tools.calendar_tools.DayInCalendar;
import tools.products_tools.Macro;
import tools.products_tools.Product;

import java.sql.Date;

public class DayInCalendarFactoryToMakeTest {
    public static DayInCalendar getExampleDayInCalendar(){
        Macro macro = new Macro(100,100,100,100);
        return new DayInCalendar(Date.valueOf("2024-02-29"), "Monday", "breakfast", 10,
                new Product("Bulbulator", "BulBul", 100,
                        macro, 100, "Test Bul bul"), macro,
                " 2008-11-11 13:23:44", "op",new Macro(1,2,3,4) );
    }
}
