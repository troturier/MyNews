package com.openclassrooms.mynews.Utils;

import com.openclassrooms.mynews.Models.Articles;
import com.openclassrooms.mynews.Models.Result;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Class used to manage the streams of the HTTP requests for the NY Times API
 */
public class NyTimesStreams {

    public static Observable<Articles> streamFetchTopStories(String section){
        NyTimesService nyTimesService = NyTimesService.retrofit.create(NyTimesService.class);
        return nyTimesService.getTopStories(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10000, TimeUnit.SECONDS);
    }

    public static Observable<Articles> streamFetchMostPopular(){
        NyTimesService nyTimesService = NyTimesService.retrofit.create(NyTimesService.class);
        return nyTimesService.getMostPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10000, TimeUnit.SECONDS);
    }

    public static Observable<Result> streamFetchSearch(String query, String start_date, String end_date){
        NyTimesService nyTimesService = NyTimesService.retrofit.create(NyTimesService.class);
        if(query.isEmpty())
            query = null;
        if(start_date.isEmpty())
            start_date = null;
        if(end_date.isEmpty())
            end_date = null;
        return nyTimesService.getSearch(query, start_date, end_date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10000, TimeUnit.SECONDS);
        }
}
