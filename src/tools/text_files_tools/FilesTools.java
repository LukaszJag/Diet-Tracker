package tools.text_files_tools;

import configuration.Config;
import tools.calendar_tools.DayInCalendar;
import tools.products_tools.Product;
import tools.sql_tools.calendar.InsertToCalendarDayTable;

import java.io.*;
import java.util.Scanner;

public class FilesTools {
    public static void main(String[] args) {

    }

    //<editor-fold desc="Make files or text  methods: makeEmptyFile, makeTextFileForProduct, makeSQLTextFileForProduct">
    public static void makeEmptyFile(String newFileName, String destination) {
        try {
            File newFile = new File(destination + "/" + newFileName + ".txt");
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

    public static void makeSQLTextFileForProduct(String fileName, String sqlStatement) {
        makeEmptyFile(fileName, Config.DESTINATION_FOR_SQL_TEXT_FILE_PRODUCTS);

        try {
            writeProductSQLToFile(sqlStatement, fileName);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    //</editor-fold>


    //<editor-fold desc="Convert file - convertFileToStringArray, convertAndPrintFileInStringArray">
    public static String[] convertFileToStringArraySeparatedByColon(String fileNameWithExtension) {
        // This int value may cause problem because max amount of lines in file is dynamic
        int maxLinesInFile = 160;
        String[] fileByLinesInArray = new String[maxLinesInFile];
        int counter = 0;
        int indexOfColon = 0;
        String line;
        try {
            Scanner fileScanner = new Scanner(new File(fileNameWithExtension));
            while (fileScanner.hasNext()) {
                line = fileScanner.nextLine();
                indexOfColon = line.lastIndexOf(":");
                if (indexOfColon + 2 > line.length()) {
                    fileByLinesInArray[counter] = line.substring(indexOfColon);
                } else {
                    fileByLinesInArray[counter] = line.substring((indexOfColon + 2));
                }

                counter++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (int i = counter; i < fileByLinesInArray.length; i++) {
            fileByLinesInArray[i] = "0";
        }

        String[] resultFileByLinesInArray = new String[counter];
        for (int i = 0; i < counter; i++) {
            resultFileByLinesInArray[i] = fileByLinesInArray[i];
        }
        return resultFileByLinesInArray;
    }

    public static String[] convertFileToStringArray(String fileNameWithExtension) throws FileNotFoundException {
        // This int value may cause problem because max amount of lines in file is dynamic
        int maxLinesInFile = 160;
        String[] fileByLinesInArray = new String[maxLinesInFile];
        int counter = 0;
        String line;
        Scanner fileScanner = new Scanner(new File(fileNameWithExtension));
        while (fileScanner.hasNext()) {
            line = fileScanner.nextLine();
            fileByLinesInArray[counter] = line;
            counter++;
        }


        for (int i = counter; i < fileByLinesInArray.length; i++) {
            fileByLinesInArray[i] = "0";
        }

        String[] resultFileByLinesInArray = new String[counter];
        for (int i = 0; i < counter; i++) {
            resultFileByLinesInArray[i] = fileByLinesInArray[i];
        }
        return resultFileByLinesInArray;
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
    //</editor-fold>


    //<editor-fold desc="Write data - writeToFileOverwriteAllFile, writeSQLStatementForDayInCalendarToTXTFile, writeProductSQLToFile, writeProductToFile">
    public static void writeSQLStatementForDayInCalendarToTXTFile(String fileName, DayInCalendar dayInCalendar) throws IOException {

        String directoryPath = "src/data_store_and_backup/text_files/days/" + dayInCalendar.getDayDateFormatFriendlyForSQL().toString();
        File theDir = new File(directoryPath);
        if (!theDir.exists()) {
            theDir.mkdirs();
        } else {
            System.out.println("Directory already exist");
        }

        String fullPath = directoryPath + "/" + fileName + ".txt";
        int counter = 0;

        String SQLquery = InsertToCalendarDayTable.createInsertSQLQueryForCalendarDay(dayInCalendar);
        if (!DirectoryTools.doesDirectoryExist(fullPath)) {

            File file = new File(fullPath);

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.append(SQLquery);
            bufferedWriter.append("\n");
            bufferedWriter.close();
        } else {
            System.out.println("File already exist");
            String alternativePathForDuplicate = directoryPath + "/" + fileName + "_duplicate" + String.valueOf(counter) + ".txt";
            while (DirectoryTools.doesDirectoryExist(alternativePathForDuplicate)) {
                counter++;
                alternativePathForDuplicate = directoryPath + "/" + fileName + "_duplicate_" + String.valueOf(counter) + ".txt";
            }

            File file = new File(alternativePathForDuplicate);

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.append(SQLquery);
            bufferedWriter.append("\n");
            bufferedWriter.close();
            if (convertFileToStringArraySeparatedByColon(fullPath).toString().equals(SQLquery)) {
                System.out.println("Files is equal:\nFirst file:");
                System.out.println(convertFileToStringArraySeparatedByColon(fullPath).toString());
                System.out.println();
                System.out.println("Second file:");
                System.out.println(SQLquery);
                System.out.println();
            }
        }
    }
    public static void writeProductSQLToFile(String lineToWriteToFile, String fileName) throws IOException {
        String fullPath = Config.DESTINATION_FOR_SQL_TEXT_FILE_PRODUCTS + fileName + ".txt";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fullPath, true));
        bufferedWriter.append(lineToWriteToFile.toString());
        bufferedWriter.append("\n");
        bufferedWriter.close();
    }
    public static void writeProductToFile(String lineToWriteToFile, String fileName) throws IOException {
        String fullPath = Config.DESTINATION_FOR_TEXT_FILE_PRODUCTS + fileName + ".txt";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fullPath, true));
        bufferedWriter.append(lineToWriteToFile.toString());
        bufferedWriter.append("\n");
        bufferedWriter.close();
    }
    public static void writeToFileOverwriteAllFile(String fullPathWithExtension, String newContent) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fullPathWithExtension, false));
            bufferedWriter.write(newContent);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void writeToFileAtEndOFFile(String fullPathWithExtension, String newContent) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fullPathWithExtension, true));
            bufferedWriter.write("\n");
            bufferedWriter.write(newContent);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //</editor-fold>


    public static void addDayStringToTextFile(String fileName, String inputSting) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Config.DESTINATION_FOR_TEXT_FILE_DAYS + fileName + ".txt", true));
        bufferedWriter.append(inputSting);
        bufferedWriter.append("\n");
        bufferedWriter.close();
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


    //<editor-fold desc="Read files to get data">
    public static String[] getStringArrayForAllFilesInDirectory(String directory) {
        // Danger and possible problem cause because max amount of files is dynamic
        int maxAmountOfFiles = 600;
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

        String[] returnedArray = new String[counter];

        for (int i = 0; i < counter; i++) {
            returnedArray[i] = fileNameAndDirectory[i];
        }
        return returnedArray;
    }

    public static String readTXTFile(String path) {
        String fileContent = "";
        if (path == null) {
            System.out.println("Path is null");
            return null;
        }

        File file = new File(path);

        if (!file.exists()) {
            System.out.println("File doesn't exist");
            return null;
        }
        FileReader fileReader;

        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while (true) {
            try {
                if (!((line = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileContent += line + "\n";
        }

        return fileContent;
    }

    public static String[] getTXTFileLineByLine(String path) {
        int amountOfLines = getAmountOfLinesInFile(path);
        String[] linesOfFile = new String[amountOfLines];
        BufferedReader bufferedReader = getBufferedReader(path);

        int counter = 0;
        String line;

        try {
            while (((line = bufferedReader.readLine()) != null)) {
                linesOfFile[counter] = line;
                counter++;
            }
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return linesOfFile;
    }


    public static String readAndGetLineTXTFile(String path, int lineNumber) {
        String fileContent = "";
        File file = new File(path);
        FileReader fileReader;
        int counter = 0;

        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while (true) {
            counter++;
            try {
                if (!((line = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (counter == lineNumber) {
                fileContent += line;
            }
        }

        return fileContent;
    }

    public static String[] readAndGetAllLinesTXTFile(String path, int lineNumber) throws IOException {
        String[] fileAllLines;
        String[] fileLinesTMP = new String[60];
        File file = new File(path);
        FileReader fileReader;
        int counter = 0;

        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while (true) {
            counter++;

            if (!((line = bufferedReader.readLine()) != null)) break;


            if (counter == lineNumber) {
                fileLinesTMP[counter] += line;
            }
        }

        fileAllLines = new String[counter];
        for (int i = 0; i < counter; i++) {
            fileAllLines[i] = fileLinesTMP[i];
        }

        return fileAllLines;
    }

    public static String[] getFullAPathToAllTextFilesInDirectory(String pathToDirectory) {
        // It may cause error: hard code length to 100

        String[] fullPathToFiles = new String[7000];
        File dir = new File(pathToDirectory);
        File[] directoryListing = dir.listFiles();
        int counter = 0;
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.isDirectory()) {
                    File directoryFiles = new File(child.getPath());
                    File[] directoryFilesArray = directoryFiles.listFiles();

                    for (File fileInDir : directoryFilesArray) {
                        fullPathToFiles[counter] = fileInDir.getPath();
                        counter++;
                    }
                }
            }
        } else {
            System.out.println("Wrong directory");
        }
        return fullPathToFiles;
    }
    //</editor-fold>

    public static void sendSQLQueryToTxtFile(DayInCalendar dayInCalendar, String addProductToDayDisplaySelectedFDateDayLabel, String amountOfProductTextField) {

        try {
            String nameAndPathOfFile = addProductToDayDisplaySelectedFDateDayLabel + "_" + dayInCalendar.getDayProductProduct().getProductName() + "_" + amountOfProductTextField;
            nameAndPathOfFile = nameAndPathOfFile.replace(" ", "_");
            FilesTools.writeSQLStatementForDayInCalendarToTXTFile(nameAndPathOfFile, dayInCalendar);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    //<editor-fold desc="Get amount of lines in file + Get File">
    public static int getAmountOfLinesInFile(String path) {
        int lines = 0;

        try {
            FileReader in;
            BufferedReader br;
            in = new FileReader(path);
            br = new BufferedReader(in);

            while ((br.readLine() != null)) {
                lines++;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lines;
    }

    public static File getFile(String path) {
        if (path == null) {
            System.out.println("Path is null");
            return null;
        }

        File file = new File(path);

        if (!file.exists()) {
            System.out.println("File doesn't exist");
            return null;
        }

        return file;
    }

    public static BufferedReader getBufferedReader(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            return bufferedReader;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


        /*
    public static int getAmountOfLinesInFile(File file){
        return -1;
    }
    */
    //</editor-fold>


}
