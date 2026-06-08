package com.lukaszjag.diet_tracker_android.tools.cloud_data_tools;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // Look closely! We are using your specific hash and polandcentral-01 domain!
    // Make sure it ends with the slash ( / ) at the end!
    private static final String BASE_URL = "https://diettrackerandroidversionapi-grcbhva9e5gqhzhz.polandcentral-01.azurewebsites.net/api/AndroidAzure";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}