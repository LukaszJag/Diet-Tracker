package tests;

import sql_tools.ImportDateFromTXTFilesToSQLDB;
import sql_tools.RunQuery;
import text_files_tools.ConvertTXTFileToOutput;

import java.sql.SQLException;

public class QuickTest2 {
    public static void main(String[] args) {
        //test1();
        test2();
    }

    private static void test1() {
        String[] testArray = ImportDateFromTXTFilesToSQLDB.getPureDateFromFileInArray(ConvertTXTFileToOutput.convertToStringArray("src/text_files/products/Burak.txt"));
        for (int i = 0; i < testArray.length; i++) {
            System.out.println(testArray[i]);
        }
    }

    private static void test2(){
        String[] fileInArray = ConvertTXTFileToOutput.convertToStringArray("src/text_files/products/Burak.txt");
        String readyToInsertQuery = ImportDateFromTXTFilesToSQLDB.convertTextFileToSQLQuery(fileInArray);
        try {
            RunQuery.runQueryOnCalendarTable(readyToInsertQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
