package tools.arrays_tools;

public class ArraysTools {
    public static void printArrayWithoutNull(String[] arrayToPrint){
        for (int i = 0; i < arrayToPrint.length; i++) {
            if (arrayToPrint[i] != null) {
                System.out.println(arrayToPrint[i] + "\n");
            }
        }
    }

    public static void printArray(String[] arrayToPrint){
        for (int i = 0; i < arrayToPrint.length; i++) {
            System.out.println(arrayToPrint[i]);
        }
    }

    public static void printArrayWithNumOfPosition(String[] arrayToPrint){
        for (int i = 0; i < arrayToPrint.length; i++) {
            System.out.println("[" + i + "]:" + arrayToPrint[i]);
        }
    }
}
