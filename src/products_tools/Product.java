package products_tools;

import configuration.Config;

public class Product {

    // Values

    private String productName;
    private float productMeasureOfProductWeightToCalculateMacro;
    private Macro productMacroForItsSetMeasure;
    private String productBrand;
    private float productPackWeight;

    // Constructors
    public Product(String name, String brand, float macroFor, Macro product_macro, float weight_of_pack){
        this.productName = name;
        this.productBrand = brand;
        this.productMacroForItsSetMeasure = product_macro;
        this.productPackWeight = weight_of_pack;
    }

    public String[] productDataInStringArray(Product productWithData){
        String[] productDataStringArray = new String[Config.ALL_PRODUCT_VALUES_FIELD_COUNT];

        // Set values to array
        productDataStringArray[0] = productWithData.getProductName();
        productDataStringArray[1] = productWithData.getProductBrand();
        productDataStringArray[2] = String.valueOf(productWithData.getProductPackWeight() + "f");

        /*
            UNDONE CODE NEED TO SET OTHER VALUES AND RETURN IT
         */

        return null;
    }

    // Getters and Setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductMeasureOfProductWeightToCalculateMacro() {
        return productMeasureOfProductWeightToCalculateMacro;
    }

    public void setProductMeasureOfProductWeightToCalculateMacro(float productMeasureOfProductWeightToCalculateMacro) {
        this.productMeasureOfProductWeightToCalculateMacro = productMeasureOfProductWeightToCalculateMacro;
    }

    public Macro getProductMacroForItsSetMeasure() {
        return productMacroForItsSetMeasure;
    }

    public void setProductMacroForItsSetMeasure(Macro productMacroForItsSetMeasure) {
        this.productMacroForItsSetMeasure = productMacroForItsSetMeasure;
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
