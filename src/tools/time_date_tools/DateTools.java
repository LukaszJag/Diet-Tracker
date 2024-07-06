package tools.time_date_tools;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
