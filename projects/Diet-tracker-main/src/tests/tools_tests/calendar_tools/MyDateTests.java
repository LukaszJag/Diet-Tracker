package tests.tools_tests.calendar_tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tools.calendar_tools.MyDate;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static tools.calendar_tools.MyDate.getAmountOfDaysInMonthIfContainsItsName;

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
        class GetNameOfMethods {
            @Nested
            class GetNameOfMonthContainsInString {
                @Test
                public void noMonthDetected() {
                    assertEquals(-1, MyDate.getNameOfMonthContainsInString("testest-100#@"));
                }

                @Test
                public void oneMonthDetected() {
                    assertEquals(5, MyDate.getNameOfMonthContainsInString("text14MaYtest"));
                }

                @Test
                public void firstInTextMonthDetected() {
                    assertEquals(4, MyDate.getNameOfMonthContainsInString("apriltext14test"));
                }

                @Test
                public void lastInTextMonthDetected() {
                    assertEquals(4, MyDate.getNameOfMonthContainsInString("text14testapril"));
                }

                @Test
                public void twoMonthDetected() {
                    assertEquals(4, MyDate.getNameOfMonthContainsInString("apriltext14testmay"));
                }

                @Test
                public void threeMonthDetected() {
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
        class GeneralTimeAndDateOperations {
            @Test
            public void getPreviousMonth() {
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
            class GetNextDateFromMontAndYearSQLFriendlyFormat {
                @Test
                public void getNextDateFromMontAndYearSQLFriendlyFormat_Test1() {
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
        class GetNameOf {
            @Test
            public void getNextDay() {
                String result = MyDate.getNextDayDateName("Monday");
                assertEquals("Tuesday", result);
            }

            @Test
            public void getNextDay2() {
                String result = MyDate.getNextDayDateName("Sunday");
                assertEquals("Monday", result);
            }
        }

        @Nested
        class CheckSQLFormat {
            @Test
            public void checkTrueCorrectness1() {
                assertTrue(MyDate.checkSQLFormat("2026-01-20"));
            }

            @Test
            public void checkTrueCorrectness2() {
                assertTrue(MyDate.checkSQLFormat("2028-01-21"));
            }

            @Test
            public void checkTrueCorrectness3() {
                assertTrue(MyDate.checkSQLFormat("2025-01-22"));
            }

            @Test
            public void checkTrueCorrectness4() {
                assertTrue(MyDate.checkSQLFormat("1996-01-20"));
            }

            @Test
            public void checkFalseCorrectness1() {
                assertFalse(MyDate.checkSQLFormat("0206-01-20"));
            }

            @Test
            public void checkFalseCorrectness2() {
                assertFalse(MyDate.checkSQLFormat("2026-01-35"));
            }

            @Test
            public void checkFalseCorrectness3() {
                assertFalse(MyDate.checkSQLFormat("2026-02-29"));
            }

            @Test
            public void checkFalseCorrectness4() {
                assertFalse(MyDate.checkSQLFormat("2026-00-20"));
            }

            @Test
            public void checkFalseCorrectness5() {
                assertFalse(MyDate.checkSQLFormat("2026-01-00"));
            }

            @Test
            public void checkFalseCorrectness6() {
                assertFalse(MyDate.checkSQLFormat(""));
            }

            @Test
            public void checkFalseCorrectness7() {
                assertFalse(MyDate.checkSQLFormat("a2026-01-22abc"));
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


}
