package gui;

import configuration.Config;
import runners_and_tests.run_update.RunnerFullUpdateDayStatistics;
import runners_and_tests.run_update.UpdateProductAndCalendarTableFull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainWindow extends JFrame {
    public MainWindow() {
        makeRunWindow();
    }

    private JFrame startWindow;
    private BorderLayout startWindowBorderLayout;

    //<editor-fold desc="Panels">
    private JPanel startWindowPanel = new JPanel();
    private JPanel panelLeft = new JPanel();
    private JPanel panelRight = new JPanel();
    private JPanel panelCenter = new JPanel();
    private JPanel panelUpper = new JPanel();
    private JPanel panelDown = new JPanel();
    //</editor-fold>

    //<editor-fold desc="Labels">
    JLabel currentCalendarTableLabel = new JLabel();
    JLabel currentProductTableLabel = new JLabel();
    //</editor-fold>

    //<editor-fold desc="Buttons">
    JButton addProductButton = new JButton("Add new product");
    JButton mealManagerButton = new JButton("Meal manager");

    JButton addProductToDay = new JButton("Add product to day");
    JButton calculateBMR = new JButton("Calculate BMR");
    JButton calendarMonthStatsView = new JButton("Month stats view");


    JButton changeProductDataBase = new JButton("Change product table");
    JButton refreshDataBaseButton = new JButton("Refresh Data base");
    JButton refreshCalendarAndProductDataBaseButton = new JButton("Refresh CalendarAndProduct Data");
    JButton refreshDaysStatisticsDataBaseButton = new JButton("Refresh DaysStatistics Data");

    JButton closeApplicationButton = new JButton("Exit");
    //</editor-fold>


    public void makeRunWindow() {
        setUpAndStartMenuWindow();
    }

    private void setUpAndStartMenuWindow() {

        startWindow = new JFrame("Diet Tracker - main window");
        startWindow.setSize(Config.START_WINDOWS_WIDTH, Config.START_WINDOWS_HEIGHT);
        startWindowBorderLayout = new BorderLayout();
        startWindow.setLayout(startWindowBorderLayout);
        startWindowPanel.setPreferredSize(new Dimension(100, 40));

        setPanels();
        addButtons();
        addLabels();
        addPanelsToStartFrame();

        startWindow.setLocationRelativeTo(null);
        startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startWindow.setResizable(false);
        startWindow.setVisible(true);
    }

    private void addPanelsToStartFrame() {

        startWindow.add(panelLeft, BorderLayout.WEST);
        startWindow.add(panelRight, BorderLayout.EAST);
        startWindow.add(panelCenter, BorderLayout.CENTER);
        startWindow.add(panelUpper, BorderLayout.NORTH);
        startWindow.add(panelDown, BorderLayout.SOUTH);
    }

    private void setPanels() {
        panelLeft.setBackground(Color.BLACK);
        panelRight.setBackground(Color.GRAY);
        panelCenter.setBackground(Color.DARK_GRAY);
        panelUpper.setBackground(Color.BLUE);
        panelDown.setBackground(Color.WHITE);

        panelLeft.setPreferredSize(new Dimension(150, 40));
        panelRight.setPreferredSize(new Dimension(200, 100));
        panelCenter.setPreferredSize(new Dimension(50, 100));
        panelUpper.setPreferredSize(new Dimension(100, 50));
        panelDown.setPreferredSize(new Dimension(100, 25));
    }

    private void addButtons() {

        addProductButton.setBackground(Color.GREEN);

        addProductButton.addActionListener(new AddProductButton());
        panelLeft.add(addProductButton);

        mealManagerButton.addActionListener(new MealMangerButton());
        panelLeft.add(mealManagerButton);


        addProductToDay.addActionListener(new AddProducttoDayButton());
        addProductToDay.setBackground(Color.ORANGE);
        panelLeft.add(addProductToDay);


        calculateBMR.addActionListener(new calculateBMRActionListener());
        panelLeft.add(calculateBMR);

        calendarMonthStatsView.addActionListener(new calendarMonthStatsViewActionListener());
        panelLeft.add(calendarMonthStatsView);


        changeProductDataBase.addActionListener(new ChangeProductTableActioListener());
        panelRight.add(changeProductDataBase);
        changeProductDataBase.setBackground(Color.PINK);

        refreshCalendarAndProductDataBaseButton.addActionListener(new RefreshCalendarAndProductDataBaseButtonActionListener());
        panelRight.add(refreshCalendarAndProductDataBaseButton);

        refreshDaysStatisticsDataBaseButton.addActionListener(new RefreshDaysStatisticsDataBaseButtonActionListener());
        panelRight.add(refreshDaysStatisticsDataBaseButton);

        refreshDataBaseButton.addActionListener(new RefreshDataBaseButtonActionListener());
        panelRight.add(refreshDataBaseButton);

        closeApplicationButton.addActionListener(new CloseAplicationButtonActionListener());
        panelRight.add(closeApplicationButton);
    }

    private void addLabels() {
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
            if (Config.CURRENT_DATABASE_TABLE_PRODUCT.equals("product_table")) {
                Config.CURRENT_DATABASE_TABLE_PRODUCT = "product_table_test";
            } else {
                Config.CURRENT_DATABASE_TABLE_PRODUCT = "product_table";
            }

            currentProductTableLabel.setText("Current Calendar Table is: " + Config.CURRENT_DATABASE_TABLE_PRODUCT);

            JOptionPane.showMessageDialog(null, "Product table: " + Config.CURRENT_DATABASE_TABLE_PRODUCT);
        }
    }

    private class RefreshDataBaseButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                UpdateProductAndCalendarTableFull.updateProductAndCalendarTableFull();
                RunnerFullUpdateDayStatistics.runFullUpdateForDayStatistics();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            JOptionPane.showMessageDialog(null, "Whole database is update");
        }
    }

    private class calculateBMRActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new BMRWindow();
        }
    }

    private class calendarMonthStatsViewActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new CalendarMonthStatsView();
        }
    }

    private class RefreshDaysStatisticsDataBaseButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                RunnerFullUpdateDayStatistics.runFullUpdateForDayStatistics();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            JOptionPane.showMessageDialog(null, "Day Statistics is update");
        }
    }

    private class RefreshCalendarAndProductDataBaseButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                UpdateProductAndCalendarTableFull.updateProductAndCalendarTableFull();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(null, "Calendar and product database is update");
        }
    }
}
