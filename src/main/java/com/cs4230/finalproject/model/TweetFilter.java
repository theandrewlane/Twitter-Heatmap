package com.cs4230.finalproject.model;

import com.cs4230.finalproject.Utilities.KeywordReader;
import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created by jthomann on 12/5/16.
 */
@Component
public class TweetFilter {

    public Tweet filterByHashTag(Set<String> keywordList, Tweet tweet) {
        for(HashTagEntity hashtag : tweet.getEntities().getHashTags()) {
            if(keywordList.contains(hashtag.getText().toLowerCase())) {
                return tweet;
            }
        }
        return null;
    }

    public Tweet filter(List<String> keywordList, Tweet tweet) {
        String[] tweetedWords = tweet.getText().split(" ");
        for(String keyword : keywordList) {
            for(String text : tweetedWords) {
                if(text.equalsIgnoreCase(keyword)) {
                    return tweet;
                }
            }
        }
        return null;
    }

}
