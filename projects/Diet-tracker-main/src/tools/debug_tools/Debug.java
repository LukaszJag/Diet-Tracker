package tools.debug_tools;

import static configuration.Config.*;

public class Debug {
    public static void printRedSystemPrintln(String Message){
        System.out.println(ANSI_RED + Message + ANSI_RESET);;
    }

    public static void printYellowSystemPrintln(String Message){
        System.out.println(ANSI_YELLOW + Message + ANSI_RESET);;
    }

    public static void printOrangeSystemPrintln(String nameOfMethod, String variableName, String value){
        System.out.println(ANSI_YELLOW + nameOfMethod + " \t->\t " + variableName + " \t->\t " + value +ANSI_RESET);;
    }

    public static void printKeyAndValue(String key, String value, int amountOfTabsBetween){
        int amountOfTabs = (key.length()+1) / 4;
        String tabs = "";

        for (int j = 0; j < 6-amountOfTabs; j++) {
            tabs += "\t";
        }

        System.out.println("key: " + key + tabs + " value: " + value);
    }
}
