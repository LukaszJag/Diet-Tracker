package products_tools;

import Ganeral.Config;
import text_files_tools.MakeFoldersAndTextFile;

import java.io.IOException;

public class Product {

    public Product(String name, String brand, Macro product_macro, float weight_of_pack){
        this.fileName = name;
        this.brand = brand;
        this.product_macro = product_macro;
        this.weight_of_pack = weight_of_pack;
    }

    public void makeTextFileForProduct(Product product, float macroFor){
        MakeFoldersAndTextFile.makeEmptyFile(fileName, Config.DESTINATION_FOR_TEXT_FILE_PRODUCTS);


        try {
            MakeFoldersAndTextFile.writeProductToFile("Name: "+ product.getName(), fileName);
            MakeFoldersAndTextFile.writeProductToFile("Brand: " + product.getBrand(), fileName);
            MakeFoldersAndTextFile.writeProductToFile("Package has: "+ product.getWeight_of_pack(), fileName);

            MakeFoldersAndTextFile.writeProductToFile("Macro for: " + macroFor, fileName);

            MakeFoldersAndTextFile.writeProductToFile("KCal: " + product.getProduct_macro().getKcal(), fileName);
            MakeFoldersAndTextFile.writeProductToFile("Protein: " + product.getProduct_macro().getProtein(), fileName);
            MakeFoldersAndTextFile.writeProductToFile("Fat: " + product.getProduct_macro().getFat(), fileName);

            MakeFoldersAndTextFile.writeProductToFile("Carbs: " + product.product_macro.getCarbs(), fileName);


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
