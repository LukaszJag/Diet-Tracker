package tools.raports_tools;

public class GetBMR {
    public static double getBMR(int height, int weight, int age){
        return 66.5 + (13.75 * weight) + (5.003 * height) - (6.75 * age);
    }
}
