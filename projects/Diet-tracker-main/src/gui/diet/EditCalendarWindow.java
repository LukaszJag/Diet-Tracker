package gui.diet;

import tools.azure.AzureSqlSync;
import tools.sql_tools.days_statistics.UpdateDaysStatisticsFilledData;
import tools.sql_tools.general.get.GetConnection;
import tools.sql_tools.general.run.RunQuery;

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

public class EditCalendarWindow extends JFrame {

    private JRadioButton sourceLocal = new JRadioButton("Local MySQL", true);
    private JRadioButton sourceAzure = new JRadioButton("Azure Cloud SQL");
    private JTextField dateSearchField = new JTextField(10);
    private JButton searchButton = new JButton("Search Date");

    private JComboBox<CalendarComboBoxItem> matchesComboBox = new JComboBox<>();

    // Detail input text fields
    private JTextField dateField = new JTextField(15);
    private JTextField dayNameField = new JTextField(15);
    private JTextField mealNameField = new JTextField(15);
    private JTextField productNameField = new JTextField(15);
    private JTextField amountField = new JTextField(15);
    private JTextField baseKcalField = new JTextField(15);
    private JTextField baseProteinField = new JTextField(15);
    private JTextField baseFatField = new JTextField(15);
    private JTextField baseCarbsField = new JTextField(15);
    private JTextField commentField = new JTextField(15);

    // Action Buttons
    private JButton saveButton = new JButton("Save Changes (All DBs)");
    private JButton deleteButton = new JButton("Delete Record (All DBs)");
    private String selectedRowId = "";

    private static class CalendarComboBoxItem {
        String rowId;
        String displayText;
        Map<String, String> data;

        public CalendarComboBoxItem(String rowId, String displayText, Map<String, String> data) {
            this.rowId = rowId;
            this.displayText = displayText;
            this.data = data;
        }

        @Override
        public String toString() {
            return displayText;
        }
    }

    public EditCalendarWindow() {
        super("Edit & Delete Calendar Entries (MySQL & Azure)");
        setupUI();
    }

    private void setupUI() {
        setSize(560, 620);
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
        actionSelector.add(new JLabel("Search Date (YYYY-MM-DD):"));
        actionSelector.add(dateSearchField);
        actionSelector.add(searchButton);
        searchPanel.add(actionSelector);
        add(searchPanel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel(new GridLayout(11, 2, 5, 5));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        detailsPanel.add(new JLabel("Match found inside database:"));
        detailsPanel.add(matchesComboBox);
        detailsPanel.add(new JLabel("Log Date:"));
        detailsPanel.add(dateField);
        detailsPanel.add(new JLabel("Day Name:"));
        detailsPanel.add(dayNameField);
        detailsPanel.add(new JLabel("Meal Type:"));
        detailsPanel.add(mealNameField);
        detailsPanel.add(new JLabel("Product Name:"));
        detailsPanel.add(productNameField);
        detailsPanel.add(new JLabel("Consumed Weight (g):"));
        detailsPanel.add(amountField);
        detailsPanel.add(new JLabel("Base Kcal (per 100g):"));
        detailsPanel.add(baseKcalField);
        detailsPanel.add(new JLabel("Base Protein (per 100g):"));
        detailsPanel.add(baseProteinField);
        detailsPanel.add(new JLabel("Base Fat (per 100g):"));
        detailsPanel.add(baseFatField);
        detailsPanel.add(new JLabel("Base Carbs (per 100g):"));
        detailsPanel.add(baseCarbsField);
        detailsPanel.add(new JLabel("Optional Comment:"));
        detailsPanel.add(commentField);
        add(detailsPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        saveButton.setBackground(new Color(40, 167, 69));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 12));

        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));

        southPanel.add(saveButton);
        southPanel.add(deleteButton);
        add(southPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> executeSearch());
        matchesComboBox.addActionListener(e -> handleSelectionChange());
        saveButton.addActionListener(e -> handleSave());
        deleteButton.addActionListener(e -> handleDelete());

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void executeSearch() {
        String dateVal = dateSearchField.getText().trim();
        if (dateVal.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid search date.");
            return;
        }

        matchesComboBox.removeAllItems();
        List<Map<String, String>> results = sourceLocal.isSelected()
                ? queryLocalCalendar(dateVal)
                : queryAzureCalendar(dateVal);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No records found on the selected database source.");
            return;
        }

        for (Map<String, String> row : results) {
            String rowId = row.get("row_id");
            String display = "[" + row.get("meal_name") + "] " + row.get("product_name") + " (" + row.get("amount_of_product") + "g)";
            matchesComboBox.addItem(new CalendarComboBoxItem(rowId, display, row));
        }

        JOptionPane.showMessageDialog(this, "Found " + results.size() + " matches.");
    }

    private void handleSelectionChange() {
        CalendarComboBoxItem item = (CalendarComboBoxItem) matchesComboBox.getSelectedItem();
        if (item == null) return;

        Map<String, String> data = item.data;
        selectedRowId = item.rowId;

        dateField.setText(data.get("day_date") != null ? data.get("day_date").substring(0, 10) : "");
        dayNameField.setText(data.get("day_name"));
        mealNameField.setText(data.get("meal_name"));
        productNameField.setText(data.get("product_name"));
        amountField.setText(data.get("amount_of_product"));
        baseKcalField.setText(data.get("kcal"));
        baseProteinField.setText(data.get("protein"));
        baseFatField.setText(data.get("fat"));
        baseCarbsField.setText(data.get("carbs"));
        commentField.setText(data.get("comment_optional"));
    }

    private void handleSave() {
        if (selectedRowId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an entry first.");
            return;
        }

        try {
            float weight = Float.parseFloat(amountField.getText().replace(',', '.'));
            float baseKcal = Float.parseFloat(baseKcalField.getText().replace(',', '.'));
            float baseProtein = Float.parseFloat(baseProteinField.getText().replace(',', '.'));
            float baseFat = Float.parseFloat(baseFatField.getText().replace(',', '.'));
            float baseCarbs = Float.parseFloat(baseCarbsField.getText().replace(',', '.'));

            float kcalConsume = (baseKcal * weight) / 100.0f;
            float proteinConsume = (baseProtein * weight) / 100.0f;
            float fatConsume = (baseFat * weight) / 100.0f;
            float carbsConsume = (baseCarbs * weight) / 100.0f;

            String logDate = dateField.getText().trim();

            // 1. Update Local MySQL
            try (Connection conn = GetConnection.getConnectionWithLocalHost()) {
                String localSql = "UPDATE calendar SET day_date = ?, day_name = ?, meal_name = ?, product_name = ?, " +
                        "amount_of_product = ?, kcal = ?, protein = ?, fat = ?, carbs = ?, " +
                        "kcal_consume = ?, protein_consume = ?, fat_consume = ?, carbs_consume = ?, " +
                        "comment_optional = ?, is_synced = 0 WHERE row_id = ?";

                try (PreparedStatement ps = conn.prepareStatement(localSql)) {
                    ps.setString(1, logDate);
                    ps.setString(2, dayNameField.getText());
                    ps.setString(3, mealNameField.getText());
                    ps.setString(4, productNameField.getText());
                    ps.setFloat(5, weight);
                    ps.setFloat(6, baseKcal);
                    ps.setFloat(7, baseProtein);
                    ps.setFloat(8, baseFat);
                    ps.setFloat(9, baseCarbs);
                    ps.setFloat(10, kcalConsume);
                    ps.setFloat(11, proteinConsume);
                    ps.setFloat(12, fatConsume);
                    ps.setFloat(13, carbsConsume);
                    ps.setString(14, commentField.getText());
                    ps.setString(15, selectedRowId);
                    ps.executeUpdate();
                }
            }

            // 2. Sync Update statement with Azure SQL
            String azureSql = "UPDATE [diet_tracker_schema].[calendar] SET " +
                    "day_date = '" + logDate.replace("'", "''") + "', " +
                    "day_name = '" + dayNameField.getText().replace("'", "''") + "', " +
                    "meal_name = '" + mealNameField.getText().replace("'", "''") + "', " +
                    "product_name = '" + productNameField.getText().replace("'", "''") + "', " +
                    "amount_of_product = " + weight + ", " +
                    "kcal = " + baseKcal + ", " +
                    "protein = " + baseProtein + ", " +
                    "fat = " + baseFat + ", " +
                    "carbs = " + baseCarbs + ", " +
                    "kcal_consume = " + kcalConsume + ", " +
                    "protein_consume = " + proteinConsume + ", " +
                    "fat_consume = " + fatConsume + ", " +
                    "carbs_consume = " + carbsConsume + ", " +
                    "comment_optional = '" + commentField.getText().replace("'", "''") + "' " +
                    "WHERE row_id = '" + selectedRowId.replace("'", "''") + "'";

            AzureSqlSync.syncQueryToAzure(azureSql, selectedRowId);

            // 3. Recalculate local day statistics
            refreshDayStatistics(logDate);

            JOptionPane.showMessageDialog(this, "Row successfully updated in Local MySQL and Azure SQL!");
            executeSearch();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please verify all numerical values are valid numbers.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed updating locally: " + ex.getMessage());
        }
    }

    private void handleDelete() {
        if (selectedRowId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an entry to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to permanently delete this row from BOTH Local MySQL and Azure SQL?\nProduct: "
                        + productNameField.getText() + " (Row ID: " + selectedRowId + ")",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String logDate = dateField.getText().trim();

        try {
            // 1. Delete from Local MySQL
            try (Connection conn = GetConnection.getConnectionWithLocalHost()) {
                String localDelete = "DELETE FROM calendar WHERE row_id = ?";
                try (PreparedStatement ps = conn.prepareStatement(localDelete)) {
                    ps.setString(1, selectedRowId);
                    ps.executeUpdate();
                }
            }

            // 2. Delete from Azure Cloud SQL
            String azureDelete = "DELETE FROM [diet_tracker_schema].[calendar] WHERE [row_id] = '"
                    + selectedRowId.replace("'", "''") + "'";
            AzureSqlSync.syncQueryToAzure(azureDelete);

            // 3. Recalculate day statistics locally
            refreshDayStatistics(logDate);

            JOptionPane.showMessageDialog(this, "Row permanently deleted from Local and Azure databases!");
            clearForm();
            executeSearch();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting locally: " + ex.getMessage());
        }
    }

    private void refreshDayStatistics(String date) {
        if (date == null || date.isEmpty()) return;
        try {
            String updateMacrosSql = UpdateDaysStatisticsFilledData.prepareQueryForFillConsumedMacro(date);
            RunQuery.runQuery(updateMacrosSql);

            String updatePointsSql = UpdateDaysStatisticsFilledData.prepareQueryForUpdateAmountOfFilledPointsFromNotepad(date);
            RunQuery.runQuery(updatePointsSql);
        } catch (Exception ex) {
            System.err.println("Could not refresh statistics for date " + date + ": " + ex.getMessage());
        }
    }

    private List<Map<String, String>> queryLocalCalendar(String date) {
        List<Map<String, String>> list = new ArrayList<>();
        String sql = "SELECT * FROM calendar WHERE day_date = ?";
        try (Connection conn = GetConnection.getConnectionWithLocalHost();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, date);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("row_id", rs.getString("row_id"));
                    row.put("day_date", rs.getString("day_date"));
                    row.put("day_name", rs.getString("day_name"));
                    row.put("meal_name", rs.getString("meal_name"));
                    row.put("product_name", rs.getString("product_name"));
                    row.put("amount_of_product", String.valueOf(rs.getFloat("amount_of_product")));
                    row.put("kcal", String.valueOf(rs.getFloat("kcal")));
                    row.put("protein", String.valueOf(rs.getFloat("protein")));
                    row.put("fat", String.valueOf(rs.getFloat("fat")));
                    row.put("carbs", String.valueOf(rs.getFloat("carbs")));
                    row.put("comment_optional", rs.getString("comment_optional"));
                    list.add(row);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private List<Map<String, String>> queryAzureCalendar(String date) {
        try {
            String azureQuery = "SELECT * FROM [diet_tracker_schema].[calendar] WHERE [day_date] LIKE '" + date.replace("'", "''") + "%'";
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
        selectedRowId = "";
        matchesComboBox.removeAllItems();
        dateField.setText("");
        dayNameField.setText("");
        mealNameField.setText("");
        productNameField.setText("");
        amountField.setText("");
        baseKcalField.setText("");
        baseProteinField.setText("");
        baseFatField.setText("");
        baseCarbsField.setText("");
        commentField.setText("");
    }
}