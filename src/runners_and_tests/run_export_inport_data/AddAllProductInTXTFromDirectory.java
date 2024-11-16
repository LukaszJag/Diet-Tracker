package runners_and_tests.run_export_inport_data;

import tools.sql_tools.CheckIfRowExist;
import tools.sql_tools.products.ImportDateFromTXTFilesToSQLDB;
import tools.sql_tools.general.RunQuery;
import tools.text_files_tools.FilesTools;

import java.sql.SQLException;

public class AddAllProductInTXTFromDirectory {
    public static void main(String[] args){
        String[] allProductFromDirectory = FilesTools.getStringArrayForAllFilesInDirectory("src/data_store_and_backup/text_files/products");
        String[] fileInArray;
        String readyToInsertQuery;
        int counterOfExist = 0;
        int counterOfDoNotExist = 0;
        int counterOfAllProducts = 0;

        for (int i = 0; i < allProductFromDirectory.length; i++) {

            fileInArray = FilesTools.convertFileToStringArraySeparatedByColon(allProductFromDirectory[i]);
            if (fileInArray == null){
                break;
            }

            readyToInsertQuery = ImportDateFromTXTFilesToSQLDB.convertTextFileToSQLQuery(fileInArray);

            try {
                if(CheckIfRowExist.isProductNameExistInProductTable(fileInArray[0])){
                    counterOfExist++;
                }else {
                    try {
                        RunQuery.runQuery(readyToInsertQuery);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    counterOfDoNotExist++;
                }
            } catch (SQLException e) {
                System.out.println(readyToInsertQuery);
                throw new RuntimeException(e);
            }
            counterOfAllProducts++;
        }

        System.out.println("All products amount: " + counterOfAllProducts);
        System.out.println("All products that EXIST: " + counterOfExist);
        System.out.println("All products that DON'T EXIST: " + counterOfDoNotExist);
    }
}
