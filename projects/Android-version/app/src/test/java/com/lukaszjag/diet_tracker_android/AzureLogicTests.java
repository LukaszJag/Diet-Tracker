package com.lukaszjag.diet_tracker_android;

import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.AzureApiService;
import com.lukaszjag.diet_tracker_android.tools.cloud_data_tools.RetrofitClient;

import org.junit.Test;

public class AzureLogicTests {
    @Test
    public void initVariables(){
        RetrofitClient.getRetrofitInstance().create(AzureApiService.class);
        AzureApiService apiService = RetrofitClient.getRetrofitInstance().create(AzureApiService.class);
    }

}
