package datamodel;

/**
 * Created by AK on 15/10/16.
 * Records the Journey Information take using the Travel Pass
 */
public class Journey {

    private String journeyStartStation;
    private String journeyEndStation;
    private String journeyDay;
    private int journeyTime;

    public Journey(String journeyStartStation, String journeyEndStation, String journeyDay, int journeyTime) {
        this.journeyStartStation = journeyStartStation;
        this.journeyEndStation = journeyEndStation;
        this.journeyDay = journeyDay;
        this.journeyTime = journeyTime;
    }

    public String getJourneyStartStation() {
        return journeyStartStation;
    }

    public String getJourneyEndStation() {
        return journeyEndStation;
    }

    public String getJourneyDay() {
        return journeyDay;
    }

    public int getJourneyTime() {
        return journeyTime;
    }

    /**
     * Print function to return string journey details
     * @param bulletPoint Bullet Point for print purposes
     * @return Returns string value to printed when called
     */
    public String printJourney(char bulletPoint){
        return (bulletPoint + ". "+journeyStartStation + " to " + journeyEndStation+ " at " + journeyTime + "\n");
    }
}
