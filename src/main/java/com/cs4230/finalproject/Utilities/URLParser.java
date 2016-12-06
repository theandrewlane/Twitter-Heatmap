package com.cs4230.finalproject.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.*;
import com.sun.codemodel.internal.JArray;
import jdk.nashorn.internal.parser.JSONParser;

/**
 * Created by jthomann on 12/5/16.
 */
public class URLParser {
    private URL url;
    private URLConnection urlConnection;

    public void init(String urlString) {
        try {
            this.url = new URL(urlString);
            this.urlConnection = url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject getResponseBody() {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                sb.append(inputLine);
            in.close();

            //parse
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(sb.toString());
            JsonObject jb = (JsonObject) obj;

            //read
            JsonElement status = jb.get("status");
            if(status.toString().equals("\"OK\"")) {
                JsonArray resultArray = (JsonArray) jb.get("results");
                JsonObject resultObject = (JsonObject) resultArray.get(0);
                JsonObject geometryObject = (JsonObject) resultObject.get("geometry");

                return (JsonObject) geometryObject.get("location");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
