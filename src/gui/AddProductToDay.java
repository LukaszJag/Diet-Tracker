package gui;

import configuration.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class AddProductToDay {
    JPanel addProductToDayMainPanel = new JPanel();
    JPanel addProductToDayPanelNorth = new JPanel();
    JPanel addProductToDayPanelWest = new JPanel();
    JPanel addProductToDayPanelEast = new JPanel();
    JPanel addProductToDayPanelSouth = new JPanel();
    JFrame addProductToDayFrame;
    JButton addProductToDayAcceptButton = new JButton("Accept");
    JLabel addProductToDayCurrentDateTextLabel = new JLabel("CURRENT DATE:");

    JLabel addProductToDayCurrentDateLabel = new JLabel("dd.mm.yyyy");
    //Add buttons
    JButton inputCurrentDay = new JButton("Input  current day");
    JButton checkIfProductExist = new JButton("Check Product existing");
    JButton displayProductMacro = new JButton("Display Product Macro");
    JButton importProductMacroFromLibraryButton = new JButton("Import product macro");
    JLabel dayDate = new JLabel("Day Date");
    JComboBox dayNameComboBox = new JComboBox<>(new String[]{"Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});

    public AddProductToDay(){
        startAddProductToDayWindow();
    }

    private void startAddProductToDayWindow() {
        addProductToDayFrame = new JFrame("Add Product To Day");
        // Set window size
        addProductToDayFrame.setSize(Config.ADD_PRODUCT_TO_DAY_WINDOWS_WIDTH, Config.ADD_PRODUCT_TO_DAY_WINDOWS_HEIGHT);

        addProductToDayMainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridLayout gridLayoutMainPanel = new GridLayout(12, 2, 10, 10);
        //Set Layout
        addProductToDayMainPanel.setLayout(gridLayoutMainPanel);
        addProductToDayFrame.setLayout(new BorderLayout());

        // Set colors
        addProductToDayPanelNorth.setBackground(Color.BLACK);
        addProductToDayPanelSouth.setBackground(Color.GRAY);

        addProductToDayMainPanel.setBackground(Color.WHITE);

        addProductToDayPanelWest.setBackground(Color.DARK_GRAY);
        addProductToDayPanelEast.setBackground(Color.BLUE);

        // Set preferred size of panel
        addProductToDayPanelNorth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_NORTH_SIZE));
        addProductToDayPanelEast.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE));

        addProductToDayMainPanel.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER, Config.ADD_PRODUCT_TO_DAY_PANELS_CENTER));

        addProductToDayPanelWest.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_WEST_EAST_SIZE));
        addProductToDayPanelSouth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE, Config.ADD_PRODUCT_TO_DAY_PANELS_SOUTH_SIZE));

        // Add Buttons
        addProductToDayPanelWest.add(inputCurrentDay);
        addProductToDayPanelEast.add(checkIfProductExist);
        addProductToDayPanelEast.add(displayProductMacro);
        addProductToDayPanelEast.add(importProductMacroFromLibraryButton);
        addProductToDayPanelSouth.add(addProductToDayAcceptButton);

        // Add Components to Center Panel
        addProductToDayMainPanel.add(new JLabel("Date:"));
        addProductToDayMainPanel.add(new JButton("Other then current"));
        addProductToDayMainPanel.add(new JLabel("Day name:"));
        addProductToDayMainPanel.add(dayNameComboBox);
        addProductToDayMainPanel.add(new JLabel("Product name:"));
        addProductToDayMainPanel.add(new JTextField());

        addProductToDayMainPanel.add(new JLabel("Amount of product:"));
        addProductToDayMainPanel.add(new JTextField());
        addProductToDayMainPanel.add(new JLabel("Kcal:"));
        addProductToDayMainPanel.add(new JTextField());
        addProductToDayMainPanel.add(new JLabel("Protein:"));
        addProductToDayMainPanel.add(new JTextField());
        addProductToDayMainPanel.add(new JLabel("Fat:"));
        addProductToDayMainPanel.add(new JTextField());

        addProductToDayMainPanel.add(new JLabel("Carbs:"));
        addProductToDayMainPanel.add(new JTextField());
        addProductToDayMainPanel.add(new JLabel("Time(optional):"));
        addProductToDayMainPanel.add(new JTextField());
        addProductToDayMainPanel.add(new JLabel("Comment(optional):"));
        addProductToDayMainPanel.add(new JTextField());




        // Add Panels to Frame
        addProductToDayFrame.add(addProductToDayPanelNorth, BorderLayout.NORTH);
        addProductToDayFrame.add(addProductToDayPanelWest, BorderLayout.WEST);
        addProductToDayFrame.add(addProductToDayMainPanel, BorderLayout.CENTER);
        addProductToDayFrame.add(addProductToDayPanelEast, BorderLayout.EAST);
        addProductToDayFrame.add(addProductToDayPanelSouth, BorderLayout.SOUTH);

        // Add Labels
        addProductToDayCurrentDateTextLabel.setForeground(Config.dateTimeLabels);
        addProductToDayCurrentDateLabel.setForeground(Config.dateTimeLabels);

        addProductToDayCurrentDateLabel.setText(new SimpleDateFormat("dd-MM-yyyy").format(Config.date));


        addProductToDayPanelNorth.add(addProductToDayCurrentDateTextLabel);
        addProductToDayPanelNorth.add(addProductToDayCurrentDateLabel);

        System.out.println(addProductToDayMainPanel.getLayout());
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
}
