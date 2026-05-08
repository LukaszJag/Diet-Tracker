package tools.sql_tools.general.get_check_data;

import tools.sql_tools.general.get.GetResultSet;

import java.sql.ResultSet;

public class GetAmountOfRows {
    String SQLQuery;

    public static int getAmountOfRows(String SQLQuery){
        ResultSet resultSet = GetResultSet.getResultSetFromSQL(SQLQuery);
        return GetResultSet.getAmountRosInResultSet (resultSet);
    }
}
