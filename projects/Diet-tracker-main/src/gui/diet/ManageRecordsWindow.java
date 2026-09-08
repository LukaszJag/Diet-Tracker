package gui.diet;

import tools.sql_tools.general.get.GetConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageRecordsWindow extends JFrame {

    private JComboBox<String> selectModeBox = new JComboBox<>(new String[]{"Products", "Calendar Entries"});
    private JTextField searchField = new JTextField(20);
    private JButton searchButton = new JButton("Find Record");

    private JTextField f1 = new JTextField(15); // name / date
    private JTextField f2 = new JTextField(15); // brand / meal name
    private JTextField f3 = new JTextField(15); // pack weight / amount
    private JTextField f4 = new JTextField(15); // kcal
    private JTextField f5 = new JTextField(15); // protein
    private JTextField f6 = new JTextField(15); // fat
    private JTextField f7 = new JTextField(15); // carbs
    private JTextField f8 = new JTextField(15); // comment

    private JButton saveButton = new JButton("Save Record Modifications");
    private String originalKeyID = ""; // Stores product name or row_id context

    public ManageRecordsWindow() {
        super("Database Modification Utility");
        setupWindow();
    }

    private void setupWindow() {
        setSize(450, 480);
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.add(new JLabel("Mode:"));
        northPanel.add(selectModeBox);
        northPanel.add(new JLabel("Search:"));
        northPanel.add(searchField);
        northPanel.add(searchButton);
        add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        centerPanel.add(new JLabel("Name / Date:"));
        centerPanel.add(f1);
        centerPanel.add(new JLabel("Brand / Meal:"));
        centerPanel.add(f2);
        centerPanel.add(new JLabel("Pack / Consumed weight:"));
        centerPanel.add(f3);
        centerPanel.add(new JLabel("Kcal (per 100g):"));
        centerPanel.add(f4);
        centerPanel.add(new JLabel("Protein:"));
        centerPanel.add(f5);
        centerPanel.add(new JLabel("Fat:"));
        centerPanel.add(f6);
        centerPanel.add(new JLabel("Carbs:"));
        centerPanel.add(f7);
        centerPanel.add(new JLabel("Comments:"));
        centerPanel.add(f8);
        add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.add(saveButton);
        add(southPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> searchRecord());
        saveButton.addActionListener(e -> updateRecord());

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void searchRecord() {
        String input = searchField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Search term cannot be empty!");
            return;
        }

        String mode = selectModeBox.getSelectedItem().toString();
        try (Connection conn = GetConnection.getConnectionWithLocalHost()) {
            if (mode.equals("Products")) {
                String sql = "SELECT * FROM product_table WHERE product_name LIKE ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, "%" + input + "%");
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            originalKeyID = rs.getString("product_name");
                            f1.setText(rs.getString("product_name"));
                            f2.setText(rs.getString("product_brand"));
                            f3.setText(String.valueOf(rs.getFloat("product_package_has")));
                            f4.setText(String.valueOf(rs.getFloat("product_kcal")));
                            f5.setText(String.valueOf(rs.getFloat("product_protein")));
                            f6.setText(String.valueOf(rs.getFloat("product_fat")));
                            f7.setText(String.valueOf(rs.getFloat("product_carbs")));
                            f8.setText(rs.getString("comment_optional"));
                        } else {
                            JOptionPane.showMessageDialog(this, "No matching products found.");
                        }
                    }
                }
            } else {
                String sql = "SELECT * FROM calendar WHERE day_date = ? LIMIT 1";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, input);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            originalKeyID = rs.getString("row_id");
                            f1.setText(rs.getString("day_date"));
                            f2.setText(rs.getString("meal_name"));
                            f3.setText(String.valueOf(rs.getFloat("amount_of_product")));
                            f4.setText(String.valueOf(rs.getFloat("kcal")));
                            f5.setText(String.valueOf(rs.getFloat("protein")));
                            f6.setText(String.valueOf(rs.getFloat("fat")));
                            f7.setText(String.valueOf(rs.getFloat("carbs")));
                            f8.setText(rs.getString("comment_optional"));
                        } else {
                            JOptionPane.showMessageDialog(this, "No calendar entries found for this date.");
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "SQL Failure: " + ex.getMessage());
        }
    }

    private void updateRecord() {
        if (originalKeyID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Search and select a record first.");
            return;
        }

        String mode = selectModeBox.getSelectedItem().toString();
        try (Connection conn = GetConnection.getConnectionWithLocalHost()) {
            if (mode.equals("Products")) {
                String localSql = "UPDATE product_table SET product_name = ?, product_brand = ?, " +
                        "product_package_has = ?, product_kcal = ?, product_protein = ?, " +
                        "product_fat = ?, product_carbs = ?, comment_optional = ? WHERE product_name = ?";

                try (PreparedStatement ps = conn.prepareStatement(localSql)) {
                    ps.setString(1, f1.getText());
                    ps.setString(2, f2.getText());
                    ps.setFloat(3, Float.parseFloat(f3.getText()));
                    ps.setFloat(4, Float.parseFloat(f4.getText()));
                    ps.setFloat(5, Float.parseFloat(f5.getText()));
                    ps.setFloat(6, Float.parseFloat(f6.getText()));
                    ps.setFloat(7, Float.parseFloat(f7.getText()));
                    ps.setString(8, f8.getText());
                    ps.setString(9, originalKeyID);
                    ps.executeUpdate();
                }

                // Push clean MS SQL standard update statement to Azure API
                String azureSql = "UPDATE [diet_tracker_schema].[product_table] SET " +
                        "product_name = '" + f1.getText().replace("'", "''") + "', " +
                        "product_brand = '" + f2.getText().replace("'", "''") + "', " +
                        "product_package_has = " + Float.parseFloat(f3.getText()) + ", " +
                        "product_kcal = " + Float.parseFloat(f4.getText()) + ", " +
                        "product_protein = " + Float.parseFloat(f5.getText()) + ", " +
                        "product_fat = " + Float.parseFloat(f6.getText()) + ", " +
                        "product_carbs = " + Float.parseFloat(f7.getText()) + ", " +
                        "comment_optional = '" + f8.getText().replace("'", "''") + "' " +
                        "WHERE product_name = '" + originalKeyID.replace("'", "''") + "'";

                tools.azure.AzureSqlSync.syncQueryToAzure(azureSql);

            } else {
                float weight = Float.parseFloat(f3.getText());
                float baseKcal = Float.parseFloat(f4.getText());
                float baseProtein = Float.parseFloat(f5.getText());
                float baseFat = Float.parseFloat(f6.getText());
                float baseCarbs = Float.parseFloat(f7.getText());

                float kcalConsume = (baseKcal * weight) / 100.0f;
                float proteinConsume = (baseProtein * weight) / 100.0f;
                float fatConsume = (baseFat * weight) / 100.0f;
                float carbsConsume = (baseCarbs * weight) / 100.0f;

                String localSql = "UPDATE calendar SET day_date = ?, meal_name = ?, amount_of_product = ?, " +
                        "kcal_consume = ?, protein_consume = ?, fat_consume = ?, carbs_consume = ?, " +
                        "comment_optional = ?, is_synced = 0 WHERE row_id = ?";

                try (PreparedStatement ps = conn.prepareStatement(localSql)) {
                    ps.setString(1, f1.getText());
                    ps.setString(2, f2.getText());
                    ps.setFloat(3, weight);
                    ps.setFloat(4, kcalConsume);
                    ps.setFloat(5, proteinConsume);
                    ps.setFloat(6, fatConsume);
                    ps.setFloat(7, carbsConsume);
                    ps.setString(8, f8.getText());
                    ps.setString(9, originalKeyID);
                    ps.executeUpdate();
                }

                String azureSql = "UPDATE [diet_tracker_schema].[calendar] SET " +
                        "day_date = '" + f1.getText().replace("'", "''") + "', " +
                        "meal_name = '" + f2.getText().replace("'", "''") + "', " +
                        "amount_of_product = " + weight + ", " +
                        "kcal_consume = " + kcalConsume + ", " +
                        "protein_consume = " + proteinConsume + ", " +
                        "fat_consume = " + fatConsume + ", " +
                        "carbs_consume = " + carbsConsume + ", " +
                        "comment_optional = '" + f8.getText().replace("'", "''") + "' " +
                        "WHERE row_id = '" + originalKeyID.replace("'", "''") + "'";

                tools.azure.AzureSqlSync.syncQueryToAzure(azureSql, originalKeyID);
            }

            JOptionPane.showMessageDialog(this, "Local update completed. Azure sync queued successfully.");
            originalKeyID = "";
            searchField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed updating resource: " + ex.getMessage());
        }
    }
}