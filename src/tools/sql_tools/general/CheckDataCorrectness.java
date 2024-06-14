package tools.sql_tools.general;

import tools.products_tools.Macro;
import tools.products_tools.Product;
import tools.sql_tools.SQLSelect;

import java.sql.SQLException;
import java.util.Scanner;

import static tools.sql_tools.SQLSelect.getRowFromProductTableByProductNameWithColumnName;
import static tools.sql_tools.SQLSelect.getRowsInCalendarByProductName;
public class CheckDataCorrectness {

    public static void getProductAllRowInCalendar() throws SQLException {
        Scanner input = new Scanner(System.in);
        String newProductToCheck;
        String[] dataCalendar;
        String[] dataProduct;


        while (true) {
            newProductToCheck = input.next();
            dataCalendar = getRowsInCalendarByProductName(newProductToCheck);
            System.out.println();
            for (int i = 0; i < dataCalendar.length; i++) {
                if (dataCalendar[i] != null) {
                    System.out.println(dataCalendar[i]);
                }
            }
            System.out.println();
            System.out.println(getRowFromProductTableByProductNameWithColumnName(newProductToCheck));
        }
    }

    public static void getAllWrongProductByName() throws SQLException {
        String[] wrongProductData;
        wrongProductData = SQLSelect.getAllWrongProductByNameFromProductTable();

        for (int i = 0; i < 3; i++) {
            System.out.println(wrongProductData[i]);
        }
    }

    public static void getAllWrongProductMacroByName() throws SQLException {
        String[] wrongProductData;
        wrongProductData = SQLSelect.getAllWrongProductByNameFromProductTable();

        convertDataToProductValue(wrongProductData[0]);
        //for (int i = 0; i < 3; i++) {
        //    System.out.println(wrongProductData[i]);
        //}
    }

    public static Product convertDataToProductValue(String rawData){
        String[] data = new String[9];
        String[] rawDataLineByLine = rawData.split("\n");
        for (int i = 0; i < rawDataLineByLine.length; i++) {
            System.out.println("[" + i + "]: " + rawDataLineByLine[i]);
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = rawDataLineByLine[i].substring((rawDataLineByLine[i].indexOf(":") +1));
        }

        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);
        }

        Macro macro = new Macro(Float.valueOf(data[4]),(Float.valueOf(data[5])),(Float.valueOf(data[6])),(Float.valueOf(data[7])));
        Product product = new Product(data[0],data[1],(Float.valueOf(data[3])), macro, Float.valueOf(data[2]),data[8]);

        return product;
    }

}
