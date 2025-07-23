package tools.sql_tools.general;

import java.util.Enumeration;
import java.util.Hashtable;

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
            valuesPartOfStatement += "`" + keysAndValues.get(values.nextElement()) + "`"  + "," + "\n";
        }
        valuesPartOfStatement = valuesPartOfStatement.substring(0, valuesPartOfStatement.length() - 2);
        valuesPartOfStatement += ")";


        sqlStatement = "INSERT INTO " + "`" + tableName + "`" + "\n" +
                namesPartOfStatement +
                 "\n" +"VALUES" + "\n" +
                valuesPartOfStatement +
                ";";


        return sqlStatement;
    }
}
