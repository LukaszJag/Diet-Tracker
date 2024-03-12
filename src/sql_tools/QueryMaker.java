package sql_tools;

import configuration.Config;

public class QueryMaker {

    // Doesn't recognize different types of values
    public static String makeInsertSQLStatement(String tableName, String[] keysToColumns, String[] values, String[] dataTypeTable){
        if (keysToColumns.length != values.length){
            System.out.println("Values and Keys NOT equal");
            return "ERROR";
        }

        // Set head of query
        String sqlStatement = "INSERT INTO `" + tableName + "`\n";
        sqlStatement += "(";

        // Set columns name in query
        for (int i = 0; i < keysToColumns.length; i++) {
            sqlStatement += keysToColumns[i];

            if(i == keysToColumns.length - 1){
                sqlStatement += ")";
            }else {
                sqlStatement += ",\n";
            }
        }

        // Set Values verse
        sqlStatement += "\nValues\n(";

        // Useless loop
        for (int i = 0; i < values.length; i++) {

            // Take care to float value ends with .f
            if(i == 0 || i == 1) {
                sqlStatement += "'" + values[i] + "'";
            }else{
                sqlStatement += values[i];
            }

            if (i != Config.SQL_COLUMNS_FOR_INSERT_INTO_PRODUCT_TABLE.length - 1){
                sqlStatement += ",\n";
            }
        }

        sqlStatement += ");";
        System.out.println("makeInsertSQLStatement made this SQL Statement:\n\n" + sqlStatement + "\n");
        return  sqlStatement;
    }

    // Doesn't recognize different types of values
    public static String makeSearchByOneKeySQLStatement(String tableName, String keyToSearch, String valueToSearch, String valueType){
        String sqlStatement = "SELECT * FROM " + tableName + "\n";

        if (valueType.equalsIgnoreCase("STRING")){
            valueToSearch = "'" +  valueToSearch +"'";
        }

        sqlStatement += "WHERE " + keyToSearch + " = " + valueToSearch + " ;";

        System.out.println("makeSearchByOneKeySQLStatement made this SQL Statement:\n\n" + sqlStatement + "\n");
        return sqlStatement;
    }
}
