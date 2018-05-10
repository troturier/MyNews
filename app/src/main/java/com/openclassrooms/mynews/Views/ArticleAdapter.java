package com.openclassrooms.mynews.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.mynews.Models.Article;
import com.openclassrooms.mynews.Models.Articles;
import com.openclassrooms.mynews.R;

import java.util.List;

/**
 * Class for the adapter specific to the articles
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    public interface Listener {

    }

    // FOR COMMUNICATION
    private final Listener callback;

    // FOR DATA
    // private final Articles results;
    private final List<Article> articleList;
    private final RequestManager glide;

    // CONSTRUCTOR
    public ArticleAdapter(Articles articles, RequestManager glide, Listener callback) {
        this.articleList = articles.getArticles();
        this.glide = glide;
        this.callback = callback;
    }

    /**
     * Create view holder and inflating its xml layout
     * @param parent ViewGroup
     * @param viewType int
     * @return ArticleViewHolder
     */
    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, parent, false);

        return new ArticleViewHolder(view);
    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     * @param viewHolder an ArticleViewHolder
     * @param position Integer
     */
    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder viewHolder, int position) {
        viewHolder.updateWithResult(this.articleList.get(position), this.glide, this.callback);
    }

    /**
     * Returns the total count of items in the list
     * @return Articles list size
     */
    @Override
    public int getItemCount() { return this.articleList.size();
    }

    /**
     * Returns a specific result in the results list
     * @param position Article position
     * @return An article
     */
    public Article getArticle(int position){
        return this.articleList.get(position);
    }
}
