package gui.diet;

import configuration.Config;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import tools.calendar_tools.MyDate;
import tools.charts_tools.DisplayChart;
import tools.debug_tools.Debug;
import tools.products_tools.Macro;
import tools.sql_tools.calendar.SelectFromCalendar;
import tools.sql_tools.days_statistics.SelectFromDaysStatistics;
import tools.sql_tools.general.get.GetConnection;
import tools.time_date_tools.DateTools;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static tools.calendar_tools.MyDate.getNumberOfMonthInYear;


public class CalendarMonthStatsView {
    //<editor-fold desc="Main - Calendar Month Stats View - components and variables">

    //<editor-fold desc="Colors">
    Color greenKcalLimitColor = new Color(43, 191, 36);
    Color yellowKcalLimitColor = new Color(255, 229, 46);
    Color orangeKcalLimitColor = Color.ORANGE;
    Color redKcalLimitColor = new Color(214, 23, 23);

    Color noDataForButtonColor = new Color(37, 150, 190);


    Color greenMarkerColor = new Color(43, 191, 36);
    Color yellowMarkerColor = new Color(255, 229, 46);
    Color redMarkerColor = new Color(214, 23, 23);
    Color blackAverageMarkerColor = new Color(0, 0, 0);

    Color currentDayMacroTitleNorthPanelLabelColor = new Color(15, 50, 90);
    Color currentDayMacroValuesNorthPanelLabelColor = new Color(39, 192, 50);

    Color noDataColorLabelAndButton = new Color(169, 78, 188);
    Color goodDayDataColorLabelAndButton = new Color(73, 176, 76);
    Color badDayDataColorLabelAndButton = new Color(176, 73, 73);
    Color comingDaysDaysColorLabelAndButton = new Color(65, 119, 201);
    Color northPanelStaticLabelsColor = Color.BLACK;
    Color currentDayDateNorthPanelLabelColor = new Color(0, 255, 171);
    Color selectedDateAverageMacroForMonthLabelColor = new Color(238, 154, 28);
    //</editor-fold>
    //<editor-fold desc="Kcal Limits - float">
    float greenKcalLimit = Config.BMRActual.getKcal();
    float yellowKcalLimit = 4500;
    float redKcalLimit = 5500;
    //</editor-fold>
    //<editor-fold desc="Global Counters">
    int goodDaysCounter;

    int badDaysCounter;

    int noDataDaysCounter;

    int comingDaysCounter;
    //</editor-fold>
    //<editor-fold desc="Swing components">
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

    JButton differenceButton = new JButton("Difference");
    JButton emptyButton = new JButton("Empty");
    JButton showChartButton = new JButton("Show chart for selected month");
    JButton showBarChartButton = new JButton("Show bar chart for selected month");

    JButton previousMonthButton = new JButton("Previous");
    JButton currentMonthButton = new JButton("Current");
    JButton nextMonthButton = new JButton("Next");

    JButton middlePanelWestPlusDayForDaysStatisticButton = new JButton("+");
    JButton middlePanelWestMinusDayForDaysStatisticButton = new JButton("-");
    JButton setNextDayButton = new JButton("+");
    JButton setPreviousButton = new JButton("-");
    JButton checkCalendarTableButton = new JButton("Check calendar table");
    JButton checkDaysStatisticFilledTableButton = new JButton("Check days statistic");

    //</editor-fold>

    //<editor-fold desc="GridLayouts">
    GridLayout mainPanelGridLayout = new GridLayout(5, 7, 10, 10);
    GridLayout northPanelGridLayout = new GridLayout(2, 3, 5, 5);
    GridLayout northSelectedMonthStatsPanelGridLayout = new GridLayout(2, 2, 5, 5);
    GridLayout eastPanelGridLayout = new GridLayout(3, 1, 5, 5);
    GridLayout westPanelGridLayout = new GridLayout(4, 1, 5, 5);
    //</editor-fold>
    //<editor-fold desc="Labels">

    JLabel currentDayDateNorthPanelLabel = new JLabel("Current date: ????-??-??");
    JLabel currentDayMacroTitleNorthPanelLabel = new JLabel("Macro: ");
    JLabel currentDayMacroValuesNorthPanelLabel = new JLabel("Kcal: ????,?? \nProtein: ????,??g \nFat: ????,??g Carbs: ????,??g");

    JLabel selectedMonthStatsGoodDaysDaysLabel = new JLabel("Good days:");
    JLabel selectedMonthStatsBadDaysDaysLabel = new JLabel("Bad days:");
    JLabel selectedMonthStatsNoDataDaysLabel = new JLabel("No data:");
    JLabel selectedMonthStatsComingDaysDaysLabel = new JLabel("Coming days:");


    JLabel selectedDateAverageMacroForMonthLabel = new JLabel("Selected date average macro for month: ");
    //</editor-fold>
    //<editor-fold desc="Combo Boxes">
    JComboBox monthSelectComboBox = new JComboBox<>(new String[]{"April", "May", "June", "July", "August", "September", "October",
            "November", "December", "January2025", "February2025", "March2025", "April2025", "May2025", "June2025", "July2025",
            "August2025", "September2025", "October2025", "November2025", "December2025", "January2026", "February2026", "March2026", "April2026", "May2026"});
    JComboBox selectedDayProductsListComboBox = new JComboBox<String>();
    //</editor-fold>
    //<editor-fold desc="TextFields">
    JTextField checkCalendarTableDateTextField = new JTextField();
    JTextField checkDaysStatisticsTableDateTextField = new JTextField();

    //</editor-fold>
    //</editor-fold>

    ChartsClass chartsClass = new ChartsClass();
    JFreeChart jFreeChart;
    String currentDate = "????-??-??";
    String[] columnsNamesToDisplayOnQuickView = {"index", "day_date", "day_name", "product_name", "amount_of_product", "kcal_consume", "carbs_consume", "fat_consume", "protein_consume", "meal_name"};
    String[] columnsNamesFromDaysStatisticsToDisplayOnQuickView = {"index", "day_date", "amount_of_points_from_notepad", "amount_of_filled_points_from_notepad", "kcal_consume", "protein_consume", "fat_consume", "carbs_consume"};
    //</editor-fold>

    //<editor-fold desc="Constructor and start method">
    public CalendarMonthStatsView() {
    }

    public CalendarMonthStatsView(boolean onlySetupVariables) {
        if (onlySetupVariables) {
            setMainWindowSizeAndLayout();
            setPanels();
            addComponentsToPanels();
            addPanelsToFrame();
            paintButtons();
            refreshMacroAndAllComponentForNorthPanel();
        }
    }

    public void startWindow() {
        setMainWindowSizeAndLayout();
        setPanels();
        addComponentsToPanels();
        addPanelsToFrame();
        finishSetUpFrame();
        paintButtons();
        refreshMacroAndAllComponentForNorthPanel();
    }
    //</editor-fold>

    //<editor-fold desc="Frame and panels methods">
    //<editor-fold desc="Setup frame">
    private void setMainWindowSizeAndLayout() {
        // Set window size
        mainWindow.setSize(Config.CALENDAR_MONTH_STATS_VIEW_WINDOWS_WIDTH, Config.CALENDAR_MONTH_STATS_VIEW_WINDOWS_HEIGHT);
        mainWindow.setLayout(new BorderLayout());
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
        mainWindow.setResizable(true);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    //</editor-fold>

    //<editor-fold desc="Panels - methods">
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

        // TODO - make variables set in Config.java or in this class
        // Set preferred size of panel
        //    calendarMonthStatsViewPanelNorth.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_NORTH_SIZE, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_NORTH_SIZE));
//        calendarMonthStatsViewPanelEast.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE));
//        calendarMonthStatsViewPanelMain.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_CENTER, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_CENTER));
//        calendarMonthStatsViewPanelWest.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE));
        //      calendarMonthStatsViewPanelSouth.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_SOUTH_SIZE, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_SOUTH_SIZE));

        calendarMonthStatsViewPanelWest.setPreferredSize(new Dimension(350, 200));
        calendarMonthStatsViewPanelEast.setPreferredSize(new Dimension(350, 200));

        calendarMonthStatsViewPanelMain.setMaximumSize(new Dimension(200, 100));

        calendarMonthStatsViewPanelNorth.setPreferredSize(new Dimension(1200, 200));
        calendarMonthStatsViewPanelSouth.setPreferredSize(new Dimension(1200, 100));

    }

    //<editor-fold desc="addComponentsToPanels">
    private void addComponentsToPanels() {
        prepareAndAddContentToMainPanel();
        prepareAndAddContentToNorthPanel();
        prepareAndAddContentToWestPanel();
        prepareAndAddContentToEastPanel();
        prepareAndAddContentToSouthPanel();

    }

    private void prepareAndAddContentToMainPanel() {
        setDaysButtonsMainPanel("May2026");
    }

    private void prepareAndAddContentToNorthPanel() {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        currentDayDateNorthPanelLabel = new JLabel("Current date: " + date);

        calendarMonthStatsViewPanelNorth.setLayout(northPanelGridLayout);


        monthSelectComboBox.setSelectedItem("May2026");

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

        setupAverageMacroLabel();

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
        showChartButton.addActionListener(new ShowChartButtonActionListener());
        calendarMonthStatsViewPanelSouth.add(showChartButton);

        showBarChartButton.addActionListener(new ShowBarChartButtonActionListener());
        calendarMonthStatsViewPanelSouth.add(showBarChartButton);
    }

    private void prepareAndAddContentToEastPanel() {
        calendarMonthStatsViewPanelEast.setLayout(eastPanelGridLayout);
        calendarMonthStatsViewPanelEast.add(getSetMacroPanelComponent("Macro - Selected day:", 42, 42, 42, 42), 0, 0);

        calendarMonthStatsViewPanelEast.add(getSetMacroPanelComponent("Macro - goal:",
                Config.BMRActual.getKcal(), Config.BMRActual.getProtein(), Config.BMRActual.getFat(), Config.BMRActual.getCarbs()), 0, 1);

        JPanel macroPanelTMP = new JPanel();
        macroPanelTMP.setLayout(new GridLayout(1, 2, 1, 1));
        macroPanelTMP.add(differenceButton, 0, 0);
        macroPanelTMP.add(emptyButton, 0, 1);
        calendarMonthStatsViewPanelEast.add(macroPanelTMP, 0, 2);

    }

    private void prepareAndAddContentToWestPanel() {
        calendarMonthStatsViewPanelWest.setLayout(westPanelGridLayout);

        selectedDayProductsListComboBox.addItemListener(new SelectedDayProductsListComboBoxItemListener());
        calendarMonthStatsViewPanelWest.add(selectedDayProductsListComboBox);

        JPanel macroPanelForWestPanel = getSetMiniMacroPanelComponent("Macro", -33, -33, -33, -33);

        calendarMonthStatsViewPanelWest.add(macroPanelForWestPanel);

        JPanel checkSQLTablePanel = new JPanel();
        GridLayout checkSQLTablePanelGridLayout = new GridLayout(2, 3);
        checkSQLTablePanel.setLayout(checkSQLTablePanelGridLayout);

        checkCalendarTableButton.addActionListener(new CheckCalendarTableActionListener());
        checkSQLTablePanel.add(checkCalendarTableButton);

        checkCalendarTableDateTextField.setText(MyDate.getCurrentDayInSQLFormat());
        checkSQLTablePanel.add(checkCalendarTableDateTextField);

        setNextDayButton.addActionListener(new SetNextButtonActionListener());
        checkSQLTablePanel.add(setNextDayButton);


        checkDaysStatisticFilledTableButton.addActionListener(new CheckDaysStatisticFilledTableActionListener());
        checkSQLTablePanel.add(checkDaysStatisticFilledTableButton);

        checkDaysStatisticsTableDateTextField.setText(MyDate.getCurrentYearAndMonthInSQLFormat());
        checkSQLTablePanel.add(checkDaysStatisticsTableDateTextField);

        setPreviousButton.addActionListener(new SetPreviousButtonActionListener());
        checkSQLTablePanel.add(setPreviousButton);

        calendarMonthStatsViewPanelWest.add(checkSQLTablePanel);
    }
    //</editor-fold>

    //<editor-fold desc="Side help methods - for prepare Panels">
    private void prepareSelectedCounterDaysPanels() {
        selectedMonthStatsGoodDaysDaysLabel = new JLabel("Good days: " + goodDaysCounter);
        selectedMonthStatsBadDaysDaysLabel = new JLabel("Bad days: " + badDaysCounter);
        selectedMonthStatsNoDataDaysLabel = new JLabel("No data: " + noDataDaysCounter);
        selectedMonthStatsComingDaysDaysLabel = new JLabel("Coming days: " + comingDaysCounter);
    }
    //</editor-fold>
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Setup - panels components">

    //<editor-fold desc="Setup - North panel - components">
    private void setupAverageMacroLabel() {

        Macro averageMacroForMonth = SelectFromDaysStatistics.getAverageMacroForMonth(getYearFromComboBox(), getMonthFromComboBox());

        currentDayMacroValuesNorthPanelLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        currentDayMacroValuesNorthPanelLabel.setForeground(Color.BLACK);
        currentDayMacroValuesNorthPanelLabel.setText(Macro.getShortMacroInformationMinimalFormat(averageMacroForMonth).replace("-", ""));
    }
    //</editor-fold>

    //<editor-fold desc="Setup - Main panel - components">
    private void setDaysButtonsMainPanel() {
        //int amountOfDaysInMonth = monthSelectComboBox.getSelectedItem().toString();
        daysButtons = new JButton[42];
        mainPanelGridLayout = new GridLayout(6, 7, 10, 10);
    }

    private void setDaysButtonsMainPanel(String month) {
        setDaysButtonsMainPanel();

        calendarMonthStatsViewPanelMain.removeAll();

        if (month.equals("September")) {
            daysButtons = new JButton[42];
            mainPanelGridLayout = new GridLayout(6, 7, 10, 10);
        } else if (month.equals("December")) {
            daysButtons = new JButton[42];
            mainPanelGridLayout = new GridLayout(6, 7, 10, 10);
        } else if (month.equals("March2025")) {
            daysButtons = new JButton[42];
            mainPanelGridLayout = new GridLayout(6, 7, 10, 10);
        } else if (month.equals("June2025")) {
            daysButtons = new JButton[42];
            mainPanelGridLayout = new GridLayout(6, 7, 10, 10);
        } else if (month.equals("March2026")) {
            daysButtons = new JButton[42];
            mainPanelGridLayout = new GridLayout(6, 7, 10, 10);
        } else {
            daysButtons = new JButton[35];
            mainPanelGridLayout = new GridLayout(5, 7, 10, 10);
        }
        int counter = 1;

        if (month == null) {
            for (int i = 0; i < daysButtons.length; i++) {
                daysButtons[i] = new JButton("");
                daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
            }
        } else {
            if (month.equals("May2026")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i < 4) {
                        daysButtons[i] = new JButton("null");
                    } else if (i >= 35) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
            if (month.equals("April2026")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i < 2) {
                        daysButtons[i] = new JButton("null");
                    } else if (i >= 32) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
            if (month.equals("March2026")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i < 6) {
                        daysButtons[i] = new JButton("null");
                    } else if (i >= 37) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }

            if (month.equals("February2026")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i < 6) {
                        daysButtons[i] = new JButton("null");
                    } else if (i >= 34) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }

            if (month.equals("January2026")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i < 3) {
                        daysButtons[i] = new JButton("null");
                    } else if (i >= 34) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }

            if (month.equals("December2025")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i < 0) {
                        daysButtons[i] = new JButton("null");
                    } else if (i >= 31) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
            if (month.equals("November2025")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i >= 0 && i < 5) {
                        daysButtons[i] = new JButton("null");
                    } else if (i >= 35) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
            if (month.equals("October2025")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i >= 0 && i < 2) {
                        daysButtons[i] = new JButton("null");
                    } else if (i >= 33) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
            if (month.equals("September2025")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i >= 30) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
            if (month.equals("June2025")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i >= 0 && i < 6) {
                        daysButtons[i] = new JButton("null");
                    } else if (i >= 36 && i <= 41) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
            if (month.equals("May2025")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i >= 0 && i <= 2) {
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
            if (month.equals("August2025")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i <= 3) {
                        daysButtons[i] = new JButton("null");
                    } else if (i >= 35) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
            if (month.equals("July2025")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i == 0) {
                        daysButtons[i] = new JButton("null");
                    } else if (i >= 32) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
            if (month.equals("February2025")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i < 5) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 33 || i == 34) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
            if (month.equals("January2025")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i == 0 || i < 5) {
                        daysButtons[i] = new JButton("null");
                    } else if (i == 33 || i == 34) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }

            if (month.equals("March2025")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i >= 0 && i < 5) {
                        daysButtons[i] = new JButton("null");
                    } else if (i >= 36 && i <= 41) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
            if (month.equals("April2025")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i == 0) {
                        daysButtons[i] = new JButton("null");
                    } else if (i > 30 && i < 35) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
            if (month.equals("December")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i >= 0 && i < 6) {
                        daysButtons[i] = new JButton("null");
                    } else if (i >= 37 && i <= 41) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
            if (month.equals("November")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i == 0 || i == 1 || i < 4) {
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
            if (month.equals("October")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i == 0 && i < 6) {
                        daysButtons[i] = new JButton("null");
                    } else if (i >= 32) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
            if (month.equals("September")) {
                for (int i = 0; i < daysButtons.length; i++) {
                    if (i >= 0 && i < 6) {
                        daysButtons[i] = new JButton("null");
                    } else if (i >= 36 && i <= 41) {
                        daysButtons[i] = new JButton("null");
                    } else {
                        daysButtons[i] = new JButton(String.valueOf(counter));
                        daysButtons[i].setPreferredSize(Config.CALENDAR_MONTH_STATS_VIEW_BUTTONS_SIZE_DIMENSION);
                        daysButtons[i].addActionListener(new DaysButtonsActionListener(daysButtons[i]));
                        counter++;
                    }
                }
            }
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

        calendarMonthStatsViewPanelMain.setLayout(mainPanelGridLayout);

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

        String fullDate = getDateFromComboBox();

        for (int i = 0; i < daysButtons.length; i++) {
            if (!daysButtons[i].getText().equals("null")) {
                if (daysButtons[i].getText().length() == 1) {
                    fullDate = fullDate + "0" + daysButtons[i].getText();

                }

                if (daysButtons[i].getText().length() == 2) {
                    fullDate = fullDate + daysButtons[i].getText();
                }

                //<editor-fold desc="Get counter for days">
                // Get counter for days
                // Set tag and color
                int statusFoDays = dayMacroGoalStatus(fullDate);
                if (statusFoDays == 0) {
                    noDataDaysCounter++;
                }

                if (statusFoDays == 1) {
                    goodDaysCounter++;
                }

                if (statusFoDays == 2) {
                    badDaysCounter++;
                }

                if (statusFoDays == 3) {
                    comingDaysCounter++;
                }

                //<editor-fold desc="Set colors for buttons">
                // Set tag and color
                if (statusFoDays != 0 && statusFoDays != 3) {
                    float kcalValueForDay = getDayMacro(fullDate).getKcal();
                    if (kcalValueForDay < greenKcalLimit) {
                        daysButtons[i].setBackground(greenKcalLimitColor);
                    } else if (kcalValueForDay >= greenKcalLimit && kcalValueForDay < yellowKcalLimit) {
                        daysButtons[i].setBackground(yellowKcalLimitColor);
                    } else if (kcalValueForDay >= yellowKcalLimit && kcalValueForDay < redKcalLimit) {
                        daysButtons[i].setBackground(orangeKcalLimitColor);
                    } else {
                        daysButtons[i].setBackground(redKcalLimitColor);
                    }
                } else {
                    daysButtons[i].setBackground(noDataForButtonColor);
                }

                //</editor-fold>

                //</editor-fold>
            }
            fullDate = getDateFromComboBox();
        }
    }
    //</editor-fold>

    //<editor-fold desc="General">
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

    //</editor-fold>

    //<editor-fold desc="Get data - methods">
    //<editor-fold desc="Get data from - combo box">
    private String getDateFromComboBox() {
        String fullDate = "";
        String month = monthSelectComboBox.getSelectedItem().toString();
        if (month.contains("2026")) {
            fullDate = "2026";
        }
        if (month.contains("2025")) {
            fullDate = "2025";
        }
        if (month.contains("2024")) {
            fullDate = "2024";
        }

        fullDate = fullDate + "-" + MyDate.getNameOfMonthFromNumberSQLFormat(month.replaceAll("[0-9]", "")) + "-";

        return fullDate;
    }

    private int getYearFromComboBox() {
        int year = -1;
        String dateFromComboBox = monthSelectComboBox.getSelectedItem().toString();
        if (dateFromComboBox.contains("2026")) {
            year = 2026;
        }
        if (dateFromComboBox.contains("2025")) {
            year = 2025;
        }
        if (dateFromComboBox.contains("2024")) {
            year = 2024;
        }

        return year;
    }

    private int getMonthFromComboBox() {
        String month = monthSelectComboBox.getSelectedItem().toString().replaceAll("[0-9]", "");

        return getNumberOfMonthInYear(month);
    }
    //</editor-fold>

    //<editor-fold desc="Get Macro data">
    private Macro getAverageMacroForMonth(String month) {
        String fullDate = "";

        if (month.contains("2026")) {
            fullDate = "2026";
        }
        if (month.contains("2025")) {
            fullDate = "2025";
        }
        if (month.contains("2024")) {
            fullDate = "2024";
        }

        fullDate = fullDate + "-" + MyDate.getNameOfMonthFromNumberSQLFormat(month) + "-";

        String fullDateTMP = fullDate;
        Macro averageMacro = new Macro(0, 0, 0, 0);
        Macro dayMacro;
        int dayCounter = 0;

        for (int i = 0; i < daysButtons.length; i++) {

            if (daysButtons[i].getText() != "null") {
                if (daysButtons[i].getText().length() == 1) {
                    fullDate = fullDateTMP + "0" + daysButtons[i].getText();
                } else {
                    fullDate += daysButtons[i].getText();
                }

                if (dayMacroGoalStatus(fullDate) != 3 && dayMacroGoalStatus(fullDate) != 42) {
                    dayMacro = SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(fullDate);
                    averageMacro = Macro.sumOfTwoMacros(averageMacro, dayMacro);
                    dayCounter++;
                }

                fullDate = fullDateTMP;
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
        float goalKcal = Config.BMRActual.getKcal();

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
        } else if (dayMacro.getKcal() <= 0) {
            dayStatus = 0;
        } else if (dayMacro.getKcal() < goalKcal) {
            dayStatus = 1;
        } else if (dayMacro.getKcal() > goalKcal) {
            dayStatus = 2;
        }
        return dayStatus;
    }

    private Macro getDayMacro(String fullDateSQLFriendly) {
        Macro dayMacro = SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(fullDateSQLFriendly);

        return dayMacro;
    }
    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="Refresh methods">
    private void refreshMacroAndAllComponentForSelectedDayMacro(String panelTitleLabelText, Macro macro) {
        calendarMonthStatsViewPanelEast.removeAll();


        calendarMonthStatsViewPanelEast.setBackground(Color.WHITE);
        calendarMonthStatsViewPanelEast.setPreferredSize(new Dimension(Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE, Config.CALENDAR_MONTH_STATS_VIEW_PANELS_WEST_EAST_SIZE));

        calendarMonthStatsViewPanelEast.setLayout(eastPanelGridLayout);


        calendarMonthStatsViewPanelEast.add(getSetMacroPanelComponent(panelTitleLabelText,
                macro.getKcal(), macro.getProtein(), macro.getFat(), macro.getCarbs()), 0, 0);

        calendarMonthStatsViewPanelEast.add(getSetMacroPanelComponent("Macro - goal:", Config.BMRActual.getKcal(), Config.BMRActual.getProtein(), Config.BMRActual.getFat(), Config.BMRActual.getCarbs()), 0, 1);

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

    private void refreshAverageMacroPanelForNorthPanel(Macro averageMacro) {
        calendarMonthStatsViewPanelNorth.removeAll();

        setupAverageMacroLabel();

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

//</editor-fold>

    //<editor-fold desc="Actions Listeners, Item Listeners">

    //<editor-fold desc="calendarMonthStatsView - Panel Main - ActionListeners">
    private class DaysButtonsActionListener implements ActionListener {
        JButton button;

        public DaysButtonsActionListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String fullDate = "";
            String month = monthSelectComboBox.getSelectedItem().toString();

            if (month.contains("2025")) {
                fullDate = "2025";
            }
            if (month.contains("2024")) {
                fullDate = "2024";
            }

            fullDate = fullDate + "-" + MyDate.getNameOfMonthFromNumberSQLFormat(month.replaceAll("[0-9]", "")) + "-";

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
    //</editor-fold>

    //<editor-fold desc="calendarMonthStatsView - Panel North - ActionListeners">
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
    //</editor-fold>

    //<editor-fold desc="calendarMonthStatsView - Panel West - ActionListeners">
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

    private class SetNextButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentSetDate = checkCalendarTableDateTextField.getText();
            checkCalendarTableDateTextField.setText(MyDate.getNextDayDateSQLFriendlyFormat(currentSetDate));
        }
    }

    private class SetPreviousButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentSetDate = checkCalendarTableDateTextField.getText();
            checkCalendarTableDateTextField.setText(MyDate.getPreviousDayDateSQLFriendlyFormat(currentSetDate));
        }
    }

    private class CheckCalendarTableActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame checkCalendarTableButtonWindowFrame = new JFrame("Calendar Table");

            DefaultTableModel model = new DefaultTableModel();

            for (int i = 0; i < columnsNamesToDisplayOnQuickView.length; i++) {
                model.addColumn(columnsNamesToDisplayOnQuickView[i]);
            }

            JTable table = new JTable(model);

            table.setModel(model);

            table.getColumnModel().getColumn(0).setMaxWidth(50);

            int minWidthForProductNameColumn = 300;
            table.getColumnModel().getColumn(3).setMinWidth(minWidthForProductNameColumn);

            //amount of product
            int maxWidthForAmountOfProductColumn = 300;
            table.getColumnModel().getColumn(4).setMaxWidth(maxWidthForAmountOfProductColumn);

            //<editor-fold desc="Macro Columns">
            int maxWidthForMacroColumns = 250;
            table.getColumnModel().getColumn(5).setMaxWidth(maxWidthForMacroColumns);
            table.getColumnModel().getColumn(6).setMaxWidth(maxWidthForMacroColumns);
            table.getColumnModel().getColumn(7).setMaxWidth(maxWidthForMacroColumns);
            table.getColumnModel().getColumn(8).setMaxWidth(maxWidthForMacroColumns);
            //</editor-fold>


            JScrollPane scrollPane = new JScrollPane(table);
            String date = checkCalendarTableDateTextField.getText();
            Connection connection;
            String sql = "SELECT `day_date`, `day_name`, `product_name`, `amount_of_product`,  " +
                    "`kcal_consume`, `carbs_consume`, `fat_consume`, `protein_consume`, `meal_name` " +
                    "FROM calendar WHERE day_date=\"" +
                    date +
                    "\";";
            try {
                connection = GetConnection.getConnectionWithLocalHost();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                int counter = 1;
                while (resultSet.next()) {
                    String day_date = resultSet.getString(1);
                    String day_name = resultSet.getString(2);
                    String product_name = resultSet.getString(3);
                    String amount_of_product = resultSet.getString(4);
                    String kcal_consume = resultSet.getString(5);
                    String carbs_consume = resultSet.getString(6);
                    String fat_consume = resultSet.getString(7);
                    String protein_consume = resultSet.getString(8);
                    String meal_name = resultSet.getString(9);

                    model.addRow(new Object[]{counter, day_date, day_name, product_name, amount_of_product, kcal_consume, carbs_consume, fat_consume, protein_consume, meal_name});
                    counter++;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            checkCalendarTableButtonWindowFrame.add(scrollPane);
            checkCalendarTableButtonWindowFrame.setSize(Config.CHECK_CALENDAR_TABLE_BUTTON_WINDOW_FRAME_SIZE);
            checkCalendarTableButtonWindowFrame.setResizable(true);
            checkCalendarTableButtonWindowFrame.setLocationRelativeTo(null);
            checkCalendarTableButtonWindowFrame.show();
        }
    }

    private class CheckDaysStatisticFilledTableActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame checkDaysStatisticFilledTableButtonWindowFrame = new JFrame("Days Statistics");

            DefaultTableModel model = new DefaultTableModel();

            for (int i = 0; i < columnsNamesFromDaysStatisticsToDisplayOnQuickView.length; i++) {
                model.addColumn(columnsNamesFromDaysStatisticsToDisplayOnQuickView[i]);
            }

            JTable table = new JTable(model);
            table.setModel(model);
            JScrollPane scrollPane = new JScrollPane(table);
            String date = checkDaysStatisticsTableDateTextField.getText();
            Connection connection;
            String sql = "SELECT `day_date`, `amount_of_points_from_notepad`, " +
                    "`amount_of_filled_points_from_notepad`, `kcal_consume`, " +
                    "`protein_consume`, `fat_consume`, `carbs_consume`, `day_name`" +
                    "FROM days_statistics_test WHERE day_date LIKE\"" +
                    date +
                    "\";";
            try {
                connection = GetConnection.getConnectionWithLocalHost();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                int counter = 1;
                while (resultSet.next()) {
                    String day_date = resultSet.getString(1);
                    String day_name = resultSet.getString(2);
                    String product_name = resultSet.getString(3);
                    String amount_of_product = resultSet.getString(4);
                    String kcal_consume = resultSet.getString(5);
                    String carbs_consume = resultSet.getString(6);
                    String fat_consume = resultSet.getString(7);
                    String protein_consume = resultSet.getString(8);

                    model.addRow(new Object[]{counter, day_date, day_name, product_name, amount_of_product, kcal_consume, carbs_consume, fat_consume, protein_consume});
                    counter++;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            checkDaysStatisticFilledTableButtonWindowFrame.add(scrollPane);
            checkDaysStatisticFilledTableButtonWindowFrame.setSize(Config.CHECK_DAYS_STATISTIC_FILLED_TABLE_BUTTON_WINDOW_FRAME_SIZE);
            checkDaysStatisticFilledTableButtonWindowFrame.setResizable(false);
            checkDaysStatisticFilledTableButtonWindowFrame.setLocationRelativeTo(null);
            checkDaysStatisticFilledTableButtonWindowFrame.show();
        }
    }
    //</editor-fold>

    //<editor-fold desc="calendarMonthStatsView - Panel East - ActionListeners">
    //empty
    //</editor-fold>

    //<editor-fold desc="calendarMonthStatsView - Panel South - ActionListeners">
    private class ShowChartButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            chartsClass.showMonthChart();
        }
    }

    private class ShowBarChartButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            chartsClass.displayCharBar();
        }
    }

    //</editor-fold>


    //<editor-fold desc="Charts window Action Listeners">
    private class TestFrameKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                System.out.println("Right pressed");
                chartsClass.updateJFrameForCharBar(1);
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                System.out.println("Left pressed");
                chartsClass.updateJFrameForCharBar(-1);
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                System.out.println("Down pressed");
            }

            if (e.getKeyCode() == KeyEvent.VK_H) {
                System.out.println("H pressed");
            }


        }
    }

    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="inner classes">
    public class ChartsClass {
        //<editor-fold desc="Global variables">
        String[] daysNumbers;
        float[] valuesKcal;
        String chartName;
        int monthToDisplay = MyDate.getCurrentMonthNumber();
        int yearToDisplay = 2026;
        int monthIntervalForChart;
        ChartPanel chartPanel;
        //</editor-fold>

        //<editor-fold desc="Swing components">
        JMenuBar menuBar;
        JMenu menu;
        JFrame chartFrame;
        JLabel dateLabel;
        //</editor-fold>

        ChartsClass() {
            monthIntervalForChart = 0;
            dateLabel = new JLabel("" +
                    MyDate.getCurrentMonthNumber() + "-" + MyDate.getCurrentMonthNumber());
        }

        //<editor-fold desc="Charts - methods">
        public void prepareDataForCharts(int previousNextOrCurrentMonth) {
            String chartName = MyDate.getNameOfMonthFromNumber(getMonthFromComboBox()) + " stats";

            monthIntervalForChart += previousNextOrCurrentMonth;

            Date dt = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.MONTH, monthIntervalForChart);
            dt = c.getTime();

            String pattern = "yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String dateYear = simpleDateFormat.format(dt);

            String pattern2 = "MM";
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
            String dateMonth = simpleDateFormat2.format(dt);

            yearToDisplay = Integer.valueOf(dateYear);

            if (dateMonth.charAt(0) == '0') {
                monthToDisplay = Integer.valueOf(dateMonth.substring(1, 2));
            } else {

                monthToDisplay = Integer.valueOf(dateMonth);
            }

            dateLabel = new JLabel("" + dateMonth + "-" + dateYear);

            valuesKcal = new float[MyDate.getAmountOfDaysInMonth(monthToDisplay)];

            daysNumbers = MyDate.getAllDaysInMonthAndYearSQLFriendlyFormat(monthToDisplay, yearToDisplay);

            Debug.printYellowSystemPrintln((monthToDisplay + " " + yearToDisplay));

            for (int i = 0; i < valuesKcal.length; i++) {
                valuesKcal[i] = SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(daysNumbers[i]).getKcal();
            }
        }

        public void showMonthChart() {
            prepareDataForCharts(0);

            chartName = monthToDisplay + "-" + yearToDisplay;
            jFreeChart = DisplayChart.createChartPanel(chartName, "Days", "Kcal",
                    valuesKcal, "Kcal", daysNumbers);
            DisplayChart.showChart(jFreeChart);
        }

        public void prepareMonthBarChart() {
            //<editor-fold desc="Setup chart fields">

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            String[] dayInSQLFormat = new String[MyDate.getAmountOfDaysInMonth(monthToDisplay)];
            for (int i = 0; i < dayInSQLFormat.length; i++) {
                if ((i + 1) >= 10) {
                    dayInSQLFormat[i] = yearToDisplay + "-" + MyDate.getNameOfMonthFromNumberSQLFormat(monthToDisplay) + "-" + (i + 1);
                } else {
                    dayInSQLFormat[i] = yearToDisplay + "-" + MyDate.getNameOfMonthFromNumberSQLFormat(monthToDisplay) + "-" + "0" + (i + 1);
                }
            }

            for (int i = 0; i < daysNumbers.length; i++) {

                dataset.addValue(valuesKcal[i], ("" + (i + 1) + "-" + SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(dayInSQLFormat[i]).getKcal()), "kcal");
            }

            jFreeChart = ChartFactory.createBarChart(chartName, "Kcal", "Kcal",
                    dataset);

            CategoryPlot categoryPlot = jFreeChart.getCategoryPlot();
            //</editor-fold>

            //<editor-fold desc="Set float value and colors - Marker">
            float greenMarker = Config.BMRActual.getKcal();
            float yellowMarker = 4500;
            float redMarker = 5500;
            float blackAverageMarker = 0;

            int count = 0;
            float sum = 0;
            for (int i = 0; i < valuesKcal.length; i++) {
                if (valuesKcal[i] > 0) {
                    sum += valuesKcal[i];
                    count++;
                }
            }
            blackAverageMarker = (sum / count);
            //</editor-fold>

            //<editor-fold desc="Add color of bars depend on value">
            BarRenderer renderer = new BarRenderer() {

                @Override
                public Paint getItemPaint(int row, int column) {
                    CategoryDataset dataset = getPlot().getDataset();
                    Number value = dataset.getValue(row, column);

                    if (value == null) {
                        return Color.GRAY;
                    }

                    double v = value.doubleValue();

                    if (v < greenKcalLimit) {
                        return Color.GREEN;
                    } else if (v >= greenKcalLimit && v < yellowKcalLimit) {
                        return Color.YELLOW;
                    } else if (v >= yellowKcalLimit && v < redKcalLimit) {
                        return Color.ORANGE;
                    } else {
                        return Color.RED;
                    }
                }
            };
            // Optional: flat colors (no gradient)
            renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());
            renderer.setDrawBarOutline(false);

            categoryPlot.setRenderer(renderer);
            //</editor-fold>

            //<editor-fold desc="Add marker to chart">

            //<editor-fold desc="greenMarkerValueMarker">
            ValueMarker greenMarkerValueMarker = new ValueMarker(greenMarker);
            greenMarkerValueMarker.setPaint(greenMarkerColor);
            greenMarkerValueMarker.setStroke(new
                    BasicStroke(2.0f));

            greenMarkerValueMarker.setLabel(String.valueOf(Config.BMRActual.getKcal()));
            greenMarkerValueMarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
            greenMarkerValueMarker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
            //</editor-fold>

            //<editor-fold desc="yellowMarkerValueMarker">
            ValueMarker yellowMarkerValueMarker = new ValueMarker(yellowMarker);
            yellowMarkerValueMarker.setPaint(yellowMarkerColor);
            yellowMarkerValueMarker.setStroke(new BasicStroke(2.0f));

            yellowMarkerValueMarker.setLabel(String.valueOf(yellowMarker));
            yellowMarkerValueMarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
            yellowMarkerValueMarker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
            //</editor-fold>

            //<editor-fold desc="blackAverageMarkerValueMarker">
            ValueMarker blackAverageMarkerValueMarker = new ValueMarker(blackAverageMarker);
            blackAverageMarkerValueMarker.setPaint(blackAverageMarkerColor);
            blackAverageMarkerValueMarker.setStroke(new BasicStroke(2.0f));

            blackAverageMarkerValueMarker.setLabel(String.valueOf(blackAverageMarker));
            blackAverageMarkerValueMarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
            blackAverageMarkerValueMarker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
            //</editor-fold>

            //<editor-fold desc="redMarkerValueMarker">
            ValueMarker redMarkerValueMarker = new ValueMarker(redMarker);
            redMarkerValueMarker.setPaint(redMarkerColor);
            redMarkerValueMarker.setStroke(new

                    BasicStroke(2.0f));

            redMarkerValueMarker.setLabel(String.valueOf(redMarker));
            redMarkerValueMarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
            redMarkerValueMarker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);


            categoryPlot.addRangeMarker(greenMarkerValueMarker);
            categoryPlot.addRangeMarker(yellowMarkerValueMarker);
            // categoryPlot.addRangeMarker(orangeMarkerValueMarker);
            categoryPlot.addRangeMarker(redMarkerValueMarker);
            categoryPlot.addRangeMarker(blackAverageMarkerValueMarker);
            //</editor-fold>

            //</editor-fold>

            //<editor-fold desc="Set Range of displayed Y-Axis">
            NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
            rangeAxis.setRange(0, 11000);
            //</editor-fold>

        }

        public void clearAllData() {
            SwingUtilities.updateComponentTreeUI(chartFrame);

            chartFrame.remove(chartPanel);
            chartFrame.remove(dateLabel);

            menuBar.removeAll();

            chartFrame.invalidate();
            chartFrame.validate();
            chartFrame.repaint();
        }

        public void displayCharBar() {
            prepareDataForCharts(monthIntervalForChart);
            prepareMonthBarChart();
            menuBar = new JMenuBar();
            menu = new JMenu("Month");
            chartFrame = new JFrame();

            chartPanel = new ChartPanel(jFreeChart);

            menuBar.add(previousMonthButton);
            menuBar.add(nextMonthButton);
            menuBar.add(currentMonthButton);
            menuBar.add(dateLabel);

            chartFrame.setJMenuBar(menuBar);

            chartFrame.setFocusable(true);
            chartFrame.add(chartPanel);

            chartFrame.addKeyListener(new TestFrameKeyListener());

            chartFrame.setSize(new Dimension(1000, 900));
            chartFrame.setVisible(true);
            chartFrame.setResizable(false);
            chartFrame.setLocationRelativeTo(null);
        }

        public void updateJFrameForCharBar(int previousNextOrCurrentMonth) {
            clearAllData();
            prepareDataForCharts(previousNextOrCurrentMonth);
            prepareMonthBarChart();

            menuBar.add(previousMonthButton);
            menuBar.add(nextMonthButton);
            menuBar.add(currentMonthButton);
            menuBar.add(dateLabel);

            chartFrame.setJMenuBar(menuBar);

            chartPanel = new ChartPanel(jFreeChart);

            chartFrame.setFocusable(true);
            chartFrame.add(chartPanel);

            chartFrame.setSize(new Dimension(900, 700));
            chartFrame.setVisible(true);
            chartFrame.setResizable(false);
            chartFrame.setLocationRelativeTo(null);

            chartFrame.revalidate();
            chartFrame.repaint();
        }


        private class TestFrameKeyListener implements KeyListener {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    System.out.println("Right pressed");
                    chartsClass.updateJFrameForCharBar(1);
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    System.out.println("Left pressed");
                    chartsClass.updateJFrameForCharBar(-1);
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    System.out.println("Down pressed");
                }

                if (e.getKeyCode() == KeyEvent.VK_H) {
                    System.out.println("H pressed");
                }


            }
        }
        //</editor-fold>
    }
    //</editor-fold>
}
