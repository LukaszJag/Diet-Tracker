package tests.tools_tests.calendar_tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.calendar_tools.MyDate.getAmountOfDaysInMonthIfContainsItsName;

public class MyDateTests2 {
    @Test
    public void getNumberOfMonthInYearIfStringContainsItsNameTest_1(){
        assertEquals(31, getAmountOfDaysInMonthIfContainsItsName("214e12january2121"));
    }

    @Test
    public void getNumberOfMonthInYearIfStringContainsItsNameTest_2(){
        assertEquals(-1, getAmountOfDaysInMonthIfContainsItsName("214e12jan142ary2121"));
    }

    @Test
    public void getNumberOfMonthInYearIfStringContainsItsNameTest_3(){
        assertEquals(31, getAmountOfDaysInMonthIfContainsItsName("MAY"));
    }
    @Test
    public void getNumberOfMonthInYearIfStringContainsItsNameTest_4(){
        assertEquals(31, getAmountOfDaysInMonthIfContainsItsName("MaY"));
    }

    @Test
    public void getNumberOfMonthInYearIfStringContainsItsNameTest_5(){
        assertEquals(-1, getAmountOfDaysInMonthIfContainsItsName(""));
    }
    // TODO - 14.12.25
/*
    @Test
    public void getNumberOfMonthInYearIfStringContainsItsNameTest_6(){
        assertEquals(31, getAmountOfDaysInMonthIfContainsItsName("February_june_november"));
    }
    */
}
