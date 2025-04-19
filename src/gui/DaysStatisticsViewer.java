package gui;

import configuration.Config;
import tools.sql_tools.SQLCount;

import javax.swing.*;
import java.awt.*;

public class DaysStatisticsViewer {

    private JFrame startWindow;
    private BorderLayout startWindowBorderLayout;

    //<editor-fold desc="Panels">
    private JPanel startWindowPanel = new JPanel();
    private JPanel panelLeft = new JPanel();
    private JPanel panelRight = new JPanel();
    private JPanel panelCenter = new JPanel();
    private JPanel panelUpper = new JPanel();
    private JPanel panelDown = new JPanel();

    private String selectedDate = "yyyy-mm-dd";

    private int countOfSelectedDay = -1;

    JTextField selectedDateTextField = new JTextField("");


    JButton exitButton = new JButton("Exit");

    JLabel countOfSelectedDayLabel = new JLabel("");


    GridLayout gridLayoutMainPanel = new GridLayout(6, 8, 10, 10);


    public DaysStatisticsViewer() {
        startDaysStatisticsViewer();
    }

    public DaysStatisticsViewer(String selectedDate) {
        this.selectedDate = selectedDate;
        startDaysStatisticsViewer();
    }

    private void startDaysStatisticsViewer() {

        startWindow = new JFrame("Diet Tracker - DaysStatisticsViewer");
        startWindow.setSize(Config.DAYS_STATISTICS_VIEWER_WIDTH, Config.DAYS_STATISTICS_VIEWER_HEIGHT);
        startWindowBorderLayout = new BorderLayout();
        startWindow.setLayout(startWindowBorderLayout);
        startWindowPanel.setPreferredSize(new Dimension(100, 40));

        setPanels();
        addPanelsToStartFrame();


        setComponents();

        addComponentsToPanels();


        startWindow.setLocationRelativeTo(null);
        startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startWindow.setResizable(false);
        startWindow.setVisible(true);
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


        panelCenter.setLayout(gridLayoutMainPanel);
    }

    private void addPanelsToStartFrame() {

        startWindow.add(panelLeft, BorderLayout.WEST);
        startWindow.add(panelRight, BorderLayout.EAST);
        startWindow.add(panelCenter, BorderLayout.CENTER);
        startWindow.add(panelUpper, BorderLayout.NORTH);
        startWindow.add(panelDown, BorderLayout.SOUTH);
    }

    private void setComponents() {
        selectedDateTextField.setText(selectedDate);

        for (int i = 0; i < 48; i++) {
            panelCenter.add(new Button(String.valueOf(i)));
        }

        countOfSelectedDay = SQLCount.getCountFromTable("calendar", selectedDate);
        countOfSelectedDayLabel.setText(String.valueOf("Count is: " + countOfSelectedDay));
    }

    private void addComponentsToPanels() {



        //<editor-fold desc="panel Down - Components">
        panelDown.add(exitButton);
        //</editor-fold>

        //<editor-fold desc="panel Upper - Components">
        panelUpper.add(selectedDateTextField);
        panelUpper.add(countOfSelectedDayLabel);
        //</editor-fold>
    }
}
