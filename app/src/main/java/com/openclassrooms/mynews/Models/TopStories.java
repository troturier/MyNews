
package com.openclassrooms.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class used to manipulate a list of results (or articles)
 * Especially when retrieving a response through an HTTP request
 */
public class TopStories {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public List<Result> getResults() { return results; }

    public Result getResult(int position){ return results.get(position);}

    public void setResults(List<Result> results) {
        this.results = results;
    }

}
