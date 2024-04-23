package logs;

import java.io.*;

public class LogsController {

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
