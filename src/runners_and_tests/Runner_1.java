package runners_and_tests;

import sql_tools.InsertProductToSQL_Table;

public class Runner_1 {
    public static void main(String[] args) {
        System.out.println(InsertProductToSQL_Table.createInsertSQLQueryForProductTable(null));

    }
}
