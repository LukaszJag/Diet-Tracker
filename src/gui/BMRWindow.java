package gui;

import javax.swing.*;
import java.awt.*;

public class BMRWindow {
    //<editor-fold desc="Panels">
    JPanel BMRWindowMainPanel = new JPanel();
    JPanel BMRWindowPanelNorth = new JPanel();
    JPanel BMRWindowPanelWest = new JPanel();
    JPanel BMRWindowPanelEast = new JPanel();
    JPanel BMRWindowPanelSouth = new JPanel();
    //</editor-fold>

    //<editor-fold desc="Buttons">
    JButton acceptButton;
    //</editor-fold>

    //<editor-fold desc="Labels">
    JLabel weightLabel;
    JLabel heightLabel;
    JLabel ageLabel;
    JLabel sexLabel;
    JLabel basicBMRLabel;
    JLabel totalBMRLabel;
    JLabel basicBMRResultLabel;
    JLabel totalBMRResultLabel;
    JLabel physicalActivityLevelLabel;
    JLabel completeFormulaLabel;


    //</editor-fold>

    //<editor-fold desc="Text Fields">
    JTextField weightTextField;
    JTextField heightTextField;
    JTextField ageTextField;

    //</editor-fold>
    JComboBox physicalActivityLevelComboBox;
    JComboBox sexComboBox;

    JFrame BMRWindowFrame = new JFrame();

    public BMRWindow(){
        startAddNewProductWindow();
    }

    private void startAddNewProductWindow() {
        BMRWindowFrame = new JFrame("BMR");
        // hard code size - may cause problem
        BMRWindowFrame.setSize(600,400);

        //Set Layout

        BMRWindowMainPanel.setLayout(new GridLayout(9, 2));
        BMRWindowFrame.setLayout(new BorderLayout());

        setPanels();
        addPanelsToFrame();
        setComponents();
        addComponentsToPanels();


        BMRWindowFrame.setResizable(false);
        BMRWindowFrame.setLocationRelativeTo(null);
        BMRWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BMRWindowFrame.setVisible(true);
    }

    private void setPanels() {
        BMRWindowPanelNorth.setPreferredSize(new Dimension(50,50));
        BMRWindowPanelEast.setPreferredSize(new Dimension(10,10));
        BMRWindowMainPanel.setPreferredSize(new Dimension(580,200));
        BMRWindowPanelWest.setPreferredSize(new Dimension(10,10));
        BMRWindowPanelSouth.setPreferredSize(new Dimension(600,30));
    }

    private void addPanelsToFrame() {
        BMRWindowFrame.add(BMRWindowPanelNorth, BorderLayout.NORTH);
        BMRWindowFrame.add(BMRWindowPanelWest, BorderLayout.WEST);
        BMRWindowFrame.add(BMRWindowMainPanel, BorderLayout.CENTER);
        BMRWindowFrame.add(BMRWindowPanelEast, BorderLayout.EAST);
        BMRWindowFrame.add(BMRWindowPanelSouth, BorderLayout.SOUTH);
    }

    private void addComponentsToPanels() {

        //<editor-fold desc="Add components to - Main Panel">
        BMRWindowMainPanel.add(weightLabel);
        BMRWindowMainPanel.add(weightTextField);

        BMRWindowMainPanel.add(heightLabel);
        BMRWindowMainPanel.add(heightTextField);
        heightTextField.setText("185");

        BMRWindowMainPanel.add(ageLabel);
        BMRWindowMainPanel.add(ageTextField);
        ageTextField.setText("28");

        BMRWindowMainPanel.add(sexLabel);
        BMRWindowMainPanel.add(sexComboBox);
        sexComboBox.setSelectedItem("Male");

        BMRWindowMainPanel.add(physicalActivityLevelLabel);
        BMRWindowMainPanel.add(physicalActivityLevelComboBox);

        BMRWindowMainPanel.add(basicBMRLabel);
        BMRWindowMainPanel.add(basicBMRResultLabel);

        BMRWindowMainPanel.add(totalBMRLabel);
        BMRWindowMainPanel.add(totalBMRResultLabel);



        //</editor-fold>

        //<editor-fold desc="Add components to - South Panel">
        BMRWindowPanelSouth.add(acceptButton);
        //</editor-fold>

        //Add components to - North Panel
        BMRWindowPanelNorth.add(completeFormulaLabel);
    }

    private void setComponents() {

        //<editor-fold desc="Set Labels">
        weightLabel = new JLabel("Weight:");
        heightLabel = new JLabel("Height:");
        ageLabel = new JLabel("Age:");
        sexLabel = new JLabel("Sex:");
        basicBMRLabel = new JLabel("Basic BMR:");
        totalBMRLabel = new JLabel("Total BMR:");
        physicalActivityLevelLabel = new JLabel("PAL:");

        completeFormulaLabel = new JLabel("\n" +
                "\n" +
                "    For men: BMR = 66.5 + (13.75 × weight in kg) + (5.003 × height in cm) - (6.75 × age)\n" +
                "\n" +
                "    For women: BMR = 655.1 + (9.563 × weight in kg) + (1.850 × height in cm) - (4.676 × age)\n");

        basicBMRResultLabel = new JLabel("result");
        totalBMRResultLabel = new JLabel("result");
        //</editor-fold>

        //<editor-fold desc="Set TextFields">
        weightTextField = new JTextField("");
        heightTextField = new JTextField("");
        ageTextField = new JTextField("");

        //</editor-fold>

        //<editor-fold desc="Set Buttons">
        acceptButton = new JButton("Calculate BMR");
        //</editor-fold>


        String sexOptions[] = {"Male", "Female"};
        sexComboBox = new JComboBox<>(sexOptions);

        // Full table for PAL multiply - link -> https://www.inchcalculator.com/harris-benedict-calculator/

        String physicalActivityLevelOptions[] = {
                "Little to no exercise, such as a desk job with no additional physical activity",
                "Light exercise 1-2 days/week",
                "Moderate exercise 3-5 days/week",
                "Hard exercise 6-7 days/week",
                "Hard daily exercise and physical job or two times a day training"};
        physicalActivityLevelComboBox = new JComboBox<>(physicalActivityLevelOptions);
    }

}
