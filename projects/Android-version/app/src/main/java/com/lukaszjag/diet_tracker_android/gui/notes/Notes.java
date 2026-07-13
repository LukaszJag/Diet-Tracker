package com.lukaszjag.diet_tracker_android.gui.notes;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.tools.notes_tool.Note;

public class Notes extends AppCompatActivity{    private RecyclerView recyclerView;
    private MyAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_view_layout);
        // 1. Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. Initialize and set Adapter
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        // 3. Testing the Add method
        adapter.addItem(new Note("Long time task", "Skonfigurować BitWardena", "Założyć konto, ustawić hasło, dodać kilka haseł"));
        adapter.addItem(new Note("Title 1", "Dodać kategorie do książek na półce -> Chce przeczytać - lubimyczytać . pl", "Description 1"));
        adapter.addItem(new Note("Title 2", "Wyznaczyć kwote do odłożenia na monitor - najlepiej znaleźć model do kupienia", "Description 2"));
        adapter.addItem(new Note("Title 3", "Wyłączyć powiadomienia po wyłączeniu laptopa - dalej nie zrobione", "Description 3"));
        adapter.addItem(new Note("Title 4", "Znaleźć książke do Javy", "Description 4"));
        adapter.addItem(new Note("Title 5", "Kupić i zarezerwować termin na - CompTIA A+ - core 1", "Description 5"));
        adapter.addItem(new Note("Title 6", "Sprawdzić jak uporządkować Google AI Studio - np. w foldery", "Description 6"));
        adapter.addItem(new Note("Title 7", "Setup basic active directory: with ~ 6 windows users, with ~ 2-3 groups", "Description 7"));
        adapter.addItem(new Note("Title 8", "Diet tracker android version - add daily tasks with checkboxes", "Description 8"));
        // 4. Testing the Get method (Get the first item and print it to console)
        Note fetchedItem = adapter.getItem(0);
        if(fetchedItem != null) {
            System.out.println("Fetched: " + fetchedItem.getNoteTitle());
        }

        // 5. Testing the Set (Update) method (Change the 2nd item - Index 1)
        adapter.setItem(2, new Note("UPDATED Title 2", "Updated Sub 2", "Updated Desc 2"));

        // 6. Testing the Delete method (Delete the 3rd item - Index 2)
        // adapter.deleteItem(2);
    }
}
