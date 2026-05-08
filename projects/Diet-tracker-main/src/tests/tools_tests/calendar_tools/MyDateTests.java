package tests.tools_tests.calendar_tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tools.calendar_tools.MyDate;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyDateTests {

    @Nested
    class MyDateClassTests {
        Format format;
        java.util.Date utilDateImport;
        String dayName;

        @BeforeEach
        public void setupTestsData() {
            Format format = new SimpleDateFormat("EEEE");
            java.util.Date utilDateImport = new java.util.Date();
            String dayName = format.format(utilDateImport);
        }

        @Nested
        class GetNameOfMethods{
            @Nested
            class GetNameOfMonthContainsInString{
                @Test
                public void noMonthDetected(){
                    assertEquals(-1, MyDate.getNameOfMonthContainsInString("testest-100#@"));
                }
                @Test
                public void oneMonthDetected(){
                    assertEquals(5, MyDate.getNameOfMonthContainsInString("text14MaYtest"));
                }
                @Test
                public void firstInTextMonthDetected(){
                    assertEquals(4, MyDate.getNameOfMonthContainsInString("apriltext14test"));
                }
                @Test
                public void lastInTextMonthDetected(){
                    assertEquals(4, MyDate.getNameOfMonthContainsInString("text14testapril"));
                }
                @Test
                public void twoMonthDetected(){
                    assertEquals(4, MyDate.getNameOfMonthContainsInString("apriltext14testmay"));
                }

                @Test
                public void threeMonthDetected(){
                    assertEquals(4, MyDate.getNameOfMonthContainsInString("apriltext14testmay"));
                }
            }

        }
        @Nested
        class GetDataToCurrentDate {

        @Test
        public void getCurrentDayNameOfDayCapitalizationCaseTest() {
            assertEquals("Sunday", MyDate.getCurrentDayNameOfDayCapitalizationCase());
        }

        @Test
        public void getCurrentDayNameOfDayLowerCaseTest() {
            assertEquals("sunday", MyDate.getCurrentDayNameOfDayLowerCase());
        }
    }
        @Nested
        class GetNextDay {
            @Test
            public void getNextDayDateSQLFriendlyFormatTest1() {
                assertEquals("2025-12-28",
                        MyDate.getNextDayDateSQLFriendlyFormat("2025-12-27"));
            }

            @Test
            public void getNextDayDateSQLFriendlyFormatTest2() {
                assertEquals("2025-12-01",
                        MyDate.getNextDayDateSQLFriendlyFormat("2025-11-30"));
            }

            @Test
            public void getNextDayDateSQLFriendlyFormatTest3() {
                assertEquals("2026-01-01",
                        MyDate.getNextDayDateSQLFriendlyFormat("2025-12-31"));
            }

            @Test
            public void getPreviousDayDateSQLFriendlyFormatTest1() {
                assertEquals("2025-12-26",
                        MyDate.getPreviousDayDateSQLFriendlyFormat("2025-12-27"));
            }

            @Test
            public void getPreviousDayDateSQLFriendlyFormatTest2() {
                assertEquals("2025-11-29",
                        MyDate.getPreviousDayDateSQLFriendlyFormat("2025-11-30"));
            }

            @Test
            public void getPreviousDayDateSQLFriendlyFormatTest3() {
                assertEquals("2025-12-31",
                        MyDate.getPreviousDayDateSQLFriendlyFormat("2026-01-01"));
            }
        }
        @Nested
        class GeneralTimeAndDateOperations{
            @Test
            public void getPreviousMonth(){
                Date dt = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(dt);
                c.add(Calendar.MONTH, -1);
                dt = c.getTime();
                String pattern = "yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date = simpleDateFormat.format(dt);
                System.out.println(date);
            }
        }
    }
}
