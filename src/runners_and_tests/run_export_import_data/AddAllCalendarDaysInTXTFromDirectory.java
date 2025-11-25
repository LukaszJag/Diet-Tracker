package runners_and_tests.run_export_import_data;

import tools.sql_tools.CheckIfRowExist;
import tools.sql_tools.general.RunQuery;
import tools.text_files_tools.FilesTools;

import java.sql.SQLException;

public class AddAllCalendarDaysInTXTFromDirectory {
    public static void main(String[] args) throws SQLException {
        String[] allFilesPath = getPathsOfFiles();
        // May cause Error : hard code length
        int arraysLength = 6000;
        String[] productThatExist = new String[arraysLength];
        int counterExistArray = 0;
        int counterNotExistArray = 0;
        String[] productThatNotExist = new String[arraysLength];
        int amountOfExistRows = 0;
        int amountOfNotExistRows = 0;


        for (int i = 0; i < allFilesPath.length; i++) {
            if (checkIfProductExist(allFilesPath[i])) {
                productThatExist[counterExistArray] = allFilesPath[i];
                counterExistArray++;
                amountOfExistRows++;
            } else {
                productThatNotExist[counterNotExistArray] = allFilesPath[i];
                counterNotExistArray++;
                amountOfNotExistRows++;
            }
        }

        //<editor-fold desc="Print - result">
        System.out.println("PRODUCT THAT EXIST IN TABLE:");
        for (int i = 0; i < counterExistArray; i++) {
            System.out.println(productThatExist[i]);
        }
        System.out.println();
        System.out.println();

        System.out.println("PRODUCT THAT NOT EXIST IN TABLE:");
        for (int i = 0; i < counterNotExistArray; i++) {
            System.out.println(productThatNotExist[i]);
        }
        System.out.println();
        System.out.println();
        //</editor-fold>

        String query;
        for (int i = 0; i < counterNotExistArray; i++) {
            query = FilesTools.readTXTFile(productThatNotExist[i]);
            RunQuery.runQuery(query);
        }

        System.out.println("All rows amount: " + allFilesPath.length);
        System.out.println("Row that EXIST: " + amountOfExistRows);
        System.out.println("Row that NOT EXIST: " + amountOfNotExistRows);
    }


    public static String[] getPathsOfFiles() {
        int amountOfFiles = 0;
        String pathToDirectory = "src/data_store_and_backup/text_files/days";
        String[] allFilesPath = FilesTools.getFullAPathToAllTextFilesInDirectory(pathToDirectory);
        for (int i = 0; i < allFilesPath.length; i++) {
            if (allFilesPath[i] != null) {
                amountOfFiles++;
            }
        }

        String[] result = new String[amountOfFiles];
        for (int i = 0; i < amountOfFiles; i++) {
            result[i] = allFilesPath[i];
        }
        return result;
    }

    public static boolean checkIfProductExist(String fullSQLQueryTXTFile) {
        boolean result;

        String dayDate = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile, 18);
        String productName = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile, 22);
        String amountOfProduct = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile, 21);
        //String optionalTime = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile, 27);

        dayDate = dayDate.replace("(", "");
        dayDate = dayDate.replace("'", "");
        dayDate = dayDate.replace(",", "");

        productName = productName.replace("'", "");
        productName = productName.substring(0, productName.length() - 1);

        amountOfProduct = amountOfProduct.replace(",", "");

        //optionalTime = optionalTime.replace(",","");
        //optionalTime = optionalTime.replace("'", "");

        try {
            result = CheckIfRowExist.isCalendarRowExistInProductTable(dayDate, productName, amountOfProduct);
            //, optionalTime);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
