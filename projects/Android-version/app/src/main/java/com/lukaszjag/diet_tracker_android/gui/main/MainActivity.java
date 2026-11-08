package com.lukaszjag.diet_tracker_android.gui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.gui.diet.GetProductData;
import com.lukaszjag.diet_tracker_android.gui.diet.day_data_view.DayData;
import com.lukaszjag.diet_tracker_android.gui.notes.Notes;
import com.lukaszjag.diet_tracker_android.gui.notes.NotesHistory;
import com.lukaszjag.diet_tracker_android.gui.settings.AppSettingsActivity;
import com.lukaszjag.diet_tracker_android.tools.theme.ThemeHelper;
import com.lukaszjag.diet_tracker_android.tools.widget.WidgetColorSettingsActivity;

public class MainActivity extends AppCompatActivity {

    private Button addMealButton;
    private Button getDayDataButton;
    private Button noteTasksButton;
    private Button notesHistoryButton;
    private Button monthlyStatsButton;
    private Button widgetSettingsButton;
    private Button appSettingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_window);

        setupUIComponents();
        addListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Dynamically applies the chosen button theme color
        ThemeHelper.applyButtonTheme(this);
    }

    private void setupUIComponents() {
        addMealButton = findViewById(R.id.addMealButton);
        getDayDataButton = findViewById(R.id.getDayDataButton);
        noteTasksButton = findViewById(R.id.noteTaskButtonButton);
        notesHistoryButton = findViewById(R.id.notesHistoryButton);
        monthlyStatsButton = findViewById(R.id.monthlyStatsButton);
        widgetSettingsButton = findViewById(R.id.widgetSettingsButton);
        appSettingsButton = findViewById(R.id.appSettingsButton);
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

        // Opens the Widget Color Settings picker
        if (widgetSettingsButton != null) {
            widgetSettingsButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, WidgetColorSettingsActivity.class);
                startActivity(intent);
            });
        }

        // Opens the general App & Calendar Settings
        if (appSettingsButton != null) {
            appSettingsButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AppSettingsActivity.class);
                startActivity(intent);
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, AppSettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}