package com.cs4230.finalproject.model;

//import org.springframework.social.twitter.api.Tweet;

/**
 * Created by jthomann on 12/14/16.
 */
public class TweetUser {
    private String lat;
    private String lng;
    private String name;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
