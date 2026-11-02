package com.lukaszjag.diet_tracker_android.tools.cloud_data_tools;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://diettrackerandroidversionapi-grcbhva9e5gqhzhz.polandcentral-01.azurewebsites.net/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    // MUST be placed BEFORE GsonConverterFactory:
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}