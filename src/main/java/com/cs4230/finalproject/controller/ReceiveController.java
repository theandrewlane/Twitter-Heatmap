package com.cs4230.finalproject.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jthomann on 12/14/16.
 */
@Controller
public class ReceiveController {

    private List<StreamListener> listeners = new ArrayList<>();
    private List<Tweet> tweets = new ArrayList<>();

    @Autowired
    private Twitter twitter;

    @Autowired
    private TwitterStreamService tss;

    @SuppressWarnings("unused")
    @MessageMapping("/bounds")
    public void receiveBounds(String jsonString) {
        //parse
        JsonParser parser = new JsonParser();
        Object obj = parser.parse(jsonString);
        JsonObject bounds = (JsonObject) obj;
        System.out.println(bounds.toString());
        //clear tweets
        this.tweets.clear();
        this.listeners.add(tss);
        TwitterFilterParameters tfp = new TwitterFilterParameters();
        tfp.add(listeners, twitter, bounds);
    }

    @SuppressWarnings("unused")
    @MessageMapping("/disconnect")
    public void receiveDisconnect(String secret) {
        if(secret.equals("ewd87yewdew87dewcew3")) {
            listeners.clear();
        }
    }


    public List<Tweet> getTweets() {
        return this.tweets;
    }
}
