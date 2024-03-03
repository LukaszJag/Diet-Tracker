package runners_and_tests;

import calendar_tools.DayInCalendar;
import products_tools.Macro;
import products_tools.Product;

import java.sql.Date;

public class DayFactoryToMakeTest {
    public static DayInCalendar getExampleDayInCalendar(){
        Macro macro = new Macro(100,100,100,100);
        return new DayInCalendar(Date.valueOf("2024-02-29"), "2024-02-29", "Monday", 10,
                new Product("Bulbulator", "BulBul",
                        100,macro, 100), macro, " 2008-11-11 13:23:44", "op");
    }
}
