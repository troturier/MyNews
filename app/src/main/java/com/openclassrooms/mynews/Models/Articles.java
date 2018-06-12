
package com.openclassrooms.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class used to manipulate a list of articles
 * Especially when retrieving a response through an HTTP request
 */
public class Articles {

    @SerializedName(value="results", alternate={"docs"})
    @Expose
    private List<Article> articles = null;

    public List<Article> getArticles() { return articles; }

    public Article getArticle(int position){ return articles.get(position);}

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
