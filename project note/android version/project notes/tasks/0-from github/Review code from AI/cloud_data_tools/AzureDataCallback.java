package com.lukaszjag.diet_tracker_android.tools.cloud_data_tools;

import com.lukaszjag.diet_tracker_android.tools.sql_tools.RowInTable;

import java.util.ArrayList;

public interface AzureDataCallback {
    void onSuccess(ArrayList<RowInTable> resultTable);
    void onFailure(String errorMessage);
}
