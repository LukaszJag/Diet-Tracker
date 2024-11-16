package gui;

import runners_and_tests.run_update.RunnerFullUpdateDayStatistics;
import tools.time_date_tools.DateTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;

public class RefreshDayStatisticsWindow {

    private JFrame mainFrame;
    private BorderLayout mainFrameWindowBorderLayout;

    //<editor-fold desc="Panels">
    private JPanel panelLeft = new JPanel();
    private JPanel panelRight = new JPanel();
    private JPanel panelCenter = new JPanel();
    private JPanel panelUpper = new JPanel();
    private JPanel panelDown = new JPanel();
    //</editor-fold>

    String[] radioButtonsArgs = {"This day", "This week", "This month", "This year", "Full"};
    JRadioButton[] selectScopeToRefresh = new JRadioButton[radioButtonsArgs.length];
    JButton refreshDaysStatisticsButton = new JButton("Refresh Days Statistics");
    JButton refreshDaysStatisticsFullButton = new JButton("Refresh Days Statistics Full");
    JButton refreshDaysStatisticsSeptemberButton = new JButton("Refresh Days Statistics September");
    JButton getRefreshDaysStatisticsRadioButtonScopeButton = new JButton("Refresh");
    ButtonGroup buttonGroup = new ButtonGroup();

    public RefreshDayStatisticsWindow() {
        makeRefreshDayStatisticsWindows();
    }

    private void makeRefreshDayStatisticsWindows() {
        mainFrame = new JFrame("Diet Tracker - Refresh Day Statistics Window");
        mainFrame.setSize(400, 400);
        mainFrameWindowBorderLayout = new BorderLayout();
        mainFrame.setLayout(mainFrameWindowBorderLayout);

        setRadioButton();
        setButtons();
        setLabels();
        setPanels();
        addComponentsToPanels();
        addPanelsToStartFrame();

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void setRadioButton() {
        for (int i = 0; i < selectScopeToRefresh.length; i++) {

            selectScopeToRefresh[i] = new JRadioButton(radioButtonsArgs[i]);
            selectScopeToRefresh[i].setOpaque(false);
            buttonGroup.add(selectScopeToRefresh[i]);
        }
    }

    private void setButtons() {
        refreshDaysStatisticsButton.addActionListener(new RefreshDayStatisticsButtonActionListener());
        refreshDaysStatisticsFullButton.addActionListener( new refreshDaysStatisticsFullButtonActionListener());
        refreshDaysStatisticsSeptemberButton.addActionListener(new refreshDaysStatisticsSeptemberButtonActionListener());
        getRefreshDaysStatisticsRadioButtonScopeButton.addActionListener(new getRefreshDaysStatisticsRadioButtonScopeButtonActionListener());
    }

    private void setLabels() {

    }

    private void setPanels() {
        panelLeft.setBackground(Color.BLACK);
        panelRight.setBackground(Color.GRAY);
        panelCenter.setBackground(Color.DARK_GRAY);
        panelUpper.setBackground(Color.BLUE);
        panelDown.setBackground(Color.WHITE);

        panelLeft.setPreferredSize(new Dimension(150, 40));
        panelRight.setPreferredSize(new Dimension(100, 100));
        panelCenter.setPreferredSize(new Dimension(50, 100));
        panelUpper.setPreferredSize(new Dimension(100, 50));
        panelDown.setPreferredSize(new Dimension(100, 55));
    }

    private void addComponentsToPanels() {
        // Panel Center
        panelCenter.add(refreshDaysStatisticsFullButton);
        panelCenter.add(refreshDaysStatisticsSeptemberButton);
        panelCenter.add(refreshDaysStatisticsButton);
        // Panel Down



        // Panel Right
        for (int i = 0; i < selectScopeToRefresh.length; i++) {
            panelRight.add(selectScopeToRefresh[i]);
        }

        panelRight.add(getRefreshDaysStatisticsRadioButtonScopeButton);
    }

    private void addPanelsToStartFrame() {
        //mainFrame.add(panelLeft, BorderLayout.WEST);
        mainFrame.add(panelRight, BorderLayout.EAST);
        mainFrame.add(panelCenter, BorderLayout.CENTER);
        mainFrame.add(panelUpper, BorderLayout.NORTH);
        mainFrame.add(panelDown, BorderLayout.SOUTH);
    }


    private class RefreshDayStatisticsButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String months[] = {
                    "January"
                    ,"February"
                    ,"March"
                    ,"April"
                    ,"May"
                    ,"June"
                    ,"July"
                    ,"August"
                    ,"September"
                    ,"October"
                    ,"November"
                    ,"December"};

            Calendar calendar = Calendar.getInstance();
            String month = months[calendar.get(Calendar.MONTH)];
            String message = "none";

            for (int i = 0; i < selectScopeToRefresh.length; i++) {
                if (selectScopeToRefresh[i].isSelected()) {
                    message = selectScopeToRefresh[i].getText();
                }

            }
            if (!message.equals("none")) {
                JOptionPane.showMessageDialog(null, "Refresh - " + message + month);
            } else {
                JOptionPane.showMessageDialog(null, "Refresh - " + message);
            }
        }
    }

    private class refreshDaysStatisticsFullButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            long timeStart = System.currentTimeMillis();
            try {
                RunnerFullUpdateDayStatistics.runFullUpdateForDayStatistics();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            long timeEnd = System.currentTimeMillis();
            long duration = timeEnd - timeStart;
            JOptionPane.showMessageDialog(null, "Full update has finished time: " + duration);
        }
    }

    private class refreshDaysStatisticsSeptemberButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            long timeStart = System.currentTimeMillis();

            try {
                RunnerFullUpdateDayStatistics.updateDaysStatisticsSeptember();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            long timeEnd = System.currentTimeMillis();
            long duration = timeEnd - timeStart;
            JOptionPane.showMessageDialog(null, "September update has finished time: " + duration);

        }
    }

    private class getRefreshDaysStatisticsRadioButtonScopeButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = "";

            for (int i = 0; i < selectScopeToRefresh.length; i++) {
                if (selectScopeToRefresh[i].isSelected()) {
                    message = selectScopeToRefresh[i].getText();
                }

            }

            if (message.equals("This month")) {
                String monthName = DateTools.getCurrentMontNameCamelCase();

                try {
                    RunnerFullUpdateDayStatistics.updateMonth(monthName);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                JOptionPane.showMessageDialog(null, "Refresh - " + monthName);
            } else {
                JOptionPane.showMessageDialog(null, "Nothing happened");
            }
        }
    }
}
