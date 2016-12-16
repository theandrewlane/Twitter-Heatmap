package com.cs4230.finalproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/")
public class TwitterController {
    @Inject
    private TwitterStreamService twitterStreamService;

    @Inject
    private Twitter twitter;

    @Inject
    public TwitterController(TwitterStreamService streamService, Twitter twitter) {
        this.twitterStreamService = streamService;
        this.twitter = twitter;
    }

    @RequestMapping("/")
    public String home(Model model) {
        return "index";
    }

    @PostMapping("/twitter-search")
    public String tweetSearchPost(Model model) {
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
        model.addAttribute("isSearching", true);

        model.addAttribute("tweetSearch", null);
        return "index";
    }

    @RequestMapping("/twitter-stream")
    public String streamTweet(Model model) throws InterruptedException {
        model.addAttribute("isStreaming", true);
        twitterStreamService.streamApi(model);
        return "index";
    }
}
