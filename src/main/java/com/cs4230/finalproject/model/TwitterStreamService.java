package com.cs4230.finalproject.model;

import org.springframework.beans.factory.annotation.Autowired;
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
public class TwitterStreamService {

    private SimpMessagingTemplate template;
    private int tweetCount = 0;

    private TweetAnalysis ta = new TweetAnalysis();

    @Autowired
    public TwitterStreamService(SimpMessagingTemplate template) {

        this.template = template;
    }

//    @MessageMapping("/tweetLocation")
//    public void setLocation(String location) {
////Data from view should be sent here
//
//    }



    @SendTo("/tweets/stream")
    public void tweetStream(Tweet tweet) {
        this.template.convertAndSend("/tweets/stream", tweet);
    }

//    private final org.slf4j.Logger log = LoggerFactory.getLogger(TwitterStreamService.class);
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
                //add to analysis controller
                ta.add(tweet);
                //ta.print();
                tweets.add(tweet);
                tweetStream(tweet);
                tweetCount++;
                model.addAttribute("tweetCount", tweetCount);
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
        Float west = -124.84f;
        Float south = 24.39f;
        Float east = -66.88f;
        Float north = 49.38f;

        FilterStreamParameters filterStreamParameters = new FilterStreamParameters();
        filterStreamParameters.addLocation(west, south, east, north);

        twitter.streamingOperations().filter(filterStreamParameters, listeners);
        return tweets;
    }
}
