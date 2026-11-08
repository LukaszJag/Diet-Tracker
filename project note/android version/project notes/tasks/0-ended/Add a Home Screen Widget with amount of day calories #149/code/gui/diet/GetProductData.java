package com.lukaszjag.diet_tracker_android.gui.diet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.AzureDataCallback;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.GetFromSQLDatabase;
import com.lukaszjag.diet_tracker_android.tools.date_tools.MyDate;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.QueryMaker;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.RowInTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class GetProductData extends AppCompatActivity {

    //<editor-fold desc="Global variables">
    ArrayList<RowInTable> products = new ArrayList<>();
    int counter = 0;
    String productComment = "none";

    //<editor-fold desc="UI components">
    private Spinner productSpinner;
    private Spinner mealNameSpinner;

    //<editor-fold desc="Button">
    private Button getProductFromDatabase;
    private Button previousButton;
    private Button nextButton;
    private Button dateDataButton;
    private Button productCommentButton;
    private Button acceptButton;
    //</editor-fold>

    //<editor-fold desc="EditText">
    private EditText productNameEditText;
    private EditText weightEditText;
    //</editor-fold>

    //<editor-fold desc="TextView">
    private TextView productNameTextView;
    private TextView brandLabel;
    private TextView kcalLabel;
    private TextView proteinLabel;
    private TextView fatLabel;
    private TextView carbsLabel;
    private TextView brandValueTextView;
    private TextView kcalValueTextView;
    private TextView proteinValueTextView;
    private TextView fatValueTextView;
    private TextView carbsValueTextView;

    private TextView dateDataTextView;
    //</editor-fold>

    //<editor-fold desc="Gather Information From UI - Variables">
    String dayDate;
    String dayName;
    String mealName;
    double amountOfProduct;
    String productName;
    String productBrand = "";
    String timeOptional;
    String commentOptional;

    //<editor-fold desc="Macro variables">
    double kcal;
    double protein;
    double fat;
    double carbs;

    double kcalConsume;
    double carbsConsume;
    double proteinConsume;
    double fatConsume;

    String rawKcal;
    String rawProtein;
    String rawFat;
    String rawCarbs;
    //</editor-fold>
    //</editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.get_product_data);
        setupAllElements();
    }

    private void setupAllElements() {
        initUIComponents();
        setupComponents();
        addListeners();
    }

    private void setupComponents() {
        ArrayList<String> mealNamesList = new ArrayList<>(Arrays.asList(
                "None", "Breakfast", "Second Breakfast", "Snack 1",
                "Dinner", "Snack 2", "Supper", "After workout", "Night snack"
        ));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                GetProductData.this,
                android.R.layout.simple_spinner_item,
                mealNamesList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealNameSpinner.setAdapter(adapter);
    }

    private void initUIComponents() {
        productSpinner = findViewById(R.id.productSpinner);
        mealNameSpinner = findViewById(R.id.mealNameSpinner);

        getProductFromDatabase = findViewById(R.id.getProductFromDatabase);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        dateDataButton = findViewById(R.id.datePickButton);
        productCommentButton = findViewById(R.id.productCommentButton);
        acceptButton = findViewById(R.id.acceptButton);

        productNameEditText = findViewById(R.id.productNameEditText);
        weightEditText = findViewById(R.id.weightEditText);

        productNameTextView = findViewById(R.id.productNameTextView);
        brandLabel = findViewById(R.id.brandLabel);
        kcalLabel = findViewById(R.id.kcalLabel);
        proteinLabel = findViewById(R.id.proteinLabel);
        fatLabel = findViewById(R.id.fatLabel);

        brandValueTextView = findViewById(R.id.brandValueTextView);
        kcalValueTextView = findViewById(R.id.kcalValueTextView);
        proteinValueTextView = findViewById(R.id.proteinValueTextView);
        fatValueTextView = findViewById(R.id.fatValueTextView);
        carbsValueTextView = findViewById(R.id.carbsValueTextView);

        dateDataTextView = findViewById(R.id.dateDataTextView);
        dateDataTextView.setText(MyDate.getCurrentDayInSQLFormat());
    }

    private void setValuesToMacroUITable() {
        brandValueTextView.setText(productBrand != null ? productBrand : "-");
        kcalValueTextView.setText(String.format(Locale.getDefault(), "%.0f", kcal));
        proteinValueTextView.setText(String.format(Locale.getDefault(), "%.1f", protein));
        fatValueTextView.setText(String.format(Locale.getDefault(), "%.1f", fat));
        carbsValueTextView.setText(String.format(Locale.getDefault(), "%.1f", carbs));
    }

    private void getMacroValues() {
        if (products.isEmpty() || counter < 0 || counter >= products.size()) {
            return;
        }

        dayDate = dateDataTextView.getText().toString();
        dayName = MyDate.getDayNameInLowerCase(dayDate);

        if (mealNameSpinner.getSelectedItem() != null) {
            mealName = mealNameSpinner.getSelectedItem().toString();
        }

        // Fetch brand from product row if available, otherwise fallback
        String fetchedBrand = products.get(counter).getValue("brand_name");
        if (fetchedBrand == null) {
            fetchedBrand = products.get(counter).getValue("product_brand");
        }
        productBrand = (fetchedBrand != null) ? fetchedBrand : "";

        productComment = products.get(counter).getValue("comment_optional");
        if (productComment == null) productComment = "none";

        if (productSpinner.getSelectedItem() != null) {
            productName = productSpinner.getSelectedItem().toString();
        }

        // 1. Safe parsing of weight
        String amountOfProductTmp = weightEditText.getText().toString().trim();
        try {
            if (!amountOfProductTmp.isEmpty()) {
                amountOfProduct = Double.parseDouble(amountOfProductTmp);
            } else {
                amountOfProduct = 0.0;
            }
        } catch (NumberFormatException e) {
            amountOfProduct = 0.0;
        }

        // 2. Safe parsing of macros
        try {
            rawKcal = products.get(counter).getValue("product_kcal");
            kcal = Double.parseDouble(rawKcal);
        } catch (NumberFormatException | NullPointerException e) {
            kcal = 0.0;
        }

        try {
            rawProtein = products.get(counter).getValue("product_protein");
            protein = Double.parseDouble(rawProtein);
        } catch (NumberFormatException | NullPointerException e) {
            protein = 0.0;
        }

        try {
            rawFat = products.get(counter).getValue("product_fat");
            fat = Double.parseDouble(rawFat);
        } catch (NumberFormatException | NullPointerException e) {
            fat = 0.0;
        }

        try {
            rawCarbs = products.get(counter).getValue("product_carbs");
            carbs = Double.parseDouble(rawCarbs);
        } catch (NumberFormatException | NullPointerException e) {
            carbs = 0.0;
        }
    }

    private void showSimpleDialog() {
        // Prevent IndexOutOfBoundsException when no products are loaded
        if (products.isEmpty() || counter < 0 || counter >= products.size()) {
            Toast.makeText(GetProductData.this, "No product selected to view comments.", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(GetProductData.this)
                .setTitle(products.get(counter).getValue("product_name"))
                .setMessage(productComment != null ? productComment : "No comment")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void gatherInformationFromUIPrintValues() {
        Log.d("DIET_DEBUG", "dayDate: " + dayDate);
        Log.d("DIET_DEBUG", "dayName: " + dayName);
        Log.d("DIET_DEBUG", "mealName: " + mealName);
        Log.d("DIET_DEBUG", "productBrand: " + productBrand);
        Log.d("DIET_DEBUG", "productComment: " + productComment);
        Log.d("DIET_DEBUG", "productName: " + productName);
        Log.d("DIET_DEBUG", "amountOfProduct: " + amountOfProduct);
        Log.d("DIET_DEBUG", "kcal: " + kcal + " | protein: " + protein + " | fat: " + fat + " | carbs: " + carbs);
    }

    private void addListeners() {
        productCommentButton.setOnClickListener(v -> showSimpleDialog());

        getProductFromDatabase.setOnClickListener(v -> {
            String searchText = productNameEditText.getText().toString().trim();
            String query = QueryMaker.getAllProductTableLikeProductNameCaseInsensitive(searchText);

            GetFromSQLDatabase.runAzureQueryAIChat(query, new AzureDataCallback() {
                @Override
                public void onSuccess(ArrayList<RowInTable> resultTable) {
                    // Always perform UI updates on the Main UI thread
                    runOnUiThread(() -> {
                        products = resultTable;
                        counter = 0;

                        Toast.makeText(GetProductData.this, "Found " + products.size() + " products", Toast.LENGTH_SHORT).show();

                        List<String> productList = new ArrayList<>();
                        for (int i = 0; i < products.size(); i++) {
                            productList.add(products.get(i).getValue("product_name"));
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                GetProductData.this,
                                android.R.layout.simple_spinner_item,
                                productList
                        );
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        productSpinner.setAdapter(adapter);

                        if (!products.isEmpty()) {
                            productNameTextView.setText(products.get(0).getValue("product_name"));
                            getMacroValues();
                            setValuesToMacroUITable();
                        }
                    });
                }

                @Override
                public void onFailure(String errorMessage) {
                    runOnUiThread(() -> {
                        Toast.makeText(GetProductData.this, "Failed to get data: " + errorMessage, Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });

        nextButton.setOnClickListener(view -> {
            if (!products.isEmpty()) {
                if (counter < products.size() - 1) {
                    counter++;
                } else {
                    counter = 0;
                }
                productSpinner.setSelection(counter);
                getMacroValues();
                setValuesToMacroUITable();
            }
        });

        previousButton.setOnClickListener(view -> {
            if (!products.isEmpty()) {
                if (counter > 0) {
                    counter--;
                } else {
                    counter = products.size() - 1;
                }
                productSpinner.setSelection(counter);
                getMacroValues();
                setValuesToMacroUITable();
            }
        });

        dateDataButton.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select a Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            datePicker.addOnPositiveButtonClickListener(selection -> {
                // Ensure date formatting uses UTC to prevent 1-day timezone shifting
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                String formattedDate = sdf.format(new Date(selection));

                dateDataTextView.setText(formattedDate);
            });

            datePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        });

        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!products.isEmpty() && position < products.size()) {
                    counter = position;
                    productNameTextView.setText(products.get(counter).getValue("product_name"));
                    getMacroValues();
                    setValuesToMacroUITable();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        acceptButton.setOnClickListener(v -> {
            getMacroValues();

            if (productName == null || productName.trim().isEmpty()) {
                Toast.makeText(GetProductData.this, "Please select a product first", Toast.LENGTH_SHORT).show();
                return;
            }

            if (amountOfProduct <= 0) {
                Toast.makeText(GetProductData.this, "Please enter a valid weight", Toast.LENGTH_SHORT).show();
                return;
            }

            // Consumed macros based on standard 100g product nutrition values
            kcalConsume = (kcal * amountOfProduct) / 100.0;
            carbsConsume = (carbs * amountOfProduct) / 100.0;
            fatConsume = (fat * amountOfProduct) / 100.0;
            proteinConsume = (protein * amountOfProduct) / 100.0;

            timeOptional = "none";
            commentOptional = (productComment != null && !productComment.trim().isEmpty()) ? productComment : "none";

            gatherInformationFromUIPrintValues();

            String myInsertQuery = QueryMaker.insertMealToCalendarTable(
                    dayDate,
                    dayName,
                    mealName,
                    amountOfProduct,
                    productName,
                    kcal,
                    protein,
                    fat,
                    carbs,
                    timeOptional,
                    commentOptional,
                    kcalConsume,
                    carbsConsume,
                    fatConsume,
                    proteinConsume
            );

            Log.d("AZURE_INSERT_SQL", "Executing: " + myInsertQuery);

            GetFromSQLDatabase.runAzureQueryAIChat(myInsertQuery, new AzureDataCallback() {
                @Override
                public void onSuccess(ArrayList<RowInTable> resultTable) {
                    runOnUiThread(() -> {
                        Toast.makeText(GetProductData.this, "Meal added successfully!", Toast.LENGTH_SHORT).show();
                        weightEditText.setText("");
                    });
                }

                @Override
                public void onFailure(String errorMessage) {
                    runOnUiThread(() -> {
                        Toast.makeText(GetProductData.this, "Failed to add meal: " + errorMessage, Toast.LENGTH_LONG).show();
                        Log.e("AZURE_INSERT_ERROR", errorMessage);
                    });
                }
            });
        });
    }
}