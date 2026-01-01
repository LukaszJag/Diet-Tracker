package tools.sql_tools.general;

import java.util.LinkedHashMap;

public class Select {
    // TODO
    public static LinkedHashMap<String, String> selectAllDataFromTable(String tableName, String key, String operator, String value){
/*
example:
SELECT * from table WHERE key = value ??? -> =
SELECT * from table WHERE key LIKE value ??? -> LIKE
SELECT * from table WHERE key != value ??? -> !=

how to handle this upper examples
 */
        return null;
    }

    public static LinkedHashMap<String, String> selectAllDataFromTable(String tableName, String firstKey, String firstOperator, String firstValue,
                                                                String secondKey, String secondOperator, String secondValue){
        return null;
    }

}
