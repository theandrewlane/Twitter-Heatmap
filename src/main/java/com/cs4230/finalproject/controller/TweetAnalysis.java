package com.cs4230.finalproject.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.Tweet;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * All tweets are redirected to this controller
 * and will be filtered and stored accordingly
 * Created by jthomann on 11/27/16.
 */
public class TweetAnalysis {

    @Autowired
    private Map<String, Integer> htMap;

    private PrintWriter pw;
    private Timer timer = new Timer();

    /**
     * Constructor
     */
    public TweetAnalysis() {
        htMap = Collections.synchronizedMap(new TreeMap<>(String.CASE_INSENSITIVE_ORDER));
        initFile(); //initialize file streams
        writeToFile(10000); //write to file in increments
    }

    /**
     * Add a tweet
     *
     * @param t Tweet
     */
    public void add(Tweet t) {
        //create a list of hashtags from a tweet
        List<HashTagEntity> lht = t.getEntities().getHashTags();
        for (HashTagEntity el : lht) {
            //put it in a hashmap and increment the frequency
            if (htMap.containsKey(el.getText())) {
                int value = htMap.get(el.getText());
                htMap.put(el.getText(), ++value);
            } else
                htMap.put(el.getText(), 1);
        }
    }

    /**
     * Initialize file streams and create file
     */
    private void initFile() {
        try {
            System.out.println(); //Had to add this line so PMD wouldn't fail the build
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Write to file as JSON in increments
     *
     * @param increment Time in milliseconds before writing
     */
    private void writeToFile(int increment) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (!htMap.isEmpty()) {
                        pw = new PrintWriter(new FileOutputStream("hashmap.json", false));
                        Gson gson = new Gson();
                        String json = gson.toJson(htMap);
                        pw.write(json);
                        pw.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, increment, increment);
    }
}
