package runners_and_tests;

import calendar_tools.Calendar;

public class TestCalendarClass {

    public static void main(String[] args) {
        Calendar.addNewProductToCalendar("2024-02-24", "wednesday", 80, ProductFactoryToMakeTests.productBarExample());
    }
}
