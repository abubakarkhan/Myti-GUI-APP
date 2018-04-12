package datamodel;

/**
 * Created by AK on 7/09/2016.
 */
public class Station {

    private String stationName;
    private int zone;
    private int journeyStartCounter = 0;
    private int journeyEndCounter = 0;
    private final static int ZONE_ONE_AND_TWO = 3;

    public Station(String name, int zone) {
        this.stationName = name;
        this.zone = zone;
    }

    public String getName() {return stationName;}

    public int getZone() {
        return zone;
    }

    public static int getZoneOneAndTwo() {return ZONE_ONE_AND_TWO;}

    public void setJourneyStartCounter(int journeyStartCounter) {this.journeyStartCounter = journeyStartCounter;}

    public void setJourneyEndCounter(int journeyEndCounter) {this.journeyEndCounter = journeyEndCounter;}

    public int getJourneyStartCounter() {return journeyStartCounter;}

    public int getJourneyEndCounter() {return journeyEndCounter;}

    /**
     * Prints the statistics of stations and the number of journeys started and ended there
     */

    public String print(){
        return ( stationName + ": "+ journeyStartCounter
                +" journeys started here, "+ journeyEndCounter +" journeys ended here\n");
    }
}
