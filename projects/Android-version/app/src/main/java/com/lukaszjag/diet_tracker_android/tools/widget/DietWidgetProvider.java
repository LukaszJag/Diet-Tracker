package com.lukaszjag.diet_tracker_android.tools.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.gui.diet.day_data_view.DayData;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.AzureDataCallback;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.GetFromSQLDatabase;
import com.lukaszjag.diet_tracker_android.tools.date_tools.MyDate;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.QueryMaker;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.RowInTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DietWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "WIDGET_DEBUG";
    private static final String PREFS_NAME = "DietWidgetPrefs";
    private static final String PREF_PREFIX_KEY = "selected_date_";

    public static final String ACTION_REFRESH = "com.lukaszjag.diet_tracker_android.ACTION_WIDGET_REFRESH";
    public static final String ACTION_PREV_DAY = "com.lukaszjag.diet_tracker_android.ACTION_WIDGET_PREV_DAY";
    public static final String ACTION_NEXT_DAY = "com.lukaszjag.diet_tracker_android.ACTION_WIDGET_NEXT_DAY";

    // Pobranie aktualnie wybranej daty dla danego widżetu (domyślnie: dzisiaj)
    private static String getSelectedDate(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return prefs.getString(PREF_PREFIX_KEY + appWidgetId, today);
    }

    // Zapisanie wybranej daty
    private static void saveSelectedDate(Context context, int appWidgetId, String date) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(PREF_PREFIX_KEY + appWidgetId, date).apply();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            fetchDataAndUpdateWidget(context, appWidgetManager, widgetId, null);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        Log.d(TAG, "onReceive action: " + action);

        if (action == null) return;

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        // Uruchomienie goAsync() zapobiega uśpieniu procesu podczas zapytania do Azure
        final PendingResult pendingResult = goAsync();

        if (ACTION_PREV_DAY.equals(action) && appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
            String currentDate = getSelectedDate(context, appWidgetId);
            String prevDate = MyDate.getPreviousDayDateSQLFriendlyFormat(currentDate);
            saveSelectedDate(context, appWidgetId, prevDate);
            fetchDataAndUpdateWidget(context, appWidgetManager, appWidgetId, pendingResult);

        } else if (ACTION_NEXT_DAY.equals(action) && appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
            String currentDate = getSelectedDate(context, appWidgetId);
            String nextDate = MyDate.getNextDayDateSQLFriendlyFormat(currentDate);
            saveSelectedDate(context, appWidgetId, nextDate);
            fetchDataAndUpdateWidget(context, appWidgetManager, appWidgetId, pendingResult);

        } else if (ACTION_REFRESH.equals(action) || AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
            ComponentName thisWidget = new ComponentName(context, DietWidgetProvider.class);
            int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

            if (allWidgetIds.length == 0) {
                if (pendingResult != null) pendingResult.finish();
                return;
            }

            for (int i = 0; i < allWidgetIds.length; i++) {
                PendingResult pr = (i == allWidgetIds.length - 1) ? pendingResult : null;
                fetchDataAndUpdateWidget(context, appWidgetManager, allWidgetIds[i], pr);
            }
        } else {
            if (pendingResult != null) pendingResult.finish();
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        // Czyszczenie preferencji po usunięciu widżetu
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        for (int widgetId : appWidgetIds) {
            editor.remove(PREF_PREFIX_KEY + widgetId);
        }
        editor.apply();
    }

    private static void fetchDataAndUpdateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, PendingResult pendingResult) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_calorie_tracker);

        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }

        Intent settingsIntent = new Intent(context, WidgetColorSettingsActivity.class);
        PendingIntent settingsPendingIntent = PendingIntent.getActivity(context, 999, settingsIntent, flags);
        views.setOnClickPendingIntent(R.id.widgetSettingsButton, settingsPendingIntent);

        // 1. Kliknięcie widżetu -> otwarcie DayData
        Intent openAppIntent = new Intent(context, DayData.class);
        PendingIntent openAppPendingIntent = PendingIntent.getActivity(context, 0, openAppIntent, flags);
        views.setOnClickPendingIntent(R.id.widgetRoot, openAppPendingIntent);

        // 2. Kliknięcie: Poprzedni dzień (<)
        Intent prevIntent = new Intent(context, DietWidgetProvider.class);
        prevIntent.setAction(ACTION_PREV_DAY);
        prevIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        prevIntent.setData(Uri.parse("dietwidget://prev/" + appWidgetId));
        PendingIntent prevPendingIntent = PendingIntent.getBroadcast(context, appWidgetId * 10 + 1, prevIntent, flags);
        views.setOnClickPendingIntent(R.id.widgetPrevDayButton, prevPendingIntent);

        // 3. Kliknięcie: Następny dzień (>)
        Intent nextIntent = new Intent(context, DietWidgetProvider.class);
        nextIntent.setAction(ACTION_NEXT_DAY);
        nextIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        nextIntent.setData(Uri.parse("dietwidget://next/" + appWidgetId));
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, appWidgetId * 10 + 2, nextIntent, flags);
        views.setOnClickPendingIntent(R.id.widgetNextDayButton, nextPendingIntent);

        // 4. Kliknięcie: Odśwież (⟳)
        Intent refreshIntent = new Intent(context, DietWidgetProvider.class);
        refreshIntent.setAction(ACTION_REFRESH);
        refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        refreshIntent.setData(Uri.parse("dietwidget://refresh/" + appWidgetId));
        PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, appWidgetId * 10 + 3, refreshIntent, flags);
        views.setOnClickPendingIntent(R.id.widgetRefreshButton, refreshPendingIntent);

        // 5. Podpięcie ListView
        Intent serviceIntent = new Intent(context, DietWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.widgetListView, serviceIntent);
        views.setEmptyView(R.id.widgetListView, R.id.widgetEmptyView);

        // 6. Ustalenie daty do wyświetlenia
        String selectedDate = getSelectedDate(context, appWidgetId);
        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        String displayDateText;
        if (selectedDate.equals(todayDate)) {
            displayDateText = "Today (" + selectedDate.substring(5) + ")";
        } else {
            displayDateText = selectedDate;
        }

        views.setTextViewText(R.id.widgetDateTextView, displayDateText);
        views.setTextViewText(R.id.widgetKcalTextView, "Pobieranie...");
        appWidgetManager.partiallyUpdateAppWidget(appWidgetId, views);

        // 7. Zapytanie SQL do Azure
        String query = QueryMaker.getAllProductsFromCalendarTableLikeDate(selectedDate);
        Log.d(TAG, "Fetching data for date: " + selectedDate + " | Query: " + query);

        SharedPreferences prefs = context.getSharedPreferences("DietWidgetPrefs", Context.MODE_PRIVATE);
        int bgColor = prefs.getInt("widget_bg_color", Color.WHITE);
        views.setInt(R.id.widgetRoot, "setBackgroundColor", bgColor);

// Jeśli tło jest ciemne, zmień kolor daty na jasny
        double darkness = 1 - (0.299 * Color.red(bgColor) + 0.587 * Color.green(bgColor) + 0.114 * Color.blue(bgColor)) / 255;
        int headerTextColor = (darkness > 0.5) ? Color.WHITE : Color.parseColor("#333333");
        views.setTextColor(R.id.widgetDateTextView, headerTextColor);

        GetFromSQLDatabase.runAzureQueryAIChat(query, new AzureDataCallback() {
            @Override
            public void onSuccess(ArrayList<RowInTable> resultTable) {
                float totalKcal = 0;
                float totalProtein = 0;
                float totalFat = 0;
                float totalCarbs = 0;

                ArrayList<RowInTable> rows = (resultTable != null) ? resultTable : new ArrayList<>();

                for (RowInTable row : rows) {
                    totalKcal += safeParse(row.getValue("kcal_consume"));
                    totalProtein += safeParse(row.getValue("protein_consume"));
                    totalFat += safeParse(row.getValue("fat_consume"));
                    totalCarbs += safeParse(row.getValue("carbs_consume"));
                }

                views.setTextViewText(R.id.widgetKcalTextView, String.format(Locale.getDefault(), "%.0f kcal", totalKcal));
                views.setTextViewText(R.id.widgetProteinTextView, String.format(Locale.getDefault(), "P: %.1fg", totalProtein));
                views.setTextViewText(R.id.widgetFatTextView, String.format(Locale.getDefault(), "F: %.1fg", totalFat));
                views.setTextViewText(R.id.widgetCarbsTextView, String.format(Locale.getDefault(), "C: %.1fg", totalCarbs));

                DietWidgetService.productList = rows;
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widgetListView);
                appWidgetManager.updateAppWidget(appWidgetId, views);

                if (pendingResult != null) pendingResult.finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "Fetch failed: " + errorMessage);
                views.setTextViewText(R.id.widgetKcalTextView, "Błąd sieci");
                appWidgetManager.updateAppWidget(appWidgetId, views);

                if (pendingResult != null) pendingResult.finish();
            }
        });
    }

    private static float safeParse(String val) {
        if (val == null || val.trim().isEmpty() || val.equalsIgnoreCase("null")) return 0f;
        try {
            return Float.parseFloat(val);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    public static void forceWidgetUpdate(Context context) {
        Intent intent = new Intent(context, DietWidgetProvider.class);
        intent.setAction(ACTION_REFRESH);
        context.sendBroadcast(intent);
    }
}