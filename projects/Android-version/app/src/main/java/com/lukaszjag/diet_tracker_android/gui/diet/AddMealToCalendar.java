package com.lukaszjag.diet_tracker_android.gui.diet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.databinding.ActivityMainBinding;

public class AddMealToCalendar extends AppCompatActivity {

    //<editor-fold desc="Buttons">
    Button passDataButton;
    Button searchProductButton;
    //</editor-fold>

    //<editor-fold desc="TextViews">
    TextView tvDate;
    TextView tvMealName;
    TextView tvProductName;
    TextView tvProductSuggestion;
    TextView tvBrand;
    TextView tvAmount;
    TextView tvKcal;
    TextView tvProtein;
    //</editor-fold>

    //<editor-fold desc="EditTexts">
    EditText etDate;
    EditText etMealName;
    EditText etProductName;
    EditText etProductSuggestion;
    EditText etBrand;
    EditText etAmount;
    EditText etKcal;
    EditText etProtein;
    //</editor-fold>

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.add_meal_to_calendar);

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }
    public void setupComponents() {
        setContentView(R.layout.add_meal_to_calendar);

        passDataButton = (Button) findViewById(R.id.passDataButton);
        searchProductButton = findViewById(R.id.searchProductButton);

        tvDate = (TextView) findViewById(R.id.date);
        tvMealName = (TextView) findViewById(R.id.mealName);
        tvProductName = (TextView) findViewById(R.id.productName);
        tvProductSuggestion = (TextView) findViewById(
                R.id.productNameSuggestion
        );
        tvBrand = (TextView) findViewById(R.id.brand);
        tvAmount = (TextView) findViewById(R.id.amountOfProduct);
        tvKcal = (TextView) findViewById(R.id.kcal);
        tvProtein = (TextView) findViewById(R.id.protein);


        etDate = (EditText) findViewById(R.id.dateEditText);
        etMealName = (EditText) findViewById(R.id.mealNameEditText);
        etProductName = (EditText) findViewById(R.id.productNameEditText);
        etProductSuggestion = (EditText) findViewById(R.id.productNameSuggestionEditText);
        etBrand = (EditText) findViewById(R.id.brandEditText);
        etAmount = (EditText) findViewById(R.id.amountOfProductEditText);
        etKcal = (EditText) findViewById(R.id.kcalEditText);
        etProtein = (EditText) findViewById(R.id.proteinEditText);



        Button passDataButton;

        EditText etDate;
        EditText etMealName;
        EditText etProductName;
        EditText etProductSuggestion;
        EditText etBrand;
        EditText etAmount;
        EditText etKcal;
        EditText etProtein;

        passDataButton = (Button) findViewById(R.id.passDataButton);

        etDate = (EditText) findViewById(R.id.dateEditText);
        etMealName = (EditText) findViewById(R.id.mealNameEditText);
        etProductName = (EditText) findViewById(R.id.productNameEditText);
        etProductSuggestion = (EditText) findViewById(R.id.productNameSuggestionEditText);
        etBrand = (EditText) findViewById(R.id.brandEditText);
        etAmount = (EditText) findViewById(R.id.amountOfProductEditText);
        etKcal = (EditText) findViewById(R.id.kcalEditText);
        etProtein = (EditText) findViewById(R.id.proteinEditText);

        passDataButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("data", String.valueOf(etDate.getEditableText()));
                Log.i("data", String.valueOf(etMealName.getEditableText()));
                Log.i("data", String.valueOf(etProductName.getEditableText()));
                Log.i("data", String.valueOf(etProductSuggestion.getText()));
                Log.i("data", String.valueOf(etBrand.getText()));
                Log.i("data", String.valueOf(etAmount.getText()));
                Log.i("data", String.valueOf(etKcal.getText()));
                Log.i("data", String.valueOf(etProtein.getText()));
            }
        });

        searchProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
