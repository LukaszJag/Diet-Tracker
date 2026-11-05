package com.lukaszjag.diet_tracker_android.tools.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.lukaszjag.diet_tracker_android.R;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.RowInTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DietWidgetService extends RemoteViewsService {

    // Lista produktów współdzielona z widżetem
    public static List<RowInTable> productList = new ArrayList<>();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new DietRemoteViewsFactory(this.getApplicationContext());
    }

    static class DietRemoteViewsFactory implements RemoteViewsFactory {
        private final Context context;

        public DietRemoteViewsFactory(Context context) {
            this.context = context;
        }

        @Override
        public void onCreate() {}

        @Override
        public void onDataSetChanged() {
            // Wywoływane po notifyAppWidgetViewDataChanged
        }

        @Override
        public void onDestroy() {}

        @Override
        public int getCount() {
            return productList != null ? productList.size() : 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (productList == null || position < 0 || position >= productList.size()) {
                return null;
            }

            RowInTable row = productList.get(position);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_product_item);

            String name = row.getValue("product_name");
            String meal = row.getValue("meal_name");
            String amountStr = row.getValue("amount_of_product");
            String kcalStr = row.getValue("kcal_consume");

            float amount = 0f;
            float kcal = 0f;
            try {
                if (amountStr != null) amount = Float.parseFloat(amountStr);
                if (kcalStr != null) kcal = Float.parseFloat(kcalStr);
            } catch (NumberFormatException ignored) {}

            views.setTextViewText(R.id.itemProductName, name != null ? name : "Unknown");

            String details = (meal != null ? meal : "Meal") + " • " + String.format(Locale.getDefault(), "%.0fg", amount);
            views.setTextViewText(R.id.itemProductDetails, details);

            views.setTextViewText(R.id.itemProductKcal, String.format(Locale.getDefault(), "%.0f kcal", kcal));

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}