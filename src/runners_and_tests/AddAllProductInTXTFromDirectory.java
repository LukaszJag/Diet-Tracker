package runners_and_tests;

import sql_tools.CheckIfRowExist;
import sql_tools.ImportDateFromTXTFilesToSQLDB;
import sql_tools.RunQuery;
import text_files_tools.FilesTools;

import java.sql.SQLException;

public class AddAllProductInTXTFromDirectory {
    public static void main(String[] args) throws SQLException {
        String[] allProductFromDirectory = FilesTools.getStringArrayForAllFilesInDirectory("src/data_store_and_backup/text_files/products");
        String[] fileInArray;
        String readyToInsertQuery;
        int counterOfExist = 0;
        int counterOfDoNotExist = 0;
        int counterOfAllProducts = 0;

        for (int i = 0; i < allProductFromDirectory.length; i++) {

            fileInArray = FilesTools.convertFileToStringArray(allProductFromDirectory[i]);
            if (fileInArray == null){
                break;
            }

            readyToInsertQuery = ImportDateFromTXTFilesToSQLDB.convertTextFileToSQLQuery(fileInArray);
            System.out.println("\n ITERATION NUMER: " + i + "\nFile: " + fileInArray[0] + "\n");

            if(CheckIfRowExist.isProductNameExistInProductTable(fileInArray[0])){
                System.out.println("[" + i + "] -> Product EXIST: (" + fileInArray[0] + ")");
                counterOfExist++;
            }else {
                try {
                    System.out.println("[" + i + "] -> Product DON'T EXIST: (" + fileInArray[0] + ")");
                    System.out.println(readyToInsertQuery);
                    RunQuery.runQuery(readyToInsertQuery);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                counterOfDoNotExist++;
            }
            counterOfAllProducts++;
        }

        System.out.println("All products amount: " + counterOfAllProducts);
        System.out.println("All products that EXIST: " + counterOfExist);
        System.out.println("All products that DON'T EXIST: " + counterOfDoNotExist);
    }
}
