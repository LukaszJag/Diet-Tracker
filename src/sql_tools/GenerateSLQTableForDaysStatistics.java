package sql_tools;

import configuration.Config;
import text_files_tools.FilesTools;

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
        String[] dataForDaysStatistics = {dateInString, "0", "0", "0", "0", "0", "0", correctDate};
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

    public static void generateWholeMonth() {
        String year = "2024";
        String month = "05";
        String[] readyDateDays = new String[31];
        for (int i = 0; i < 31; i++) {
            if (String.valueOf(i+1).length() == 1){
                readyDateDays[i] = year + "-" + month + "-" + "0" + (i+1);
            }else {
                readyDateDays[i] = year + "-" + month + "-"  + (i+1);
            }
        }

        for (int i = 0; i < readyDateDays.length; i++) {
            try {
                generateDaysStatisticsInTable(createInsertSQLQueryForDaysStatistics(readyDateDays[i]));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    public static void generateWholeMonthAndFillAmountOfPointsFromNotepadMAY(){
        String queryForExecute = "";
        String dateDay = "";
        for (int i = 1; i <= 31; i++) {
            dateDay = String.valueOf(i);
            if (dateDay.length() == 1){
                dateDay = "0" + dateDay;
            }
            queryForExecute = "UPDATE `diet_tracker_schema`.`days_statistics_test`" +
            "SET "
            + "`amount_of_points_from_notepad`= " + FilesTools.readAndGetLineTXTFile("src/data_store_and_backup/text_files/days_statistics_test/quick_fill_amount_of_point_in_notepad/may_2024", i)
            + " WHERE day_date = '2024-05-" +dateDay +  "';";
            System.out.println(queryForExecute);
            try {
                generateDaysStatisticsInTable(queryForExecute);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
