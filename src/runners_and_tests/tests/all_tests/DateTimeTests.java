package runners_and_tests.tests.all_tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeTests {
    public static void main(String[] args) throws ParseException {
        test1();
    }

    private static void test1() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);

        Date date = null;

        date = sdf.parse("19/05/2024");

        //specifies the pattern to print
        sdf.applyPattern("EEEE");
        String str = sdf.format(date);

        //prints day name with date
        System.out.println(str);

        String dateString = "";
        String[] dateStringArray = {"19/05/2024", "13/05/2024", "26/05/2024", "19-05-2024", "13-05-2024", "26-05-2024", "2024-05-24",
                "2024-04-08",
                "2024-04-13",
                "2024-04-26",
                "2024-04-28",
                "2024-05-02",
                "2024-05-12"};


        System.out.println(dateStringArray[7] + " GOOD");
        System.out.println();
        System.out.println(dateStringArray[8] + " GOOD");
        System.out.println(dateStringArray[9] + " GOOD");
        System.out.println(dateStringArray[10] + " GOOD");
        System.out.println(dateStringArray[11] + " GOOD");
        System.out.println(dateStringArray[12] + " GOOD");
        System.out.println();

        for (int i = 0; i < dateStringArray.length; i++) {
            if (i <= 2) {
                sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);
                date = sdf.parse(dateStringArray[i]);
                sdf.applyPattern("EEEE");
                dateString = sdf.format(date);
                System.out.println(dateStringArray[i] + " -> " + dateString);
            }
            if (i > 2 && i <= 5) {
                sdf = new SimpleDateFormat("dd-MM-yyyy", java.util.Locale.ENGLISH);
                date = sdf.parse(dateStringArray[i]);
                sdf.applyPattern("EEEE");
                dateString = sdf.format(date);
                System.out.println(dateStringArray[i] + " -> " + dateString);
            }
            if (i == 6) {
                sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
                date = sdf.parse(dateStringArray[i]);
                sdf.applyPattern("EEEE");
                dateString = sdf.format(date);
                System.out.println(dateStringArray[i] + " -> " + dateString);

                System.out.println();
                System.out.println("DIRECT FROM SQL");
            }
            if (i > 6) {
                sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
                date = sdf.parse(dateStringArray[i]);
                sdf.applyPattern("EEEE");
                dateString = sdf.format(date);
                System.out.println(dateStringArray[i] + " -> " + dateString);
            }
        }
    }

}
