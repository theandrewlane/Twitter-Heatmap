package com.cs4230.finalproject.controller;

import com.cs4230.finalproject.model.TweetFilter;
import com.cs4230.finalproject.model.TweetGeocode;
import com.cs4230.finalproject.model.TweetUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrewlane on 11/16/16.
 */

@Controller
public class TwitterStreamService implements StreamListener {

    private TweetFilter tf = new TweetFilter();
    private TweetGeocode tg = new TweetGeocode();
    private int tweetCount = 0;
    private final List<Tweet> tweets = new ArrayList<>();

    @Autowired
    private SendController sc;

    @SuppressWarnings("unused")
    @Autowired
    private ReceiveController rc;

    @Override
    public void onWarning(StreamWarningEvent warningEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTweet(Tweet tweet) {
        //print out tweet to console
        System.out.println(tweet.getText());
        //Filter each tweet
        Tweet filteredTweet = tf.filterByHashTag(tweet);
        //Geocode the filtered tweet
        JsonObject coordinates = tg.geocode(filteredTweet);
        //Send to GUI
        if(coordinates != null) {
            //create custom tweet class to store lat and lng
            //and whatever extra parameters need to be sent to the front end
            TweetUser user = new TweetUser();
            user.setLat(coordinates.get("lat").toString());
            user.setLng(coordinates.get("lng").toString());
            user.setName(filteredTweet.getUser().getName());
            //convert to a json string and send to stream
            Gson gson = new Gson();
            String jsonString = gson.toJson(user);
            System.out.println(jsonString);
            sc.tweetStream(jsonString);
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

    List<Tweet> streamApi(Model model) throws InterruptedException {
        tweetCount++;
        model.addAttribute("tweetCount", tweetCount);
        return tweets;
    }

}
