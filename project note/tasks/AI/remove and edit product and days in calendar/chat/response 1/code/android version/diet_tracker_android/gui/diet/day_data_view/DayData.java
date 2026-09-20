package com.lukaszjag.diet_tracker_android.gui.diet.day_data_view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.AzureDataCallback;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.GetFromSQLDatabase;
import com.lukaszjag.diet_tracker_android.tools.date_tools.MyDate;
import com.lukaszjag.diet_tracker_android.tools.products_tools.MyAdapterProduct;
import com.lukaszjag.diet_tracker_android.tools.products_tools.Product;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.QueryMaker;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.RowInTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DayData extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapterProduct adapter;
    private Button dateDataButton;
    private Button prevDayButton;
    private Button nextDayButton;
    private TextView dateDataTextView;
    private TextView dayNameTextView;
    private TextView noDataTextView;

    // Summary views
    private LinearLayout summaryLayout;
    private TextView totalKcalTextView;
    private TextView totalProteinTextView;
    private TextView totalFatTextView;
    private TextView totalCarbsTextView;

    private ArrayList<RowInTable> products = new ArrayList<>();
    private String dateOfData;

    private final SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private final SimpleDateFormat dayNameFormat = new SimpleDateFormat("EEEE", Locale.getDefault());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_data_layout);

        dateOfData = MyDate.getCurrentDayInSQLFormat();

        setupAllElements();
        addListeners();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize and set Adapter
        adapter = new MyAdapterProduct();
        recyclerView.setAdapter(adapter);

        // Adapter Click Listener
        adapter.setOnProductClickListener(new MyAdapterProduct.OnProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                showFullProductDataDialog(product);
            }
        });

        // Pull active date records
        updateDateAndFetch();
    }

    private void setupAllElements() {
        dateDataButton = findViewById(R.id.datePickButton);
        prevDayButton = findViewById(R.id.prevDayButton);
        nextDayButton = findViewById(R.id.nextDayButton);
        dateDataTextView = findViewById(R.id.dateTextView);
        dayNameTextView = findViewById(R.id.dayNameTextView);
        noDataTextView = findViewById(R.id.noDataTextView);

        // Summary components
        summaryLayout = findViewById(R.id.summaryLayout);
        totalKcalTextView = findViewById(R.id.totalKcalTextView);
        totalProteinTextView = findViewById(R.id.totalProteinTextView);
        totalFatTextView = findViewById(R.id.totalFatTextView);
        totalCarbsTextView = findViewById(R.id.totalCarbsTextView);
    }

    private void addListeners() {
        dateDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select a Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();

                datePicker.addOnPositiveButtonClickListener(selection -> {
                    dateOfData = sqlDateFormat.format(new Date(selection));
                    updateDateAndFetch();
                });

                datePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        prevDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shiftDate(-1);
            }
        });

        nextDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shiftDate(1);
            }
        });
    }

    private void shiftDate(int amount) {
        try {
            Date currentDate = sqlDateFormat.parse(dateOfData);
            if (currentDate != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(currentDate);
                cal.add(Calendar.DATE, amount);
                dateOfData = sqlDateFormat.format(cal.getTime());
                updateDateAndFetch();
            }
        } catch (Exception e) {
            Log.e("DayData", "Error updating calendar selection offset: " + e.getMessage());
        }
    }

    private void updateDateAndFetch() {
        dateDataTextView.setText(dateOfData);
        updateDayOfWeekName(dateOfData);
        getDataForDate(dateOfData);
    }

    private void updateDayOfWeekName(String sqlDateStr) {
        try {
            Date date = sqlDateFormat.parse(sqlDateStr);
            if (date != null) {
                String nameOfDay = dayNameFormat.format(date);
                dayNameTextView.setText(nameOfDay);
            }
        } catch (Exception e) {
            dayNameTextView.setText("");
        }
    }

    private void getDataForDate(String dateInSQLFormat) {
        String query = QueryMaker.getAllProductsFromCalendarTableLikeDate(dateInSQLFormat);

        GetFromSQLDatabase.runAzureQueryAIChat(query, new AzureDataCallback() {
            @Override
            public void onSuccess(ArrayList<RowInTable> resultTable) {
                products = resultTable;
                setDataToView();
            }

            @Override
            public void onFailure(String errorMessage) {
                System.out.println("Failed to get data: " + errorMessage);
                Toast.makeText(DayData.this, "Connection Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataToView() {
        adapter.clearItems();

        if (products == null || products.isEmpty()) {
            noDataTextView.setVisibility(View.VISIBLE);
            summaryLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            return;
        }

        noDataTextView.setVisibility(View.GONE);
        summaryLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        float totalKcal = 0;
        float totalProtein = 0;
        float totalFat = 0;
        float totalCarbs = 0;

        for (RowInTable row : products) {
            String productName = row.getValue("product_name");
            String mealName = row.getValue("meal_name");
            String amountOfProduct = row.getValue("amount_of_product");
            String kcalConsume = row.getValue("kcal_consume");
            String proteinConsume = row.getValue("protein_consume");
            String fatConsume = row.getValue("fat_consume");
            String carbsConsume = row.getValue("carbs_consume");
            String comment = row.getValue("comment_optional");

            float amount = safeParseFloat(amountOfProduct);
            float kcal = safeParseFloat(kcalConsume);
            float protein = safeParseFloat(proteinConsume);
            float fat = safeParseFloat(fatConsume);
            float carbs = safeParseFloat(carbsConsume);

            totalKcal += kcal;
            totalProtein += protein;
            totalFat += fat;
            totalCarbs += carbs;

            Product entry = new Product(productName, mealName, amount, kcal, protein, fat, carbs, comment);
            adapter.addItem(entry);
        }

        // Format all summary values to exactly two decimal places
        totalKcalTextView.setText(String.format(Locale.getDefault(), "Kcal: %.2f", totalKcal));
        totalProteinTextView.setText(String.format(Locale.getDefault(), "Prot: %.2fg", totalProtein));
        totalFatTextView.setText(String.format(Locale.getDefault(), "Fat: %.2fg", totalFat));
        totalCarbsTextView.setText(String.format(Locale.getDefault(), "Carb: %.2fg", totalCarbs));
    }

    private float safeParseFloat(String value) {
        if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("null")) {
            return 0f;
        }
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    private void showFullProductDataDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(product.getProductName());

        StringBuilder text = new StringBuilder();
        text.append("Meal: ").append(product.getMealName() != null ? product.getMealName() : "N/A").append("\n");
        text.append("Amount: ").append(product.getProductMeasureOfProductWeightToCalculateMacro()).append(" g\n\n");

        text.append("Macro Calculations Consumed:\n");
        text.append("- Calories: ").append(product.getConsumedKcal()).append(" kcal\n");
        text.append("- Protein: ").append(product.getConsumedProtein()).append(" g\n");
        text.append("- Fat: ").append(product.getConsumedFat()).append(" g\n");
        text.append("- Carbohydrates: ").append(product.getConsumedCarbs()).append(" g\n\n");

        if (product.getCommentOptional() != null && !product.getCommentOptional().isEmpty() && !product.getCommentOptional().equalsIgnoreCase("null")) {
            text.append("Comment: ").append(product.getCommentOptional());
        }

        builder.setMessage(text.toString());
        builder.setPositiveButton("Close", null);
        builder.show();
    }
}