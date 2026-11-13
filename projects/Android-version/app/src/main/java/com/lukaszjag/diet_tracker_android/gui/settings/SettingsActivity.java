package com.lukaszjag.diet_tracker_android.gui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.tools.theme.ThemeHelper;
import com.lukaszjag.diet_tracker_android.tools.widget.WidgetColorSettingsActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btnNotesSettings = findViewById(R.id.btnNotesSettings);
        Button btnOpenAppSettings = findViewById(R.id.btnOpenAppSettings);
        Button btnOpenWidgetSettings = findViewById(R.id.btnOpenWidgetSettings);

        btnNotesSettings.setOnClickListener(v ->
                startActivity(new Intent(SettingsActivity.this, NotesSettingsActivity.class)));

        btnOpenAppSettings.setOnClickListener(v ->
                startActivity(new Intent(SettingsActivity.this, AppSettingsActivity.class)));

        btnOpenWidgetSettings.setOnClickListener(v ->
                startActivity(new Intent(SettingsActivity.this, WidgetColorSettingsActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThemeHelper.applyButtonTheme(this);
    }
}