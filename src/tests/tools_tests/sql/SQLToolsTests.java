package tests.tools_tests.sql;

import org.junit.jupiter.api.Test;
import tools.sql_tools.general.InsertToTable;
import tools.text_files_tools.FilesTools;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLToolsTests {
    @Test
    public void prepareSQLInsertStatement(){
        String tableName = "test_Table_Name";
        HashMap<String, String> hashMapToTest = new HashMap<>();
        hashMapToTest.put("column_1", "value_1");
        hashMapToTest.put("column_2", "value_2");
        hashMapToTest.put("column_3", "value_3");
        hashMapToTest.put("column_4", "value_4");

        String insertSQLStatement = InsertToTable.prepareSQLInsertStatementHashMap(tableName, hashMapToTest);

        String expectedStatement = FilesTools.readTXTFile("src/tests/test_resources/tools_test_resources/sql/prepareSQLInsertStatement_resources.txt");

        assertEquals(expectedStatement, insertSQLStatement);
    }
}
