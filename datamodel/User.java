package datamodel;

/**
 * Created by AK on 8/09/2016.
 * User clase for registered MyTi cards
 */
public class User {

    private String userID;
    private String name;
    private String emailAddress;
    private String cardID;


    public User(String userID,String name,String emailAddress,String cardID) {
        this.userID = userID;
        this.name = name;
        this.emailAddress = emailAddress;
        this.cardID = cardID;
    }

    public String getName() {
        return name;
    }


    public String getUserID() {return userID;}

    public String getCardID() {return cardID;}

    public String getEmailAddress() {return emailAddress;}
}

