package tools.calendar_tools;

import configuration.Config;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MyDate {
    //<editor-fold desc="Get amount of - (...)">
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

    public static int getAmountOfDaysInMonthIfContainsItsName(String month) {
        int amountOfDays = -1;
        int amountOfDetectedMonth = 0;
        if (month.toLowerCase().contains("january")) {
            amountOfDays = 31;
            amountOfDetectedMonth++;
        }
        if (month.toLowerCase().contains("february")) {
            amountOfDays = 28;
            amountOfDetectedMonth++;
        }
        if (month.toLowerCase().contains("march")) {
            amountOfDays = 31;
            amountOfDetectedMonth++;
        }
        if (month.toLowerCase().contains("april")) {
            amountOfDays = 30;
            amountOfDetectedMonth++;
        }
        if (month.toLowerCase().contains("may")) {
            amountOfDays = 31;
            amountOfDetectedMonth++;
        }
        if (month.toLowerCase().contains("june")) {
            amountOfDays = 30;
            amountOfDetectedMonth++;
        }
        if (month.toLowerCase().contains("july")) {
            amountOfDays = 31;
            amountOfDetectedMonth++;
        }
        if (month.toLowerCase().contains("august")) {
            amountOfDays = 31;
            amountOfDetectedMonth++;
        }
        if (month.toLowerCase().contains("september")) {
            amountOfDays = 30;
            amountOfDetectedMonth++;
        }
        if (month.toLowerCase().contains("october")) {
            amountOfDays = 31;
            amountOfDetectedMonth++;
        }
        if (month.toLowerCase().contains("november")) {
            amountOfDays = 30;
            amountOfDetectedMonth++;
        }
        if (month.toLowerCase().contains("december")) {
            amountOfDays = 31;
            amountOfDetectedMonth++;
        }

        if (amountOfDays > 1) {

            System.out.println("Detected more then one month in input string");
            // TODO - 14.12.25
            //throw new IOException("Detected more then one month in input string");
        }


        if (amountOfDays > 1) {
            System.out.println();
        }
        return amountOfDays;
    }

    public static int getAmountOfDaysInMonth(int month) {
        if (month == 1) {
            return 31;
        }
        if (month == 2) {
            return 28;
        }
        if (month == 3) {
            return 31;
        }
        if (month == 4) {
            return 30;
        }
        if (month == 5) {
            return 31;
        }
        if (month == 6) {
            return 30;
        }
        if (month == 7) {
            return 31;
        }
        if (month == 8) {
            return 31;
        }
        if (month == 9) {
            return 30;
        }
        if (month == 10) {
            return 31;
        }
        if (month == 11) {
            return 30;
        }
        if (month == 12) {
            return 31;
        }
        return -1;
    }

    //</editor-fold>

    //<editor-fold desc="Get number of - (...)">
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

    //</editor-fold>

    //<editor-fold desc="Get name of - (...)">
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

    public static String getNameOfMonthFromNumberSQLFormat(int month) {
        if (month == 1) {
            return "01";
        }

        if (month == 2) {
            return "02";
        }

        if (month == 3) {
            return "03";
        }

        if (month == 4) {
            return "04";
        }


        if (month == 5) {
            return "05";
        }

        if (month == 6) {
            return "06";
        }

        if (month == 7) {
            return "07";
        }


        if (month == 8) {
            return "08";
        }

        if (month == 9) {
            return "09";
        }

        if (month == 10) {
            return "10";
        }

        if (month == 11) {
            return "11";
        }

        if (month == 12) {
            return "12";
        }

        return "-1";
    }

    public static String getNameOfMonthFromNumberSQLFormat(String month) {
        if (month.equalsIgnoreCase("January")) {
            return "01";
        }
        if (month.equalsIgnoreCase("February")) {
            return "02";
        }
        if (month.equalsIgnoreCase("March")) {
            return "03";
        }
        if (month.equalsIgnoreCase("April")) {
            return "04";
        }
        if (month.equalsIgnoreCase("May")) {
            return "05";
        }
        if (month.equalsIgnoreCase("June")) {
            return "06";
        }
        if (month.equalsIgnoreCase("July")) {
            return "07";
        }
        if (month.equalsIgnoreCase("August")) {
            return "08";
        }
        if (month.equalsIgnoreCase("September")) {
            return "09";
        }
        if (month.equalsIgnoreCase("October")) {
            return "10";
        }
        if (month.equalsIgnoreCase("November")) {
            return "11";
        }
        if (month.equalsIgnoreCase("December")) {
            return "12";
        }
        return "-1";
    }

    // TODO
    public static int getNameOfMonthContainsInString(String fullStringContainsMonth) {
        int counterMonthsInText = 0;
        int monthNumber = -2;
        if (fullStringContainsMonth.toLowerCase().contains("january")) {
            monthNumber = 1;
            counterMonthsInText++;
        }
        if (fullStringContainsMonth.toLowerCase().contains("february")) {
            monthNumber = 2;
            counterMonthsInText++;
        }
        if (fullStringContainsMonth.toLowerCase().contains("march")) {
            monthNumber = 3;
            counterMonthsInText++;
        }
        if (fullStringContainsMonth.toLowerCase().contains("april")) {
            monthNumber = 4;
            counterMonthsInText++;
        }
        if (fullStringContainsMonth.toLowerCase().contains("may")) {
            monthNumber = 5;
            counterMonthsInText++;
        }
        if (fullStringContainsMonth.toLowerCase().contains("june")) {
            monthNumber = 6;
            counterMonthsInText++;
        }
        if (fullStringContainsMonth.toLowerCase().contains("july")) {
            monthNumber = 7;
            counterMonthsInText++;
        }
        if (fullStringContainsMonth.toLowerCase().contains("august")) {
            monthNumber = 8;
            counterMonthsInText++;
        }
        if (fullStringContainsMonth.toLowerCase().contains("september")) {
            monthNumber = 9;
            counterMonthsInText++;
        }
        if (fullStringContainsMonth.toLowerCase().contains("october")) {
            monthNumber = 10;
            counterMonthsInText++;
        }
        if (fullStringContainsMonth.toLowerCase().contains("november")) {
            monthNumber = 11;
            counterMonthsInText++;
        }
        if (fullStringContainsMonth.toLowerCase().contains("december")) {
            monthNumber = 12;
            counterMonthsInText++;
        }

        // TODO
        if (counterMonthsInText > 1) {
            return -100;
        } else if (counterMonthsInText == 1) {
            return monthNumber;
        } else {
            return -1;
        }
    }

    public static String getDayNameInPascalCase() {
        Format format = new SimpleDateFormat("EEEE");
        java.util.Date utilDateImport = new java.util.Date();
        String dayNameCurrentDatePascalCase = format.format(utilDateImport);
        return dayNameCurrentDatePascalCase;
    }

    public static String getDayNameInLowerCase() {
        Format format = new SimpleDateFormat("EEEE");
        java.util.Date utilDateImport = new java.util.Date();
        String dayNameCurrentDateLowerCase = format.format(utilDateImport);
        return dayNameCurrentDateLowerCase.toLowerCase();
    }
    //</editor-fold>

    //<editor-fold desc="Get date in (...) format">
    public static String[] getAllDaysInMonthAndYearSQLFriendlyFormat(int month, int year) {
        String[] allDaysInSQLFormat = new String[getAmountOfDaysInMonth(month)];

        String monthPart = "";
        if (month < 10) {
            monthPart = "0" + month;
        } else {
            monthPart = "" + month;
        }

        for (int i = 0; i < allDaysInSQLFormat.length; i++) {
            if (i < 10) {
                allDaysInSQLFormat[i] = "" + year + "-" + monthPart + "-" + "0" + (i + 1);
            } else {
                allDaysInSQLFormat[i] = "" + year + "-" + monthPart + "-" + (i + 1);
            }
        }

        return allDaysInSQLFormat;
    }

    public static String getNextDayDateSQLFriendlyFormat(String dateSQLFormat) {
        String dt = dateSQLFormat;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        c.add(Calendar.DATE, 1);  // number of days to add
        dt = sdf.format(c.getTime());
        return dt;
    }

    public static String getNextDayDateName(String dayName) {
        String dt = dayName;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        c.add(Calendar.DAY_OF_WEEK, 1);  // number of days to add
        dt = sdf.format(c.getTime());
        return dt;
    }

    public static String getPreviousDayDateName(String dayName) {
        String dt = dayName;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        c.add(Calendar.DAY_OF_WEEK, -1);  // number of days to add
        dt = sdf.format(c.getTime());
        return dt;
    }

    public static String getPreviousDayDateSQLFriendlyFormat(String dateSQLFormat) {
        String dt = dateSQLFormat;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        c.add(Calendar.DATE, -1);  // number of days to add
        dt = sdf.format(c.getTime());
        return dt;
    }

    // TODO
    public static String getNextDateFromMontAndYearSQLFriendlyFormat(String monthInNumber, String year){
        String dt = year + "-" + monthInNumber;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        c.add(Calendar.MONTH, 1);  // number of months to add
        dt = sdf.format(c.getTime());
        return dt;
    }

    public static String getNextDateFromMontAndYearSQLFriendlyFormat(int month, int year){
        String monthInNumber = "";
        if (month < 10){
            monthInNumber += "0" + month;
        }else {
            monthInNumber = month + "";
        }

        String dt = year + "-" + monthInNumber;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        c.add(Calendar.MONTH, 1);  // number of months to add
        dt = sdf.format(c.getTime());
        return dt;
    }

    public static String getPreviousDateFromMontAndYearSQLFriendlyFormat(String monthInNumber, String year){
        String dt = year + "-" + monthInNumber;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        c.add(Calendar.DATE, -1);  // number of months to add
        dt = sdf.format(c.getTime());
        return dt;
    }

    public static String getPreviousDateFromMontAndYearSQLFriendlyFormat(int month, int year){
        String monthInNumber = "";
        if (month < 10){
            monthInNumber += "0" + month;
        }else {
            monthInNumber = month + "";
        }

        String dt = year + "-" + monthInNumber;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        c.add(Calendar.DATE, -1);  // number of months to add
        dt = sdf.format(c.getTime());
        return dt;
    }
    //</editor-fold>

    //GetDateFrom
    public static String getYearFromSQLFriendlyFormatDate(String date){
        return date.substring(0,4);
    }

    public static int getYearFromSQLFriendlyFormatDateToInt(String date){
        return Integer.valueOf(date.substring(0,4));
    }

    public static String getMonthFromSQLFriendlyFormatDate(String date){
        return date.substring(5,7);
    }

    public static int getMonthFromSQLFriendlyFormatDateToInt(String date){
        return Integer.valueOf(date.substring(5,7));
    }
    public static boolean isYearAndMonthHasPassed(int year, int month, int day) {
        Date date = Config.date;
        Date passedDate = new Date(year, month, day);

        if (passedDate.before(date)) {
            return true;
        } else {
            return false;
        }
    }

    //<editor-fold desc="Get data from current date ">
    public static int getCurrentMonthNumber() {
        return Integer.valueOf(new SimpleDateFormat("MM").format(Config.date));
    }

    public static String getCurrentMonthName() {
        return new SimpleDateFormat("MMMM").format(Config.date);
    }

    public static int getCurrentYear() {
        return Integer.valueOf(new SimpleDateFormat("yyyy").format(Config.date));
    }

    public static int getAmountOfDaysInCurrentMonthOPassedMonth(int year, int month) {
        LocalDate date = LocalDate.now();

        LocalDate passedDate = LocalDate.of(year, month, MyDate.getAmountOfDaysInMonth(month));

        if (date.isAfter(passedDate)) {
            return MyDate.getAmountOfDaysInMonth(month);
        } else {
            return date.getDayOfMonth();
        }
    }

    public static String getCurrentDayInSQLFormat() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Config.date);
    }
    public static String getCurrentYearAndMonthInSQLFormat() {
        String result = new SimpleDateFormat("yyyy-MM").format(Config.date);
        result += "%";
        return result;
    }
    public static String[] getAllDaysForCurrentMonthInSQLFriendlyFormat() {
        int monthInt = Integer.valueOf(new SimpleDateFormat("MM").format(Config.date));
        String firstPartOfResult = new SimpleDateFormat("yyyy-MM").format(Config.date);

        int amountOfDaysInCurrentMonth = getAmountOfDaysInMonth(monthInt);
        String[] allDays = new String[amountOfDaysInCurrentMonth];

        for (int i = 0; i < amountOfDaysInCurrentMonth; i++) {
            if (i < 9) {
                allDays[i] = firstPartOfResult + "-0" + (i + 1);
            } else {
                allDays[i] = firstPartOfResult + "-" + (i + 1);
            }
        }
        return allDays;
    }

    public static String getCurrentDayNameOfDayCapitalizationCase() {
        Format format = new SimpleDateFormat("EEEE");
        java.util.Date utilDateImport = new java.util.Date();
        String dayName = format.format(utilDateImport);

        return dayName;
    }

    public static String getCurrentDayNameOfDayLowerCase() {
        Format format = new SimpleDateFormat("EEEE");
        java.util.Date utilDateImport = new java.util.Date();
        String dayName = format.format(utilDateImport);

        return dayName.toLowerCase();
    }
    //</editor-fold>


}
