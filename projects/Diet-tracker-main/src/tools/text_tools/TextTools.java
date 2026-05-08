package tools.text_tools;

public class TextTools {
    public static String getRidOfAllBlankSigns(String textInput){
        String outputWithoutBlanksSigns = "";

        for (int i = 0; i < textInput.length(); i++) {
            if ((textInput.charAt(i) <= '~') && (textInput.charAt(i) >= '!')){
                outputWithoutBlanksSigns += textInput.charAt(i);
            }
        }


        return  outputWithoutBlanksSigns;
    }
}
