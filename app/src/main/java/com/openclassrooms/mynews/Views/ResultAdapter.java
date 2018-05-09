package com.openclassrooms.mynews.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.mynews.Models.Result;
import com.openclassrooms.mynews.Models.TopStories;
import com.openclassrooms.mynews.R;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultViewHolder> {

    public interface Listener {

    }

    // FOR COMMUNICATION
    private final Listener callback;

    // FOR DATA
    // private final TopStories results;
    private final List<Result> resultsL;
    private final RequestManager glide;

    // CONSTRUCTOR
    public ResultAdapter (TopStories results, RequestManager glide, Listener callback) {
        this.resultsL = results.getResults();
        this.glide = glide;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, parent, false);

        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder viewHolder, int position) {
        viewHolder.updateWithResult(this.resultsL.get(position), this.glide, this.callback);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() { return this.resultsL.size();
    }

    public Result getResult(int position){
        return this.resultsL.get(position);
    }
}
