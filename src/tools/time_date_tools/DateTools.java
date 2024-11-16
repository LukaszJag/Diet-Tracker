package tools.time_date_tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTools {

    public static String getCurrentDateSQLFriendlyFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateString = dateFormat.format(new Date());
        return currentDateString;
    }

    public static Date getDateFromString(String dateInString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try{
            date = dateFormat.parse(dateInString);
        }catch (ParseException e){
            System.out.println("Wrong date from string required: yyyy-MM-dd");
        }
        return date;
    }

    public static String getMonthNameInUpperCase(int month){
        Month[] monthsMonth = Month.values();
        return (monthsMonth[month].getDisplayName(TextStyle.FULL, Locale.ENGLISH)).toUpperCase();
    }

    public static String[] getAllMonthsNamesInUpperCase(){
        Month[] monthsMonth = Month.values();
        String[] monthsInUpperCase = new String[12];
        for (int i = 0; i < monthsMonth.length; i++) {
            monthsInUpperCase[i] = (monthsMonth[i].getDisplayName(
                    TextStyle.FULL, Locale.ENGLISH)).toUpperCase();
        }
        return monthsInUpperCase;
    }

    public static String getMonthNameInLowerCase(int month){
        Month[] monthsMonth = Month.values();
        return (monthsMonth[month].getDisplayName(TextStyle.FULL, Locale.ENGLISH)).toLowerCase();
    }

    public static String getCurrentMontNameCamelCase(){
            String months[] = {
                    "January"
                    ,"February"
                    ,"March"
                    ,"April"
                    ,"May"
                    ,"June"
                    ,"July"
                    ,"August"
                    ,"September"
                    ,"October"
                    ,"November"
                    ,"December"};

            Calendar calendar = Calendar.getInstance();
            String month = months[calendar.get(Calendar.MONTH)];

            return  month;
    }
    public static String[] getAllMonthsNamesInLowerCase(){
        Month[] monthsMonth = Month.values();
        String[] monthsInLowerCase = new String[12];
        for (int i = 0; i < monthsMonth.length; i++) {
            monthsInLowerCase[0] = (monthsMonth[i].getDisplayName(
                    TextStyle.FULL, Locale.ENGLISH)).toLowerCase();
        }
        return monthsInLowerCase;
    }
}
