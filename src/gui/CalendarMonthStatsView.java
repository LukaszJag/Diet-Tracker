package gui;

import configuration.Config;

import javax.swing.*;
import java.awt.*;

public class CalendarMonthStatsView {
    JFrame mainWindow = new JFrame("Calendar month stats view");
    // Panels
    JPanel calendarMonthStatsViewPanelMain = new JPanel();
    JPanel calendarMonthStatsViewPanelNorth = new JPanel();
    JPanel calendarMonthStatsViewPanelWest = new JPanel();
    JPanel calendarMonthStatsViewPanelEast = new JPanel();
    JPanel calendarMonthStatsViewPanelSouth = new JPanel();
    JButton[] daysButtons = new JButton[30];
    GridLayout gridLayoutMainPanel = new GridLayout(5, 7, 10, 10);

    public void startWindow() {
        setMainWindowSizeAndLayout();
        setPanels();
        setDaysButtons();
        addComponentsToPanels();
        addPanelsToFrame();
        finishSetUpFrame();
    }

    private void addComponentsToPanels() {
        for (int i = 0; i < 5; i++) {
            calendarMonthStatsViewPanelMain.add(new JLabel(""));
        }
        for (int i = 0; i < daysButtons.length; i++) {
            calendarMonthStatsViewPanelMain.add(daysButtons[i]);
        }
    }

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
        calendarMonthStatsViewPanelMain.setLayout(gridLayoutMainPanel);

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
            daysButtons[i] = new JButton(String.valueOf(i+1));
            daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
        }
    }

}
