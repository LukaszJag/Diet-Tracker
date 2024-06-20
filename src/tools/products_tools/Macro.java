package tools.products_tools;

public class Macro {
    // Values
    private float kcal;
    private float protein;
    private float fat;
    private float carbs;

    // Constructors
    public Macro(float kcal, float protein, float fat, float carbs){
        this.kcal = kcal;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    public static boolean isMacroEqual(Macro macro1, Macro macro2){
        if (macro1.getKcal() == macro2.getKcal()){

        }else{
            return false;
        }

        if (macro1.getProtein() == macro2.getProtein()){

        }else{
            return false;
        }

        if (macro1.getFat() == macro2.getFat()){

        }else{
            return false;
        }

        if (macro1.getCarbs() == macro2.getCarbs()){

        }else{
            return false;
        }

        return true;
    }
    public static Macro getConsumedMacro(float amount, Macro macro){
        float amountMultiplier = amount/100.00f;
        String floatValue = String.format("%2f",amountMultiplier);
        amountMultiplier = Float.valueOf(floatValue);
        Macro consumedMacro = new Macro(
                amountMultiplier * macro.getKcal(),
                amountMultiplier * macro.getProtein(),
                amountMultiplier * macro.getFat(),
                amountMultiplier * macro.getCarbs()
        );
        return consumedMacro;
    }

    public static Macro cutNumberPrecisionForAllValues(Macro macro){
        Macro cuttedMacro = new Macro(
                Float.valueOf(String.format("%.0f", macro.getKcal())),
                 Float.valueOf(String.format("%.0f", macro.getProtein())),
                 Float.valueOf(String.format("%.0f", macro.getFat())),
                 Float.valueOf(String.format("%.0f", macro.getCarbs()))
        );

                 return cuttedMacro;
    }

    public static void printAllValues(Macro macro){
        System.out.println("Kcal \t: " + macro.getKcal());
        System.out.println("Protein : " + macro.getProtein());
        System.out.println("Fat \t: " + macro.getFat());
        System.out.println("Carbs \t: " + macro.getCarbs());
    }

    public static void showAllMacroData(Macro macro){
        System.out.println("kcal: " + macro.getKcal());
        System.out.println("protein: " + macro.getProtein());
        System.out.println("fat: " + macro.getFat());
        System.out.println("carbs: " + macro.getCarbs());

    }
    // Getters and Setters
    public float getKcal() {
        return kcal;
    }

    public void setKcal(float kcal) {
        this.kcal = kcal;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }
}
