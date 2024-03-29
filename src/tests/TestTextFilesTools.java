package tests;

import text_files_tools.DirectoryTools;
import text_files_tools.FilesTools;

public class TestTextFilesTools {
    public static void main(String[] args) {
        test2();
    }

    public static void test1() {
        try {
            FilesTools.convertAndPrintFileInStringArray("src/text_files/products/Burak.txt");
            //ConvertTXTFileToOutput.convertToStringArray("Burak.txt");

        }catch (Exception e){
            System.out.println("Exception has been caught: " + e.toString());
        }
    }

    public static void test2 (){
        DirectoryTools.printAllFilesInDirectory("src/text_files/products/");
    }

    public static void test3 (){
    }
}
