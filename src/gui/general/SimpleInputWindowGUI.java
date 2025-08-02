package gui.general;

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

    Dimension windowSize = new Dimension(windowWidth, windowHeight);


    private void startAddNewProductWindow() {
        windowFrame = new JFrame(windowName);
        windowFrame.setSize(windowSize);
        setPanels();
        addPanelsToFrame();
        setComponents();
        addComponentsToPanels();
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
