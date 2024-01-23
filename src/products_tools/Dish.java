package products_tools;

public class Dish {
    Product product;
    Macro productMacro;
    float gramsOfProduct;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Macro getProductMacro() {
        return productMacro;
    }

    public void setProductMacro(Macro productMacro) {
        this.productMacro = productMacro;
    }

    public float getGramsOfProduct() {
        return gramsOfProduct;
    }

    public void setGramsOfProduct(float gramsOfProduct) {
        this.gramsOfProduct = gramsOfProduct;
    }
}
