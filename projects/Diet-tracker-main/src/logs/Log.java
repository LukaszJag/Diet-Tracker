package logs;

import configuration.Config;
import tools.calendar_tools.DayInCalendar;
import tools.products_tools.Macro;
import tools.products_tools.Product;
import tools.text_files_tools.FilesTools;
import tools.time_date_tools.DateTools;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {


    //<editor-fold desc="Tags">
    String[] allLogsTypes = {"UNSET", "UNCATEGORIZED", "SQL INSERTION", "WARNING", "ERROR",
            "SQL IMPORT TXT FILES", "JAVA IMPORT SQL TABLE",
            "ADD PRODUCT TO CALENDAR TABLE BY GUI" + "ADD PRODUCT TO PRODUCT TABLE BY GUI"};

    String addNewProductToCalendarTableTag = "ADD PRODUCT TO CALENDAR TABLE BY GUI";
    String addNewProductToProductTableTag = "ADD PRODUCT TO PRODUCT TABLE BY GUI";
    String errorTag = "ERROR";
    //</editor-fold>


    String logType;
    int logNumber;

    Date logDateCreation;
    String logBody;

    /*
    (dayInCalendarProduct.getProductName(), dayInCalendar.getDayProductMacro(),
                dayInCalendar.getDayAmountOfProduct(), dayInCalendar.getDayDateDayName(), dayInCalendar.getMealName() ,dayInCalendar.getDayProductProduct(),
                dayInCalendar.getConsumedMacro())
     */
    public static void addNewLogForProductToCalendarGUIAccept(String dayDateFormatFriendlyForSQL, String productName, Macro productMacro, float amountOfProduct,
                                                              String dayDateDayName, String mealName, Product dayProductProduct,
                                                              Macro consumedMacro, DayInCalendar dayInCalendar) {
        String logID = "";
        logID += getLogID();
        increaseLogAmountByOne();

        String date = dayInCalendar.getDayDateFormatFriendlyForSQL();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateString = format.format(new Date());

        String currentCalendarTable = Config.CURRENT_DATABASE_TABLE_CALENDAR;

        // It may cause error hard code value: tag
        String logBody = logID + ":-:" + currentDateString + ":-:" + "ADD PRODUCT TO CALENDAR TABLE BY GUI" + ":-:" + currentCalendarTable + ":-:" + date + ":-:" + dayDateDayName + ":-:" + productName + ":-:" + amountOfProduct + ":-:" + Macro.getShortMacroInformation(productMacro) +
                ":-:" + mealName + ":-:" + dayProductProduct.getProductBrand() + ":-:" + Macro.getShortMacroInformation(consumedMacro) + ":-:";

        // It may cause error hard code value: path
        FilesTools.writeToFileAtEndOFFile("src/logs/all_logs.txt", logBody);
        System.out.println("Log for calendar window: has been added");

    }

    public static void makeLogForAddNewProductToSQLTable(
            String productName,
            String brand,
            String packageHas,
            String macroFor,
            Macro productMacro) {
        String logID = "";
        logID += getLogID();
        increaseLogAmountByOne();

        String currentDateString = DateTools.getCurrentDateSQLFriendlyFormat();

        String currentProductTable = Config.CURRENT_DATABASE_TABLE_PRODUCT;
        String logBody = logID + ":-:" + currentDateString + ":-:" + "ADD PRODUCT TO PRODUCT TABLE BY GUI" + ":-:" + currentProductTable + ":-:" + productName
                + ":-:" + brand + ":-:" + Macro.getShortMacroInformation(productMacro) +
                ":-:" + packageHas + ":-:" + macroFor + ":-:";

        FilesTools.writeToFileAtEndOFFile("src/logs/all_logs.txt", logBody);
        System.out.println("Log for product window: has been added");
    }

    public String getLogReadableForm(Log log) {
        String logLine = "";
        logLine = String.valueOf(log.getLogNumber()) + ":-:" + log.getLogType() + ":-:" + log.getLogBody() +
                ":-:" + log.getLogDateCreation().toString() + ":-:";

        return logLine;
    }

    //<editor-fold desc="Handling log ID part">
    public static int getLogID() {
        int logID = -1;

        String getLogID = FilesTools.readAndGetLineTXTFile("src/logs/logID.txt", 1);
        logID = Integer.valueOf(getLogID);
        return logID;
    }

    public static void increaseLogAmountByOne() {
        int logIDNumber = getLogID();
        String newID = String.valueOf(logIDNumber + 1);
        FilesTools.writeToFileOverwriteAllFile("src/logs/logID.txt", newID);
    }
    //</editor-fold>


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

    public String getAddNewProductToCalendarTableTag() {
        return addNewProductToCalendarTableTag;
    }

    public void setAddNewProductToCalendarTableTag(String addNewProductToCalendarTableTag) {
        this.addNewProductToCalendarTableTag = addNewProductToCalendarTableTag;
    }

    public String getAddNewProductToProductTableTag() {
        return addNewProductToProductTableTag;
    }

    public void setAddNewProductToProductTableTag(String addNewProductToProductTableTag) {
        this.addNewProductToProductTableTag = addNewProductToProductTableTag;
    }

    public String getErrorTag() {
        return errorTag;
    }

    public void setErrorTag(String errorTag) {
        this.errorTag = errorTag;
    }
    //</editor-fold>
}
