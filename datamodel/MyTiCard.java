package datamodel;

import java.util.ArrayList;

/**
 * MyTi Card class
 * MyTi Card needed to purchase travel passes and take journeys
 */


public class MyTiCard {

    private final static double CREDIT_LIMIT = 100.0;  // maximum allowed credit
    private String cardID;
    private double credit;
    private String userID;
    private ArrayList<TravelPass> travelPassArrayList = new ArrayList<>();

    /**
     * @param cardID Unique MyTi Card ID
     * @param userID Unique User ID - If no user then userID = null
     * @param credit Balance on card
     */
    public MyTiCard(String cardID,String userID,double credit) {
        this.cardID = cardID;
        this.userID = userID;
        this.credit = credit;
    }

    /**
     * Override to string to populate JavaFX choice box
     * @return
     */
    @Override
    public String toString() {
        return cardID;
    }

    public double getCredit() {
        return credit;
    }

    public String getUserID() {return userID;}

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getCardID() {
        return cardID;
    }

    public void setUserID(String userID) {this.userID = userID;}

    public ArrayList<TravelPass> getTravelPassArrayList() {
        return travelPassArrayList;
    }

    /**
     * Checks if the new balance after topup is under the Max credit limit allowed
     * @param currentBalance
     * @param topUp
     * @return
     */
    public boolean checkBalanceLimit(double currentBalance, double topUp){
        if (currentBalance + topUp <= CREDIT_LIMIT){
            return true;
        }
        return false;
    }

    /**
     * Add new Travel Pass to the travel pass array list
     * @param travelPass
     */
    public void addNewTravelPass(TravelPass travelPass){
        travelPassArrayList.add(travelPass);
    }
    public boolean isAllDayPass(){
        boolean allDayPass = true;
        for (TravelPass travelPass : travelPassArrayList){
            allDayPass = travelPass.isAllDayPass();
        }
        return allDayPass;
    }

    /**
     * Validate Journey for a 2 hour travel pass
     * Increment Station Counters
     * Add Journey to travel pass if a Valid Pass
     * @param day
     * @param timeOfJourney
     * @param startStation
     * @param endStation
     * @return True if Valid
     */
    public boolean validateJourneyTwoHourPass(String day, int timeOfJourney, String startStation, String endStation){
        String travelDay;
        int travelPassPurchaseTime;
        boolean validPass = false;
        for (TravelPass travelPass: travelPassArrayList){
            travelDay = travelPass.getDayOfTravel();
            travelPassPurchaseTime = travelPass.getTimeOfPurchase();
            if ((travelDay.equalsIgnoreCase(day)) && ((travelPassPurchaseTime + TravelPass.getTwoHours()) >= timeOfJourney &&
                            (timeOfJourney >= travelPassPurchaseTime))){
                if (travelPass.isZoneOneAndTwo()){
                    validPass = true;
                    MyTiData.getInstance().incrementJourneyCounter(startStation,endStation);
                    travelPass.addJourney(startStation,endStation,day,timeOfJourney);
                    break;
                }else if (!travelPass.isZoneOneAndTwo()){
                    if (!MyTiData.getInstance().journeyZonesOneAndTwo(startStation,endStation)){
                        MyTiData.getInstance().incrementJourneyCounter(startStation,endStation);
                        travelPass.addJourney(startStation,endStation,day,timeOfJourney);
                        validPass = true;
                        break;
                    }
                }
            }
        }
        return validPass;
    }

    /**
     * Validate Journey for All Day Pass
     * Increment Station Counters
     * Add Journey to travel pass if a Valid Pass
     * @param day
     * @param startStation
     * @param endStation
     * @param timeOfJourney
     * @return True if Valid
     */
    public boolean validateJourneyAllDayPass(String day, String startStation, String endStation,int timeOfJourney){
        String travelDay;
        boolean validPass = false;
        for (TravelPass travelPass: travelPassArrayList){
            travelDay = travelPass.getDayOfTravel();
            if ((travelDay.equalsIgnoreCase(day))){
                if (travelPass.isZoneOneAndTwo()){
                    validPass = true;
                    MyTiData.getInstance().incrementJourneyCounter(startStation,endStation);
                    travelPass.addJourney(startStation,endStation,day,timeOfJourney);
                    break;
                }else if (!travelPass.isZoneOneAndTwo()){
                    if (!MyTiData.getInstance().journeyZonesOneAndTwo(startStation,endStation)){
                        validPass = true;
                        MyTiData.getInstance().incrementJourneyCounter(startStation,endStation);
                        travelPass.addJourney(startStation,endStation,day,timeOfJourney);
                        break;
                    }
                }
            }
        }
        return validPass;
    }

    /**
     * Returns string for print function for MyTi Card Details
     * @param myTiCard
     * @return
     */
    public String printCardInformation(MyTiCard myTiCard){
        if (myTiCard.getUserID().equals(""))
            return ("\nMyTi Card: " + myTiCard.getCardID() + "\n");
        else {
            String userID = myTiCard.getUserID();
            String username = null;
            for (User user : MyTiData.getInstance().getUserList()){
                if (user.getUserID().equals(userID)){
                    username = user.getName();
                    break;
                }
            }
            return ("\nMyTi Card: " + myTiCard.getCardID() + "\n" +
                    "Owner: " + username +"\n");
        }
    }
}
