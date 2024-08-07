package tools.sql_tools.general;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.text.ParseException;

public class IsTheRowAlreadyExist {
    public static boolean isTheDayAlreadyExist(String tableName, String columnName, String dataInStringToCheckFormatSQLFriendly) {
        Integer valueInInt;
        Float valueInFloat;

        try {
            valueInFloat = Float.valueOf(dataInStringToCheckFormatSQLFriendly);
        } catch (Exception e) {
            //System.out.println("Only Warning: Passed data is not Float - location - IsTheRowAlreadyExist -> isTheDayAlreadyExist");
        }

        try {
            valueInInt = Integer.valueOf(dataInStringToCheckFormatSQLFriendly);
        } catch (Exception e) {
            //System.out.println("Only Warning: Passed data is not Integer - location - IsTheRowAlreadyExist -> isTheDayAlreadyExist");
        }




        String sqlStatementString = "SELECT EXISTS(SELECT * FROM " + tableName + " WHERE " + columnName
                + "=" + "\"" +  dataInStringToCheckFormatSQLFriendly + "\"" + ");";

        ResultSet resultSet = GetResultSet.getResultSetFromSQL(sqlStatementString);
        String sqlStatementStringResult;

        if (GetResultSet.resultSetNextReturnValue(resultSet)){
            sqlStatementStringResult = GetResultSet.getFromResultSetByColumnNumberString(1
                , resultSet);

        }else {
            System.out.println("error result set");
            return false;
        }


        if (sqlStatementStringResult.equals("1")) {
            return true;
        } else if (sqlStatementStringResult.equals("0")) {
            return false;
        } else {
            System.out.println("Error - get unexpected value - need fix -  IsTheRowAlreadyExist -> isTheDayAlreadyExist");
            return false;
        }
    }
}
