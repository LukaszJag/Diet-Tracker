package tests.tools_tests.calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tools.calendar_tools.MyDate;

import java.text.Format;
import java.text.SimpleDateFormat;

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

        @Test
        public void getCurrentDayNameOfDayCapitalizationCaseTest() {
            Assertions.assertEquals("Sunday", MyDate.getCurrentDayNameOfDayCapitalizationCase());
        }

        @Test
        public void getCurrentDayNameOfDayLowerCaseTest() {
            Assertions.assertEquals("sunday", MyDate.getCurrentDayNameOfDayLowerCase());
        }

    }

}
