package com.openclassrooms.mynews.Utils;

import android.support.annotation.Nullable;

import com.openclassrooms.mynews.Models.TopStories;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopStoriesCalls {

    // 1 - Creating a callback
    public interface Callbacks {
        void onResponse(@Nullable List<TopStories> users);
        void onFailure();
    }

    // 2 - Public method to start fetching top stories
    public static void fetchTopStories(Callbacks callbacks, String section){

        // 2.1 - Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        // 2.2 - Get a Retrofit instance and the related endpoints
        TopStoriesService topStoriesService = TopStoriesService.retrofit.create(TopStoriesService.class);

        // 2.3 - Create the call on NY Times API
        Call<List<TopStories>> call = topStoriesService.getTopStories(section);
        // 2.4 - Start the call
        call.enqueue(new Callback<List<TopStories>>() {

            @Override
            public void onResponse(Call<List<TopStories>> call, Response<List<TopStories>> response) {
                // 2.5 - Call the proper callback used in controller (MainFragment)
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<TopStories>> call, Throwable t) {
                // 2.5 - Call the proper callback used in controller (MainFragment)
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }
}
