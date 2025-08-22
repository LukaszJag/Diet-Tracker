package gui.general;

import configuration.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InputPatternWindowGUI {
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

    ArrayList<String> filedNamesData = new ArrayList<>();
    ArrayList<JTextField> textFieldToHoldData = new ArrayList<>();

    public JButton acceptButton = new JButton("Accept");


    public InputPatternWindowGUI(){
        setupEmptyInstant();
    }

    private void setupEmptyInstant() {
        setFrame();
        setPanels();
        addPanelsToFrame();
        setGeneralComponents();
    }

    public void displayWindow(){
        windowFrame.setLocationRelativeTo(null);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setResizable(false);
        windowFrame.setVisible(true);
    }

    public void startAddNewProductWindow() {
        setFrame();
        setPanels();
        addPanelsToFrame();
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

    public ArrayList<String> getDataFromEveryTextField(){
        ArrayList<String> dataToReturn = new ArrayList<String>();

        for (int i = 0; i < textFieldToHoldData.size(); i++) {
            dataToReturn.add(textFieldToHoldData.get(i).getText());
        }
        return null;
    }

    public void addComponentsToMainPanel(ArrayList<String> fieldsToAdd) {


        for (int i = 0; i < fieldsToAdd.size(); i++) {
            mainPanel.add(new JLabel(fieldsToAdd.get(i))).setForeground(Color.BLACK);
            String textForTextField = "" + i;
            textFieldToHoldData.add(i, new JTextField());
            mainPanel.add(textFieldToHoldData.get(i));

        }
    }

    //<editor-fold desc="addComponentsToPanels">
    public void addComponentsToSouthPanel() {
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

    public void setGeneralComponents(){
        acceptButton.addActionListener(new AcceptButtonActionListener());
        southPanel.add(acceptButton);
    }

    public ActionListener getAcceptButtonActionListener(){
        return acceptButton.getActionListeners()[0];
    }

    private class AcceptButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
