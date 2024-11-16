package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        // Panel Down
        panelDown.add(refreshDaysStatisticsButton);

        // Panel Right
        for (int i = 0; i < selectScopeToRefresh.length; i++) {
            panelRight.add(selectScopeToRefresh[i]);
        }
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
}
