package com.cs4230.finalproject.controller;

import com.cs4230.finalproject.model.TweetFilter;
import com.cs4230.finalproject.model.TweetGeocode;
import com.cs4230.finalproject.model.TweetUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
public class TwitterStreamService {

    private SimpMessagingTemplate template;
    private int tweetCount = 0;

    private JsonObject bounds;

    @Autowired
    public TwitterStreamService(SimpMessagingTemplate template) {

        this.template = template;
    }

//    @MessageMapping("/tweetLocation")
//    public void setLocation(String location) {
////Data from view should be sent here
//
//    }

    @MessageMapping("/bounds")
    public void getBounds(String jsonString) {
        System.out.println(jsonString);
        //parse
        JsonParser parser = new JsonParser();
        Object obj = parser.parse(jsonString);
        bounds = (JsonObject) obj;
        System.out.println(bounds.toString());
    }

    @SendTo("/tweets/stream")
    public void tweetStream(String jsonString) {
        this.template.convertAndSend("/tweets/stream", jsonString);
    }

//    private final org.slf4j.Logger log = LoggerFactory.getLogger(TwitterStreamService.class);
    private final List<Tweet> tweets = new ArrayList<>();

    @Autowired
    private Twitter twitter;

//    private TweetAnalysis ta = new TweetAnalysis();

    private TweetFilter tf = new TweetFilter();

    private TweetGeocode tg = new TweetGeocode();

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
                Tweet filteredTweet = tf.filterByHashTag(tweet);
                //Geocode the filtered tweet
                JsonObject coordinates = tg.geocode(filteredTweet);
                //Analyze each tweet
                //ta.add(filteredTweet);
                // TODO
                //Send to GUI
                if(coordinates != null && locationVerification(coordinates)) {
                    //create custom tweet class to store lat and lng
                    //and whatever extra parameters need to be sent to the front end
                    TweetUser user = new TweetUser();
                    user.setLat(coordinates.get("lat").toString());
                    user.setLng(coordinates.get("lng").toString());
                    user.setName(filteredTweet.getUser().getName());
                    //convert to a json string and send to stream
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(user);
                    //System.out.println(jsonString);
                    tweetStream(jsonString);
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
        Float west = -124.7844079f;
        Float south = 24.7433195f;
        Float east = -66.9513812f;
        Float north = 49.3457868f;

        FilterStreamParameters filterStreamParameters = new FilterStreamParameters();
        filterStreamParameters.addLocation(west, south, east, north);

        twitter.streamingOperations().filter(filterStreamParameters, listeners);

        return tweets;
    }

    public boolean locationVerification(JsonObject jsonObject) {

        double northCheck = Double.parseDouble(bounds.get("north").toString());
        double southCheck = Double.parseDouble(bounds.get("south").toString());
        double eastCheck = Double.parseDouble(bounds.get("east").toString());
        double westCheck = Double.parseDouble(bounds.get("west").toString());

        double lat = Double.parseDouble(jsonObject.get("lat").toString());
        double lng = Double.parseDouble(jsonObject.get("lng").toString());

        if (lat < westCheck || lat > eastCheck) {
            return false;
        }

        if (lng < southCheck || lng > northCheck) {
            return false;
        }

        return true;
    }
}
