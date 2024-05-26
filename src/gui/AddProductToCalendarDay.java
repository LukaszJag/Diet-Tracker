package gui;

import tools.calendar_tools.DayInCalendar;
import configuration.Config;
import tools.products_tools.Macro;
import tools.products_tools.Product;
import tools.sql_tools.calendar.InsertToCalendarDayTable;
import tools.sql_tools.SQLSelect;
import tools.text_files_tools.FilesTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    // Buttons
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
    JLabel timeOptionalLabel = new JLabel("Time(optional):");
    JLabel commentOptionalLabel = new JLabel("Comment(optional):");
    JLabel chosenCalendarTableLabel = new JLabel();
    JLabel shortcutWordLabel = new JLabel("SHORTCUTS:");
    JLabel shortcutsCRTTipsLabel = new JLabel("CTRL - Search product by name");
    JLabel shortcutsDOWNArrowTipsLabel = new JLabel("Down arrow - Fill selected name");
    JLabel shortcutsUPArrowTipsLabel = new JLabel("Up arrow - Fill macro for product");
    JLabel shortcutsCRTLAmountOfProductTipsLabel = new JLabel("CTRL (Amount...) - Accept Data");

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

    JComboBox<String> productSuggestionNameComboBox = new JComboBox<>(new String[]{""});
    //</editor-fold>

    //<editor-fold desc="Grid Layout">
    GridLayout gridLayoutMainPanel = new GridLayout(12, 2, 10, 10);
    //</editor-fold>

    //</editor-fold>

    //<editor-fold desc="Starting Constructors">
    // Starting Constructor
    public AddProductToCalendarDay() {
        startAddProductToDayWindow();
    }
    //</editor-fold>

    //<editor-fold desc="Main Methods">
    private void setFrame() {
        // Set window size
        addProductToDayFrame.setSize(Config.ADD_PRODUCT_TO_DAY_WINDOWS_WIDTH, Config.ADD_PRODUCT_TO_DAY_WINDOWS_HEIGHT);
        addProductToDayFrame.setLayout(new BorderLayout());
    }

    private void setPanels() {
        //Set Layout
        addProductToDayPanelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addProductToDayPanelMain.setLayout(gridLayoutMainPanel);

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
        addProductToDayPanelWest.add(inputCurrentDayButton);
        inputCurrentDayButton.addActionListener(new inputCurrentDayButtonActionListener());

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

        Color labelsColor = Color.yellow;
        shortcutWordLabel.setForeground(Color.RED);
        shortcutsCRTTipsLabel.setForeground(labelsColor);
        shortcutsDOWNArrowTipsLabel.setForeground(labelsColor);
        shortcutsUPArrowTipsLabel.setForeground(labelsColor);

        addProductToDayPanelEast.add(shortcutWordLabel);
        addProductToDayPanelEast.add(shortcutsCRTTipsLabel);
        addProductToDayPanelEast.add(shortcutsDOWNArrowTipsLabel);
        addProductToDayPanelEast.add(shortcutsUPArrowTipsLabel);
        addProductToDayPanelEast.add(shortcutsCRTLAmountOfProductTipsLabel);


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
        addProductToDayPanelMain.add(kcalTextField);

        addProductToDayPanelMain.add(proteinLabel);
        addProductToDayPanelMain.add(proteinLTextField);

        addProductToDayPanelMain.add(fatLabel);
        addProductToDayPanelMain.add(fatTextField);

        addProductToDayPanelMain.add(carbsLabel);
        addProductToDayPanelMain.add(carbsTextField);

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
        addProductToDayFrame.setResizable(false);
        addProductToDayFrame.setLocationRelativeTo(null);
        addProductToDayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            kcalTextField.setText(resultOfCheckIfProductExist[4]);
            proteinLTextField.setText(resultOfCheckIfProductExist[5]);
            fatTextField.setText(resultOfCheckIfProductExist[6]);
            carbsTextField.setText(resultOfCheckIfProductExist[7]);

            String productData = "Product name:    " + resultOfCheckIfProductExist[0] + "\nKcal:    " + resultOfCheckIfProductExist[4]
                    + "\nProtein:    " + resultOfCheckIfProductExist[5] + "\nFat:    " + resultOfCheckIfProductExist[6]
                    + "\nCarbs:    " + resultOfCheckIfProductExist[7];
            return true;
        } else {
            return false;

        }
    }

    //</editor-fold>

    //<editor-fold desc="Action Listeners Classes">
    private class AddProductToDayAcceptButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            float amountOfProductInGrams = -1;
            try {
                amountOfProductInGrams = Float.valueOf(amountOfProductTextField.getText());
            } catch (Exception ex) {
                System.out.println("Error: Cannot parse data from amountOfProductTextField[String]->[Float]");
            }

            Product dayInCalendarProduct = getDayProductFromGUI();
            Macro consumedMacro = calculateConsumedMacro(dayInCalendarProduct, amountOfProductInGrams);
            DayInCalendar dayInCalendar = getDayInCalendarFromDataInGUI(dayInCalendarProduct, consumedMacro);

            FilesTools.sendSQLQueryToTxtFile(dayInCalendar, addProductToDayDisplaySelectedFDateDayLabel.getText(), amountOfProductTextField.getText());

            try {
                InsertToCalendarDayTable.addRowToCalendarTable(dayInCalendar);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            JOptionPane.showMessageDialog(null, "Product has been added. Date: " +
                    addProductToDayDisplaySelectedFDateNameDayLabel.getText()
                    + " \nDay name: " + addProductToDayDisplaySelectedFDateDayLabel.getText() +
                    chosenCalendarTableLabel.getText());
        }

        public Product getDayProductFromGUI() {
            Macro productMacro = new Macro(
                    Float.valueOf(kcalTextField.getText()),
                    Float.valueOf(proteinLTextField.getText()),
                    Float.valueOf(fatTextField.getText()),
                    Float.valueOf(carbsTextField.getText()));
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

            java.util.Date datePassedToSQL;
            try {
                datePassedToSQL = new SimpleDateFormat("yyyy-MM-dd").parse(addProductToDayDisplaySelectedFDateDayLabel.getText());
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }

            //</editor-fold>


            float kcalConsumeCalculated = Float.valueOf(kcalTextField.getText()) * (Float.valueOf(amountOfProductTextField.getText()) / (100.0f));

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
                        if (clickedButton.getText().length() == 1) {
                            reulstString = otherDataTextField.getText() + "-" + "0" + clickedButton.getText();
                        } else {
                            reulstString = otherDataTextField.getText() + "-" + clickedButton.getText();
                        }
                        otherDataTextField.setText(reulstString);

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

                java.util.Date utilDateImport = new GregorianCalendar(2024, month-1, dayNameOtherDateInt).getTime();;
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
            MainWindow mainWindow = new MainWindow();
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

    private class inputCurrentDayButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "NOTHING HAPPENED");
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
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                String suggestionFromComboBoxString = productSuggestionNameComboBox.getSelectedItem().toString();
                productNameTextField.setText(suggestionFromComboBoxString);
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                isProductExist();
            }
        }

        public String[] searchAllProductWith(String wordToSearch) {
            String[] allProductArray;
            String[] resultArray = new String[100];

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
                acceptProduct();
            }
        }

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

            FilesTools.sendSQLQueryToTxtFile(dayInCalendar, addProductToDayDisplaySelectedFDateDayLabel.getText(), amountOfProductTextField.getText());

            try {
                InsertToCalendarDayTable.addRowToCalendarTable(dayInCalendar);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            JOptionPane.showMessageDialog(null, "Product has been added. Date: " +
                    addProductToDayDisplaySelectedFDateNameDayLabel.getText()
                    + " \nDay name: " + addProductToDayDisplaySelectedFDateDayLabel.getText() +
                    chosenCalendarTableLabel.getText());
        }

        public Product getDayProductFromGUI() {
            Macro productMacro = new Macro(
                    Float.valueOf(kcalTextField.getText()),
                    Float.valueOf(proteinLTextField.getText()),
                    Float.valueOf(fatTextField.getText()),
                    Float.valueOf(carbsTextField.getText()));
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

            java.util.Date datePassedToSQL;
            try {
                datePassedToSQL = new SimpleDateFormat("yyyy-MM-dd").parse(addProductToDayDisplaySelectedFDateDayLabel.getText());
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }

            //</editor-fold>


            float kcalConsumeCalculated = Float.valueOf(kcalTextField.getText()) * (Float.valueOf(amountOfProductTextField.getText()) / (100.0f));

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

    }
}
