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
    JFrame addProductToDayFrame = new JFrame("Add Product To Day");
    JButton addProductToDayAcceptButton = new JButton("Accept");
    JLabel addProductToDayCurrentDateTextLabel = new JLabel("CURRENT DATE:");

    JLabel addProductToDayCurrentDateLabel = new JLabel("dd.mm.yyyy");

    public AddProductToDay(){
        startAddProductToDayWindow();
    }

    private void startAddProductToDayWindow() {
        // Set window size
        addProductToDayFrame.setSize(Config.ADD_PRODUCT_TO_DAY_WINDOWS_WIDTH, Config.ADD_PRODUCT_TO_DAY_WINDOWS_HEIGHT);

        //Set Layout
        addProductToDayMainPanel.setLayout(new GridLayout(8, 2));
        addProductToDayFrame.setLayout(new BorderLayout());

        // Add accept button
        //addProductToDayPanelSouth.add(addProductToDayAcceptButton);
        //addProductToDayAcceptButton.addActionListener(new AddProductToDayAcceptButton());

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

        addProductToDayFrame.setResizable(false);
        addProductToDayFrame.setLocale(null);
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
