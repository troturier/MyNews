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

import java.util.List;

/**
 * Class for the adapter specific to the results
 */
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

    /**
     * Create view holder and inflating its xml layout
     * @param parent ViewGroup
     * @param viewType int
     * @return ResultViewHolder
     */
    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, parent, false);

        return new ResultViewHolder(view);
    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     * @param viewHolder a ResultViewHolder
     * @param position Integer
     */
    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder viewHolder, int position) {
        viewHolder.updateWithResult(this.resultsL.get(position), this.glide, this.callback);
    }

    /**
     * Returns the total count of items in the list
     * @return Results list size
     */
    @Override
    public int getItemCount() { return this.resultsL.size();
    }

    /**
     * Returns a specific result in the results list
     * @param position Result position
     * @return A result
     */
    public Result getResult(int position){
        return this.resultsL.get(position);
    }
}
