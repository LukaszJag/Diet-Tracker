package tests;

import calendar_tools.DayInCalendar;
import org.junit.jupiter.api.Test;
import products_tools.Macro;
import products_tools.Product;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class DayInCalendarTest {

    @Test
    void dayDataInStringArray() {
        Macro macro = new Macro(100,100,100,100);
        DayInCalendar dayInCalendar = new DayInCalendar(Date.valueOf("2024-02-29"), "2024-02-29", "Monday", 10,
                new Product("Bulbulator", "BulBul", 100,
                        macro, 100, "Test Bul bul"), macro,
                " 2008-11-11 13:23:44", "op");
    }

    @Test
    void testDayDataInStringArray() {
    }

    @Test
    void dayDataShowData() {
    }

    @Test
    void dayDataShowDataWithSQLColumns() {
    }
}