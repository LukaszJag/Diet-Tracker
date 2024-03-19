package text_files_tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConvertTXTFileToOutput {
    public static String[] convertToStringArray(String fileNameWithExtension){
        // This int value may cause problem because max amount of lines in file is dynamic
        int maxLinesInFile = 20;
        String[] fileByLinesInArray = new String[maxLinesInFile];
        int counter = 0;
        try {
            Scanner fileScanner = new Scanner(new File(fileNameWithExtension));
            while(fileScanner.hasNext()){
                fileByLinesInArray[counter] = fileScanner.nextLine();
                counter++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (int i = counter; i < fileByLinesInArray.length; i++) {
            fileByLinesInArray[i] = "0";
        }

        return fileByLinesInArray;
    }

    public static void convertAndPrintFileInStringArray(String fileNameWithExtension){
        // This int value may cause problem because max amount of lines in file is dynamic
        int maxLinesInFile = 20;
        String[] fileByLinesInArray = new String[maxLinesInFile];
        int counter = 0;
        try {
            Scanner fileScanner = new Scanner(new File(fileNameWithExtension));
            while(fileScanner.hasNext()){
                fileByLinesInArray[counter] = fileScanner.nextLine();
                counter++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (int i = counter; i < fileByLinesInArray.length; i++) {
            fileByLinesInArray[i] = "0";
        }

        for (int i = 0; i < fileByLinesInArray.length; i++) {
            System.out.println(fileByLinesInArray[i]);
        }
    }
}
