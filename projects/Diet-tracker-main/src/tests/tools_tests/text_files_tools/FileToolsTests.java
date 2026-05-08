package tests.tools_tests.text_files_tools;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tools.text_files_tools.FilesTools;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class FileToolsTests {

    @Nested
    class GetTXTFileLineByLine {
        @Test
        public void getTXTFileLineByLine_test_text_file_1() {
            String[] arrayToTest = FilesTools.getTXTFileLineByLine("src/tests/test_resources/textFiles/test-text-file-1.txt");
            String[] expectedArray = new String[]{"1", "2", "3", "4", "5", "6", "7"};
            assertArrayEquals(expectedArray, arrayToTest);
        }

        @Test
        public void getTXTFileLineByLine_test_text_file_2() {
            String[] arrayToTest = FilesTools.getTXTFileLineByLine("src/tests/test_resources/textFiles/test-text-file-2.txt");
            String[] expectedArray = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};
            assertArrayEquals(expectedArray, arrayToTest);
        }

        @Test
        public void getTXTFileLineByLine_test_text_file_3() {
            String[] arrayToTest = FilesTools.getTXTFileLineByLine("src/tests/test_resources/textFiles/test-text-file-3.txt");
            String[] expectedArray = new String[]{"1", "", "2", "", "3", "", "4", "", "5", "", ""};
            assertArrayEquals(expectedArray, arrayToTest);
        }

        @Test
        public void getTXTFileLineByLine_test_text_file_4() {
            String[] arrayToTest = FilesTools.getTXTFileLineByLine("src/tests/test_resources/textFiles/test-text-file-4.txt");
            String[] expectedArray = new String[]{""};
            assertArrayEquals(expectedArray, arrayToTest);
        }


        @Test
        public void getTXTFileLineByLine_test_text_file_5() {
            String[] arrayToTest = FilesTools.getTXTFileLineByLine("src/tests/test_resources/textFiles/test-text-file-5.txt");
            String[] expectedArray = new String[]{"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"};
            assertArrayEquals(expectedArray, arrayToTest);
        }


        @Test
        public void getTXTFileLineByLine_test_text_file_6() {
            String[] arrayToTest = FilesTools.getTXTFileLineByLine("src/tests/test_resources/textFiles/test-text-file-6.txt");
            String[] expectedArray = new String[]{"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                    "b"};
            assertArrayEquals(expectedArray, arrayToTest);
        }

        @Test
        public void getTXTFileLineByLine_test_text_file_7() {
            String[] arrayToTest = FilesTools.getTXTFileLineByLine("src/tests/test_resources/textFiles/test-text-file-7.txt");
            String[] expectedArray = new String[]{"$", "%", "'", "\"'", "/", "\\", "-", "=", "+", "*", ".", ",", "?", "end"};
            assertArrayEquals(expectedArray, arrayToTest);
        }
    }

    @Test
    public void readAndGetLineTXTFile_test_1(){
        String fullSQLQueryTXTFile = "src/data_store_and_backup/text_files/days/2026-02-15/2026-02-15_Cukier_18.txt";

        String[] tmp_test = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile);

        System.out.println("" + tmp_test[17]);
        System.out.println("" + tmp_test[21]);
        System.out.println("" + tmp_test[20]);

        System.out.println();
        System.out.println();
        String dayDate = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile, 18);
        String productName = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile, 22);
        String amountOfProduct = FilesTools.readAndGetLineTXTFile(fullSQLQueryTXTFile, 21);

        System.out.println(dayDate);
        System.out.println(productName);
        System.out.println(amountOfProduct);
    }
}
