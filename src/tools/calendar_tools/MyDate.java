package tools.calendar_tools;

import configuration.Config;

import java.text.Format;
import java.text.SimpleDateFormat;

public class MyDate {
    public static int getAmountOfDaysInMonth(String month) {
        if (month.equals("January")) {
            return 31;
        }
        if (month.equals("February")) {
            return 28;
        }
        if (month.equals("March")) {
            return 31;
        }
        if (month.equals("April")) {
            return 30;
        }
        if (month.equals("May")) {
            return 31;
        }
        if (month.equals("June")) {
            return 30;
        }
        if (month.equals("July")) {
            return 31;
        }
        if (month.equals("August")) {
            return 31;
        }
        if (month.equals("September")) {
            return 30;
        }
        if (month.equals("October")) {
            return 31;
        }
        if (month.equals("November")) {
            return 30;
        }
        if (month.equals("December")) {
            return 31;
        }
        return -1;
    }

    public static int getNumberOfMonthInYear(String month) {
        if (month.equalsIgnoreCase("January")) {
            return 1;
        }
        if (month.equalsIgnoreCase("February")) {
            return 2;
        }
        if (month.equalsIgnoreCase("March")) {
            return 3;
        }
        if (month.equalsIgnoreCase("April")) {
            return 4;
        }
        if (month.equalsIgnoreCase("May")) {
            return 5;
        }
        if (month.equalsIgnoreCase("June")) {
            return 6;
        }
        if (month.equalsIgnoreCase("July")) {
            return 7;
        }
        if (month.equalsIgnoreCase("August")) {
            return 8;
        }
        if (month.equalsIgnoreCase("September")) {
            return 9;
        }
        if (month.equalsIgnoreCase("October")) {
            return 10;
        }
        if (month.equalsIgnoreCase("November")) {
            return 11;
        }
        if (month.equalsIgnoreCase("December")) {
            return 12;
        }
        return -1;
    }

    public static String getNameOfMonthFromNumber(int month) {

        if (month == 1) {
            return "January";
        }

        if (month == 2) {
            return "February";
        }

        if (month == 3) {
            return "March";
        }

        if (month == 4) {
            return "April";
        }


        if (month == 5) {
            return "May";
        }

        if (month == 6) {
            return "June";
        }

        if (month == 7) {
            return "July";
        }


        if (month == 8) {
            return "August";
        }

        if (month == 9) {
            return "September";
        }

        if (month == 10) {
            return "October";
        }

        if (month == 11) {
            return "November";
        }

        if (month == 12) {
            return "December";
        }

        return "-1";
    }

    public static String getCurrentDayInSQLFormat(){
         return new SimpleDateFormat("yyyy-MM-dd").format(Config.date);
    }

    public static String getDayNameInPascalCase(){
        Format format = new SimpleDateFormat("EEEE");
        java.util.Date utilDateImport = new java.util.Date();
        String dayNameCurrentDatePascalCase = format.format(utilDateImport);
        return dayNameCurrentDatePascalCase;
    }

    public static String getDayNameInLowerCase(){
        Format format = new SimpleDateFormat("EEEE");
        java.util.Date utilDateImport = new java.util.Date();
        String dayNameCurrentDateLowerCase = format.format(utilDateImport);
        return dayNameCurrentDateLowerCase.toLowerCase();
    }
}
