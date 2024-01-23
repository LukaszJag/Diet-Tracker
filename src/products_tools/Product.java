package products_tools;

import GUI.Config;
import text_files_tools.MakeTextFile;

import java.io.IOException;

public class Product {

    public Product(String name, String brand, Macro product_macro, float weight_of_pack){
        this.fileName = name;
        this.brand = brand;
        this.product_macro = product_macro;
        this.weight_of_pack = weight_of_pack;
    }

    public void makeTextFileForProduct(Product product, float macroFor){
        MakeTextFile.makeEmptyFile(fileName, Config.destinationForTextFile[0]);


        try {
            MakeTextFile.writeProductToFile("Name: "+ product.getName(), fileName);
            MakeTextFile.writeProductToFile("Brand: " + product.getBrand(), fileName);
            MakeTextFile.writeProductToFile("Package has: "+ product.getWeight_of_pack(), fileName);

            MakeTextFile.writeProductToFile("Macro for: " + macroFor, fileName);

            MakeTextFile.writeProductToFile("KCal: " + product.getProduct_macro().getKcal(), fileName);
            MakeTextFile.writeProductToFile("Protein: " + product.getProduct_macro().getProtein(), fileName);
            MakeTextFile.writeProductToFile("Fat: " + product.getProduct_macro().getFat(), fileName);

            MakeTextFile.writeProductToFile("Carbs: " + product.product_macro.getCarbs(), fileName);


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    private String fileName;

    private String brand;
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
