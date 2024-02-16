package calendar_tools;

import products_tools.Macro;
import products_tools.Product;

import java.util.Date;

public class DayOneProduct {
    private Date dayDate;
    private float amount_of_product;
    private Product dayOneProductProduct;
    private Macro dayOneProductMacro;
    private String dayOneProductOptionalTime;

    public DayOneProduct(Date dayDate, float amount_of_product, Product dayOneProductProduct,
                         Macro dayOneProductMacro, String dayOneProductOptionalTime) {
        this.dayDate = dayDate;
        this.amount_of_product = amount_of_product;
        this.dayOneProductProduct = dayOneProductProduct;
        this.dayOneProductMacro = dayOneProductMacro;
        this.dayOneProductOptionalTime = dayOneProductOptionalTime;
    }

}
