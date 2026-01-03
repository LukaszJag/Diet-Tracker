package tools.sql_tools.general;

import com.mysql.cj.conf.ConnectionUrlParser;
import tools.sql_tools.general.get.GetConnection;
import tools.sql_tools.general.get.GetResultSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

}
