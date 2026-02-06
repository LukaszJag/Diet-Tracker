package tests.all_tests;

import tools.text_files_tools.DirectoryTools;

public class FileDirectoryTests {
    public static void main(String[] args) {
        test1();
    }
    private static void test1() {
        DirectoryTools.printAllFilesInAllDirectory("src/data_store_and_backup/text_files/days");
    }
}
