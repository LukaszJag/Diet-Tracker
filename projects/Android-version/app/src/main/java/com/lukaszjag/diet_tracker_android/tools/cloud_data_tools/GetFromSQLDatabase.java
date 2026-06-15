package com.lukaszjag.diet_tracker_android.tools.cloud_data_tools;

import android.util.Log;
import android.widget.Toast;

import com.lukaszjag.diet_tracker_android.gui.AddMealToCalendar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFromSQLDatabase {
    private void fetchData() {
        // 1. Create the API service
        Log.i("i", "1. Create the API service");
        AzureApiService apiService = RetrofitClient.getRetrofitInstance().create(AzureApiService.class);

        // 2. Call the server asynchronously (won't freeze your app)
        Log.i("i", "2. Call the server asynchronously (won't freeze your app)");
        Call<List<CalendarDay>> call = apiService.getCalendarData("calendar");
        call.enqueue(new Callback<List<CalendarDay>>() {

            @Override
            public void onResponse(Call<List<CalendarDay>> call, Response<List<CalendarDay>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<CalendarDay> sqlData = response.body();

                    // Success! Let's display the name of the first item in a Toast.
                    if (sqlData.size() > 0) {
                        String firstItemName = sqlD ata.get(0).getProduct_name();

                        Log.i("AZURE_SQL_INFO", "Connected! Found: " + firstItemName);

                        // You can also print the whole list to the Android Studio Logcat
                        for (CalendarDay CalendarDay : sqlData) {
                            Log.d("AZURE_SQL_DATA", "Name: " + CalendarDay.getProduct_name() + ", Email: " + CalendarDay.getDay_date());
                        }
                    } else {
                        Log.i("AZURE_SQL_INFO", "Connected, but SQL table is empty");
                    }

                } else {

                    if(response.isSuccessful() ){
                        Log.i("check_data", "response.isSuccessful() is true");
                    } else {

                        try {
                            String errorUrl = response.raw().request().url().toString(); // <--- GETS THE EXACT URL
                            Log.e("AZURE_SQL_ERROR", "Android tried to ping this URL: " + errorUrl);
                            Log.e("AZURE_SQL_ERROR", "Server Code: " + response.code());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    // Reveal the true error message hidden in errorBody
                    try {
                        String errorMessage = "Unknown error";
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                        }
                        Log.e("AZURE_SQL_ERROR_OLD", "Server Code: " + response.code() + " | Error: " + errorMessage);
                        Log.e("AZURE_SQL_ERROR_OLD", "Server error: " + response.code());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if(response.body() == null){
                    Log.i("check_data", "Response body is null");
                }else{
                    Log.i("check_data", "Response body is not null: " + response.body().toString());
                }
                Log.e("AZURE_SQL_ERROR", "Server error: " + response.code());
            }


            @Override
            public void onFailure(Call<List<CalendarDay>> call, Throwable t) {
                // This runs if there's no internet, wrong URL, or JSON parsing error
                Log.e("AZURE_SQL_ERROR", "Connection Failed!");
                Log.e("AZURE_SQL_ERROR", t.getMessage());
            }
        });
    }
}
