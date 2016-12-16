package com.cs4230.finalproject.controller;

import com.google.gson.JsonObject;
import org.springframework.social.twitter.api.FilterStreamParameters;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.Twitter;
import java.util.List;

/**
 * Created by jthomann on 12/15/16.
 */
public class TwitterFilterParameters {

    public void add(List<StreamListener> listeners, Twitter twitter, JsonObject bounds) {
        Float west = Float.parseFloat(bounds.get("west").toString());
        Float south = Float.parseFloat(bounds.get("south").toString());
        Float east = Float.parseFloat(bounds.get("east").toString());
        Float north = Float.parseFloat(bounds.get("north").toString());
        FilterStreamParameters fsp = new FilterStreamParameters();
        fsp.addLocation(west, south, east, north);
        twitter.streamingOperations().filter(fsp, listeners);
    }
}
