package com.openclassrooms.mynews.Controllers.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.mynews.Controllers.Activities.WebViewActivity;
import com.openclassrooms.mynews.Models.Article;
import com.openclassrooms.mynews.Models.Articles;
import com.openclassrooms.mynews.Models.Result;
import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.ItemClickSupport;
import com.openclassrooms.mynews.Utils.MyApplication;
import com.openclassrooms.mynews.Utils.NyTimesStreams;
import com.openclassrooms.mynews.Views.ResultArticleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchFragment extends Fragment implements ResultArticleAdapter.Listener {

    // FOR DESIGN
    @BindView(R.id.fragment_search_recycler_view)
    RecyclerView recyclerViewSearch;
    @BindView(R.id.fragment_search_swipe_container)
    SwipeRefreshLayout swipeRefreshLayoutSearch;

    //FOR DATA
    private Disposable disposableSearch;
    private List<Article> articleList2;
    private ResultArticleAdapter adapterSearch;
    private String query = "";
    private String start_date = "";
    private String end_date = "";
    private String section = "";

    public SearchFragment() { }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            query = bundle.getString("query", null);
            start_date = bundle.getString("sDate", null);
            end_date = bundle.getString("eDate", null);
            section = bundle.getString("section", "type_of_material:News");
        }
        this.configureRecyclerView();
        this.configureSwipeRefreshLayout();
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit();
        swipeRefreshLayoutSearch.setRefreshing(false);
        return view;
    }

    // -----------------
    // ACTION
    // -----------------

    /**
     * Load the URL of an article in the WebView activity
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerViewSearch, R.layout.fragment_search_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Article article = adapterSearch.getArticle(position);
                    Intent intent = new Intent(getContext(), WebViewActivity.class);
                    intent.putExtra("Url", article.getWebUrl());
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
        this.articleList2 = new ArrayList<>();
        Articles articles2 = new Articles();
        articles2.setArticles(articleList2);
        // Create adapter passing in the sample result data
        this.adapterSearch = new ResultArticleAdapter(articles2, Glide.with(this), this);
        // Attach the adapter to the recyclerview to populate items
        this.recyclerViewSearch.setAdapter(this.adapterSearch);
        // Set layout manager to position the items
        this.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * Will trigger a new Http request to the API when the user swipe from the top to the bottom while being at the top of the RecyclerView
     */
    private void configureSwipeRefreshLayout(){
        swipeRefreshLayoutSearch.setOnRefreshListener(this::executeHttpRequestWithRetrofit);
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    /**
     * Will execute a Http request using RxJAVA and Retrofit
     */
    public void executeHttpRequestWithRetrofit(){
        ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        this.disposableSearch = NyTimesStreams.streamFetchSearch(query, start_date, end_date, section).subscribeWith(new DisposableObserver<Result>() {
                    @Override
                    public void onNext(Result result) {
                        updateUISearch(result.getArticles().getArticles());
                    }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onComplete() { mProgressDialog.dismiss(); }
                });
    }

    /**
     * Perform any final cleanup before an activity is destroyed.
     */
    private void disposeWhenDestroy(){
        if (this.disposableSearch != null && !this.disposableSearch.isDisposed()) this.disposableSearch.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    /**
     * Update the UI with the new values retrieved by the HTTP request
     * @param res List of articles (Articles)
     */
    private void updateUISearch(List<Article> res){
        articleList2.clear();
        articleList2.addAll(res);
        if (articleList2.size() == 0){
            getActivity().onBackPressed();
            Toast.makeText(MyApplication.getAppContext(), "No article found with current parameters", Toast.LENGTH_LONG).show();
        }
        adapterSearch.notifyDataSetChanged();
        swipeRefreshLayoutSearch.setRefreshing(false);
    }
}
