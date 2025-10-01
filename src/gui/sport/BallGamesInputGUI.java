package gui.sport;

import gui.general.InputPatternWindowGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class BallGamesInputGUI {
    static InputPatternWindowGUI inputPatternWindowGUI;
    public static void main(String[] args) {
        inputPatternWindowGUI = new InputPatternWindowGUI();
        inputPatternWindowGUI.setPanelsColors(Color.WHITE, Color.DARK_GRAY, Color.BLUE, Color.GRAY,  Color.BLACK);

        ArrayList<String> fieldsToAdd = new ArrayList<String>(Arrays.asList("workout_ID", "day_date", "day_name", "location_of_workout",
                "general_type_of_workout", "type_of_workout", "name_of_exercise", "number_of_set",
                "reps", "duration_of_break_seconds",
                "duration_in_seconds", "distance_in_meters", "amount_of_sets", "comment"));
        inputPatternWindowGUI.addComponentsToMainPanel(fieldsToAdd);

        inputPatternWindowGUI.displayWindow();

        inputPatternWindowGUI.acceptButton.addActionListener(new AcceptButtonActionListener());
    }

    private static class AcceptButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            inputPatternWindowGUI.getDataFromEveryTextField();
        }
    }
}
