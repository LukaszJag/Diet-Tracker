package tools.sql_tools.general;

import java.util.*;

public class InsertToTable {
    public static String prepareSQLStatement(String tableName, Hashtable<String, String> keysAndValues) {
        String sqlStatement = "";
        String namesPartOfStatement = "";
        String valuesPartOfStatement = "";

        Enumeration<String> names = keysAndValues.keys();
        namesPartOfStatement += "(";
        for (int i = 0; i < keysAndValues.size(); i++) {
            namesPartOfStatement += "`" + names.nextElement() + "`" + "," + "\n";
        }
        namesPartOfStatement = namesPartOfStatement.substring(0, namesPartOfStatement.length() - 2);
        namesPartOfStatement += ")";

        Enumeration<String> values = keysAndValues.keys();
        valuesPartOfStatement += "(";
        for (int i = 0; i < keysAndValues.size(); i++) {
            valuesPartOfStatement += "`" + keysAndValues.get(values.nextElement()) + "`" + "," + "\n";
        }
        valuesPartOfStatement = valuesPartOfStatement.substring(0, valuesPartOfStatement.length() - 2);
        valuesPartOfStatement += ")";


        sqlStatement = "INSERT INTO " + "`" + tableName + "`" + "\n" +
                namesPartOfStatement +
                "\n" + "VALUES" + "\n" +
                valuesPartOfStatement +
                ";";


        return sqlStatement;
    }

    public static String prepareSQLInsertStatementHashMap(String tableName, HashMap<String, String> keysAndValues) {
        String sqlStatement = "";
        String namesPartOfStatement = "";
        String valuesPartOfStatement = "";

        Set<String> names = keysAndValues.keySet();
        Iterator<String> iterator = names.iterator();
        namesPartOfStatement += "(";
        String[] keysInOrder = new String[names.size()];
        for (int i = 0; i < keysAndValues.size(); i++) {
            keysInOrder[i] = iterator.next();
            namesPartOfStatement += "`" + keysInOrder[i] + "`" + "," + "\n";
        }

        namesPartOfStatement = namesPartOfStatement.substring(0, namesPartOfStatement.length() - 2);
        namesPartOfStatement += ")";

        valuesPartOfStatement += "(";
        for (int i = 0; i < keysAndValues.size(); i++) {
            valuesPartOfStatement += "`" + keysAndValues.get(keysInOrder[i]) + "`" + "," + "\n";
        }
        valuesPartOfStatement = valuesPartOfStatement.substring(0, valuesPartOfStatement.length() - 2);
        valuesPartOfStatement += ")";


        sqlStatement = "INSERT INTO " + "`" + tableName + "`" + "\n" +
                namesPartOfStatement +
                "\n" + "VALUES" + "\n" +
                valuesPartOfStatement +
                ";";


        return sqlStatement;
    }
}
