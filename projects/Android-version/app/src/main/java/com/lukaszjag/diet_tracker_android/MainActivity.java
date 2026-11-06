package com.lukaszjag.diet_tracker_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.lukaszjag.diet_tracker_android.gui.diet.GetProductData;
import com.lukaszjag.diet_tracker_android.gui.diet.day_data_view.DayData;
import com.lukaszjag.diet_tracker_android.gui.notes.Notes;
import com.lukaszjag.diet_tracker_android.gui.notes.NotesHistory;

public class MainActivity extends AppCompatActivity {

    private Button addMealButton;
    private Button getDayDataButton;
    private Button noteTasksButton;
    private Button notesHistoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_window);

        setupUIComponents();
        addListeners();
    }

    private void setupUIComponents() {
        addMealButton = findViewById(R.id.addMealButton);
        getDayDataButton = findViewById(R.id.getDayDataButton);
        noteTasksButton = findViewById(R.id.noteTaskButtonButton);
        notesHistoryButton = findViewById(R.id.notesHistoryButton);
    }

    private void addListeners() {
        noteTasksButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Notes.class);
            startActivity(intent);
        });

        notesHistoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotesHistory.class);
            startActivity(intent);
        });

        addMealButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GetProductData.class);
            startActivity(intent);
        });

        getDayDataButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DayData.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}