package com.lukaszjag.diet_tracker_android.tools.cloud_data_tools;

import android.util.Log;

import com.lukaszjag.diet_tracker_android.tools.sql_tools.RowInTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFromSQLDatabase {

    public void runCustomAzureQuery(String mySqlString) {
        AzureApiService apiService = RetrofitClient.getRetrofitInstance().create(AzureApiService.class);
        QueryRequest requestBody = new QueryRequest(mySqlString);
        Call<List<Map<String, Object>>> call = apiService.executeCustomQuery(requestBody);

        call.enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Map<String, Object>> dynamicSqlData = response.body();
                    if (!dynamicSqlData.isEmpty()) {
                        Log.d("AZURE_CUSTOM_SQL", "amount of rows: " + dynamicSqlData.size());
                        for (int i = 0; i < dynamicSqlData.size(); i++) {
                            Map<String, Object> row = dynamicSqlData.get(i);
                            Log.d("AZURE_CUSTOM_SQL", "Row data: [" + i + "]: " + row.get("product_name"));
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

    public static void runAzureQueryAIChat(String mySqlString, AzureDataCallback callback) {
        AzureApiService apiService = RetrofitClient.getRetrofitInstance().create(AzureApiService.class);
        QueryRequest requestBody = new QueryRequest(mySqlString);

        Log.d("AZURE_DEBUG", "Sending Query: " + mySqlString);

        Call<List<Map<String, Object>>> call = apiService.executeCustomQuery(requestBody);

        call.enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                ArrayList<RowInTable> resultTable = new ArrayList<>();

                Log.d("AZURE_DEBUG", "Response Code: " + response.code());

                if (response.isSuccessful()) {
                    List<Map<String, Object>> dynamicSqlData = response.body();

                    if (dynamicSqlData != null && !dynamicSqlData.isEmpty()) {
                        for (int i = 0; i < dynamicSqlData.size(); i++) {
                            Map<String, Object> row = dynamicSqlData.get(i);
                            RowInTable rowInTable = new RowInTable();

                            for (String key : row.keySet()) {
                                rowInTable.putKeyAndValueToRow(key, String.valueOf(row.get(key)));
                            }
                            resultTable.add(rowInTable);
                        }
                        callback.onSuccess(resultTable);
                    } else {
                        Log.d("AZURE_DEBUG", "Query executed successfully with empty result set.");
                        callback.onSuccess(new ArrayList<>());
                    }
                } else {
                    String detailedError = "Unknown error";
                    try {
                        if (response.errorBody() != null) {
                            detailedError = response.errorBody().string();
                        }
                    } catch (IOException e) {
                        Log.e("AZURE_DEBUG", "Failed to parse error body: " + e.getMessage());
                    }

                    Log.e("AZURE_ERROR_DETAILS", "HTTP Error " + response.code() + ": " + detailedError);
                    callback.onFailure("Server error (" + response.code() + "): " + detailedError);
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {
                Log.e("AZURE_DEBUG", "Network Transport Failure: " + t.getMessage(), t);
                callback.onFailure("Network fail: " + t.getMessage());
            }
        });
    }
}