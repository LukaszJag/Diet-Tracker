package com.lukaszjag.diet_tracker_android.tools.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.AzureDataCallback;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.GetFromSQLDatabase;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.QueryMaker;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.RowInTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppSettings {
    private static final String TAG = "AppSettingsSync";
    private static final String PREF_NAME = "DietTrackerAppSettings";

    public static final String KEY_BUTTON_BG_COLOR = "app_button_bg_color";
    public static final String KEY_CALENDAR_HEATMAP_COLOR = "calendar_heatmap_color";
    public static final String KEY_CALENDAR_SCALE = "calendar_ui_scale";
    public static final String KEY_NOTE_CATEGORY_COLOR_PREFIX = "note_category_color_";

    // Defaults
    public static final int DEFAULT_BUTTON_COLOR = Color.parseColor("#4253A6");
    public static final int DEFAULT_HEATMAP_COLOR = Color.parseColor("#4CAF50"); // Green
    public static final float DEFAULT_CALENDAR_SCALE = 1.0f; // 100%
    public static final int DEFAULT_NOTE_BG_COLOR = Color.parseColor("#EEEEEE");

    public interface SyncCallback {
        void onComplete(boolean success);
    }

    // --- LOCAL READ / WRITE ---

    public static int getButtonColor(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_BUTTON_BG_COLOR, DEFAULT_BUTTON_COLOR);
    }

    public static void setButtonColor(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_BUTTON_BG_COLOR, color).apply();
    }

    public static int getCalendarHeatmapColor(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_CALENDAR_HEATMAP_COLOR, DEFAULT_HEATMAP_COLOR);
    }

    public static void setCalendarHeatmapColor(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_CALENDAR_HEATMAP_COLOR, color).apply();
    }

    public static float getCalendarScale(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getFloat(KEY_CALENDAR_SCALE, DEFAULT_CALENDAR_SCALE);
    }

    public static void setCalendarScale(Context context, float scale) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putFloat(KEY_CALENDAR_SCALE, scale).apply();
    }

    public static int getNoteCategoryColor(Context context, String mainCategory) {
        if (mainCategory == null || mainCategory.trim().isEmpty()) {
            return DEFAULT_NOTE_BG_COLOR;
        }
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_NOTE_CATEGORY_COLOR_PREFIX + mainCategory.trim(), DEFAULT_NOTE_BG_COLOR);
    }

    public static void setNoteCategoryColor(Context context, String mainCategory, int color) {
        if (mainCategory == null || mainCategory.trim().isEmpty()) return;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_NOTE_CATEGORY_COLOR_PREFIX + mainCategory.trim(), color).apply();
    }

    public static void resetNoteCategoryColor(Context context, String mainCategory) {
        if (mainCategory == null || mainCategory.trim().isEmpty()) return;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_NOTE_CATEGORY_COLOR_PREFIX + mainCategory.trim()).apply();
    }

    public static void resetAllNoteCategoryColors(Context context, List<String> mainCategories) {
        if (mainCategories == null) return;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        for (String cat : mainCategories) {
            if (cat != null && !cat.trim().isEmpty()) {
                editor.remove(KEY_NOTE_CATEGORY_COLOR_PREFIX + cat.trim());
            }
        }
        editor.apply();
    }

    // --- AZURE SQL SYNC ---

    public static String colorToHex(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }

    public static void setNoteCategoryColorWithSync(Context context, String mainCategory, int color, SyncCallback callback) {
        if (mainCategory == null || mainCategory.trim().isEmpty()) return;
        final Context appContext = context.getApplicationContext();

        // Save locally immediately
        setNoteCategoryColor(appContext, mainCategory, color);

        // Push to Azure SQL
        String key = KEY_NOTE_CATEGORY_COLOR_PREFIX + mainCategory.trim();
        String hex = colorToHex(color);
        String query = QueryMaker.upsertAppSettingQuery(key, hex);

        GetFromSQLDatabase.runAzureQueryAIChat(query, new AzureDataCallback() {
            @Override
            public void onSuccess(ArrayList<RowInTable> resultTable) {
                if (callback != null) callback.onComplete(true);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "Failed to sync category color: " + errorMessage);
                if (callback != null) callback.onComplete(false);
            }
        });
    }

    public static void resetNoteCategoryColorWithSync(Context context, String mainCategory, SyncCallback callback) {
        if (mainCategory == null || mainCategory.trim().isEmpty()) return;
        final Context appContext = context.getApplicationContext();

        resetNoteCategoryColor(appContext, mainCategory);

        String key = KEY_NOTE_CATEGORY_COLOR_PREFIX + mainCategory.trim();
        String query = QueryMaker.deleteAppSettingQuery(key);

        GetFromSQLDatabase.runAzureQueryAIChat(query, new AzureDataCallback() {
            @Override
            public void onSuccess(ArrayList<RowInTable> resultTable) {
                if (callback != null) callback.onComplete(true);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "Failed to reset category color in Azure: " + errorMessage);
                if (callback != null) callback.onComplete(false);
            }
        });
    }

    public static void resetAllNoteCategoryColorsWithSync(Context context, List<String> mainCategories, SyncCallback callback) {
        final Context appContext = context.getApplicationContext();

        resetAllNoteCategoryColors(appContext, mainCategories);

        String query = QueryMaker.deleteAppSettingsByPrefixQuery(KEY_NOTE_CATEGORY_COLOR_PREFIX);
        GetFromSQLDatabase.runAzureQueryAIChat(query, new AzureDataCallback() {
            @Override
            public void onSuccess(ArrayList<RowInTable> resultTable) {
                if (callback != null) callback.onComplete(true);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "Failed to reset all category colors in Azure: " + errorMessage);
                if (callback != null) callback.onComplete(false);
            }
        });
    }

    public static void saveAppAndCalendarSettingsWithSync(Context context, int buttonColor, int heatmapColor, float scale, SyncCallback callback) {
        final Context appContext = context.getApplicationContext();

        setButtonColor(appContext, buttonColor);
        setCalendarHeatmapColor(appContext, heatmapColor);
        setCalendarScale(appContext, scale);

        Map<String, String> settingsMap = new HashMap<>();
        settingsMap.put(KEY_BUTTON_BG_COLOR, colorToHex(buttonColor));
        settingsMap.put(KEY_CALENDAR_HEATMAP_COLOR, colorToHex(heatmapColor));
        settingsMap.put(KEY_CALENDAR_SCALE, String.valueOf(scale));

        String query = QueryMaker.upsertMultipleAppSettingsQuery(settingsMap);
        GetFromSQLDatabase.runAzureQueryAIChat(query, new AzureDataCallback() {
            @Override
            public void onSuccess(ArrayList<RowInTable> resultTable) {
                if (callback != null) callback.onComplete(true);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "Failed to sync app settings: " + errorMessage);
                if (callback != null) callback.onComplete(false);
            }
        });
    }

    public static void syncFromAzure(Context context, SyncCallback callback) {
        final Context appContext = context.getApplicationContext();
        String query = QueryMaker.getAllAppSettingsQuery();

        GetFromSQLDatabase.runAzureQueryAIChat(query, new AzureDataCallback() {
            @Override
            public void onSuccess(ArrayList<RowInTable> resultTable) {
                SharedPreferences prefs = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                // Clear old cached note category colors so resets made on other devices apply here too
                for (String existingKey : prefs.getAll().keySet()) {
                    if (existingKey.startsWith(KEY_NOTE_CATEGORY_COLOR_PREFIX)) {
                        editor.remove(existingKey);
                    }
                }

                if (resultTable != null) {
                    for (RowInTable row : resultTable) {
                        String key = row.getValue("setting_key");
                        String value = row.getValue("setting_value");

                        if (key == null || value == null || "null".equalsIgnoreCase(value)) continue;

                        try {
                            if (KEY_BUTTON_BG_COLOR.equals(key)) {
                                editor.putInt(KEY_BUTTON_BG_COLOR, Color.parseColor(value));
                            } else if (KEY_CALENDAR_HEATMAP_COLOR.equals(key)) {
                                editor.putInt(KEY_CALENDAR_HEATMAP_COLOR, Color.parseColor(value));
                            } else if (KEY_CALENDAR_SCALE.equals(key)) {
                                editor.putFloat(KEY_CALENDAR_SCALE, Float.parseFloat(value));
                            } else if (key.startsWith(KEY_NOTE_CATEGORY_COLOR_PREFIX)) {
                                editor.putInt(key, Color.parseColor(value));
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Skipping invalid setting value for " + key + ": " + value);
                        }
                    }
                }

                editor.apply();
                if (callback != null) callback.onComplete(true);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "Failed to pull settings from Azure: " + errorMessage);
                if (callback != null) callback.onComplete(false);
            }
        });
    }
}