package com.lukaszjag.diet_tracker_android.tools.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class AppSettings {
    private static final String PREF_NAME = "DietTrackerAppSettings";
    private static final String KEY_BUTTON_BG_COLOR = "app_button_bg_color";
    private static final String KEY_CALENDAR_HEATMAP_COLOR = "calendar_heatmap_color";
    private static final String KEY_CALENDAR_SCALE = "calendar_ui_scale";

    // Defaults
    public static final int DEFAULT_BUTTON_COLOR = Color.parseColor("#4253A6");
    public static final int DEFAULT_HEATMAP_COLOR = Color.parseColor("#4CAF50"); // Green
    public static final float DEFAULT_CALENDAR_SCALE = 1.0f; // 100%

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
}