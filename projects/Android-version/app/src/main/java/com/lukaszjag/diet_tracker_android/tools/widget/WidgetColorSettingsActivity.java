package com.lukaszjag.diet_tracker_android.tools.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lukaszjag.diet_tracker_android.R;

import java.util.Locale;

public class WidgetColorSettingsActivity extends AppCompatActivity {

    private ColorSatValView satValPicker;
    private ColorHueBarView huePicker;
    private ImageView colorPreview;
    private EditText etR, etG, etB, etH, etS, etV, etHex;
    private Button saveColorButton;

    private float currentHue = 0f;
    private float currentSat = 0f;
    private float currentVal = 1f;
    private boolean isUpdatingFromCode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_color_settings);

        satValPicker = findViewById(R.id.satValPicker);
        huePicker = findViewById(R.id.huePicker);
        colorPreview = findViewById(R.id.colorPreview);

        etR = findViewById(R.id.etR);
        etG = findViewById(R.id.etG);
        etB = findViewById(R.id.etB);
        etH = findViewById(R.id.etH);
        etS = findViewById(R.id.etS);
        etV = findViewById(R.id.etV);
        etHex = findViewById(R.id.etHex);
        saveColorButton = findViewById(R.id.saveColorButton);

        // Odczyt wcześniej zapisanego koloru (domyślnie biały)
        SharedPreferences prefs = getSharedPreferences("DietWidgetPrefs", Context.MODE_PRIVATE);
        int savedColor = prefs.getInt("widget_bg_color", Color.WHITE);
        setColor(savedColor);

        // Listenery pickera
        satValPicker.setListener((sat, val) -> {
            currentSat = sat;
            currentVal = val;
            updateColorViews();
        });

        huePicker.setListener(hue -> {
            currentHue = hue;
            satValPicker.setHue(hue);
            updateColorViews();
        });

        // Wpisywanie ręczne HEX
        etHex.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdatingFromCode) return;
                try {
                    String hex = s.toString().trim();
                    if (hex.length() == 6) {
                        int color = Color.parseColor("#" + hex);
                        setColor(color);
                    }
                } catch (Exception ignored) {}
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Szablony
        findViewById(R.id.presetWhite).setOnClickListener(v -> setColor(0xFFFFFFFF));
        findViewById(R.id.presetDark).setOnClickListener(v -> setColor(0xFF252525));
        findViewById(R.id.presetAmoled).setOnClickListener(v -> setColor(0xFF000000));
        findViewById(R.id.presetBlue).setOnClickListener(v -> setColor(0xFF1E3A8A));

        // Zapis
        saveColorButton.setOnClickListener(v -> {
            int finalColor = Color.HSVToColor(new float[]{currentHue, currentSat, currentVal});
            prefs.edit().putInt("widget_bg_color", finalColor).apply();

            // Odśwież widżet na pulpicie
            DietWidgetProvider.forceWidgetUpdate(this);

            Toast.makeText(this, "Widget background updated!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void setColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        currentHue = hsv[0];
        currentSat = hsv[1];
        currentVal = hsv[2];

        huePicker.setHue(currentHue);
        satValPicker.setHue(currentHue);
        satValPicker.setSatVal(currentSat, currentVal);

        updateColorViews();
    }

    private void updateColorViews() {
        int color = Color.HSVToColor(new float[]{currentHue, currentSat, currentVal});
        colorPreview.setBackgroundColor(color);

        isUpdatingFromCode = true;

        etR.setText(String.valueOf(Color.red(color)));
        etG.setText(String.valueOf(Color.green(color)));
        etB.setText(String.valueOf(Color.blue(color)));

        etH.setText(String.format(Locale.getDefault(), "%.0f", currentHue));
        etS.setText(String.format(Locale.getDefault(), "%.0f", currentSat * 100));
        etV.setText(String.format(Locale.getDefault(), "%.0f", currentVal * 100));

        String hex = String.format("%06X", (0xFFFFFF & color));
        etHex.setText(hex);

        isUpdatingFromCode = false;
    }
}