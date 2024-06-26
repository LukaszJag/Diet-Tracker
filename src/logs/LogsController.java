package logs;

import tools.text_files_tools.FilesTools;
import tools.text_tools.TextTools;

import java.io.*;
import java.text.ParseException;

public class LogsController {
    public static int getAmountOfLogs(){
        int amountOfLogs = -1;

        String amountOfLogsInTextFile = FilesTools.readTXTFile("src/logs/configOfLogs.txt");
        String amountOfLogsInTextFileWithoutBlankSigns;
        amountOfLogsInTextFileWithoutBlankSigns = TextTools.getRidOfAllBlankSigns(amountOfLogsInTextFile);

        try{
            amountOfLogs = Integer.valueOf(amountOfLogsInTextFileWithoutBlankSigns);
        }catch (Exception e){
            System.out.println("Content of configOfLogs.txt cannot be convert to int");
        }
        return amountOfLogs;
    }

    static BufferedWriter bufferedWriter;
    public static BufferedWriter createBufferedWriter(){
        File file = new File("src/logs/all_logs.txt");
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bufferedWriter;
    }

    public BufferedReader createBufferedReader(){
        File file = new File("src/logs/all_logs.txt");
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bufferedReader;
    }

    public static void writeLineToAllLogsFile(Log log) throws IOException {
        System.out.println("\nLog Form:");
        System.out.println(log.getLogReadableForm(log));
        createBufferedWriter();
        bufferedWriter.append(log.getLogReadableForm(log));
        bufferedWriter.append("\n");
        bufferedWriter.close();
    }

    public void printAllLogs() throws IOException {
        String line = "";
        if((line = createBufferedReader().readLine()) != null){
            System.out.println(line);
        }
        System.out.println("\nEND OF LOGS\n\n");
    }
}
