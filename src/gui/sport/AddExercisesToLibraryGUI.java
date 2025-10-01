package gui.sport;

import configuration.Config;
import gui.diet.AddProductToCalendarDay;
import gui.diet.CalendarMonthStatsView;
import logs.Log;
import runners_and_tests.run_update.RunnerFullUpdateDayStatistics;
import tools.calendar_tools.DayInCalendar;
import tools.calendar_tools.MyDate;
import tools.products_tools.Macro;
import tools.products_tools.Product;
import tools.sql_tools.SQLSelect;
import tools.sql_tools.calendar.InsertToCalendarDayTable;
import tools.sql_tools.days_statistics.SelectFromDaysStatistics;
import tools.text_files_tools.FilesTools;
import tools.time_date_tools.DateTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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
    //<editor-fold desc="Main - AddProductToCalendarDay - components and variables">

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
    JButton addProductToDayAcceptButton = new JButton("Accept");
    JButton backToMainWindowButton = new JButton("Go to Start");
    JButton exitProgramProductWindowButton = new JButton("Exit application");
    JButton clearTextFieldsButton = new JButton("Clear");

    //</editor-fold>

    //<editor-fold desc="Labels">
    JLabel exercise_idLabel = new JLabel("null");
    JLabel exercise_nameLabel = new JLabel("null");
    JLabel added_weightLabel = new JLabel("null");
    JLabel body_partLabel = new JLabel("null");
    JLabel commentLabel = new JLabel("null");
    JLabel exercise_targetLabel = new JLabel("null");
    JLabel instructionsLabel = new JLabel("null");
    JLabel type_of_measureLabel = new JLabel("null");
    JLabel weight_typeLabel = new JLabel("null");
    ArrayList<JLabel> allLabelsArrayList = new ArrayList<JLabel>(List.of(new JLabel[]{exercise_idLabel, exercise_nameLabel, added_weightLabel, body_partLabel,
            commentLabel, exercise_targetLabel, instructionsLabel, type_of_measureLabel, weight_typeLabel
    }));

    //</editor-fold>

    //<editor-fold desc="TextFields">
    JTextField exercise_idJTextField = new JTextField();
    JTextField exercise_nameJTextField = new JTextField();
    JTextField added_weightJTextField = new JTextField();
    JTextField body_partJTextField = new JTextField();
    JTextField commentJTextField = new JTextField();
    JTextField exercise_targetJTextField = new JTextField();
    JTextField instructionsJTextField = new JTextField();
    JTextField type_of_measureJTextField = new JTextField();
    JTextField weight_typeJTextField = new JTextField();

    ArrayList<JTextField> allJTextFieldArrayList = new ArrayList<JTextField>(List.of(new JTextField[]{exercise_idJTextField, exercise_nameJTextField,
            added_weightJTextField, body_partJTextField,
            commentJTextField, exercise_targetJTextField, instructionsJTextField, type_of_measureJTextField, weight_typeJTextField}));
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
    BoxLayout panelWestBoxLayout = new BoxLayout(addExercisesToLibraryPanelWest, BoxLayout.Y_AXIS);
    GridLayout gridLayoutMainPanel = new GridLayout(15, 2, 10, 10);
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
        addExercisesToLibraryPanelNorth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE));
        addExercisesToLibraryPanelEast.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE));
        addExercisesToLibraryPanelMain.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER, Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER));
        addExercisesToLibraryPanelWest.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE));
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

        //</editor-fold>

        //<editor-fold desc="Add Components to Center Panel">

        // 1 - row
        // 2 - row
        // 3 - row
        // 4 - row
        // 5 - row
        // 6 - row
        // 7 - row
        // 8 - row
        // 9 - row

        //</editor-fold>
    }

    //</editor-fold>

    //<editor-fold desc="Main Methods">



    //<editor-fold desc="Accept product to calendar table methods">

    //</editor-fold>

    //<editor-fold desc="Action Listeners Classes">
    //</editor-fold>


}
