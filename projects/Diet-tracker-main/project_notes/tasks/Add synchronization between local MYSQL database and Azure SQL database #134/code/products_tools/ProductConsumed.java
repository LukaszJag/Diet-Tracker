package tools.products_tools;

public class ProductConsumed {

    Product product = new Product();
    Macro consumedMacro = new Macro();
    float amountOfProduct;

    @Override
    public String toString() {
        return "TMPProduct{" +
                "productArrayList=" + product +
                ", consumedMacro=" + consumedMacro +
                ", amountOfProduct=" + amountOfProduct +
                '}';
    }

    //<editor-fold desc="Constructors">
    public ProductConsumed() {
    }

    public ProductConsumed(Product productArrayList, float amountOfProduct, Macro consumedMacro) {
        this.product = productArrayList;
        this.amountOfProduct = amountOfProduct;
        this.consumedMacro = consumedMacro;
    }
    //</editor-fold>

    //<editor-fold desc="Getters and Setters">
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Macro getConsumedMacro() {
        return consumedMacro;
    }

    public void setConsumedMacro(Macro consumedMacro) {
        this.consumedMacro = consumedMacro;
    }

    public float getAmountOfProduct() {
        return amountOfProduct;
    }

    public void setAmountOfProduct(float amountOfProduct) {
        this.amountOfProduct = amountOfProduct;
    }


    //</editor-fold>
}
