package products_tools;

import Ganeral.Config;
import text_files_tools.MakeFoldersAndTextFile;

import java.io.IOException;

public class Product {

    public Product(String name, String brand, float macroFor, Macro product_macro, float weight_of_pack){
        this.fileName = name;
        this.brand = brand;
        this.product_macro = product_macro;
        this.weight_of_pack = weight_of_pack;
    }

    private String fileName;

    private String brand;

    public float getMacroFor() {
        return macroFor;
    }

    public void setMacroFor(float macroFor) {
        this.macroFor = macroFor;
    }

    private float macroFor;
    private Macro product_macro;

    private float weight_of_pack;
    public String getName() {
        return fileName;
    }

    public void setName(String name) {
        this.fileName = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Macro getProduct_macro() {
        return product_macro;
    }

    public void setProduct_macro(Macro product_macro) {
        this.product_macro = product_macro;
    }

    public float getWeight_of_pack() {
        return weight_of_pack;
    }

    public void setWeight_of_pack(float weight_of_pack) {
        this.weight_of_pack = weight_of_pack;
    }

}
