package com.lukaszjag.diet_tracker_android.tools.cloud_data_tools;

import android.util.Log;
import android.widget.Toast;

import com.lukaszjag.diet_tracker_android.MainActivity;
import com.lukaszjag.diet_tracker_android.gui.AddMealToCalendar;
import com.lukaszjag.diet_tracker_android.tools.sql_tools.RowInTable;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFromSQLDatabase {
    private void runCustomAzureQuery(String mySqlString) {
        AzureApiService apiService = RetrofitClient.getRetrofitInstance().create(AzureApiService.class);

        QueryRequest requestBody = new QueryRequest(mySqlString);

        Call<List<Map<String, Object>>> call = apiService.executeCustomQuery(requestBody);

        call.enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Map<String, Object>> dynamicSqlData = response.body();

                    if (dynamicSqlData.size() > 0) {
                        Log.d("AZURE_CUSTOM_SQL", "amount of rows: " + dynamicSqlData.size());
                        // You can check the logs to see the resulting columns


                        for (int i = 0; i < dynamicSqlData.size(); i++) {
                            Map<String, Object> row = dynamicSqlData.get(i);
                            Log.d("AZURE_CUSTOM_SQL", "Row data: [" + i + "]: " + row.get("product_name"));
                            //Log.d("AZURE_CUSTOM_SQL", "First row data: " + row.toString());
                        }
                    } else {
                        Log.d("AZURE_DATABASE_SQL", "No result");
                    }

                } else {
                    Log.d("AZURE_ERROR", "response.isSuccessful() or response.body() != null");
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {
                Log.e("AZURE_CUSTOM_SQL", "Network fail: " + t.getMessage());
            }
        });
    }
}
