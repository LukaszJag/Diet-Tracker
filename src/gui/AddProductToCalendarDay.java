package gui;

import calendar_tools.DayInCalendar;
import configuration.Config;
import products_tools.Macro;
import products_tools.Product;
import sql_tools.InsertToCalendarDayTable;
import sql_tools.SQLSelect;
import text_files_tools.FilesTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddProductToCalendarDay {

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
    JButton changeToCalendarMainTableButton = new JButton("Chose - Calendar Table");
    JButton changeToCalendarTestTableButton = new JButton("Chose - Calendar TEST Table");
    //</editor-fold>

    //<editor-fold desc="Labels">
    // Labels
    JLabel addProductToDayCurrentDateTextLabel = new JLabel("CURRENT DATE:");
    JLabel addProductToDayDisplaySelectedDay = new JLabel("Selected date: ");
    JLabel addProductToDayDisplaySelectedFDateDayLabel = new JLabel();
    JLabel addProductToDayDisplaySelectedFDateNameDayLabel = new JLabel();
    JLabel addProductToDayCurrentDateLabel = new JLabel("dd.mm.yyyy");
    JLabel dateLabel = new JLabel("Date:");
    JLabel dayMealNameLabel = new JLabel("Meal name(IN PROGRESS):");
    JLabel productNameLabel = new JLabel("Product name:");
    JLabel amountOfProductLabel = new JLabel("Amount of product:");
    JLabel kcalLabel = new JLabel("Kcal:");
    JLabel proteinLabel = new JLabel("Protein:");
    JLabel fatLabel = new JLabel("Fat:");
    JLabel carbsLabel = new JLabel("Carbs:");
    JLabel timeOptionalLabel = new JLabel("Time(optional):");
    JLabel commentOptionalLabel = new JLabel("Comment(optional):");
    JLabel chosenCalendarTableLabel = new JLabel();
    //</editor-fold>

    //<editor-fold desc="TextFields">
    // TextFields
    JTextField dateTextField = new JTextField();
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
    JComboBox<String> dayMealNameComboBox = new JComboBox<>(new String[]{"None" , "Breakfast", "Second Breakfast", "Snack 1", "Dinner", "Snack 2"
            , "Supper", "After workout"});
    //</editor-fold>

    //<editor-fold desc="Grid Layout">
    GridLayout gridLayoutMainPanel = new GridLayout(12, 2, 10, 10);
    //</editor-fold>

    //<editor-fold desc="Starting Constructor">
    // Starting Constructor
    public AddProductToCalendarDay() {
        startAddProductToDayWindow();
    }
    //</editor-fold>

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

        addProductToDayPanelEast.add(displayProductMacroButton);
        addProductToDayPanelEast.add(importProductMacroFromLibraryButton);

        addProductToDayPanelEast.add(fillTheExistingProductMacroButton);
        fillTheExistingProductMacroButton.addActionListener(new FillTheExistingProductMacroButtonListener());

        addProductToDayPanelEast.add(changeToCalendarMainTableButton);
        changeToCalendarMainTableButton.addActionListener(new ChangeCalendarTableButtonListener());

        addProductToDayPanelEast.add(changeToCalendarTestTableButton);
        changeToCalendarTestTableButton.addActionListener(new ChangeCalendarTableToCalendarTestButtonListener());

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

        addProductToDayPanelMain.add(amountOfProductLabel);
        addProductToDayPanelMain.add(amountOfProductTextField);

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

    // Main Method
    private void startAddProductToDayWindow() {

        setFrame();
        setPanels();
        addComponentsToPanels();
        addPanelsToFrame();
        finishSetUpFrame();
    }

    private class AddProductToDayAcceptButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("\n" + dayMealNameComboBox.getSelectedItem().toString() + "\n");
            float amountOfProductInGrams = -1;
            try {
                amountOfProductInGrams = Float.valueOf(amountOfProductTextField.getText());
            }catch (Exception ex){
                System.out.println("Error: Cannot parse data from amountOfProductTextField[String]->[Float]");
            }

            Product dayInCalendarProduct = getDayProductFromGUI();
            Macro consumedMacro = calculateConsumedMacro(dayInCalendarProduct, amountOfProductInGrams);
            DayInCalendar dayInCalendar = getDayInCalendarFromDataInGUI(dayInCalendarProduct, consumedMacro);

            sendSQLQueryToTxtFile(dayInCalendar);

            try {
                InsertToCalendarDayTable.addRowToCalendarTable(dayInCalendar);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            JOptionPane.showMessageDialog(null, "Product has been added");
        }

        public Product getDayProductFromGUI(){
            Macro productMacro = new Macro(
                    Float.valueOf(kcalTextField.getText()),
                    Float.valueOf(proteinLTextField.getText()),
                    Float.valueOf(fatTextField.getText()),
                    Float.valueOf(carbsTextField.getText()));
            Product dayInCalendarProduct = new Product(productNameTextField.getText(), "None",
                    100, productMacro, -1,"");

            return dayInCalendarProduct;
        }

        public DayInCalendar getDayInCalendarFromDataInGUI(Product productFromGUI, Macro consumedMacro){
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
                    dayAmountOfProduct,  productFromGUI, productFromGUI.getProductMacroForItsSetMeasure(), dayProductOptionalTime, dayProductOptionalComment, consumedMacro);

            return dayInCalendar;
        }

        public void sendSQLQueryToTxtFile(DayInCalendar dayInCalendar){

            try {
                String nameAndPathOfFile = addProductToDayDisplaySelectedFDateDayLabel.getText() + "_" + dayInCalendar.getDayProductProduct().getProductName() + "_" + String.valueOf(amountOfProductTextField.getText());nameAndPathOfFile = nameAndPathOfFile.replace(" ", "_");
                FilesTools.writeSQLStatementForDayInCalendarToTXTFile(nameAndPathOfFile,dayInCalendar);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }

        public Macro calculateConsumedMacro(Product productToCalculateConsumedMacro, float amountOfProductInGram){
            float amountOfProductToCalculate = amountOfProductInGram/100;
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
                JOptionPane.showMessageDialog(null, "Product data has been filled:\n " + productData);
            } else {
                JOptionPane.showMessageDialog(null, "Product doesn't exist");
            }
        }
    }

    private class OtherThenCurrentDateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame otherThenCurrentDateButtonWindowFrame = new JFrame("frame");

            JPanel dialogWindowPanel = new JPanel();
            JLabel otherDateExampleLabel = new JLabel("Input date in yyyy-MM-dd");
            JTextField otherDataTextField = new JTextField(20);
            JButton dialogWindowAcceptButton = new JButton("Accept new date");

            // dialogWindowPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            // dialogWindowPanel.setLayout(new GridLayout(3,3));
            // add actionlistener to button
            dialogWindowAcceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    addProductToDayDisplaySelectedFDateDayLabel.setText(otherDataTextField.getText());

                    System.out.println("\n Date has been change to: ");
                    System.out.println(otherDataTextField.getText() + "\n");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date newDate;

                    try {
                        newDate = formatter.parse(otherDataTextField.getText());
                    } catch (ParseException ex) {
                        System.out.println("Wrong date format");
                        JOptionPane.showMessageDialog(null, "Wrong date input");
                        throw new RuntimeException(ex);
                    }

                    Format newDateformat = new SimpleDateFormat("EEEE");
                    String dayNameCurrentDateOnStartWindow = newDateformat.format(newDate);
                    addProductToDayDisplaySelectedFDateNameDayLabel.setText(dayNameCurrentDateOnStartWindow);
                    JOptionPane.showMessageDialog(null, "Date has been changed");

                }
            });

            dialogWindowPanel.add(otherDateExampleLabel);
            dialogWindowPanel.add(otherDataTextField);
            dialogWindowPanel.add(dialogWindowAcceptButton);
            //dialogWindowPanel.add(otherDateExampleLabel);
            //dialogWindowPanel.add(otherDataTextField);
            //dialogWindowPanel.add(dialogWindowAcceptButton);

            otherThenCurrentDateButtonWindowFrame.add(dialogWindowPanel);
            otherThenCurrentDateButtonWindowFrame.setSize(500, 200);
            otherThenCurrentDateButtonWindowFrame.setResizable(false);
            otherThenCurrentDateButtonWindowFrame.setLocationRelativeTo(null);
            otherThenCurrentDateButtonWindowFrame.show();
        }
    }

    private class BackToMainWindowButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addProductToDayFrame.dispose();
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
}
