package text_files_tools;

import Ganeral.Config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MakeFoldersAndTextFile {
    public static void makeEmptyFile(String newFileName, String destination){
        try{
            File newFile = new File( destination + newFileName + ".txt");
            if(newFile.createNewFile()){
                System.out.println("File created: " + newFile.getName());
            }else{
                System.out.println("File already exists.");
            }
        }catch (IOException e){
            System.out.println("An error occurred. - Class MakeTextFile -> makeEmptyFile");
            e.printStackTrace();
        }
    }

    private void makeDirectory(String newDirectoryName, String path){
        if(path.equals("")){
            path = Config.DESTINATION_FOR_TEXT_FILE_DAYS;
        }
    }

    public static void writeProductToFile(String lineToWriteToFile, String fileName) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Config.DESTINATION_FOR_TEXT_FILE_PRODUCTS + fileName + ".txt", true));
        bufferedWriter.append(lineToWriteToFile.toString());
        bufferedWriter.append("\n");
        bufferedWriter.close();
    }
}
