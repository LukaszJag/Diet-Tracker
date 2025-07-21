package gui.gym;

import configuration.Config;

import javax.swing.*;
import java.awt.*;

public class AddWorkoutToDataBaseGUI {

//    (`ID`,
//            `day_date`,
//            `day_name`,
//            `type_of_workout`,
//            `location_of_workout`,
//            `enter_workout_area_full_time_date`,
//            `start_workout_time_full`,
//            `end_workout_time_full`,
//            `duration_workout_time_seconds`)

    //<editor-fold desc="Main - AddProductToCalendarDay - components and variables">

    //<editor-fold desc="Panels">
    JPanel panelMain = new JPanel();
    JPanel panelNorth = new JPanel();
    JPanel panelWest = new JPanel();
    JPanel panelEast = new JPanel();
    JPanel panelSouth = new JPanel();

    //</editor-fold>

    //<editor-fold desc="Frame">
    // Frame
    JFrame mainFrame = new JFrame("Add Workout to - table_for_workout - table");

    //</editor-fold>

    //<editor-fold desc="Layout">
    GridLayout gridLayoutMainPanel = new GridLayout(8, 2, 10, 10);

    //</editor-fold>

    //<editor-fold desc="Buttons">
    JButton selectDateButton = new JButton("Select date");
    JButton acceptAddWorkoutButton = new JButton("Accept");

    //</editor-fold>

    //<editor-fold desc="Labels">
    // Labels
    JLabel IDLabel = new JLabel("ID:");
    JLabel dayDateLabel = new JLabel("dayDate:");
    JLabel dayNameDateLabel = new JLabel("dayNameDate:");
    JLabel typeOfWorkoutLabel = new JLabel("typeOfWorkout:");
    JLabel locationOfWorkoutLabel = new JLabel("locationOfWorkout:");
    JLabel enterWorkoutAreaFullTimeDateLabel = new JLabel("Enter Time:");
    JLabel startWorkoutTimeFullLabel = new JLabel("Start workout time:");
    JLabel endWorkoutTimeFullLabel = new JLabel("End workout time:");
    JLabel durationWorkoutTimeSecondsLabel = new JLabel("Duration of workout:");

    JLabel selectedDateLabel = new JLabel("Selected Date");
    JLabel selectedDateValueLabel = new JLabel("yyyy-mm-dd");

    //</editor-fold>

    //<editor-fold desc="TextFields">
    // TextFields
    JTextField dayDateTextField = new JTextField();
    JTextField dayNameDateTextField = new JTextField();
    JTextField typeOfWorkoutTextField = new JTextField();
    JTextField locationOfWorkoutTextField = new JTextField();
    JTextField enterWorkoutAreaFullTimeDateTextField = new JTextField();
    JTextField startWorkoutTimeFullTextField = new JTextField();
    JTextField endWorkoutTimeFullTextField = new JTextField();
    JTextField durationWorkoutTimeSecondsTextField = new JTextField();
    //</editor-fold>

    //<editor-fold desc="ComboBox">
    // ComboBox
    JComboBox<String> typeOfWorkoutComboBox = new JComboBox<>(new String[]{"static strength", "walking", "running",
            "swimming", "cardio", "lift weight", "calisthenics", "street workout", "stretch", "riding bike", "mobility",});

    //</editor-fold>


    //</editor-fold>

    //<editor-fold desc="Starting Constructors">
    // Starting Constructor
    public AddWorkoutToDataBaseGUI() {
        startAddWorkoutToTableForWorkout();
    }
    //</editor-fold>

    private void startAddWorkoutToTableForWorkout() {
        setFrame();
        setPanels();
        addComponentsToPanels();
        addPanelsToFrame();
        finishSetUpFrame();
    }

    private void addPanelsToFrame() {
        // Add Panels to Frame
        mainFrame.add(panelNorth, BorderLayout.NORTH);
        mainFrame.add(panelWest, BorderLayout.WEST);
        mainFrame.add(panelMain, BorderLayout.CENTER);
        mainFrame.add(panelEast, BorderLayout.EAST);
        mainFrame.add(panelSouth, BorderLayout.SOUTH);

    }

    //<editor-fold desc="startAddWorkoutToTableForWorkout - methods">
    private void setFrame() {
        // Set window size
        mainFrame.setSize(Config.ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_WINDOWS_WIDTH, Config.ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_WINDOWS_HEIGHT);
        mainFrame.setLayout(new BorderLayout());
    }

    private void finishSetUpFrame() {
        mainFrame.setResizable(true);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    private void setPanels() {
        //<editor-fold desc="Set Layout">
        panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelMain.setLayout(gridLayoutMainPanel);
        //</editor-fold>

        //<editor-fold desc="Set panels colors">
        panelNorth.setBackground(Color.BLACK);
        panelSouth.setBackground(Color.GRAY);
        panelMain.setBackground(Color.WHITE);
        panelWest.setBackground(Color.DARK_GRAY);
        panelEast.setBackground(Color.BLUE);
        //</editor-fold>

        //<editor-fold desc="Set preferred size of panel">

        panelNorth.setPreferredSize(new Dimension(Config.ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_PANELS_NORTH_SIZE, Config.ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_PANELS_NORTH_SIZE));
        panelEast.setPreferredSize(new Dimension(Config.ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_PANELS_WEST_EAST_SIZE, Config.ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_PANELS_WEST_EAST_SIZE));
        panelMain.setPreferredSize(new Dimension(Config.ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_PANELS_CENTER, Config.ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_PANELS_CENTER));
        panelWest.setPreferredSize(new Dimension(Config.ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_PANELS_WEST_EAST_SIZE, Config.ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_PANELS_WEST_EAST_SIZE));
        panelSouth.setPreferredSize(new Dimension(Config.ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_PANELS_SOUTH_SIZE, Config.ADD_WORKOUT_TO_DATABASE_GUI_WINDOW_PANELS_SOUTH_SIZE));

        //</editor-fold>

    }

    private void addComponentsToPanels() {

        //<editor-fold desc="panelSouth - add components">
        panelSouth.add(acceptAddWorkoutButton);
        //</editor-fold>

        //<editor-fold desc="panelMain - add components">
        panelMain.add(dayDateLabel);
        panelMain.add(dayDateTextField);

        panelMain.add(dayNameDateLabel);
        panelMain.add(dayNameDateTextField);

        panelMain.add(typeOfWorkoutLabel);
        panelMain.add(typeOfWorkoutComboBox);

        panelMain.add(locationOfWorkoutLabel);
        panelMain.add(locationOfWorkoutTextField);

        panelMain.add(enterWorkoutAreaFullTimeDateLabel);
        panelMain.add(enterWorkoutAreaFullTimeDateTextField);

        panelMain.add(startWorkoutTimeFullLabel);
        panelMain.add(startWorkoutTimeFullTextField);

        panelMain.add(endWorkoutTimeFullLabel);
        panelMain.add(endWorkoutTimeFullTextField);

        panelMain.add(durationWorkoutTimeSecondsLabel);
        panelMain.add(durationWorkoutTimeSecondsTextField);
        //</editor-fold>

        //<editor-fold desc="panelWest - add components">
        panelWest.add(selectDateButton);

        selectedDateLabel.setForeground(Color.WHITE);
        panelWest.add(selectedDateLabel);

        selectedDateValueLabel.setForeground(Color.WHITE);
        panelWest.add(selectedDateValueLabel);
        //</editor-fold>


    }
    //</editor-fold>

}
