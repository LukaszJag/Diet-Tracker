package com.lukaszjag.diet_tracker_android.gui;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.AzureDataCallback;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.GetFromSQLDatabase;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.RowInTable;

import java.util.ArrayList;

public class GetProductData extends AppCompatActivity {

    ArrayList<RowInTable> products = new ArrayList<>();
    int counter;

    //<editor-fold desc="UI components">
// 1. Declare variables for your UI components

    // Buttons
    private Button getProductFromDatabase;
    private Button previousButton;
    private Button nextButton;

    // EditText
    private EditText productNameEditText;

    // RecyclerView
    private RecyclerView getProductDataRecyclerView;

    // TextView Labels
    private TextView productNameTextView;
    private TextView brandLabel;
    private TextView kcalLabel;
    private TextView proteinLabel;
    private TextView fatLabel;
    private TextView carbsLabel; // Added

    // TextView Values (Replaced generic textView6, textView9, etc.)
    private TextView brandValueTextView;
    private TextView kcalValueTextView;
    private TextView proteinValueTextView;
    private TextView fatValueTextView;
    private TextView carbsValueTextView;
    //</editor-fold>

    // Use onCreate instead of a constructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bind the layout file to this Activity
        setContentView(R.layout.get_product_data);

        setupAllElements();
    }
    private void setupAllElements() {
        setupUIComponents();
        setGetProductDataComponents();
        addListeners();
    }
    private void setupUIComponents(){
        // 2. Connect the variables to the XML IDs using findViewById

        // Buttons
        getProductFromDatabase = findViewById(R.id.getProductFromDatabase);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);

        // EditText
        productNameEditText = findViewById(R.id.productNameEditText);

        // RecyclerView
        getProductDataRecyclerView = findViewById(R.id.getProductDataRecyclerView);

        // Named TextViews
        productNameTextView = findViewById(R.id.productNameTextView);
        brandLabel = findViewById(R.id.brandLabel);
        kcalLabel = findViewById(R.id.kcalLabel);
        proteinLabel = findViewById(R.id.proteinLabel);
        fatLabel = findViewById(R.id.fatLabel);

        // Numbered TextViews
        brandValueTextView = findViewById(R.id.brandValueTextView);
        kcalValueTextView = findViewById(R.id.kcalValueTextView);
        proteinValueTextView = findViewById(R.id.proteinValueTextView);
        fatValueTextView = findViewById(R.id.fatValueTextView);
        carbsValueTextView = findViewById(R.id.carbsValueTextView);
    }
    private void addListeners(){
        //<editor-fold desc="getProductFromDatabase - Listener">
        getProductFromDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("BUTTON ALLERT", "Press button");
                String query = makeQueryForButtonListener(productNameEditText.getText().toString());
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
                //getFromSQLDatabase.runCustomAzureQuery(query);
            }
        });
        //</editor-fold>

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if (counter < products.size()) {
                    productNameTextView.setText(products.get(counter).getValue("product_name"));
                    counter++;
                }else {
                    counter = 0;
                    productNameTextView.setText(products.get(counter).getValue("product_name"));
                }
                addMacroFromProduct();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if (counter > 0) {
                    counter--;
                    productNameTextView.setText(products.get(counter).getValue("product_name"));
                }else {
                    counter = products.size() - 1;
                    productNameTextView.setText(products.get(counter).getValue("product_name"));
                }
                addMacroFromProduct();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.getProductDataRecyclerView);

        // 2. THIS IS THE LINE FIXING YOUR CRASH: Tell it to be a vertical list
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            // "%.0f" for kcal because calories are usually whole numbers (e.g., 238)
            kcalValueTextView.setText(String.format(java.util.Locale.getDefault(), "%.0f", kcal));

            // "%.1f" for macros for 1 decimal place (e.g., 7.2)
            proteinValueTextView.setText(String.format(java.util.Locale.getDefault(), "%.1f", protein));
            fatValueTextView.setText(String.format(java.util.Locale.getDefault(), "%.1f", fat));
            carbsValueTextView.setText(String.format(java.util.Locale.getDefault(), "%.1f", carbs));

        } catch (NumberFormatException | NullPointerException e) {
            // FALLBACK: If the database is missing a value (null) or has bad data,
            // it will prevent the app from crashing and just set the raw value instead.
            kcalValueTextView.setText(products.get(counter).getValue("product_kcal"));
            proteinValueTextView.setText(products.get(counter).getValue("product_protein"));
            fatValueTextView.setText(products.get(counter).getValue("product_fat"));
            carbsValueTextView.setText(products.get(counter).getValue("product_carbs"));
        }
    }
    private void setGetProductDataComponents() {


    }
    private String makeQueryForButtonListener(String productData) {
        return "SELECT * FROM [diet_tracker_schema].[product_table] WHERE LOWER(product_name) LIKE '%" + productData + "%'";
    }

}
