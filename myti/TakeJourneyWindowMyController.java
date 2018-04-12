package myti;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import datamodel.MyTiCard;
import datamodel.MyTiData;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by AK on 29/9/16.
 */
public class TakeJourneyWindowMyController extends MyController implements Initializable{

    @FXML
    private ChoiceBox<MyTiCard> myTiCardChoiceBox;
    @FXML
    private ToggleGroup journeyStartToggleGroup;
    @FXML
    private ToggleGroup journeyEndToggleGroup;
    @FXML
    private ToggleGroup journeyDayToggleGroup;
    @FXML
    private Spinner hourSelection;
    @FXML
    private Spinner minutesSelection;

    /**
     * Populate Choice Box
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        myTiCardChoiceBox.getItems().setAll(MyTiData.getInstance().getMyTiCardList());
    }

    /**
     * Handles take journey button and displays appropriate alerts
     */
    public void handleTakeJourneyButton(){
        if (myTiCardChoiceBox.getSelectionModel().getSelectedItem() == null){
            noCardSelectedAlert();
            return;
        }
        if (validateJourneyInformation()){
            successfulJourneyAlert();
        }else {
            unsuccessfulJourneyAlert();
        }

    }

    /**
     * Journey Unsuccessful Alert
     */
    public void unsuccessfulJourneyAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Journey Unsuccessful");
        alert.setHeaderText("You do not have a travel pass covering selected travel options");
        alert.setContentText("Please purchase a new travel pass covering the selected travel options.");
        alert.showAndWait();
    }

    /**
     * Journey Successful Alert
     */
    public void successfulJourneyAlert(){
        int startZone;
        int endZone;
        int hour = (int)hourSelection.getValue();
        int minutes = (int)minutesSelection.getValue();
        String selectedStartStation = ((RadioButton)journeyStartToggleGroup.getSelectedToggle()).getText();
        String selectedEndStation = ((RadioButton)journeyEndToggleGroup.getSelectedToggle()).getText();
        String selectedJourneyDay = ((RadioButton)journeyDayToggleGroup.getSelectedToggle()).getText();

        startZone = MyTiData.getInstance().startStationZone(selectedStartStation);
        endZone = MyTiData.getInstance().endStationZone(selectedEndStation);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Journey Successful");
        alert.setHeaderText("You are travelling from " + selectedStartStation + " to " + selectedEndStation);
        alert.setContentText("Your journey is for Zone "+ startZone + " + " + endZone + "\n"+
                "Travel Day: " + selectedJourneyDay+ "\n" + "Travel Time: " + hour+":"+ minutes + "\nEnjoy your travel!");
        alert.showAndWait();
    }

    /**
     * Gather input for journey time
     * @return
     */
    public int journeyTime(){
        int hour = (int)hourSelection.getValue();
        int minutes = (int)minutesSelection.getValue();
        int time = (hour * getHourConverter()) + minutes;
        return time;
    }

    /**
     * Validate Journey Input received
     * @return True If Valid Journey
     */
    public boolean validateJourneyInformation(){
        String selectedStartStation = ((RadioButton)journeyStartToggleGroup.getSelectedToggle()).getText();
        String selectedEndStation = ((RadioButton)journeyEndToggleGroup.getSelectedToggle()).getText();
        String selectedJourneyDay = ((RadioButton)journeyDayToggleGroup.getSelectedToggle()).getText();
        int time = journeyTime();

        if(checkValidTravelPass(selectedStartStation,selectedEndStation,selectedJourneyDay,time)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * Check for valid travel pass for the chosen journey
     * @param startStation
     * @param endStation
     * @param journeyDay
     * @param time
     * @return True if valid pass
     */
    public boolean checkValidTravelPass(String startStation,String endStation, String journeyDay,int time){
        MyTiCard myTiCard = myTiCardChoiceBox.getSelectionModel().getSelectedItem();
        if (myTiCard.getTravelPassArrayList().isEmpty()){
            return false;
        }else if (myTiCard.isAllDayPass()){
            return myTiCard.validateJourneyAllDayPass(journeyDay,startStation,endStation,time);
        }else if (!myTiCard.isAllDayPass()){
            return myTiCard.validateJourneyTwoHourPass(journeyDay,time,startStation,endStation);
        }
        return false;
    }
}
