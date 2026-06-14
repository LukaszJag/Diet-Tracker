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
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.AzureApiService;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.CalendarDay;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.RetrofitClient;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.CalendarDay;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void setupComponents() {
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
        fetchData();
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

    private void fetchData() {
        // 1. Create the API service
        Log.i("i", "1. Create the API service");
        AzureApiService apiService = RetrofitClient.getRetrofitInstance().create(AzureApiService.class);

        // 2. Call the server asynchronously (won't freeze your app)
        Log.i("i", "2. Call the server asynchronously (won't freeze your app)");
        Call<List<CalendarDay>> call = apiService.getDataFromAzure();
        call.enqueue(new Callback<List<CalendarDay>>() {

            @Override
            public void onResponse(Call<List<CalendarDay>> call, Response<List<CalendarDay>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<CalendarDay> sqlData = response.body();

                    // Success! Let's display the name of the first item in a Toast.
                    if (sqlData.size() > 0) {
                        String firstItemName = sqlData.get(0).getProduct_name();

                        Toast.makeText(MainActivity.this, "Connected! Found: " + firstItemName, Toast.LENGTH_LONG).show();

                        // You can also print the whole list to the Android Studio Logcat
                        for (CalendarDay CalendarDay : sqlData) {
                            Log.d("AZURE_SQL_DATA", "Name: " + CalendarDay.getProduct_name() + ", Email: " + CalendarDay.getDay_date());
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Connected, but SQL table is empty", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    if(response.isSuccessful() ){
                        Log.i("check_data", "response.isSuccessful() is true");
                    } else {

                        try {
                            String errorUrl = response.raw().request().url().toString(); // <--- GETS THE EXACT URL
                            Log.e("AZURE_SQL_ERROR", "Android tried to ping this URL: " + errorUrl);
                            Log.e("AZURE_SQL_ERROR", "Server Code: " + response.code());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    // Reveal the true error message hidden in errorBody
                    try {
                        String errorMessage = "Unknown error";
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                        }
                        Log.e("AZURE_SQL_ERROR_OLD", "Server Code: " + response.code() + " | Error: " + errorMessage);
                        Toast.makeText(MainActivity.this, "Ser  ver error: " + response.code(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                    if(response.body() == null){
                        Log.i("check_data", "Response body is null");
                    }else{
                        Log.i("check_data", "Response body is not null: " + response.body().toString());
                    }
                    Toast.makeText(MainActivity.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                }


            @Override
            public void onFailure(Call<List<CalendarDay>> call, Throwable t) {
                // This runs if there's no internet, wrong URL, or JSON parsing error
                Toast.makeText(MainActivity.this, "Connection Failed!", Toast.LENGTH_SHORT).show();
                Log.e("AZURE_SQL_ERROR", t.getMessage());
            }
        });
    }
}