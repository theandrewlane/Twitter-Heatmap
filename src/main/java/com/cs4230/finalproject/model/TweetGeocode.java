package com.cs4230.finalproject.model;

import com.cs4230.finalproject.utilities.URLParser;
import com.google.gson.JsonObject;
import org.springframework.social.twitter.api.Tweet;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by jthomann on 12/5/16.
 */
public class TweetGeocode {

    public JsonObject geocode(Tweet tweet) {
        try {
            String location = tweet.getUser().getLocation();
            if(location == null)
                return null;
            String apiKey = System.getenv("MAPS_API_KEY");
            String urlEncodedLocation = URLEncoder.encode(location, "UTF-8");

            String baseUrl = "https://maps.googleapis.com/maps/api/geocode/";
            String format = "json";
            URLParser urlReader = new URLParser();
            urlReader.init(baseUrl + format + "?address=" + urlEncodedLocation + "&key=" + apiKey);
            JsonObject obj = null;
            obj = urlReader.getResponseBody();
            if(obj != null) {
                return obj;
        }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
