package tools.debug_tools;

import static configuration.Config.*;

public class Debug {
    public static void printRedSystemPrintln(String Message){
        System.out.println(ANSI_RED + Message + ANSI_RESET);;
    }

    public static void printYellowSystemPrintln(String Message){
        System.out.println(ANSI_YELLOW + Message + ANSI_RESET);;
    }
}
