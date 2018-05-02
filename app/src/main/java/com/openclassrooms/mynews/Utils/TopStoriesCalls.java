package com.openclassrooms.mynews.Utils;

import android.support.annotation.Nullable;

import com.openclassrooms.mynews.Models.TopStories;

import java.io.IOException;
import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopStoriesCalls {

    public interface Callbacks {
        void onResponse(@Nullable TopStories stories);
        void onFailure();
    }

    public static void fetchTopStories(Callbacks callbacks){

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        TopStoriesService topStoriesService = TopStoriesService.retrofit.create(TopStoriesService.class);

        Call<TopStories> call = topStoriesService.getTopStories();
        call.enqueue(new Callback<TopStories>() {

            @Override
            public void onResponse(Call<TopStories> call, Response<TopStories> response) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<TopStories> call, Throwable t) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
                if (t instanceof IOException) { }
            }
        });
    }
}
