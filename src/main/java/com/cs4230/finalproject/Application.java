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
        String consumerKey = "Xx4kDdCGz6eLViniKP7Tib14W"; // The application's consumer key
        String consumerSecret = "5nQNHJjSkW1Csk2UHg394SfRJz2ZVdeGv3qS5l1oXPBP6FpURe"; // The application's consumer secret
        String accessToken = "801175468388945920-clf0yXAQ7edbVgehemQc6udEqqTKj3m"; // The access token granted after OAuth authorization
        String accessTokenSecret = "M9z1953ZSftGDUxyviln72dEFsnZKAMNUeUJDFv7lEcei"; // The access token secret granted after OAuth authorization
        return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
}