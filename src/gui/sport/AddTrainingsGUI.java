package gui.sport;

import configuration.Config;
import tools.calendar_tools.MyDate;
import tools.products_tools.Macro;
import tools.sql_tools.days_statistics.SelectFromDaysStatistics;

import javax.swing.*;
import java.awt.*;
import java.text.Format;
import java.text.SimpleDateFormat;

public class AddTrainingsGUI {

    /*
    MySQL fields:

        training_id
        workout_id
        workout_name
        day_date
        day_name
        location
        training_start
        training_end
        duration
        comment

     */

    //<editor-fold desc="Main - AddWorkoutGUI - components and variables">

    //<editor-fold desc="Panels">
    JPanel addTrainingsPanelMain = new JPanel();
    JPanel addTrainingsPanelNorth = new JPanel();
    JPanel addTrainingsPanelWest = new JPanel();
    JPanel addTrainingsPanelEast = new JPanel();
    JPanel addTrainingsPanelSouth = new JPanel();
    //</editor-fold>

    //<editor-fold desc="Frame">
    JFrame addTrainingsFrame = new JFrame("AddTrainingsGUI");
    //</editor-fold>

    //<editor-fold desc="Buttons">
    JButton addTrainingsAcceptButton = new JButton("Accept");
    JButton backToMainWindowButton = new JButton("Go to Start");
    JButton exitProgramProductWindowButton = new JButton("Exit application");
    JButton clearTextFieldsButton = new JButton("Clear");

    //</editor-fold>

    //<editor-fold desc="Labels">

    JLabel training_idLabel = new JLabel("null");
    JLabel workout_idLabel = new JLabel("null");
    JLabel workout_nameLabel = new JLabel("null");
    JLabel day_dateLabel = new JLabel("null");
    JLabel day_nameLabel = new JLabel("null");
    JLabel locationLabel = new JLabel("null");
    JLabel training_startLabel = new JLabel("null");
    JLabel training_endLabel = new JLabel("null");
    JLabel durationLabel = new JLabel("null");
    JLabel commentLabel = new JLabel("null");


    //</editor-fold>

    //<editor-fold desc="TextFields">

    JTextField training_idTextField = new JTextField("null");
    JTextField workout_idTextField = new JTextField("null");
    JTextField workout_nameTextField = new JTextField("null");
    JTextField day_dateTextField = new JTextField("null");
    JTextField day_nameTextField = new JTextField("null");
    JTextField locationTextField = new JTextField("null");
    JTextField training_startTextField = new JTextField("null");
    JTextField training_endTextField = new JTextField("null");
    JTextField durationTextField = new JTextField("null");
    JTextField commentTextField = new JTextField("null");


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
    BoxLayout panelWestBoxLayout = new BoxLayout(addTrainingsPanelWest, BoxLayout.Y_AXIS);
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
    public AddTrainingsGUI() {

        startAddTrainingsGUI();
    }

    private void startAddTrainingsGUI() {
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
        addTrainingsFrame.setSize(1000, 600);
        addTrainingsFrame.setLayout(new BorderLayout());
    }

    private void addPanelsToFrame() {
        // Add Panels to Frame
        addTrainingsFrame.add(addTrainingsPanelNorth, BorderLayout.NORTH);
        addTrainingsFrame.add(addTrainingsPanelWest, BorderLayout.WEST);
        addTrainingsFrame.add(addTrainingsPanelMain, BorderLayout.CENTER);
        addTrainingsFrame.add(addTrainingsPanelEast, BorderLayout.EAST);
        addTrainingsFrame.add(addTrainingsPanelSouth, BorderLayout.SOUTH);

    }

    private void finishSetUpFrame() {
        addTrainingsFrame.setResizable(true);
        addTrainingsFrame.setLocationRelativeTo(null);
        addTrainingsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addTrainingsFrame.setVisible(true);
    }

    //</editor-fold>

    //<editor-fold desc="Panels methods">

    private void setPanels() {
        //Set Layout
        addTrainingsPanelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addTrainingsPanelMain.setLayout(gridLayoutMainPanel);
        addTrainingsPanelWest.setLayout(panelWestBoxLayout);

        // addProductToDayPanelWest.setLayout(westPanelBoxLayout);
        // Set panels colors
        addTrainingsPanelNorth.setBackground(Color.BLACK);
        addTrainingsPanelSouth.setBackground(Color.GRAY);
        addTrainingsPanelMain.setBackground(Color.WHITE);
        addTrainingsPanelWest.setBackground(Color.DARK_GRAY);
        addTrainingsPanelEast.setBackground(Color.BLUE);

        // Set preferred size of panel
        // DO TO - add hard coded values to cobfig
        addTrainingsPanelNorth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE));
        addTrainingsPanelEast.setPreferredSize(new Dimension(200, 200));
        addTrainingsPanelMain.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER, Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER));
        addTrainingsPanelWest.setPreferredSize(new Dimension(200, 200));
        addTrainingsPanelSouth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE));
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

        addTrainingsPanelSouth.add(addTrainingsAcceptButton);

        //</editor-fold>

        //<editor-fold desc="Add Components to Center Panel">


        // 1 - training_id
        addTrainingsPanelMain.add(training_idLabel);
        addTrainingsPanelMain.add(training_idTextField);
        // 2 - workout_id
        addTrainingsPanelMain.add(workout_idLabel);
        addTrainingsPanelMain.add(workout_idTextField);


        // 3 - workout_name
        addTrainingsPanelMain.add(workout_nameLabel);
        addTrainingsPanelMain.add(workout_nameTextField);


        // 4 - day_date
        addTrainingsPanelMain.add(day_dateLabel);
        addTrainingsPanelMain.add(day_dateTextField);


        // 5 - day_name
        addTrainingsPanelMain.add(day_nameLabel);
        addTrainingsPanelMain.add(day_nameTextField);


        // 6 - location
        addTrainingsPanelMain.add(locationLabel);
        addTrainingsPanelMain.add(locationTextField);


        // 7 - training_start
        addTrainingsPanelMain.add(training_startLabel);
        addTrainingsPanelMain.add(training_startTextField);


        // 8 - training_end
        addTrainingsPanelMain.add(training_endLabel);
        addTrainingsPanelMain.add(training_endTextField);


        // 9 - duration
        addTrainingsPanelMain.add(durationLabel);
        addTrainingsPanelMain.add(durationTextField);


        // 10 - comment
        addTrainingsPanelMain.add(commentLabel);
        addTrainingsPanelMain.add(commentTextField);

        //</editor-fold>
    }

    //</editor-fold>

    //<editor-fold desc="Main Methods">


    //<editor-fold desc="Accept product to calendar table methods">

    //</editor-fold>

    //<editor-fold desc="Action Listeners Classes">
    //</editor-fold>


}

