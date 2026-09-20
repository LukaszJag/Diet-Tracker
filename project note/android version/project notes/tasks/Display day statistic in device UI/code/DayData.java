package com.lukaszjag.diet_tracker_android.gui.diet.day_data_view;

import static kotlinx.coroutines.internal.Concurrent_commonKt.getValue;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.gui.diet.GetProductData;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.AzureDataCallback;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.GetFromSQLDatabase;
import com.lukaszjag.diet_tracker_android.tools.date_tools.MyDate;
import com.lukaszjag.diet_tracker_android.tools.products_tools.MyAdapterProduct;
import com.lukaszjag.diet_tracker_android.tools.products_tools.Product;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.QueryMaker;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.RowInTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DayData extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapterProduct adapter;
    private Button dateDataButton;
    private TextView dateDataTextView;
    ArrayList<RowInTable> products = new ArrayList<>();
    String dateOfData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_data_layout);

        dateOfData = MyDate.getCurrentDayInSQLFormat();

        setupAllElements();
        addListeners();
        getDataForDate(dateOfData);
        setDataToView();

        // 1. Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. Initialize and set Adapter
        adapter = new MyAdapterProduct();
        recyclerView.setAdapter(adapter);

        //addTestProducts();

    }


    private void setupAllElements() {
        initUIComponents();
    }

    private void initUIComponents() {
        dateDataButton = findViewById(R.id.datePickButton);

        dateDataTextView = findViewById(R.id.dateTextView);
        dateDataTextView.setText(MyDate.getCurrentDayInSQLFormat());
    }

    private void addListeners() {
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

            dateOfData = dateDataTextView.getText().toString();
                getDataForDate(dateOfData);
                setDataToView();
            }
        });
    }

    private void addTestProducts() {
        adapter.addItem(new Product("Kotlet schabowy", "323", "487.73"));
        adapter.addItem(new Product("Ketchup lagodny", "53", "49.29"));
        adapter.addItem(new Product("Orzeszki ziemne solone", "61", "369.05"));
        adapter.addItem(new Product("Bułka grahamka", "80", "212"));
        adapter.addItem(new Product("Paprykarz szczeciński - Graal", "330", "396"));
        adapter.addItem(new Product("Mleko 1,5%", "818", "359.92"));
        adapter.addItem(new Product("Budyń o smaku czekoladowym z cukrem (sam proszek)", "126", "459.9"));
        adapter.addItem(new Product("Pestki dyni", "47", "261.32"));
        adapter.addItem(new Product("Śliwka suszona", "43", "126.42"));
        adapter.addItem(new Product("Orzechy włoskie", "26", "167.7"));
        adapter.addItem(new Product("Żurawina suszona", "46", "157.32"));
        adapter.addItem(new Product("Daktyle", "19", "55.1"));
        adapter.addItem(new Product("Jajko sadzone", "192", "470.4"));
        adapter.addItem(new Product("Kasza gryczana - ugotowana", "369", "446.49"));
        adapter.addItem(new Product("Zbyszko 3 Cytryny Napój gazowany", "1750", "332.5"));
        adapter.addItem(new Product("Sok Cytryna limonka", "1000", "450"));
    }

    private void getDataForDate(String dateInSQLFormat) {

        String query = QueryMaker.getAllProductsFromCalendarTableLikeDate(dateOfData);

        // Call the method and provide the callback
        GetFromSQLDatabase.runAzureQueryAIChat(query, new AzureDataCallback() {

            @Override
            public void onSuccess(ArrayList<RowInTable> resultTable) {
                // This code runs when the data successfully arrives from Azure!
                System.out.println("Result size: " + resultTable.size());
                products = resultTable;
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle the error (e.g., show a Toast to the user)
                System.out.println("Failed to get data: " + errorMessage);
            }
        });
    }

    private void setDataToView() {
        String productName, amountOfProduct, kcalOfProduct;
        for (int i = 0; i < products.size(); i++) {
            productName = products.get(i).getValue("product_name");
            amountOfProduct = products.get(i).getValue("amount_of_product");
            kcalOfProduct = products.get(i).getValue("kcal_consume");
            adapter.addItem(new Product(productName,amountOfProduct, kcalOfProduct));
        }
    }
}