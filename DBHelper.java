
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBHelper {

    private static Connection sqlConnection;

    public static void connection() throws IOException, SQLException {
        try {
            String resourceName = "config.properties";
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Properties props = new Properties();
            try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
                props.load(resourceStream);
            }
            String username = props.getProperty("username");
            String password = props.getProperty("password");

            String server = "jdbc:mysql://127.0.0.1:3306/TwitterThangDB";

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            sqlConnection = DriverManager.getConnection(server, username, password);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getKeywords() {

        ArrayList<String> kWords = new ArrayList<String>();
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

    public int login(String userName, String password) {

        int success = 0;




        return success;
    }

    public void newUserInfo(String fname, String lname, String address, String city, String state,
                            String pCode, String country, String phone, String email) {





    }

}
