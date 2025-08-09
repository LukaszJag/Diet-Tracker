package gui.general;

import configuration.Config;

import javax.swing.*;
import java.awt.*;

public abstract class SimpleInputWindowGUI {
    //<editor-fold desc="Panels">
    JPanel panelMain = new JPanel();
    JPanel panelNorth = new JPanel();
    JPanel panelWest = new JPanel();
    JPanel panelEast = new JPanel();
    JPanel panelSouth = new JPanel();
    //</editor-fold>

    JFrame windowFrame;

    String windowName;

    int windowWidth;
    int windowHeight;

    Dimension windowSize = Config.SIMPLE_INPUT_WINDOW_GUI_WINDOW_SIZE;

    public SimpleInputWindowGUI(){
        startAddNewProductWindow();
    }

    private void startAddNewProductWindow() {
        windowFrame = new JFrame(windowName);
        windowFrame.setSize(windowSize);
        setPanels();
        addPanelsToFrame();
        setComponents();
        addComponentsToPanels();

        windowFrame.setLocationRelativeTo(null);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setResizable(false);
        windowFrame.setVisible(true);
    }

    private void addComponentsToPanels() {
    }

    private void setComponents() {
    }

    private void addPanelsToFrame() {
    }

    private void setPanels() {

    }
}
