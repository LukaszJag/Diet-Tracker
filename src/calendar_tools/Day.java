package calendar_tools;

import products_tools.Macro;
import products_tools.Product;

public class Day {
    private Macro dayMacro;
    private Product[] dayProductsArray = new Product[20];

    public Day(){
        for (int i = 0; i < dayProductsArray.length; i++) {
            dayProductsArray[i] = null;
        }
    }
    private void addProductToDay(Product product){
        int counter = 0;

        while(dayProductsArray[counter] != null){
            counter++;
        }

        counter++;
        System.out.println("Array has empty position at [" + counter + "]");

        if (counter >= dayProductsArray.length){
            System.out.println("Array is full");
        }else {
            dayProductsArray[counter] = product;
            System.out.println("Product has been added to day product Array");
        }
    }

    private Product[] getAllDayProducts(){
        return dayProductsArray;
    }
}
