package com.lukaszjag.diet_tracker_android.tools.cloud_data_tools;

public class User {
    // Variable names MUST exactly match your SQL database column names

    String day_date;
    String day_name;
    String product_name;
    String amount_of_product;
    String kcal;
    String protein;
    String fat;
    String carbs;
    String time_optional;
    String comment_optional;
    String kcal_consume;
    String carbs_consume;
    String fat_consume;
    String protein_consume;
    String meal_name;

    //<editor-fold desc="Getters and Setters">
    public String getDay_date() {
        return day_date;
    }

    public void setDay_date(String day_date) {
        this.day_date = day_date;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getAmount_of_product() {
        return amount_of_product;
    }

    public void setAmount_of_product(String amount_of_product) {
        this.amount_of_product = amount_of_product;
    }

    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getTime_optional() {
        return time_optional;
    }

    public void setTime_optional(String time_optional) {
        this.time_optional = time_optional;
    }

    public String getComment_optional() {
        return comment_optional;
    }

    public void setComment_optional(String comment_optional) {
        this.comment_optional = comment_optional;
    }

    public String getKcal_consume() {
        return kcal_consume;
    }

    public void setKcal_consume(String kcal_consume) {
        this.kcal_consume = kcal_consume;
    }

    public String getCarbs_consume() {
        return carbs_consume;
    }

    public void setCarbs_consume(String carbs_consume) {
        this.carbs_consume = carbs_consume;
    }

    public String getFat_consume() {
        return fat_consume;
    }

    public void setFat_consume(String fat_consume) {
        this.fat_consume = fat_consume;
    }

    public String getProtein_consume() {
        return protein_consume;
    }

    public void setProtein_consume(String protein_consume) {
        this.protein_consume = protein_consume;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }
    //</editor-fold>
}
