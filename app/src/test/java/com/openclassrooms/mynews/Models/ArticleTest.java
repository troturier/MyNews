package com.openclassrooms.mynews.Models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ArticleTest {

    private Article article = new Article();
    private String testStr = "test";

    @Test
    public void setTitle() {
        article.setTitle(testStr);
        assertEquals("Title setter or getter is invalid", testStr, article.getTitle());
    }

    @Test
    public void setSection() {
        article.setSection(testStr);
        assertEquals("Section setter or getter is invalid", testStr, article.getSection());
    }

    @Test
    public void setSubsection() {
        article.setSubsection(testStr);
        assertEquals("Subsection setter or getter is invalid", testStr, article.getSubsection());
    }

    @Test
    public void setUrl() {
        article.setUrl(testStr);
        assertEquals("Url setter or getter is invalid", testStr, article.getUrl());
    }

    @Test
    public void setPublishedDate() {
        article.setPublishedDate(testStr);
        assertEquals("PublishedDate setter or getter is invalid", testStr, article.getPublishedDate());
    }

    @Test
    public void setMultimedia() {
        Multimedium multimedia = new Multimedium();
        multimedia.setUrl(testStr);
        List<Multimedium> multimedium = new ArrayList<>();
        multimedium.add(multimedia);
        article.setMultimedia(multimedium);
        assertEquals("Multimedia setter or getter is invalid", testStr, article.getMultimedia().get(0).getUrl());
    }

    @Test
    public void setMedia() {
        Medium media = new Medium();
        media.setCaption(testStr);
        List<Medium> medium = new ArrayList<>();
        medium.add(media);
        article.setMedia(medium);
        assertEquals("Media setter or getter is invalid", testStr, article.getMedia().get(0).getCaption());
    }
}