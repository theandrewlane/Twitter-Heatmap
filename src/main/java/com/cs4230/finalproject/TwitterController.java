package com.cs4230.finalproject;

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
private final StreamService streamService;
    private final Twitter twitter;

    private final ConnectionRepository connectionRepository;

    @Inject
    public TwitterController(StreamService streamService, Twitter twitter, ConnectionRepository connectionRepository) {
        this.streamService = streamService;
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping("/")
    public String helloTwitter(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }

        model.addAttribute(twitter.userOperations().getUserProfile());
        SearchResults tweets  = twitter.searchOperations().search("#BeerPong");
        List<Tweet> tweetz = new ArrayList<>();
        tweetz.addAll(tweets.getTweets());
        model.addAttribute("tweets", tweetz);
        return "tweet";
    }

    @RequestMapping("/stream")
    public String streamTweet(Model model) throws InterruptedException{
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }
        model.addAllAttributes(streamService.streamApi(model));
        return "stream";
    }

}
