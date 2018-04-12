package datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by AK on 7/10/16.
 * Singleton Class to store the data model for the project
 */
public class MyTiData {

    private static MyTiData instance = new MyTiData();
    private ObservableList<MyTiCard> myTiCardList;
    private List<Station> stationArrayList = new ArrayList<>(); // Collection of Stations
    private List<User> userList = new ArrayList<>(); // Store user information
    private double ZONE_1_2HR_FARE; // Zone 1, 2 Hour fare
    private double ZONE_1_AND_2_2HR_FARE; // Zone 1 & 2, 2 Hour fare
    private double ZONE_1_All_DAY_FARE; // Zone 1, All day fare
    private double ZONE_1_AND_2_All_DAY_FARE; // Zone 1 & 2, All day Fare

    /**
     * Load the MyTi Card information from text file
     * @throws IOException
     */
    public void loadMyTiCards() throws IOException {
        String cardsFileName = "/Resources/cardData.txt";
        myTiCardList = FXCollections.observableArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(cardsFileName)));
        String input;
        try {
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split(":");

                String cardID = itemPieces[0];
                String userID = itemPieces[1];
                String balance = itemPieces[2];
                MyTiCard myTiCard = new MyTiCard(cardID,userID,Double.parseDouble(balance));
                myTiCardList.add(myTiCard);
            }
        } finally {
            if(br != null) {
                br.close();
            }
        }
    }

    /**
     * Save the MyTi Card information to text file
     * @throws IOException
     */
    public void storeMyTiCardData() throws IOException{
        String cardsFileName = "src/Resources/cardData.txt";
        FileWriter cardsFile = new FileWriter(cardsFileName);
        BufferedWriter bw = new BufferedWriter(cardsFile);
        try {
            Iterator<MyTiCard> iterator = myTiCardList.iterator();
            while(iterator.hasNext()) {
                MyTiCard myTiCard = iterator.next();
                bw.write(String.format("%s:%s:%s",myTiCard.getCardID(),myTiCard.getUserID(),myTiCard.getCredit()));
                bw.newLine();
            }

        } finally {
            if(bw != null) {
                bw.close();
            }
        }
    }

    /**
     * Load station data from text file
     * @throws IOException
     */
    public void loadStationData() throws IOException{
        String stationsFileName = "/Resources/stationData.txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(stationsFileName)));
        String input;
        try {
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split(",");

                String stationName = itemPieces[0];
                String stationZone = itemPieces[1];
                Station station = new Station(stationName,Integer.parseInt(stationZone));
                stationArrayList.add(station);
            }
        } finally {
            if(br != null) {
                br.close();
            }
        }
    }

    /**
     * Load user data from text file
     * @throws IOException
     */
    public void loadUserData() throws IOException{
        String usersFileName = "/Resources/userData.txt";
        BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(usersFileName)));
        String input;
        try {
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split(",");
                String userID = itemPieces[0];
                String userName = itemPieces[1];
                String userEmail = itemPieces[2];
                String cardID = itemPieces[3];
                User user = new User(userID,userName,userEmail,cardID);
                userList.add(user);
            }
        } finally {
            if(br != null) {
                br.close();
            }
        }
    }

    /**
     * Save User Data to Text file
     * @throws IOException
     */
    public void storeUserData() throws IOException{
        String usersFileName = "src/Resources/userData.txt";
        FileWriter usersFile = new FileWriter(usersFileName);
        BufferedWriter bw = new BufferedWriter(usersFile);
        try {
            Iterator<User> iterator = userList.iterator();
            while(iterator.hasNext()) {
                User user = iterator.next();
                bw.write(String.format("%s,%s,%s,%s",user.getUserID(),user.getName(),user.getEmailAddress(),user.getCardID()));
                bw.newLine();
            }

        } finally {
            if(bw != null) {
                bw.close();
            }
        }
    }

    /**
     * Load Price Information from text file
     * @throws IOException
     */
    public void loadPriceData() throws IOException{
        String pricesFile = ("/Resources/priceInformation.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(pricesFile)));
        String input;
        try {
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split(",");

                String zone1_2Hr = itemPieces[0];
                String zone_1_And_2_2Hr = itemPieces[1];
                String zone_1_AllDay = itemPieces[2];
                String zone_1_And_2_AllDay = itemPieces[3];

                ZONE_1_2HR_FARE = Double.parseDouble(zone1_2Hr);
                ZONE_1_AND_2_2HR_FARE = Double.parseDouble(zone_1_And_2_2Hr);
                ZONE_1_All_DAY_FARE = Double.parseDouble(zone_1_AllDay);
                ZONE_1_AND_2_All_DAY_FARE =Double.parseDouble(zone_1_And_2_AllDay);
            }
        } finally {
            if(br != null) {
                br.close();
            }
        }
    }

    /**
     * Check if the journey is for both zone 1 and 2.
     * @param startStation
     * @param endStation
     * @return True is journey covers both zones
     */
    public boolean journeyZonesOneAndTwo(String startStation, String endStation){
        int zone = 0;
        boolean zoneOneAndTwo;
        for (Station station : stationArrayList) {
            if (station.getName().equalsIgnoreCase(startStation)) {
                zone = station.getZone();
                break;
            }
        }
        for (Station station : stationArrayList) {
            if (station.getName().equalsIgnoreCase(endStation)) {
                zone += station.getZone();
                break;
            }
        }
        if (zone >= Station.getZoneOneAndTwo())
            zoneOneAndTwo = true;
        else
            zoneOneAndTwo = false;

        return zoneOneAndTwo;
    }

    /**
     * Increment station counters for successful journeys
     * @param startStation
     * @param endStation
     */
    public void incrementJourneyCounter(String startStation,String endStation){
        for (Station station : stationArrayList){
            if (station.getName().equals(startStation)){
                int currentStartCounter = station.getJourneyStartCounter();
                currentStartCounter += 1;
                station.setJourneyStartCounter(currentStartCounter);
            }
            if (station.getName().equals(endStation)){
                int currentEndCounter = station.getJourneyEndCounter();
                currentEndCounter += 1;
                station.setJourneyEndCounter(currentEndCounter);
            }
        }
    }

    /**
     * Check the zone for journey start station
     * @param startStation
     * @return Zone
     */
    public int startStationZone(String startStation){
        for (Station station : stationArrayList){
            if (station.getName().equals(startStation)){
                return station.getZone();
            }
        }
        return -1;
    }

    /**
     * Check the zone for journey end station
     * @param endStation
     * @return Zone
     */
    public int endStationZone(String endStation){
        for (Station station : stationArrayList){
            if (station.getName().equals(endStation)){
                return station.getZone();
            }
        }
        return -1;
    }

    /**
     * Add user to the user list
     * @param user
     */
    public void addUser(User user) {userList.add(user);}

    /**
     * Attach a user to MyTi Card
     * @param user
     */
    public void addUserToCard(User user){
        MyTiCard myTiCard = findMyTiCardWithID(user.getCardID());
        myTiCard.setUserID(user.getUserID());
    }

    /**
     * Find a MyTi Card using card ID
     * @param cardID
     * @return
     */
    public MyTiCard findMyTiCardWithID(String cardID){
        for (MyTiCard myTiCard: myTiCardList){
            if (myTiCard.getCardID().equalsIgnoreCase(cardID)){
                return myTiCard;
            }
        }return null;
    }

    /**
     * @return Returns the singleton instance of the class
     */
    public static MyTiData getInstance() {
        return instance;
    }

    /**
     * @return Returns MyTi Card list
     */
    public ObservableList<MyTiCard> getMyTiCardList() {
        return myTiCardList;
    }

    /**
     * @return Returns Station list
     */
    public List<Station> getStationArrayList() {return stationArrayList;}

    /**
     * @return Returns User list
     */
    public List<User> getUserList() {return userList;}

    public double getZONE_1_2HR_FARE() {return ZONE_1_2HR_FARE;}

    public double getZONE_1_AND_2_2HR_FARE() {return ZONE_1_AND_2_2HR_FARE;}

    public double getZONE_1_All_DAY_FARE() {return ZONE_1_All_DAY_FARE;}

    public double getZONE_1_AND_2_All_DAY_FARE() {return ZONE_1_AND_2_All_DAY_FARE;}

    /**
     * Private Constructor for the singleton class
     */
    private MyTiData(){
    }
}
