package gui.gym;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WorkoutsPickupGUI {


    //<editor-fold desc="Panels">
    JPanel mainPanel = new JPanel();
    JPanel northPanel = new JPanel();
    JPanel westPanel = new JPanel();
    JPanel eastPanel = new JPanel();
    JPanel southPanel = new JPanel();
    //</editor-fold>

    JFrame windowFrame;

    String windowName;

    int windowWidth;
    int windowHeight;

    Dimension windowSize = new Dimension(800, 400);

    GridLayout gridLayoutMainPanel = new GridLayout(17, 3, 0, 0);

    ArrayList<String> sportToPickArrayList = new ArrayList<String>(Arrays.asList("Gym", "Swimming", "Play on field"));

    public JButton acceptButton = new JButton("Accept");


    public WorkoutsPickupGUI() {
        startWorkoutsPickupGUWindow();
    }

    public void displayWindow() {
        windowFrame.setLocationRelativeTo(null);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setResizable(false);
        windowFrame.setVisible(true);
    }

    public void startWorkoutsPickupGUWindow() {
        setFrame();
        setPanels();
        addPanelsToFrame();
        addComponentsToPanels();
        windowFrame.setLocationRelativeTo(null);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setResizable(false);
        windowFrame.setVisible(true);
    }

    public void setFrame() {
        windowFrame = new JFrame(windowName);
        windowFrame.setSize(windowSize);

    }

    public void setPanels() {
        // mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.setLayout(gridLayoutMainPanel);
        westPanel.setBackground(Color.BLACK);
        eastPanel.setBackground(Color.GRAY);
        mainPanel.setBackground(Color.DARK_GRAY);
        northPanel.setBackground(Color.BLUE);
        southPanel.setBackground(Color.WHITE);

        westPanel.setPreferredSize(new Dimension(150, 40));
        eastPanel.setPreferredSize(new Dimension(200, 100));
        mainPanel.setPreferredSize(new Dimension(50, 100));
        northPanel.setPreferredSize(new Dimension(100, 50));
        southPanel.setPreferredSize(new Dimension(100, 80));
    }

    public void setPanelsColors(Color mainPanelColor, Color westPanelColor, Color eastPanelColor, Color southPanelColor, Color northPanelColor) {
        westPanel.setBackground(westPanelColor);
        eastPanel.setBackground(eastPanelColor);
        mainPanel.setBackground(mainPanelColor);
        northPanel.setBackground(northPanelColor);
        southPanel.setBackground(southPanelColor);
    }

    //<editor-fold desc="addComponentsToPanels">
    private void addComponentsToPanels() {
        addComponentsToMainPanel();
        addComponentsToSouthPanel();
        addComponentsToEastPanel();
        addComponentsToWestPanel();
        addComponentsToNorthPanel();
    }

    public void addComponentsToMainPanel() {

        for (int i = 0; i < sportToPickArrayList.size(); i++) {
            mainPanel.add(new JButton(sportToPickArrayList.get(i)));
        }
    }

    public void addComponentsToSouthPanel() {
        southPanel.add(acceptButton);
    }

    public void addComponentsToEastPanel() {
    }

    public void addComponentsToWestPanel() {
    }

    public void addComponentsToNorthPanel() {
    }
//</editor-fold>

    public void addPanelsToFrame() {
        windowFrame.add(westPanel, BorderLayout.WEST);
        windowFrame.add(eastPanel, BorderLayout.EAST);
        windowFrame.add(mainPanel, BorderLayout.CENTER);
        windowFrame.add(northPanel, BorderLayout.NORTH);
        windowFrame.add(southPanel, BorderLayout.SOUTH);
    }

}