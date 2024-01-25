package GUI;

import javax.swing.*;

public class Window {
    String windowName;
    int windowWidth = 600;
    int windowHeight = 400;
    JPanel addProductMainPanel = new JPanel();
    JPanel addProductPanelNorth = new JPanel();
    JPanel addProductPanelWest = new JPanel();
    JPanel addProductPanelEast = new JPanel();
    JPanel addProductPanelSouth = new JPanel();
    JFrame windowFrame = new JFrame();
    //Declare Text Fields
    public Window(String windowName){
        this.windowName = windowName;
    }

    protected void setWindowSize(int width, int height){
        this.windowWidth = width;
        this.windowHeight = height;
    }
    protected void startDisplayNewWindow() {
        windowFrame = new JFrame(windowName);
        windowFrame.setSize(this.windowWidth, this.windowHeight);
        windowFrame.setResizable(false);
        windowFrame.setLocale(null);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setVisible(true);
    }
}
