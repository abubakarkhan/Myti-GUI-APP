package datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AK on 7/09/2016.
 */
public class TravelPass {

    private String dayOfTravel;
    private int timeOfPurchase = -1;
    private final static int TWO_HOURS = 200;
    private String cardID;
    private boolean allDayPass;
    private boolean zoneOneAndTwo;
    private List<Journey> journeyList = new ArrayList<>();

    public TravelPass(String dayOfTravel, int timeOfPurchase, String cardID, boolean allDayPass,boolean zoneOneAndTwo) {
        this.dayOfTravel = dayOfTravel;
        this.timeOfPurchase = timeOfPurchase;
        this.cardID = cardID;
        this.allDayPass = allDayPass;
        this.zoneOneAndTwo = zoneOneAndTwo;
    }

    /**
     * Add journey take using the travel pass
     * @param startStation
     * @param endStation
     * @param journeyDay
     * @param journeyTime
     */
    public void addJourney(String startStation,String endStation,String journeyDay,int journeyTime){
        journeyList.add(new Journey(startStation,endStation,journeyDay,journeyTime));
    }

    /**
     * Travel Pass Print function
     * @param pointCounter
     * @return String from the print function to the GUI
     */
    public String travelPassPrintInformation(int pointCounter){
        if (allDayPass && zoneOneAndTwo)
            return (pointCounter +". All day Zone 1-2 Travel Pass purchased on " + dayOfTravel + " at " + timeOfPurchase +"\n");
        else if(allDayPass && (!zoneOneAndTwo))
            return (pointCounter +". All day Zone 1 Travel Pass purchased on " + dayOfTravel + " at " + timeOfPurchase +"\n");
        else if((!allDayPass) && zoneOneAndTwo)
            return (pointCounter +". 2 Hour Zone 1-2 Travel Pass purchased on " + dayOfTravel + " at " + timeOfPurchase +"\n");
        else
            return (pointCounter +". 2 Hour Zone 1 Travel Pass purchased on " + dayOfTravel + " at " + timeOfPurchase +"\n");
    }

    public List<Journey> getJourneyList() {return journeyList;}

    public String getDayOfTravel() {return dayOfTravel;}

    public int getTimeOfPurchase() {return timeOfPurchase;}

    public static int getTwoHours() {return TWO_HOURS;}

    public boolean isAllDayPass() {return allDayPass;}

    public boolean isZoneOneAndTwo() {return zoneOneAndTwo;}
}



