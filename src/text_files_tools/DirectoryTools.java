package text_files_tools;

import java.io.File;

public class DirectoryTools {
    public static void makeDirectory(String path, String directoryName){
        new File(path+directoryName).mkdirs();
    }

    public static boolean doesDirectoryExist(String fullDirectoryPath){
        boolean isDirectoryExist = new File(fullDirectoryPath).exists();
        return isDirectoryExist;
    }

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
}
