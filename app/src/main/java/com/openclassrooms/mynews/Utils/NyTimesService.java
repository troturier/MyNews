package com.openclassrooms.mynews.Utils;

import com.openclassrooms.mynews.Models.Articles;
import com.openclassrooms.mynews.Models.Result;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Class used to create a Retrofit request for the NY Times API
 */
interface NyTimesService {

    @Headers({
            "api_key: 514f85e678024ec3a52dd5cb986fdc63"
    })
    @GET("svc/topstories/v2/{section}.json")
    Observable<Articles> getTopStories(@Path("section") String section);

    @GET("svc/search/v2/articlesearch.json?api-key=514f85e678024ec3a52dd5cb986fdc63&sort=newest&fq=type_of_material:News")
    Observable<Result> getSearch(
            @Query("q") String query,
            @Query("begin_date") String b_date,
            @Query("end_date") String e_date);

    @Headers({
            "api_key: 514f85e678024ec3a52dd5cb986fdc63"
    })
    @GET("svc/mostpopular/v2/mostviewed/all-sections/1.json")
    Observable<Articles> getMostPopular();

    OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientBuilder.build())
            .build();
}
