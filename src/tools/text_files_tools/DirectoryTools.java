package tools.text_files_tools;

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

    public static void printAllFilesInAllDirectory(String startDirectory){
        File dir = new File(startDirectory);
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {
            for (File child : directoryListing) {

                if(child.isDirectory()){
                    printAllFilesInDirectory(child.getPath());
                }
            }
        } else {
            System.out.println("Wrong directory");
        }
    }

    public static String[] getPathForAllFilesInAllDirectory(String startDirectory){
        // hard code length - may cause error
        String[] allFilesPath = new String[500];
        File dir = new File(startDirectory);
        File[] directoryListing = dir.listFiles();

        int counter = 0;
        if (directoryListing != null) {
            for (File child : directoryListing) {

                if(child.isDirectory()){
                    File[] directoryListingSecondLevel = child.listFiles();
                    for (File childSecondLevel : directoryListingSecondLevel) {
                        if (childSecondLevel.isDirectory()){
                            System.out.println("Directory in lower level - there should be only files - error");
                            System.out.println(childSecondLevel.toString());
                            return null;
                        }
                        allFilesPath[counter] = childSecondLevel.getPath();
                        counter++;
                    }

                }
            }
        } else {
            System.out.println("Wrong directory");
        }

        String[] resultArray = new String[counter];
        for (int i = 0; i < counter; i++) {
            resultArray[i] = allFilesPath[i];
        }

        return resultArray;
    }
}
