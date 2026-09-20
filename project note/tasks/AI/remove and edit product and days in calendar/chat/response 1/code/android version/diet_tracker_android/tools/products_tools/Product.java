package com.lukaszjag.diet_tracker_android.tools.products_tools;

import com.lukaszjag.diet_tracker_android.config.Config;

public class Product {
    private String productName;
    private float productMeasureOfProductWeightToCalculateMacro;
    private Macro productMacroForItsSetMeasure;
    private String productBrand;
    private float productPackWeight;
    private String commentOptional;

    // Added support parameters
    private String mealName;
    private float consumedKcal;
    private float consumedProtein;
    private float consumedFat;
    private float consumedCarbs;

    public Product(String name, String brand, float productMeasureOfProductWeightToCalculateMacro, Macro product_macro, float weight_of_pack, String commentOptional){
        this.productName = name;
        this.productBrand = brand;
        this.productMeasureOfProductWeightToCalculateMacro = productMeasureOfProductWeightToCalculateMacro;
        this.productMacroForItsSetMeasure = product_macro;
        this.productPackWeight = weight_of_pack;
        this.commentOptional = commentOptional;
    }

    public Product(String productName, String productMeasureOfProductWeightToCalculateMacro, String kcalProductMacroForItsSetMeasure) {
        this.productName = productName;
        this.productMeasureOfProductWeightToCalculateMacro = Float.valueOf(productMeasureOfProductWeightToCalculateMacro);
        this.productMacroForItsSetMeasure = new Macro(Float.valueOf(kcalProductMacroForItsSetMeasure),0f,0f,0f);
    }

    // Extended constructor for comprehensive history entries
    public Product(String productName, String mealName, float amountOfProduct, float consumedKcal, float consumedProtein, float consumedFat, float consumedCarbs, String commentOptional) {
        this.productName = productName;
        this.mealName = mealName;
        this.productMeasureOfProductWeightToCalculateMacro = amountOfProduct;
        this.consumedKcal = consumedKcal;
        this.consumedProtein = consumedProtein;
        this.consumedFat = consumedFat;
        this.consumedCarbs = consumedCarbs;

        this.commentOptional = commentOptional;
        this.productMacroForItsSetMeasure = new Macro(consumedKcal, consumedProtein, consumedFat, consumedCarbs);
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

        productDataStringArray[0] = productWithData.getProductName();
        productDataStringArray[1] = productWithData.getProductBrand();
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

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public float getConsumedKcal() {
        return consumedKcal;
    }

    public float getConsumedProtein() {
        return consumedProtein;
    }

    public float getConsumedFat() {
        return consumedFat;
    }

    public float getConsumedCarbs() {
        return consumedCarbs;
    }

    @Override
    public String toString() {
        return "Product{" + "\n" +
                "productName = '" + productName + '\'' + "\n" +
                "productMeasureOfProductWeightToCalculateMacro = " + productMeasureOfProductWeightToCalculateMacro + "\n" +
                "productMacroForItsSetMeasure = " + productMacroForItsSetMeasure.getShortMacroInformation() + "\n" +
                "productBrand = '" + productBrand + '\'' + "\n" +
                "productPackWeight = " + productPackWeight + "\n" +
                "commentOptional = '" + commentOptional + '\'' + "\n" +
                '}';
    }
}