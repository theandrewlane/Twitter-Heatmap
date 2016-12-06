package com.cs4230.finalproject.Utilities;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

/**
 * Created by jthomann on 12/5/16.
 */
@Component
public class KeywordReader {
    private List<String> keywordList;
    private BufferedReader bf;
    private FileReader fr;

    public void init(String fileName, List<String> keywordList) {
        try {
            this.fr = new FileReader(fileName);
            this.bf = new BufferedReader(this.fr);
            this.keywordList = keywordList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readInKeywords() {
        try {
            String line;

            while ((line = this.bf.readLine()) != null) {
                keywordList.add(line);
            }
            //bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getKeywordList() {
        return keywordList;
    }
}
