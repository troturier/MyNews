package com.openclassrooms.mynews.Utils;

import com.openclassrooms.mynews.Models.TopStories;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface TopStoriesService {
    @Headers({
            "api_key: 514f85e678024ec3a52dd5cb986fdc63"
    })
    @GET("svc/topstories/v2/home.json")
    Call<TopStories> getTopStories();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
