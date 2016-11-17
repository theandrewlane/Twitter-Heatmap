package com.cs4230.finalproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrewlane on 11/16/16.
 */

@Controller
public class TwitterStreamController {

    private SimpMessagingTemplate template;

    @Autowired
    public TwitterStreamController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/tweetLocation")
    public void setLocation(String location) {
//Data from view should be sent here

    }

    @SendTo("/topic/tweets")
    public void tweetStream(Tweet tweet) {
        this.template.convertAndSend("/topic/tweets", tweet);
    }

    private final Logger log = LoggerFactory.getLogger(TwitterStreamController.class);
    private final List<Tweet> tweets = new ArrayList<>();

    @Autowired
    private Twitter twitter;

    public List<Tweet> streamApi(Model model) throws InterruptedException {
        List<StreamListener> listeners = new ArrayList<>();

        StreamListener streamListener = new StreamListener() {

            @Override
            public void onWarning(StreamWarningEvent warningEvent) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTweet(Tweet tweet) {
                tweets.add(tweet);
                tweetStream(tweet);
            }

            @Override
            public void onLimit(int numberOfLimitedTweets) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDelete(StreamDeleteEvent deleteEvent) {
                // TODO Auto-generated method stub

            }
        };

        listeners.add(streamListener);
        //This sets the GeoCode (-122.75,36.8,-121.75,37.8) of San Francisco(South-West and North-East) region as given in below twitter docs
        Float west = -122.75f;
        Float south = 36.8f;
        Float east = -121.75f;
        Float north = 37.8f;

        FilterStreamParameters filterStreamParameters = new FilterStreamParameters();
        filterStreamParameters.addLocation(west, south, east, north);

        twitter.streamingOperations().filter(filterStreamParameters, listeners);
        return tweets;
    }
}
