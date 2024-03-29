package tests;

import products_tools.Product;
import runners_and_tests.ProductFactoryToMakeTests;
import sql_tools.ImportDateFromTXTFilesToSQLDB;
import sql_tools.RunQuery;
import text_files_tools.DirectoryTools;
import text_files_tools.FilesTools;

import java.sql.SQLException;

public class QuickTest2 {
    public static void main(String[] args) {
        //test1();
        // test2();
        //test3();
        //test4();
        test5();
    }
    private static void test1() {
        String[] testArray = ImportDateFromTXTFilesToSQLDB.getPureDateFromFileInArray(FilesTools.convertFileToStringArray("src/text_files/products/Burak.txt"));
        for (int i = 0; i < testArray.length; i++) {
            System.out.println(testArray[i]);
        }
    }

    private static void test2(){
        String[] fileInArray = FilesTools.convertFileToStringArray("src/text_files/products/Burak.txt");
        String readyToInsertQuery = ImportDateFromTXTFilesToSQLDB.convertTextFileToSQLQuery(fileInArray);
        try {
            RunQuery.runQueryOnCalendarTable(readyToInsertQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void test3() {
        DirectoryTools.makeDirectory("src/text_files/days/","test1");
    }

    private static void test4() {
        if (DirectoryTools.doesDirectoryExist("src/text_files/days/test2")) {
            System.out.println("Directory exist");
        }else{
            System.out.println("Directory doesn't exist");
        }
    }

    private static void test5() {
        System.out.println(Product.isProductEqual(ProductFactoryToMakeTests.productBarExample(), ProductFactoryToMakeTests.productBarExample()));
        System.out.println(Product.isProductEqual(ProductFactoryToMakeTests.productBarExample(), ProductFactoryToMakeTests.productBarOtherExample()));
    }
}
