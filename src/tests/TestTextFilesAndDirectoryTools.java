package tests;

import text_files_tools.DirectoryTools;
import text_files_tools.FilesTools;

import java.io.IOException;

public class TestTextFilesAndDirectoryTools {
    public static void main(String[] args) {
        //test1();
        //test2();
        //test3();
        //test4();
        test5();
    }


    public static void test1() {
        try {
            FilesTools.convertAndPrintFileInStringArray("src/data_store_and_backup/text_files/products/Burak.txt");
            //ConvertTXTFileToOutput.convertToStringArray("Burak.txt");

        } catch (Exception e) {
            System.out.println("Exception has been caught: " + e.toString());
        }
    }

    public static void test2() {
        DirectoryTools.printAllFilesInDirectory("src/data_store_and_backup/text_files/products/");
    }

    // test display all days directory and files
    public static void test3() {
        String directory = "src/data_store_and_backup/text_files/days";
        DirectoryTools.printAllFilesInDirectory(directory);
    }

    public static void test4() {
        String directory = "src/data_store_and_backup/text_files/days";
        DirectoryTools.printAllFilesInAllDirectory(directory);

    }

    private static void test5() {
        String directory = "src/data_store_and_backup/text_files/days/2024-04-13/2024-04-13_Cukier_122.txt";
        String output = FilesTools.readTXTFile(directory);
        System.out.println(output);
    }
}
