package tools.calendar_tools;

import configuration.Config;
import tools.products_tools.Macro;
import tools.products_tools.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DayInCalendar {
    private Date dayDate;
    private String dayDateFormatFriendlyForSQL;
    private String dayDateDayName;
    private String mealName;
    private float dayAmountOfProduct;
    private Product dayProductProduct;
    private Macro dayProductMacro;
    private String dayProductOptionalTime;
    private String dayProductOptionalComment;
    private Macro consumedMacro;


    public DayInCalendar(Date dayDate, String dayDateDayName, float dayAmountOfProduct, Product dayProductProduct,
                         Macro dayProductMacro, String dayProductOptionalTime, String dayProductOptionalComment) {
        this.dayDate = dayDate;
        this.dayDateDayName = dayDateDayName;
        this.dayAmountOfProduct = dayAmountOfProduct;
        this.dayProductProduct = dayProductProduct;
        this.dayProductMacro = dayProductMacro;
        this.dayProductOptionalTime = dayProductOptionalTime;
        this.dayProductOptionalComment = dayProductOptionalComment;
    }

    public DayInCalendar(String dayDateFormatFriendlyForSQL, String dayDateDayName, String mealName, float dayAmountOfProduct, Product dayProductProduct,
                         Macro dayProductMacro, String dayProductOptionalTime, String dayProductOptionalComment, Macro consumedMacro) {
        this.dayDateFormatFriendlyForSQL = dayDateFormatFriendlyForSQL;
        this.dayDateDayName = dayDateDayName;
        this.mealName = mealName;
        this.dayAmountOfProduct = dayAmountOfProduct;
        this.dayProductProduct = dayProductProduct;
        this.dayProductMacro = dayProductMacro;
        this.dayProductOptionalTime = dayProductOptionalTime;
        this.dayProductOptionalComment = dayProductOptionalComment;
        this.consumedMacro = consumedMacro;
    }


    public DayInCalendar(Date dayDate, String dayDateDayName, String mealName, float dayAmountOfProduct, Product dayProductProduct,
                         Macro dayProductMacro, String dayProductOptionalTime, String dayProductOptionalComment, Macro consumedMacro) {
        this.dayDate = dayDate;
        this.dayDateDayName = dayDateDayName;
        this.mealName = mealName;
        this.dayAmountOfProduct = dayAmountOfProduct;
        this.dayProductProduct = dayProductProduct;
        this.dayProductMacro = dayProductMacro;
        this.dayProductOptionalTime = dayProductOptionalTime;
        this.dayProductOptionalComment = dayProductOptionalComment;
        this.consumedMacro = consumedMacro;
    }

    public String[] dayDataInStringArray(DayInCalendar dayInCalendar) {
        String[] dayDataInStringArray = new String[Config.DAY_IN_CALENDAR_STRING_ARRAY_LENGTH];

        dayDataInStringArray[0] = dayInCalendar.getDayDateFormatFriendlyForSQL();
        dayDataInStringArray[1] = dayInCalendar.getDayDateDayName();
        dayDataInStringArray[2] = dayInCalendar.getMealName();
        dayDataInStringArray[3] = String.valueOf(dayInCalendar.getDayAmountOfProduct());
        dayDataInStringArray[4] = dayInCalendar.getDayProductProduct().getProductName();
        dayDataInStringArray[5] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getKcal());
        dayDataInStringArray[6] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getProtein());
        dayDataInStringArray[7] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getFat());
        dayDataInStringArray[8] = String.valueOf(dayInCalendar.getDayProductProduct().getProductMacroForItsSetMeasure().getCarbs());

        if (dayDataInStringArray[9] == null) {
            String pattern = "yyyy-MM-dd hh:mm:ss";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            String today = dateFormat.format(new Date());
            dayDataInStringArray[9] = today;
        } else {
            dayDataInStringArray[9] = dayInCalendar.getDayProductOptionalTime();
        }
        if (dayInCalendar.getDayProductOptionalComment().equals("")) {
            dayDataInStringArray[10] = "none";
        } else {
            dayDataInStringArray[10] = dayInCalendar.getDayProductOptionalComment();
        }
        return dayDataInStringArray;
    }

    public static void dayDataShowAllData(DayInCalendar dayInCalendar) {
        String[] dayDataInStringArray = new String[Config.DAY_IN_CALENDAR_STRING_ARRAY_LENGTH];


        try {
            dayDataInStringArray[0] = new SimpleDateFormat("yyyy-MM-dd").parse(dayInCalendar.getDayDate().toString()).toString();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

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
            System.out.println("[i]: " + i + " -> " + Config.SQL_COLUMNS_CALENDAR[i] + " - " + dayDataInStringArray[i]);
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

    public Macro getConsumedMacro() {
        return consumedMacro;
    }

    public void setConsumedMacro(Macro consumedMacro) {
        this.consumedMacro = consumedMacro;
    }

    public String getDayDateFormatFriendlyForSQL() {
        return dayDateFormatFriendlyForSQL;
    }

    public void setDayDateFormatFriendlyForSQL(String dayDateFormatFriendlyForSQL) {
        this.dayDateFormatFriendlyForSQL = dayDateFormatFriendlyForSQL;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }


    //</editor-fold>

}
