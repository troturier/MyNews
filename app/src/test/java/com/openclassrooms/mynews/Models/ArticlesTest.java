package com.openclassrooms.mynews.Models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ArticlesTest {

    private Article article = new Article();
    private Articles articles = new Articles();

    @Test
    public void setArticles() {
        String testStr = "test";
        article.setTitle(testStr);
        List<Article> articlesL = new ArrayList<>();
        articlesL.add(article);
        articles.setArticles(articlesL);
        List<Article> articles2 = articles.getArticles();
        assertEquals("Articles getter or setter is invalid", testStr, articles.getArticle(0).getTitle());
        assertEquals("Articles getter or setter is invalid", testStr, articles2.get(0).getTitle());
    }
}