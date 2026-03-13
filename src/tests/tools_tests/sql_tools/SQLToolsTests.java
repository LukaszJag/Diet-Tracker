package tests.tools_tests.sql_tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tools.sql_tools.general.RowInTable;
import tools.sql_tools.general.statements.Select;
import tools.sql_tools.general.statements.InsertToTable;
import tools.text_files_tools.FilesTools;
import tools.sql_tools.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLToolsTests {
    @Nested
    class GeneralTools {

        @Nested
        class InsertToTableTests {
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

        @Nested
        class SelectTests {
            @Nested
            class SelectGeneralSQLTools {
                // TODO
                @Test
                public void selectOneProductFromProductTable() {
                    LinkedHashMap<String, String> functionOutput = Select.selectAllDataFromTable("product_table", "product_name", "=", "Arbuz");
                    LinkedHashMap<String, String> expected = new LinkedHashMap<>();
                    Assertions.assertTrue(functionOutput.entrySet().equals(null));
                }

                @Test
                public void selectAllDataFromQueryTests_1() {
                    String dateInSQLFriendlyFormat = "2026-05-05";
                    String SQLQuery = "" +
                            "SELECT " +
                            "* " +
                            "FROM " +
                            "calendar " +
                            "WHERE " +
                            "day_date" +
                            "=" +
                            "\"" +
                            dateInSQLFriendlyFormat +
                            "\"" +
                            ";";

                    ArrayList<ArrayList<HashMap<String, String>>> dataFromTable = Select.selectAllDataFromQuery(SQLQuery);

                    System.out.println(dataFromTable.size());
                    System.out.println(dataFromTable.get(0).size());
//                System.out.println(dataFromTable.get(0).get(0).get("product_name"));
                }

                @Test
                public void selectAllDataFromQueryTests_2() {
                    String dateInSQLFriendlyFormat = "2026-05-05";
                    String SQLQuery = "" +
                            "SELECT " +
                            "* " +
                            "FROM " +
                            "calendar " +
                            "WHERE " +
                            "day_date" +
                            "=" +
                            "\"" +
                            dateInSQLFriendlyFormat +
                            "\"" +
                            ";";

                    ArrayList<HashMap<String, String>> dataFromTable = Select.selectAllDataFromQuery2(SQLQuery);

                    System.out.println(dataFromTable.size());
                    System.out.println(dataFromTable.get(0).size());
//                System.out.println(dataFromTable.get(0).get(0).get("product_name"));
                }

                @Test
                public void selectAllDataFromQueryCukier_2026_03_11() {
                    String dateInSQLFriendlyFormat = "2026-03-11";
                    String productNameCukier = "Cukier";
                    String SQLQuery = "" +
                            "SELECT " +
                            "* " +
                            "FROM " +
                            "calendar " +
                            "WHERE " +
                            "day_date" +
                            "=" +
                            "\"" +
                            dateInSQLFriendlyFormat +
                            "\"" +
                            " AND " +
                            "product_name " +
                            " = " +
                            "\"" +
                            productNameCukier +
                            "\"" +
                            ";";

                    HashMap<String, String> dataFromTable = Select.selectOneRowDataFromQuery(SQLQuery);

                    System.out.println(dataFromTable.size());
                    System.out.println();
                    System.out.println(dataFromTable.toString());
                }
            }
        }

        @Nested
        class RowTests {
            RowInTable food;
            RowInTable cars;
            RowInTable calendar_2026_03_11_Cukier;

            @BeforeEach
            public void populateCalendarCukier_2026_03_11() {
                String dateInSQLFriendlyFormat = "2026-03-11";
                String productNameCukier = "Cukier";
                String SQLQuery = "" +
                        "SELECT " +
                        "* " +
                        "FROM " +
                        "calendar " +
                        "WHERE " +
                        "day_date" +
                        "=" +
                        "\"" +
                        dateInSQLFriendlyFormat +
                        "\"" +
                        " AND " +
                        "product_name " +
                        " = " +
                        "\"" +
                        productNameCukier +
                        "\"" +
                        ";";

                HashMap<String, String> dataFromTable = Select.selectOneRowDataFromQuery(SQLQuery);
                calendar_2026_03_11_Cukier = new RowInTable(dataFromTable);
            }


            //@BeforeEach
            public void populatePlants() {
                food = new RowInTable();
                food.putKeyAndValueToRow("fruit", "apple");
                food.putKeyAndValueToRow("fruit", "banana");
                food.putKeyAndValueToRow("veg", "carrot");
                food.putKeyAndValueToRow("drink", "orange juice");
                food.putKeyAndValueToRow("drink", "milk");
                food.putKeyAndValueToRow("dairy", "milk");
                food.putKeyAndValueToRow("drink", "water");

            }

            //@BeforeEach
            public void populateCars() {
                cars = new RowInTable();
                cars.putKeyAndValueToRow("German", "BMW");
                cars.putKeyAndValueToRow("German", "Porche");
                cars.putKeyAndValueToRow("Japanese", "Nissan");
                cars.putKeyAndValueToRow("USA", "Dodge");
            }

            @Test
            public void rowGetKey() {
                System.out.println(food.getKey("carrot"));
                System.out.println(food.getKey("banana"));
            }

            @Test
            public void multipleKeyFromOneValue() {
                System.out.println(food.getKey("milk"));
            }

            @Test
            public void getAllKeys() {
                System.out.println(food.getAllKeys());
            }

            @Test
            public void getAllValuesAndKeyFromCalendarRow(){
                calendar_2026_03_11_Cukier.printAlLValuesAndKey();
            }
        }

        @Nested
        class TableTests{

            String QueryForTest_1 = "SELECT * FROM calendar WHERE day_date=\"2026-03-11\"";

            @Test
            public void setQueryForTest_1(){
                Table table = new Table(QueryForTest_1);
                table.printTable();
            }
        }
    }
    @Nested
    class DaysStatisticsTools {

    }
}

