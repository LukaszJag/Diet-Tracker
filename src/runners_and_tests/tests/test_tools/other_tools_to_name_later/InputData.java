package runners_and_tests.tests.test_tools.other_tools_to_name_later;

import tools.time_date_tools.DateTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class InputData {
    public static  void chooseDateInputByConsole(){
        Scanner input = new Scanner(System.in);
        String dateInString;
        String year;
        String month;
        String day;

        System.out.println("Input year:");
        year = input.nextLine();
        System.out.println("Input month:");
        month = input.nextLine();
        System.out.println("Input day:");
        day = input.nextLine();

        String fullDate = year + "-" + month + "-" + day;
        System.out.println("Full date: " + fullDate);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date =  DateTools.getDateFromString(fullDate);

        System.out.println("Date toString(): " + date.toString());

    }
}
