package com.lukaszjag.diet_tracker_android.tools.cloud_data_tools;

public class CalendarDay {
    // Variable names MUST exactly match your SQL database column names

    String day_date;
    String day_name;
    String product_name;
    Double amount_of_product;
    Double kcal;
    Double protein;
    Double fat;
    Double carbs;
    String time_optional;
    String comment_optional;
    Double kcal_consume;
    Double carbs_consume;
    Double fat_consume;
    Double protein_consume;
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

    public Double getAmount_of_product() {
        return amount_of_product;
    }

    public void setAmount_of_product(Double amount_of_product) {
        this.amount_of_product = amount_of_product;
    }

    public Double getKcal() {
        return kcal;
    }

    public void setKcal(Double kcal) {
        this.kcal = kcal;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
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

    public Double getKcal_consume() {
        return kcal_consume;
    }

    public void setKcal_consume(Double kcal_consume) {
        this.kcal_consume = kcal_consume;
    }

    public Double getCarbs_consume() {
        return carbs_consume;
    }

    public void setCarbs_consume(Double carbs_consume) {
        this.carbs_consume = carbs_consume;
    }

    public Double getFat_consume() {
        return fat_consume;
    }

    public void setFat_consume(Double fat_consume) {
        this.fat_consume = fat_consume;
    }

    public Double getProtein_consume() {
        return protein_consume;
    }

    public void setProtein_consume(Double protein_consume) {
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
