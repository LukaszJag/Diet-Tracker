package gui.sport;

import configuration.Config;
import tools.calendar_tools.MyDate;
import tools.products_tools.Macro;
import tools.sql_tools.days_statistics.SelectFromDaysStatistics;
import tools.sql_tools.general.Select;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddExercisesToLibraryGUI {
    /*
    MySQL fields:

     exercise_id
     exercise_name
     added_weight
     body_part
     comment
     exercise_target
     instructions
     type_of_measure
     weight_type
     */

    //<editor-fold desc="Main - AddExercisesToLibraryGUI - components and variables">

    //<editor-fold desc="Panels">
    JPanel addExercisesToLibraryPanelMain = new JPanel();
    JPanel addExercisesToLibraryPanelNorth = new JPanel();
    JPanel addExercisesToLibraryPanelWest = new JPanel();
    JPanel addExercisesToLibraryPanelEast = new JPanel();
    JPanel addExercisesToLibraryPanelSouth = new JPanel();
    //</editor-fold>

    //<editor-fold desc="Frame">
    JFrame addExercisesToLibraryFrame = new JFrame("Add Exercises To Library");
    //</editor-fold>

    //<editor-fold desc="Buttons">
    JButton addExerciseAcceptButton = new JButton("Accept");
    JButton backToMainWindowButton = new JButton("Go to Start");
    JButton exitProgramProductWindowButton = new JButton("Exit application");
    JButton clearTextFieldsButton = new JButton("Clear");

    //</editor-fold>

    //<editor-fold desc="Labels">
    JLabel exercise_idLabel = new JLabel("exercise_id");
    JLabel exercise_nameLabel = new JLabel("exercise_name");
    JLabel exercise_targetLabel = new JLabel("exercise_target");
    JLabel instructionsLabel = new JLabel("instructions");
    JLabel body_partLabel = new JLabel("body_part");
    JLabel added_weightLabel = new JLabel("added_weight");
    JLabel weight_typeLabel = new JLabel("weight_type");
    JLabel commentLabel = new JLabel("comment");
    JLabel type_of_measureLabel = new JLabel("type_of_measure");

    ArrayList<JLabel> allLabelsArrayList = new ArrayList<JLabel>(List.of(new JLabel[]{exercise_idLabel, exercise_nameLabel, added_weightLabel, body_partLabel,
            commentLabel, exercise_targetLabel, instructionsLabel, type_of_measureLabel, weight_typeLabel
    }));

    //</editor-fold>

    //<editor-fold desc="TextFields">
    JTextField exercise_idJTextField = new JTextField("will be give by program");
    JTextField exercise_nameJTextField = new JTextField();

    JTextField instructionsJTextField = new JTextField();

    JTextField added_weightJTextField = new JTextField();
    JTextField weight_typeJTextField = new JTextField();
    JTextField commentJTextField = new JTextField();


    //</editor-fold>

    //<editor-fold desc="ComboBox">
    // ComboBox
    JComboBox<String> exercise_targetJComboBox = new JComboBox<>(new String[]{"none", "No specific", "Cardio", "Mobility", "Strength"});
    JComboBox<String> bodyPartComboBox = new JComboBox<>(new String[]{"none", "Chest", "Back", "Biceps", "Triceps", "Legs"});

    JComboBox<String> typeOfMeasureComboBox = new JComboBox<>(new String[]{
            "none", "duration", "reps", "reps and weight", "distance", "distance and duration"});
    //</editor-fold>

    //<editor-fold desc="RadioButton">

    JRadioButton productSearchSelectedRadioButton = new JRadioButton("Product search");

    JRadioButton brandSearchSelectedRadioButton = new JRadioButton("Brand search");

    //</editor-fold>

    //<editor-fold desc="ButtonGroup">
    ButtonGroup selectSearchTypeButtonGroup;
    //</editor-fold>

    //<editor-fold desc="Layout">
    BoxLayout panelWestBoxLayout = new BoxLayout(addExercisesToLibraryPanelWest, BoxLayout.Y_AXIS);
    GridLayout gridLayoutMainPanel = new GridLayout(11, 2, 10, 10);
    GridLayout checkDaysStatisticsDialogGridLayout = new GridLayout(34, 4, 0, 0);
    GridLayout panelWestGridLayout = new GridLayout(9, 1, 5, 10);

    //</editor-fold>

    //<editor-fold desc="TextAreas">
    JTextArea dayMacroTextArea = new JTextArea(6, 4);
    //</editor-fold>

    //<editor-fold desc="Tables">
    public JTable macroTable = new JTable(6, 2);
    //</editor-fold>

    //<editor-fold desc="JComponent">
    ArrayList<Object> allJTextFieldArrayList = new ArrayList<Object>(List.of(new Object[]{
            // Data in order from top to bottom
            exercise_idJTextField,
            exercise_nameJTextField,
            exercise_targetJComboBox,
            instructionsJTextField,
            bodyPartComboBox,
            added_weightJTextField,
            weight_typeJTextField,
            commentJTextField,
            typeOfMeasureComboBox
    }));

    ArrayList<JLabel> allJLabelFieldArrayList = new ArrayList<JLabel>(List.of(new JLabel[]{
            // Data in order from top to bottom
            exercise_idLabel,
            exercise_nameLabel,
            exercise_targetLabel,
            instructionsLabel,
            body_partLabel,
            added_weightLabel,
            weight_typeLabel,
            commentLabel,
            type_of_measureLabel
    }));
    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="Starting constructors and methods">

    public static void main(String[] args) {
        new AddExercisesToLibraryGUI();
    }

    // Starting Constructor
    public AddExercisesToLibraryGUI() {

        startAddExercisesToLibraryGUI();
    }

    private void startAddExercisesToLibraryGUI() {
        setFrame();
        setPanels();
        addComponentsToPanels();
        addPanelsToFrame();
        finishSetUpFrame();
    }

    //</editor-fold>

    //<editor-fold desc="Frame methods">

    private void setFrame() {
        // Set window size
        // hard coded - to move to config class
        addExercisesToLibraryFrame.setSize(1000, 600);
        addExercisesToLibraryFrame.setLayout(new BorderLayout());
    }

    private void addPanelsToFrame() {
        // Add Panels to Frame
        addExercisesToLibraryFrame.add(addExercisesToLibraryPanelNorth, BorderLayout.NORTH);
        addExercisesToLibraryFrame.add(addExercisesToLibraryPanelWest, BorderLayout.WEST);
        addExercisesToLibraryFrame.add(addExercisesToLibraryPanelMain, BorderLayout.CENTER);
        addExercisesToLibraryFrame.add(addExercisesToLibraryPanelEast, BorderLayout.EAST);
        addExercisesToLibraryFrame.add(addExercisesToLibraryPanelSouth, BorderLayout.SOUTH);

    }

    private void finishSetUpFrame() {
        addExercisesToLibraryFrame.setResizable(true);
        addExercisesToLibraryFrame.setLocationRelativeTo(null);
        addExercisesToLibraryFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addExercisesToLibraryFrame.setVisible(true);
    }

    //</editor-fold>

    //<editor-fold desc="Panels methods">

    private void setPanels() {
        //Set Layout
        addExercisesToLibraryPanelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addExercisesToLibraryPanelMain.setLayout(gridLayoutMainPanel);
        addExercisesToLibraryPanelWest.setLayout(panelWestBoxLayout);

        // addProductToDayPanelWest.setLayout(westPanelBoxLayout);
        // Set panels colors
        addExercisesToLibraryPanelNorth.setBackground(Color.BLACK);
        addExercisesToLibraryPanelSouth.setBackground(Color.GRAY);
        addExercisesToLibraryPanelMain.setBackground(Color.WHITE);
        addExercisesToLibraryPanelWest.setBackground(Color.DARK_GRAY);
        addExercisesToLibraryPanelEast.setBackground(Color.BLUE);

        // Set preferred size of panel
        // DO TO - add hard coded values to cobfig
        addExercisesToLibraryPanelNorth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE));
        addExercisesToLibraryPanelEast.setPreferredSize(new Dimension(200, 200));
        addExercisesToLibraryPanelMain.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER, Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER));
        addExercisesToLibraryPanelWest.setPreferredSize(new Dimension(200, 200));
        addExercisesToLibraryPanelSouth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE));
    }

    private void addComponentsToPanels() {

        //<editor-fold desc="Global variables for panels">
        // Global variables for panels
        Format format = new SimpleDateFormat("EEEE");
        java.util.Date utilDateImport = new java.util.Date();
        String dayNameCurrentDateOnStartWindow = format.format(utilDateImport);

        Macro curretDayMacro = SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(MyDate.getCurrentDayInSQLFormat());
        //</editor-fold>

        //<editor-fold desc="Add Components to Panel - North">

        //</editor-fold>

        //<editor-fold desc="Add Components to Panel - West">

        //</editor-fold>

        //<editor-fold desc="Add Components to Panel - East">

        //</editor-fold>

        //<editor-fold desc="Add Components to Panel - South">
        addExerciseAcceptButton.addActionListener(new AddExerciseAcceptButtonActionListener());
        addExercisesToLibraryPanelSouth.add(addExerciseAcceptButton);
        //</editor-fold>

        //<editor-fold desc="Add Components to Center Panel">

        // 1 - exercise_id
        addExercisesToLibraryPanelMain.add(exercise_idLabel);
        addExercisesToLibraryPanelMain.add(exercise_idJTextField);


        // 2 - exercise_name
        addExercisesToLibraryPanelMain.add(exercise_nameLabel);
        addExercisesToLibraryPanelMain.add(exercise_nameJTextField);


        // 3 - exercise_target
        addExercisesToLibraryPanelMain.add(exercise_targetLabel);
        addExercisesToLibraryPanelMain.add(exercise_targetJComboBox);


        // 4 - instructions
        addExercisesToLibraryPanelMain.add(instructionsLabel);
        addExercisesToLibraryPanelMain.add(instructionsJTextField);


        // 5 - body_part
        addExercisesToLibraryPanelMain.add(body_partLabel);
        addExercisesToLibraryPanelMain.add(bodyPartComboBox);


        // 6 - added_weight
        addExercisesToLibraryPanelMain.add(added_weightLabel);
        addExercisesToLibraryPanelMain.add(added_weightJTextField);


        // 7 - weight_type
        addExercisesToLibraryPanelMain.add(weight_typeLabel);
        addExercisesToLibraryPanelMain.add(weight_typeJTextField);


        // 8 - comment
        addExercisesToLibraryPanelMain.add(commentLabel);
        addExercisesToLibraryPanelMain.add(commentJTextField);


        // 9 - type_of_measure
        addExercisesToLibraryPanelMain.add(type_of_measureLabel);
        addExercisesToLibraryPanelMain.add(typeOfMeasureComboBox);


        //</editor-fold>
    }


    //</editor-fold>

    //<editor-fold desc="Data managing">

    private HashMap<String, String> getDataFromGUI() {
        String[] contentOfComponents = new String[allJTextFieldArrayList.size()];
        for (int i = 0; i < allJTextFieldArrayList.size(); i++) {

            if (allJTextFieldArrayList.get(i) instanceof JTextField) {
                contentOfComponents[i] = ((JTextField) allJTextFieldArrayList.get(i)).getText();
            }

            if (allJTextFieldArrayList.get(i) instanceof JComboBox) {
                contentOfComponents[i] = ((JComboBox) allJTextFieldArrayList.get(i)).getSelectedItem().toString();
            }
        }

        for (int i = 0; i < contentOfComponents.length; i++) {
            if (contentOfComponents[i].isEmpty()) {
                System.out.println("empty");
            } else {
                System.out.println(contentOfComponents[i]);
            }
        }

        HashMap<String, String> resultHashMap = new HashMap<>();

        // TODO
        // resultHashMap.

        for (int i = 0; i < allJLabelFieldArrayList.size(); i++) {
            resultHashMap.put(contentOfComponents[i], allJLabelFieldArrayList.get(i).getText());
        }

        return resultHashMap;
    }

    private String getNewIDForExercise() {
            /* id examples (always 6 characters):
            00001e
            00022e
            00123e

             */
            String idString = "";

            // Get last id
            // Create new next id



            return idString;
    }

    private String[] getAllIDsInTable(){
        // TODO - 17.12
        Select.getAllValuesInColumn("exercises_library_table_test", 0);

        return null;
    }

    //</editor-fold>

    //<editor-fold desc="Action Listeners Classes">
    private class AddExerciseAcceptButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            HashMap<String, String> dataFromGUI = getDataFromGUI();

            // TODO
            //System.out.println(InsertToTable.prepareSQLStatement("exercise", dataFromGUI));



        }
    }
    //</editor-fold>

}
