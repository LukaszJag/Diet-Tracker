package runners_and_tests.run_export_import_data;

import tools.sql_tools.CheckIfRowExist;
import tools.sql_tools.general.run.RunQuery;
import tools.text_files_tools.FilesTools;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class AddAllCalendarDaysInTXTFromDirectory {
    CheckIfRowExist checkIfRowExist;
    public static void main(String[] args){

        AddAllCalendarDaysInTXTFromDirectory addAllCalendarDaysInTXTFromDirectory = new AddAllCalendarDaysInTXTFromDirectory();
        addAllCalendarDaysInTXTFromDirectory.main_method();
    }

    public void main_method(){
        checkIfRowExist = new CheckIfRowExist();
        String[] allFilesPath = getPathsOfFiles();
        // May cause Error : hard code length
        int arraysLength = 7000;
        String[] productThatExist = new String[arraysLength];
        int counterExistArray = 0;
        int counterNotExistArray = 0;
        String[] productThatNotExist = new String[arraysLength];
        int amountOfExistRows = 0;
        int amountOfNotExistRows = 0;


        long start = System.nanoTime();
        //System.out.println(time.toString());
        int counter = 0;
        for (int i = 0; i < allFilesPath.length; i++) {
            if (checkIfProductExist2(allFilesPath[i])) {
                productThatExist[counterExistArray] = allFilesPath[i];
                counterExistArray++;
                amountOfExistRows++;
            } else {
                productThatNotExist[counterNotExistArray] = allFilesPath[i];
                counterNotExistArray++;
                amountOfNotExistRows++;
            }
            counter++;
            if (counter % 500 ==0){
                System.out.println("Refrashed row in calendar table: " + counter + "\t" + (TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - start)));
            }
        }

        String query;
        for (int i = 0; i < counterNotExistArray; i++) {
            query = FilesTools.readTXTFile(productThatNotExist[i]);
            try {
                RunQuery.runQuery(query);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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

    public boolean checkIfProductExist2(String fullSQLQueryTXTFile) {
        boolean result;

        // TO DO change name of this variable below
        String[] tmp_test = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile);


        String dayDate = tmp_test[17];
        String productName = tmp_test[21];
        String amountOfProduct = tmp_test[20];

        /*
        String dayDate = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile, 18);
        String productName = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile, 22);
        String amountOfProduct = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile, 21);
        */

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
            result = checkIfRowExist.isCalendarRowExistInProductTable_local_variables(dayDate,productName, amountOfProduct);
            //, optionalTime);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }




    public static boolean checkIfProductExist(String fullSQLQueryTXTFile) {
        boolean result;

        // TO DO change name of this variable below
        String[] tmp_test = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile);


        String dayDate = tmp_test[17];
        String productName = tmp_test[21];
        String amountOfProduct = tmp_test[20];

        /*
        String dayDate = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile, 18);
        String productName = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile, 22);
        String amountOfProduct = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile, 21);
        */

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
