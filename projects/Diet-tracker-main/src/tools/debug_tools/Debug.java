package tools.debug_tools;

import static configuration.Config.*;

public class Debug {
    public static void printRedSystemPrintln(String message){
        System.out.println(ANSI_RED + message + ANSI_RESET);;
    }

    public static void printYellowSystemPrintln(String message){
        System.out.println(ANSI_YELLOW + message + ANSI_RESET);;
    }



    public static void printBlueSystemPrintln(String message){
        System.out.println(ANSI_BLUE + message +  ANSI_RESET);;
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
