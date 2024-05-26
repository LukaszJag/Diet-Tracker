package runners_and_tests.tests;

import tools.calendar_tools.Calendar;

import java.util.Scanner;

public class TestCalendarClass {

    public static void main(String[] args) {

    }

    private void testAddNewProductToCalendar(){
        Calendar.addNewRowToCalendar("2024-02-24", "wednesday", 80, ProductFactoryToMakeTests.productBarExample());
    }

    private void addNewRowToCalendarByInput(){
        Scanner scannerInput = new Scanner(System.in);

    }
}
