package com.cs4230.finalproject.controller;

import com.cs4230.finalproject.Utilities.KeywordReader;
import com.cs4230.finalproject.model.TweetAnalysis;
import com.cs4230.finalproject.model.TweetFilter;
import com.cs4230.finalproject.model.TweetGeocode;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.apache.coyote.http11.Constants.a;

/**
 * Created by andrewlane on 11/16/16.
 */

@Controller
public class TwitterStreamService {

    private SimpMessagingTemplate template;
    private int tweetCount = 0;

    private TweetAnalysis ta;

    @Autowired
    private KeywordReader keywordReader;

    @Autowired
    private TweetFilter tf;

    @Autowired
    private TweetGeocode tg;

    @Autowired
    public TwitterStreamService(SimpMessagingTemplate template) {

        this.template = template;
    }

    public List<String> getKeywordList() {
        keywordReader.init("src/main/files/party-keywords.txt", new ArrayList<String>());
        keywordReader.readInKeywords();
        return keywordReader.getKeywordList();
    }
//    @MessageMapping("/tweetLocation")
//    public void setLocation(String location) {
////Data from view should be sent here
//
//    }

    @SendTo("/tweets/stream")
    public void tweetStream(JsonObject obj) {
        this.template.convertAndSend("/tweets/stream", obj.toString());
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
                //Filter each tweet
                Tweet filteredTweet = tf.filter(getKeywordList(), tweet);
                //Geocode the filtered tweet
                JsonObject coordinates = tg.geocode(filteredTweet);
                //Analyze each tweet
                //ta.add(filteredTweet);
                // TODO
                //Send to GUI
                if(coordinates != null) {
                    tweets.add(filteredTweet);
                    tweetStream(coordinates);
                    tweetCount++;
                    model.addAttribute("tweetCount", tweetCount);
                }
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
        Float west = -111.993474f;
        Float south = 33.332718f;
        Float east = -111.825695f;
        Float north = 33.4872f;

        FilterStreamParameters filterStreamParameters = new FilterStreamParameters();
        filterStreamParameters.addLocation(west, south, east, north);

        twitter.streamingOperations().filter(filterStreamParameters, listeners);
        return tweets;
    }
}
