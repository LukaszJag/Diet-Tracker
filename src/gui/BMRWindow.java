package gui;

import configuration.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BMRWindow {
    //<editor-fold desc="Panels">
    JPanel BMRWindowMainPanel = new JPanel();
    JPanel BMRWindowPanelNorth = new JPanel();
    JPanel BMRWindowPanelWest = new JPanel();
    JPanel BMRWindowPanelEast = new JPanel();
    JPanel BMRWindowPanelSouth = new JPanel();
    //</editor-fold>

    String fullFormulaString = "For men: BMR = 66.5 + (13.75 × weight in kg) + (5.003 × height in cm) - (6.75 × age)\n" +
            "For women: BMR = 655.1 + (9.563 × weight in kg) + (1.850 × height in cm) - (4.676 × age)";

    // In order from Sedentary to Extra active 0, 1 ,2, 3, 4
    /*  To determine your TDEE (Total Daily Energy Expenditure), multiply your BMR by the appropriate activity factor, as follows:

        Sedentary (little or no exercise): calories = BMR × 1.2;

        Lightly active (light exercise/sports 1-3 days/week): calories = BMR × 1.375;

        Moderately active (moderate exercise/sports 3-5 days/week): calories = BMR × 1.55;

        Very active (hard exercise/sports 6-7 days a week): calories = BMR × 1.725; and

        If you are extra active (very hard exercise/sports & a physical job): calories = BMR × 1.9.

     */
    String physicalActivityLevelOptions[] = {
            "Little to no exercise, such as a desk job with no additional physical activity",
            "Light exercise 1-2 days/week",
            "Moderate exercise 3-5 days/week",
            "Hard exercise 6-7 days/week",
            "Hard daily exercise and physical job or two times a day training"};

    Color BMRWindowPanelNorthColor = new Color(0, 0, 0);
    Color BMRWindowPanelEastColor = new Color(233, 176, 18);
    Color BMRWindowMainPanelColor = new Color(211, 204, 216);
    Color BMRWindowPanelWestColor = new Color(45, 64, 142);
    Color BMRWindowPanelSouthColor = new Color(255, 255, 255);

    //<editor-fold desc="Buttons">
    JButton acceptButton = new JButton("Accept data");
    JButton showFullFormulaButton = new JButton("Show formula");
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


    //</editor-fold>

    //<editor-fold desc="Text Fields">
    JTextField weightTextField;
    JTextField heightTextField;
    JTextField ageTextField;

    //</editor-fold>
    JComboBox physicalActivityLevelComboBox;
    JComboBox sexComboBox;

    JFrame BMRWindowFrame = new JFrame();

    public BMRWindow() {
        startAddNewProductWindow();
    }

    private void startAddNewProductWindow() {
        BMRWindowFrame = new JFrame("BMR");
        // hard code size - may cause problem
        BMRWindowFrame.setSize(Config.BMR_WINDOW_WINDOWS_WIDTH, Config.BMR_WINDOW_WINDOWS_HEIGHT);

        //Set Layout

        BMRWindowMainPanel.setLayout(new GridLayout(9, 2));
        BMRWindowFrame.setLayout(new BorderLayout());

        setPanels();
        addPanelsToFrame();
        setComponents();
        addComponentsToPanels();


        BMRWindowFrame.setResizable(false);
        BMRWindowFrame.setLocationRelativeTo(null);
        BMRWindowFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        BMRWindowFrame.setVisible(true);

    }

    private void setPanels() {
        BMRWindowPanelNorth.setBackground(BMRWindowPanelNorthColor);
        BMRWindowPanelEast.setBackground(BMRWindowPanelEastColor);
        BMRWindowMainPanel.setBackground(BMRWindowMainPanelColor);
        BMRWindowPanelWest.setBackground(BMRWindowPanelWestColor);
        BMRWindowPanelSouth.setBackground(BMRWindowPanelSouthColor);


        BMRWindowPanelNorth.setPreferredSize(new Dimension(Config.BMR_WINDOW_PANELS_NORTH_SIZE, Config.BMR_WINDOW_PANELS_NORTH_SIZE));
        BMRWindowPanelEast.setPreferredSize(new Dimension(Config.BMR_WINDOW_PANELS_WEST_EAST_SIZE, Config.BMR_WINDOW_PANELS_WEST_EAST_SIZE));
        BMRWindowMainPanel.setPreferredSize(new Dimension(Config.BMR_WINDOW_PANELS_CENTER, Config.BMR_WINDOW_PANELS_CENTER));
        BMRWindowPanelWest.setPreferredSize(new Dimension(Config.BMR_WINDOW_PANELS_WEST_EAST_SIZE, Config.BMR_WINDOW_PANELS_WEST_EAST_SIZE));
        BMRWindowPanelSouth.setPreferredSize(new Dimension(Config.BMR_WINDOW_PANELS_SOUTH_SIZE, Config.BMR_WINDOW_PANELS_SOUTH_SIZE));
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

        BMRWindowPanelEast.add(showFullFormulaButton);

        //Add components to - North Panel
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

        basicBMRResultLabel = new JLabel("result");
        totalBMRResultLabel = new JLabel("result");
        //</editor-fold>

        //<editor-fold desc="Set TextFields">
        weightTextField = new JTextField("");
        heightTextField = new JTextField("");
        ageTextField = new JTextField("");

        //</editor-fold>

        //<editor-fold desc="Set Buttons">
        showFullFormulaButton.addActionListener(new ShowFullFormulaButtonActionListener());
        acceptButton.addActionListener(new AcceptButtonActionListener());
        //</editor-fold>


        String sexOptions[] = {"Male", "Female"};
        sexComboBox = new JComboBox<>(sexOptions);

        // Full table for PAL multiply - link -> https://www.inchcalculator.com/harris-benedict-calculator/

        physicalActivityLevelComboBox = new JComboBox<>(physicalActivityLevelOptions);
    }

    private double[] passAndGetDataFromGUIToCalcutate() {
        float weight = Float.valueOf(weightTextField.getText());

        float height = Float.valueOf(heightTextField.getText());

        int age = Integer.valueOf(ageTextField.getText());

        boolean isMale;

        if (sexComboBox.getSelectedItem().toString().equals("Male")) {
            isMale = true;
        } else {
            isMale = false;
        }

        String PAL = physicalActivityLevelComboBox.getSelectedItem().toString();

        double[] basicAndTotalBMR = new double[2];

        basicAndTotalBMR[0] = calculateBasicBMR(weight, height, age, isMale);
        basicAndTotalBMR[1] = calculateTotalBMR(weight, height, age, isMale, PAL);
        return basicAndTotalBMR;
    }

    private double calculateBasicBMR(float weight, float height, int age, boolean isMale) {
        if (isMale) {
            double BMRforMen = (66.5 + (13.75 * weight) + (5.003 * height) - (6.75 * age));
            return BMRforMen;
        }

        if (!isMale) {
            double BMRforWoman = (655.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age));
            return BMRforWoman;
        }
        return -1;
    }

    private double calculateTotalBMR(float weight, float height, int age, boolean isMale, String PAL) {
        int palOption = -1;
        for (int i = 0; i < physicalActivityLevelOptions.length; i++) {
            if (physicalActivityLevelOptions[i].equals(PAL)) {
                palOption = i;
            }
        }

        double[] palMultiplyInOrder = {1.2, 1.375, 1.55, 1.725, 1.9};


        if (isMale) {
            double BMRforMen = (66.5 + (13.75 * weight) + (5.003 * height) - (6.75 * age));
            double BMRforMenTotal = palMultiplyInOrder[palOption] * BMRforMen;
            return BMRforMenTotal;
        }

        if (!isMale) {
            double BMRforWomen = (655.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age));
            double BMRforWomenTotal = palMultiplyInOrder[palOption] * BMRforWomen;
            return BMRforWomenTotal;
        }

        return -1;
    }

    private class ShowFullFormulaButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, fullFormulaString);
        }
    }

    private class AcceptButtonActionListener implements ActionListener {
        @Override

        public void actionPerformed(ActionEvent e) {

            double[] basicAndTotalBMR = passAndGetDataFromGUIToCalcutate();

            String basicBMR = String.format("%.2f", basicAndTotalBMR[0]);
            String totalBMR = String.format("%.2f", basicAndTotalBMR[1]);


            basicBMRResultLabel.setText(basicBMR);
            totalBMRResultLabel.setText( totalBMR);

            String messageBMR = "Basic BMR on current data: " + basicBMR + "\n" +
                    "Total BMR on current data: " + totalBMR ;

            JOptionPane.showMessageDialog(null, messageBMR);
        }
    }
}
