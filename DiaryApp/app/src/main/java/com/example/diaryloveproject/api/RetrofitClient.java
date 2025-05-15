package com.example.diaryloveproject.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.14.5:9000/";
    private static RetrofitClient instance;
    private final Retrofit retrofit;

    private RetrofitClient() {
        // Custom Gson hỗ trợ LocalDateTime
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                                LocalDateTime.parse(json.getAsString()))
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))  // dùng Gson đã custom
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public ApiService getAuthApi() {
        return retrofit.create(ApiService.class);
    }
}