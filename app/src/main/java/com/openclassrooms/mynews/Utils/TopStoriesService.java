package com.openclassrooms.mynews.Utils;

import com.openclassrooms.mynews.Models.TopStories;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TopStoriesService {
    @GET("svc/topstories/v2/{section}.json?514f85e678024ec3a52dd5cb986fdc63")
    Call<List<TopStories>> getFollowing(@Path("section") String section);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
