package tools.calendar_tools;

import configuration.Config;
import tools.products_tools.Product;

public class Calendar {

    public static void addNewRowToCalendar(String dayDate, String dayName, float amountOfProduct, Product product){
        int argumentsPassed = 0;
        int calendarSQLFieldsAmount = Config.SQL_COLUMNS_CALENDAR.length;

        for (int i = 0; i < calendarSQLFieldsAmount; i++) {

            if(Config.SQL_COLUMNS_CALENDAR[i].equals("day_date")){
                System.out.println(dayDate);
                argumentsPassed++;
            }

            if (Config.SQL_COLUMNS_CALENDAR[i].equals("day_name")){
                System.out.println(dayName);
                argumentsPassed++;
            }

            if(Config.SQL_COLUMNS_CALENDAR[i].equals("amount_of_product")){
                System.out.println(amountOfProduct);
                argumentsPassed++;
            }

            if(Config.SQL_COLUMNS_CALENDAR[i].equals("product_name")){
                System.out.println(product.getProductName());
                argumentsPassed++;
            }

            if(Config.SQL_COLUMNS_CALENDAR[i].equals("kcal")){
                System.out.println(product.getProductMacroForItsSetMeasure().getKcal());
                argumentsPassed++;
            }

            if(Config.SQL_COLUMNS_CALENDAR[i].equals("protein")){
                System.out.println(product.getProductMacroForItsSetMeasure().getProtein());
                argumentsPassed++;
            }

            if(Config.SQL_COLUMNS_CALENDAR[i].equals("fat")){
                System.out.println(product.getProductMacroForItsSetMeasure().getFat());
                argumentsPassed++;
            }

            if(Config.SQL_COLUMNS_CALENDAR[i].equals("carbs")){
                System.out.println(product.getProductMacroForItsSetMeasure().getCarbs());
                argumentsPassed++;
            }

            if(Config.SQL_COLUMNS_CALENDAR[i].equals("time_optional")){
                System.out.println("time is optional information");
                argumentsPassed++;
            }

            if(Config.SQL_COLUMNS_CALENDAR[i].equals("comment_optional")){
                System.out.println("comment is optional information");
                argumentsPassed++;
            }

        }
        System.out.println("Arguments needed: " + calendarSQLFieldsAmount);
        System.out.println("Argument passed: " + argumentsPassed);
    }
}
