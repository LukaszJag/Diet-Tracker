package com.lukaszjag.diet_tracker_android.tools.theme;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lukaszjag.diet_tracker_android.tools.settings.AppSettings;

public class ThemeHelper {

    /**
     * Traverses the current Activity's view hierarchy and updates all Button backgrounds.
     */
    public static void applyButtonTheme(Activity activity) {
        if (activity == null) return;
        int buttonColor = AppSettings.getButtonColor(activity);
        View root = activity.findViewById(android.R.id.content);
        if (root != null) {
            applyThemeToViewHierarchy(root, buttonColor);
        }
    }

    public static void applyThemeToViewHierarchy(View view, int color) {
        if (view == null) return;
        if (view instanceof Button) {
            applyColorToButton((Button) view, color);
        } else if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            for (int i = 0; i < vg.getChildCount(); i++) {
                applyThemeToViewHierarchy(vg.getChildAt(i), color);
            }
        }
    }

    public static void applyColorToButton(Button button, int color) {
        if (button == null) return;
        Drawable bg = button.getBackground();
        if (bg != null) {
            bg.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        } else {
            button.setBackgroundColor(color);
        }

        // Maintain high contrast text (white or black depending on color darkness)
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        button.setTextColor(darkness > 0.4 ? Color.WHITE : Color.BLACK);
    }
}