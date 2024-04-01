package text_files_tools;

import configuration.Config;
import products_tools.Product;

import java.io.*;
import java.util.Scanner;

public class FilesTools {
    public static void main(String[] args) {

    }

    public static String[] convertFileToStringArray(String fileNameWithExtension) {
        // This int value may cause problem because max amount of lines in file is dynamic
        int maxLinesInFile = 20;
        String[] fileByLinesInArray = new String[maxLinesInFile];
        int counter = 0;
        try {
            Scanner fileScanner = new Scanner(new File(fileNameWithExtension));
            while (fileScanner.hasNext()) {
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

    public static void convertAndPrintFileInStringArray(String fileNameWithExtension) {
        // This int value may cause problem because max amount of lines in file is dynamic
        int maxLinesInFile = 20;
        String[] fileByLinesInArray = new String[maxLinesInFile];
        int counter = 0;
        try {
            Scanner fileScanner = new Scanner(new File(fileNameWithExtension));
            while (fileScanner.hasNext()) {
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

    public static void makeEmptyFile(String newFileName, String destination) {
        try {
            File newFile = new File(destination + newFileName + ".txt");
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred. - Class MakeTextFile -> makeEmptyFile");
            e.printStackTrace();
        }
    }

    public static void makeTextFileForProduct(Product product, float macroFor) {
        String fileName = product.getProductName();
        makeEmptyFile(fileName, Config.DESTINATION_FOR_TEXT_FILE_PRODUCTS);


        try {
            writeProductToFile("Name: " + product.getProductName(), fileName);
            writeProductToFile("Brand: " + product.getProductBrand(), fileName);
            writeProductToFile("Package has: " + product.getProductPackWeight(), fileName);

            writeProductToFile("Macro for: " + macroFor, fileName);

            writeProductToFile("KCal: " + product.getProductMacroForItsSetMeasure().getKcal(), fileName);
            writeProductToFile("Protein: " + product.getProductMacroForItsSetMeasure().getProtein(), fileName);
            writeProductToFile("Fat: " + product.getProductMacroForItsSetMeasure().getFat(), fileName);

            writeProductToFile("Carbs: " + product.getProductMacroForItsSetMeasure().getCarbs(), fileName);
            writeProductToFile("Comment optional: " + product.getCommentOptional(), fileName);


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void writeProductToFile(String lineToWriteToFile, String fileName) throws IOException {
        String fullPath = Config.DESTINATION_FOR_TEXT_FILE_PRODUCTS + fileName + ".txt";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fullPath, true));
        bufferedWriter.append(lineToWriteToFile.toString());
        bufferedWriter.append("\n");
        bufferedWriter.close();
    }

    public static void addDayStringToTextFile(String fileName, String inputSting) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Config.DESTINATION_FOR_TEXT_FILE_DAYS + fileName + ".txt", true));
        bufferedWriter.append(inputSting);
        bufferedWriter.append("\n");
        bufferedWriter.close();
    }

    public static void writeSQLStatementForDayInCalendarToTXTFile(String SQLquery, String fileName) throws IOException {
        String fullPath = Config.DESTINATION_FOR_TEXT_FILE_DAYS + fileName + ".txt";

        if(!DirectoryTools.doesDirectoryExist(fullPath)) {

            File file = new File(fullPath);

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.append(SQLquery);
            bufferedWriter.append("\n");
            bufferedWriter.close();
        } else {
            System.out.println("File already exist");

            if (convertFileToStringArray(fullPath).toString().equals(SQLquery)) {
                System.out.println("Files is equal:\nFirst file:");
                System.out.println(convertFileToStringArray(fullPath).toString());
                System.out.println();
                System.out.println("Second file:");
                System.out.println(SQLquery);
                System.out.println();
            }
        }
    }

    public static void addLineToAllFilesInDirectory(String lineToAdd, String directory) {
        // Danger and possible problem cause because max amount of files is dynamic
        int maxAmountOfFiles = 300;
        String[] fileNameAndDirectory = new String[maxAmountOfFiles];
        int counter = 0;

        File dir = new File(directory);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                System.out.println(child.toString());
                try {
                    writeProductToFile(lineToAdd, child.getName() + ".txt");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileNameAndDirectory[counter] = child.toString();
                counter++;
            }
        } else {
            System.out.println("Wrong directory");
        }
        System.out.println("Counter: " + counter);
    }

    public static String[] getStringArrayForAllFilesInDirectory(String directory) {
        // Danger and possible problem cause because max amount of files is dynamic
        int maxAmountOfFiles = 300;
        String[] fileNameAndDirectory = new String[maxAmountOfFiles];
        int counter = 0;

        File dir = new File(directory);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                fileNameAndDirectory[counter] = child.toString();
                counter++;
            }
        } else {
            System.out.println("Wrong directory");
        }
        return fileNameAndDirectory;
    }
}
