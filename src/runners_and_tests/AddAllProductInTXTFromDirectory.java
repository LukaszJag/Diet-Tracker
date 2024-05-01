package runners_and_tests;

import sql_tools.CheckIfRowExist;
import sql_tools.ImportDateFromTXTFilesToSQLDB;
import sql_tools.RunQuery;
import text_files_tools.FilesTools;

import java.sql.SQLException;

public class AddAllProductInTXTFromDirectory {
    public static void main(String[] args) throws SQLException {
        String[] allProductFromDirectory = FilesTools.getStringArrayForAllFilesInDirectory("src/text_files/products/");
        String[] fileInArray;
        String readyToInsertQuery;
        for (int i = 0; i < allProductFromDirectory.length; i++) {
            fileInArray = FilesTools.convertFileToStringArray(allProductFromDirectory[i]);
            readyToInsertQuery = ImportDateFromTXTFilesToSQLDB.convertTextFileToSQLQuery(fileInArray);
            System.out.println("\n ITERATION NUMER: " + i + "\nFile: " + fileInArray[0] + "\n");

            if(CheckIfRowExist.isProductNameExistInProductTable(fileInArray[i])){
                System.out.println([fileInArray]);
            }else {
                try {
                    RunQuery.runQueryOnCalendarTable(readyToInsertQuery);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
