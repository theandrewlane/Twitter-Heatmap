package com.cs4230.finalproject.model;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;

import java.util.List;

/**
 * Created by andrewlane on 11/16/16.
 */
public class TweetQuery
    {

    private static List<String> list;

    //static block to load all search words into list
    static
        {
        try
            {
            list = Files.readAllLines(new File("partywords.txt").toPath(), Charset.defaultCharset());
            } catch (IOException e)
            {
            e.printStackTrace();
            }
        }

    public static List<String> getPartyWordList()
        {
        return list;
        }


    }
