package gui;

import configuration.Config;
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
import java.util.Date;
import java.util.GregorianCalendar;

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
    JButton refreshDaysStatisticsDataBaseButton = new JButton("Refresh DaysStatistics Data");
    JButton addProductToDayAcceptButton = new JButton("Accept");
    JButton inputCurrentDayButton = new JButton("Input  current day");
    JButton checkIfProductExistButton = new JButton("Check Product existing");
    JButton displayProductMacroButton = new JButton("Display Product Macro");
    JButton importProductMacroFromLibraryButton = new JButton("Import product macro");
    JButton fillTheExistingProductMacroButton = new JButton("Fill product");
    JButton otherThenCurrentDateButton = new JButton("Other then current");
    JButton backToMainWindowButton = new JButton("Go to Start");
    JButton exitProgramProductWindowButton = new JButton("Exit application");
    JButton changeToCalendarMainTableButton = new JButton("Calendar Table");
    JButton changeToCalendarTestTableButton = new JButton("Calendar TEST Table");
    JButton clearTextFieldsButton = new JButton("Clear");
    JButton getProductFullInfo = new JButton("Get product full info");
    JButton showEnableShortCutsButton = new JButton("Shortcuts tips");
    JButton checkDaysStatisticFilledTable = new JButton("Check days statistic");
    JButton productsCommentDisplayJButton = new JButton("Get comment");
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
    JLabel kcalLabel = new JLabel("Kcal:");
    JLabel proteinLabel = new JLabel("Protein:");
    JLabel fatLabel = new JLabel("Fat:");
    JLabel carbsLabel = new JLabel("Carbs:");
    JLabel brandLabel = new JLabel("Brand:");
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
    JLabel brandNameLabel = new JLabel("?");
    JLabel packageHasAmountJLabel = new JLabel("?");
    //</editor-fold>

    //<editor-fold desc="TextFields">
    // TextFields
    JTextField productNameTextField = new JTextField();
    JTextField amountOfProductTextField = new JTextField();

    JTextField kcalTextField = new JTextField();
    JTextField proteinLTextField = new JTextField();
    JTextField fatTextField = new JTextField();
    JTextField carbsTextField = new JTextField();

    JTextField timeOptionalTextField = new JTextField();
    JTextField commentOptionalTextField = new JTextField();
    //</editor-fold>

    //<editor-fold desc="ComboBox">
    // ComboBox
    JComboBox<String> dayMealNameComboBox = new JComboBox<>(new String[]{"None", "Breakfast", "Second Breakfast", "Snack 1", "Dinner", "Snack 2"
            , "Supper", "After workout", "Night snack"});
    JComboBox<String> checkDaysStatisticsDialogMonthsComboBox = new JComboBox<>(DateTools.getAllMonthsNamesInUpperCase());

    JComboBox<String> productSuggestionNameComboBox = new JComboBox<>(new String[]{""});
    //</editor-fold>

    //<editor-fold desc="Layout">
    BoxLayout panelWestBoxLayout = new BoxLayout(addProductToDayPanelWest, BoxLayout.Y_AXIS);
    GridLayout gridLayoutMainPanel = new GridLayout(13, 2, 10, 10);
    GridLayout checkDaysStatisticsDialogGridLayout = new GridLayout(34, 4, 0,0 );
    GridLayout panelWestGridLayout = new GridLayout(8, 1, 5, 10);

    //</editor-fold>

    //<editor-fold desc="TextAreas">
    JTextArea dayMacroTextArea = new JTextArea(6, 4);
    //</editor-fold>

    JTable macroTable = new JTable(6,2);
    //</editor-fold>

    //<editor-fold desc="Starting Constructors">
    // Starting Constructor
    public AddProductToCalendarDay() {

        startAddProductToDayWindow();
    }
    //</editor-fold>

    //<editor-fold desc="Main Methods">
    private void setFrame() {
        setUpMacroTable();
        // Set window size
        addProductToDayFrame.setSize(Config.ADD_PRODUCT_TO_DAY_WINDOWS_WIDTH, Config.ADD_PRODUCT_TO_DAY_WINDOWS_HEIGHT);
        addProductToDayFrame.setLayout(new BorderLayout());
    }

    private void setPanels() {
        //Set Layout
        addProductToDayPanelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addProductToDayPanelMain.setLayout(gridLayoutMainPanel);
        addProductToDayPanelWest.setLayout(panelWestBoxLayout);

//        addProductToDayPanelWest.setLayout(westPanelBoxLayout);
        // Set panels colors
        addProductToDayPanelNorth.setBackground(Color.BLACK);
        addProductToDayPanelSouth.setBackground(Color.GRAY);
        addProductToDayPanelMain.setBackground(Color.WHITE);
        addProductToDayPanelWest.setBackground(Color.DARK_GRAY);
        addProductToDayPanelEast.setBackground(Color.BLUE);

        // Set preferred size of panel
        addProductToDayPanelNorth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE));
        addProductToDayPanelEast.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE));
        addProductToDayPanelMain.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER, Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER));
        addProductToDayPanelWest.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE));
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

        inputCurrentDayButton.setPreferredSize(new Dimension(200,10));
        addProductToDayPanelWest.add(inputCurrentDayButton);
        inputCurrentDayButton.addActionListener(new InputCurrentDayButtonActionListener());

        addProductToDayDisplaySelectedDay.setForeground(Config.addProductToDayCurrentDateLabelColor);
        addProductToDayPanelWest.add(addProductToDayDisplaySelectedDay);

        addProductToDayDisplaySelectedFDateDayLabel.setForeground(Config.addProductToDayCurrentDateLabelColor);
        addProductToDayDisplaySelectedFDateDayLabel.setText(new SimpleDateFormat("yyyy-MM-dd").format(Config.date));

        addProductToDayDisplaySelectedFDateNameDayLabel.setForeground(Config.addProductToDayCurrentDateLabelColor);

        addProductToDayDisplaySelectedFDateNameDayLabel.setText(dayNameCurrentDateOnStartWindow);

        addProductToDayPanelWest.add(addProductToDayDisplaySelectedFDateNameDayLabel);
        addProductToDayPanelWest.add(addProductToDayDisplaySelectedFDateDayLabel);

        chosenCalendarTableLabel = new JLabel("Current Table is: " + Config.CURRENT_DATABASE_TABLE_CALENDAR);
        chosenCalendarTableLabel.setForeground(Config.CHOSE_TABLE_TO_INSERT_DATA);

        addProductToDayPanelWest.add(chosenCalendarTableLabel);

        refreshDaysStatisticsDataBaseButton.addActionListener(new RefreshDaysStatisticsDataBaseButtonActionListener());
        addProductToDayPanelWest.add(refreshDaysStatisticsDataBaseButton);

        checkDaysStatisticFilledTable.addActionListener(new CheckDaysStatisticFilledTableActionListener());
        addProductToDayPanelWest.add(checkDaysStatisticFilledTable);

        dayMacroTextArea.setPreferredSize(new Dimension(200, 500));
        dayMacroTextArea.setText(curretDayMacro.getShortMacroInformationPrettyFormat(curretDayMacro));
        //addProductToDayPanelWest.add(dayMacroTextArea);

        macroTable.setPreferredSize(new Dimension(200,600));
        addProductToDayPanelWest.add(macroTable);

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
        // Add Components to Center Panel
        addProductToDayPanelMain.add(dateLabel);
        addProductToDayPanelMain.add(otherThenCurrentDateButton);
        otherThenCurrentDateButton.addActionListener(new OtherThenCurrentDateButtonListener());

        addProductToDayPanelMain.add(dayMealNameLabel);


        dayMealNameComboBox.setSelectedItem("None");
        addProductToDayPanelMain.add(dayMealNameComboBox);

        addProductToDayPanelMain.add(productNameLabel);
        addProductToDayPanelMain.add(productNameTextField);
        productNameTextField.addKeyListener(new ProductNameTextFieldKeyListener());

        addProductToDayPanelMain.add(productNameSuggestionLabel);
        addProductToDayPanelMain.add(productSuggestionNameComboBox);

        addProductToDayPanelMain.add(amountOfProductLabel);
        addProductToDayPanelMain.add(amountOfProductTextField);
        amountOfProductTextField.addKeyListener(new AmountOfProductTextFieldKeyListener());

        addProductToDayPanelMain.add(kcalLabel);
        addProductToDayPanelMain.add(kcalAmountJLabel);

        addProductToDayPanelMain.add(proteinLabel);
        addProductToDayPanelMain.add(proteinAmountJLabel);

        addProductToDayPanelMain.add(fatLabel);
        addProductToDayPanelMain.add(fatAmountJLabel);

        addProductToDayPanelMain.add(brandLabel);
        addProductToDayPanelMain.add(brandNameLabel);



        addProductToDayPanelMain.add(PackageLabel);
        addProductToDayPanelMain.add(packageHasAmountJLabel);

        addProductToDayPanelMain.add(timeOptionalLabel);
        addProductToDayPanelMain.add(timeOptionalTextField);


        addProductToDayPanelMain.add(productsCommentJLabel);
        productsCommentDisplayJButton.addActionListener(new ProductsCommentDisplayJButtonActionListener());
        addProductToDayPanelMain.add(productsCommentDisplayJButton);


        addProductToDayPanelMain.add(timeOptionalLabel);
        addProductToDayPanelMain.add(timeOptionalTextField);

        addProductToDayPanelMain.add(commentOptionalLabel);
        addProductToDayPanelMain.add(commentOptionalTextField);
        //</editor-fold>
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

    private void startAddProductToDayWindow() {
        setFrame();
        setPanels();
        addComponentsToPanels();
        addPanelsToFrame();
        finishSetUpFrame();
    }

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
            brandNameLabel.setText(resultOfCheckIfProductExist[1]);
            packageHasAmountJLabel.setText(resultOfCheckIfProductExist[2]);

            String productData = "Product name:    " + resultOfCheckIfProductExist[0] + "\nKcal:    " + resultOfCheckIfProductExist[4]
                    + "\nProtein:    " + resultOfCheckIfProductExist[5] + "\nFat:    " + resultOfCheckIfProductExist[6]
                    + "\nCarbs:    " + resultOfCheckIfProductExist[7];
            return true;
        } else {
            return false;

        }
    }
    public void clearGUIMacroValues(){
        amountOfProductTextField.setText("");
        kcalAmountJLabel.setText("?");
        proteinAmountJLabel.setText("?");
        fatAmountJLabel.setText("?");
        carbsAmountJLabel.setText("?");

        commentOptionalTextField.setText("");
        productNameTextField.setText("");
        dayMealNameComboBox.setSelectedItem("None");
        productSuggestionNameComboBox.setSelectedItem(null);

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
                dayInCalendar.getDayAmountOfProduct(), dayInCalendar.getDayDateDayName(), dayInCalendar.getMealName() ,dayInCalendar.getDayProductProduct(),
                dayInCalendar.getConsumedMacro(), dayInCalendar);
        try {
            InsertToCalendarDayTable.addRowToCalendarTable(dayInCalendar);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        //<editor-fold desc="Prepare and Execute update data for selected month">
        String addProductToDayDisplaySelectedFDateDayLabelString = addProductToDayDisplaySelectedFDateDayLabel.getText();
        String monthInNumberString = "" + addProductToDayDisplaySelectedFDateDayLabelString.charAt(5) + addProductToDayDisplaySelectedFDateDayLabelString.charAt(6);

        if (monthInNumberString.charAt(0) == '0'){
            monthInNumberString = "" +  monthInNumberString.charAt(1);
        }
        int monthInNumberInt = Integer.valueOf(monthInNumberString);

        String yearInString =  addProductToDayDisplaySelectedFDateDayLabelString.substring(0,4);
        int yearInNumber = Integer.valueOf(yearInString);

        String numberOfDay = "" + addProductToDayDisplaySelectedFDateDayLabelString.charAt(8) + addProductToDayDisplaySelectedFDateDayLabelString.charAt(9);
        int numberOfDayInt;
        if(numberOfDay.charAt(0) == '0'){
            numberOfDayInt = Integer.parseInt("" + numberOfDay.charAt(1));
        }

        numberOfDayInt = Integer.parseInt(numberOfDay);
        RunnerFullUpdateDayStatistics.runFullUpdateForAllOneMonthInDayStatistics(numberOfDayInt, monthInNumberInt, yearInNumber);
        //</editor-fold>

        JOptionPane.showMessageDialog(null, "Product has been added. Date: " +
                addProductToDayDisplaySelectedFDateNameDayLabel.getText()
                + " \nDay name: " + addProductToDayDisplaySelectedFDateDayLabel.getText() +
                chosenCalendarTableLabel.getText());
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

    public void mealNameAcceptManager(){
        if (dayMealNameComboBox.getSelectedItem().toString().equals("None")){
            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want add product with -None- meal name?", "Right meal name?",  JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION)
            {
                acceptProduct();
            }else {
                JOptionPane.showMessageDialog(null,"Back to adding product");
            }
        }else {
            acceptProduct();
        }

    }

    public void setUpMacroTable(){
        Macro macroToDisplay = SelectFromDaysStatistics.getMacroFromDaysStatisticsByDate(MyDate.getCurrentDayInSQLFormat());
        macroTable.setValueAt("amount_of_points_from_notepad", 0,0);
        macroTable.setValueAt("--2", 0,1);
        macroTable.setValueAt("amount_of_filled_points_from_notepad", 1,0);
        macroTable.setValueAt("--4", 1,1);
        macroTable.setValueAt("kcal_consume", 2,0);
        macroTable.setValueAt(macroToDisplay.getKcal(), 2,1);
        macroTable.setValueAt("protein_consume", 3,0);
        macroTable.setValueAt(macroToDisplay.getProtein(), 3,1);
        macroTable.setValueAt("fat_consume", 4,0);
        macroTable.setValueAt(macroToDisplay.getFat(), 4,1);
        macroTable.setValueAt("carbs_consume", 5,0);
        macroTable.setValueAt(macroToDisplay.getCarbs(), 5,1);





    }
    //<editor-fold desc="Action Listeners Classes">
    private class AddProductToDayAcceptButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mealNameAcceptManager();
        }

    }

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

        private static class dialogWindowPanelMouseListener implements MouseListener {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getSource().getClass() == clickedButton.getClass()) {
                    clickedButton = (JButton) e.getSource();

                    if (!clickedButton.getText().equals("Accept new date")) {
                        if (clickedButton.getText().equals("2024") || clickedButton.getText().equals("2025")){
                            otherDataTextField.setText(clickedButton.getText());
                        }else {
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
                            dialogWindowPanel.disable();
                            otherThenCurrentDateButtonWindowFrame.dispose();
                        }
                    }
                }
            }

            private void changeDateOnCalendarWindowGUI(){
                Format format = new SimpleDateFormat("EEEE");

                String otherThanCurrentDateString = otherDataTextField.getText();
                String dayNameOtherDate = String.valueOf(otherThanCurrentDateString.charAt(8)) + String.valueOf(otherThanCurrentDateString.charAt(9));
                otherThanCurrentDateString = String.valueOf(otherThanCurrentDateString.charAt(5)) + String.valueOf(otherThanCurrentDateString.charAt(6));

                if (String.valueOf(dayNameOtherDate.charAt(0)).equals("0")){
                    dayNameOtherDate = String.valueOf(dayNameOtherDate.charAt(1));
                }
                int dayNameOtherDateInt = Integer.valueOf(dayNameOtherDate);

                if (String.valueOf(otherThanCurrentDateString.charAt(0)).equals("0")){
                    otherThanCurrentDateString = String.valueOf(otherThanCurrentDateString.charAt(1));
                }
                int month = Integer.valueOf(otherThanCurrentDateString);

                String dateInString = otherDataTextField.getText();
                String year = "";
                int yearInInt = 0;
                if(dateInString.substring(0,4).equals("2025")){
                    yearInInt = 2025;
                }

                if(dateInString.substring(0,4).equals("2024")){
                    yearInInt = 2024;
                }

                java.util.Date utilDateImport = new GregorianCalendar(yearInInt, month-1, dayNameOtherDateInt).getTime();;
                String dayNameCurrentDateOnStartWindow = format.format(utilDateImport);
                addProductToDayDisplaySelectedFDateNameDayLabel.setText(dayNameCurrentDateOnStartWindow);
                addProductToDayDisplaySelectedFDateDayLabel.setText(otherDataTextField.getText());
            }
            private boolean checkIfDateIsCorrect() {
                String dateToCheck = otherDataTextField.getText();
                System.out.println(dateToCheck);
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
            System.out.println();
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

    private class InputCurrentDayButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addProductToDayDisplaySelectedFDateDayLabel.setText(MyDate.getCurrentDayInSQLFormat());
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
            System.out.println(productInfoInString);

            JOptionPane.showMessageDialog(null, "Full product info: " + productInfoInString);
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

    private class CheckDaysStatisticFilledTableActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog checkDaysStatisticsDialog = new JDialog();

            checkDaysStatisticsDialog.setLayout(checkDaysStatisticsDialogGridLayout);
            checkDaysStatisticsDialog.add(checkDaysStatisticsDialogMonthsComboBox);

            for (int i = 0; i < 120; i++) {
                checkDaysStatisticsDialog.add(new JButton(String.valueOf(i)));
            }
            checkDaysStatisticsDialog.setName("Check days Statistics");
            checkDaysStatisticsDialog.setLocationRelativeTo(null);
            checkDaysStatisticsDialog.setSize(1000,800);
            checkDaysStatisticsDialog.setResizable(true);
            checkDaysStatisticsDialog.setVisible(true);
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
                String allRightNamesProductsString = "";
                JOptionPane.showMessageDialog(null, searchAllProductWith(productNameTextField.getText()));

                for (int i = 0; i < allRightNamesProductsArray.length; i++) {
                    if (allRightNamesProductsArray[i] != null) {
                        productSuggestionNameComboBox.addItem(allRightNamesProductsArray[i]);
                    }
                }
                //productSuggestionNameComboBox.addItem(searchAllProductWith(productNameTextField.getText()));
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

            if (e.getKeyCode() == KeyEvent.VK_LEFT){
                selectBackItemInMealComboBox();
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                selectNextItemInMealComboBox();
            }
        }

        public void selectNextItemInMealComboBox(){
            int productIndex = dayMealNameComboBox.getSelectedIndex();
            int comboBoxItemCount = dayMealNameComboBox.getItemCount();

            int nextProductIndex = productIndex + 1;

            if (nextProductIndex <  comboBoxItemCount) {
                dayMealNameComboBox.setSelectedIndex(nextProductIndex);
            }else {
                dayMealNameComboBox.setSelectedIndex(0);
            }
        }

        public void selectBackItemInMealComboBox(){
            int productIndex = dayMealNameComboBox.getSelectedIndex();
            int comboBoxItemCount = dayMealNameComboBox.getItemCount();

            int nextProductIndex = productIndex - 1;
            

            if (nextProductIndex <= -1) {
                dayMealNameComboBox.setSelectedIndex(comboBoxItemCount - 1);
            }else {
                dayMealNameComboBox.setSelectedIndex(nextProductIndex);
            }
        }

        public void selectNextItemInComboBox(){
            int productIndex = productSuggestionNameComboBox.getSelectedIndex();
            int comboBoxItemCount = productSuggestionNameComboBox.getItemCount();

            int nextProductIndex = productIndex + 1;

            if (nextProductIndex <  comboBoxItemCount) {
                productSuggestionNameComboBox.setSelectedIndex(nextProductIndex);
            }else {
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
            }
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

            shortcutsDialog.setSize(300,300);

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

}
