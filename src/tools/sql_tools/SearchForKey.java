package tools.sql_tools;

import tools.sql_tools.general.RunQuery;

import java.sql.SQLException;

public class SearchForKey {
    public static void searchForKeyInCalendarTable(String productValue) throws SQLException {
        String tableName = "calendar_test";
        String productKey = "product_name";
        String sqlStatement = QueryMaker.makeSearchByOneKeySQLStatement(tableName, productKey, productValue, "String");
        RunQuery.runQuery(sqlStatement);
    }
}
