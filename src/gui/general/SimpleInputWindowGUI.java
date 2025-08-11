package gui.general;

import configuration.Config;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class SimpleInputWindowGUI {
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

    public SimpleInputWindowGUI(){
        startAddNewProductWindow();
    }

    private void startAddNewProductWindow() {
        setFrame();
        setPanels();
        addPanelsToFrame();
        setComponents();
        addComponentsToPanels();
    }

    private void setFrame() {
        windowFrame = new JFrame(windowName);
        windowFrame.setSize(windowSize);
        windowFrame.setLocationRelativeTo(null);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setResizable(false);
        windowFrame.setVisible(true);
    }

    //<editor-fold desc="addComponentsToPanels">
    private void addComponentsToPanels() {
        addComponentsToMainPanel(null, null);

        addComponentsToNorthPanel();
        addComponentsToWestPanel();
        addComponentsToEastPanel();
        addComponentsToSouthPanel();

    }

    private void addComponentsToMainPanel(ArrayList<JLabel> labelsToAdd, ArrayList<?> mainElements) {

    }

    private void addComponentsToSouthPanel() {
    }

    private void addComponentsToEastPanel() {
    }

    private void addComponentsToWestPanel() {
    }

    private void addComponentsToNorthPanel() {
    }
    //</editor-fold>

    private void setComponents() {
    }

    private void addPanelsToFrame() {
    }

    private void setPanels() {

    }
}
