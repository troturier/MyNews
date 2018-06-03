package com.openclassrooms.mynews.Models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MediumTest {

    private String testStr = "test";
    private Medium medium = new Medium();
    private Multimedium multimedium = new Multimedium();

    @Test
    public void setType() {
        medium.setType(testStr);
        assertEquals("Type getter or setter invalid", testStr, medium.getType());
    }

    @Test
    public void setCaption() {
        medium.setCaption(testStr);
        assertEquals("Caption getter or setter invalid", testStr, medium.getCaption());
    }

    @Test
    public void setMultimedia() {
        multimedium.setUrl(testStr);
        List<Multimedium> multimediumList = new ArrayList<>();
        multimediumList.add(multimedium);
        medium.setMultimedia(multimediumList);
        assertEquals("Multimedia getter or setter invalid", testStr, medium.getMultimedia().get(0).getUrl());
    }
}