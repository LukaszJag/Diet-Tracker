package runners_and_tests;

import sql_tools.InsertProductToSQL_Table;
import text_files_tools.MakeFoldersAndTextFile;

import java.io.IOException;

public class Test_SQL_Statement_To_Txt {
    public static void main(String[] args) throws IOException {
        System.out.println(InsertProductToSQL_Table.createInsertSQLQueryForProductTable(ProductFactoryToMakeTests.productBarExample()));
        MakeFoldersAndTextFile.writeSQLStatementToTXTFile(InsertProductToSQL_Table.createInsertSQLQueryForProductTable(ProductFactoryToMakeTests.productBarExample()));
    }
}
