package gui.sport;

import configuration.Config;
import tools.calendar_tools.MyDate;
import tools.products_tools.Macro;
import tools.sql_tools.days_statistics.SelectFromDaysStatistics;

import javax.swing.*;
import java.awt.*;
import java.text.Format;
import java.text.SimpleDateFormat;

public class AddWorkoutsGUI {
    /*
    MySQL fields:

        workout_id
        workout_name
        day_date
        day_name
        exercise_id
        exercise_name
        set_number_of_sets_in_exercise
        amount_of_sets
        amount
        comment

     */

    //<editor-fold desc="Main - AddWorkoutGUI - components and variables">

    //<editor-fold desc="Panels">
    JPanel addWorkoutsPanelMain = new JPanel();
    JPanel addWorkoutsPanelNorth = new JPanel();
    JPanel addWorkoutsPanelWest = new JPanel();
    JPanel addWorkoutsPanelEast = new JPanel();
    JPanel addWorkoutsPanelSouth = new JPanel();
    //</editor-fold>

    //<editor-fold desc="Frame">
    JFrame addWorkoutsFrame = new JFrame("AddWorkoutsGUI");
    //</editor-fold>

    //<editor-fold desc="Buttons">
    JButton addWorkoutsAcceptButton = new JButton("Accept");
    JButton backToMainWindowButton = new JButton("Go to Start");
    JButton exitProgramProductWindowButton = new JButton("Exit application");
    JButton clearTextFieldsButton = new JButton("Clear");

    //</editor-fold>

    //<editor-fold desc="Labels">

    JLabel workout_idLabel = new JLabel("workout_id");
    JLabel workout_nameLabel = new JLabel("workout_name");
    JLabel day_dateLabel = new JLabel("day_date");
    JLabel day_nameLabel = new JLabel("day_name");
    JLabel exercise_idLabel = new JLabel("exercise_id");
    JLabel exercise_nameLabel = new JLabel("exercise_name");
    JLabel set_number_of_sets_in_exerciseLabel = new JLabel("set_number_of_sets_in_exercise");
    JLabel amount_of_setsLabel = new JLabel("amount_of_sets");
    JLabel amountLabel = new JLabel("amount");
    JLabel commentLabel = new JLabel("comment");


    //</editor-fold>

    //<editor-fold desc="TextFields">

    JTextField workout_idTextField = new JTextField("");
    JTextField workout_nameTextField = new JTextField("");
    JTextField day_dateTextField = new JTextField("");
    JTextField day_nameTextField = new JTextField("");
    JTextField exercise_idTextField = new JTextField("");
    JTextField exercise_nameTextField = new JTextField("");
    JTextField set_number_of_sets_in_exerciseTextField = new JTextField("");
    JTextField amount_of_setsTextField = new JTextField("");
    JTextField amountTextField = new JTextField("");
    JTextField commentTextField = new JTextField("");


    //</editor-fold>

    //<editor-fold desc="ComboBox">
    // ComboBox
    JComboBox<String> bodyPartComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4"});

    JComboBox<String> typeOfMeasureComboBox = new JComboBox<>(new String[]{"11", "22", "33", "44"});
    //</editor-fold>

    //<editor-fold desc="RadioButton">

    JRadioButton productSearchSelectedRadioButton = new JRadioButton("Product search");

    JRadioButton brandSearchSelectedRadioButton = new JRadioButton("Brand search");

    //</editor-fold>

    //<editor-fold desc="ButtonGroup">
    ButtonGroup selectSearchTypeButtonGroup;
    //</editor-fold>

    //<editor-fold desc="Layout">
    BoxLayout panelWestBoxLayout = new BoxLayout(addWorkoutsPanelWest, BoxLayout.Y_AXIS);
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

    //</editor-fold>

    //<editor-fold desc="Starting constructors and methods">

    public static void main(String[] args) {
        new AddWorkoutsGUI();
    }

    // Starting Constructor
    public AddWorkoutsGUI() {

        startAddWorkoutGUI();
    }

    private void startAddWorkoutGUI() {
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
        addWorkoutsFrame.setSize(1000, 600);
        addWorkoutsFrame.setLayout(new BorderLayout());
    }

    private void addPanelsToFrame() {
        // Add Panels to Frame
        addWorkoutsFrame.add(addWorkoutsPanelNorth, BorderLayout.NORTH);
        addWorkoutsFrame.add(addWorkoutsPanelWest, BorderLayout.WEST);
        addWorkoutsFrame.add(addWorkoutsPanelMain, BorderLayout.CENTER);
        addWorkoutsFrame.add(addWorkoutsPanelEast, BorderLayout.EAST);
        addWorkoutsFrame.add(addWorkoutsPanelSouth, BorderLayout.SOUTH);

    }

    private void finishSetUpFrame() {
        addWorkoutsFrame.setResizable(true);
        addWorkoutsFrame.setLocationRelativeTo(null);
        addWorkoutsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addWorkoutsFrame.setVisible(true);
    }

    //</editor-fold>

    //<editor-fold desc="Panels methods">

    private void setPanels() {
        //Set Layout
        addWorkoutsPanelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addWorkoutsPanelMain.setLayout(gridLayoutMainPanel);
        addWorkoutsPanelWest.setLayout(panelWestBoxLayout);

        // addProductToDayPanelWest.setLayout(westPanelBoxLayout);
        // Set panels colors
        addWorkoutsPanelNorth.setBackground(Color.BLACK);
        addWorkoutsPanelSouth.setBackground(Color.GRAY);
        addWorkoutsPanelMain.setBackground(Color.WHITE);
        addWorkoutsPanelWest.setBackground(Color.DARK_GRAY);
        addWorkoutsPanelEast.setBackground(Color.BLUE);

        // Set preferred size of panel
        // DO TO - add hard coded values to cobfig
        addWorkoutsPanelNorth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE));
        addWorkoutsPanelEast.setPreferredSize(new Dimension(200, 200));
        addWorkoutsPanelMain.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER, Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER));
        addWorkoutsPanelWest.setPreferredSize(new Dimension(200, 200));
        addWorkoutsPanelSouth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE));
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

        addWorkoutsPanelSouth.add(addWorkoutsAcceptButton);

        //</editor-fold>

        //<editor-fold desc="Add Components to Center Panel">

        // 1 - workout_id
        addWorkoutsPanelMain.add(workout_idLabel);

        addWorkoutsPanelMain.add(workout_idTextField);
        // 2 - workout_name
        addWorkoutsPanelMain.add(workout_nameLabel);
        addWorkoutsPanelMain.add(workout_nameTextField);


        // 3 - day_date
        addWorkoutsPanelMain.add(day_dateLabel);
        addWorkoutsPanelMain.add(day_dateTextField);


        // 4 - day_name
        addWorkoutsPanelMain.add(day_nameLabel);
        addWorkoutsPanelMain.add(day_nameTextField);


        // 5 - exercise_id
        addWorkoutsPanelMain.add(exercise_idLabel);
        addWorkoutsPanelMain.add(exercise_idTextField);


        // 6 - exercise_name
        addWorkoutsPanelMain.add(exercise_nameLabel);
        addWorkoutsPanelMain.add(exercise_nameTextField);


        // 7 - set_number_of_sets_in_exercise
        addWorkoutsPanelMain.add(set_number_of_sets_in_exerciseLabel);
        addWorkoutsPanelMain.add(set_number_of_sets_in_exerciseTextField);


        // 8 - amount_of_sets
        addWorkoutsPanelMain.add(amount_of_setsLabel);
        addWorkoutsPanelMain.add(amount_of_setsTextField);


        // 9 - amount
        addWorkoutsPanelMain.add(amountLabel);
        addWorkoutsPanelMain.add(amountTextField);


        // 10 - comment
        addWorkoutsPanelMain.add(commentLabel);
        addWorkoutsPanelMain.add(commentTextField);

        //</editor-fold>
    }

    //</editor-fold>

    //<editor-fold desc="Main Methods">


    //<editor-fold desc="Accept product to calendar table methods">

    //</editor-fold>

    //<editor-fold desc="Action Listeners Classes">
    //</editor-fold>


}

