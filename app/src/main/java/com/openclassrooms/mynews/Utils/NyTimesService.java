package com.openclassrooms.mynews.Utils;

import com.openclassrooms.mynews.Models.Articles;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Class used to create a Retrofit request for the NY Times API
 */
interface NyTimesService {
    @Headers({
            "api_key: 514f85e678024ec3a52dd5cb986fdc63"
    })
    @GET("svc/topstories/v2/home.json")
    Observable<Articles> getTopStories();

    @Headers({
            "api_key: 514f85e678024ec3a52dd5cb986fdc63"
    })
    @GET("https://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/1.json")
    Observable<Articles> getMostPopular();

    OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientBuilder.build())
            .build();
}
