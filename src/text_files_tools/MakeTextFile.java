package text_files_tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MakeTextFile {
    public static void makeEmptyFile(String newFileName){
        try{
            File newFile = new File("src/TextFiles/" + newFileName + ".txt");
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

    public static void writeToFile(String lineToWriteToFile, String fileName) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/TextFiles/" + fileName + ".txt", true));
        bufferedWriter.append(lineToWriteToFile);
        bufferedWriter.append("\n");
        bufferedWriter.close();
    }
}
