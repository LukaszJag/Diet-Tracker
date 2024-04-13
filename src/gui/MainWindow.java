package gui;

import configuration.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    public MainWindow(){
        makeRunWindow();
    }
    private JFrame startWindow;
    private BorderLayout startWindowBorderLayout;
    private JPanel startWindowPanel = new JPanel();
    private JPanel panelLeft = new JPanel();
    private JPanel panelRight = new JPanel();
    private JPanel panelCenter = new JPanel();
    private JPanel panelUpper = new JPanel();
    private JPanel panelDown = new JPanel();
    JLabel currentCalendarTableLabel = new JLabel();
    JLabel currentProductTableLabel = new JLabel();

    public void makeRunWindow(){
        setUpAndStartMenuWindow();
    }

    private void setUpAndStartMenuWindow(){

        startWindow = new JFrame("Diet Tracker");
        startWindow.setSize(Config.START_WINDOWS_WIDTH, Config.START_WINDOWS_HEIGHT);
        startWindowBorderLayout = new BorderLayout();
        startWindow.setLayout(startWindowBorderLayout);
        startWindowPanel.setPreferredSize(new Dimension(100,40));

        setPanels();
        addButtons();
        addLabels();
        addPanelsToStartFrame();

        startWindow.setLocationRelativeTo(null);
        startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startWindow.setResizable(false);
        startWindow.setVisible(true);
    }

    private void addPanelsToStartFrame(){

        startWindow.add(panelLeft, BorderLayout.WEST);
        startWindow.add(panelRight, BorderLayout.EAST);
        startWindow.add(panelCenter, BorderLayout.CENTER);
        startWindow.add(panelUpper, BorderLayout.NORTH);
        startWindow.add(panelDown, BorderLayout.SOUTH);
    }

    private void setPanels(){
        panelLeft.setBackground(Color.BLACK);
        panelRight.setBackground(Color.GRAY);
        panelCenter.setBackground(Color.DARK_GRAY);
        panelUpper.setBackground(Color.BLUE);
        panelDown.setBackground(Color.WHITE);

        panelLeft.setPreferredSize(new Dimension(150,40));
        panelRight.setPreferredSize(new Dimension(150,100));
        panelCenter.setPreferredSize(new Dimension(100,100));
        panelUpper.setPreferredSize(new Dimension(100,50));
        panelDown.setPreferredSize(new Dimension(100,25));
    }

    private void addButtons(){
        Button addProductButton = new Button("Add new product");
        addProductButton.setBackground(Color.GREEN);

        addProductButton.addActionListener(new AddProductButton());
        panelLeft.add(addProductButton);

        Button mealManagerButton = new Button("Meal manager");
        mealManagerButton.addActionListener(new MealMangerButton());
        panelLeft.add(mealManagerButton);

        Button addProductToDay =new Button("Add product to day");
        addProductToDay.addActionListener(new AddProducttoDayButton());
        addProductToDay.setBackground(Color.ORANGE);
        panelLeft.add(addProductToDay);

        JButton changeProductDataBase = new JButton("Change product table");
        changeProductDataBase.addActionListener(new ChangeProductTableActioListener());
        panelRight.add(changeProductDataBase);
        changeProductDataBase.setBackground(Color.PINK);


        Button closeAplicationButton =new Button("Exit");
        closeAplicationButton.addActionListener(new CloseAplicationButtonActionListener());
        panelRight.add(closeAplicationButton);
    }

    private void addLabels(){
        Label dateAndTime = new Label("There will be date and time");
        panelUpper.add(dateAndTime);
        currentProductTableLabel = new JLabel("Current Calendar Table is: " + Config.CURRENT_DATABASE_TABLE_PRODUCT);
        currentCalendarTableLabel = new JLabel("Current Product Table is: " + Config.CURRENT_DATABASE_TABLE_CALENDAR);

        currentProductTableLabel.setForeground(Config.mainWindowDataBaseProductTableLabelColor);
        currentCalendarTableLabel.setForeground(Config.mainWindowDataBaseCalendarTableLabelColor);

        panelDown.add(currentCalendarTableLabel);
        panelDown.add(currentProductTableLabel);
    }

    private class AddProductButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            new AddSingleProductWindow();

        }
    }

    private class MealMangerButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    private class AddProducttoDayButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddProductToCalendarDay addProductToCalendarDay = new AddProductToCalendarDay();
        }
    }

    private class CloseAplicationButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            startWindow.dispose();
        }
    }

    private class ChangeProductTableActioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Config.CURRENT_DATABASE_TABLE_PRODUCT.equals("product_table")){
                Config.CURRENT_DATABASE_TABLE_PRODUCT = "product_table_test";
            }else {
                Config.CURRENT_DATABASE_TABLE_PRODUCT = "product_table";
            }

            currentProductTableLabel.setText("Current Calendar Table is: " + Config.CURRENT_DATABASE_TABLE_PRODUCT);

            JOptionPane.showMessageDialog(null,"Product table: " + Config.CURRENT_DATABASE_TABLE_PRODUCT);
        }
    }
}
