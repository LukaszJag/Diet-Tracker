package runners_and_tests.tests.get_simple_data_to_test;

import tools.products_tools.Macro;
import tools.products_tools.Product;

public class ProductFactoryToMakeTests {
    public static Product productBarExample(){
        return  new Product("Bar", "Mars", 100f, new Macro(200f, 20, 10, 80), 80, "Baton Mars");
    }
    public static Product productBarOtherExample(){
        return  new Product("Bar", "Twix", 100f, new Macro(250f, 30, 10, 80), 80, "Baton Twix");
    }
}
