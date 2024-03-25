package text_files_tools;

import java.io.File;
import java.io.IOException;

public class AddToFileWriter {
    public static void main(String[] args) {
        addLineToAllFilesInDirectory("Comment optional: ", "src/text_files/products/");
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
                    MakeFoldersAndTextFile.writeProductToFile(lineToAdd, child.getName() + ".txt");
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
}

