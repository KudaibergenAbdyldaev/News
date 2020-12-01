package com.example.newsapi.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRepository {
    //Inti Retrofit, что бы не вызывать снова и снова
    public static final String BASE_URL = "https://newsapi.org/v2/";
    private static NewsRepository apiFactory;
    private static Retrofit retrofit;

    private NewsRepository() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static NewsRepository getInstance() {
        if (apiFactory == null) {
            apiFactory = new NewsRepository();
        }
        return apiFactory;
    }

    public ApiInterface getApiService() {
        return retrofit.create(ApiInterface.class);
    }
}
