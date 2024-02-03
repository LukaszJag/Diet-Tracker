package runners_and_tests;

import sql_tools.InsertProductToSQL_Table;

public class Test_SQL {
    public static void main(String[] args) {
        System.out.println(InsertProductToSQL_Table.createInsertSQLQueryForProductTable(TestProductFactory.productBarExample()));
    }
}
