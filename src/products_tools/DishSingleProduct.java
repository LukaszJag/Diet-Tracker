package products_tools;

public class DishSingleProduct extends Dish{
    public DishSingleProduct(Product product, float gramsOfProduct){
        this.product = product;
        this.gramsOfProduct = gramsOfProduct;
        productMacro = product.getProduct_macro();
    }

}
