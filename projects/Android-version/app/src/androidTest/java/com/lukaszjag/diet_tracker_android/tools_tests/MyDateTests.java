package com.lukaszjag.diet_tracker_android.tools_tests;

import static org.junit.Assert.assertEquals;

import com.lukaszjag.diet_tracker_android.tools.date_tools.MyDate;

import org.junit.Test;

public class MyDateTests {
    @Test
    public void getDayNameInLowerCase(){
        assertEquals("monday", MyDate.getDayNameInLowerCase("2026-07-20"));
    }
}
