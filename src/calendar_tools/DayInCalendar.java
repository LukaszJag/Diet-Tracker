package calendar_tools;

import configuration.Config;
import products_tools.Macro;
import products_tools.Product;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayInCalendar {
    private Date dayDate;
    private String dayDateInString;
    private String dayDateDayName;
    private float dayAmountOfProduct;
    private Product dayProductProduct;
    private Macro dayProductMacro;
    private String dayProductOptionalTime;
    private String dayProductOptionalComment;
    private float dayKcalConsume;


    public DayInCalendar(Date dayDate, String dayDateInString,String dayDateDayName, float dayAmountOfProduct, Product dayProductProduct,
                         Macro dayProductMacro, String dayProductOptionalTime, String dayProductOptionalComment) {
        this.dayDate = dayDate;
        this.dayDateInString = dayDateInString;
        this.dayDateDayName = dayDateDayName;
        this.dayAmountOfProduct = dayAmountOfProduct;
        this.dayProductProduct = dayProductProduct;
        this.dayProductMacro = dayProductMacro;
        this.dayProductOptionalTime = dayProductOptionalTime;
        this.dayProductOptionalComment = dayProductOptionalComment;
    }

    public DayInCalendar(Date dayDate, String dayDateInString,String dayDateDayName, float dayAmountOfProduct, Product dayProductProduct,
                         Macro dayProductMacro, String dayProductOptionalTime, String dayProductOptionalComment, float dayKcalConsume) {
        this.dayDate = dayDate;
        this.dayDateInString = dayDateInString;
        this.dayDateDayName = dayDateDayName;
        this.dayAmountOfProduct = dayAmountOfProduct;
        this.dayProductProduct = dayProductProduct;
        this.dayProductMacro = dayProductMacro;
        this.dayProductOptionalTime = dayProductOptionalTime;
        this.dayProductOptionalComment = dayProductOptionalComment;
        this.dayKcalConsume = dayKcalConsume;
    }

    public String[]  dayDataInStringArray(DayInCalendar dayInCalendar){
        String[] dayDataInStringArray = new String[Config.DAY_IN_CALENDAR_STRING_ARRAY_LENGTH];

        dayDataInStringArray[0] = dayDate.toString();
        dayDataInStringArray[1] = dayInCalendar.getDayDateDayName();
        dayDataInStringArray[2] = String.valueOf(dayInCalendar.getDayAmountOfProduct());
        dayDataInStringArray[3] = dayInCalendar.getDayProductProduct().getProductName();
        dayDataInStringArray[4] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getKcal());
        dayDataInStringArray[5] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getProtein());
        dayDataInStringArray[6] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getFat());
        dayDataInStringArray[7] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getCarbs());

        if(dayDataInStringArray[8] == null){
            String pattern = "yyyy-MM-dd hh:mm:ss";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            String today = dateFormat.format(new Date());
            System.out.println(today);
            dayDataInStringArray[8] = today;
        }else{
            dayDataInStringArray[8] = dayInCalendar.getDayProductOptionalTime();
        }

        dayDataInStringArray[9] = dayInCalendar.getDayProductOptionalComment();

        System.out.println("\nDayInCalendar -> dayDataInStringArray:");
        for (int i = 0; i < dayDataInStringArray.length; i++) {
            System.out.println("[i]: " + i + " - " + dayDataInStringArray[i]);
        }
        System.out.println();
        return dayDataInStringArray;
    }

    public String[]  dayDataInStringArray(DayInCalendar dayInCalendar, float dayKcalConsume){
        String[] dayDataInStringArray = new String[Config.DAY_IN_CALENDAR_STRING_ARRAY_LENGTH_WITH_KCAL_CONSUME_LENGTH];

        dayDataInStringArray[0] = new SimpleDateFormat("yyyy-MM-dd").format(dayInCalendar.getDayDate());
        dayDataInStringArray[1] = dayInCalendar.getDayDateDayName();
        dayDataInStringArray[2] = String.valueOf(dayInCalendar.getDayAmountOfProduct());
        dayDataInStringArray[3] = dayInCalendar.getDayProductProduct().getProductName();
        dayDataInStringArray[4] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getKcal());
        dayDataInStringArray[5] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getProtein());
        dayDataInStringArray[6] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getFat());
        dayDataInStringArray[7] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getCarbs());

        dayDataInStringArray[10] = String.valueOf(dayKcalConsume);

        if(dayDataInStringArray[8] == null){
            String pattern = "yyyy-MM-dd hh:mm:ss";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            String today = dateFormat.format(new Date());
            System.out.println(today);
            dayDataInStringArray[8] = today;
        }else{
            dayDataInStringArray[8] = dayInCalendar.getDayProductOptionalTime();
        }

        dayDataInStringArray[9] = dayInCalendar.getDayProductOptionalComment();

        System.out.println("\nDayInCalendar -> dayDataInStringArray:");
        for (int i = 0; i < dayDataInStringArray.length; i++) {
            System.out.println("[i]: " + i + " - " + dayDataInStringArray[i]);
        }
        System.out.println();
        return dayDataInStringArray;
    }
    public static void dayDataShowData(DayInCalendar dayInCalendar) {
        String[] dayDataInStringArray = new String[Config.DAY_IN_CALENDAR_STRING_ARRAY_LENGTH];

        dayDataInStringArray[0] = dayInCalendar.getDayDate().toString();
        dayDataInStringArray[1] = dayInCalendar.getDayDateDayName();
        dayDataInStringArray[2] = String.valueOf(dayInCalendar.getDayAmountOfProduct());
        dayDataInStringArray[3] = dayInCalendar.getDayProductProduct().getProductName();
        dayDataInStringArray[4] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getKcal());
        dayDataInStringArray[5] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getProtein());
        dayDataInStringArray[6] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getFat());
        dayDataInStringArray[7] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getCarbs());
        if (dayDataInStringArray[8] == null) {
            dayDataInStringArray[8] = "2010-10-10 11:11:11";
        } else {
            dayDataInStringArray[8] = dayInCalendar.getDayProductOptionalTime();
        }

        System.out.println("\n DayInCalendar -> dayDataShowData:");
        for (int i = 0; i < dayDataInStringArray.length; i++) {
            System.out.println("[i]: " + i + " - " + dayDataInStringArray[i]);
        }
        System.out.println();
    }
    public static void dayDataShowDataWithSQLColumns(DayInCalendar dayInCalendar) {
        String[] dayDataInStringArray = new String[Config.DAY_IN_CALENDAR_STRING_ARRAY_LENGTH];
        dayDataInStringArray[0] = new SimpleDateFormat("yyyy-MM-dd").format(dayInCalendar.getDayDate());
        dayDataInStringArray[1] = dayInCalendar.getDayDateDayName();
        dayDataInStringArray[2] = String.valueOf(dayInCalendar.getDayAmountOfProduct());
        dayDataInStringArray[3] = dayInCalendar.getDayProductProduct().getProductName();
        dayDataInStringArray[4] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getKcal());
        dayDataInStringArray[5] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getProtein());
        dayDataInStringArray[6] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getFat());
        dayDataInStringArray[7] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getCarbs());
        if (dayDataInStringArray[8] == null) {
            dayDataInStringArray[8] = "2010-10-10 11:11:11";
        } else {
            dayDataInStringArray[8] = dayInCalendar.getDayProductOptionalTime();
        }

        System.out.println("\nDayInCalendar -> dayDataShowData:");
        for (int i = 0; i < dayDataInStringArray.length; i++) {
            System.out.println("[i]: " + i + " -> " + Config.SQL_COLUMNS_CALENDAR[i]  + " - " + dayDataInStringArray[i]);
        }
        System.out.println();
    }

    //<editor-fold desc="Getters and Setters">
    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    public String getDayDateInString() {
        return dayDateInString;
    }

    public void setDayDateInString(String dayDateInString) {
        this.dayDateInString = dayDateInString;
    }

    public String getDayDateDayName() {
        return dayDateDayName;
    }

    public void setDayDateDayName(String dayDateDayName) {
        this.dayDateDayName = dayDateDayName;
    }

    public float getDayAmountOfProduct() {
        return dayAmountOfProduct;
    }

    public void setDayAmountOfProduct(float dayAmountOfProduct) {
        this.dayAmountOfProduct = dayAmountOfProduct;
    }

    public Product getDayProductProduct() {
        return dayProductProduct;
    }

    public void setDayProductProduct(Product dayProductProduct) {
        this.dayProductProduct = dayProductProduct;
    }

    public Macro getDayProductMacro() {
        return dayProductMacro;
    }

    public void setDayProductMacro(Macro dayProductMacro) {
        this.dayProductMacro = dayProductMacro;
    }

    public String getDayProductOptionalTime() {
        return dayProductOptionalTime;
    }

    public void setDayProductOptionalTime(String dayProductOptionalTime) {
        this.dayProductOptionalTime = dayProductOptionalTime;
    }

    public String getDayProductOptionalComment() {
        return dayProductOptionalComment;
    }

    public void setDayProductOptionalComment(String dayProductOptionalComment) {
        this.dayProductOptionalComment = dayProductOptionalComment;
    }

    public float getDayKcalConsume() {
        return dayKcalConsume;
    }

    public void setDayKcalConsume(float dayKcalConsume) {
        this.dayKcalConsume = dayKcalConsume;
    }
    //</editor-fold>
}
