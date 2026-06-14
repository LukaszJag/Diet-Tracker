package com.lukaszjag.diet_tracker_android.tools.cloud_data_tools;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AzureApiService {
    // This perfectly matches the name of your Azure Function from VS Code!
    @GET("api/AndroidAzure")
    Call<List<CalendarDay>> getDataFromAzure();
}
