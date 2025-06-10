package gui;

import javax.swing.*;
import java.awt.*;

public class LoadingBarGUI {
    public static JPanel panelCenter;
    public static JFrame frame;
    public static JLabel label;
    public static JProgressBar progressBar;

    public LoadingBarGUI() {
        this.panelCenter = new JPanel();
        this.frame = new JFrame("loading bar - window");
        this.progressBar = new JProgressBar();
        this.label = new JLabel("test");
        displayLoadingBar();
    }

    public LoadingBarGUI(int ver) {
        this.panelCenter = new JPanel();
        this.frame = new JFrame("loading bar - window");
        this.progressBar = new JProgressBar();
        this.label = new JLabel("test");
    }


    public void displayLoadingBar() {

        frame.setSize(400, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted (true);

        panelCenter.setSize(300,50);
        panelCenter.setLayout(new GridLayout());
        panelCenter.add(progressBar);
        panelCenter.add(label);
        panelCenter.add(new JLabel("test test"));
        System.out.println("loading bar - point");

        frame.add(panelCenter);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void fill(int progress) {

        // fill the menu bar
        progressBar.setValue(progressBar.getValue() + progress);
    }

    public void fillToFull() {

        // fill the menu bar
        progressBar.setValue(100);
    }

    public void setLabel(String newLabelValue) {

        label.setText(newLabelValue);
    }

    public void closeWindow() {
        frame.dispose();
    }
}
