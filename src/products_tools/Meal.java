package products_tools;

public class Meal {
    Macro mealMacro;
    DishSingleProduct[] dishSingleProductsInMeal;

    public Meal(){
        mealMacro = new Macro(0,0,0,0);
        dishSingleProductsInMeal = new DishSingleProduct[20];
    }

    private void addNewSingleProductDish(DishSingleProduct newSingleProductDish){
        for(int i = 0; i < dishSingleProductsInMeal.length; i++) {
            if(dishSingleProductsInMeal[i] != null){
                System.out.println("Position occupied by: " + dishSingleProductsInMeal[i].getProduct().getName());
            }

        }
    }
}
