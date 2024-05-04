package tests;

import calendar_tools.DayInCalendar;
import products_tools.Product;
import sql_tools.*;
import text_files_tools.DirectoryTools;
import text_files_tools.FilesTools;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuickTests {
    public static void main(String[] args) throws SQLException {
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
        //test13();
        //test14();
        test15();
    }

    private static void test1() {
        String[] testArray = ImportDateFromTXTFilesToSQLDB.getPureDateFromFileInArray(FilesTools.convertFileToStringArray("src/data_store_and_backup/text_files/products/Burak.txt"));
        for (int i = 0; i < testArray.length; i++) {
            System.out.println(testArray[i]);
        }
    }
    private static void test2(){
        String[] fileInArray = FilesTools.convertFileToStringArray("src/data_store_and_backup/text_files/products/Burak.txt");
        String readyToInsertQuery = ImportDateFromTXTFilesToSQLDB.convertTextFileToSQLQuery(fileInArray);
        try {
            RunQuery.runQueryOnCalendarTable(readyToInsertQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void test3() {
        DirectoryTools.makeDirectory("src/data_store_and_backup/text_files/days/","test1");
    }
    private static void test4() {
        if (DirectoryTools.doesDirectoryExist("src/data_store_and_backup/text_files/days/test2")) {
            System.out.println("Directory exist");
        }else{
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
    public static void test8(){
        DayInCalendar dayInCalendar = DayInCalendarFactoryToMakeTest.getExampleDayInCalendar();
        String[] dayInCalendarDataInArray = dayInCalendar.dayDataInStringArray(dayInCalendar);

        for (int i = 0; i < dayInCalendarDataInArray.length; i++) {
            System.out.println("i: [" + i + "]: " + dayInCalendarDataInArray[i]);
        }
    }
    public static void test9(){
        DayInCalendar dayInCalendar = DayInCalendarFactoryToMakeTest.getExampleDayInCalendar();
        DayInCalendar.dayDataShowAllData(dayInCalendar);

    }
    public static void test10(){
        InsertToCalendarDayTable.createInsertSQLQueryForCalendarDay(DayInCalendarFactoryToMakeTest.getExampleDayInCalendar());
    }
    private static void test11() {
        try {
            SQLSelect.getAllProductNamesFromProductTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void test12() throws SQLException {
        System.out.println(CheckIfRowExist.isProductNameExistInProductTable("Burak"));
        System.out.println(CheckIfRowExist.isProductNameExistInProductTable("Bagietka - BBQ Strips"));
    }
    private static void test13() {
        String sqlStatement = "SELECT COUNT(product_name)\n" +
                "FROM product_table\n" +
                "WHERE product_name = \"" + "Burak" + "\";";
        System.out.println(sqlStatement);
    }
    private static void test14() {
        String[] allUnique = SelectDistinctValues.selectAllUniqueCalendarByDayDate();
        for (int i = 0; i < allUnique.length; i++) {
            System.out.println(allUnique[i]);
        }
    }


    private static void test15() {
        DirectoryTools.printAllFilesInAllDirectory("src/data_store_and_backup/text_files/days");
    }
}
