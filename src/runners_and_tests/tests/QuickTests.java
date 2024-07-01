package runners_and_tests.tests;

import configuration.Config;
import logs.Log;
import logs.LogsController;
import runners_and_tests.tests.get_simple_data_to_test.DayInCalendarFactoryToMakeTest;
import runners_and_tests.tests.get_simple_data_to_test.ProductFactoryToMakeTests;
import tools.calendar_tools.DayInCalendar;
import tools.products_tools.Product;
import tools.sql_tools.CheckIfRowExist;
import tools.sql_tools.SQLSelect;
import tools.sql_tools.SQLSelectDay;
import tools.sql_tools.SelectDistinctValues;
import tools.sql_tools.calendar.ExportAllDaysDataToSQLCalendar;
import tools.sql_tools.calendar.InsertToCalendarDayTable;
import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;
import tools.sql_tools.general.RunQuery;
import tools.sql_tools.products.ImportDateFromTXTFilesToSQLDB;
import tools.text_files_tools.DirectoryTools;
import tools.text_files_tools.FilesTools;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        //test13();
        //test14();
        //test15();
        //test16();
        //test17();
        //test18();
        //test19();
        //test20();
        //test21();
        //test22();
        //test23();
        //test24();
        //test25();
        //test26();
        test27();
    }


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
    //</editor-fold>

    //<editor-fold desc="TEST 16 -> 20">
    private static void test16() throws ParseException {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);

            Date date = null;

            date = sdf.parse("19/05/2024");

            //specifies the pattern to print
            sdf.applyPattern("EEEE");
            String str = sdf.format(date);

            //prints day name with date
            System.out.println(str);

            String dateString = "";
            String[] dateStringArray = {"19/05/2024", "13/05/2024", "26/05/2024", "19-05-2024", "13-05-2024", "26-05-2024","2024-05-24",
            "2024-04-08",
            "2024-04-13",
            "2024-04-26",
            "2024-04-28",
            "2024-05-02",
            "2024-05-12"};


        System.out.println(dateStringArray[7] + " GOOD");
        System.out.println();
        System.out.println(dateStringArray[8] + " GOOD");
        System.out.println(dateStringArray[9] + " GOOD");
        System.out.println(dateStringArray[10] + " GOOD");
        System.out.println(dateStringArray[11] + " GOOD");
        System.out.println(dateStringArray[12] + " GOOD" );
        System.out.println();

        for (int i = 0; i < dateStringArray.length; i++) {
            if (i <= 2) {
                sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);
                date = sdf.parse(dateStringArray[i]);
                sdf.applyPattern("EEEE");
                dateString = sdf.format(date);
                System.out.println(dateStringArray[i] + " -> " + dateString);
            }
            if (i > 2 && i <=5){
                sdf = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.ENGLISH);
                date = sdf.parse(dateStringArray[i]);
                sdf.applyPattern("EEEE");
                dateString = sdf.format(date);
                System.out.println(dateStringArray[i] + " -> " + dateString);
            }
            if (i ==6){
                sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
                date = sdf.parse(dateStringArray[i]);
                sdf.applyPattern("EEEE");
                dateString = sdf.format(date);
                System.out.println(dateStringArray[i] + " -> " + dateString);

                System.out.println();
                System.out.println("DIRECT FROM SQL");
            }
            if(i > 6){
                sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
                date = sdf.parse(dateStringArray[i]);
                sdf.applyPattern("EEEE");
                dateString = sdf.format(date);
                System.out.println(dateStringArray[i] + " -> " + dateString);
            }
        }


    }

    private static void test17() {
        String SQLquery  = "";
        SQLquery = GenerateSLQTableForDaysStatistics.createInsertSQLQueryForDaysStatistics("2024-05-20");
        System.out.println(SQLquery);
    }

    private static void test18() {
        GenerateSLQTableForDaysStatistics.generateWholeMonthMay();
        GenerateSLQTableForDaysStatistics.generateWholeMonthJune();
    }

    private static void test19() {
        for (int i = 0; i < 31; i++) {
            System.out.println(FilesTools.readAndGetLineTXTFile("src/data_store_and_backup/text_files/days_statistics_test/quick_fill_amount_of_point_in_notepad/may_2024.txt.txt",i));
        }
    }

    private static void test20() throws SQLException {
        String [] table = SQLSelectDay.getAllRowFromDay(20,05,2024);
        for (int i = 0; i < table.length; i++) {
            System.out.println(i + ": " + table[i]);
        }
    }
    //</editor-fold>

    //<editor-fold desc=""TEST 21 -> 25">
    private static void test21() throws IOException {

        /*
        for (int i = 0; i < allProductFiles.length; i++) {
            System.out.print("[" + i + "]:\t");
            ChangeProductTextFiles.getLineNumberInFile(allProductFiles[i], "", 3);
        }

        */
    }

    private static void test22() {
        ExportAllDaysDataToSQLCalendar.exportDataFromTxtToSQLCalendarTable();
    }

    private static void test23() throws SQLException {

    }

    private static void test24() {
        System.out.println(LogsController.getAmountOfLogs());
    }

    private static void test25() {
        System.out.println("|" + Log.getLogID() + "|");
    }
    //</editor-fold>


    private static void test26() {
        Log.increaseLogAmountByOne();
    }

    private static void test27() {
        DayInCalendar dayInCalendar = DayInCalendarFactoryToMakeTest.getExampleDayInCalendarWitDateFriendlyForSQL();
        Product dayInCalendarProduct = ProductFactoryToMakeTests.productBarExample();

        Log.addNewLogForProductToCalendarGUIAccept(dayInCalendar.getDayDateFormatFriendlyForSQL(), dayInCalendarProduct.getProductName(), dayInCalendar.getDayProductMacro(),
                dayInCalendar.getDayAmountOfProduct(), dayInCalendar.getDayDateDayName(), dayInCalendar.getMealName() ,dayInCalendar.getDayProductProduct(),
                dayInCalendar.getConsumedMacro(), dayInCalendar);
    }
}
