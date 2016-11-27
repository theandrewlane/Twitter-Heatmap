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
        String consumerKey = "36l901PIgIUBMD44PtfW9jMvK"; // The application's consumer key
        String consumerSecret = "2tbEC4zA8yZ2FARRQP6aiQUH9OaPrPAtrJxsIJuxIhU65ByHXE"; // The application's consumer secret
        String accessToken = "116844737-JhEcoCteosHLPYm02k7ZGWZ3jW6jFWIiyIgDjp51"; // The access token granted after OAuth authorization
        String accessTokenSecret = "Q8XMMcGMg6IiXkrqLTt26yWo0JV6Q1Ta3fDWIoocuBd49"; // The access token secret granted after OAuth authorization
        return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
}