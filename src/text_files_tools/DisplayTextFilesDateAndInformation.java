package text_files_tools;

import java.io.File;

public class DisplayTextFilesDateAndInformation {
    public static void printAllFilesInDirectory(String directory) {
        File dir = new File(directory);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                System.out.println(child.toString());
            }
        } else {
            System.out.println("Wrong directory");
        }
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
