package com.cs4230.finalproject.controller;

/**
 * Created by Arthur Brennan on 12/15/2016.
 */

import com.cs4230.finalproject.model.User;
import com.cs4230.finalproject.service.DBHelper;

public class TwitterSignup {

    private DBHelper db = new DBHelper();
    private User user = new User();


    public void newUserSignUp() {

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userName = user.getUserName();
        String email = user.getEmail();
        String password = user.getPassword();

        boolean checkUser = checkUser(userName);
        boolean checkEmail = checkEmail(email);

        if (checkUser && checkEmail){
            db.newUserInfo(firstName, lastName, email, userName, password);

            //then maybe reload page?? or go to their profile page??

        }

        if (!checkUser){
            // display alert "User name is already in use"

        }

        if (!checkEmail){
            //display alert "Email address must be unique"


        }
    }

    private boolean checkUser(String userName){

        boolean valid = false;

        String checkUser = db.checkUserName(userName);

        if (checkUser.equals("0"))
            valid = true;

        return valid;
    }

    private boolean checkEmail(String email){

        boolean valid = false;

        String checkEmail = db.checkEmail(email);

        if (checkEmail.equals("0"))
            valid = true;

        return valid;
    }


}
