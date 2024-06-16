package tools.sql_tools.general;

public class CheckDataCorrectness {

}

    /*

    public static void getProductAllRowInCalendar() throws SQLException {
        Scanner input = new Scanner(System.in);
        String newProductToCheck;
        String[] dataCalendar;
        String[] dataProduct;


        while (true) {
            newProductToCheck = input.next();
            dataCalendar = getRowsInCalendarByProductName(newProductToCheck);
            System.out.println();
            for (int i = 0; i < dataCalendar.length; i++) {
                if (dataCalendar[i] != null) {
                    System.out.println(dataCalendar[i]);
                }
            }
            System.out.println();
            System.out.println(getRowFromProductTableByProductNameWithColumnName(newProductToCheck));
        }
    }

    public static void getAllWrongProductByName() throws SQLException {
        String[] wrongProductData;
        wrongProductData = SQLSelect.getAllWrongProductByNameFromProductTable();

        for (int i = 0; i < 3; i++) {
            System.out.println(wrongProductData[i]);
        }
    }

    public static void getAllWrongProductMacroByName() throws SQLException {
        String[] wrongProductData;
        wrongProductData = SQLSelect.getAllWrongProductByNameFromProductTable();

        convertDataFromProductStringArrayToProductObject(wrongProductData[0]);
        //for (int i = 0; i < 3; i++) {
        //    System.out.println(wrongProductData[i]);
        //}
    }

    public static String[] getStringArraySplitByColonSign(String[] arrayToSplit, int numberOfLines){
        String[] resultArray = new String[arrayToSplit.length];
        for (int i = 0; i < arrayToSplit.length; i++) {
            if (arrayToSplit[i] == null) {
                break;
            } else{
                resultArray[i] = arrayToSplit[i].substring((arrayToSplit[i].indexOf(":") + 1));
            }
        }

        return resultArray;
    }

    public static Product convertDataFromProductStringArrayToProductObject(String rawData){
        String[] data;
        String[] rawDataLineByLine = rawData.split("\n");


        data = getStringArraySplitByColonSign(rawDataLineByLine, 9);

        Macro macro = new Macro(
                Float.valueOf(data[4]),
                (Float.valueOf(data[5])),
                (Float.valueOf(data[6])),
                (Float.valueOf(data[7])));

        Product product = new Product(
                data[0],
                data[1],
                (Float.valueOf(data[3])),
                macro,
                Float.valueOf(data[2]),
                data[8]);

        return product;
    }

    public static void splitProductFromProductArray(String[] SQLRawDataFromTable){
        for (int i = 0; i < SQLRawDataFromTable.length; i++) {
            if (SQLRawDataFromTable[i] == null){
                break;
            }else{
                consumedMacroFromConsumedProductInCalendarTable(SQLRawDataFromTable[i].split("\n"));
            }

        }
    }
    public static Macro consumedMacroFromConsumedProductInCalendarTable(String[] SQLRawDataFromTable){
        Macro resultMacro;

        String[] dataAfterColon = getStringArraySplitByColonSign(SQLRawDataFromTable, 14);
        resultMacro = new Macro(
                Float.valueOf(dataAfterColon[10]),
                Float.valueOf(dataAfterColon[11]),
                Float.valueOf(dataAfterColon[12]),
                Float.valueOf(dataAfterColon[13]));

        //System.out.println("consumedMacroFromConsumedProductInCalendarTable");

        System.out.println();
        Macro.printAllValues(resultMacro);
        return resultMacro;
    }

    public static Macro productMacroFromConsumedProductInCalendarTable(String[] SQLRawDataFromTable){
        Macro resultMacro;

        String[] dataAfterColon = getStringArraySplitByColonSign(SQLRawDataFromTable, 15);
        resultMacro = new Macro(
                Float.valueOf(dataAfterColon[4]),
                Float.valueOf(dataAfterColon[5]),
                Float.valueOf(dataAfterColon[6]),
                Float.valueOf(dataAfterColon[7]));

        return resultMacro;
    }

    public static float amountOfProductFromConsumedProductInCalendarTable(String[] SQLRawDataFromTable){
        String[] dataAfterColon = getStringArraySplitByColonSign(SQLRawDataFromTable, 15);
        float resultFloat = Float.valueOf(dataAfterColon[3]);
        return resultFloat;
    }

    public static boolean compareTwoMacrosAndAmountOfProductFromCalendar(Macro macroFromProductTable, Macro macroFromCalendarTable, float productAmount){
        System.out.println("macroFromProductTable macro: ");
        Macro.printAllValues(macroFromProductTable);

        System.out.println("macroFromCalendarTable macro: ");
        Macro.printAllValues(macroFromCalendarTable);
        System.out.println();

        Macro rightMacro = new Macro(
                (productAmount/100) * macroFromProductTable.getKcal(),
                (productAmount/100) * macroFromProductTable.getProtein(),
                (productAmount/100) * macroFromProductTable.getFat(),
                (productAmount/100) * macroFromProductTable.getCarbs());

        Macro macroToCheck = new Macro(
                (productAmount/100) * macroFromCalendarTable.getKcal(),
                (productAmount/100) * macroFromCalendarTable.getProtein(),
                (productAmount/100) * macroFromCalendarTable.getFat(),
                (productAmount/100) * macroFromCalendarTable.getCarbs());

        System.out.println("rightMacro macro: ");
        Macro.printAllValues(rightMacro);

        System.out.println("macroToCheck macro: ");
        Macro.printAllValues(macroToCheck);

        return Macro.isMacroEqual(rightMacro, macroToCheck);
    }

    public static void printAllDataFromRawSQLStringArrayTEST() throws SQLException {
        Product productFromProductTable = convertDataFromProductStringArrayToProductObject(SQLSelect.getRowFromProductTableByProductNameWithColumnName("Nutella"));
        String[] array = productFromProductTable.productDataInStringArray(productFromProductTable);
        for (String line: array) {
            System.out.println(line);
        }

        String[] allProductFromCalendar = SQLSelect.getAllProductDataByNameFromCalendarTable("Nutella");
        Macro productFromCalendarMacro;
        for (int i = 0; i < allProductFromCalendar.length; i++) {
            if(allProductFromCalendar[i] == null){
                System.out.println("Null detected");
                break;
            }else {
                String[] productRawSQLDataToArray = allProductFromCalendar[i].split("\n");

                productFromCalendarMacro = productMacroFromConsumedProductInCalendarTable(productRawSQLDataToArray);
                //.out.println(allProductFromCalendar[i]);
                System.out.println(compareTwoMacrosAndAmountOfProductFromCalendar(productFromProductTable.getProductMacroForItsSetMeasure(),
                        productFromCalendarMacro, amountOfProductFromConsumedProductInCalendarTable(productRawSQLDataToArray)));
                System.out.println();
            }
        }

    }

}
*/
