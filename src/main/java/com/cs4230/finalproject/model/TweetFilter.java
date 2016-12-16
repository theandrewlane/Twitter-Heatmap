package com.cs4230.finalproject.model;

import com.cs4230.finalproject.service.DBHelper;
import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.Tweet;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.Collections;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jthomann on 12/5/16.
 */
public class TweetFilter {

    private Set<String> keywordSet;

    public TweetFilter() {
        try {
            DBHelper.connection();
        } catch (IOException | SQLException e){
            e.printStackTrace();
        }
        this.keywordSet = new HashSet<>(new DBHelper().getKeywords());
    }

    public Tweet filterByHashTag(Tweet tweet) {
        for(HashTagEntity hashtag : tweet.getEntities().getHashTags()) {
            if(keywordSet.contains(hashtag.getText().toLowerCase())) {
                return tweet;
            }
        }
        return null;
    }
}
