package gui;

import configuration.Config;
import tools.products_tools.Macro;
import tools.sql_tools.calendar.SelectFromCalendar;
import tools.sql_tools.days_statistics.SelectFromDaysStatistics;
import tools.time_date_tools.DateTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CalendarMonthStatsView {
    //<editor-fold desc="Main - Calendar Month Stats View - components and variables">

    String currentDate = "????-??-??";
    //<editor-fold desc="Global Counters">
    int goodDaysCounter;

    int badDaysCounter;

    int noDataDaysCounter;

    int comingDaysCounter;
    //</editor-fold>

    //<editor-fold desc="Global Colors">

    Color selectedMonthStatsGoodDaysDaysPanelColor = new Color(0, 255, 94);
    Color selectedMonthStatsBadDaysDaysPanelColor = new Color(230, 47, 194);
    Color selectedMonthStatsNoDataDaysDaysPanelColor = new Color(100, 100, 100);


    Color currentDayMacroTitleNorthPanelLabelColor = new Color(15, 50, 90);
    Color currentDayMacroValuesNorthPanelLabelColor = new Color(39, 192, 50);

    Color noDataColorLabelAndButton = new Color(169, 78, 188);
    Color goodDayDataColorLabelAndButton = new Color(73, 176, 76);
    Color badDayDataColorLabelAndButton = new Color(176, 73, 73);
    Color comingDaysDaysColorLabelAndButton = new Color(65, 119, 201);
    Color spainHolidaysDaysColorLabelAndButton = new Color(255, 196, 0);

    Color northPanelStaticLabelsColor = new Color(58, 123, 125);
    Color currentDayDateNorthPanelLabelColor = new Color(0, 255, 171);
    Color selectedDateAverageMacroForMonthLabelColor = new Color(238, 154, 28);
    //Color currentDayMacroTitleNorthPanelLabelColor = new Color(20,60,105);

    //</editor-fold>


    //<editor-fold desc="Frames">
    JFrame mainWindow = new JFrame("Calendar month stats view");
    //</editor-fold>

    //<editor-fold desc="Panels">
    JPanel calendarMonthStatsViewPanelMain = new JPanel();
    JPanel calendarMonthStatsViewPanelNorth = new JPanel();
    JPanel calendarMonthStatsViewPanelWest = new JPanel();
    JPanel calendarMonthStatsViewPanelEast = new JPanel();
    JPanel calendarMonthStatsViewPanelSouth = new JPanel();

    JPanel selectedDaysCounterGoodDaysPanel;
    JPanel selectedDaysCounterBadDaysPanel;
    JPanel selectedDaysCounterNoDataDaysPanel;
    JPanel selectedDaysCounterComingDaysPanel;

    JPanel selectedMonthStatsNorthsPanel;

    //</editor-fold>

    //<editor-fold desc="Buttons">
    JButton[] daysButtons;
    JButton[] listOfTheProductButtons = new JButton[24];
    //</editor-fold>

    //<editor-fold desc="GridLayouts">
    GridLayout mainPanelGridLayout = new GridLayout(5, 7, 10, 10);
    GridLayout northPanelGridLayout = new GridLayout(2, 3, 5, 5);
    GridLayout northSelectedMonthStatsPanelGridLayout = new GridLayout(2, 2, 5, 5);
    GridLayout eastPanelGridLayout = new GridLayout(3, 1, 5, 5);
    GridLayout westPanelGridLayout = new GridLayout(6, 1, 5, 5);
    //</editor-fold>

    //<editor-fold desc="Labels">

    JLabel currentDayDateNorthPanelLabel = new JLabel("Current date: ????-??-??");
    JLabel currentDayMacroTitleNorthPanelLabel = new JLabel("Macro: ");
    JLabel currentDayMacroValuesNorthPanelLabel = new JLabel("Kcal: ????,?? \nProtein: ????,??g \nFat: ????,??g Carbs: ????,??g");

    JLabel selectedDayStatsTitleEastPanelLabel = new JLabel("Selected day stats:");


    JLabel selectedMonthStatsGoodDaysDaysLabel = new JLabel("Good days:");
    JLabel selectedMonthStatsBadDaysDaysLabel = new JLabel("Bad days:");
    JLabel selectedMonthStatsNoDataDaysLabel = new JLabel("No data:");
    JLabel selectedMonthStatsComingDaysDaysLabel = new JLabel("Coming days:");


    JLabel selectedDateAverageMacroForMonthLabel = new JLabel("Selected date average macro for month: ");
    //</editor-fold>

    JComboBox monthSelectComboBox = new JComboBox<>(new String[]{"April", "May", "June", "July", "August"});
    JComboBox selectedDayProductsListComboBox = new JComboBox<String>();

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
        refreshMacroAndAllComponentForNorthPanel();
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

    //<editor-fold desc="Prepare Add Content - to Panels">
    private void prepareAndAddContentToMainPanel() {
        setDaysButtonsMainPanel("August");
    }

    private void prepareAndAddContentToNorthPanel() {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        currentDayDateNorthPanelLabel = new JLabel("Current date: " + date);

        calendarMonthStatsViewPanelNorth.setLayout(northPanelGridLayout);


        monthSelectComboBox.setSelectedItem("August");

        selectedDaysCounterGoodDaysPanel = new JPanel();
        selectedDaysCounterBadDaysPanel = new JPanel();
        selectedDaysCounterNoDataDaysPanel = new JPanel();
        selectedDaysCounterComingDaysPanel = new JPanel();

        selectedDaysCounterGoodDaysPanel.setBackground(goodDayDataColorLabelAndButton);
        selectedDaysCounterBadDaysPanel.setBackground(badDayDataColorLabelAndButton);
        selectedDaysCounterNoDataDaysPanel.setBackground(noDataColorLabelAndButton);
        selectedDaysCounterComingDaysPanel.setBackground(comingDaysDaysColorLabelAndButton);

        prepareSelectedCounterDaysPanels();

        selectedDaysCounterGoodDaysPanel.add(selectedMonthStatsGoodDaysDaysLabel);
        selectedDaysCounterBadDaysPanel.add(selectedMonthStatsBadDaysDaysLabel);
        selectedDaysCounterNoDataDaysPanel.add(selectedMonthStatsNoDataDaysLabel);
        selectedDaysCounterComingDaysPanel.add(selectedMonthStatsComingDaysDaysLabel);


        selectedMonthStatsNorthsPanel = new JPanel();
        selectedMonthStatsNorthsPanel.setLayout(northSelectedMonthStatsPanelGridLayout);

        selectedMonthStatsNorthsPanel.add(selectedDaysCounterGoodDaysPanel);
        selectedMonthStatsNorthsPanel.add(selectedDaysCounterBadDaysPanel);
        selectedMonthStatsNorthsPanel.add(selectedDaysCounterNoDataDaysPanel);
        selectedMonthStatsNorthsPanel.add(selectedDaysCounterComingDaysPanel);


        calendarMonthStatsViewPanelNorth.add(monthSelectComboBox, 1, 0);
        calendarMonthStatsViewPanelNorth.add(selectedMonthStatsNorthsPanel, 1, 1);
        calendarMonthStatsViewPanelNorth.add(currentDayMacroValuesNorthPanelLabel, 1, 2);
        calendarMonthStatsViewPanelNorth.add(currentDayDateNorthPanelLabel, 0, 0);
        calendarMonthStatsViewPanelNorth.add(selectedDateAverageMacroForMonthLabel, 0, 1);
        calendarMonthStatsViewPanelNorth.add(currentDayMacroTitleNorthPanelLabel, 0, 2);

        monthSelectComboBox.addItemListener(new MonthComboBoxItemListener());

        //<editor-fold desc="Color and size of font in labels">
        currentDayDateNorthPanelLabel.setForeground(northPanelStaticLabelsColor);
        currentDayDateNorthPanelLabel.setFont(calendarMonthStatsViewPanelNorth.getFont().deriveFont(20.0f));
        currentDayMacroTitleNorthPanelLabel.setForeground(northPanelStaticLabelsColor);
        currentDayMacroValuesNorthPanelLabel.setForeground(northPanelStaticLabelsColor);

        currentDayDateNorthPanelLabel.setOpaque(true);
        currentDayMacroTitleNorthPanelLabel.setOpaque(true);
        currentDayMacroValuesNorthPanelLabel.setOpaque(true);
        selectedDateAverageMacroForMonthLabel.setOpaque(true);


        selectedDateAverageMacroForMonthLabel.setBackground(selectedDateAverageMacroForMonthLabelColor);
        currentDayDateNorthPanelLabel.setBackground(currentDayDateNorthPanelLabelColor);
        currentDayMacroTitleNorthPanelLabel.setBackground(currentDayMacroTitleNorthPanelLabelColor);
        currentDayMacroValuesNorthPanelLabel.setBackground(currentDayMacroValuesNorthPanelLabelColor);
        //</editor-fold>

    }

    private void prepareAndAddContentToSouthPanel() {
    }

    private void prepareAndAddContentToEastPanel() {
        calendarMonthStatsViewPanelEast.setLayout(eastPanelGridLayout);
        calendarMonthStatsViewPanelEast.add(getSetMacroPanelComponent("Macro - Selected day:", 42, 42, 42, 42), 0, 0);

        calendarMonthStatsViewPanelEast.add(getSetMacroPanelComponent("Macro - goal:", 4297, 140, 120, 671), 0, 1);

        JPanel macroPanelTMP = new JPanel();
        macroPanelTMP.setLayout(new GridLayout(1, 2, 1, 1));
        macroPanelTMP.add(new JButton("Difference"), 0, 0);
        macroPanelTMP.add(new JButton("Empty"), 0, 1);
        calendarMonthStatsViewPanelEast.add(macroPanelTMP, 0, 2);

    }

    private void prepareAndAddContentToWestPanel() {
        calendarMonthStatsViewPanelWest.setLayout(westPanelGridLayout);

        selectedDayProductsListComboBox.addItemListener(new SelectedDayProductsListComboBoxItemListener());
        calendarMonthStatsViewPanelWest.add(selectedDayProductsListComboBox);

        JPanel macroPanelForWestPanel = getSetMiniMacroPanelComponent("Macro", -33, -33, -33, -33);

        calendarMonthStatsViewPanelWest.add(macroPanelForWestPanel);

    }

    //<editor-fold desc="Side help methods - for prepare Panels">

    private void refreshCountersForDays() {
        int goodDaysCounterTMP = 0;
        int badDaysCounterTMP = 0;
        int noDataDaysCounterTMP = 0;
        int comingDaysCounterTMP = 0;

        for (int i = 0; i < daysButtons.length; i++) {
            if (daysButtons[i].getBackground() == noDataColorLabelAndButton) {
                noDataDaysCounterTMP++;
            }
            if (daysButtons[i].getBackground() == goodDayDataColorLabelAndButton) {
                goodDaysCounterTMP++;
            }

            if (daysButtons[i].getBackground() == badDayDataColorLabelAndButton) {
                badDaysCounterTMP++;
            }

            if (daysButtons[i].getBackground() == comingDaysDaysColorLabelAndButton) {
                comingDaysCounterTMP++;
            }
        }

        this.goodDaysCounter = goodDaysCounterTMP;
        this.badDaysCounter = badDaysCounterTMP;
        this.noDataDaysCounter = noDataDaysCounterTMP;
        this.comingDaysCounter = comingDaysCounterTMP;

    }

    private void prepareSelectedCounterDaysPanels() {
        selectedMonthStatsGoodDaysDaysLabel = new JLabel("Good days: " + goodDaysCounter);
        selectedMonthStatsBadDaysDaysLabel = new JLabel("Bad days: " + badDaysCounter);
        selectedMonthStatsNoDataDaysLabel = new JLabel("No data: " + noDataDaysCounter);
        selectedMonthStatsComingDaysDaysLabel = new JLabel("Coming days: " + comingDaysCounter);
    }

    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="get set Macro panel">
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

    private JPanel getSetMiniMacroPanelComponent(String panelTitleLabelText, float kcal, float protein, float fat, float carbs) {
        GridLayout gridLayout = new GridLayout(3, 2, 5, 5);

        JPanel macroPanel = new JPanel();
        macroPanel.setLayout(gridLayout);

        JLabel titleLabel = new JLabel(panelTitleLabelText);
        JLabel emptyLabel = new JLabel("");

        JLabel kcalLabel = new JLabel("Kcal: " + kcal);

        JLabel proteinLabel = new JLabel("Protein: " + protein);

        JLabel fatLabel = new JLabel("Fat: " + fat);

        JLabel carbsLabel = new JLabel("Carbs: " + carbs);

        macroPanel.add(titleLabel);

        macroPanel.add(emptyLabel);

        macroPanel.add(kcalLabel);

        macroPanel.add(proteinLabel);

        macroPanel.add(fatLabel);
        macroPanel.add(carbsLabel);

        return macroPanel;
    }

    //</editor-fold>
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
            if (month.equals("August")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i == 0) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 1) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 2) {
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

    private void paintButtons() {

        goodDaysCounter = 0;
        badDaysCounter = 0;
        noDataDaysCounter = 0;
        comingDaysCounter = 0;

        String fullDate = "2024-";
        String month = monthSelectComboBox.getSelectedItem().toString();

        if (month == "August") {
            fullDate += "08-";
        }
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


        for (int i = 0; i < daysButtons.length; i++) {
            if (!daysButtons[i].getText().equals("null")) {
                if (daysButtons[i].getText().length() == 1) {
                    fullDate = fullDate + "0" + daysButtons[i].getText();
                }

                if (daysButtons[i].getText().length() == 2) {
                    fullDate = fullDate + daysButtons[i].getText();
                }


                // Set tag and color
                if (dayMacroGoalStatus(fullDate) == 0) {
                    daysButtons[i].setBackground(noDataColorLabelAndButton);
                    noDataDaysCounter++;
                }

                if (dayMacroGoalStatus(fullDate) == 1) {
                    daysButtons[i].setBackground(goodDayDataColorLabelAndButton);
                    goodDaysCounter++;
                }

                if (dayMacroGoalStatus(fullDate) == 2) {
                    daysButtons[i].setBackground(badDayDataColorLabelAndButton);
                    badDaysCounter++;
                }

                if (dayMacroGoalStatus(fullDate) == 3) {
                    daysButtons[i].setBackground(comingDaysDaysColorLabelAndButton);
                    comingDaysCounter++;
                }
                if (dayMacroGoalStatus(fullDate) == 42) {
                    daysButtons[i].setBackground(spainHolidaysDaysColorLabelAndButton);
                }
            }

            fullDate = fullDateCache;
        }
    }

    private Macro getAverageMacroForMonth(String monthString) {
        String friendlySQLFormatMonthDate = "";

        if (monthString.equals("April")){
            friendlySQLFormatMonthDate = "2024-" + "08-";
        }
        if (monthString.equals("May")){
            friendlySQLFormatMonthDate = "2024-" + "05-";
        }
        if (monthString.equals("June")){
            friendlySQLFormatMonthDate = "2024-" + "06-";
        }
        if (monthString.equals("July")){
            friendlySQLFormatMonthDate = "2024-" + "07-";
        }
        if (monthString.equals("August")){
            friendlySQLFormatMonthDate = "2024-" + "08-";
        }

        String fullDate = friendlySQLFormatMonthDate;
        Macro averageMacro = new Macro(0,0,0,0);
        Macro dayMacro;
        int dayCounter = 0;

        for (int i = 0; i < daysButtons.length; i++) {

            if (daysButtons[i].getText() != "null") {
                if (daysButtons[i].getText().length() == 1) {
                    fullDate = friendlySQLFormatMonthDate + "0" + daysButtons[i].getText();
                } else {
                    fullDate += daysButtons[i].getText();
                }

                if (dayMacroGoalStatus(fullDate) != 0 && dayMacroGoalStatus(fullDate) != 3 && dayMacroGoalStatus(fullDate) != 42) {
                    dayMacro = SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(fullDate);
                    averageMacro = Macro.sumOfTwoMacros(averageMacro, dayMacro);
                    dayCounter++;
                }

                fullDate = friendlySQLFormatMonthDate;
            }
        }

        averageMacro.setKcal(averageMacro.getKcal() / dayCounter);
        averageMacro.setProtein(averageMacro.getProtein() / dayCounter);
        averageMacro.setFat(averageMacro.getFat() / dayCounter);
        averageMacro.setCarbs(averageMacro.getCarbs() / dayCounter);

        Macro.printAllValues(averageMacro);
        return averageMacro;
    }

    private int dayMacroGoalStatus(String fullDateSQLFriendly) {
        // int = 0 no data
        // int = 1 pass goal
        // int = 2 break goal
        // int = 3 coming days
        // int = 42 Spain Holiday
        int dayStatus = -1;

        // hard code - macro goal - may cause problem - to refactor
        float goalKcal = 4297;

        // Set holidays buttons - June
        for (int i = 22; i <= 30; i++) {
            String holidaysDate = "2024-06-" + String.valueOf(i);
            if (fullDateSQLFriendly.equals(holidaysDate)) {
                dayStatus = 42;
                return dayStatus;
            }
        }
        // Set holidays buttons - July
        for (int i = 1; i <= 6; i++) {
            String holidaysDate = "2024-07-0" + String.valueOf(i);
            if (fullDateSQLFriendly.equals(holidaysDate)) {
                dayStatus = 42;
                return dayStatus;
            }
        }

        Macro dayMacro = SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(fullDateSQLFriendly);

        String currentDateString = DateTools.getCurrentDateSQLFriendlyFormat();

        Date curentDate;
        Date parameterDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            curentDate = dateFormat.parse(currentDateString);
            parameterDate = dateFormat.parse(fullDateSQLFriendly);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (parameterDate.compareTo(curentDate) > 0) {
            dayStatus = 3;
        } else if (dayMacro.getKcal() == -2) {
            dayStatus = 0;
        } else if (dayMacro.getKcal() < goalKcal) {
            dayStatus = 1;
        } else if (dayMacro.getKcal() > goalKcal) {
            dayStatus = 2;
        }


        return dayStatus;
    }

    //<editor-fold desc="Refresh methods">
    private void refreshMacroAndAllComponentForSelectedDayMacro(String panelTitleLabelText, Macro macro) {
        calendarMonthStatsViewPanelEast.removeAll();


        calendarMonthStatsViewPanelEast.setBackground(Color.WHITE);
        calendarMonthStatsViewPanelEast.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE));

        calendarMonthStatsViewPanelEast.setLayout(eastPanelGridLayout);


        calendarMonthStatsViewPanelEast.add(getSetMacroPanelComponent(panelTitleLabelText,
                macro.getKcal(), macro.getProtein(), macro.getFat(), macro.getCarbs()), 0, 0);

        calendarMonthStatsViewPanelEast.add(getSetMacroPanelComponent("Macro - goal:", 4297, 140, 120, 671), 0, 1);

        JPanel macroPanelTMP = new JPanel();
        macroPanelTMP.setLayout(new GridLayout(1, 2, 1, 1));
        macroPanelTMP.add(new JButton("Difference"), 0, 0);
        macroPanelTMP.add(new JButton("Empty"), 0, 1);
        calendarMonthStatsViewPanelEast.add(macroPanelTMP, 0, 2);


        mainWindow.add(calendarMonthStatsViewPanelEast, BorderLayout.EAST);
        mainWindow.validate();
        mainWindow.repaint();
    }

    private void refreshWestPanel(Macro macro, String amountOfProduct) {
        calendarMonthStatsViewPanelWest.removeAll();

        calendarMonthStatsViewPanelWest.setLayout(westPanelGridLayout);


        selectedDayProductsListComboBox.addItemListener(new SelectedDayProductsListComboBoxItemListener());
        calendarMonthStatsViewPanelWest.add(selectedDayProductsListComboBox);


        JPanel macroPanelForWestPanel = getSetMiniMacroPanelComponent("Macro " + currentDate, macro.getKcal(), macro.getProtein(), macro.getFat(), macro.getCarbs());

        calendarMonthStatsViewPanelWest.add(macroPanelForWestPanel);

        calendarMonthStatsViewPanelWest.validate();
        calendarMonthStatsViewPanelWest.repaint();

        mainWindow.add(calendarMonthStatsViewPanelWest, BorderLayout.WEST);
        mainWindow.validate();
        mainWindow.repaint();

    }
    private void refreshAverageMacroPanelForNorthPanel(Macro averageMacro){
        calendarMonthStatsViewPanelNorth.removeAll();

        currentDayMacroValuesNorthPanelLabel = new JLabel(Macro.getShortMacroInformation(averageMacro));

        calendarMonthStatsViewPanelNorth.add(monthSelectComboBox, 1, 0);
        calendarMonthStatsViewPanelNorth.add(selectedMonthStatsNorthsPanel, 1, 1);
        calendarMonthStatsViewPanelNorth.add(currentDayMacroValuesNorthPanelLabel, 1, 2);
        calendarMonthStatsViewPanelNorth.add(currentDayDateNorthPanelLabel, 0, 0);
        calendarMonthStatsViewPanelNorth.add(new JLabel("Selected date: " + monthSelectComboBox.getSelectedItem()), 0, 1);
        calendarMonthStatsViewPanelNorth.add(currentDayMacroTitleNorthPanelLabel, 0, 2);

        currentDayMacroValuesNorthPanelLabel.validate();
        currentDayMacroValuesNorthPanelLabel.repaint();

        mainWindow.validate();
        mainWindow.repaint();
    }

    private void refreshMacroAndAllComponentForNorthPanel() {
        calendarMonthStatsViewPanelNorth.removeAll();

        selectedDaysCounterGoodDaysPanel.removeAll();
        selectedDaysCounterBadDaysPanel.removeAll();
        selectedDaysCounterNoDataDaysPanel.removeAll();
        selectedDaysCounterComingDaysPanel.removeAll();

        selectedMonthStatsNorthsPanel.removeAll();

        refreshCountersForDays();
        prepareSelectedCounterDaysPanels();


        selectedDaysCounterGoodDaysPanel.add(selectedMonthStatsGoodDaysDaysLabel);
        selectedDaysCounterBadDaysPanel.add(selectedMonthStatsBadDaysDaysLabel);
        selectedDaysCounterNoDataDaysPanel.add(selectedMonthStatsNoDataDaysLabel);
        selectedDaysCounterComingDaysPanel.add(selectedMonthStatsComingDaysDaysLabel);

        selectedMonthStatsNorthsPanel.add(selectedDaysCounterGoodDaysPanel);
        selectedMonthStatsNorthsPanel.add(selectedDaysCounterBadDaysPanel);
        selectedMonthStatsNorthsPanel.add(selectedDaysCounterNoDataDaysPanel);
        selectedMonthStatsNorthsPanel.add(selectedDaysCounterComingDaysPanel);


        calendarMonthStatsViewPanelNorth.add(monthSelectComboBox, 1, 0);
        calendarMonthStatsViewPanelNorth.add(selectedMonthStatsNorthsPanel, 1, 1);
        calendarMonthStatsViewPanelNorth.add(currentDayMacroValuesNorthPanelLabel, 1, 2);
        calendarMonthStatsViewPanelNorth.add(currentDayDateNorthPanelLabel, 0, 0);
        calendarMonthStatsViewPanelNorth.add(new JLabel("Selected date: " + monthSelectComboBox.getSelectedItem()), 0, 1);
        calendarMonthStatsViewPanelNorth.add(currentDayMacroTitleNorthPanelLabel, 0, 2);


        selectedDaysCounterGoodDaysPanel.validate();
        selectedDaysCounterBadDaysPanel.validate();
        selectedDaysCounterNoDataDaysPanel.validate();
        selectedDaysCounterComingDaysPanel.validate();


        calendarMonthStatsViewPanelNorth.validate();
        calendarMonthStatsViewPanelNorth.repaint();

        mainWindow.validate();
        mainWindow.repaint();
    }

    public void refreshComboBox(JComboBox newComboBox) {
        calendarMonthStatsViewPanelWest.removeAll();
        calendarMonthStatsViewPanelWest.setLayout(westPanelGridLayout);
        calendarMonthStatsViewPanelWest.add(selectedDayProductsListComboBox);
        selectedDayProductsListComboBox.addItemListener(new SelectedDayProductsListComboBoxItemListener());

        calendarMonthStatsViewPanelWest.add(newComboBox);

        JPanel macroPanelForWestPanel = getSetMiniMacroPanelComponent("Macro", -33, -33, -33, -33);

        calendarMonthStatsViewPanelWest.add(macroPanelForWestPanel);
        calendarMonthStatsViewPanelWest.validate();
        calendarMonthStatsViewPanelWest.repaint();

        mainWindow.validate();
        mainWindow.repaint();
    }

    //</editor-fold>
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

            if (month == "August") {
                fullDate += "08-";
            }
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


            String[][] productArrayForComboBox;
            try {
                productArrayForComboBox = SelectFromCalendar.selectAllDataFromCalendarTableForDay(fullDate);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            String[] listToComboBox = new String[productArrayForComboBox.length];

            for (int i = 0; i < listToComboBox.length; i++) {
                listToComboBox[i] = productArrayForComboBox[i][2];
            }

            JComboBox newComboBox = selectedDayProductsListComboBox = new JComboBox<>(listToComboBox);
            refreshComboBox(newComboBox);

            currentDate = fullDate;
        }
    }

    private class MonthComboBoxItemListener implements java.awt.event.ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String itemString = event.getItem().toString();
                setDaysButtonsMainPanel(itemString);
                paintButtons();
                refreshMacroAndAllComponentForNorthPanel();
                refreshAverageMacroPanelForNorthPanel(getAverageMacroForMonth(itemString));
            }
        }
    }

    private class SelectedDayProductsListComboBoxItemListener implements java.awt.event.ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {


                String productName;
                productName = selectedDayProductsListComboBox.getSelectedItem().toString();
                String[] rowData;

                try {
                    rowData = SelectFromCalendar.selectAllFromCalendarTableForDateAndProductName(productName, currentDate);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                float kcal = Float.valueOf(rowData[10]);
                float protein = Float.valueOf(rowData[11]);
                float fat = Float.valueOf(rowData[12]);
                float carbs = Float.valueOf(rowData[13]);


                Macro macro = new Macro(kcal, protein, fat, carbs);
                refreshWestPanel(macro, productName);
            }
        }
    }


    //</editor-fold>
}
