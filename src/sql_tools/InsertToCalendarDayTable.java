package sql_tools;

import calendar_tools.DayInCalendar;
import configuration.Config;
import products_tools.Product;
import text_files_tools.FilesTools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class InsertToCalendarDayTable {
    public static void insertLineToCalendarDayTable(Product product) throws SQLException {
        Connection connection = GetConnection.getConnectionWithLocalHost();
        // String sql = createInsertSQLQueryForProductTable(product);
       // PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // preparedStatement.execute(sql);
    }

    public static void addRowToCalendarTable(DayInCalendar dayInCalendar) throws SQLException {
        Connection connection = GetConnection.getConnectionWithLocalHost();
        String sqlStatement = createInsertSQLQueryForCalendarDay(dayInCalendar);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.execute(sqlStatement);
    }

    public static void addRowToCalendarTable(DayInCalendar dayInCalendar, float amountOfProduct) throws SQLException {
        Connection connection = GetConnection.getConnectionWithLocalHost();
        String sqlStatement = createInsertSQLQueryForCalendarDay(dayInCalendar, amountOfProduct);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.execute(sqlStatement);
        String nameOfDayTextFile = new SimpleDateFormat("yyyy-MM-dd").format(dayInCalendar.getDayDate()) + "_" +
                dayInCalendar.getDayDateDayName() + "_" +
                dayInCalendar.getDayProductProduct().getProductName() + "_" +
                String.valueOf(dayInCalendar.getDayAmountOfProduct());
        try {
            FilesTools.addDayStringToTextFile(nameOfDayTextFile, sqlStatement);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String createInsertSQLQueryForCalendarDay(DayInCalendar dayToInsert){
        // Set head of query
        String sqlStatement = "INSERT INTO `diet_tracker_schema`." + Config.CURRENT_DATABASE_TABLE_CALENDAR + "\n";
        sqlStatement += "(";

        // Set columns name in query
        for (int i = 0; i < Config.SQL_COLUMNS_CALENDAR.length; i++) {

            sqlStatement += "`" + Config.SQL_COLUMNS_CALENDAR[i] + "`";

            if (i == Config.SQL_COLUMNS_CALENDAR.length - 1){
                sqlStatement += ")";
            }else {
                sqlStatement += ",\n";
            }
        }

        // Set Values verse
        sqlStatement += "\nValues\n(";
        String[] dayDataInArray = dayToInsert.dayDataInStringArray(dayToInsert);

        for (int i = 0; i < Config.SQL_COLUMNS_CALENDAR.length; i++) {
            //System.out.println("[i]: " + i + " - " + dayDataInArray[i]);

            // Take care to float value ends with .f
            if(i == 0 || i == 1 || i == 3 || i == 8 || i == 9) {
                sqlStatement += "'" + dayDataInArray[i] + "'";
            }else{
                sqlStatement += dayDataInArray[i];
            }

            if (i != Config.SQL_COLUMNS_CALENDAR.length - 1){
                    sqlStatement +=  ",\n";
            }else {
                //sqlStatement += "\n";
            }
        }
        sqlStatement += ");";

        System.out.println();
        System.out.println("InsertToCalendarDayTable -> createInsertSQLQueryForCalendarDay");
        System.out.println("SQL Statement: " + sqlStatement);

        return  sqlStatement;
    }

    public static String createInsertSQLQueryForCalendarDay(DayInCalendar dayToInsert, float amountOfProduct){
        System.out.println("\nInsertToCalendarDayTable -> createInsertSQLQueryForCalendarDay:");
        // Set head of query
        String sqlStatement = "INSERT INTO `diet_tracker_schema`." + Config.CURRENT_DATABASE_TABLE_CALENDAR + "\n";
        sqlStatement += "(";

        // Set columns name in query
        for (int i = 0; i < Config.SQL_COLUMNS_CALENDAR_WITH_KCAL_CONSUME.length; i++) {

            sqlStatement += "`" + Config.SQL_COLUMNS_CALENDAR_WITH_KCAL_CONSUME[i] + "`";

            if (i == Config.SQL_COLUMNS_CALENDAR_WITH_KCAL_CONSUME.length - 1){
                sqlStatement += ")";
            }else {
                sqlStatement += ",\n";
            }
        }

        // Set Values verse
        sqlStatement += "\nValues\n(";
        String[] dayDataInArray = dayToInsert.dayDataInStringArray(dayToInsert);




        float kcal_consume = amountOfProduct * dayToInsert.getDayProductMacro().getKcal();
        float carbs_consume = amountOfProduct * dayToInsert.getDayProductMacro().getCarbs();
        float fat_consume = amountOfProduct * dayToInsert.getDayProductMacro().getFat();
        float protein_consume = amountOfProduct * dayToInsert.getDayProductMacro().getProtein();

        String[] consume_values = {String.valueOf(kcal_consume), String.valueOf(carbs_consume), String.valueOf(fat_consume), String.valueOf(protein_consume)};


        String[] allDataToInsert = new String[dayDataInArray.length + consume_values.length];

        int counter = 0;
        for (; counter < dayDataInArray.length; counter++) {
            allDataToInsert[counter] = dayDataInArray[counter];
        }


        System.out.println();
        System.out.println("First print: \n");
        for (int i = 0; i < allDataToInsert.length; i++) {
            System.out.println("i = [" + i + "]: " + allDataToInsert[i]);
        }
        System.out.println("\nCounter: " + counter);
        System.out.println();

        for (int i = 0; i < consume_values.length; i++) {
            System.out.println();
            System.out.println(counter - dayDataInArray.length + " " + consume_values[i]);
            System.out.println();
            allDataToInsert[counter] = consume_values[i];
        }

        System.out.println();
        System.out.println("Print: \n");
        for (int i = 0; i < allDataToInsert.length; i++) {
            System.out.println("i = [" + i + "]: " + allDataToInsert[i]);
        }
        System.out.println();



        System.out.println("All columns data: ");
        for (int i = 0; i < Config.SQL_COLUMNS_CALENDAR_WITH_KCAL_CONSUME.length; i++) {
            System.out.println("[i]: " + i + " - " + allDataToInsert[i]);
            // Take care to float value ends with .f
            if(i == 0 || i == 1 || i == 3 || i == 8 || i == 9) {
                sqlStatement += "'" + allDataToInsert[i] + "'";
            }else{
                sqlStatement += allDataToInsert[i];
            }

            if (i != Config.SQL_COLUMNS_CALENDAR_WITH_KCAL_CONSUME.length - 1){
                sqlStatement +=  ",\n";
            }else {
                //sqlStatement += "\n";
            }
        }
        sqlStatement += ");";

        System.out.println("SQL Statement:\n" + sqlStatement);
        System.out.println();

        return  sqlStatement;
    }
}
