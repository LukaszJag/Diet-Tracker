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
        startNewWindow();
    }

    public Window(String windowName, int width, int height){
        this.windowWidth = width;
        this.windowHeight = height;
        this.windowName = windowName;
        startNewWindow();
    }
    private void startNewWindow() {
        windowFrame = new JFrame(windowName);

    }
}
