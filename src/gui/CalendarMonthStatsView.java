package gui;

import configuration.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;


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
    JButton[] daysButtons;
    //</editor-fold>

    //<editor-fold desc="GridLayouts">
    GridLayout mainPanelGridLayout = new GridLayout(5, 7, 10, 10);
    GridLayout northPanelGridLayout = new GridLayout(2, 3, 5, 5);
    GridLayout eastPanelGridLayout = new GridLayout(3, 1, 5, 5);
    //</editor-fold>

    //<editor-fold desc="Labels">

    //<editor-fold desc="Label - North Panel">
    JLabel currentDayDateNorthPanelLabel = new JLabel("Current date: ????-??-??");
    JLabel currentDayMacroTitleNorthPanelLabel = new JLabel("Macro: ");
    JLabel currentDayMacroValuesNorthPanelLabel = new JLabel("Kcal: ????,?? \nProtein: ????,??g \nFat: ????,??g Carbs: ????,??g");
    //</editor-fold>
    JLabel selectedDayStatsTitleEastPanelLabel = new JLabel("Selected day stats:");

    JLabel macroOfSelectedDayEastPanelLabel = new JLabel("Kcal: ????,?? Protein: ????,??g Fat: ????,??g Carbs: ????,??g");

    JLabel macroGoalsEastPanelLabel = new JLabel("Kcal: ????,?? Protein: ????,??g Fat: ????,??g Carbs: ????,??g");
    //</editor-fold>

    JComboBox monthSelectComboBox = new JComboBox<>(new String[]{"April", "May", "June", "July"});

    //</editor-fold>

    public void startWindow() {
        setMainWindowSizeAndLayout();
        setPanels();
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
        calendarMonthStatsViewPanelMain.removeAll();
        addButtonsToMainPanel("July");
    }
    private void addButtonsToMainPanel(String month) {
        setDaysButtons(month);

        calendarMonthStatsViewPanelMain.removeAll();

        for (int i = 0; i < daysButtons.length; i++) {
            if (daysButtons[i].getText().equals("null")) {
                calendarMonthStatsViewPanelMain.add(new JLabel(""));
            } else {
                calendarMonthStatsViewPanelMain.add(daysButtons[i]);
            }
        }
    }

    private void prepareAndAddContentToNorthPanel() {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        currentDayDateNorthPanelLabel = new JLabel("Current date: " + date);

        //<editor-fold desc="Set up - North Panel">
        calendarMonthStatsViewPanelNorth.setLayout(northPanelGridLayout);

        calendarMonthStatsViewPanelNorth.add(monthSelectComboBox, 1, 0);
        monthSelectComboBox.setSelectedItem("July");

        calendarMonthStatsViewPanelNorth.add(new JLabel("test5"), 1, 1);

        calendarMonthStatsViewPanelNorth.add(currentDayMacroValuesNorthPanelLabel, 1, 2);

        calendarMonthStatsViewPanelNorth.add(currentDayDateNorthPanelLabel, 0, 0);

        setSelectedDateLabelNorthPanel(null, null);

        calendarMonthStatsViewPanelNorth.add(currentDayMacroTitleNorthPanelLabel, 0, 2);
        //</editor-fold>

        monthSelectComboBox.addItemListener(new MonthSelectComboBoxItemListener());

        //<editor-fold desc="Color and size of font in labels">
        currentDayDateNorthPanelLabel.setForeground(Config.northPanelStaticLabelsColor);
        currentDayDateNorthPanelLabel.setFont(calendarMonthStatsViewPanelNorth.getFont().deriveFont(20.0f));
        currentDayMacroTitleNorthPanelLabel.setForeground(Config.northPanelStaticLabelsColor);
        currentDayMacroValuesNorthPanelLabel.setForeground(Config.northPanelStaticLabelsColor);

        currentDayDateNorthPanelLabel.setOpaque(true);
        currentDayMacroTitleNorthPanelLabel.setOpaque(true);
        currentDayMacroValuesNorthPanelLabel.setOpaque(true);

        currentDayDateNorthPanelLabel.setBackground(Config.currentDayDateNorthPanelLabelColor);
        currentDayMacroTitleNorthPanelLabel.setBackground(Color.BLUE);
        currentDayMacroValuesNorthPanelLabel.setBackground(Color.GREEN);
        //</editor-fold>

    }

    private void setSelectedDateLabelNorthPanel(String month, String day){
        calendarMonthStatsViewPanelNorth.add(new JLabel("Selected date: " + monthSelectComboBox.getSelectedItem()), 0, 1);
    }

    private void prepareAndAddContentToSouthPanel() {
    }

    private void prepareAndAddContentToEastPanel() {
        //<editor-fold desc="Set up - East Panel">
        calendarMonthStatsViewPanelEast.setLayout(eastPanelGridLayout);
        calendarMonthStatsViewPanelEast.add(selectedDayStatsTitleEastPanelLabel, 0, 0);
        calendarMonthStatsViewPanelEast.add(emptyMacroPanel(), 0, 1);
        //macroOfSelectedDayEastPanelLabel.setFont(Font.createFont());

        calendarMonthStatsViewPanelEast.add(emptyMacroPanel(), 0, 2);
        //</editor-fold>

    }

    private void prepareAndAddContentToWestPanel() {

    }


    //</editor-fold>

    private JPanel emptyMacroPanel() {
        GridLayout gridLayout = new GridLayout(5, 1, 5, 5);

        JPanel macroPanel = new JPanel();
        macroPanel.setLayout(gridLayout);

        JLabel titleLabel = new JLabel("Macro");

        JLabel kcalLabel = new JLabel("Kcal:");

        JLabel proteinLabel = new JLabel("Protein:");

        JLabel fatLabel = new JLabel("Fat:");

        JLabel carbsLabel = new JLabel("Carbs:");

        macroPanel.add(titleLabel);

        macroPanel.add(kcalLabel);

        macroPanel.add(proteinLabel);

        macroPanel.add(fatLabel);
        macroPanel.add(carbsLabel);

        return macroPanel;
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

    private void setDaysButtons(String month) {
        daysButtons = new JButton[35];
        int counter = 1;

        if (month == null) {
            for (int i = 0; i < daysButtons.length; i++) {
                daysButtons[i] = new JButton("");
                daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
            }
        } else {

            if (month.equals("July")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i == 31) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 32) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 33) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 34) {
                        daysButtons[i] = new JButton("null");

                    } else {
                        daysButtons[i] = new JButton(String.valueOf(i + 1));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                    }
                }

            }

            if (month.equals("June")) {
                for (int i = 0; i < daysButtons.length; i++) {

                    if (i == 0) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 1) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 2) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 3) {
                        daysButtons[i] = new JButton("null");

                    } else if (i == 4) {
                        daysButtons[i] = new JButton("null");

                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        counter++;
                    }
                }
            }


            if (month.equals("May")) {
                for (int i = 0; i < daysButtons.length; i++) {

                    if (i == 0) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 1) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 33) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 34) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        counter++;
                    }
                }
            }


            if (month.equals("April")) {
                for (int i = 0; i < daysButtons.length; i++) {

                     if (i == 30) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 31) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 32) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 33) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 34) {
                        daysButtons[i] = new JButton("null");

                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        counter++;
                    }
                }
            }
        }


    }

    private class MonthSelectComboBoxItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                Object item = event.getItem();
                String getSelectedItem = monthSelectComboBox.getSelectedItem().toString();
                addButtonsToMainPanel(getSelectedItem);
                // do something with object
            }
        }
    }

    private class DaysButtonsActionListener implements ActionListener {
        JButton button;
        public DaysButtonsActionListener(JButton button){
            this.button = button;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String fullDate = "2024-";
            String month = monthSelectComboBox.getSelectedItem().toString();
            if (month == "July"){
                fullDate += "07-";
            }

            if (button.getText().length() == 1){
                fullDate = fullDate +"0" + button.getText();
            }

            if (button.getText().length() == 2){
                fullDate = fullDate + button.getText();
            }
            System.out.println(fullDate);
        }
    }
    //</editor-fold>

}
