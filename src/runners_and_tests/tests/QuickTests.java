package runners_and_tests.tests;

import runners_and_tests.tests.test_tools.get_simple_data_to_test.DayInCalendarFactoryToMakeTest;
import runners_and_tests.tests.test_tools.get_simple_data_to_test.ProductFactoryToMakeTests;
import tools.calendar_tools.DayInCalendar;
import tools.products_tools.Product;
import tools.sql_tools.SQLSelect;
import tools.sql_tools.calendar.InsertToCalendarDayTable;
import tools.sql_tools.general.insert.InsertToTable;
import tools.sql_tools.general.IsTheRowAlreadyExist;
import tools.sql_tools.general.run.RunQuery;
import tools.sql_tools.general.SumTable;
import tools.sql_tools.products.ImportDateFromTXTFilesToSQLDB;
import tools.text_files_tools.DirectoryTools;
import tools.text_files_tools.FilesTools;

import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

public class QuickTests {
    public static void main(String[] args) throws SQLException, ParseException, IOException {
        //test1();
        //test2();
        //test3();
        //test4();
        //test5();
        //test6();
        //test7();
        //test8();
        //test9();
        //test10();
        //test11();
        //test12();

        // test -> DisplayCurrentDayInSQLFormat - method
        //testDisplayCurrentDayInSQLFormat();

        // test -> SumTable - class
        //checkSumTableQueryCorrectness();
       checkSumTableResultCorrectness();

        // test -> InsertToTable - class
        //checkCorrectnessOfStatementInClassInsertToTable();

    }

    //<editor-fold desc="test -> InsertToTable - class">
    private static void checkCorrectnessOfStatementInClassInsertToTable() {
        Hashtable<String, String> testHashTable = new Hashtable<String, String>();

        testHashTable.put("product_name" , "Agrest");
        testHashTable.put("product_brand" , "owoce");
        testHashTable.put("product_package_has" , "0");
        testHashTable.put("product_macro_for" , "100");
        testHashTable.put("product_kcal" , "44");
        testHashTable.put("product_protein" , "0.8");
        testHashTable.put("product_fat" , "0.15");
        testHashTable.put("product_carbs" , "8.8");
        testHashTable.put("comment_optional" , "Link: https://www.tabele-kalorii.pl/kalorie,Agrest.html");


        String  resultStatement = InsertToTable.prepareSQLStatement("tableTestName", testHashTable);
        System.out.println(resultStatement);
    }
    //</editor-fold>

    //<editor-fold desc="test -> SumTable - class">
    private static void checkSumTableResultCorrectness() {
        Hashtable<String, Float> fieldAndSum = new Hashtable<>();

        String tableName = "days_statistics_test";
        String[] fieldsNamesToSum = {"kcal_consume", "protein_consume", "fat_consume", "carbs_consume"};
        String whereColumnName = "day_date";
        String whereColumnValue = "2025-08%";

       fieldAndSum = SumTable.sumRowsInTableWhereMonthOfTime(tableName, fieldsNamesToSum, whereColumnName,whereColumnValue);

        Enumeration<String> namesOfFields = fieldAndSum.keys();
        while (namesOfFields.hasMoreElements()){
            String nextElement = namesOfFields.nextElement();
            System.out.println(nextElement + " <-> " + fieldAndSum.get(nextElement));
        }
        for (int i = 0; i < fieldAndSum.size(); i++) {
            System.out.println();
        }
    }

    private static void checkSumTableQueryCorrectness() {
        String tableName = "days_statistics_test";
        String[] fieldsNamesToSum = {"kcal_consume", "protein_consume", "fat_consume", "carbs_consume"};
        String whereColumnName = "day_date";
        String whereColumnValue = "2025-07%";

       // System.out.println(SumTable.prepareSQLQuery(tableName, fieldsNamesToSum, whereColumnName,whereColumnValue));
    }
    //</editor-fold>

    //<editor-fold desc="test -> DisplayCurrentDayInSQLFormat - method">
    private static void testDisplayCurrentDayInSQLFormat() {
        Format format = new SimpleDateFormat("EEEE");
        java.util.Date utilDateImport = new java.util.Date();
        String dayNameCurrentDateOnStartWindow = format.format(utilDateImport);
    }
    //</editor-fold>

    //<editor-fold desc="TESTS 1 -> 10">
    private static void test1() {
        String[] testArray = ImportDateFromTXTFilesToSQLDB.getPureDateFromFileInArray(FilesTools.convertFileToStringArraySeparatedByColon("src/data_store_and_backup/text_files/products/Burak.txt"));
        for (int i = 0; i < testArray.length; i++) {
            System.out.println(testArray[i]);
        }
    }

    private static void test2() {
        String[] fileInArray = FilesTools.convertFileToStringArraySeparatedByColon("src/data_store_and_backup/text_files/products/Burak.txt");
        String readyToInsertQuery = ImportDateFromTXTFilesToSQLDB.convertTextFileToSQLQuery(fileInArray);
        try {
            RunQuery.runQuery(readyToInsertQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void test3() {
        DirectoryTools.makeDirectory("src/data_store_and_backup/text_files/days/", "test1");
    }

    private static void test4() {
        if (DirectoryTools.doesDirectoryExist("src/data_store_and_backup/text_files/days/test2")) {
            System.out.println("Directory exist");
        } else {
            System.out.println("Directory doesn't exist");
        }
    }

    private static void test5() {
        System.out.println(Product.isProductEqual(ProductFactoryToMakeTests.productBarExample(), ProductFactoryToMakeTests.productBarExample()));
        System.out.println(Product.isProductEqual(ProductFactoryToMakeTests.productBarExample(), ProductFactoryToMakeTests.productBarOtherExample()));
    }

    //testDateDisplay
    public static void test6() throws ParseException {
        String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String today = dateFormat.format(new Date());
        System.out.println(today);
    }

    //getRowFromProductTableByProductNameGetArrayTest
    public static void test7() throws SQLException {
        SQLSelect.getRowFromProductTableByProductNameGetArray("Burak");
        SQLSelect.getRowFromProductTableByProductNameGetArray("Jogurt kiwi");
        SQLSelect.getRowFromProductTableByProductNameGetArray("Rower");
    }

    // print all data from DayInCalendarObject
    public static void test8() {
        DayInCalendar dayInCalendar = DayInCalendarFactoryToMakeTest.getExampleDayInCalendar();
        String[] dayInCalendarDataInArray = dayInCalendar.dayDataInStringArray(dayInCalendar);

        for (int i = 0; i < dayInCalendarDataInArray.length; i++) {
            System.out.println("i: [" + i + "]: " + dayInCalendarDataInArray[i]);
        }
    }

    public static void test9() {
        DayInCalendar dayInCalendar = DayInCalendarFactoryToMakeTest.getExampleDayInCalendar();
        DayInCalendar.dayDataShowAllData(dayInCalendar);

    }

    public static void test10() {
        InsertToCalendarDayTable.createInsertSQLQueryForCalendarDay(DayInCalendarFactoryToMakeTest.getExampleDayInCalendar());
    }
    //</editor-fold>

    //<editor-fold desc="TEST 11 -> 15">


    private static void test11() {

        String[] dataToCheck = {"2024-07-01",
                "2024-07-01",
                "2024-08-01",
                "2024-08-02",
                "2024-07-08",
                "2024-01-01"};

        for (int i = 0; i < dataToCheck.length; i++) {

            System.out.println("i [" + i + "]: " + IsTheRowAlreadyExist.isTheDayAlreadyExist("days_statistics_test","day_date",
                    dataToCheck[i]));
        }
    }
    private static void test12() {
        System.out.println(Month.valueOf("MAY"));
    }

    private static void test13GetAmountOfDaysInMonth() {
        String[] monthsString = {"Jan", "Feb", "Mar", "Apr",
                "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec"};

        Calendar calendar = Calendar.getInstance();
        Month[] monthsMonth = Month.values();
        for (int i = 0; i < monthsMonth.length; i++) {
            System.out.println((monthsMonth[i].getDisplayName(
                    TextStyle.FULL, Locale.ENGLISH)).toUpperCase());
        }
    }
    //</editor-fold>

}
