package com.example.newsapi.network;

import com.example.newsapi.pojo.News;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    //For retrofit
    @GET("top-headlines")
    Observable<News> getNewsList(
            @Query("country") String country,
            @Query("apiKey") String apiKey);
}
