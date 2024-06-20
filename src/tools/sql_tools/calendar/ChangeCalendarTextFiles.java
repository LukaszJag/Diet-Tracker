package tools.sql_tools.calendar;

import tools.products_tools.Macro;
import tools.sql_tools.SQLSelect;
import tools.text_files_tools.DirectoryTools;
import tools.text_files_tools.FilesTools;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class ChangeCalendarTextFiles {
    public static void changeAllSQLTextFilesForCalendar() throws FileNotFoundException {
        String[] allFilesPath;
        String pathToSQLTextFiles = "src/data_store_and_backup/text_files/days";
        allFilesPath = DirectoryTools.getPathForAllFilesInAllDirectory(pathToSQLTextFiles);

        System.out.println("All files path amount: " + allFilesPath.length + "\n");

        String[] fileArrayTMP;
        int allRightDataRows = 0;
        int allWrongDataRows = 0;
        int allFileDataRowsCheck = 0;

        for (int i = 0; i < allFilesPath.length; i++) {

            fileArrayTMP = FilesTools.convertFileToStringArray(allFilesPath[i]);
            if (extractDataAndPassToDoesCalendarProductHasRightMacro(fileArrayTMP, allFilesPath[i])){
                allRightDataRows++;
            }else {
                System.out.print("[" + i + "]");
                System.out.println();
                allWrongDataRows++;
            }
            allFileDataRowsCheck++;
        }

        System.out.println("All file data rows checked: " + allFileDataRowsCheck);
        System.out.println("All file data rows with right data: " + allRightDataRows);
        System.out.println("All file data rows with wrong data; " + allWrongDataRows);
    }

    public static boolean extractDataAndPassToDoesCalendarProductHasRightMacro(String[] fileTextContentInArray, String pathToFile) {
        Macro productMacro;
        Macro consumedMacro;
        String productName = fileTextContentInArray[21].replace("'", "");
        float amountOfProduct;
        // delete last coma
        productName = productName.substring(0, productName.length() - 1);

        amountOfProduct = Float.valueOf(fileTextContentInArray[20].substring(0, fileTextContentInArray[20].length() - 1));
        productMacro = new Macro(
                Float.valueOf(fileTextContentInArray[22].substring(0, fileTextContentInArray[22].length() - 1)),
                Float.valueOf(fileTextContentInArray[23].substring(0, fileTextContentInArray[23].length() - 1)),
                Float.valueOf(fileTextContentInArray[24].substring(0, fileTextContentInArray[24].length() - 1)),
                Float.valueOf(fileTextContentInArray[25].substring(0, fileTextContentInArray[25].length() - 1))
        );

        consumedMacro = new Macro(
                Float.valueOf(fileTextContentInArray[28].substring(0, fileTextContentInArray[28].length() - 1)),
                Float.valueOf(fileTextContentInArray[31].substring(0, fileTextContentInArray[31].length() - 1)),
                Float.valueOf(fileTextContentInArray[30].substring(0, fileTextContentInArray[30].length() - 1)),
                Float.valueOf(fileTextContentInArray[29].substring(0, fileTextContentInArray[29].length() - 1))
        );

       // QuickTests.printArrayWithNumOfPosition(fileTextContentInArray);
        if (!doesCalendarRowDataIsCorrect(productName, amountOfProduct, productMacro, consumedMacro, false)){
            System.out.println("\n" + pathToFile);
        }
        return doesCalendarRowDataIsCorrect(productName, amountOfProduct, productMacro, consumedMacro, true);
    }

    public static boolean doesCalendarRowDataIsCorrect(String productName, float amountOfProduct, Macro productMacroFromCalendar,
                                                       Macro consumedMacro, boolean showLogs) {
        boolean isDataCorrect = true;
        Macro productMacroFromSQLProductTable = getMacroFromProductTableByProductName(productName);
        boolean errorType1 = false;
        boolean errorType2 = false;
        boolean errorType3 = false;

        Macro consumedMacroCalculateByProductMacroFromCalendar = Macro.getConsumedMacro(amountOfProduct, productMacroFromCalendar);
        Macro consumedMacroCalculateByProductMacroFromProductTable = Macro.getConsumedMacro(amountOfProduct, productMacroFromSQLProductTable);

        consumedMacroCalculateByProductMacroFromCalendar = Macro.cutNumberPrecisionForAllValues(consumedMacroCalculateByProductMacroFromCalendar);
        consumedMacro = Macro.cutNumberPrecisionForAllValues(consumedMacro);
        consumedMacroCalculateByProductMacroFromProductTable = Macro.cutNumberPrecisionForAllValues(consumedMacroCalculateByProductMacroFromProductTable);

        if (consumedMacroCalculateByProductMacroFromProductTable.getKcal() == consumedMacro.getKcal()){

        }else {
            if (!Macro.isMacroEqual(productMacroFromSQLProductTable, productMacroFromCalendar)) {
                isDataCorrect = false;
                errorType1 = true;
            }

            if (!Macro.isMacroEqual(consumedMacroCalculateByProductMacroFromProductTable, consumedMacro)) {
                isDataCorrect = false;
                errorType2 = true;
            }

            if (!Macro.isMacroEqual(consumedMacroCalculateByProductMacroFromProductTable, consumedMacroCalculateByProductMacroFromCalendar)) {
                isDataCorrect = false;
                errorType3 = true;
            }
        }

        if (errorType1 && showLogs){
            System.out.println("Macro from SQL Product Table doesn't match Macro from SQL Calendar table");
            System.out.println("Error type [1] for product: " + productName
                    + "\nAmount: " + amountOfProduct);

            System.out.println();
            Macro.printAllValues(productMacroFromSQLProductTable);
            System.out.println();
            Macro.printAllValues(productMacroFromCalendar);
            System.out.println();
        }

        if (errorType2 && showLogs){
            System.out.println("Consumed Macro: from SQL Product Table doesn't match Consumed Macro from SQL Calendar table");
            System.out.println("Error type [2] for product: " + productName
                    + "\nAmount: " + amountOfProduct);
            System.out.println("\nconsumedMacroCalculateByProductMacroFromProductTable:");
            Macro.printAllValues(consumedMacroCalculateByProductMacroFromProductTable);
            System.out.println("\nconsumedMacro:");
            Macro.printAllValues(consumedMacro);
            System.out.println();
        }

        if (errorType3 && showLogs){
            System.out.println("Consumed Macro: from SQL Product Table doesn't match Consumed Macro calculated from product Macro from SQL Calendar table");
            System.out.println("Error type [3] for product: " + productName
                    + "\nAmount: " + amountOfProduct);
            System.out.println();
            Macro.printAllValues(consumedMacroCalculateByProductMacroFromProductTable);
            System.out.println();
            Macro.printAllValues(consumedMacroCalculateByProductMacroFromCalendar);
            System.out.println();
        }


        if (!isDataCorrect && showLogs){
            System.out.println();
            System.out.println();
            System.out.println();
        }

        return isDataCorrect;
    }

    public static Macro getMacroFromProductTableByProductName(String productName) {
        String[] productDataFromSQL;
        Macro resultMacro = new Macro(-1,-1,-1,-1);
        try {
            productDataFromSQL = SQLSelect.getRowFromProductTableByProductNameGetArray(productName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try{

        resultMacro = new Macro(
                Float.valueOf(productDataFromSQL[4]),
                Float.valueOf(productDataFromSQL[5]),
                Float.valueOf(productDataFromSQL[6]),
                Float.valueOf(productDataFromSQL[7])

        );
        }catch (NullPointerException e){
            System.out.println("Catch exception:");
            System.out.println(productName);

            for (int i = 0; i < productDataFromSQL.length; i++) {
                System.out.println(productDataFromSQL[i]);
            }

            System.exit(0);
        }

        return resultMacro;
    }
}
