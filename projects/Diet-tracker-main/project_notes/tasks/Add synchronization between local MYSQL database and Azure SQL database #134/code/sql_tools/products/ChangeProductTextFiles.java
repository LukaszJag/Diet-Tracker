package tools.sql_tools.products;

import configuration.Config;
import tools.text_files_tools.FilesTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ChangeProductTextFiles {

    public static void getLineNumberInFile(String fileName, String filePath, int lineNumber) throws FileNotFoundException {
        filePath = "";
        String fullPath = filePath + fileName;
        Scanner scannerInput = new Scanner(new File(fullPath));

        // May cause error hard code array length
        String[] textLinesInFile = new String[20];
        int counter = 0;

        while(scannerInput.hasNextLine()){
            textLinesInFile[counter] = scannerInput.nextLine();
            counter++;
        }

        /*
        System.out.println("All file lines:");
        for (int i = 0; i < counter; i++) {
            System.out.println("[" + i + "]: " + textLinesInFile[i]);
        }
        */

        System.out.println("Request line:");
        System.out.println("[" + lineNumber + "]:" + textLinesInFile[lineNumber]);

    }

    public static void changeMacroForLineLineNumberInFile(String fullPath) throws IOException {
        File oldFile = new File(fullPath);
        Scanner scannerInput = new Scanner(oldFile);

        // May cause error hard code array length
        int counter = 0;
        String[] textLinesInFile = new String[20];
        while(scannerInput.hasNextLine()){
            textLinesInFile[counter] = scannerInput.nextLine();
            counter++;
        }

        /*
        System.out.println("All file lines:");
        for (int i = 0; i < counter; i++) {
            System.out.println("[" + i + "]: " + textLinesInFile[i]);
        }
        */

        String lineToWriteToFile = "";
        textLinesInFile[3] = "Macro for: 100.0";
        for (int i = 0; i < counter; i++) {
            lineToWriteToFile += textLinesInFile[i] + "\n";
        }

        fullPath = fullPath.replace("src\\data_store_and_backup\\text_files\\products\\","");
        fullPath = fullPath.replace(".txt","");
        String onlyFileNamePath = fullPath;
        scannerInput.close();
        if (oldFile.delete()) {
            //System.out.println("Deleted the file: " + oldFile.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
        System.out.println(onlyFileNamePath);
        FilesTools.writeProductToFile(lineToWriteToFile, onlyFileNamePath);
    }
    public static void changeLineInTextFile() throws IOException {
        //Instantiating the File class
        String filePath = "src/textFiles/sampleTextField.txt";
        //Instantiating the Scanner class to read the file
        Scanner sc = new Scanner(new File(filePath));

        //instantiating the StringBuffer class
        StringBuffer buffer = new StringBuffer();

        //Reading lines of the file and appending them to StringBuffer
        while (sc.hasNextLine()) {
            buffer.append(sc.nextLine() + System.lineSeparator());
        }

        String fileContents = buffer.toString();
        System.out.println("Contents of the file: " + fileContents);

        //closing the Scanner object
        sc.close();

        String oldLine = "Macro for: 600.0";
        String newLine = "Macro for: 100.0";

        //Replacing the old line with new line
        fileContents = fileContents.replaceAll(oldLine, newLine);

        //instantiating the FileWriter class
        FileWriter writer = new FileWriter(filePath);

        System.out.println("");
        System.out.println("new data: " + fileContents);
        writer.append(fileContents);
        writer.flush();
    }
}
