package com.cs4230.finalproject.controller;

/**
 * Created by Arthur Brennan on 12/15/2016.
 */

import com.cs4230.finalproject.service.DBHelper;
import com.cs4230.finalproject.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class TwitterLogin {

    private DBHelper db = new DBHelper();
    private User user = new User();

    public void checkUser(){

        String uName = user.getUserName();
        String pWord = user.getPassword();
        ArrayList<String> profile;

        try {
            DBHelper.connection();
        } catch (SQLException | IOException e) {

            e.printStackTrace();
        }

        String validate = db.login(uName, pWord);


        if (validate.equals("0")){
            // display an alert saying "Invalid Username or Password"
            user.setValid(false);
        }
        else {
            profile = db.displayProfile(validate);
            user.setValid(true);

            //go to profile page and pass profile arrayList


        }
    }
}

