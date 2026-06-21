package com.lukaszjag.diet_tracker_android;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lukaszjag.diet_tracker_android.databinding.ActivityMainBinding;
import com.lukaszjag.diet_tracker_android.gui.AddMealToCalendar;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.AzureApiService;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.QueryRequest;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.RetrofitClient;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button getProductDataButton;
    Button mainButton;
    Button getDayDataButton;
    EditText productNameInputEditText;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setupComponents();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.get_product_data); // or whatever layout you are using

        // 1. Find the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.getProductDataRecyclerView);

        // 2. THIS IS THE LINE FIXING YOUR CRASH: Tell it to be a vertical list
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // addButtons();

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
        addGetProductDataComponents();
        /*
        String queryToRun = "SELECT * FROM [diet_tracker_schema].[product_table] WHERE LOWER(product_name) LIKE 'pom%'";
        runCustomAzureQuery(queryToRun);
*/
    }

    private String makeQueryForButtonListener(String productData){
        return "SELECT * FROM [diet_tracker_schema].[product_table] WHERE LOWER(product_name) LIKE '%" + productData + "%'";
    }
    private void addGetProductDataComponents(){
        productNameInputEditText =findViewById(R.id.productNameEditText);
        getProductDataButton = findViewById(R.id.getProductFromDatabase);
        getProductDataButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String query = makeQueryForButtonListener(productNameInputEditText.getText().toString());
                Log.i("BUTTON ALLERT", "Run query: " + query);
                runCustomAzureQuery(query);
            }
        });
    }

    private void addButtons() {
        mainButton = findViewById(R.id.addMealButton);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMealToCalendar.class);
                startActivity(intent);
            }
        });

        getDayDataButton = findViewById(R.id.getDayDataButton);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMealToCalendar.class);
                startActivity(intent);
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

    private void runCustomAzureQuery(String mySqlString) {
        AzureApiService apiService = RetrofitClient.getRetrofitInstance().create(AzureApiService.class);

        // 1. Package the string into the object
        QueryRequest requestBody = new QueryRequest(mySqlString);

        // 2. Send the raw string directly to Azure!
        Call<List<Map<String, Object>>> call = apiService.executeCustomQuery(requestBody);

        call.enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Map<String, Object>> dynamicSqlData = response.body();

                    if (dynamicSqlData.size() > 0) {
                        Toast.makeText(MainActivity.this, "Query Success! Items: " + dynamicSqlData.size(), Toast.LENGTH_SHORT).show();
                        Log.d("AZURE_CUSTOM_SQL", "amount of rows: " + dynamicSqlData.size());
                        // You can check the logs to see the resulting columns


                        for (int i = 0; i < dynamicSqlData.size(); i++) {
                            Map<String, Object> row = dynamicSqlData.get(i);
                            Log.d("AZURE_CUSTOM_SQL", "Row data: [" + i + "]: "  + row.get("product_name"));
                            //Log.d("AZURE_CUSTOM_SQL", "First row data: " + row.toString());
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Query executed, but no results found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Syntax Error or Server crash: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {
                Log.e("AZURE_CUSTOM_SQL", "Network fail: " + t.getMessage());
            }
        });
    }
}