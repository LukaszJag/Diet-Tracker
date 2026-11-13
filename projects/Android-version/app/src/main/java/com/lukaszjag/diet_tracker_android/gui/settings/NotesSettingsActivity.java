package com.lukaszjag.diet_tracker_android.gui.settings;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.tools.notes_tool.categories.NoteCategoryData;
import com.lukaszjag.diet_tracker_android.tools.settings.AppSettings;
import com.lukaszjag.diet_tracker_android.tools.theme.ThemeHelper;
import com.lukaszjag.diet_tracker_android.tools.widget.ColorHueBarView;
import com.lukaszjag.diet_tracker_android.tools.widget.ColorSatValView;

import java.util.List;

public class NotesSettingsActivity extends AppCompatActivity {

    private RecyclerView rvCategoryColors;
    private CategoryColorAdapter adapter;
    private List<String> mainCategories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_settings);

        mainCategories = NoteCategoryData.getMainCategories();

        rvCategoryColors = findViewById(R.id.rvCategoryColors);
        rvCategoryColors.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryColorAdapter(mainCategories);
        rvCategoryColors.setAdapter(adapter);

        Button btnResetAll = findViewById(R.id.btnResetAllCategoryColors);
        btnResetAll.setOnClickListener(v -> {
            AppSettings.resetAllNoteCategoryColorsWithSync(this, mainCategories, success -> runOnUiThread(() -> {
                String msg = success ? "All colors reset & synced to cloud" : "Reset locally (Azure sync failed)";
                Toast.makeText(NotesSettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
            }));
            adapter.notifyDataSetChanged();
        });

        AppSettings.syncFromAzure(this, success -> runOnUiThread(() -> {
            if (success && adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThemeHelper.applyButtonTheme(this);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void showColorPickerDialog(String categoryName, int position) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_color_picker, null);

        TextView tvTitle = dialogView.findViewById(R.id.cpTitle);
        ColorSatValView satValPicker = dialogView.findViewById(R.id.cpSatValPicker);
        ColorHueBarView huePicker = dialogView.findViewById(R.id.cpHuePicker);
        View colorPreview = dialogView.findViewById(R.id.cpColorPreview);
        EditText etHex = dialogView.findViewById(R.id.cpEtHex);

        tvTitle.setText("Color for: " + categoryName);

        final float[] hsv = new float[3];
        final boolean[] isUpdatingFromCode = {false};

        Runnable updateViews = () -> {
            int color = Color.HSVToColor(hsv);
            applySwatchBackground(colorPreview, color);
            isUpdatingFromCode[0] = true;
            etHex.setText(String.format("%06X", (0xFFFFFF & color)));
            isUpdatingFromCode[0] = false;
        };

        // Initialize with currently saved color
        int initialColor = AppSettings.getNoteCategoryColor(this, categoryName);
        Color.colorToHSV(initialColor, hsv);
        huePicker.setHue(hsv[0]);
        satValPicker.setHue(hsv[0]);
        satValPicker.setSatVal(hsv[1], hsv[2]);
        updateViews.run();

        satValPicker.setListener((sat, val) -> {
            hsv[1] = sat;
            hsv[2] = val;
            updateViews.run();
        });

        huePicker.setListener(hue -> {
            hsv[0] = hue;
            satValPicker.setHue(hue);
            updateViews.run();
        });

        etHex.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdatingFromCode[0]) return;
                String hex = s.toString().trim();
                if (hex.length() == 6) {
                    try {
                        int parsed = Color.parseColor("#" + hex);
                        Color.colorToHSV(parsed, hsv);
                        huePicker.setHue(hsv[0]);
                        satValPicker.setHue(hsv[0]);
                        satValPicker.setSatVal(hsv[1], hsv[2]);
                        applySwatchBackground(colorPreview, parsed);
                    } catch (Exception ignored) {}
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        View.OnClickListener presetClick = v -> {
            int presetColor = AppSettings.DEFAULT_NOTE_BG_COLOR;
            int id = v.getId();
            if (id == R.id.cpPresetBlue) presetColor = Color.parseColor("#E3F2FD");
            else if (id == R.id.cpPresetGreen) presetColor = Color.parseColor("#E8F5E9");
            else if (id == R.id.cpPresetYellow) presetColor = Color.parseColor("#FFFDE7");
            else if (id == R.id.cpPresetOrange) presetColor = Color.parseColor("#FFF3E0");
            else if (id == R.id.cpPresetPurple) presetColor = Color.parseColor("#F3E5F5");
            else if (id == R.id.cpPresetRed) presetColor = Color.parseColor("#FFEBEE");

            Color.colorToHSV(presetColor, hsv);
            huePicker.setHue(hsv[0]);
            satValPicker.setHue(hsv[0]);
            satValPicker.setSatVal(hsv[1], hsv[2]);
            updateViews.run();
        };

        dialogView.findViewById(R.id.cpPresetDefault).setOnClickListener(presetClick);
        dialogView.findViewById(R.id.cpPresetBlue).setOnClickListener(presetClick);
        dialogView.findViewById(R.id.cpPresetGreen).setOnClickListener(presetClick);
        dialogView.findViewById(R.id.cpPresetYellow).setOnClickListener(presetClick);
        dialogView.findViewById(R.id.cpPresetOrange).setOnClickListener(presetClick);
        dialogView.findViewById(R.id.cpPresetPurple).setOnClickListener(presetClick);
        dialogView.findViewById(R.id.cpPresetRed).setOnClickListener(presetClick);

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Apply", (d, which) -> {
                    int chosenColor = Color.HSVToColor(hsv);
                    AppSettings.setNoteCategoryColorWithSync(NotesSettingsActivity.this, categoryName, chosenColor, success -> runOnUiThread(() -> {
                        if (!success) {
                            Toast.makeText(NotesSettingsActivity.this, "Saved locally (Azure sync failed)", Toast.LENGTH_SHORT).show();
                        }
                    }));
                    adapter.notifyItemChanged(position);
                    Toast.makeText(NotesSettingsActivity.this, categoryName + " color saved", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void applySwatchBackground(View view, int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setStroke(2, Color.parseColor("#BDBDBD"));
        view.setBackground(drawable);
    }

    private class CategoryColorAdapter extends RecyclerView.Adapter<CategoryColorAdapter.ViewHolder> {

        private final List<String> categories;

        CategoryColorAdapter(List<String> categories) {
            this.categories = categories;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_note_category_color, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String category = categories.get(position);
            int color = AppSettings.getNoteCategoryColor(NotesSettingsActivity.this, category);
            int btnColor = AppSettings.getButtonColor(NotesSettingsActivity.this);

            holder.tvName.setText(category);
            holder.tvHex.setText(String.format("#%06X", (0xFFFFFF & color)));
            applySwatchBackground(holder.viewPreview, color);

            ThemeHelper.applyColorToButton(holder.btnPick, btnColor);
            ThemeHelper.applyColorToButton(holder.btnReset, btnColor);

            holder.btnPick.setOnClickListener(v -> showColorPickerDialog(category, holder.getAdapterPosition()));
            holder.viewPreview.setOnClickListener(v -> showColorPickerDialog(category, holder.getAdapterPosition()));

            holder.btnReset.setOnClickListener(v -> {
                AppSettings.resetNoteCategoryColorWithSync(NotesSettingsActivity.this, category, success -> runOnUiThread(() -> {
                    if (!success) {
                        Toast.makeText(NotesSettingsActivity.this, "Reset locally (Azure sync failed)", Toast.LENGTH_SHORT).show();
                    }
                }));
                notifyItemChanged(holder.getAdapterPosition());
            });
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            View viewPreview;
            TextView tvName, tvHex;
            Button btnPick, btnReset;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                viewPreview = itemView.findViewById(R.id.viewCategoryColorPreview);
                tvName = itemView.findViewById(R.id.tvCategoryName);
                tvHex = itemView.findViewById(R.id.tvCategoryHex);
                btnPick = itemView.findViewById(R.id.btnPickCategoryColor);
                btnReset = itemView.findViewById(R.id.btnResetCategoryColor);
            }
        }
    }
}