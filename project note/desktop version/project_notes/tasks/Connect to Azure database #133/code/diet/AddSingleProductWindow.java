package gui.diet;

import configuration.Config;
import logs.Log;
import tools.products_tools.Macro;
import tools.products_tools.Product;
import tools.sql_tools.products.InsertProductToSQL_Table;
import tools.text_files_tools.FilesTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddSingleProductWindow {

    //<editor-fold desc="Panels">
    JPanel addProductMainPanel = new JPanel();
    JPanel addProductPanelNorth = new JPanel();
    JPanel addProductPanelWest = new JPanel();
    JPanel addProductPanelEast = new JPanel();
    JPanel addProductPanelSouth = new JPanel();
    //</editor-fold>

    //<editor-fold desc="Buttons">
    JButton acceptButton;
    JButton backToMainWindowButton;
    JButton exitProgramProductWindowButton = new JButton();
    JButton clearTextFieldsButton;
    //</editor-fold>

    //<editor-fold desc="Labels">
    JLabel productNameLabel;
    JLabel productBrandLabel;
    JLabel productPackageHasLabel;
    JLabel productMacroForLabel;
    JLabel productKCalLabel;
    JLabel productProteinLabel;
    JLabel productFatLabel;
    JLabel productCarbsLabel;
    JLabel productCommentOptionalCarbsLabel;
    //</editor-fold>

    //<editor-fold desc="Text Fields">
    JTextField productNameTextField;
    JTextField productBrandTextField;
    JTextField productPackageHasTextField;
    JTextField productKCalTextField;
    JTextField productProteinTextField;
    JTextField productFatTextField;
    JTextField productCarbsTextField;
    JTextField productCommentOptionalCarbsTextField;
    //</editor-fold>
    JComboBox productMacroForComboBox;

    JFrame addProductWindowFrame = new JFrame();

    public AddSingleProductWindow() {
        startAddNewProductWindow();
    }

    private void startAddNewProductWindow() {
        addProductWindowFrame = new JFrame("Diet Tracker");
        addProductWindowFrame.setSize(Config.ADD_PRODUCT_WINDOWS_WIDTH, Config.ADD_PRODUCT_WINDOWS_HEIGHT);

        //Set Layout
        addProductMainPanel.setLayout(new GridLayout(9, 2));
        addProductWindowFrame.setLayout(new BorderLayout());

        setPanels();
        addPanelsToFrame();
        setComponents();
        addComponentsToPanels();


        addProductWindowFrame.setResizable(false);
        addProductWindowFrame.setLocationRelativeTo(null);
        addProductWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addProductWindowFrame.setVisible(true);
        addProductWindowFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void setPanels() {
        addProductPanelNorth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_PANELS_NORTH_SIZE, Config.ADD_PRODUCT_PANELS_NORTH_SIZE));
        addProductPanelEast.setPreferredSize(new Dimension(Config.ADD_PRODUCT_PANELS_WEST_EAST_SIZE, Config.ADD_PRODUCT_PANELS_WEST_EAST_SIZE));
        addProductMainPanel.setPreferredSize(new Dimension(Config.ADD_PRODUCT_PANELS_CENTER, Config.ADD_PRODUCT_PANELS_CENTER));
        addProductPanelWest.setPreferredSize(new Dimension(Config.ADD_PRODUCT_PANELS_WEST_EAST_SIZE, Config.ADD_PRODUCT_PANELS_WEST_EAST_SIZE));
        addProductPanelSouth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_PANELS_SOUTH_SIZE, Config.ADD_PRODUCT_PANELS_SOUTH_SIZE));
    }

    private void addPanelsToFrame() {
        addProductWindowFrame.add(addProductPanelNorth, BorderLayout.NORTH);
        addProductWindowFrame.add(addProductPanelWest, BorderLayout.WEST);
        addProductWindowFrame.add(addProductMainPanel, BorderLayout.CENTER);
        addProductWindowFrame.add(addProductPanelEast, BorderLayout.EAST);
        addProductWindowFrame.add(addProductPanelSouth, BorderLayout.SOUTH);
    }

    private void addComponentsToPanels() {

        //<editor-fold desc="Add components to - Main Panel">
        addProductMainPanel.add(productNameLabel);
        addProductMainPanel.add(productNameTextField);

        addProductMainPanel.add(productBrandLabel);
        addProductMainPanel.add(productBrandTextField);

        addProductMainPanel.add(productPackageHasLabel);
        addProductMainPanel.add(productPackageHasTextField);

        addProductMainPanel.add(productMacroForLabel);
        addProductMainPanel.add(productMacroForComboBox);

        addProductMainPanel.add(productKCalLabel);
        addProductMainPanel.add(productKCalTextField);

        addProductMainPanel.add(productProteinLabel);
        addProductMainPanel.add(productProteinTextField);

        addProductMainPanel.add(productFatLabel);
        addProductMainPanel.add(productFatTextField);

        addProductMainPanel.add(productCarbsLabel);
        addProductMainPanel.add(productCarbsTextField);

        addProductMainPanel.add(productCommentOptionalCarbsLabel);
        addProductMainPanel.add(productCommentOptionalCarbsTextField);
        //</editor-fold>

        //<editor-fold desc="Add components to - South Panel">
        addProductPanelSouth.add(acceptButton);
        addProductPanelSouth.add(backToMainWindowButton);
        addProductPanelSouth.add(exitProgramProductWindowButton);
        addProductPanelSouth.add(clearTextFieldsButton);
        //</editor-fold>

    }

    private void setComponents() {

        //<editor-fold desc="Set Labels">
        productNameLabel = new JLabel("Name:");
        productBrandLabel = new JLabel("Brand:");
        productPackageHasLabel = new JLabel("Product has[g]:");
        productMacroForLabel = new JLabel("Product Macro for[g]/pack");
        productKCalLabel = new JLabel("KCal[g]:");
        productProteinLabel = new JLabel("Protein[g]:");
        productFatLabel = new JLabel("Fat[g]:");
        productCarbsLabel = new JLabel("Carbs[g]:");
        productCommentOptionalCarbsLabel = new JLabel("Comment(optional):");
        //</editor-fold>

        //<editor-fold desc="Set TextFields">
        productNameTextField = new JTextField(Config.ADD_PRODUCT_TEXT_FIELD_SIZE);
        productBrandTextField = new JTextField(Config.ADD_PRODUCT_TEXT_FIELD_SIZE);
        productPackageHasTextField = new JTextField(Config.ADD_PRODUCT_TEXT_FIELD_SIZE);
        productKCalTextField = new JTextField(Config.ADD_PRODUCT_TEXT_FIELD_SIZE);
        productProteinTextField = new JTextField(Config.ADD_PRODUCT_TEXT_FIELD_SIZE);
        productFatTextField = new JTextField(Config.ADD_PRODUCT_TEXT_FIELD_SIZE);
        productCarbsTextField = new JTextField(Config.ADD_PRODUCT_TEXT_FIELD_SIZE);
        productCommentOptionalCarbsTextField = new JTextField(160);
        //</editor-fold>

        //<editor-fold desc="Set Buttons">
        acceptButton = new JButton("Accept");

        acceptButton.addActionListener(new AddNewProductButtonActionListener());
        backToMainWindowButton = new JButton("Go to Start");

        backToMainWindowButton.addActionListener(new BackToMainWindowButtonActionListener());
        exitProgramProductWindowButton = new JButton("Exit application");

        exitProgramProductWindowButton.addActionListener(new ExitApplicationButtonActionListener());
        clearTextFieldsButton = new JButton("Clear");
        clearTextFieldsButton.addActionListener(new clearTextFieldsButtonActionListener());
        //</editor-fold>


        String macroOption[] = {"100g", "package"};
        productMacroForComboBox = new JComboBox<>(macroOption);

    }

    private void acceptProduct() {

        //<editor-fold desc="Set variables to store data in String">
        String[] addNewProductData = new String[Config.howManyParametersToAddProduct];
        String name = "";
        String brand = "";
        String packageHas = "";
        String macroFor = "";
        String kCal = "";
        String protein = "";
        String fat = "";
        String carbs = "";
        String commentOptional = productCommentOptionalCarbsTextField.getText();
        //</editor-fold>

        //<editor-fold desc="Get data from GUI to array in String type">
        name = productNameTextField.getText();
        brand = productBrandTextField.getText();
        if (productPackageHasTextField.getText().equals("")) {
            System.out.println("Product filed is empty");
            packageHas = "0";
        } else {
            System.out.println("Product filed is not empty");
            packageHas = productPackageHasTextField.getText();
        }
        macroFor = productMacroForComboBox.getSelectedItem().toString();
        kCal = productKCalTextField.getText();
        protein = productProteinTextField.getText();
        fat = productFatTextField.getText();
        carbs = productCarbsTextField.getText();

        if (macroFor.equals("package")) {
            macroFor = packageHas;
        } else {
            macroFor = "100";
        }

        try {
            addNewProductData[0] = name;
            addNewProductData[1] = brand;
            addNewProductData[2] = packageHas;
            addNewProductData[3] = macroFor;
            addNewProductData[4] = kCal;
            addNewProductData[5] = protein;
            addNewProductData[6] = fat;
            addNewProductData[7] = carbs;

        } catch (IllegalArgumentException exception) {
            System.out.println("Wrong Type exeption.");
        }
        //</editor-fold>

        boolean passTest = true;

        //<editor-fold desc="Parse data to float type">
        try {
            packageHas = packageHas.replace(',', '.');
            Float.parseFloat(packageHas);
        } catch (Exception ParseToDoubleException) {
            JOptionPane.showMessageDialog(null, "Wrong input in package has text field.");
            passTest = false;
        }

        try {
            kCal = kCal.replace(',', '.');
            Float.parseFloat(kCal);
        } catch (Exception ParseToDoubleException) {
            JOptionPane.showMessageDialog(null, "Wrong input in KCal text field.");
            passTest = false;
        }

        try {
            protein = protein.replace(',', '.');
            Float.parseFloat(protein);
        } catch (Exception ParseToDoubleException) {
            JOptionPane.showMessageDialog(null, "Wrong input in protein text field.");
            passTest = false;
        }

        try {
            fat = fat.replace(',', '.');
            Float.parseFloat(fat);
        } catch (Exception ParseToDoubleException) {
            JOptionPane.showMessageDialog(null, "Wrong input in fat text field.");
            passTest = false;
        }

        try {
            carbs = carbs.replace(',', '.');
            Float.parseFloat(carbs);
        } catch (Exception ParseToDoubleException) {
            JOptionPane.showMessageDialog(null, "Wrong input in carbs text field.");
            passTest = false;
        }
        //</editor-fold>

        Macro newProductMacro = new Macro(Float.parseFloat(kCal), Float.parseFloat(protein),
                Float.parseFloat(fat), Float.parseFloat(carbs));
        Product newProduct = new Product(name, brand, Float.parseFloat(macroFor), newProductMacro, Float.parseFloat(packageHas), commentOptional);

        FilesTools.makeTextFileForProduct(newProduct, Float.parseFloat(macroFor));
        FilesTools.makeSQLTextFileForProduct(newProduct.getProductName(), InsertProductToSQL_Table.createInsertSQLQueryForProductTable(newProduct));
        try {
            InsertProductToSQL_Table.insertProduct(newProduct);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        Log.makeLogForAddNewProductToSQLTable(
                name,
                brand,
                packageHas,
                macroFor, newProductMacro);


        JOptionPane.showMessageDialog(null, "Product add to library." + "Product data:\n\n" + "Name: " + name + "\nBrand: " + brand
                + "\nPackage has: " + packageHas + "\nMacro for: " + macroFor
                + "\nKCal: " + kCal + "\nProtein: " + protein +
                "\nFat: " + fat + "\nCarbs: " + carbs + "\nComment" + commentOptional);
    }

    public class AddNewProductButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == acceptButton) {
                acceptProduct();
            }
        }
    }

    private class BackToMainWindowButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //MainWindow mainWindow = new MainWindow();
            //mainWindow.makeRunWindow();
            addProductWindowFrame.setExtendedState(Frame.ICONIFIED);
        }
    }

    private class ExitApplicationButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class clearTextFieldsButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            productNameTextField.setText("");
            productBrandTextField.setText("");
            productPackageHasTextField.setText("");
            productKCalTextField.setText("");
            productProteinTextField.setText("");
            productFatTextField.setText("");
            productCarbsTextField.setText("");
            productCommentOptionalCarbsTextField.setText("");
        }
    }
}