package com.openclassrooms.mynews.Utils;

import com.openclassrooms.mynews.Models.TopStories;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Class used to manage the streams of the HTTP requests for the Top Stories API
 */
public class TopStoriesStreams {

    public static Observable<TopStories> streamFetchTopStories(){
        TopStoriesService topStoriesService = TopStoriesService.retrofit.create(TopStoriesService.class);
        return topStoriesService.getTopStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10000, TimeUnit.SECONDS);
    }
}
