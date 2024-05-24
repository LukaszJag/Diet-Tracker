package sql_tools;

import text_files_tools.FilesTools;

import java.io.File;
import java.sql.SQLException;

public class ExportAllDaysDataToSQLCalendar {
    static String pathToDaysTXTFilesDirectory = "src/data_store_and_backup/text_files/days";

    public static void exportDataFromTxtToSQLCalendarTable() {
        getAllTXTFiles(pathToDaysTXTFilesDirectory);
    }

    public static void getAllTXTFiles(String startPath) {
        File dir = new File(startPath);
        File[] directoryListing = dir.listFiles();
        String fileName = "";
        int dotIndex = 0;

        if (directoryListing != null) {

            for (File child : directoryListing) {
                fileName = child.getName();
                dotIndex = fileName.lastIndexOf('.');

                if (child.isFile()) {
                    System.out.println(fileName.substring(dotIndex));
                    if (fileName.substring(dotIndex).equals(".txt")) {

                        try {
                            RunQuery.runQuery(FilesTools.readTXTFile(child.getPath()));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }

                if (child.isDirectory()) {
                    getAllTXTFiles(child.getPath());
                }
            }

        } else {
            System.out.println("Wrong directory");
        }
    }
}

