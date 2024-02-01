package products_tools;

public class Product {

    // Values

    private String productName;
    private float productMacroForThisMeasure;
    private Macro productMacroForItsMeasure;
    private String productBrand;
    private float productPackWeight;

    // Constructors
    public Product(String name, String brand, float macroFor, Macro product_macro, float weight_of_pack){
        this.productName = name;
        this.productBrand = brand;
        this.productMacroForItsMeasure = product_macro;
        this.productPackWeight = weight_of_pack;
    }

    // Getters and Setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductMacroForThisMeasure() {
        return productMacroForThisMeasure;
    }

    public void setProductMacroForThisMeasure(float productMacroForThisMeasure) {
        this.productMacroForThisMeasure = productMacroForThisMeasure;
    }

    public Macro getProductMacroForItsMeasure() {
        return productMacroForItsMeasure;
    }

    public void setProductMacroForItsMeasure(Macro productMacroForItsMeasure) {
        this.productMacroForItsMeasure = productMacroForItsMeasure;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public float getProductPackWeight() {
        return productPackWeight;
    }

    public void setProductPackWeight(float productPackWeight) {
        this.productPackWeight = productPackWeight;
    }
}
