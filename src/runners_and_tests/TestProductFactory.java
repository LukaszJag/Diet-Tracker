package runners_and_tests;

import products_tools.Macro;
import products_tools.Product;
import sql_tools.InsertProductToSQL_Table;

public class TestProductFactory {
    public static Product productBarExample(){
        return  new Product("Bar", "Mars", 100f, new Macro(200f, 20, 10, 80), 80);
    }
}
