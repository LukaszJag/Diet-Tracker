package com.lukaszjag.diet_tracker_android;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.lukaszjag.diet_tracker_android.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // UI components
    Button passDataButton;

    TextView tvDate;
    TextView tvMealName;
    TextView tvProductName;
    TextView tvProductSuggestion;
    TextView tvBrand;
    TextView tvAmount;
    TextView tvKcal;
    TextView tvProtein;

    EditText etDate;
    EditText etMealName;
    EditText etProductName;
    EditText etProductSuggestion;
    EditText etBrand;
    EditText etAmount;
    EditText etKcal;
    EditText etProtein;

    public void setupComponents(){
        passDataButton = (Button) findViewById(R.id.passDataButton);

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
    }


    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setupComponents();

        setContentView(R.layout.add_product_to_calendar);

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

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

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

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}