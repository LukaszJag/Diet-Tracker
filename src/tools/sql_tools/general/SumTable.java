package tools.sql_tools.general;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class SumTable {
    public static Hashtable sumRowsInTableWhereColumnLike(String tableName, String[] fieldsNamesToSum, String whereColumnName, String whereColumnValue) {
        Connection connection;

        String sqlQuery = prepareSQLQuery(tableName, fieldsNamesToSum, whereColumnName, whereColumnValue);

        Hashtable<String, Float> fieldAndSum = new Hashtable<>();

        ResultSet resultSet = GetResultSet.getResultSetFromSQL(sqlQuery);
        try {
            while (resultSet.next()) {
                for (int i = 0; i < fieldsNamesToSum.length; i++) {
                    fieldAndSum.put(fieldsNamesToSum[i], Float.valueOf((resultSet.getString(fieldsNamesToSum[i])).replace(",", "")));

                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }


        return fieldAndSum;
    }/**/

    public static Hashtable sumRowsInTableWherePeriodOfTime(String tableName, String[] fieldsNamesToSum, String whereColumnName, String whereColumnValue) {
        Connection connection;

        String sqlQuery = prepareSQLQuery(tableName, fieldsNamesToSum, whereColumnName, whereColumnValue);

        Hashtable<String, Float> fieldAndSum = new Hashtable<>();

        ResultSet resultSet = GetResultSet.getResultSetFromSQL(sqlQuery);
        try {
            while (resultSet.next()) {
                for (int i = 0; i < fieldsNamesToSum.length; i++) {
                    fieldAndSum.put(fieldsNamesToSum[i], Float.valueOf((resultSet.getString(fieldsNamesToSum[i])).replace(",", "")));

                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }


        return fieldAndSum;
    }

    public static Hashtable sumRowsInTableWhereMonthOfTime(String tableName, String[] fieldsNamesToSum, String timeColumnName, String monthInSQLFormat) {
        Connection connection;

        String sqlQuery = prepareSQLQuery(tableName, fieldsNamesToSum, timeColumnName, monthInSQLFormat);

        Hashtable<String, Float> fieldAndSum = new Hashtable<>();

        ResultSet resultSet = GetResultSet.getResultSetFromSQL(sqlQuery);
        try {
            while (resultSet.next()) {
                for (int i = 0; i < fieldsNamesToSum.length; i++) {
                    if (resultSet.getString(fieldsNamesToSum[i]) == null) {
                        System.out.println("i is null for: " + i);
                    } else {
                        String buffor = (resultSet.getString(fieldsNamesToSum[i])).replace(",", "");
                        if (buffor == null) {

                            fieldAndSum.put(fieldsNamesToSum[i], 0.0f);
                        } else {
                            fieldAndSum.put(fieldsNamesToSum[i], Float.valueOf(buffor));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return fieldAndSum;
    }

    public static String prepareSQLQueryWhereMonthOfTime(String tableName, String[] fieldsNamesToSum, String timeColumnName, String monthInSQLFormat) {
        String fieldsSumQuery = "";

        for (int i = 0; i < fieldsNamesToSum.length; i++) {
            if (i == (fieldsNamesToSum.length - 1)) {
                fieldsSumQuery += "FORMAT((SUM(" + fieldsNamesToSum[i] + ")), '0.00') AS \"" + fieldsNamesToSum[i] + "\" \n";
            } else {
                fieldsSumQuery += "FORMAT((SUM(" + fieldsNamesToSum[i] + ")), '0.00') AS \"" + fieldsNamesToSum[i] + "\", \n";
            }
        }

        String sqlQuery = "SELECT \n" +
                fieldsSumQuery +
                "\n" +
                "FROM " + tableName + "\n" +
                "\n" +
                "WHERE " + timeColumnName + " LIKE \"" + monthInSQLFormat + "\";\n";

        return sqlQuery;
    }

    public static String prepareSQLQuery(String tableName, String[] fieldsNamesToSum, String whereColumnName, String whereColumnValue) {
        String fieldsSumQuery = "";

        for (int i = 0; i < fieldsNamesToSum.length; i++) {
            if (i == (fieldsNamesToSum.length - 1)) {
                fieldsSumQuery += "FORMAT((SUM(" + fieldsNamesToSum[i] + ")), '0.00') AS \"" + fieldsNamesToSum[i] + "\" \n";
            } else {
                fieldsSumQuery += "FORMAT((SUM(" + fieldsNamesToSum[i] + ")), '0.00') AS \"" + fieldsNamesToSum[i] + "\", \n";
            }
        }

        String sqlQuery = "SELECT \n" +
                fieldsSumQuery +
                "\n" +
                "FROM " + tableName + "\n" +
                "\n" +
                "WHERE " + whereColumnName + " LIKE \"" + whereColumnValue + "\";\n";

        return sqlQuery;
    }

    public static String prepareSQLQueryWherePeriodOfTime(String tableName, String[] fieldsNamesToSum, String whereColumnName, String whereColumnValue) {
        String fieldsSumQuery = "";

        for (int i = 0; i < fieldsNamesToSum.length; i++) {
            if (i == (fieldsNamesToSum.length - 1)) {
                fieldsSumQuery += "FORMAT((SUM(" + fieldsNamesToSum[i] + ")), '0.00') AS \"" + fieldsNamesToSum[i] + "\" \n";
            } else {
                fieldsSumQuery += "FORMAT((SUM(" + fieldsNamesToSum[i] + ")), '0.00') AS \"" + fieldsNamesToSum[i] + "\", \n";
            }
        }

        String sqlQuery = "SELECT \n" +
                fieldsSumQuery +
                "\n" +
                "FROM " + tableName + "\n" +
                "\n" +
                "WHERE " + whereColumnName + " LIKE \"" + whereColumnValue + "\";\n";

        return sqlQuery;
    }
}
