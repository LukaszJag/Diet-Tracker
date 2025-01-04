package tools.calendar_tools;

public class MyDate {
    public static int getAmountOfDaysInMonth(String month, int year) {
        if (month.equals("January")) {
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
    public static int getAmountOfDaysInMonth(String month) {
        if (month.equals("January")) {
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
        if (month.equalsIgnoreCase("January ")) {
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
}
