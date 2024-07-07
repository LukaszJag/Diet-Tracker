package gui;

import configuration.Config;

import javax.swing.*;
import java.awt.*;

public class CalendarMonthStatsView {
    //<editor-fold desc="Main - Calendar Month Stats View - components and variables">

    //<editor-fold desc="Frames">
    JFrame mainWindow = new JFrame("Calendar month stats view");
    //</editor-fold>

    //<editor-fold desc="Panels">
    JPanel calendarMonthStatsViewPanelMain = new JPanel();
    JPanel calendarMonthStatsViewPanelNorth = new JPanel();
    JPanel calendarMonthStatsViewPanelWest = new JPanel();
    JPanel calendarMonthStatsViewPanelEast = new JPanel();
    JPanel calendarMonthStatsViewPanelSouth = new JPanel();
    //</editor-fold>

    //<editor-fold desc="Buttons">
    JButton[] daysButtons = new JButton[30];
    //</editor-fold>

    //<editor-fold desc="GridLayouts">
    GridLayout mainPanelGridLayout = new GridLayout(5, 7, 10, 10);
    GridLayout northPanelGridLayout = new GridLayout(2, 5, 5, 5);
    //</editor-fold>

    //<editor-fold desc="Labels">
    JLabel currentDayDateNorthPanelLabel = new JLabel("Current date: ????-??-??");
    JLabel currentDayMacroTitleNorthPanelLabel = new JLabel("Macro: ");
    JLabel currentDayMacroValuesNorthPanelLabel = new JLabel("Kcal: ????,?? Protein: ????,??g Fat: ????,??g Carbs: ????,??g");
    //</editor-fold>

    //</editor-fold>

    public void startWindow() {
        setMainWindowSizeAndLayout();
        setPanels();
        setDaysButtons();
        addComponentsToPanels();
        addPanelsToFrame();
        finishSetUpFrame();
    }

    //<editor-fold desc="Start window: methods">
    private void addComponentsToPanels() {

        prepareAndAddContentToMainPanel();
        prepareAndAddContentToNorthPanel();
        prepareAndAddContentToWestPanel();
        prepareAndAddContentToEastPanel();
        prepareAndAddContentToSouthPanel();

    }

    //<editor-fold desc="Prepare and add content to: Panels">
    private void prepareAndAddContentToMainPanel() {
        for (int i = 0; i < 5; i++) {
            calendarMonthStatsViewPanelMain.add(new JLabel(""));
        }
        for (int i = 0; i < daysButtons.length; i++) {
            calendarMonthStatsViewPanelMain.add(daysButtons[i]);
        }
    }

    private void prepareAndAddContentToNorthPanel() {
        calendarMonthStatsViewPanelNorth.setLayout(northPanelGridLayout);

        calendarMonthStatsViewPanelNorth.add(currentDayDateNorthPanelLabel, 0, 0);

        calendarMonthStatsViewPanelNorth.add(currentDayMacroTitleNorthPanelLabel, 0, 1);

        calendarMonthStatsViewPanelNorth.add(currentDayMacroValuesNorthPanelLabel, 0, 2);

        //<editor-fold desc="Color and size of font in labels">
        currentDayDateNorthPanelLabel.setForeground(Config.northPanelStaticLabelsColor);
        currentDayDateNorthPanelLabel.setFont(calendarMonthStatsViewPanelNorth.getFont().deriveFont(20.0f));
        currentDayMacroTitleNorthPanelLabel.setForeground(Config.northPanelStaticLabelsColor);
        currentDayMacroValuesNorthPanelLabel.setForeground(Config.northPanelStaticLabelsColor);

        currentDayDateNorthPanelLabel.setOpaque(true);
        currentDayMacroTitleNorthPanelLabel.setOpaque(true);
        currentDayMacroValuesNorthPanelLabel.setOpaque(true);

        currentDayDateNorthPanelLabel.setBackground(Color.RED);
        currentDayMacroTitleNorthPanelLabel.setBackground(Color.BLUE);
        currentDayMacroValuesNorthPanelLabel.setBackground(Color.GREEN);
        //</editor-fold>

    }

    private void prepareAndAddContentToSouthPanel() {
    }

    private void prepareAndAddContentToEastPanel() {

    }

    private void prepareAndAddContentToWestPanel() {

    }


    //</editor-fold>

    private void setMainWindowSizeAndLayout() {
        // Set window size
        mainWindow.setSize(Config.CALENDAR_MONTH_STATS_VIEW_WINDOWS_WIDTH, Config.CALENDAR_MONTH_STATS_VIEW_WINDOWS_HEIGHT);
        mainWindow.setLayout(new BorderLayout());
    }

    private void finishSetUpFrame() {
        mainWindow.setResizable(false);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }

    private void addPanelsToFrame() {
        // Add Panels to Frame
        mainWindow.add(calendarMonthStatsViewPanelNorth, BorderLayout.NORTH);
        mainWindow.add(calendarMonthStatsViewPanelWest, BorderLayout.WEST);
        mainWindow.add(calendarMonthStatsViewPanelMain, BorderLayout.CENTER);
        mainWindow.add(calendarMonthStatsViewPanelEast, BorderLayout.EAST);
        mainWindow.add(calendarMonthStatsViewPanelSouth, BorderLayout.SOUTH);

    }

    private void setPanels() {
        //Set Layout
        calendarMonthStatsViewPanelMain.setBorder(BorderFactory.createEmptyBorder(7, 5, 5, 5));
        calendarMonthStatsViewPanelMain.setLayout(mainPanelGridLayout);

        // Set panels colors
        calendarMonthStatsViewPanelNorth.setBackground(Color.BLACK);
        calendarMonthStatsViewPanelSouth.setBackground(Color.GRAY);
        calendarMonthStatsViewPanelMain.setBackground(Color.WHITE);
        calendarMonthStatsViewPanelWest.setBackground(Color.DARK_GRAY);
        calendarMonthStatsViewPanelMain.setBackground(Color.BLUE);

        // Set preferred size of panel
        calendarMonthStatsViewPanelNorth.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_NORTH_SIZE, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_NORTH_SIZE));
        calendarMonthStatsViewPanelEast.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE));
        calendarMonthStatsViewPanelMain.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_CENTER, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_CENTER));
        calendarMonthStatsViewPanelWest.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE));
        calendarMonthStatsViewPanelSouth.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_SOUTH_SIZE, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_SOUTH_SIZE));
    }

    private void setDaysButtons() {
        for (int i = 0; i < daysButtons.length; i++) {
            daysButtons[i] = new JButton(String.valueOf(i + 1));
            daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
        }
    }
    //</editor-fold>

}
