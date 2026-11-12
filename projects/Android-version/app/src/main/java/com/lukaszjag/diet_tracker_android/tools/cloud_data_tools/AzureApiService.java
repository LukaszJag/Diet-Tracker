package com.lukaszjag.diet_tracker_android.tools.cloud_data_tools;

import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AzureApiService {

    // Enforce UTF-8 charset
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("api/AndroidAzure")
    Call<List<Map<String, Object>>> executeCustomQuery(@Body QueryRequest customQueryObject);

}