package tools.products_tools;

import configuration.Config;

public class Product {

    // Values

    private String productName;
    private float productMeasureOfProductWeightToCalculateMacro;
    private Macro productMacroForItsSetMeasure;
    private String productBrand;
    private float productPackWeight;
    private String commentOptional;

    // Constructors
    public Product(String name, String brand, float productMeasureOfProductWeightToCalculateMacro, Macro product_macro, float weight_of_pack, String commentOptional){
        this.productName = name;
        this.productBrand = brand;
        this.productMeasureOfProductWeightToCalculateMacro = productMeasureOfProductWeightToCalculateMacro;
        this.productMacroForItsSetMeasure = product_macro;
        this.productPackWeight = weight_of_pack;
        this.commentOptional = commentOptional;
    }

    public static boolean isProductEqual(Product productOne, Product productTwo){
        String[] productOneInArray = productOne.productDataInStringArray(productOne);
        String[] productTwoInArray = productOne.productDataInStringArray(productTwo);

        for (int i = 0; i < productOneInArray.length; i++) {
            if(!productOneInArray[i].equals(productTwoInArray[i])){
                return false;
            }
        }
        return true;
    }

    public String[] productDataInStringArray(Product productWithData){
        String[] productDataStringArray = new String[Config.ALL_PRODUCT_VALUES_FIELD_COUNT];

        // Set values to array - no numeric
        productDataStringArray[0] = productWithData.getProductName();
        productDataStringArray[1] = productWithData.getProductBrand();

        // Set values to array - numeric
        productDataStringArray[2] = String.valueOf(productWithData.getProductPackWeight());
        productDataStringArray[3] = String.valueOf(productWithData.getProductMeasureOfProductWeightToCalculateMacro());
        productDataStringArray[4] = String.valueOf(productWithData.getProductMacroForItsSetMeasure().getKcal());
        productDataStringArray[5] = String.valueOf(productWithData.getProductMacroForItsSetMeasure().getProtein());
        productDataStringArray[6] = String.valueOf(productWithData.getProductMacroForItsSetMeasure().getFat());
        productDataStringArray[7] = String.valueOf(productWithData.getProductMacroForItsSetMeasure().getCarbs());
        productDataStringArray[8] =  productWithData.getCommentOptional();
        return productDataStringArray;
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

    public String getCommentOptional() {
        return commentOptional;
    }

    public void setCommentOptional(String commentOptional) {
        this.commentOptional = commentOptional;
    }
}
