package gui.general;

import configuration.Config;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SimpleInputWindowGUI {
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

    Dimension windowSize = Config.SIMPLE_INPUT_WINDOW_GUI_WINDOW_SIZE;

    GridLayout gridLayoutMainPanel = new GridLayout(17, 3, 0, 0);

    public SimpleInputWindowGUI(){
        startAddNewProductWindow();
    }

    public void startAddNewProductWindow() {
        setFrame();
        setPanels();
        addPanelsToFrame();
        setComponents();
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

    //<editor-fold desc="addComponentsToPanels">
    public void addComponentsToPanels() {
        addComponentsToMainPanel(null, null);

        westPanel.setBackground(Color.BLACK);
        eastPanel.setBackground(Color.GRAY);
        mainPanel.setBackground(Color.DARK_GRAY);
        northPanel.setBackground(Color.BLUE);
        southPanel.setBackground(Color.WHITE);

        westPanel.setPreferredSize(new Dimension(150, 40));
        eastPanel.setPreferredSize(new Dimension(200, 100));
        mainPanel.setPreferredSize(new Dimension(50, 100));
        northPanel.setPreferredSize(new Dimension(100, 50));
        southPanel.setPreferredSize(new Dimension(100, 25));

    }

    public void addComponentsToMainPanel(ArrayList<JLabel> labelsToAdd, ArrayList<?> mainElements) {
        for (int i = 0; i < Config.gym_workoutTable.length; i++) {
            mainPanel.add(new JLabel(Config.gym_workoutTable[i])).setForeground(Color.BLACK);
            String textForTextField = "" + i;
            mainPanel.add(new JTextField(textForTextField)).setPreferredSize(new Dimension(20,5));

        }
    }

    public void addComponentsToSouthPanel() {
    }

    public void addComponentsToEastPanel() {
    }

    public void addComponentsToWestPanel() {
    }

    public void addComponentsToNorthPanel() {
    }
    //</editor-fold>

    public void setComponents() {
    }

    public void addPanelsToFrame() {
        windowFrame.add(westPanel, BorderLayout.WEST);
        windowFrame.add(eastPanel, BorderLayout.EAST);
        windowFrame.add(mainPanel, BorderLayout.CENTER);
        windowFrame.add(northPanel, BorderLayout.NORTH);
        windowFrame.add(southPanel, BorderLayout.SOUTH);
    }

    public void setPanels() {
       // mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPanel.setLayout(gridLayoutMainPanel);

    }
}
