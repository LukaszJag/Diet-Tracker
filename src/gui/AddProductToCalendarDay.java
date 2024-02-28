package gui;

import configuration.Config;
import sql_tools.SQLSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class AddProductToCalendarDay {

    // Panels
    JPanel addProductToDayPanelMain = new JPanel();
    JPanel addProductToDayPanelNorth = new JPanel();
    JPanel addProductToDayPanelWest = new JPanel();
    JPanel addProductToDayPanelEast = new JPanel();
    JPanel addProductToDayPanelSouth = new JPanel();

    // Frame
    JFrame addProductToDayFrame;

    // Buttons
    JButton addProductToDayAcceptButton = new JButton("Accept");
    JButton inputCurrentDayButton = new JButton("Input  current day");
    JButton checkIfProductExistButton = new JButton("Check Product existing");
    JButton displayProductMacroButton = new JButton("Display Product Macro");
    JButton importProductMacroFromLibraryButton = new JButton("Import product macro");

    // Labels
    JLabel addProductToDayCurrentDateTextLabel = new JLabel("CURRENT DATE:");
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

    // TextFields
    JTextField dateTextField = new JTextField();
    JTextField dayNameLTextField= new JTextField();
    JTextField productNameTextField = new JTextField();
    JTextField amountOfProductTextField = new JTextField();
    JTextField kcalTextField = new JTextField();
    JTextField proteinLTextField= new JTextField();
    JTextField fatTextField = new JTextField();
    JTextField carbsTextField = new JTextField();
    JTextField timeOptionalTextField = new JTextField();
    JTextField commentOptionalTextField = new JTextField();
    // ComboBox
    JComboBox dayNameComboBox = new JComboBox<>(new String[]{"Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});

    public AddProductToCalendarDay(){
        startAddProductToDayWindow();
    }

    private void startAddProductToDayWindow() {
        addProductToDayFrame = new JFrame("Add Product To Day");
        // Set window size
        addProductToDayFrame.setSize(Config.ADD_PRODUCT_TO_DAY_WINDOWS_WIDTH, Config.ADD_PRODUCT_TO_DAY_WINDOWS_HEIGHT);

        addProductToDayPanelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridLayout gridLayoutMainPanel = new GridLayout(12, 2, 10, 10);
        //Set Layout
        addProductToDayPanelMain.setLayout(gridLayoutMainPanel);
        addProductToDayFrame.setLayout(new BorderLayout());

        // Set colors
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

        // Add Buttons
        addProductToDayPanelWest.add(inputCurrentDayButton);
        addProductToDayPanelEast.add(checkIfProductExistButton);
        checkIfProductExistButton.addActionListener(new CheckIfProductExistButtonActionListener());

        addProductToDayPanelEast.add(displayProductMacroButton);
        addProductToDayPanelEast.add(importProductMacroFromLibraryButton);
        addProductToDayPanelSouth.add(addProductToDayAcceptButton);

        // Add Components to Center Panel
        addProductToDayPanelMain.add(dateLabel);
        addProductToDayPanelMain.add(new JButton("Other then current"));

        addProductToDayPanelMain.add(dayNameLabel);
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




        // Add Panels to Frame
        addProductToDayFrame.add(addProductToDayPanelNorth, BorderLayout.NORTH);
        addProductToDayFrame.add(addProductToDayPanelWest, BorderLayout.WEST);
        addProductToDayFrame.add(addProductToDayPanelMain, BorderLayout.CENTER);
        addProductToDayFrame.add(addProductToDayPanelEast, BorderLayout.EAST);
        addProductToDayFrame.add(addProductToDayPanelSouth, BorderLayout.SOUTH);

        // Add Labels
        addProductToDayCurrentDateTextLabel.setForeground(Config.dateTimeLabels);
        addProductToDayCurrentDateLabel.setForeground(Config.dateTimeLabels);

        addProductToDayCurrentDateLabel.setText(new SimpleDateFormat("dd-MM-yyyy").format(Config.date));


        addProductToDayPanelNorth.add(addProductToDayCurrentDateTextLabel);
        addProductToDayPanelNorth.add(addProductToDayCurrentDateLabel);

        System.out.println(addProductToDayPanelMain.getLayout());
        addProductToDayFrame.setResizable(false);
        addProductToDayFrame.setLocationRelativeTo(null);
        addProductToDayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addProductToDayFrame.setVisible(true);
    }

    private class AddProductToDayAcceptButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Accept Button Has Been Pressed");
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
}
