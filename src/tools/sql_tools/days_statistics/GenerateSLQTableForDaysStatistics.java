package tools.sql_tools.days_statistics;

import configuration.Config;
import tools.calendar_tools.MyDate;
import tools.sql_tools.general.GetConnection;
import tools.sql_tools.general.IsTheRowAlreadyExist;
import tools.text_files_tools.FilesTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateSLQTableForDaysStatistics {
    public static void generateDaysStatisticsInTable(String sqlStatement) throws SQLException {
        Connection connection = GetConnection.getConnectionWithLocalHost();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.execute(sqlStatement);
    }

    public static String createInsertSQLQueryForDaysStatistics(String dateInString) {
        String sqlStatement = "INSERT INTO `diet_tracker_schema`." + "`" + "days_statistics_test" + "`\n";
        sqlStatement += "(";

        for (int i = 0; i < Config.SQL_COLUMNS_DAYS_STATISTICS_TEST.length; i++) {

            sqlStatement += Config.SQL_COLUMNS_DAYS_STATISTICS_TEST[i];

            if (i == Config.SQL_COLUMNS_DAYS_STATISTICS_TEST.length - 1) {
                sqlStatement += ")";
            } else {
                sqlStatement += ",\n";
            }
        }
        sqlStatement += "\nValues\n(";


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);

        Date date = null;

        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //specifies the pattern to print
        sdf.applyPattern("EEEE");
        String correctDate = sdf.format(date);


        String dayNameInString = "Monday";
        String[] dataForDaysStatistics = {dateInString, "0", "0", "null", "null", "null", "null", correctDate};
        for (int i = 0; i < Config.SQL_COLUMNS_DAYS_STATISTICS_TEST.length; i++) {

            // Take care to float value ends with .f
            if (i == 0 || i == 7) {
                sqlStatement += "'" + dataForDaysStatistics[i] + "'";
            } else {
                sqlStatement += dataForDaysStatistics[i];
            }

            if (i != Config.SQL_COLUMNS_DAYS_STATISTICS_TEST.length - 1) {
                sqlStatement += ",\n";
            }
        }

        sqlStatement += ");";

        return sqlStatement;
    }

    public static void generateWholeMonth(String month, int year) {
        int daysInMonth = MyDate.getAmountOfDaysInMonth(month);

        if (month.equals("December")) {
            month = "12";
        }
        if (month.equals("November")) {
            month = "11";
        }
        if (month.equals("October")) {
            month = "10";
        }
        if (month.equals("September")) {
            month = "09";
        }
        if (month.equals("August")) {
            month = "08";
        }
        if (month.equals("July")) {
            month = "07";
        }
        if (month.equals("June")) {
            month = "06";
        }
        if (month.equals("May")) {
            month = "05";
        }
        if (month.equals("April")) {
            month = "04";
        }

        if (month.equals("January")) {
            month = "01";
        }

        if (month.equals("February")) {
            month = "02";
        }

        if (month.equals("March")) {
            month = "03";
        }


        String[] readyDateDays = new String[daysInMonth];
        for (int i = 0; i < readyDateDays.length; i++) {
            if (String.valueOf(i + 1).length() == 1) {
                readyDateDays[i] = year + "-" + month + "-" + "0" + (i + 1);
            } else {
                readyDateDays[i] = year + "-" + month + "-" + (i + 1);
            }
        }

        for (int i = 0; i < readyDateDays.length; i++) {
            if (!IsTheRowAlreadyExist.isTheDayAlreadyExist("days_statistics_test", "day_date", readyDateDays[i])) {
                try {
                    generateDaysStatisticsInTable(createInsertSQLQueryForDaysStatistics(readyDateDays[i]));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void generateWholeMonthAndFillAmountOfPointsFromNotepad(String month, int year) {
        String queryForExecute = "";
        String dateDay = "";
        String pointInOneDay = "";

        int daysInMonth = MyDate.getAmountOfDaysInMonth(month);
        String numberOfMonthInYear = String.valueOf(MyDate.getNumberOfMonthInYear(month));

        if (numberOfMonthInYear.length() == 1) {
            numberOfMonthInYear = "0" + numberOfMonthInYear;
        }

        String txtFile = month.toLowerCase() + "_" + year + ".txt";

        for (int i = 1; i <= daysInMonth; i++) {
            dateDay = String.valueOf(i);
            if (dateDay.length() == 1) {
                dateDay = "0" + dateDay;
            }
            pointInOneDay = FilesTools.readAndGetLineTXTFile("src/data_store_and_backup/text_files/days_statistics_test/"
                    + year + "/" + txtFile, i);

            queryForExecute = "UPDATE `diet_tracker_schema`.`days_statistics_test`" +
                    "SET "
                    + "`amount_of_points_from_notepad`= " + pointInOneDay
                    + " WHERE day_date = '" + year + "-" + numberOfMonthInYear + "-" + dateDay + "';";


            if (pointInOneDay.equals("")) {
                return;
            }

            try {
                generateDaysStatisticsInTable(queryForExecute);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void generateWholeMonthAndFillAmountOfPointsFromNotepad(int numberOfDay, String month, int year) {
        String queryForExecute = "";
        String dateDay = "";
        String pointInOneDay = "";

        String numberOfMonthInYear = String.valueOf(MyDate.getNumberOfMonthInYear(month));

        if (numberOfMonthInYear.length() == 1) {
            numberOfMonthInYear = "0" + numberOfMonthInYear;
        }

        String txtFile = month.toLowerCase() + "_" + year + ".txt";

        if (numberOfDay < 10) {
            dateDay = "0" + numberOfDay;
        } else {
            dateDay = String.valueOf(numberOfDay);
        }

        pointInOneDay = FilesTools.readAndGetLineTXTFile("src/data_store_and_backup/text_files/days_statistics_test/quick_fill_amount_of_point_in_notepad/" + txtFile, numberOfDay);
        queryForExecute = "UPDATE `diet_tracker_schema`.`days_statistics_test`" +
                "SET "
                + "`amount_of_points_from_notepad`= " + pointInOneDay
                + " WHERE day_date = '" + year + "-" + numberOfMonthInYear + "-" + dateDay + "';";
        if (pointInOneDay.equals("")) {
            return;
        }

        try {
            generateDaysStatisticsInTable(queryForExecute);
            System.out.println(queryForExecute);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
