package tools.sql_tools.general;

import com.mysql.cj.conf.ConnectionUrlParser;
import tools.sql_tools.general.get.GetResultSet;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Select {
    // TODO clean up code
    // TODO only selectAllDataFromTable AND selectOneRowDataFromTable - method is finish - finish rest of methods
    public static LinkedHashMap<String, String> selectAllDataFromTable(String tableName, String key, String operator, String value) {
        String sqlStatement = "SELECT";
/*
example:
SELECT * from table WHERE key = value ??? -> =
SELECT * from table WHERE key LIKE value ??? -> LIKE
SELECT * from table WHERE key != value ??? -> !=

how to handle this upper examples
 */
        return null;
    }

    public static ConnectionUrlParser.Pair<String, String> selectOneRowDataFromTable(String tableName, String selectedColumn1, String selectedColumn2,
                                                                                     String key, String operator, String value) {
        ConnectionUrlParser.Pair<String, String> resultPair;

        String sqlStatement = "SELECT " +
                selectedColumn1 + " , " + selectedColumn2 +
                " FROM " + tableName +
                " WHERE "+
                " " + key +
                " " + operator +
                " " + "\"" + value + "\"" + ";";


        ResultSet resultSet;
        GetResultSet getResultSet = new GetResultSet();
        resultSet = getResultSet.getResultSetFromSQL(sqlStatement);

        String left = "";
        String right = "";

        try {
            if (resultSet.next()) {
                left = resultSet.getString(1);

                right = resultSet.getString(2);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultPair =new ConnectionUrlParser.Pair<>(left, right);
        /*
example:
SELECT * from table WHERE key = value ??? -> =
SELECT * from table WHERE key LIKE value ??? -> LIKE
SELECT * from table WHERE key != value ??? -> !=

how to handle this upper examples
 */

    }

    public static LinkedHashMap<String, String> selectAllDataFromTable(String tableName, String selectedColumn1, String selectedColumn2,
                                                                       String key, String operator, String value) {
        LinkedHashMap<String, String> resultLinkedHashMap = new LinkedHashMap<>();

        String sqlStatement = "SELECT " +
                selectedColumn1 + " , " + selectedColumn2 +
                " FROM " + tableName +
                " WHERE "+
                " " + key +
                " " + operator +
                " " + "\"" + value + "\"" + ";";

        ResultSet resultSet;
        GetResultSet getResultSet = new GetResultSet();
        resultSet = getResultSet.getResultSetFromSQL(sqlStatement);


        try {
            while (resultSet.next()) {
                resultLinkedHashMap.put(resultSet.getString(1), resultSet.getString(2));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultLinkedHashMap;
        /*
example:
SELECT * from table WHERE key = value ??? -> =
SELECT * from table WHERE key LIKE value ??? -> LIKE
SELECT * from table WHERE key != value ??? -> !=

how to handle this upper examples
 */

    }

    public static ArrayList<ArrayList<HashMap<String, String>>> selectAllDataFromQuery(String SQLQuery){
        //<editor-fold desc="Values">
        ResultSet resultSet;
        ResultSetMetaData resultSetMetaData;

        ArrayList<String> columnsNames;
        //</editor-fold>

        resultSet = GetResultSet.getResultSetFromSQL(SQLQuery);

        int amountOfColumns = GetResultSet.getAmountColumnsInResultSet(resultSet);

        ArrayList<ArrayList<HashMap<String, String>>> rows = new ArrayList<ArrayList<HashMap<String, String>>>();

        ArrayList<HashMap<String, String>> row = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> valuesAndKeysOfRow = new HashMap<>();



        int counter = 0;
        int rowNumber = 0;

        while(GetResultSet.isResultSetHasNext(resultSet)){
            for (int i = 1; i < amountOfColumns; i++) {
                valuesAndKeysOfRow.put(GetResultSet.getColumnName(resultSet, i), GetResultSet.getValueOfString(resultSet, i));
            }
            row.add(counter, valuesAndKeysOfRow);
            valuesAndKeysOfRow.clear();
            counter++;

            rowNumber++;
        }

        rows.add(0, row);

        return rows;
    }

    public static ArrayList<HashMap<String, String>> selectAllDataFromQuery2(String SQLQuery){
        //<editor-fold desc="Values">
        ResultSet resultSet;
        ResultSetMetaData resultSetMetaData;

        ArrayList<String> columnsNames;
        //</editor-fold>

        resultSet = GetResultSet.getResultSetFromSQL(SQLQuery);

        int amountOfColumns = GetResultSet.getAmountColumnsInResultSet(resultSet);

        ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> valuesAndKeysOfRow = new HashMap<>();



        int counter = 0;
        int rowNumber = 0;

        while(GetResultSet.isResultSetHasNext(resultSet)){
            for (int i = 1; i < amountOfColumns; i++) {
                System.out.println(GetResultSet.getColumnName(resultSet, i));
                System.out.println(GetResultSet.getValueOfString(resultSet, i));
                valuesAndKeysOfRow.put(GetResultSet.getColumnName(resultSet, i), GetResultSet.getValueOfString(resultSet, i));
            }
            rows.add(counter, valuesAndKeysOfRow);
            System.out.println(valuesAndKeysOfRow);
            System.out.println("TEST -" + counter);
            valuesAndKeysOfRow.clear();
            counter++;

            rowNumber++;
        }


        return rows;
    }
}
