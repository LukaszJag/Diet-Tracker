package tests;

import sql_tools.SQLSelect;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuickTests1 {
    public static void main(String[] args) throws SQLException, ParseException {
        testDateDisplay();
    }

    public static void testDateDisplay() throws ParseException {
        String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String today = dateFormat.format(new Date());
        System.out.println(today);
    }

    public static void getRowFromProductTableByProductNameGetArrayTest() throws SQLException {
        SQLSelect.getRowFromProductTableByProductNameGetArray("Burak");
        SQLSelect.getRowFromProductTableByProductNameGetArray("Jogurt kiwi");
        SQLSelect.getRowFromProductTableByProductNameGetArray("Rower");
    }
}
