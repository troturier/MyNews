package com.openclassrooms.mynews.Utils;

import com.openclassrooms.mynews.Controllers.MainFragmentTest;
import com.openclassrooms.mynews.Models.Articles;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface NyTimesServiceTest {

    @GET("/topStories_200_ok_response.json")
    Observable<Articles> getTopStories();

    @GET("/mostPopular_200_ok_response.json")
    Observable<Articles> getMostPopular();

    OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MainFragmentTest.server.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientBuilder.build())
            .build();
}
