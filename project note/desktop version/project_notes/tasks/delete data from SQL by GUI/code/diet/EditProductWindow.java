package gui.diet;

import tools.sql_tools.general.get.GetConnection;
import tools.azure.AzureSqlSync;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditProductWindow extends JFrame {

    private JRadioButton sourceLocal = new JRadioButton("Local MySQL", true);
    private JRadioButton sourceAzure = new JRadioButton("Azure Cloud SQL");
    private JTextField productSearchField = new JTextField(15);
    private JButton searchButton = new JButton("Search Product");

    private JComboBox<ProductComboBoxItem> matchesComboBox = new JComboBox<>();

    // Edit inputs
    private JTextField nameField = new JTextField(15);
    private JTextField brandField = new JTextField(15);
    private JTextField packWeightField = new JTextField(15);
    private JTextField baseMeasureField = new JTextField(15);
    private JTextField kcalField = new JTextField(15);
    private JTextField proteinField = new JTextField(15);
    private JTextField fatField = new JTextField(15);
    private JTextField carbsField = new JTextField(15);
    private JTextField commentField = new JTextField(15);

    private JButton saveButton = new JButton("Accept & Update Product");
    private String originalProductName = "";

    private static class ProductComboBoxItem {
        String name;
        String displayText;
        Map<String, String> data;

        public ProductComboBoxItem(String name, String displayText, Map<String, String> data) {
            this.name = name;
            this.displayText = displayText;
            this.data = data;
        }

        @Override
        public String toString() {
            return displayText;
        }
    }

    public EditProductWindow() {
        super("Edit Product Library");
        setupUI();
    }

    private void setupUI() {
        setSize(520, 520);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new GridLayout(2, 1));
        JPanel sourceSelector = new JPanel();
        ButtonGroup sourceGroup = new ButtonGroup();
        sourceGroup.add(sourceLocal);
        sourceGroup.add(sourceAzure);
        sourceSelector.add(sourceLocal);
        sourceSelector.add(sourceAzure);
        searchPanel.add(sourceSelector);

        JPanel actionSelector = new JPanel();
        actionSelector.add(new JLabel("Product Name:"));
        actionSelector.add(productSearchField);
        actionSelector.add(searchButton);
        searchPanel.add(actionSelector);
        add(searchPanel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        detailsPanel.add(new JLabel("Product matches:"));
        detailsPanel.add(matchesComboBox);
        detailsPanel.add(new JLabel("Product Name:"));
        detailsPanel.add(nameField);
        detailsPanel.add(new JLabel("Brand:"));
        detailsPanel.add(brandField);
        detailsPanel.add(new JLabel("Package Weight (g):"));
        detailsPanel.add(packWeightField);
        detailsPanel.add(new JLabel("Macro Base (g):"));
        detailsPanel.add(baseMeasureField);
        detailsPanel.add(new JLabel("Kcal (per Base):"));
        detailsPanel.add(kcalField);
        detailsPanel.add(new JLabel("Protein:"));
        detailsPanel.add(proteinField);
        detailsPanel.add(new JLabel("Fat:"));
        detailsPanel.add(fatField);
        detailsPanel.add(new JLabel("Carbohydrates:"));
        detailsPanel.add(carbsField);
        detailsPanel.add(new JLabel("Comments:"));
        detailsPanel.add(commentField);
        add(detailsPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.add(saveButton);
        add(southPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> executeSearch());
        matchesComboBox.addActionListener(e -> handleSelectionChange());
        saveButton.addActionListener(e -> handleSave());

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void executeSearch() {
        String inputVal = productSearchField.getText().trim();
        if (inputVal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Search keyword cannot be blank.");
            return;
        }

        matchesComboBox.removeAllItems();
        List<Map<String, String>> results = new ArrayList<>();

        if (sourceLocal.isSelected()) {
            results = queryLocalProducts(inputVal);
        } else {
            results = queryAzureProducts(inputVal);
        }

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No products matched this keyword.");
            return;
        }

        for (Map<String, String> row : results) {
            String name = row.get("product_name");
            String display = name + " (" + row.get("product_brand") + ")";
            matchesComboBox.addItem(new ProductComboBoxItem(name, display, row));
        }

        JOptionPane.showMessageDialog(this, "Product search completed. Found " + results.size() + " matches.");
    }

    private void handleSelectionChange() {
        ProductComboBoxItem item = (ProductComboBoxItem) matchesComboBox.getSelectedItem();
        if (item == null) return;

        Map<String, String> data = item.data;
        originalProductName = item.name;

        nameField.setText(data.get("product_name"));
        brandField.setText(data.get("product_brand"));
        packWeightField.setText(data.get("product_package_has") != null ? data.get("product_package_has") : data.get("product_pack_weight"));
        baseMeasureField.setText(data.get("product_macro_for"));
        kcalField.setText(data.get("product_kcal"));
        proteinField.setText(data.get("product_protein"));
        fatField.setText(data.get("product_fat"));
        carbsField.setText(data.get("product_carbs"));
        commentField.setText(data.get("comment_optional") != null ? data.get("comment_optional") : data.get("product_comment"));

        JOptionPane.showMessageDialog(this, "Product data pulled successfully!");
    }

    private void handleSave() {
        if (originalProductName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a product from matches ComboBox before trying to save updates.");
            return;
        }

        try {
            float packageHas = Float.parseFloat(packWeightField.getText());
            float macroFor = Float.parseFloat(baseMeasureField.getText());
            float kcal = Float.parseFloat(kcalField.getText());
            float protein = Float.parseFloat(proteinField.getText());
            float fat = Float.parseFloat(fatField.getText());
            float carbs = Float.parseFloat(carbsField.getText());

            // 1. Update Local MySQL
            try (Connection conn = GetConnection.getConnectionWithLocalHost()) {
                String localSql = "UPDATE product_table SET product_name = ?, product_brand = ?, " +
                        "product_package_has = ?, product_macro_for = ?, product_kcal = ?, " +
                        "product_protein = ?, product_fat = ?, product_carbs = ?, comment_optional = ? WHERE product_name = ?";

                try (PreparedStatement ps = conn.prepareStatement(localSql)) {
                    ps.setString(1, nameField.getText());
                    ps.setString(2, brandField.getText());
                    ps.setFloat(3, packageHas);
                    ps.setFloat(4, macroFor);
                    ps.setFloat(5, kcal);
                    ps.setFloat(6, protein);
                    ps.setFloat(7, fat);
                    ps.setFloat(8, carbs);
                    ps.setString(9, commentField.getText());
                    ps.setString(10, originalProductName);
                    ps.executeUpdate();
                }
            }

            // 2. Sync update to Azure SQL database
            String azureSql = "UPDATE [diet_tracker_schema].[product_table] SET " +
                    "product_name = '" + nameField.getText().replace("'", "''") + "', " +
                    "product_brand = '" + brandField.getText().replace("'", "''") + "', " +
                    "product_package_has = " + packageHas + ", " +
                    "product_macro_for = " + macroFor + ", " +
                    "product_kcal = " + kcal + ", " +
                    "product_protein = " + protein + ", " +
                    "product_fat = " + fat + ", " +
                    "product_carbs = " + carbs + ", " +
                    "comment_optional = '" + commentField.getText().replace("'", "''") + "' " +
                    "WHERE product_name = '" + originalProductName.replace("'", "''") + "'";

            AzureSqlSync.syncQueryToAzure(azureSql);

            JOptionPane.showMessageDialog(this, "Local database and Azure SQL updated successfully.");
            clearForm();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Numeric input formats are invalid. Please confirm entries are floats.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed saving changes locally: " + ex.getMessage());
        }
    }

    private List<Map<String, String>> queryLocalProducts(String keyword) {
        List<Map<String, String>> list = new ArrayList<>();
        String sql = "SELECT * FROM product_table WHERE product_name LIKE ?";
        try (Connection conn = GetConnection.getConnectionWithLocalHost();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("product_name", rs.getString("product_name"));
                    row.put("product_brand", rs.getString("product_brand"));
                    row.put("product_package_has", String.valueOf(rs.getFloat("product_package_has")));
                    row.put("product_macro_for", String.valueOf(rs.getFloat("product_macro_for")));
                    row.put("product_kcal", String.valueOf(rs.getFloat("product_kcal")));
                    row.put("product_protein", String.valueOf(rs.getFloat("product_protein")));
                    row.put("product_fat", String.valueOf(rs.getFloat("product_fat")));
                    row.put("product_carbs", String.valueOf(rs.getFloat("product_carbs")));
                    row.put("comment_optional", rs.getString("comment_optional"));
                    list.add(row);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private List<Map<String, String>> queryAzureProducts(String keyword) {
        try {
            String azureQuery = "SELECT * FROM [diet_tracker_schema].[product_table] WHERE [product_name] LIKE '%" + keyword.replace("'", "''") + "%'";
            String escapedQuery = azureQuery.replace("\\", "\\\\").replace("\"", "\\\"");
            String jsonPayload = "{\"sql_query\":\"" + escapedQuery + "\"}";

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(15))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://diettrackerandroidversionapi-grcbhva9e5gqhzhz.polandcentral-01.azurewebsites.net/api/AndroidAzure"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return parseJson(response.body());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<Map<String, String>> parseJson(String json) {
        List<Map<String, String>> results = new ArrayList<>();
        if (!json.contains("[")) return results;

        String clean = json.trim().substring(1, json.trim().length() - 1);
        String[] objects = clean.split("\\},\\{");
        for (String obj : objects) {
            Map<String, String> map = new HashMap<>();
            String item = obj.replace("{", "").replace("}", "");
            String[] pairs = item.split(",");
            for (String pair : pairs) {
                String[] kv = pair.split(":");
                if (kv.length == 2) {
                    String k = kv[0].replace("\"", "").trim();
                    String v = kv[1].replace("\"", "").trim();
                    map.put(k, v);
                }
            }
            results.add(map);
        }
        return results;
    }

    private void clearForm() {
        originalProductName = "";
        matchesComboBox.removeAllItems();
        nameField.setText("");
        brandField.setText("");
        packWeightField.setText("");
        baseMeasureField.setText("");
        kcalField.setText("");
        proteinField.setText("");
        fatField.setText("");
        carbsField.setText("");
        commentField.setText("");
    }
}