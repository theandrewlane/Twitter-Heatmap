package com.cs4230.finalproject.model;

import java.util.ArrayList;

/**
 * Created by andrewlane on 12/14/16.
 */

public class User {

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<String> profile;
    private boolean valid;
    private boolean validUserName;
    private boolean validEmail;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfile(ArrayList<String> profile){ this.profile = profile; }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void setValidUserName(boolean valid) { this.validUserName = validUserName; }

    public void setValidEmail(boolean valid) { this.validEmail = validEmail; }

    public String getUserName() {
        return userName;
    }

    public String getPassword() { return password; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() { return email; }

    public ArrayList<String> getProfile() { return profile; }

    public boolean isValid() {
        return valid;
    }

    public boolean isValidUserName() { return validUserName; }

    public boolean isValidEmail() { return validEmail; }
}