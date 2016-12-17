package com.cs4230.finalproject.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/")
public class TwitterController {
    @SuppressWarnings("unused")
    @Inject
    private TwitterStreamService twitterStreamService;

    @Inject
    private Twitter twitter;

    @Inject
    public TwitterController(TwitterStreamService twitterStreamService, Twitter twitter) {
        this.twitterStreamService = twitterStreamService;
        this.twitter = twitter;
    }

    @RequestMapping("/")
    public String home(Model model) {
        //This is only set to true here for testing purposes... setting to false will trigger a login scenario
        model.addAttribute("hasAuth", true);
        return "index";
    }

    @PostMapping("/twitter-search")
    public String tweetSearchPost(Model model) {
        model.addAttribute("hasAuth", true);
        model.addAttribute(twitter.userOperations().getUserProfile());
        SearchResults tweets = twitter.searchOperations().search("test");
        List<Tweet> tweetz = new ArrayList<>();
        tweetz.addAll(tweets.getTweets());
        model.addAttribute("tweets", tweetz);
        model.addAttribute("isSearching", true);
        return "index";
    }

    @MessageMapping("/search")
    public void search(String query) {

    }

    @GetMapping("/twitter-search")
    public String tweetSearchGet(Model model) {
        model.addAttribute("hasAuth", true);
        model.addAttribute("isSearching", true);
        model.addAttribute("tweetSearch", null);
        return "index";
    }

    @RequestMapping("/twitter-stream")
    public String streamTweet(Model model) throws InterruptedException {
        model.addAttribute("hasAuth", true);
        model.addAttribute("isStreaming", true);
        return "index";
    }

    //This is only set up for testing purposes -
    //We should use this strategy for authentication - http://www.bogotobogo.com/Java/tutorials/Spring-Boot/Spring-Boot-Authetication-Securing-Web-Application.php
    @RequestMapping("/login")
    public String authCheck(Model model) {
        //If user isn't authenticated, they should be pushed to the login page
        model.addAttribute("hasAuth", false);
        return "user/login";
    }
}
