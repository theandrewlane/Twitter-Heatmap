package com.cs4230.finalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Twitter twitter() {
        //Should set these as environment variables on our app server
        String consumerKey = "***REMOVED***"; // The application's consumer key
        String consumerSecret = "***REMOVED***"; // The application's consumer secret
        String accessToken = "***REMOVED***"; // The access token granted after OAuth authorization
        String accessTokenSecret = "***REMOVED***"; // The access token secret granted after OAuth authorization
        return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
}