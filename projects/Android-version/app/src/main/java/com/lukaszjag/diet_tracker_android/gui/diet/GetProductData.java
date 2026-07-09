package com.lukaszjag.diet_tracker_android.gui.diet;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

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
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GetProductData extends AppCompatActivity {

    ArrayList<RowInTable> products = new ArrayList<>();
    int counter;

    //<editor-fold desc="UI components">
    private Spinner productSpinner;

    //<editor-fold desc="Button">
    private Button getProductFromDatabase;
    private Button previousButton;
    private Button nextButton;
    private Button dateDataButton;
    //</editor-fold>

    //<editor-fold desc="EditText">
    private EditText productNameEditText;
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
        setupUIComponents();
        addListeners();
    }
    private void setupUIComponents(){
        //<editor-fold desc="Spinner">
        productSpinner = findViewById(R.id.productSpinner);
        //</editor-fold>

        //<editor-fold desc="Buttons">
        getProductFromDatabase = findViewById(R.id.getProductFromDatabase);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        dateDataButton = findViewById(R.id.datePickButton);
        //</editor-fold>

        //<editor-fold desc="EditText">
        productNameEditText = findViewById(R.id.productNameEditText);
        //</editor-fold>

        //<editor-fold desc="TextViews">
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
        //</editor-fold>
    }
    private void addListeners(){

        getProductFromDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("BUTTON ALLERT", "Press button");
                String query = QueryMaker.getAllProductTableLikeProductNameCaseInsensitive(productNameEditText.getText().toString());
                Log.i("BUTTON ALLERT", "Run query: " + query);

                // Call the method and provide the callback
                GetFromSQLDatabase.runAzureQueryAIChat(query, new AzureDataCallback() {

                    @Override
                    public void onSuccess(ArrayList<RowInTable> resultTable) {
                        // This code runs when the data successfully arrives from Azure!
                        System.out.println("Result size: " + resultTable.size());
                        products = resultTable;
                        counter = 0;
                        // NOW the ArrayList has your data.
                        // You can update your RecyclerView, UI, or variables here.
                        // e.g., myAdapter.setData(resultTable);
                        // e.g., myAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        // Handle the error (e.g., show a Toast to the user)
                        System.out.println("Failed to get data: " + errorMessage);
                    }
                });


                Toast.makeText(getApplicationContext(), String.valueOf(products.size()), Toast.LENGTH_LONG).show();
                //toast.show();

                for (int i = 0; i < products.size(); i++) {
                    products.get(i).printAlLValuesAndKey();
                }

                Spinner spinner = (Spinner) findViewById(R.id.productSpinner);

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
                    addMacroFromProduct();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!products.isEmpty()) {
                    if (counter < products.size() - 1) {
                        counter++;
                    } else {
                        counter = 0;
                    }
                    productSpinner.setSelection(counter); // This triggers the Spinner listener to update macros
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!products.isEmpty()) {
                    if (counter > 0) {
                        counter--;
                    } else {
                        counter = products.size() - 1;
                    }
                    productSpinner.setSelection(counter); // This triggers the Spinner listener to update macros
                }
            }
        });

        dateDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select a Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds()) // default to today
                        .build();

                // Listen for when the user clicks "OK"
                datePicker.addOnPositiveButtonClickListener(selection -> {
                    // Convert the timestamp back to a readable date string
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDate = sdf.format(new Date(selection));

                    dateDataTextView.setText(formattedDate);
                });

                // Show the picker
                datePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");

            }
        });

        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!products.isEmpty()) {
                    counter = position;
                    productNameTextView.setText(products.get(counter).getValue("product_name"));
                    addMacroFromProduct();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    private void addMacroFromProduct(){
// Brand is text, so it stays exactly as you had it
        brandValueTextView.setText(products.get(counter).getValue("product_brand"));

        try {
            // 1. Get the raw values (converting to String first to be safe)
            String rawKcal = String.valueOf(products.get(counter).getValue("product_kcal"));
            String rawProtein = String.valueOf(products.get(counter).getValue("product_protein"));
            String rawFat = String.valueOf(products.get(counter).getValue("product_fat"));
            String rawCarbs = String.valueOf(products.get(counter).getValue("product_carbs"));

            // 2. Parse them into doubles
            double kcal = Double.parseDouble(rawKcal);
            double protein = Double.parseDouble(rawProtein);
            double fat = Double.parseDouble(rawFat);
            double carbs = Double.parseDouble(rawCarbs);

            // 3. Format and set the text
            kcalValueTextView.setText(String.format(java.util.Locale.getDefault(), "%.0f", kcal));

            proteinValueTextView.setText(String.format(java.util.Locale.getDefault(), "%.1f", protein));
            fatValueTextView.setText(String.format(java.util.Locale.getDefault(), "%.1f", fat));
            carbsValueTextView.setText(String.format(java.util.Locale.getDefault(), "%.1f", carbs));

        } catch (NumberFormatException | NullPointerException e) {
            kcalValueTextView.setText(products.get(counter).getValue("product_kcal"));
            proteinValueTextView.setText(products.get(counter).getValue("product_protein"));
            fatValueTextView.setText(products.get(counter).getValue("product_fat"));
            carbsValueTextView.setText(products.get(counter).getValue("product_carbs"));
        }
    }
}
