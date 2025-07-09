package gui;

import configuration.Config;

import javax.swing.*;
import java.awt.*;

public class AddWorkoutToDataBaseGUI {

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
    JFrame mainFrame = new JFrame("Add Workout");

    //</editor-fold>

    //<editor-fold desc="Layout">

    GridLayout gridLayoutMainPanel = new GridLayout(13, 2, 10, 10);
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
        //addComponentsToPanels();
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

        //addProductToDayPanelNorth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE));
        //addProductToDayPanelEast.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE));
        //addProductToDayPanelMain.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER, Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER));
        //addProductToDayPanelWest.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE));
        //addProductToDayPanelSouth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE));

        //</editor-fold>

    }
    //</editor-fold>

}
