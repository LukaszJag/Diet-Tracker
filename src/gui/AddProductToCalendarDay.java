package gui;

import calendar_tools.DayInCalendar;
import configuration.Config;
import products_tools.Macro;
import products_tools.Product;
import sql_tools.InsertToCalendarDayTable;
import sql_tools.SQLSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    //</editor-fold>

    //<editor-fold desc="Labels">
    // Labels
    JLabel addProductToDayCurrentDateTextLabel = new JLabel("CURRENT DATE:");
    JLabel addProductToDayDisplaySelectedDay = new JLabel("Selected date: ");
    JLabel addProductToDayCurrentDateLabel = new JLabel("dd.mm.yyyy");
    JLabel dateLabel = new JLabel("Date:");
    JLabel dayNameLabel = new JLabel("Day name:");
    JLabel productNameLabel = new JLabel("Product name:");
    JLabel amountOfProductLabel = new JLabel("Amount of product:");
    JLabel kcalLabel = new JLabel("Kcal:");
    JLabel proteinLabel = new JLabel("Protein:");
    JLabel fatLabel = new JLabel("Fat:");
    JLabel carbsLabel = new JLabel("Carbs:");
    JLabel timeOptionalLabel = new JLabel("Time(optional):");
    JLabel commentOptionalLabel = new JLabel("Comment(optional):");
    //</editor-fold>

    //<editor-fold desc="TextFields">
    // TextFields
    JTextField dateTextField = new JTextField();
    JTextField productNameTextField = new JTextField();
    JTextField amountOfProductTextField = new JTextField();
    JTextField kcalTextField = new JTextField();
    JTextField proteinLTextField= new JTextField();
    JTextField fatTextField = new JTextField();
    JTextField carbsTextField = new JTextField();
    JTextField timeOptionalTextField = new JTextField();
    JTextField commentOptionalTextField = new JTextField();
    //</editor-fold>

    //<editor-fold desc="ComboBox">
    // ComboBox
    JComboBox<String> dayNameComboBox = new JComboBox<>(new String[]{"Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});
    //</editor-fold>

    //<editor-fold desc="Grid Layout">
    GridLayout gridLayoutMainPanel = new GridLayout(12, 2, 10, 10);
    //</editor-fold>

    //<editor-fold desc="Starting Constructor">
    // Starting Constructor
    public AddProductToCalendarDay(){
        startAddProductToDayWindow();
    }
    //</editor-fold>

    private void setFrame(){
        // Set window size
        addProductToDayFrame.setSize(Config.ADD_PRODUCT_TO_DAY_WINDOWS_WIDTH, Config.ADD_PRODUCT_TO_DAY_WINDOWS_HEIGHT);
        addProductToDayFrame.setLayout(new BorderLayout());
    }

    private void setPanels(){
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

    private void addComponentsToPanels(){

        //<editor-fold desc="Add Components to Panel - North">

        addProductToDayCurrentDateTextLabel.setForeground(Config.dateTimeLabels);
        addProductToDayPanelNorth.add(addProductToDayCurrentDateTextLabel);

        addProductToDayCurrentDateLabel.setForeground(Config.addProductToDayCurrentDateLabelColor);
        addProductToDayCurrentDateLabel.setText(new SimpleDateFormat("dd-MM-yyyy").format(Config.date));
        addProductToDayPanelNorth.add(addProductToDayCurrentDateLabel);
        //</editor-fold>

        //<editor-fold desc="Add Components to Panel - West">
        // Add Buttons
        addProductToDayPanelWest.add(inputCurrentDayButton);
        addProductToDayDisplaySelectedDay.setForeground(Config.addProductToDayCurrentDateLabelColor);
        addProductToDayPanelWest.add(addProductToDayDisplaySelectedDay);
        //</editor-fold>

        //<editor-fold desc="Add Components to Panel - East">
        addProductToDayPanelEast.add(checkIfProductExistButton);
        checkIfProductExistButton.addActionListener(new CheckIfProductExistButtonActionListener());

        addProductToDayPanelEast.add(displayProductMacroButton);
        addProductToDayPanelEast.add(importProductMacroFromLibraryButton);

        addProductToDayPanelEast.add(fillTheExistingProductMacroButton);
        fillTheExistingProductMacroButton.addActionListener(new FillTheExistingProductMacroButtonListener());
        //</editor-fold>

        //<editor-fold desc="Add Components to Panel - South">
        addProductToDayPanelSouth.add(addProductToDayAcceptButton);
        addProductToDayAcceptButton.addActionListener(new AddProductToDayAcceptButtonListener());
        //</editor-fold>

        //<editor-fold desc="Add Components to Center Panel">
        // Add Components to Center Panel
        addProductToDayPanelMain.add(dateLabel);
        addProductToDayPanelMain.add(otherThenCurrentDateButton);
        addProductToDayPanelMain.add(dayNameLabel);

        Format f = new SimpleDateFormat("EEEE");
        java.util.Date utilDate = new java.util.Date();
        String str = f.format(utilDate);
        dayNameComboBox.setSelectedItem(str);

        addProductToDayPanelMain.add(dayNameComboBox);

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

    private void addPanelsToFrame(){
        // Add Panels to Frame
        addProductToDayFrame.add(addProductToDayPanelNorth, BorderLayout.NORTH);
        addProductToDayFrame.add(addProductToDayPanelWest, BorderLayout.WEST);
        addProductToDayFrame.add(addProductToDayPanelMain, BorderLayout.CENTER);
        addProductToDayFrame.add(addProductToDayPanelEast, BorderLayout.EAST);
        addProductToDayFrame.add(addProductToDayPanelSouth, BorderLayout.SOUTH);

    }

    private void finishSetUpFrame(){
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
            // Getting from direct from TextFields: Macro, day
            Macro productMacro = new Macro(
                    Float.valueOf(kcalTextField.getText()),
                    Float.valueOf(proteinLTextField.getText()),
                    Float.valueOf(fatTextField.getText()),
                    Float.valueOf(carbsTextField.getText()));
            String dayDateInString = dateTextField.getText();
            String dayProductOptionalTime = timeOptionalTextField.getText();
            String dayProductOptionalComment = commentOptionalTextField.getText();
            float dayAmountOfProduct = Float.valueOf(amountOfProductTextField.getText());

            // Getting from ComboBox
            String dayDateDayName = dayNameComboBox.getSelectedItem().toString();

            // Set passing date to current
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println(LocalDateTime.now().format(formatter));
            Date dayDate = Date.valueOf(LocalDateTime.now().format(formatter));

            System.out.println(productNameTextField.getText());

            Product dayProductProduct = new Product(productNameTextField.getText(), "None",
                    100, productMacro,-1);

            float kcalConsumeCalculated = Float.valueOf(kcalTextField.getText()) * (Float.valueOf(amountOfProductTextField.getText()) / (100.0f));

            DayInCalendar dayInCalendar = new DayInCalendar(dayDate, dayDateInString, dayDateDayName, dayAmountOfProduct,
                    dayProductProduct, productMacro ,dayProductOptionalTime, dayProductOptionalComment, kcalConsumeCalculated);

            System.out.println("AddProductToCalendarDay -> AddProductToDayAcceptButtonListener:");
            DayInCalendar.dayDataShowData(dayInCalendar);

            try {
                InsertToCalendarDayTable.addRowToCalendarTable(dayInCalendar, kcalConsumeCalculated);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(null, "Product has been added");
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

            for (String pos: resultOfCheckIfProductExist){
                if (pos != null) {
                    isExist = true;
                    break;
                }
            }

            if(isExist){
                String productData =  "Product name:    " + resultOfCheckIfProductExist[0] + "\nKcal:    " + resultOfCheckIfProductExist[4]
                        + "\nProtein:    " + resultOfCheckIfProductExist[5] + "\nFat:    " + resultOfCheckIfProductExist[6] + "\nCarbs:    " + resultOfCheckIfProductExist[7];
                JOptionPane.showMessageDialog(null, "Product data:\n " + productData);
            }else {
                JOptionPane.showMessageDialog(null,"Product doesn't exist");
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

            for (String pos: resultOfCheckIfProductExist){
                if (pos != null) {
                    isExist = true;
                    break;
                }
            }

            if(isExist){
                // Filling text fields
                kcalTextField.setText(resultOfCheckIfProductExist[4]);
                proteinLTextField.setText(resultOfCheckIfProductExist[5]);
                fatTextField.setText(resultOfCheckIfProductExist[6]);
                carbsTextField.setText(resultOfCheckIfProductExist[7]);

                String productData =  "Product name:    " + resultOfCheckIfProductExist[0] + "\nKcal:    " + resultOfCheckIfProductExist[4]
                        + "\nProtein:    " + resultOfCheckIfProductExist[5] + "\nFat:    " + resultOfCheckIfProductExist[6]
                        + "\nCarbs:    " + resultOfCheckIfProductExist[7];
                JOptionPane.showMessageDialog(null, "Product data has been filled:\n " + productData);
            }else {
                JOptionPane.showMessageDialog(null,"Product doesn't exist");
            }
        }
    }
}
