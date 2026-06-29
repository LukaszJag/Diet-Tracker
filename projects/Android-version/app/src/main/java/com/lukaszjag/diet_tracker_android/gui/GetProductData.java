package com.lukaszjag.diet_tracker_android.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lukaszjag.diet_tracker_android.MainActivity;
import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.AzureApiService;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.GetFromSQLDatabase;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.QueryRequest;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.RetrofitClient;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetProductData extends AppCompatActivity {
    Button getProductDataButton;
    Button mainButton;
    Button getDayDataButton;
    EditText productNameInputEditText;
    // Use onCreate instead of a constructor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bind the layout file to this Activity
        setContentView(R.layout.get_product_data);

        setupAllElements();
    }




    private void setupAllElements(){
        setGetProductDataComponents();
    }
    private void setGetProductDataComponents(){
        EditText productNameInputEditText =findViewById(R.id.productNameEditText);
        Button getProductDataButton = findViewById(R.id.getProductFromDatabase);

        getProductDataButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.i("BUTTON ALLERT", "Press button");
                String query = makeQueryForButtonListener(productNameInputEditText.getText().toString());
                Log.i("BUTTON ALLERT", "Run query: " + query);
                GetFromSQLDatabase getFromSQLDatabase = new GetFromSQLDatabase();
                getFromSQLDatabase.runCustomAzureQuery(query);
                new GetFromSQLDatabase().runCustomAzureQuery(query);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.getProductDataRecyclerView);

        // 2. THIS IS THE LINE FIXING YOUR CRASH: Tell it to be a vertical list
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private String makeQueryForButtonListener(String productData){
        return "SELECT * FROM [diet_tracker_schema].[product_table] WHERE LOWER(product_name) LIKE '%" + productData + "%'";
    }

}
