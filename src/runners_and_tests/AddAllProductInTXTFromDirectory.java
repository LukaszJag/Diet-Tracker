package runners_and_tests;

import sql_tools.ImportDateFromTXTFilesToSQLDB;
import sql_tools.RunQuery;
import text_files_tools.ConvertTXTFileToOutput;
import text_files_tools.DisplayTextFilesDateAndInformation;

import java.sql.SQLException;

public class AddAllProductInTXTFromDirectory {
    public static void main(String[] args) {
        String[] allProductFromDirectory = DisplayTextFilesDateAndInformation.getStringArrayForAllFilesInDirectory("src/text_files/products/");
        String[] fileInArray;
        String readyToInsertQuery;
        for (int i = 0; i < allProductFromDirectory.length; i++) {
            fileInArray = ConvertTXTFileToOutput.convertToStringArray(allProductFromDirectory[i]);
            readyToInsertQuery = ImportDateFromTXTFilesToSQLDB.convertTextFileToSQLQuery(fileInArray);
            System.out.println("\n ITERATION NUMER: " + i + "\nFile: " + fileInArray[0] + "\n");

            try {
                RunQuery.runQueryOnCalendarTable(readyToInsertQuery);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
