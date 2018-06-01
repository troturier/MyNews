package com.openclassrooms.mynews.Utils;

import com.openclassrooms.mynews.Models.Articles;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NyTimesStreamsTest {

    public static Observable<Articles> streamFetchTopStories(){
        NyTimesServiceTest nyTimesServiceTest = NyTimesServiceTest.retrofit.create(NyTimesServiceTest.class);
        return nyTimesServiceTest.getTopStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10000, TimeUnit.SECONDS);
    }

    public static Observable<Articles> streamFetchMostPopular(){
        NyTimesServiceTest nyTimesServiceTest = NyTimesServiceTest.retrofit.create(NyTimesServiceTest.class);
        return nyTimesServiceTest.getMostPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10000, TimeUnit.SECONDS);
    }
}
