package tools.calendar_tools;

public class MyDate {
    public static int getAmountOfDaysInMonth(String month, int year){
        if (year == 2024) {
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
        }

        return  -1;
    }

    public static int getNumberOfMonthInYear(String month){

        if (month.equals("January ")) {
            return 1;
        }
        if (month.equals("February")) {
            return 2;
        }
        if (month.equals("March")) {
            return 3;
        }
        if (month.equals("April")) {
            return 4;
        }
        if (month.equals("May")) {
            return 5;
        }
        if (month.equals("June")) {
            return 6;
        }
        if (month.equals("July")) {
            return 7;
        }
        if (month.equals("August")) {
            return 8;
        }
        if (month.equals("September")) {
            return 9;
        }
        if (month.equals("October")) {
            return 10;
        }
        if (month.equals("November")) {
            return 11;
        }

        if (month.equals("December")) {
            return 12;
        }
        return -1;
    }
}
