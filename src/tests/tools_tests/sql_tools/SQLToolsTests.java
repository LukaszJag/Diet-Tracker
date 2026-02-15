package tests.tools_tests.sql_tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tools.sql_tools.days_statistics.SelectFromDaysStatistics;
import tools.sql_tools.general.Select;
import tools.sql_tools.general.insert.InsertToTable;
import tools.text_files_tools.FilesTools;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLToolsTests {
    @Nested
    class GeneralTools {
         @Test
        public void prepareSQLInsertStatement() {
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

    // TODO
    @Nested
    class DaysStatisticsTools{

    }

    @Nested
    class SelectGeneralSQLTools{
        // TODO
        @Test
        public void selectOneProductFromProductTable(){
    LinkedHashMap<String, String> functionOutput = Select.selectAllDataFromTable("product_table", "product_name", "=", "Arbuz");
    LinkedHashMap<String, String> expected = new LinkedHashMap<>();
    Assertions.assertTrue(functionOutput.entrySet().equals(null));
}
    }

}
