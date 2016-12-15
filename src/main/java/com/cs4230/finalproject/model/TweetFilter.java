package com.cs4230.finalproject.model;

import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.Tweet;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jthomann on 12/5/16.
 */
public class TweetFilter {

    private Set<String> keywordSet;

    public TweetFilter() {
        this.keywordSet = new HashSet<>();
        this.keywordSet.add("job");
    }

    public Tweet filterByHashTag(Tweet tweet) {
        for(HashTagEntity hashtag : tweet.getEntities().getHashTags()) {
            if(keywordSet.contains(hashtag.getText().toLowerCase())) {
                return tweet;
            }
        }
        return null;
    }

    public Tweet filter(Tweet tweet) {
        String[] tweetedWords = tweet.getText().split(" ");
        for(String keyword : keywordSet) {
            for(String text : tweetedWords) {
                if(text.equalsIgnoreCase(keyword)) {
                    return tweet;
                }
            }
        }
        return null;
    }

}
