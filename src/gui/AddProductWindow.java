package gui;

import configuration.Config;
import sql_tools.InsertProductToSQL_Table;
import products_tools.Macro;
import products_tools.Product;
import text_files_tools.MakeFoldersAndTextFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddProductWindow {

    JPanel addProductMainPanel = new JPanel();
    JPanel addProductPanelNorth = new JPanel();
    JPanel addProductPanelWest = new JPanel();
    JPanel addProductPanelEast = new JPanel();
    JPanel addProductPanelSouth = new JPanel();
    JButton acceptButton;
    JFrame addProductWindowFrame = new JFrame();
    //Declare Text Fields
    JTextField productNameTextField;
    JTextField productBrandTextField;
    JTextField productPackageHasTextField;
    JComboBox productMacroForComboBox;
    JTextField productKCalTextField;
    JTextField productProteinTextField;
    JTextField productFatTextField;
    JTextField productCarbsTextField;

    public AddProductWindow() {
        startAddNewProductWindow();
    }

    private void startAddNewProductWindow() {
        addProductWindowFrame = new JFrame("Diet Tracker");
        addProductWindowFrame.setSize(Config.ADD_PRODUCT_WINDOWS_WIDTH, Config.ADD_PRODUCT_WINDOWS_HEIGHT);
        //Set Layout
        addProductMainPanel.setLayout(new GridLayout(8, 2));
        addProductWindowFrame.setLayout(new BorderLayout());

        acceptButton = new JButton("Accept");
        addProductPanelSouth.add(acceptButton);
        acceptButton.addActionListener(new AddNewProductButtonActionListener());

        addProductWindowFrame.add(addProductPanelNorth, BorderLayout.NORTH);
        addProductWindowFrame.add(addProductPanelWest, BorderLayout.WEST);
        addProductWindowFrame.add(addProductMainPanel, BorderLayout.CENTER);
        addProductWindowFrame.add(addProductPanelEast, BorderLayout.EAST);
        addProductWindowFrame.add(addProductPanelSouth, BorderLayout.SOUTH);

        addProductPanelNorth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_PANELS_NORTH_SIZE, Config.ADD_PRODUCT_PANELS_NORTH_SIZE));
        addProductPanelEast.setPreferredSize(new Dimension(Config.ADD_PRODUCT_PANELS_WEST_EAST_SIZE, Config.ADD_PRODUCT_PANELS_WEST_EAST_SIZE));

        addProductMainPanel.setPreferredSize(new Dimension(Config.ADD_PRODUCT_PANELS_CENTER, Config.ADD_PRODUCT_PANELS_CENTER));

        addProductPanelWest.setPreferredSize(new Dimension(Config.ADD_PRODUCT_PANELS_WEST_EAST_SIZE, Config.ADD_PRODUCT_PANELS_WEST_EAST_SIZE));
        addProductPanelSouth.setPreferredSize(new Dimension(Config.ADD_PRODUCT_PANELS_SOUTH_SIZE, Config.ADD_PRODUCT_PANELS_SOUTH_SIZE));

        String macroOption[] = {"100g", "package"};
        productMacroForComboBox = new JComboBox<>(macroOption);

        //Set Labels
        JLabel productNameLabel = new JLabel("Name:");
        JLabel productBrandLabel = new JLabel("Brand:");
        JLabel productPackageHasLabel = new JLabel("Product has[g]:");
        JLabel productMacroForLabel = new JLabel("Product Macro for[g]/pack");
        JLabel productKCalLabel = new JLabel("KCal[g]:");
        JLabel productProteinLabel = new JLabel("Protein[g]:");
        JLabel productFatLabel = new JLabel("Fat[g]:");
        JLabel productCarbsLabel = new JLabel("Carbs[g]:");

        //Set Text Field
        productNameTextField = new JTextField(Config.ADD_PRODUCT_TEXT_FIELD_SIZE);
        productBrandTextField = new JTextField(Config.ADD_PRODUCT_TEXT_FIELD_SIZE);
        productPackageHasTextField = new JTextField(Config.ADD_PRODUCT_TEXT_FIELD_SIZE);
        productKCalTextField = new JTextField(Config.ADD_PRODUCT_TEXT_FIELD_SIZE);
        productProteinTextField = new JTextField(Config.ADD_PRODUCT_TEXT_FIELD_SIZE);
        productFatTextField = new JTextField(Config.ADD_PRODUCT_TEXT_FIELD_SIZE);
        productCarbsTextField = new JTextField(Config.ADD_PRODUCT_TEXT_FIELD_SIZE);

        //Add components to panel
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



        addProductWindowFrame.setResizable(false);
        addProductWindowFrame.setLocationRelativeTo(null);
        addProductWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addProductWindowFrame.setVisible(true);
    }

    private void setPanels(){

    }
    public class AddNewProductButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == acceptButton) {
                String[] addNewProductData = new String[Config.howManyParametersToAddProduct];
                String name = "";
                String brand = "";
                String packageHas = "";
                String macroFor = "";
                String kCal = "";
                String protein = "";
                String fat = "";
                String carbs = "";

                name = productNameTextField.getText();
                brand = productBrandTextField.getText();
                packageHas = productPackageHasTextField.getText();
                macroFor = productMacroForComboBox.getSelectedItem().toString();
                kCal = productKCalTextField.getText();
                protein = productProteinTextField.getText();
                fat = productFatTextField.getText();
                carbs = productCarbsTextField.getText();

                if(macroFor.equals("package")){
                    macroFor = packageHas;
                }else {
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

                }catch (IllegalArgumentException exception){
                    System.out.println("Wrong Type exeption.");
                }

                boolean passTest = true;

                try {
                    Float.parseFloat(packageHas);
                }catch(Exception ParseToDoubleException){
                    JOptionPane.showMessageDialog(null, "Wrong input in package has text field.");
                    passTest = false;
                }

                try {
                    Float.parseFloat(kCal);
                }catch(Exception ParseToDoubleException){
                    JOptionPane.showMessageDialog(null, "Wrong input in KCal text field.");
                    passTest = false;
                }

                try {
                    Float.parseFloat(protein);
                }catch(Exception ParseToDoubleException){
                    JOptionPane.showMessageDialog(null, "Wrong input in protein text field.");
                    passTest = false;
                }

                try {
                    Float.parseFloat(fat);
                }catch(Exception ParseToDoubleException){
                    JOptionPane.showMessageDialog(null, "Wrong input in fat text field.");
                    passTest = false;
                }

                try {
                    Float.parseFloat(carbs);
                }catch(Exception ParseToDoubleException){
                    JOptionPane.showMessageDialog(null, "Wrong input in carbs text field.");
                    passTest = false;
                }
                JOptionPane.showMessageDialog(null, "Product data:\n\n" + "Name: " + name +"\nBrand: " + brand
                        + "\nPackage has: " + packageHas + "\nMacro for: " + macroFor
                        + "\nKCal: " + kCal + "\nProtein: " + protein +
                        "\nFat: " + fat + "\nCarbs: " + carbs);

                Macro newProductMacro = new Macro(Float.parseFloat(kCal), Float.parseFloat(protein),
                        Float.parseFloat(fat), Float.parseFloat(carbs));
                Product newProduct = new Product(name, brand, Float.parseFloat(macroFor), newProductMacro, Float.parseFloat(packageHas));
                MakeFoldersAndTextFile.makeTextFileForProduct(newProduct, Float.parseFloat(packageHas));

                try {
                    InsertProductToSQL_Table.insertProduct(newProduct);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, "Product add to library");


            }
        }
    }
}