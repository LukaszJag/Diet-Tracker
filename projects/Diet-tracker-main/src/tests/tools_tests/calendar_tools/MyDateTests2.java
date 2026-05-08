package tests.tools_tests.calendar_tools;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tools.calendar_tools.MyDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.calendar_tools.MyDate.getAmountOfDaysInMonthIfContainsItsName;

public class MyDateTests2 {

    @Nested
    class GetDateInFormat {
        @Nested
        class GetPreviousDateFRomMontAndYear {
            @Test
            public void getPreviousDateFromMontAndYearSQLFriendlyFormatTest_1() {
                String result = MyDate.getPreviousDateFromMontAndYearSQLFriendlyFormat("11", "2025");
                assertEquals("2025-10", result);
            }

            @Test
            public void getPreviousDateFromMontAndYearSQLFriendlyFormatTest_2() {
                String result = MyDate.getPreviousDateFromMontAndYearSQLFriendlyFormat("09", "2025");
                assertEquals("2025-08", result);
            }

            @Test
            public void getPreviousDateFromMontAndYearSQLFriendlyFormatTest_3() {
                String result = MyDate.getPreviousDateFromMontAndYearSQLFriendlyFormat("01", "2025");
                assertEquals("2024-12", result);
            }

            @Test
            public void getPreviousDateFromMontAndYearSQLFriendlyFormatTest_4() {
                String result = MyDate.getPreviousDateFromMontAndYearSQLFriendlyFormat(11, 2025);
                assertEquals("2025-10", result);
            }

            @Test
            public void getPreviousDateFromMontAndYearSQLFriendlyFormatTest_5() {
                String result = MyDate.getPreviousDateFromMontAndYearSQLFriendlyFormat(9, 2025);
                assertEquals("2025-08", result);
            }

            @Test
            public void getPreviousDateFromMontAndYearSQLFriendlyFormatTest_6() {
                String result = MyDate.getPreviousDateFromMontAndYearSQLFriendlyFormat(1, 2025);
                assertEquals("2024-12", result);
            }

        }

        @Nested
        class GetNextDateFromMontAndYearSQLFriendlyFormat{
            @Test
            public void getNextDateFromMontAndYearSQLFriendlyFormat_Test1(){
                assertEquals("2026-02", MyDate.getNextDateFromMontAndYearSQLFriendlyFormat(1, 2026));
            }
        }
    }

    @Nested
    class GetAmountOfDaysInMonthIfContainsItsName {
        @Test
        public void getNumberOfMonthInYearIfStringContainsItsNameTest_1() {
            assertEquals(31, getAmountOfDaysInMonthIfContainsItsName("214e12january2121"));
        }

        @Test
        public void getNumberOfMonthInYearIfStringContainsItsNameTest_2() {
            assertEquals(-1, getAmountOfDaysInMonthIfContainsItsName("214e12jan142ary2121"));
        }

        @Test
        public void getNumberOfMonthInYearIfStringContainsItsNameTest_3() {
            assertEquals(31, getAmountOfDaysInMonthIfContainsItsName("MAY"));
        }

        @Test
        public void getNumberOfMonthInYearIfStringContainsItsNameTest_4() {
            assertEquals(31, getAmountOfDaysInMonthIfContainsItsName("MaY"));
        }

        @Test
        public void getNumberOfMonthInYearIfStringContainsItsNameTest_5() {
            assertEquals(-1, getAmountOfDaysInMonthIfContainsItsName(""));
        }
    }

    @Nested
    class GetDateFrom {
        @Nested
        class GetYearFromSQLFriendlyFormatDate {
            @Test
            public void getYearFromSQLFriendlyFormatDateTest_1() {
                assertEquals("2025", MyDate.getYearFromSQLFriendlyFormatDate("2025-01-11"));
                assertEquals("2024", MyDate.getYearFromSQLFriendlyFormatDate("2024-01-11"));
                assertEquals("2024", MyDate.getYearFromSQLFriendlyFormatDate("2024"));
            }

            @Test
            public void getYearFromSQLFriendlyFormatDateToIntTest_1() {
                assertEquals(2025, MyDate.getYearFromSQLFriendlyFormatDateToInt("2025-01-11"));
                assertEquals(2024, MyDate.getYearFromSQLFriendlyFormatDateToInt("2024-01-11"));
                assertEquals(2024, MyDate.getYearFromSQLFriendlyFormatDateToInt("2024"));
            }
        }

        @Nested
        class GetMonthFromSQLFriendlyFormatDate {
            @Test
            public void getMonthFromSQLFriendlyFormatDate_Test_1() {
                assertEquals("01", MyDate.getMonthFromSQLFriendlyFormatDate("2022-01-11"));
                assertEquals("12", MyDate.getMonthFromSQLFriendlyFormatDate("2022-12-11"));
            }

            @Test
            public void getMonthFromSQLFriendlyFormatDateToInt() {
                assertEquals(1, MyDate.getMonthFromSQLFriendlyFormatDateToInt("2022-01-11"));
                assertEquals(12, MyDate.getMonthFromSQLFriendlyFormatDateToInt("2022-12-11"));
            }
        }
    }

    @Nested
    class GetNameOf{
        @Test
        public void getNextDay(){
            String result = MyDate.getNextDayDateName("Monday");
                assertEquals("Tuesday", result);
        }

        @Test
        public void getNextDay2(){
            String result = MyDate.getNextDayDateName("Sunday");
            assertEquals("Monday", result);
        }
    }


    // TODO - 14.12.25
/*
    @Test
    public void getNumberOfMonthInYearIfStringContainsItsNameTest_6(){
        assertEquals(31, getAmountOfDaysInMonthIfContainsItsName("February_june_november"));
    }
    */
}
