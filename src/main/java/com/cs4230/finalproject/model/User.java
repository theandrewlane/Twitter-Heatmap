package com.cs4230.finalproject.model;

/**
 * Created by andrewlane on 12/14/16.
 */

public class User {

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private boolean valid;

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

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFiRstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isValid() {
        return valid;
    }
}