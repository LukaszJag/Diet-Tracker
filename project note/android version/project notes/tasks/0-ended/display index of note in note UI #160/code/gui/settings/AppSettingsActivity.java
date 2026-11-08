package com.lukaszjag.diet_tracker_android.gui.settings;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.tools.settings.AppSettings;
import com.lukaszjag.diet_tracker_android.tools.theme.ThemeHelper;

public class AppSettingsActivity extends AppCompatActivity {

    private View previewButtonColor;
    private EditText etButtonColorHex;
    private View previewHeatmapColor;
    private EditText etHeatmapColorHex;
    private RadioGroup rgCalendarSize;
    private Button btnSaveSettings;

    private int currentButtonColor;
    private int currentHeatmapColor;
    private float currentScale;
    private boolean isUpdatingFromCode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);

        previewButtonColor = findViewById(R.id.previewButtonColor);
        etButtonColorHex = findViewById(R.id.etButtonColorHex);
        previewHeatmapColor = findViewById(R.id.previewHeatmapColor);
        etHeatmapColorHex = findViewById(R.id.etHeatmapColorHex);
        rgCalendarSize = findViewById(R.id.rgCalendarSize);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);

        // Load existing settings
        currentButtonColor = AppSettings.getButtonColor(this);
        currentHeatmapColor = AppSettings.getCalendarHeatmapColor(this);
        currentScale = AppSettings.getCalendarScale(this);

        updateButtonColorUI(currentButtonColor);
        updateHeatmapColorUI(currentHeatmapColor);
        setupScaleRadio();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThemeHelper.applyButtonTheme(this);
    }

    private void updateButtonColorUI(int color) {
        currentButtonColor = color;
        previewButtonColor.setBackgroundColor(color);
        isUpdatingFromCode = true;
        etButtonColorHex.setText(String.format("%06X", (0xFFFFFF & color)));
        isUpdatingFromCode = false;
    }

    private void updateHeatmapColorUI(int color) {
        currentHeatmapColor = color;
        previewHeatmapColor.setBackgroundColor(color);
        isUpdatingFromCode = true;
        etHeatmapColorHex.setText(String.format("%06X", (0xFFFFFF & color)));
        isUpdatingFromCode = false;
    }

    private void setupScaleRadio() {
        if (currentScale <= 0.85f) {
            rgCalendarSize.check(R.id.rbSizeSmall);
        } else if (currentScale <= 1.05f) {
            rgCalendarSize.check(R.id.rbSizeMedium);
        } else if (currentScale <= 1.3f) {
            rgCalendarSize.check(R.id.rbSizeLarge);
        } else {
            rgCalendarSize.check(R.id.rbSizeExtraLarge);
        }
    }

    private void setupListeners() {
        // Button color presets
        findViewById(R.id.btnPresetNavy).setOnClickListener(v -> updateButtonColorUI(Color.parseColor("#4253A6")));
        findViewById(R.id.btnPresetGreen).setOnClickListener(v -> updateButtonColorUI(Color.parseColor("#2E7D32")));
        findViewById(R.id.btnPresetBlue).setOnClickListener(v -> updateButtonColorUI(Color.parseColor("#1565C0")));
        findViewById(R.id.btnPresetPurple).setOnClickListener(v -> updateButtonColorUI(Color.parseColor("#6A1B9A")));
        findViewById(R.id.btnPresetRed).setOnClickListener(v -> updateButtonColorUI(Color.parseColor("#B71C1C")));
        findViewById(R.id.btnPresetDark).setOnClickListener(v -> updateButtonColorUI(Color.parseColor("#37474F")));

        // Heatmap color presets
        findViewById(R.id.hmPresetGreen).setOnClickListener(v -> updateHeatmapColorUI(Color.parseColor("#4CAF50")));
        findViewById(R.id.hmPresetBlue).setOnClickListener(v -> updateHeatmapColorUI(Color.parseColor("#2196F3")));
        findViewById(R.id.hmPresetOrange).setOnClickListener(v -> updateHeatmapColorUI(Color.parseColor("#FF9800")));
        findViewById(R.id.hmPresetPurple).setOnClickListener(v -> updateHeatmapColorUI(Color.parseColor("#9C27B0")));
        findViewById(R.id.hmPresetRed).setOnClickListener(v -> updateHeatmapColorUI(Color.parseColor("#F44336")));

        // Text inputs
        etButtonColorHex.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdatingFromCode) return;
                try {
                    String hex = s.toString().trim();
                    if (hex.length() == 6) {
                        currentButtonColor = Color.parseColor("#" + hex);
                        previewButtonColor.setBackgroundColor(currentButtonColor);
                    }
                } catch (Exception ignored) {}
            }
            public void afterTextChanged(Editable s) {}
        });

        etHeatmapColorHex.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdatingFromCode) return;
                try {
                    String hex = s.toString().trim();
                    if (hex.length() == 6) {
                        currentHeatmapColor = Color.parseColor("#" + hex);
                        previewHeatmapColor.setBackgroundColor(currentHeatmapColor);
                    }
                } catch (Exception ignored) {}
            }
            public void afterTextChanged(Editable s) {}
        });

        // Save
        btnSaveSettings.setOnClickListener(v -> {
            int checkedId = rgCalendarSize.getCheckedRadioButtonId();
            if (checkedId == R.id.rbSizeSmall) currentScale = 0.8f;
            else if (checkedId == R.id.rbSizeMedium) currentScale = 1.0f;
            else if (checkedId == R.id.rbSizeLarge) currentScale = 1.25f;
            else if (checkedId == R.id.rbSizeExtraLarge) currentScale = 1.5f;

            AppSettings.setButtonColor(this, currentButtonColor);
            AppSettings.setCalendarHeatmapColor(this, currentHeatmapColor);
            AppSettings.setCalendarScale(this, currentScale);

            ThemeHelper.applyButtonTheme(this);
            Toast.makeText(this, "Settings saved successfully!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}