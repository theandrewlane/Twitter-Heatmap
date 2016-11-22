package com.cs4230.finalproject.controller;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class TwitterController {
private final TwitterStreamController twitterStreamController;
    private final Twitter twitter;
    private final ConnectionRepository connectionRepository;

    @Inject
    public TwitterController(TwitterStreamController streamService, Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitterStreamController = streamService;
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping("/")
    public String home() {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "forward:/connect/twitter";
        }
        return null;
    }

    @RequestMapping("/twitter-search")
    public String helloTwitter(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "forward:/connect/twitter";
        }        model.addAttribute(twitter.userOperations().getUserProfile());
        SearchResults tweets  = twitter.searchOperations().search("#BeerPong");
        List<Tweet> tweetz = new ArrayList<>();
        tweetz.addAll(tweets.getTweets());
        model.addAttribute("tweets", tweetz);
        return "twitterSearch/search";
    }

    @RequestMapping("/twitter-stream")
    public String streamTweet(Model model) throws InterruptedException{
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "forward:/connect/twitter";
        }
        twitterStreamController.streamApi(model);
        return "twitterStream/stream";
    }


}
