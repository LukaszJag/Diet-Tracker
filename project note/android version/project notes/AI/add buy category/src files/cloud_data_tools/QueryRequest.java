package com.lukaszjag.diet_tracker_android.tools.cloud_data_tools;

public class QueryRequest {
    private String sql_query;

    public QueryRequest(String customQuery) {
        this.sql_query = customQuery;
    }
}