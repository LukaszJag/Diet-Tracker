package gui;

import configuration.Config;
import tools.products_tools.Macro;
import tools.sql_tools.days_statistics.SelectFromDaysStatistics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
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

    //<editor-fold desc="Constructor">
    public CalendarMonthStatsView() {
        startWindow();
    }
    //</editor-fold>

    //<editor-fold desc="Start window: methods">
    public void startWindow() {
        setMainWindowSizeAndLayout();
        setPanels();
        addComponentsToPanels();
        addPanelsToFrame();
        finishSetUpFrame();
        paintButtons();
    }

    private void setMainWindowSizeAndLayout() {
        // Set window size
        mainWindow.setSize(Config.CALENDAR_MONTH_STATS_VIEW_WINDOWS_WIDTH, Config.CALENDAR_MONTH_STATS_VIEW_WINDOWS_HEIGHT);
        mainWindow.setLayout(new BorderLayout());
    }

    private void setPanels() {
        //Set Layout
        calendarMonthStatsViewPanelMain.setBorder(BorderFactory.createEmptyBorder(7, 5, 5, 5));
        calendarMonthStatsViewPanelMain.setLayout(mainPanelGridLayout);

        // Set panels colors
        calendarMonthStatsViewPanelNorth.setBackground(Color.BLACK);
        calendarMonthStatsViewPanelSouth.setBackground(Color.GRAY);
        calendarMonthStatsViewPanelEast.setBackground(Color.WHITE);
        calendarMonthStatsViewPanelWest.setBackground(Color.DARK_GRAY);
        calendarMonthStatsViewPanelMain.setBackground(Color.BLUE);

        // Set preferred size of panel
        calendarMonthStatsViewPanelNorth.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_NORTH_SIZE, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_NORTH_SIZE));
        calendarMonthStatsViewPanelEast.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE));
        calendarMonthStatsViewPanelMain.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_CENTER, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_CENTER));
        calendarMonthStatsViewPanelWest.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE));
        calendarMonthStatsViewPanelSouth.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_SOUTH_SIZE, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_SOUTH_SIZE));
    }


    private void addComponentsToPanels() {

        prepareAndAddContentToMainPanel();
        prepareAndAddContentToNorthPanel();
        prepareAndAddContentToWestPanel();
        prepareAndAddContentToEastPanel();
        prepareAndAddContentToSouthPanel();

    }

    private void addPanelsToFrame() {
        // Add Panels to Frame
        mainWindow.add(calendarMonthStatsViewPanelNorth, BorderLayout.NORTH);
        mainWindow.add(calendarMonthStatsViewPanelWest, BorderLayout.WEST);
        mainWindow.add(calendarMonthStatsViewPanelMain, BorderLayout.CENTER);
        mainWindow.add(calendarMonthStatsViewPanelEast, BorderLayout.EAST);
        mainWindow.add(calendarMonthStatsViewPanelSouth, BorderLayout.SOUTH);

    }

    private void finishSetUpFrame() {
        mainWindow.setResizable(false);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }
    //</editor-fold>

    //<editor-fold desc="Prepare and add content to: Panels">

    private void prepareAndAddContentToMainPanel() {
        setDaysButtonsMainPanel("June");
    }

    private void prepareAndAddContentToNorthPanel() {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        currentDayDateNorthPanelLabel = new JLabel("Current date: " + date);

        calendarMonthStatsViewPanelNorth.setLayout(northPanelGridLayout);

        calendarMonthStatsViewPanelNorth.add(monthSelectComboBox, 1, 0);
        monthSelectComboBox.setSelectedItem("June");

        calendarMonthStatsViewPanelNorth.add(new JLabel("test5"), 1, 1);

        calendarMonthStatsViewPanelNorth.add(currentDayMacroValuesNorthPanelLabel, 1, 2);

        calendarMonthStatsViewPanelNorth.add(currentDayDateNorthPanelLabel, 0, 0);

        calendarMonthStatsViewPanelNorth.add(new JLabel("Selected date: " + monthSelectComboBox.getSelectedItem()), 0, 1);

        calendarMonthStatsViewPanelNorth.add(currentDayMacroTitleNorthPanelLabel, 0, 2);

        monthSelectComboBox.addItemListener(new ComboBoxItemListener());

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

    private void prepareAndAddContentToSouthPanel() {
    }

    private void prepareAndAddContentToEastPanel() {
        calendarMonthStatsViewPanelEast.setLayout(eastPanelGridLayout);
        calendarMonthStatsViewPanelEast.add(selectedDayStatsTitleEastPanelLabel, 0, 0);
        calendarMonthStatsViewPanelEast.add(getSetMacroPanelComponent("Macro - Selected day:", 42, 42, 42, 42), 0, 1);

        calendarMonthStatsViewPanelEast.add(getSetMacroPanelComponent("Macro - goal:", 4297, 140, 120, 671), 0, 2);

    }

    private void prepareAndAddContentToWestPanel() {

    }

    //</editor-fold>

    private JPanel getSetMacroPanelComponent(String panelTitleLabelText, float kcal, float protein, float fat, float carbs) {
        GridLayout gridLayout = new GridLayout(5, 1, 5, 5);

        JPanel macroPanel = new JPanel();
        macroPanel.setLayout(gridLayout);

        JLabel titleLabel = new JLabel(panelTitleLabelText);

        JLabel kcalLabel = new JLabel("Kcal: " + kcal);

        JLabel proteinLabel = new JLabel("Protein: " + protein);

        JLabel fatLabel = new JLabel("Fat: " + fat);

        JLabel carbsLabel = new JLabel("Carbs: " + carbs);

        macroPanel.add(titleLabel);

        macroPanel.add(kcalLabel);

        macroPanel.add(proteinLabel);

        macroPanel.add(fatLabel);
        macroPanel.add(carbsLabel);

        return macroPanel;
    }

    private void refreshMacroAndAllComponentForSelectedDayMacro(String panelTitleLabelText, Macro macro) {
        calendarMonthStatsViewPanelEast.removeAll();


        calendarMonthStatsViewPanelEast.setBackground(Color.WHITE);
        calendarMonthStatsViewPanelEast.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE));

        calendarMonthStatsViewPanelEast.setLayout(eastPanelGridLayout);
        calendarMonthStatsViewPanelEast.add(selectedDayStatsTitleEastPanelLabel, 0, 0);

        calendarMonthStatsViewPanelEast.add(getSetMacroPanelComponent(panelTitleLabelText,
                macro.getKcal(), macro.getProtein(), macro.getFat(), macro.getCarbs()), 0, 1);

        calendarMonthStatsViewPanelEast.add(getSetMacroPanelComponent("Macro - goal:", 4297, 140, 120, 671), 0, 2);


        mainWindow.add(calendarMonthStatsViewPanelEast, BorderLayout.EAST);
        mainWindow.validate();
        mainWindow.repaint();
    }

    private void setDaysButtonsMainPanel(String month) {
        calendarMonthStatsViewPanelMain.removeAll();
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
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
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
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
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
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
        }

        for (int i = 0; i < daysButtons.length; i++) {
            if (daysButtons[i].getText().equals("null")) {
                calendarMonthStatsViewPanelMain.add(new JLabel(""));
            } else {
                calendarMonthStatsViewPanelMain.add(daysButtons[i]);
            }
        }
        mainWindow.validate();
        mainWindow.repaint();
    }

    private void paintButtons(){
        String fullDate = "2024-";
        String month = monthSelectComboBox.getSelectedItem().toString();

        if (month == "July") {
            fullDate += "07-";
        }
        if (month == "June") {
            fullDate += "06-";
        }
        if (month == "May") {
            fullDate += "05-";
        }
        if (month == "April") {
            fullDate += "04-";
        }

        String fullDateCache = fullDate;

        Color noDataColor = new Color(169, 78, 188);
        Color passDataColor = new Color(73, 176, 76);
        Color braekLimitDataColor = new Color(176, 73, 73);

        for (int i = 0; i < daysButtons.length; i++) {
            if (!daysButtons[i].getText().equals("null")) {
                if (daysButtons[i].getText().length() == 1) {
                    fullDate = fullDate + "0" + daysButtons[i].getText();
                }

                if (daysButtons[i].getText().length() == 2) {
                    fullDate = fullDate + daysButtons[i].getText();
                }
                System.out.println(daysButtons[i].getText());
                System.out.println(fullDate);

                if (dayMacroGoalStatus(fullDate) == 0) {
                    daysButtons[i].setBackground(noDataColor);
                }

                if (dayMacroGoalStatus(fullDate) == 1) {
                    daysButtons[i].setBackground(passDataColor);
                }

                if (dayMacroGoalStatus(fullDate) == 2) {
                    daysButtons[i].setBackground(braekLimitDataColor);
                }
            }

            fullDate = fullDateCache;
        }
    }

    private int dayMacroGoalStatus(String fullDateSQLFriendly){
        // int = 0 no data
        // int = 1 pass goal
        // int = 2 break goal
        int dayStatus = -1;

        // hard code - macro goal - may cause problem - to refactor
        float goalKcal = 4297;

        Macro dayMacro = SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(fullDateSQLFriendly);

        if (dayMacro.getKcal() == -2) {
            dayStatus = 0;
        } else if (dayMacro.getKcal() < goalKcal) {
            dayStatus = 1;
        } else if (dayMacro.getKcal() > goalKcal) {
            dayStatus = 2;
        }

        Macro.printAllValues(dayMacro);

        return dayStatus;
    }

    //<editor-fold desc="Actions Listeners, Item Listeners">
    private class DaysButtonsActionListener implements ActionListener {
        JButton button;

        public DaysButtonsActionListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String fullDate = "2024-";


            String month = monthSelectComboBox.getSelectedItem().toString();

            if (month == "July") {
                fullDate += "07-";
            }
            if (month == "June") {
                fullDate += "06-";
            }
            if (month == "May") {
                fullDate += "05-";
            }
            if (month == "April") {
                fullDate += "04-";
            }


            if (button.getText().length() == 1) {
                fullDate = fullDate + "0" + button.getText();
            }

            if (button.getText().length() == 2) {
                fullDate = fullDate + button.getText();
            }


            Macro macroToPrintInGUI;

            macroToPrintInGUI = SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(fullDate);

            String panelTitleLabelText = "Macro - Selected day: " + fullDate;
            refreshMacroAndAllComponentForSelectedDayMacro(panelTitleLabelText, macroToPrintInGUI);

        }
    }

    private class ComboBoxItemListener implements java.awt.event.ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String itemString = event.getItem().toString();
                System.out.println(itemString);
                setDaysButtonsMainPanel(itemString);
                paintButtons();
            }
        }
    }


    //</editor-fold>
}
