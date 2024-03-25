package sql_tools;

public class ImportDateFromTXTFilesToSQLDB {
    public static String convertTextFileToSQLQuery(String[] fileInStringArray){
        String[] pureDataFromTXTFile = getPureDateFromFileInArray(fileInStringArray);
        String preparedStringQuery = prepareQueryAndFields(pureDataFromTXTFile);

        //System.out.println(preparedStringQuery);

        return preparedStringQuery;
    }

    public static String[] getPureDateFromFileInArray(String[] fileInStringArray){
        // Hard strict max array size may cause error
        int maxLinesForResult = 20;
        String tmpDataFromLine;
        String pureDataFromLine;
        int positionOfColon;
        String[] result = new String[maxLinesForResult];
        for (int i = 0; i < fileInStringArray.length; i++) {
            if (fileInStringArray[i] != "0"){
                tmpDataFromLine = fileInStringArray[i];
                positionOfColon = tmpDataFromLine.indexOf(":");
                pureDataFromLine = tmpDataFromLine.substring(positionOfColon + 2, fileInStringArray[i].length());
                result[i] = pureDataFromLine;
            }
        }

        return result;
    }

    public static String prepareQueryAndFields(String[] fieldDate){
        /*
        ORIGINAL QUERY:
        INSERT INTO `diet_tracker_schema`.`product_table`
        (`product_name`,
        `product_brand`,
        `product_package_has`,
        `product_macro_for`,
        `product_kcal`,
        `product_protein`,
        `product_fat`,
        `product_carbs`)
        VALUES
        (<{product_name: }>,
        <{product_brand: }>,
        <{product_package_has: }>,
        <{product_macro_for: }>,
        <{product_kcal: }>,
        <{product_protein: }>,
        <{product_fat: }>,
        <{product_carbs: }>);`


         */

        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print(fieldDate[i] + " ");
        }
        System.out.println();

        String query = "INSERT INTO\n" +
        "`diet_tracker_schema`.`product_table`\n" +
        "(`product_name`,\n" +
        "`product_brand`,\n" +
        "`product_package_has`,\n" +
        "`product_macro_for`,\n" +
        "`product_kcal`,\n" +
        "`product_protein`,\n" +
        "`product_fat`,\n" +
        "`product_carbs`)\n" +
        "VALUES (\n" +
        "'" + fieldDate[0] +"',\n" +
        "'" + fieldDate[1] +"',\n" +
        fieldDate[2] +",\n" +
        fieldDate[3] +",\n" +
        fieldDate[4] +",\n" +
        fieldDate[5] +",\n" +
        fieldDate[6] +",\n" +
        fieldDate[7] +");";

        return query;
    }
}
