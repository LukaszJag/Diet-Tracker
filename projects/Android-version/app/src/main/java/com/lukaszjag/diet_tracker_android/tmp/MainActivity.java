package com.lukaszjag.diet_tracker_android.tmp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lukaszjag.diet_tracker_android.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_in_calendar);

        // 1. Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. Initialize and set Adapter
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        // 3. Testing the Add method
        adapter.addItem(new MyItem("Title 1", "Subtitle 1", "Description 1"));
        adapter.addItem(new MyItem("Title 2", "Subtitle 2", "Description 2"));
        adapter.addItem(new MyItem("Title 3", "Subtitle 3", "Description 3"));

        // 4. Testing the Get method (Get the first item and print it to console)
        MyItem fetchedItem = adapter.getItem(0);
        if(fetchedItem != null) {
            System.out.println("Fetched: " + fetchedItem.getString1());
        }

        // 5. Testing the Set (Update) method (Change the 2nd item - Index 1)
        adapter.setItem(1, new MyItem("UPDATED Title 2", "Updated Sub 2", "Updated Desc 2"));

        // 6. Testing the Delete method (Delete the 3rd item - Index 2)
        // adapter.deleteItem(2);
    }
}