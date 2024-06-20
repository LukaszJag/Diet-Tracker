package runners_and_tests.run_update;

import tools.sql_tools.calendar.ChangeCalendarTextFiles;

import java.io.FileNotFoundException;

public class RunChangeTextFilesForCalendar {
    public static void main(String[] args) {
        System.out.println("Start RunChangeTextFilesForCalendar");
        try {
            ChangeCalendarTextFiles.changeAllSQLTextFilesForCalendar();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
