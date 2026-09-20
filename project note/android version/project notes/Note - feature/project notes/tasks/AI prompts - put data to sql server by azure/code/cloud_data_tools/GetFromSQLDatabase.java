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

                    if (dynamicSqlData.size() > 0) {
                        Log.d("AZURE_CUSTOM_SQL", "amount of rows: " + dynamicSqlData.size());

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

    public ArrayList<RowInTable> runAzureQuery(String mySqlString) {
        AzureApiService apiService = RetrofitClient.getRetrofitInstance().create(AzureApiService.class);

        ArrayList<RowInTable> resultTable = new ArrayList<>();
        QueryRequest requestBody = new QueryRequest(mySqlString);

        Call<List<Map<String, Object>>> call = apiService.executeCustomQuery(requestBody);

        call.enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Map<String, Object>> dynamicSqlData = response.body();

                    if (dynamicSqlData.size() > 0) {
                        Log.d("AZURE_CUSTOM_SQL", "amount of rows: " + dynamicSqlData.size());

                        int counter = 0;
                        for (int i = 0; i < dynamicSqlData.size(); i++) {
                            Map<String, Object> row = dynamicSqlData.get(i);
                            RowInTable rowInTable = new RowInTable();

                            for (String  key : row.keySet()) {
                                //System.out.println("hello");
                                //System.out.println("key: " + key);
                                //System.out.println("Value: " + row.get(key));
                                rowInTable.putKeyAndValueToRow(key, String.valueOf(row.get(key)));
                            }

                            resultTable.add(counter, rowInTable);
                            counter++;
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
        System.out.println(resultTable.size());
        return resultTable;
    }

    public ArrayList<RowInTable> runAzureQueryAIChat(String mySqlString) {
        AzureApiService apiService = RetrofitClient.getRetrofitInstance().create(AzureApiService.class);
        ArrayList<RowInTable> resultTable = new ArrayList<>();
        QueryRequest requestBody = new QueryRequest(mySqlString);

        Call<List<Map<String, Object>>> call = apiService.executeCustomQuery(requestBody);

        try {
            // .execute() blocks the thread and waits for the network response
            Response<List<Map<String, Object>>> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                List<Map<String, Object>> dynamicSqlData = response.body();

                if (dynamicSqlData.size() > 0) {
                    for (int i = 0; i < dynamicSqlData.size(); i++) {
                        Map<String, Object> row = dynamicSqlData.get(i);
                        RowInTable rowInTable = new RowInTable();

                        for (String key : row.keySet()) {
                            rowInTable.putKeyAndValueToRow(key, String.valueOf(row.get(key)));
                        }

                        resultTable.add(rowInTable);
                    }
                } else {
                    Log.d("AZURE_DATABASE_SQL", "No result");
                }
            } else {
                Log.d("AZURE_ERROR", "response.isSuccessful() or response.body() != null");
            }

        } catch (IOException e) {
            // This catches network errors (like no internet connection)
            Log.e("AZURE_CUSTOM_SQL", "Network fail: " + e.getMessage());
        }

        // This will now have the correct items because the code waited for the response!
        System.out.println("Result size: " + resultTable.size());

        return resultTable;
    }
// NEW APPROACH


    public static void runAzureQueryAIChat(String mySqlString, AzureDataCallback callback) {
        AzureApiService apiService = RetrofitClient.getRetrofitInstance().create(AzureApiService.class);
        QueryRequest requestBody = new QueryRequest(mySqlString);

        Call<List<Map<String, Object>>> call = apiService.executeCustomQuery(requestBody);

        // .enqueue() runs in the background automatically. No frozen UI!
        call.enqueue(new retrofit2.Callback<List<Map<String, Object>>>() {

            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, retrofit2.Response<List<Map<String, Object>>> response) {
                ArrayList<RowInTable> resultTable = new ArrayList<>();

                if (response.isSuccessful() && response.body() != null) {
                    List<Map<String, Object>> dynamicSqlData = response.body();

                    if (dynamicSqlData.size() > 0) {
                        for (int i = 0; i < dynamicSqlData.size(); i++) {
                            Map<String, Object> row = dynamicSqlData.get(i);
                            RowInTable rowInTable = new RowInTable();

                            for (String key : row.keySet()) {
                                rowInTable.putKeyAndValueToRow(key, String.valueOf(row.get(key)));
                            }

                            resultTable.add(rowInTable);
                        }

                        // SUCCESS! Send the filled ArrayList back to where it was called
                        callback.onSuccess(resultTable);

                    } else {
                        Log.d("AZURE_DATABASE_SQL", "No result");
                        callback.onSuccess(new ArrayList<>()); // Send back empty list
                    }
                } else {
                    Log.d("AZURE_ERROR", "Response failed or body is null");
                    callback.onFailure("Server returned an error");
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {
                Log.e("AZURE_CUSTOM_SQL", "Network fail: " + t.getMessage());
                callback.onFailure(t.getMessage());
            }
        });
    }
}
