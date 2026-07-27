package com.lukaszjag.diet_tracker_android.gui.diet;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GetProductData extends AppCompatActivity {

    //<editor-fold desc="Global variables">
    ArrayList<RowInTable> products = new ArrayList<>();
    ArrayList<RowInTable> mealNames = new ArrayList<>();
    int counter;
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
    String productBrand;
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
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="On start methods">
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
    //</editor-fold>

    //<editor-fold desc="UI Components - setup and more">
    private void setupComponents() {

        ArrayList<String> mealNames = new ArrayList<>(Arrays.asList("None", "Breakfast", "Second Breakfast", "Snack 1", "Dinner", "Snack 2"
                , "Supper", "After workout", "Night snack"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                GetProductData.this,
                android.R.layout.simple_spinner_item,
                mealNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealNameSpinner.setAdapter(adapter);
    }

    private void initUIComponents() {
        //<editor-fold desc="Spinner">
        productSpinner = findViewById(R.id.productSpinner);

        mealNameSpinner = findViewById(R.id.mealNameSpinner);
        //</editor-fold>

        //<editor-fold desc="Buttons">
        getProductFromDatabase = findViewById(R.id.getProductFromDatabase);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        dateDataButton = findViewById(R.id.datePickButton);
        productCommentButton = findViewById(R.id.productCommentButton);
        acceptButton = findViewById(R.id.acceptButton);
        //</editor-fold>

        //<editor-fold desc="EditText">
        productNameEditText = findViewById(R.id.productNameEditText);
        weightEditText = findViewById(R.id.weightEditText);
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

    private void addListeners() {
        productCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleDialog();
            }
        });

        getProductFromDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("BUTTON ALLERT", "Press button");
                String query = QueryMaker.getAllProductTableLikeProductNameCaseInsensitive(productNameEditText.getText().toString());
                Log.i("BUTTON ALLERT", "Run query: " + query);

                GetFromSQLDatabase.runAzureQueryAIChat(query, new AzureDataCallback() {
                    @Override
                    public void onSuccess(ArrayList<RowInTable> resultTable) {
                        // This code runs when the data successfully arrives from Azure
                        products = resultTable;
                        counter = 0;
                        Toast.makeText(getApplicationContext(), String.valueOf(products.size()), Toast.LENGTH_SHORT).show();

                        // Update the UI on the Main (UI) Thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                for (int i = 0; i < products.size(); i++) {
                                    products.get(i).printAlLValuesAndKey();
                                }

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
                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                        System.out.println("Failed to get data: " + errorMessage);
                    }
                });
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
                    getMacroValues();
                    setValuesToMacroUITable();
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
                    getMacroValues();
                    setValuesToMacroUITable();
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
                    getMacroValues();
                    setValuesToMacroUITable();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMacroValues();
                gatherInformationFromUIPrintValues();

//                String myInsertQuery = "null"; // = QueryMaker.insertMealToCalendarTable(null, null);
//
//                GetFromSQLDatabase.runAzureQueryAIChat(myInsertQuery, new AzureDataCallback() {
//                    @Override
//                    public void onSuccess(ArrayList<RowInTable> resultTable) {
//                        // Since we added SELECT 1, this will trigger successfully!
//                        System.out.println("Data successfully inserted into database!");
//                    }
//
//                    @Override
//                    public void onFailure(String errorMessage) {
//                        System.out.println("Insert failed: " + errorMessage);
//                    }
//                });
            }
        });
    }

    private void setValuesToMacroUITable() {
        brandValueTextView.setText(productBrand);

        kcalValueTextView.setText(String.format(java.util.Locale.getDefault(), "%.0f", kcal));
        proteinValueTextView.setText(String.format(java.util.Locale.getDefault(), "%.1f", protein));
        fatValueTextView.setText(String.format(java.util.Locale.getDefault(), "%.1f", fat));
        carbsValueTextView.setText(String.format(java.util.Locale.getDefault(), "%.1f", carbs));

    }
    //</editor-fold>

    //<editor-fold desc="Gather data">
    private void getMacroValues() {
        if (products.isEmpty() || counter < 0 || counter >= products.size()) {
            return;
        }

        dayDate = String.valueOf(dateDataTextView.getText());
        dayName = MyDate.getDayNameInLowerCase(dayDate);

        if (mealNameSpinner.getSelectedItem() != null) {
            mealName = mealNameSpinner.getSelectedItem().toString();
        }

        // Is brand label text correct? (See point 3 below)
        productBrand = String.valueOf(brandLabel.getText());
        productComment = products.get(counter).getValue("comment_optional");

        if (productSpinner.getSelectedItem() != null) {
            productName = productSpinner.getSelectedItem().toString();
        }

        // 1. Safe parsing of weight
        String amountOfProductTmp = String.valueOf(weightEditText.getText()).trim();
        try {
            if (!amountOfProductTmp.isEmpty()) {
                amountOfProduct = Double.parseDouble(amountOfProductTmp);
            } else {
                amountOfProduct = 0.0;
            }
        } catch (NumberFormatException e) {
            amountOfProduct = 0.0;
            System.out.println("Invalid weight value entered.");
        }

        // 2. Safe parsing of macros (independent of weight)
        try {
            rawKcal = String.valueOf(products.get(counter).getValue("product_kcal"));
            kcal = Double.parseDouble(rawKcal);
        } catch (NumberFormatException | NullPointerException e) {
            kcal = 0.0;
        }

        try {
            rawProtein = String.valueOf(products.get(counter).getValue("product_protein"));
            protein = Double.parseDouble(rawProtein);
        } catch (NumberFormatException | NullPointerException e) {
            protein = 0.0;
        }

        try {
            rawFat = String.valueOf(products.get(counter).getValue("product_fat"));
            fat = Double.parseDouble(rawFat);
        } catch (NumberFormatException | NullPointerException e) {
            fat = 0.0;
        }

        try {
            rawCarbs = String.valueOf(products.get(counter).getValue("product_carbs"));
            carbs = Double.parseDouble(rawCarbs);
        } catch (NumberFormatException | NullPointerException e) {
            carbs = 0.0;
        }
    }

    private void gatherInformationFromUI() {


        dayDate = (String) dateDataTextView.getText();

        System.out.println("All data: " + dayDate);
    }
    //</editor-fold>

    //<editor-fold desc="Debug methods">
    private void showSimpleDialog() {
        new AlertDialog.Builder(GetProductData.this)
                .setTitle(products.get(counter).getValue("product_name"))
                .setMessage(productComment)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void gatherInformationFromUIPrintValues() {
        System.out.println("dayDate: " + dayDate);
        System.out.println("dayName: " + dayName);
        System.out.println("mealName: " + mealName);
        System.out.println("productBrand: " + productBrand);
        System.out.println("productComment: " + productComment);
        System.out.println("productName: " + productName);
        System.out.println("amountOfProduct: " + amountOfProduct);
        System.out.println("rawKcal: " + rawKcal);
        System.out.println("rawProtein: " + rawProtein);
        System.out.println("rawFat: " + rawFat);
        System.out.println("rawCarbs: " + rawCarbs);
        System.out.println("kcal: " + kcal);
        System.out.println("protein: " + protein);
        System.out.println("fat: " + fat);
        System.out.println("carbs: " + carbs);
    }
    //</editor-fold>
}
