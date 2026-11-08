package com.lukaszjag.diet_tracker_android.tools.cloud_data_tools;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class NullOnEmptyConverterFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody body) throws IOException {
                if (body.contentLength() == 0) {
                    return new ArrayList<>();
                }
                String content = body.string();
                if (content == null || content.trim().isEmpty() || content.trim().equalsIgnoreCase("null")) {
                    return new ArrayList<>();
                }
                // Reconstruct the response body with the read content for Gson
                ResponseBody newBody = ResponseBody.create(body.contentType(), content);
                return delegate.convert(newBody);
            }
        };
    }
}