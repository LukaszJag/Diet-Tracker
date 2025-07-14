package tools.sql_tools.general;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Dictionary;

public class SumTable {
    public static Dictionary sumRowsInTableWhereColumnLike(String tableName, String[] fieldsNamesToSum, String whereColumnName, String whereColumnValue) {
        Connection connection;

        String sqlQuery = prepareSQLQuery(tableName, fieldsNamesToSum, whereColumnName, whereColumnValue);

        Dictionary<String, Float> dict = null; //new Dictionary<Integer, String>();
        try {
            connection = GetConnection.getConnectionWithLocalHost();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dict;
    }

    private static String prepareSQLQuery(String tableName, String[] fieldsNamesToSum, String whereColumnName, String whereColumnValue) {
        String fieldsSumQuery = "";

        for (int i = 0; i < fieldsNamesToSum.length; i++) {
            fieldsSumQuery += "FORMAT((SUM(" + fieldsNamesToSum[i] + ")), '0.00') AS \"" + fieldsNamesToSum[i] + "\", \n";
        }

        String sqlQuery = "SELECT \n" +
                fieldsSumQuery +
                "\n" +
                "FROM " + tableName + "\n" +
                "\n" +
                "WHERE " + whereColumnName + " LIKE " + whereColumnValue + ";\n";

        return sqlQuery;
    }
}
