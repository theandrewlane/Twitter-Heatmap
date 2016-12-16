package com.cs4230.finalproject.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.social.twitter.api.Stream;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jthomann on 12/14/16.
 */
@Controller
public class ReceiveController {

    private JsonObject bounds;
    private TwitterFilterParameters tfp = new TwitterFilterParameters();
    private List<StreamListener> listeners = new ArrayList<>();

    @Autowired
    private Twitter twitter;

    @Autowired
    private TwitterStreamService tss;

    @MessageMapping("/bounds")
    public void receiveBounds(String jsonString) {
        //parse
        JsonParser parser = new JsonParser();
        Object obj = parser.parse(jsonString);
        bounds = (JsonObject) obj;
        System.out.println(bounds.toString());
        this.listeners.add(tss);
        tfp.add(listeners, twitter, bounds);
    }
}
