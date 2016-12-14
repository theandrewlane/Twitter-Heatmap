package com.cs4230.finalproject.Utilities;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Set;

/**
 * Created by jthomann on 12/5/16.
 */
@Component
public class KeywordReader {
    private Set<String> keywordList;
    private BufferedReader bf;
    private FileReader fr;

    public void init(String fileName, Set<String> keywordList) {
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

    public Set<String> getKeywordList() {
        return keywordList;
    }
}
