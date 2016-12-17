package com.cs4230.finalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@SpringBootApplication
public class TwitterThingApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(TwitterThingApplication.class, args);
    }

    @Bean
    public Twitter twitter() {
        String consumerKey = System.getenv("TWIT_CONSUMER_KEY");// The application's consumer key
        String consumerSecret = System.getenv("TWIT_CONSUMER_SECRET");// The application's consumer secret
        String accessToken = System.getenv("TWIT_ACCESS_TOKEN");// The access token granted after OAuth authorization
        String accessTokenSecret = System.getenv("TWIT_ACCESS_TOKEN_SECRET");// The access token secret granted after OAuth authorization
        return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
}