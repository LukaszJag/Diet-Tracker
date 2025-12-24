package gui.diet;

import configuration.Config;
import logs.Log;
import runners_and_tests.run_update.RunnerFullUpdateDayStatistics;
import tools.calendar_tools.DayInCalendar;
import tools.calendar_tools.MyDate;
import tools.products_tools.Macro;
import tools.products_tools.Product;
import tools.sql_tools.SQLSelect;
import tools.sql_tools.calendar.InsertToCalendarDayTable;
import tools.sql_tools.days_statistics.GenerateSLQTableForDaysStatistics;
import tools.sql_tools.days_statistics.SelectFromDaysStatistics;
import tools.sql_tools.general.get.GetConnection;
import tools.text_files_tools.FilesTools;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AddProductToCalendarDay {

    //<editor-fold desc="Main - AddProductToCalendarDay - components and variables">

    //<editor-fold desc="Panels">
    // Panels
    JPanel addProductToDayPanelMain = new JPanel();
    JPanel addProductToDayPanelNorth = new JPanel();
    JPanel addProductToDayPanelWest = new JPanel();
    JPanel addProductToDayPanelEast = new JPanel();
    JPanel addProductToDayPanelSouth = new JPanel();
    //</editor-fold>

    //<editor-fold desc="Frame">
    // Frame
    JFrame addProductToDayFrame = new JFrame("Add Product To Day");

    //</editor-fold>

    //<editor-fold desc="Buttons">

    //<editor-fold desc="South panel - buttons">
    JButton addProductToDayAcceptButton = new JButton("Accept");
    JButton backToMainWindowButton = new JButton("Go to Start");
    JButton exitProgramProductWindowButton = new JButton("Exit application");
    //</editor-fold>

    //<editor-fold desc="West panel - buttons">
    JButton inputCurrentDayButton = new JButton("Input  current day");
    JButton checkCalendarTableButton = new JButton("Check calendar table");
    JButton checkDaysStatisticFilledTableButton = new JButton("Check days statistic");
    //</editor-fold>

    //<editor-fold desc="East panel - buttons">
    JButton checkIfProductExistButton = new JButton("Check Product existing");
    JButton fillTheExistingProductMacroButton = new JButton("Fill product");
    JButton changeToCalendarMainTableButton = new JButton("Calendar Table");
    JButton changeToCalendarTestTableButton = new JButton("Calendar TEST Table");
    JButton clearTextFieldsButton = new JButton("Clear");
    JButton getProductFullInfo = new JButton("Get product full info");
    JButton showEnableShortCutsButton = new JButton("Shortcuts tips");
    JButton calendarMonthStatsView = new JButton("Month stats view");
    JButton refreshDaysStatisticsDataBaseButton = new JButton("Refresh DaysStatistics Data");
    JButton editDaysStatisticsFileButton = new JButton("Edit current statistics");
    //</editor-fold>

    //<editor-fold desc="Main panel - buttons"">
    JButton otherThenCurrentDateButton = new JButton("Other then current");
    JButton productsCommentDisplayJButton = new JButton("Get comment");
    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="Labels">
    // Labels
    JLabel addProductToDayCurrentDateTextLabel = new JLabel("CURRENT DATE:");
    JLabel addProductToDayDisplaySelectedDay = new JLabel("Selected date: ");
    static JLabel addProductToDayDisplaySelectedFDateDayLabel = new JLabel();
    static JLabel addProductToDayDisplaySelectedFDateNameDayLabel = new JLabel();
    JLabel addProductToDayCurrentDateLabel = new JLabel("dd.mm.yyyy");
    JLabel dateLabel = new JLabel("Date:");
    JLabel dayMealNameLabel = new JLabel("Meal name(IN PROGRESS):");
    JLabel productNameLabel = new JLabel("Product name:");
    JLabel productNameSuggestionLabel = new JLabel("Product name suggestion:");
    JLabel amountOfProductLabel = new JLabel("Amount of product:");

    JLabel brandLabel = new JLabel("Brand:");

    JLabel kcalLabel = new JLabel("Kcal:");
    JLabel proteinLabel = new JLabel("Protein:");
    JLabel fatLabel = new JLabel("Fat:");

    JLabel carbsLabel = new JLabel("Carbs:");


    JLabel PackageLabel = new JLabel("Package has:");
    JLabel productsCommentJLabel = new JLabel("Product's comment:");
    JLabel timeOptionalLabel = new JLabel("Time(optional):");
    JLabel commentOptionalLabel = new JLabel("Comment(optional):");
    JLabel chosenCalendarTableLabel = new JLabel();
    JLabel shortcutsCRTTipsLabel = new JLabel("CTRL - Search product by name");
    JLabel shortcutsDOWNArrowTipsLabel = new JLabel("Down arrow - Fill selected name");
    JLabel shortcutsUPArrowTipsLabel = new JLabel("Up arrow - Fill macro for product");
    JLabel shortcutsCRTLAmountOfProductTipsLabel = new JLabel("CTRL (Amount...) - Accept Data");


    JLabel kcalAmountJLabel = new JLabel("?");
    JLabel proteinAmountJLabel = new JLabel("?");
    JLabel fatAmountJLabel = new JLabel("?");
    JLabel carbsAmountJLabel = new JLabel("?");
    JLabel packageHasAmountJLabel = new JLabel("?");

    JLabel BMRTitleJLabel = new JLabel("BMR table:");
    JLabel howMuchMacroLeftJLabel = new JLabel("Macro left");
    //</editor-fold>

    //<editor-fold desc="TextFields">
    // TextFields
    JTextField productNameTextField = new JTextField();
    JTextField amountOfProductTextField = new JTextField();

    JTextField brandTextField = new JTextField();

    JTextField kcalTextField = new JTextField();
    JTextField proteinLTextField = new JTextField();
    JTextField fatTextField = new JTextField();
    JTextField carbsTextField = new JTextField();

    JTextField timeOptionalTextField = new JTextField();
    JTextField commentOptionalTextField = new JTextField();
    JTextField checkCalendarTableDateTextField = new JTextField();
    JTextField checkDaysStatisticsTableDateTextField = new JTextField();
    //</editor-fold>

    //<editor-fold desc="ComboBox">
    // ComboBox
    JComboBox<String> dayMealNameComboBox = new JComboBox<>(new String[]{"None", "Breakfast", "Second Breakfast", "Snack 1", "Dinner", "Snack 2"
            , "Supper", "After workout", "Night snack"});
    JComboBox<String> productSuggestionNameComboBox = new JComboBox<>(new String[]{""});
    //</editor-fold>

    //<editor-fold desc="RadioButton">

    JRadioButton productSearchSelectedRadioButton = new JRadioButton("Product search");

    JRadioButton brandSearchSelectedRadioButton = new JRadioButton("Brand search");

    //</editor-fold>

    //<editor-fold desc="ButtonGroup">
    ButtonGroup selectSearchTypeButtonGroup;
    //</editor-fold>

    //<editor-fold desc="Layout">
    GridLayout gridLayoutMainPanel = new GridLayout(15, 2, 10, 10);

    BoxLayout panelWestBoxLayout = new BoxLayout(addProductToDayPanelWest, BoxLayout.Y_AXIS);
    GridLayout panelWestGridLayout = new GridLayout(2, 1, 5, 10);

    //</editor-fold>

    //<editor-fold desc="TextAreas">
    JTextArea dayMacroTextArea = new JTextArea(6, 4);
    //</editor-fold>

    //<editor-fold desc="Tables">
    public JTable macroTable = new JTable(6, 2);
    public JTable BMRTable = new JTable(4, 2);
    public JTable howMuchMacroLeftTable = new JTable(4, 2);

    //</editor-fold>

    //<editor-fold desc="Macros">
    Macro macroToDisplay = new Macro();

    //</editor-fold>

    //<editor-fold desc="Strings and String arrays">
    String[] columnsNamesToDisplayOnQuickView = {"index", "day_date", "day_name", "product_name", "amount_of_product", "kcal_consume", "carbs_consume", "fat_consume", "protein_consume", "meal_name"};
    String[] columnsNamesFromDaysStatisticsToDisplayOnQuickView = {"index", "day_date", "amount_of_points_from_notepad", "amount_of_filled_points_from_notepad", "kcal_consume", "protein_consume", "fat_consume", "carbs_consume"};
    //</editor-fold>

    //<editor-fold desc="Colors">
    Color calendarMonthStatsViewButtonColor = new Color(90, 153, 39);
    Color editDaysStatisticsUpperPanelColor = Color.BLACK;
    Color editDaysStatisticsDownPanelColor = Color.gray;
    Color editDaysStatisticsCenterPanelColor = Color.white;
    Color editDaysStatisticsRightPanelColor = Color.BLUE;
    Color editDaysStatisticsLeftPanelColor = new Color(46, 56, 68);
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Starting constructors and methods">

    // Starting Constructor
    public AddProductToCalendarDay() {
        startAddProductToDayWindow();
    }

    private void startAddProductToDayWindow() {
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
        addProductToDayFrame.setSize(Config.ADD_PRODUCT_TO_DAY_WINDOWS_WIDTH, Config.ADD_PRODUCT_TO_DAY_WINDOWS_HEIGHT);
        addProductToDayFrame.setLayout(new BorderLayout());
    }

    private void addPanelsToFrame() {
        // Add Panels to Frame
        addProductToDayFrame.add(addProductToDayPanelNorth, BorderLayout.NORTH);
        addProductToDayFrame.add(addProductToDayPanelWest, BorderLayout.WEST);
        addProductToDayFrame.add(addProductToDayPanelMain, BorderLayout.CENTER);
        addProductToDayFrame.add(addProductToDayPanelEast, BorderLayout.EAST);
        addProductToDayFrame.add(addProductToDayPanelSouth, BorderLayout.SOUTH);

    }

    private void finishSetUpFrame() {
        addProductToDayFrame.setResizable(true);
        addProductToDayFrame.setLocationRelativeTo(null);
        addProductToDayFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addProductToDayFrame.setVisible(true);
    }

    //</editor-fold>

    //<editor-fold desc="Panels methods">

    private void setPanels() {
        //Set Layout
        addProductToDayPanelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addProductToDayPanelMain.setLayout(gridLayoutMainPanel);


        addProductToDayPanelWest.setLayout(panelWestGridLayout);


        // addProductToDayPanelWest.setLayout(westPanelBoxLayout);
        // Set panels colors
        addProductToDayPanelNorth.setBackground(Color.BLACK);
        addProductToDayPanelSouth.setBackground(Color.GRAY);
        addProductToDayPanelMain.setBackground(Color.WHITE);
        addProductToDayPanelWest.setBackground(Color.DARK_GRAY);
        addProductToDayPanelEast.setBackground(Color.BLUE);

        // Set preferred size of panel
        addProductToDayPanelNorth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE));
        addProductToDayPanelEast.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_EAST_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_EAST_SIZE));
        addProductToDayPanelMain.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER, Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER));
        addProductToDayPanelWest.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_WEST__SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_WEST__SIZE));
        addProductToDayPanelSouth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE));
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
        addProductToDayCurrentDateTextLabel.setForeground(Config.dateTimeLabels);
        addProductToDayPanelNorth.add(addProductToDayCurrentDateTextLabel);

        addProductToDayCurrentDateLabel.setForeground(Config.addProductToDayCurrentDateLabelColor);
        addProductToDayCurrentDateLabel.setText(new SimpleDateFormat("dd-MM-yyyy").format(Config.date) + " " + dayNameCurrentDateOnStartWindow);
        addProductToDayPanelNorth.add(addProductToDayCurrentDateLabel);
        //</editor-fold>

        //<editor-fold desc="Add Components to Panel - West">
        // Add Buttons


        JPanel upperPanelWest = new JPanel();
        JPanel middlePanelWest = new JPanel();
        JPanel bottomPanelWest = new JPanel();

        upperPanelWest.setLayout(new GridLayout(4, 2));
        middlePanelWest.setLayout(new BoxLayout(middlePanelWest, BoxLayout.Y_AXIS));

        upperPanelWest.setBackground(Color.BLACK);
        middlePanelWest.setBackground(Color.BLACK);
        bottomPanelWest.setBackground(Color.BLACK);

        inputCurrentDayButton.setPreferredSize(new Dimension(200, 10));

        inputCurrentDayButton.setAlignmentX(Component.LEFT_ALIGNMENT);


        inputCurrentDayButton.addActionListener(new InputCurrentDayButtonActionListener());

        addProductToDayDisplaySelectedDay.setAlignmentX(Component.LEFT_ALIGNMENT);
        addProductToDayDisplaySelectedDay.setForeground(Config.addProductToDayCurrentDateLabelColor);


        addProductToDayDisplaySelectedFDateDayLabel.setForeground(Config.addProductToDayCurrentDateLabelColor);
        addProductToDayDisplaySelectedFDateDayLabel.setText(new SimpleDateFormat("yyyy-MM-dd").format(Config.date));

        setUpMacroTable();

        addProductToDayDisplaySelectedFDateNameDayLabel.setForeground(Config.addProductToDayCurrentDateLabelColor);

        addProductToDayDisplaySelectedFDateNameDayLabel.setText(dayNameCurrentDateOnStartWindow);


        chosenCalendarTableLabel = new JLabel("Current Table is: " + Config.CURRENT_DATABASE_TABLE_CALENDAR);
        chosenCalendarTableLabel.setForeground(Config.CHOSE_TABLE_TO_INSERT_DATA);
        chosenCalendarTableLabel.setFont(new Font(chosenCalendarTableLabel.getFont().getFontName(), chosenCalendarTableLabel.getFont().getStyle(), 11));


        checkCalendarTableButton.addActionListener(new CheckCalendarTableActionListener());


        checkCalendarTableDateTextField.setMaximumSize(new Dimension(100, 30));
        checkCalendarTableDateTextField.setText(addProductToDayDisplaySelectedFDateDayLabel.getText());


        checkDaysStatisticFilledTableButton.addActionListener(new CheckDaysStatisticFilledTableActionListener());


        String dateForCheckDaysDStatisticsTable = addProductToDayDisplaySelectedFDateDayLabel.getText().substring(0, 5) + "12%";
        checkDaysStatisticsTableDateTextField.setText(dateForCheckDaysDStatisticsTable);

        checkDaysStatisticsTableDateTextField.setMaximumSize(new Dimension(100, 30));


        dayMacroTextArea.setPreferredSize(new Dimension(200, 500));
        dayMacroTextArea.setText(Macro.getShortMacroInformationPrettyFormat(curretDayMacro));

        macroTable.setMinimumSize(new Dimension(200, 150));


        BMRTitleJLabel.setForeground(Color.GREEN);

        setupBMRTable();

        howMuchMacroLeftJLabel.setForeground(Color.GREEN);

        setupHowMuchMacroLeftTable();


        upperPanelWest.add(inputCurrentDayButton);
        upperPanelWest.add(chosenCalendarTableLabel);

        JPanel upperSecondPanel = new JPanel();
        upperSecondPanel.setLayout(new BoxLayout(upperSecondPanel, BoxLayout.Y_AXIS));
        upperSecondPanel.setBackground(Color.black);


        upperSecondPanel.add(addProductToDayDisplaySelectedDay);
        upperSecondPanel.add(addProductToDayDisplaySelectedFDateNameDayLabel);
        upperSecondPanel.add(addProductToDayDisplaySelectedFDateDayLabel);

        upperPanelWest.add(upperSecondPanel);
        upperPanelWest.add(new JLabel());
        upperPanelWest.add(checkCalendarTableButton);
        upperPanelWest.add(checkCalendarTableDateTextField);
        upperPanelWest.add(checkDaysStatisticFilledTableButton);
        upperPanelWest.add(checkDaysStatisticsTableDateTextField);


        middlePanelWest.add(macroTable);

        middlePanelWest.add(BMRTitleJLabel);
        middlePanelWest.add(BMRTable);
        middlePanelWest.add(howMuchMacroLeftJLabel);
        middlePanelWest.add(howMuchMacroLeftTable);

        addProductToDayPanelWest.add(upperPanelWest);
        addProductToDayPanelWest.add(middlePanelWest);
        //addProductToDayPanelWest.add(bottomPanelWest);

        //</editor-fold>

        //<editor-fold desc="Add Components to Panel - East">

        addProductToDayPanelEast.add(checkIfProductExistButton);
        checkIfProductExistButton.addActionListener(new CheckIfProductExistButtonActionListener());


        addProductToDayPanelEast.add(fillTheExistingProductMacroButton);
        fillTheExistingProductMacroButton.addActionListener(new FillTheExistingProductMacroButtonListener());

        addProductToDayPanelEast.add(changeToCalendarMainTableButton);
        changeToCalendarMainTableButton.addActionListener(new ChangeCalendarTableButtonListener());
        changeToCalendarMainTableButton.setBackground(Color.GREEN);

        addProductToDayPanelEast.add(changeToCalendarTestTableButton);
        changeToCalendarTestTableButton.addActionListener(new ChangeCalendarTableToCalendarTestButtonListener());
        changeToCalendarTestTableButton.setBackground(Color.ORANGE);

        addProductToDayPanelEast.add(clearTextFieldsButton);
        clearTextFieldsButton.addActionListener(new ClearTextFieldsButtonActionListener());
        clearTextFieldsButton.setBackground(Color.WHITE);

        addProductToDayPanelEast.add(getProductFullInfo);
        getProductFullInfo.addActionListener(new GetProductFullInfoActionListener());


        showEnableShortCutsButton.addActionListener(new ShowEnableShortCutsButtonActionListener());
        addProductToDayPanelEast.add(showEnableShortCutsButton);

        calendarMonthStatsView.addActionListener(new CalendarMonthStatsViewActionListener());
        calendarMonthStatsView.setBackground(calendarMonthStatsViewButtonColor);
        addProductToDayPanelEast.add(calendarMonthStatsView);

        refreshDaysStatisticsDataBaseButton.addActionListener(new RefreshDaysStatisticsDataBaseButtonActionListener());
        addProductToDayPanelEast.add(refreshDaysStatisticsDataBaseButton);

        editDaysStatisticsFileButton.addActionListener(new EditDaysStatisticsFileButtonActionListener());
        addProductToDayPanelEast.add(editDaysStatisticsFileButton);
        //</editor-fold>

        //<editor-fold desc="Add Components to Panel - South">
        addProductToDayPanelSouth.add(addProductToDayAcceptButton);
        addProductToDayAcceptButton.addActionListener(new AddProductToDayAcceptButtonListener());

        addProductToDayPanelSouth.add(backToMainWindowButton);
        backToMainWindowButton.addActionListener(new BackToMainWindowButtonActionListener());

        exitProgramProductWindowButton = new JButton("Exit application");
        addProductToDayPanelSouth.add(exitProgramProductWindowButton);
        exitProgramProductWindowButton.addActionListener(new ExitApplicationButtonActionListener());
        //</editor-fold>

        //<editor-fold desc="Add Components to Center Panel">

        // 1 - row
        addProductToDayPanelMain.add(dateLabel);
        addProductToDayPanelMain.add(otherThenCurrentDateButton);
        otherThenCurrentDateButton.addActionListener(new OtherThenCurrentDateButtonListener());

        // 2 - row
        addProductToDayPanelMain.add(dayMealNameLabel);
        dayMealNameComboBox.setSelectedItem("None");
        addProductToDayPanelMain.add(dayMealNameComboBox);

        // 3 - row
        addProductToDayPanelMain.add(productNameLabel);
        addProductToDayPanelMain.add(productNameTextField);
        productNameTextField.addKeyListener(new ProductNameTextFieldKeyListener());

        // 4 - row
        addProductToDayPanelMain.add(productNameSuggestionLabel);
        addProductToDayPanelMain.add(productSuggestionNameComboBox);

        // 5 - row
        addProductToDayPanelMain.add(amountOfProductLabel);
        addProductToDayPanelMain.add(amountOfProductTextField);
        amountOfProductTextField.addKeyListener(new AmountOfProductTextFieldKeyListener());

        // 6 - row
        addProductToDayPanelMain.add(brandLabel);
        addProductToDayPanelMain.add(brandTextField);
        brandTextField.addKeyListener(new ProductBrandTextFieldKeyListener());

        // 7 - row
        addProductToDayPanelMain.add(kcalLabel);
        addProductToDayPanelMain.add(kcalAmountJLabel);

        // 8 - row
        addProductToDayPanelMain.add(proteinLabel);
        addProductToDayPanelMain.add(proteinAmountJLabel);

        // 9 - row
        addProductToDayPanelMain.add(fatLabel);
        addProductToDayPanelMain.add(fatAmountJLabel);

        // 10 - row
        addProductToDayPanelMain.add(carbsLabel);
        addProductToDayPanelMain.add(carbsAmountJLabel);

        // 10 - row
        addProductToDayPanelMain.add(PackageLabel);
        addProductToDayPanelMain.add(packageHasAmountJLabel);

        // 11 - row
        addProductToDayPanelMain.add(productsCommentJLabel);
        productsCommentDisplayJButton.addActionListener(new ProductsCommentDisplayJButtonActionListener());
        addProductToDayPanelMain.add(productsCommentDisplayJButton);

        // 12 - row
        addProductToDayPanelMain.add(timeOptionalLabel);
        addProductToDayPanelMain.add(timeOptionalTextField);

        // 13 - row
        addProductToDayPanelMain.add(commentOptionalLabel);
        addProductToDayPanelMain.add(commentOptionalTextField);

        //</editor-fold>
    }

    //</editor-fold>

    //<editor-fold desc="Main Methods">

    public boolean isProductExist() {

        String[] resultOfCheckIfProductExist;
        boolean isExist = false;
        try {
            resultOfCheckIfProductExist = SQLSelect.getRowFromProductTableByProductNameGetArray(productNameTextField.getText());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        for (String pos : resultOfCheckIfProductExist) {
            if (pos != null) {
                isExist = true;
                break;
            }
        }

        if (isExist) {
            // Filling text fields
            kcalAmountJLabel.setText(resultOfCheckIfProductExist[4]);
            proteinAmountJLabel.setText(resultOfCheckIfProductExist[5]);
            fatAmountJLabel.setText(resultOfCheckIfProductExist[6]);
            carbsAmountJLabel.setText(resultOfCheckIfProductExist[7]);
            brandTextField.setText(resultOfCheckIfProductExist[1]);

            packageHasAmountJLabel.setText(resultOfCheckIfProductExist[2]);

            String productData = "Product name:    " + resultOfCheckIfProductExist[0] + "\nKcal:    " + resultOfCheckIfProductExist[4]
                    + "\nProtein:    " + resultOfCheckIfProductExist[5] + "\nFat:    " + resultOfCheckIfProductExist[6]
                    + "\nCarbs:    " + resultOfCheckIfProductExist[7];
            return true;
        } else {
            return false;

        }
    }

    public void clearGUIMacroValues() {
        amountOfProductTextField.setText("");
        kcalAmountJLabel.setText("?");
        proteinAmountJLabel.setText("?");
        fatAmountJLabel.setText("?");
        carbsAmountJLabel.setText("?");

        commentOptionalTextField.setText("");
        productNameTextField.setText("");
        dayMealNameComboBox.setSelectedItem("None");
        productSuggestionNameComboBox.setSelectedItem(null);

        brandTextField.setText("");

        //JComboBox<String> dayMealNameComboBox = new JComboBox<>(new String[]{"None", "Breakfast", "Second Breakfast", "Snack 1", "Dinner", "Snack 2"
        //        , "Supper", "After workout", "Night snack"});

        //JComboBox<String> productSuggestionNameComboBox = new JComboBox<>(new String[]{""});
    }

    //<editor-fold desc="Accept product to calendar table methods">
    private void acceptProduct() {
        float amountOfProductInGrams = -1;
        try {
            amountOfProductInGrams = Float.valueOf(amountOfProductTextField.getText());
        } catch (Exception ex) {
            System.out.println("Error: Cannot parse data from amountOfProductTextField[String]->[Float]");
        }

        Product dayInCalendarProduct = getDayProductFromGUI();
        Macro consumedMacro = calculateConsumedMacro(dayInCalendarProduct, amountOfProductInGrams);
        DayInCalendar dayInCalendar = getDayInCalendarFromDataInGUI(dayInCalendarProduct, consumedMacro);

        FilesTools.sendSQLQueryToTxtFile(dayInCalendar,
                addProductToDayDisplaySelectedFDateDayLabel.getText(), amountOfProductTextField.getText());

        Log.addNewLogForProductToCalendarGUIAccept(dayInCalendar.getDayDateFormatFriendlyForSQL(), dayInCalendarProduct.getProductName(), dayInCalendar.getDayProductMacro(),
                dayInCalendar.getDayAmountOfProduct(), dayInCalendar.getDayDateDayName(), dayInCalendar.getMealName(), dayInCalendar.getDayProductProduct(),
                dayInCalendar.getConsumedMacro(), dayInCalendar);
        try {
            InsertToCalendarDayTable.addRowToCalendarTable(dayInCalendar);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        //<editor-fold desc="Prepare and Execute update data for selected month">
        String addProductToDayDisplaySelectedFDateDayLabelString = addProductToDayDisplaySelectedFDateDayLabel.getText();
        String monthInNumberString = "" + addProductToDayDisplaySelectedFDateDayLabelString.charAt(5) + addProductToDayDisplaySelectedFDateDayLabelString.charAt(6);

        if (monthInNumberString.charAt(0) == '0') {
            monthInNumberString = "" + monthInNumberString.charAt(1);
        }
        int monthInNumberInt = Integer.valueOf(monthInNumberString);

        String yearInString = addProductToDayDisplaySelectedFDateDayLabelString.substring(0, 4);
        int yearInNumber = Integer.valueOf(yearInString);

        String numberOfDay = "" + addProductToDayDisplaySelectedFDateDayLabelString.charAt(8) + addProductToDayDisplaySelectedFDateDayLabelString.charAt(9);
        int numberOfDayInt;
        if (numberOfDay.charAt(0) == '0') {
            numberOfDayInt = Integer.parseInt("" + numberOfDay.charAt(1));
        }

        numberOfDayInt = Integer.parseInt(numberOfDay);
        RunnerFullUpdateDayStatistics.runFullUpdateForOneMonthInDayStatistics(numberOfDayInt, monthInNumberInt, yearInNumber);
        //</editor-fold>

        JOptionPane.showMessageDialog(null, "Product has been added. Date: " +
                addProductToDayDisplaySelectedFDateNameDayLabel.getText()
                + "\n" + consumedMacro.getShortMacroInformation());
        clearGUIMacroValues();
    }

    public Product getDayProductFromGUI() {
        Macro productMacro = new Macro(
                Float.valueOf(kcalAmountJLabel.getText()),
                Float.valueOf(proteinAmountJLabel.getText()),
                Float.valueOf(fatAmountJLabel.getText()),
                Float.valueOf(carbsAmountJLabel.getText()));
        Product dayInCalendarProduct = new Product(productNameTextField.getText(), "None",
                100, productMacro, -1, "");

        return dayInCalendarProduct;
    }

    public DayInCalendar getDayInCalendarFromDataInGUI(Product productFromGUI, Macro consumedMacro) {
        //TO DO
        //<editor-fold desc="Getting direct from TextFields: Macro, day ">


        String dayProductOptionalTime = timeOptionalTextField.getText();
        String dayProductOptionalComment = commentOptionalTextField.getText();
        float dayAmountOfProduct = Float.valueOf(amountOfProductTextField.getText());
        String dayDateDayName = addProductToDayDisplaySelectedFDateNameDayLabel.getText();
        String commentOptional = commentOptionalTextField.getText();
        //</editor-fold>

        //<editor-fold desc="Setting correct full date from West Panel Label">
        // Set passing date to correct format

        Date datePassedToSQL;
        try {
            datePassedToSQL = new SimpleDateFormat("yyyy-MM-dd").parse(addProductToDayDisplaySelectedFDateDayLabel.getText());
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

        //</editor-fold>


        float kcalConsumeCalculated = Float.valueOf(kcalAmountJLabel.getText()) * (Float.valueOf(amountOfProductTextField.getText()) / (100.0f));

        DayInCalendar dayInCalendar = new DayInCalendar(addProductToDayDisplaySelectedFDateDayLabel.getText(), dayDateDayName, dayMealNameComboBox.getSelectedItem().toString(),
                dayAmountOfProduct, productFromGUI, productFromGUI.getProductMacroForItsSetMeasure(), dayProductOptionalTime, dayProductOptionalComment, consumedMacro);

        return dayInCalendar;
    }

    public Macro calculateConsumedMacro(Product productToCalculateConsumedMacro, float amountOfProductInGram) {
        float amountOfProductToCalculate = amountOfProductInGram / 100;
        Macro productMacro = productToCalculateConsumedMacro.getProductMacroForItsSetMeasure();

        Macro cosumedMacro = new Macro(productMacro.getKcal() * amountOfProductToCalculate, productMacro.getProtein() * amountOfProductToCalculate,
                productMacro.getFat() * amountOfProductToCalculate, productMacro.getCarbs() * amountOfProductToCalculate);
        return cosumedMacro;
    }
    //</editor-fold>

    public void mealNameAcceptManager() {
        if (dayMealNameComboBox.getSelectedItem().toString().equals("None")) {
            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want add product with -None- meal name?", "Right meal name?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                acceptProduct();
            } else {
                JOptionPane.showMessageDialog(null, "Back to adding product");
            }
        } else {
            acceptProduct();
        }

    }

    public void setUpMacroTable() {
        macroToDisplay = SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(addProductToDayDisplaySelectedFDateDayLabel.getText());
        macroTable.setValueAt("amount_of_points_from_notepad", 0, 0);
        macroTable.setValueAt(SelectFromDaysStatistics.getAmountOfPointsFromNotepad(MyDate.getCurrentDayInSQLFormat()), 0, 1);
        macroTable.setValueAt("amount_of_filled_points_from_notepad", 1, 0);
        macroTable.setValueAt(SelectFromDaysStatistics.getAmountOfFilledPointsFromNotepad(MyDate.getCurrentDayInSQLFormat()), 1, 1);
        macroTable.setValueAt("kcal_consume", 2, 0);
        macroTable.setValueAt(macroToDisplay.getKcal(), 2, 1);
        macroTable.setValueAt("protein_consume", 3, 0);
        macroTable.setValueAt(macroToDisplay.getProtein(), 3, 1);
        macroTable.setValueAt("fat_consume", 4, 0);
        macroTable.setValueAt(macroToDisplay.getFat(), 4, 1);
        macroTable.setValueAt("carbs_consume", 5, 0);
        macroTable.setValueAt(macroToDisplay.getCarbs(), 5, 1);
    }

    private void setupBMRTable() {
        BMRTable.setValueAt("BMR_kcal", 0, 0);
        BMRTable.setValueAt("BMR_protein", 1, 0);
        BMRTable.setValueAt("BMR_fat", 2, 0);
        BMRTable.setValueAt("BMR_carbs", 3, 0);
        BMRTable.setValueAt(Config.BMRActual.getKcal(), 0, 1);
        BMRTable.setValueAt(Config.BMRActual.getProtein(), 1, 1);
        BMRTable.setValueAt(Config.BMRActual.getFat(), 2, 1);
        BMRTable.setValueAt(Config.BMRActual.getCarbs(), 3, 1);

    }

    private void setupHowMuchMacroLeftTable() {
        howMuchMacroLeftTable.setValueAt("kcal", 0, 0);
        howMuchMacroLeftTable.setValueAt("protein", 1, 0);
        howMuchMacroLeftTable.setValueAt("fat", 2, 0);
        howMuchMacroLeftTable.setValueAt("carbs", 3, 0);

        howMuchMacroLeftTable.setValueAt(Config.BMRActual.getKcal() - macroToDisplay.getKcal(), 0, 1);
        howMuchMacroLeftTable.setValueAt(Config.BMRActual.getProtein() - macroToDisplay.getProtein(), 1, 1);
        howMuchMacroLeftTable.setValueAt(Config.BMRActual.getFat() - macroToDisplay.getFat(), 2, 1);
        howMuchMacroLeftTable.setValueAt(Config.BMRActual.getCarbs() - macroToDisplay.getCarbs(), 3, 1);
    }

    public void setUpMacroTable(String selectedDayInSQLFormat) {
        Macro macroToDisplay = SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(selectedDayInSQLFormat);
        macroTable.setValueAt("amount_of_points_from_notepad", 0, 0);
        macroTable.setValueAt(SelectFromDaysStatistics.getAmountOfPointsFromNotepad(selectedDayInSQLFormat), 0, 1);
        macroTable.setValueAt("amount_of_filled_points_from_notepad", 1, 0);
        macroTable.setValueAt(SelectFromDaysStatistics.getAmountOfFilledPointsFromNotepad(selectedDayInSQLFormat), 1, 1);
        macroTable.setValueAt("kcal_consume", 2, 0);
        macroTable.setValueAt(macroToDisplay.getKcal(), 2, 1);
        macroTable.setValueAt("protein_consume", 3, 0);
        macroTable.setValueAt(macroToDisplay.getProtein(), 3, 1);
        macroTable.setValueAt("fat_consume", 4, 0);
        macroTable.setValueAt(macroToDisplay.getFat(), 4, 1);
        macroTable.setValueAt("carbs_consume", 5, 0);
        macroTable.setValueAt(macroToDisplay.getCarbs(), 5, 1);
    }

    //</editor-fold>

    //<editor-fold desc="Action Listeners Classes">

    //<editor-fold desc="addProductToDay - Panel North - Buttons ActionListener">
    private class AddProductToDayAcceptButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mealNameAcceptManager();
        }

    }
    private class BackToMainWindowButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addProductToDayFrame.setState(Frame.ICONIFIED);
        }
    }
    private static class ExitApplicationButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    //</editor-fold>

    //<editor-fold desc="addProductToDay - Panel West - Buttons ActionListener">
    private class InputCurrentDayButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addProductToDayDisplaySelectedFDateNameDayLabel.setText(MyDate.getDayNameInPascalCase());
            addProductToDayDisplaySelectedFDateDayLabel.setText(MyDate.getCurrentDayInSQLFormat());
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
            checkCalendarTableButtonWindowFrame.setResizable(false);
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

    //<editor-fold desc="addProductToDay - Panel Main - Buttons ActionListener">
    private class OtherThenCurrentDateButtonListener implements ActionListener {

        public OtherThenCurrentDateButtonListener() {
            setDialogWindow();
        }

        //<editor-fold desc="Variables - OtherThenCurrentDateButtonListener">
        JLabel otherDateExampleLabel = new JLabel("Input date in yyyy-MM-dd");
        static JFrame otherThenCurrentDateButtonWindowFrame = new JFrame("frame");
        static JTextField otherDataTextField = new JTextField(20);
        static JButton[] daysInMonthButtons = new JButton[31];
        static JButton[] monthsInYearButtons = new JButton[12];
        static JPanel dialogWindowPanel = new JPanel();
        static JButton clickedButton = new JButton();
        static String reulstString = "";
        JButton dialogWindowAcceptButton = new JButton("Accept new date");
        //</editor-fold>

        private void showDialogWindow() {
            cleanDateTextField();
            otherThenCurrentDateButtonWindowFrame.show();
        }

        private void setDialogWindow() {

            otherDataTextField.setText("2024-");

            prepareButtonsForDialogWindow();

            dialogWindowAcceptButton.addMouseListener(new OtherThenCurrentDateButtonListener.dialogWindowPanelMouseListener());

            dialogWindowPanel.add(otherDateExampleLabel);
            dialogWindowPanel.add(otherDataTextField);
            dialogWindowPanel.add(dialogWindowAcceptButton);

            otherThenCurrentDateButtonWindowFrame.addMouseListener(new OtherThenCurrentDateButtonListener.dialogWindowPanelMouseListener());
            dialogWindowAcceptButton.addMouseListener(new OtherThenCurrentDateButtonListener.dialogWindowPanelMouseListener());
            JButton year2024 = new JButton("2024");
            JButton year2025 = new JButton("2025");

            year2024.addMouseListener(new OtherThenCurrentDateButtonListener.dialogWindowPanelMouseListener());
            year2025.addMouseListener(new OtherThenCurrentDateButtonListener.dialogWindowPanelMouseListener());

            dialogWindowPanel.add(year2024);
            dialogWindowPanel.add(year2025);


            for (int i = 0; i < monthsInYearButtons.length; i++) {
                dialogWindowPanel.add(monthsInYearButtons[i]);
            }

            for (int i = 0; i < monthsInYearButtons.length; i++) {
                monthsInYearButtons[i].addMouseListener(new OtherThenCurrentDateButtonListener.dialogWindowPanelMouseListener());
            }

            for (int i = 0; i < daysInMonthButtons.length; i++) {
                dialogWindowPanel.add(daysInMonthButtons[i]);
            }

            for (int i = 0; i < daysInMonthButtons.length; i++) {
                daysInMonthButtons[i].addMouseListener(new OtherThenCurrentDateButtonListener.dialogWindowPanelMouseListener());
            }

            otherThenCurrentDateButtonWindowFrame.add(dialogWindowPanel);
            otherThenCurrentDateButtonWindowFrame.setSize(600, 400);
            otherThenCurrentDateButtonWindowFrame.setResizable(false);
            otherThenCurrentDateButtonWindowFrame.setLocationRelativeTo(null);
        }

        private static void cleanDateTextField() {
            otherDataTextField.setText("2024");
        }

        public void prepareButtonsForDialogWindow() {
            for (int i = 0; i < monthsInYearButtons.length; i++) {
                monthsInYearButtons[i] = new JButton(String.valueOf(i + 1));
                monthsInYearButtons[i].setBackground(Color.BLUE);
                monthsInYearButtons[i].setForeground(Color.RED);
            }

            for (int i = 0; i < daysInMonthButtons.length; i++) {
                daysInMonthButtons[i] = new JButton(String.valueOf(i + 1));
                daysInMonthButtons[i].setBackground(Color.ORANGE);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            showDialogWindow();
        }

        private class dialogWindowPanelMouseListener implements MouseListener {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getSource().getClass() == clickedButton.getClass()) {
                    clickedButton = (JButton) e.getSource();

                    if (!clickedButton.getText().equals("Accept new date")) {
                        if (clickedButton.getText().equals("2024") || clickedButton.getText().equals("2025")) {
                            otherDataTextField.setText(clickedButton.getText());
                        } else {
                            if (clickedButton.getText().length() == 1) {
                                reulstString = otherDataTextField.getText() + "-" + "0" + clickedButton.getText();
                            } else {
                                reulstString = otherDataTextField.getText() + "-" + clickedButton.getText();
                            }
                            otherDataTextField.setText(reulstString);
                        }
                    } else {
                        boolean ifDateIsIncorrect = checkIfDateIsCorrect();

                        if (!ifDateIsIncorrect) {
                            addProductToDayDisplaySelectedFDateDayLabel.setText("Wrong date input");
                            dialogWindowPanel.disable();
                            otherThenCurrentDateButtonWindowFrame.dispose();
                        } else {
                            changeDateOnCalendarWindowGUI();
                            setUpMacroTable();
                            setupHowMuchMacroLeftTable();
                            dialogWindowPanel.disable();
                            otherThenCurrentDateButtonWindowFrame.dispose();
                        }
                    }
                }
            }

            private void changeDateOnCalendarWindowGUI() {
                Format format = new SimpleDateFormat("EEEE");

                String otherThanCurrentDateString = otherDataTextField.getText();
                String dayNameOtherDate = String.valueOf(otherThanCurrentDateString.charAt(8)) + String.valueOf(otherThanCurrentDateString.charAt(9));
                otherThanCurrentDateString = String.valueOf(otherThanCurrentDateString.charAt(5)) + String.valueOf(otherThanCurrentDateString.charAt(6));

                if (String.valueOf(dayNameOtherDate.charAt(0)).equals("0")) {
                    dayNameOtherDate = String.valueOf(dayNameOtherDate.charAt(1));
                }
                int dayNameOtherDateInt = Integer.valueOf(dayNameOtherDate);

                if (String.valueOf(otherThanCurrentDateString.charAt(0)).equals("0")) {
                    otherThanCurrentDateString = String.valueOf(otherThanCurrentDateString.charAt(1));
                }
                int month = Integer.valueOf(otherThanCurrentDateString);

                String dateInString = otherDataTextField.getText();
                String year = "";
                int yearInInt = 0;
                if (dateInString.substring(0, 4).equals("2025")) {
                    yearInInt = 2025;
                }

                if (dateInString.substring(0, 4).equals("2024")) {
                    yearInInt = 2024;
                }

                java.util.Date utilDateImport = new GregorianCalendar(yearInInt, month - 1, dayNameOtherDateInt).getTime();

                String dayNameCurrentDateOnStartWindow = format.format(utilDateImport);
                addProductToDayDisplaySelectedFDateNameDayLabel.setText(dayNameCurrentDateOnStartWindow);
                addProductToDayDisplaySelectedFDateDayLabel.setText(otherDataTextField.getText());
                setUpMacroTable(otherDataTextField.getText());
                setupHowMuchMacroLeftTable();
            }

            private boolean checkIfDateIsCorrect() {
                String dateToCheck = otherDataTextField.getText();
                if (dateToCheck.length() != 10) {
                    System.out.println("Too long: " + dateToCheck.length());
                    return false;
                } else if (!((dateToCheck.charAt(4) == '-') && (dateToCheck.charAt(7) == '-'))) {
                    System.out.println("Problems with: -");
                    return false;
                } else {
                    System.out.println("Good date");
                    return true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        }


    }
    private class ProductsCommentDisplayJButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String productsComment;
            try {
                productsComment = SQLSelect.getRowFromProductTableByProductNameGetArray(productNameTextField.getText())[8];
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(null, "Product's comment: " + productsComment);
        }
    }
    //</editor-fold>

    //<editor-fold desc="addProductToDay - Panel East - Buttons ActionListener">
    private class CheckIfProductExistButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] resultOfCheckIfProductExist;
            boolean isExist = false;
            try {
                resultOfCheckIfProductExist = SQLSelect.getRowFromProductTableByProductNameGetArray(productNameTextField.getText());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            for (String pos : resultOfCheckIfProductExist) {
                if (pos != null) {
                    isExist = true;
                    break;
                }
            }

            if (isExist) {
                String productData = "Product name:    " + resultOfCheckIfProductExist[0] + "\nKcal:    " + resultOfCheckIfProductExist[4]
                        + "\nProtein:    " + resultOfCheckIfProductExist[5] + "\nFat:    " + resultOfCheckIfProductExist[6] + "\nCarbs:    " + resultOfCheckIfProductExist[7];
                JOptionPane.showMessageDialog(null, "Product data:\n " + productData);
            } else {
                JOptionPane.showMessageDialog(null, "Product doesn't exist");
            }
        }
    }
    private class FillTheExistingProductMacroButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isProductExist() == true) {
                JOptionPane.showMessageDialog(null, "Product data has been filled");
            } else {
                JOptionPane.showMessageDialog(null, "Product doesn't exist");
            }

        }
    }
    private class ChangeCalendarTableButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Config.CURRENT_DATABASE_TABLE_CALENDAR = "calendar";
            chosenCalendarTableLabel.setText("Current Table is: " + Config.CURRENT_DATABASE_TABLE_CALENDAR);
        }
    }
    private class ChangeCalendarTableToCalendarTestButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Config.CURRENT_DATABASE_TABLE_CALENDAR = "calendar_test";
            chosenCalendarTableLabel.setText("Current Table is: " + Config.CURRENT_DATABASE_TABLE_CALENDAR);
        }
    }
    private class ClearTextFieldsButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearTextFields();
        }

        public void clearTextFields() {
            dayMealNameComboBox.setSelectedItem("None");
            productNameTextField.setText("");
            amountOfProductTextField.setText("");
            kcalTextField.setText("");
            proteinLTextField.setText("");
            fatTextField.setText("");
            carbsTextField.setText("");
            timeOptionalTextField.setText("");
            commentOptionalTextField.setText("");
        }
    }
    private class GetProductFullInfoActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] productInfoArray;
            try {
                productInfoArray = SQLSelect.getRowFromProductTableByProductNameGetArray(productNameTextField.getText());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            String productInfoInString = "";
            for (int i = 0; i < productInfoArray.length; i++) {
                productInfoInString += "[" + i + "]: " + Config.SQL_COLUMNS_PRODUCT[i].replace("product_", "").replace("`", "")
                        + ":  " + productInfoArray[i] + "\n";
            }
            JOptionPane.showMessageDialog(null, "Full product info: " + productInfoInString);
        }
    }
    private class ShowEnableShortCutsButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Panel shortcutsPanel = new Panel();
            JDialog shortcutsDialog = new JDialog(addProductToDayFrame, "Shortcuts");
            JButton shortcutsForProductNameButton = new JButton("Product name text field");
            JButton shortcutsForAmountOfProductButton = new JButton("Amount Of Product text field");
            JButton closeDialogButton = new JButton("Close");

            shortcutsDialog.setSize(300, 300);

            Color labelsColor = Color.BLACK;
            //shortcutWordLabel.setForeground(Color.RED);
            shortcutsCRTTipsLabel.setForeground(labelsColor);
            shortcutsDOWNArrowTipsLabel.setForeground(labelsColor);
            shortcutsUPArrowTipsLabel.setForeground(labelsColor);

            shortcutsPanel.add(shortcutsForProductNameButton);
            shortcutsPanel.add(shortcutsForAmountOfProductButton);


            //shortcutsPanel.add(shortcutWordLabel);
            shortcutsPanel.add(shortcutsCRTTipsLabel);
            shortcutsPanel.add(shortcutsDOWNArrowTipsLabel);
            shortcutsPanel.add(shortcutsUPArrowTipsLabel);
            shortcutsPanel.add(shortcutsCRTLAmountOfProductTipsLabel);

            shortcutsPanel.add(closeDialogButton);

            shortcutsDialog.setLocationRelativeTo(null);
            shortcutsDialog.add(shortcutsPanel);
            shortcutsDialog.setVisible(true);

        }
    }
    private class CalendarMonthStatsViewActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new CalendarMonthStatsView();
        }
    }
    private class RefreshDaysStatisticsDataBaseButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                RunnerFullUpdateDayStatistics.runFullUpdateForAllMonthInDayStatistics();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            JOptionPane.showMessageDialog(null, "Day Statistics is update");
        }
    }

    //<editor-fold desc="EditDaysStatisticsFileButtonActionListener - class and methods">
    private class EditDaysStatisticsFileButtonActionListener implements ActionListener {
        //<editor-fold desc="Global variables">
        JFrame editDaysStatisticsDialogFrame = new JFrame("Edit Days Statistics File");
        JTable editDaysStatisticsTable;
        String pathToFile;
        String[] dataToSave;

        LinkedHashMap<String, String> tableValues = new LinkedHashMap<>();

        //<editor-fold desc="Tables">
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel upperPanel = new JPanel();
        JPanel downPanel = new JPanel();
        //</editor-fold>

        //<editor-fold desc="Labels">
        JLabel columPickerLabel = new JLabel("Column name(value)");
        JLabel valuePickerLabel = new JLabel("Set value to:");
        //</editor-fold>

        //<editor-fold desc="Buttons">
        JButton saveButton = new JButton("Save");
        JButton exitButton = new JButton("Exit");
        //</editor-fold>

        //<editor-fold desc="Combo Boxes">
        JComboBox<String> pickYearComboBox = new JComboBox<String>(new String[]{"2024", "2025"});
        JComboBox<String> pickFileName = new JComboBox<String>();
        //</editor-fold>
        //</editor-fold>

        public EditDaysStatisticsFileButtonActionListener() {
            setFrameWindow();
        }

        private void setFrameWindow() {
            getDataForTable();

            prepareJTable();
            setupPanels();

            addComponentsToPanels();

            setupFrame();

            saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    getDataFromTable();
                    saveTableValuesToFile();
                }

            });

            exitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    editDaysStatisticsDialogFrame.hide();
                }
            });

        }

        //<editor-fold desc="Prepare and setup GUI components">
        private void setupFrame() {
            editDaysStatisticsDialogFrame.setSize(600, 800);
            editDaysStatisticsDialogFrame.setResizable(false);
            editDaysStatisticsDialogFrame.setLocationRelativeTo(null);

        }

        private void setupPanels() {
            editDaysStatisticsDialogFrame.setLayout(new BorderLayout());

            editDaysStatisticsDialogFrame.add(upperPanel, BorderLayout.NORTH);
            editDaysStatisticsDialogFrame.add(leftPanel, BorderLayout.WEST);
            editDaysStatisticsDialogFrame.add(centerPanel, BorderLayout.CENTER);
            editDaysStatisticsDialogFrame.add(rightPanel, BorderLayout.EAST);
            editDaysStatisticsDialogFrame.add(downPanel, BorderLayout.SOUTH);


            upperPanel.setBackground(editDaysStatisticsUpperPanelColor);
            downPanel.setBackground(editDaysStatisticsDownPanelColor);
            centerPanel.setBackground(editDaysStatisticsCenterPanelColor);
            rightPanel.setBackground(editDaysStatisticsRightPanelColor);
            leftPanel.setBackground(editDaysStatisticsLeftPanelColor);

            upperPanel.setPreferredSize(new Dimension(addProductToDayFrame.getWidth(), 50));
            downPanel.setPreferredSize(new Dimension(addProductToDayFrame.getWidth(), 150));
            centerPanel.setPreferredSize(new Dimension(400, 600));
            rightPanel.setPreferredSize(new Dimension(100, 600));
            leftPanel.setPreferredSize(new Dimension(100, 600));

        }

        private void prepareJTable() {
            int amountOfDays = MyDate.getAmountOfDaysInMonth(MyDate.getCurrentMonthNumber());

            editDaysStatisticsTable = new JTable(amountOfDays, 2);

            String pointInOneDay = "";

            for (int i = 0; i < amountOfDays; i++) {
                pointInOneDay = FilesTools.readAndGetLineTXTFile(pathToFile, (i + 1));
                editDaysStatisticsTable.setValueAt(MyDate.getAllDaysForCurrentMonthInSQLFriendlyFormat()[i], i, 0);
                editDaysStatisticsTable.setValueAt(Integer.valueOf(pointInOneDay), i, 1);
            }

        }

        private void addComponentsToPanels() {
            centerPanel.add(editDaysStatisticsTable);

            downPanel.add(saveButton);
            downPanel.add(exitButton);
        }
        //</editor-fold>

        private void getDataForTable() {
            int year = MyDate.getCurrentYear();
            String nameOfCurrentMonth = MyDate.getCurrentMonthName();

            String txtFile = nameOfCurrentMonth.toLowerCase() + "_" + year + ".txt";

            pathToFile = "src/data_store_and_backup/text_files/days_statistics_test/"
                    + year + "/" + txtFile;
        }

        private void getDataFromTable() {
            int amountOfRows = editDaysStatisticsTable.getRowCount();
            dataToSave = new String[amountOfRows];

            String key;
            String value;

            for (int i = 0; i < amountOfRows; i++) {
                key = String.valueOf(editDaysStatisticsTable.getValueAt(i, 0));
                value = String.valueOf(editDaysStatisticsTable.getValueAt(i, 1));
                tableValues.put(key, value);
                dataToSave[i] = value;
            }
        }

        private void saveTableValuesToFile() {
            String contentToFile = "";

            for (Map.Entry<String, String> mapElement : tableValues.entrySet()) {
                contentToFile += mapElement.getValue() + "\n";
            }

            FilesTools.writeToFileOverwriteAllFile("src/data_store_and_backup/text_files/days_statistics_test/2025/december_2025.txt", contentToFile);
            GenerateSLQTableForDaysStatistics.generateWholeMonthAndFillAmountOfPointsFromNotepad("december", 2025);
        }


        private void showFrameWindow() {
            editDaysStatisticsDialogFrame.show();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            showFrameWindow();
        }

    }
    //</editor-fold>
    //</editor-fold>


    //</editor-fold>

    //<editor-fold desc="KeyListener Classes">

    private class ProductNameTextFieldKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                productSuggestionNameComboBox.removeAllItems();
                String[] allRightNamesProductsArray = searchAllProductWith(productNameTextField.getText());
                JOptionPane.showMessageDialog(null, searchAllProductWith(productNameTextField.getText()));

                for (int i = 0; i < allRightNamesProductsArray.length; i++) {
                    if (allRightNamesProductsArray[i] != null) {
                        productSuggestionNameComboBox.addItem(allRightNamesProductsArray[i]);
                    }
                }
                kcalAmountJLabel.setText("?");
                proteinAmountJLabel.setText("?");
                fatAmountJLabel.setText("?");
                carbsAmountJLabel.setText("?");
            }


            if (e.getKeyCode() == KeyEvent.VK_UP) {
                String suggestionFromComboBoxString = productSuggestionNameComboBox.getSelectedItem().toString();
                productNameTextField.setText(suggestionFromComboBoxString);
                isProductExist();
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                selectNextItemInComboBox();
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                selectBackItemInMealComboBox();
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                selectNextItemInMealComboBox();
            }
        }

        public void selectNextItemInMealComboBox() {
            int productIndex = dayMealNameComboBox.getSelectedIndex();
            int comboBoxItemCount = dayMealNameComboBox.getItemCount();

            int nextProductIndex = productIndex + 1;

            if (nextProductIndex < comboBoxItemCount) {
                dayMealNameComboBox.setSelectedIndex(nextProductIndex);
            } else {
                dayMealNameComboBox.setSelectedIndex(0);
            }
        }

        public void selectBackItemInMealComboBox() {
            int productIndex = dayMealNameComboBox.getSelectedIndex();
            int comboBoxItemCount = dayMealNameComboBox.getItemCount();

            int nextProductIndex = productIndex - 1;


            if (nextProductIndex <= -1) {
                dayMealNameComboBox.setSelectedIndex(comboBoxItemCount - 1);
            } else {
                dayMealNameComboBox.setSelectedIndex(nextProductIndex);
            }
        }

        public void selectNextItemInComboBox() {
            int productIndex = productSuggestionNameComboBox.getSelectedIndex();
            int comboBoxItemCount = productSuggestionNameComboBox.getItemCount();


            int nextProductIndex = productIndex + 1;

            if (nextProductIndex < comboBoxItemCount) {
                productSuggestionNameComboBox.setSelectedIndex(nextProductIndex);
            } else {
                productSuggestionNameComboBox.setSelectedIndex(0);
            }

        }

        public String[] searchAllProductWith(String wordToSearch) {
            String[] allProductArray;
            String[] resultArray = new String[500];

            try {
                allProductArray = SQLSelect.getAllProductNamesFromProductTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            int counter = 0;
            for (int i = 0; i < allProductArray.length; i++) {
                if (allProductArray[i] != null) {
                    if (allProductArray[i].contains(wordToSearch)) {
                        resultArray[counter] = allProductArray[i];
                        counter++;
                    }
                }
            }

            return resultArray;
        }
    }

    private class AmountOfProductTextFieldKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                mealNameAcceptManager();
                productNameTextField.requestFocusInWindow();
                setUpMacroTable();
                setupHowMuchMacroLeftTable();
            }
        }


    }

    private class ProductBrandTextFieldKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                productSuggestionNameComboBox.removeAllItems();
                String[] allRightNamesProductsArray = searchAllProductWithBrand(brandTextField.getText());
                JOptionPane.showMessageDialog(null, searchAllProductWithBrandIncludedToResult(brandTextField.getText()));

                for (int i = 0; i < allRightNamesProductsArray.length; i++) {
                    if (allRightNamesProductsArray[i] != null) {
                        productSuggestionNameComboBox.addItem(allRightNamesProductsArray[i]);
                    }
                }
                kcalAmountJLabel.setText("?");
                proteinAmountJLabel.setText("?");
                fatAmountJLabel.setText("?");
                carbsAmountJLabel.setText("?");
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                productNameTextField.requestFocus();
            }
        }

        public String[] searchAllProductWithBrand(String brandToSearch) {
            HashMap<String, String> allProductAndBrandHashMap;
            String[] resultArray = new String[500];

            allProductAndBrandHashMap = SQLSelect.getAllProductAndBrandNamesFromProductTable();

            int counter = 0;
            for (String key : allProductAndBrandHashMap.keySet()) {
                if (allProductAndBrandHashMap.get(key).toLowerCase().contains(brandToSearch.toLowerCase())) {
                    resultArray[counter] = key;
                    counter++;
                    System.out.println("Good");
                }
            }
            System.out.println("HashMap size: " + allProductAndBrandHashMap.size());

            return resultArray;
        }

        public String[] searchAllProductWithBrandIncludedToResult(String brandToSearch) {
            HashMap<String, String> allProductAndBrandHashMap;
            String[] resultArray = new String[500];

            allProductAndBrandHashMap = SQLSelect.getAllProductAndBrandNamesFromProductTable();

            int counter = 0;
            for (String key : allProductAndBrandHashMap.keySet()) {
                if (allProductAndBrandHashMap.get(key).toLowerCase().contains(brandToSearch.toLowerCase())) {
                    resultArray[counter] = key + " - " + allProductAndBrandHashMap.get(key);
                    counter++;
                }
            }

            return resultArray;
        }
    }

    //</editor-fold>
}


