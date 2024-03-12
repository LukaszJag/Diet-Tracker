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
    public void makeRunWindow(){
        JOptionPane.showMessageDialog(this,"Welcome in Diet Tracker Application");
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
        addComponentsToStartFrame();

        startWindow.setLocationRelativeTo(null);
        startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startWindow.setResizable(false);
        startWindow.setVisible(true);
    }

    private void addComponentsToStartFrame(){

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
        panelRight.setPreferredSize(new Dimension(100,100));
        panelCenter.setPreferredSize(new Dimension(100,100));
        panelUpper.setPreferredSize(new Dimension(100,50));
        panelDown.setPreferredSize(new Dimension(100,25));
    }

    private void addButtons(){
        Button addProductButton = new Button("Add new product");
        addProductButton.addActionListener(new AddProductButton());
        panelLeft.add(addProductButton);

        Button mealManagerButton = new Button("Meal manager");
        mealManagerButton.addActionListener(new MealMangerButton());
        panelLeft.add(mealManagerButton);

        Button addProductToDay =new Button("Add product to day");
        addProductToDay.addActionListener(new AddProducttoDayButton());
        panelLeft.add(addProductToDay);
    }

    private void addLabels(){
        Label dateAndTime = new Label("There will be date and time");
        panelUpper.add(dateAndTime);
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
            JOptionPane.showMessageDialog(null,"Welcome in Add Product to Day");
            AddProductToCalendarDay addProductToCalendarDay = new AddProductToCalendarDay();
        }
    }
}
