package products_tools;

public class Product {

    Product(String name, String brand, Macro product_macro, float weight_of_pack){
        this.name = name;
        this.brand = brand;
        this.product_macro = product_macro;
        this.weight_of_pack = weight_of_pack;
    }
    private String name;

    private String brand;
    private Macro product_macro;

    private float weight_of_pack;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
