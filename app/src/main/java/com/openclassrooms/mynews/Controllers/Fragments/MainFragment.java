package com.openclassrooms.mynews.Controllers.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.openclassrooms.mynews.Controllers.Activities.WebViewActivity;
import com.openclassrooms.mynews.Models.Article;
import com.openclassrooms.mynews.Models.Articles;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.ItemClickSupport;
import com.openclassrooms.mynews.Utils.NyTimesStreams;
import com.openclassrooms.mynews.Views.ArticleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Class used for the Fragment of the Main activity
 */
public class MainFragment extends Fragment implements ArticleAdapter.Listener {

    // FOR DESIGN
    @BindView(R.id.fragment_main_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_main_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    //FOR DATA
    private Disposable disposable;
    private List<Article> articleList;
    private ArticleAdapter adapter;

    public MainFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView();
        this.configureSwipeRefreshLayout();
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit();
        swipeRefreshLayout.setRefreshing(false);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -----------------
    // ACTION
    // -----------------

    /**
     * Load the URL of an article in the WebView activity
     */
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_main_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Article article = adapter.getArticle(position);
                    Intent intent = new Intent(getContext(), WebViewActivity.class);
                    intent.putExtra("Url", article.getUrl());
                    startActivity(intent);
                });
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    /**
     * Configure the RecyclerView by creating a new Adapter object and attaching it to the RecyclerView
     */
    private void configureRecyclerView(){
        this.articleList = new ArrayList<>();
        Articles articles = new Articles();
        articles.setArticles(articleList);
        // Create adapter passing in the sample result data
        this.adapter = new ArticleAdapter(articles, Glide.with(this), this);
        // Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);
        // Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * Will trigger a new Http request to the API when the user swipe from the top to the bottom while being at the top of the RecyclerView
     */
    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(this::executeHttpRequestWithRetrofit);
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    /**
     * Will execute a Http request using RxJAVA and Retrofit
     */
    private void executeHttpRequestWithRetrofit(){
        this.disposable = NyTimesStreams.streamFetchTopStories().subscribeWith(new DisposableObserver<Articles>() {
            @Override
            public void onNext(Articles stories) {
                updateUI(stories.getArticles());
            }

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onComplete() { }
        });
    }

    /**
     * Perform any final cleanup before an activity is destroyed.
     */
    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    /**
     * Update the UI with the new values retrieved by the HTTP request
     * @param res List of articles (Articles)
     */
    private void updateUI(List<Article> res){
        articleList.clear();
        articleList.addAll(res);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
