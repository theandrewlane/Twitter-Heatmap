package com.cs4230.finalproject.service;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DBHelper {

    private static Connection sqlConnection;

    public static void connection() throws IOException, SQLException {

        try {
            //Use environment variables to define connection parameters
            //These string values can be found on the team slack stream
            String username = System.getenv("DB_USER") ;
            String password = System.getenv("DB_PASS");
            String server = System.getenv("DB_CONNECTION_STRING");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            sqlConnection = DriverManager.getConnection(server, username, password);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getKeywords() {

        ArrayList<String> kWords = new ArrayList<>();
        Statement statement;

        try {
            String query = "Select KeywordName from Keyword";
            statement = sqlConnection.prepareStatement(query);
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                kWords.add(rs.getString("KeywordName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kWords;
    }

    public String login(String userName, String password) {

        String success = "0";
        PreparedStatement statement;

        try {
            String query =  "SELECT UserID FROM UserInfo WHERE UserName = ? AND Pword = ?";

            statement = sqlConnection.prepareStatement(query);
            statement.setString(1, userName);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                success = rs.getString("UserID");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return success;
    }

    public ArrayList<String> displayProfile(String userId){

        ArrayList<String> profile = new ArrayList<>();
        PreparedStatement statement;

        try {
            String query =  "SELECT u.UserName, p.Firstname, p.LastName, p.Email " +
                            "FROM Person AS p " +
                            "JOIN  UserInfo AS u on p.PersonID = u.PersonID " +
                            "WHERE u.userID = ?";

            statement = sqlConnection.prepareStatement(query);
            statement.setString(1, userId);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                profile.add(rs.getString("UserName"));
                profile.add(rs.getString("FirstName"));
                profile.add(rs.getString("LastName"));
                profile.add(rs.getString("Email"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return profile;
    }

    public void newUserInfo(String fname, String lname, String address, String city, String state,
                            String pCode, String country, String phone, String email,
                            String uName, String pWord) {

        PreparedStatement insertPerson;
        PreparedStatement getPersonId;
        PreparedStatement insertUser;

        String personId = "";

        try {
            String personQuery = "INSERT INTO Person (FirstName, LastName, Address, City, State, PostalCode, Country, Phone, Email) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            insertPerson = sqlConnection.prepareStatement(personQuery);
            insertPerson.setString(1, fname);
            insertPerson.setString(2, lname);
            insertPerson.setString(3, address);
            insertPerson.setString(4, city);
            insertPerson.setString(5, state);
            insertPerson.setString(6, pCode);
            insertPerson.setString(7, country);
            insertPerson.setString(8, phone);
            insertPerson.setString(9, email);

            insertPerson.execute();

            String getIdQuery = "SELECT PersonID FROM Person WHERE FirstName = ?";
            getPersonId = sqlConnection.prepareStatement(getIdQuery);
            getPersonId.setString(1, fname);

            ResultSet personIDrs = getPersonId.executeQuery();
            while (personIDrs.next()){
                personId = personIDrs.getString("PersonID");
            }

            String userQuery =  "INSERT INTO UserInfo (PersonID, UserName, Pword) " +
                                "VALUES (?, ?, ?)";
            insertUser = sqlConnection.prepareStatement(userQuery);
            insertUser.setString(1, personId);
            insertUser.setString(2, uName);
            insertUser.setString(3, pWord);

            insertUser.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String checkUserName(String uName){

        String validate = "1";
        PreparedStatement statement;

        try {
            String query =  "SELECT EXISTS(SELECT UserName FROM UserInfo WHERE UserName = ?) AS Result";

            statement = sqlConnection.prepareStatement(query);
            statement.setString(1, uName);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                validate = rs.getString("Result");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return validate;
    }

    public String checkEmail(String email){

        String validate = "1";
        PreparedStatement statement;

        try {
            String query =  "SELECT EXISTS(SELECT Email FROM Person WHERE Email = ?) AS Result";

            statement = sqlConnection.prepareStatement(query);
            statement.setString(1, email);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                validate = rs.getString("Result");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return validate;
    }
}