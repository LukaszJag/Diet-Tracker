package logs;

import java.io.IOException;
import java.text.Format;
import java.util.Date;
import java.util.Formatter;

public class Log {
    String[] allLogsTypes = {"UNSET","UNCATEGORIZED","SQL INSERTION", "WARNING", "ERROR", "SQL IMPORT TXT FILES", "JAVA IMPORT SQL TABLE"};
    String logType;
    int logNumber = 0;

    Date logDateCreation;
    String logBody;

    public Log(String logBody){
        this.logNumber += 1;
        this.logDateCreation = new Date();
        this.logBody = logBody;
    };

    public Log(Date logDateCreation, String logBody) {
        this.logNumber += 1;
        this.logDateCreation = logDateCreation;
        this.logType = "UNSET";
        this.logBody = logBody;
    }
    public Log(Date logDateCreation, String logType, String logBody) {
        this.logNumber += 1;
        this.logDateCreation = logDateCreation;
        this.logType = logType;
        this.logBody = logBody;
    }

    public static void makeLogForAddNewProductToSQLTable(String logBody) throws IOException {
        LogsController.writeLineToAllLogsFile(new Log(logBody));
    }

    public String getLogReadableForm(Log log){
        String logLine = "";
        logLine = String.valueOf(log.getLogNumber()) + ":-:" + log.getLogType() +":-:" + log.getLogBody() +
                ":-:" + log.getLogDateCreation().toString() + ":-:";

        return logLine;
    }

    //<editor-fold desc="Getters and Setters">
    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public int getLogNumber() {
        return logNumber;
    }

    public void setLogNumber(int logNumber) {
        this.logNumber = logNumber;
    }

    public Date getLogDateCreation() {
        return logDateCreation;
    }

    public void setLogDateCreation(Date logDateCreation) {
        this.logDateCreation = logDateCreation;
    }

    public String getLogBody() {
        return logBody;
    }

    public void setLogBody(String logBody) {
        this.logBody = logBody;
    }
    //</editor-fold>
}
