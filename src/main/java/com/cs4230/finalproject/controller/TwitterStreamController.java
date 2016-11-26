package com.cs4230.finalproject.controller;

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
private Float east = -111.89f, south = 40.86f;

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

    //    private final Logger log = LoggerFactory.getLogger(TwitterStreamController.class);
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
            System.out.println("You've reached the tweet limits!");

            }

            @Override
            public void onDelete(StreamDeleteEvent deleteEvent) {
                // TODO Auto-generated method stub
            System.out.println("Tweet Deleted!");

            }
        };

        listeners.add(streamListener);
//    west - the longitude of the western side of the location's bounding box.
//    south - the latitude of the southern side of the location's bounding box.
//    east - the longitude of the eastern side of the location's bounding box.
//    north - the latitude of the northern side of the location's bounding box.
    Float west = getEast() + 2.5f;
//        south = 40.76f;
//        east = -111.89f;
    Float north = getSouth() + 2.5f;

        FilterStreamParameters filterStreamParameters = new FilterStreamParameters();
        filterStreamParameters.addLocation(west, south, east, north);

        twitter.streamingOperations().filter(filterStreamParameters, listeners);
        return tweets;
    }

public Float getEast()
    {
    return east;
    }

public void setEast(Float east)
    {
    this.east = east;
    }

public Float getSouth()
    {
    return south;
    }

public void setSouth(Float south)
    {
    this.south = south;
    }
}
