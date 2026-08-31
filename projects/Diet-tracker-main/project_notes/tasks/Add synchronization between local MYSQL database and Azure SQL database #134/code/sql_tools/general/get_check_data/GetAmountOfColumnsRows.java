package tools.sql_tools.general.get_check_data;

import tools.sql_tools.general.get.GetResultSet;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class GetAmountOfColumnsRows {
    String SQLQuery;

    public static int getAmountOfRows(String SQLQuery){
        ResultSet resultSet = GetResultSet.getResultSetFromSQL(SQLQuery);
        return GetResultSet.getAmountRosInResultSet (resultSet);
    }

    public static int getAmountOfColumns(String SQLQuery){
        ResultSet resultSet = GetResultSet.getResultSetFromSQL(SQLQuery);
        ResultSetMetaData resultSetMetaData = GetResultSet.getResultSetMetaData(resultSet);
        int amountOfColumn;

        try {
            amountOfColumn = resultSetMetaData.getColumnCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return amountOfColumn;
    }
}
