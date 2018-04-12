package myti;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import datamodel.Journey;
import datamodel.MyTiCard;
import datamodel.MyTiData;
import datamodel.TravelPass;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by AK on 16/10/16.
 */
public class ViewAllJourneysDialogController implements Initializable {

    private int counter = 1;
    private char bulletPoint = 'a';
    private String viewAllJourneys = "";
    @FXML
    private TextArea journeyTextArea;

    /**
     * Update text areas to view card, travel pass and journey details
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<MyTiCard> myTiCardList = MyTiData.getInstance().getMyTiCardList();
        for (MyTiCard myTiCard: myTiCardList){
            viewAllJourneys += myTiCard.printCardInformation(myTiCard);
            counter = 1; // Reset Counter For Next MyTi Card
            if (!myTiCard.getTravelPassArrayList().isEmpty()){
                List<TravelPass> travelPassList = myTiCard.getTravelPassArrayList();
                for (TravelPass travelPass: travelPassList){
                    viewAllJourneys += travelPass.travelPassPrintInformation(counter);
                    counter++;
                    bulletPoint = 'a'; //Reset Bullet Points for Next Travel pass
                    if (!travelPass.getJourneyList().isEmpty()){
                        List<Journey> journeyList = travelPass.getJourneyList();
                        for (Journey journey: journeyList){
                            viewAllJourneys += journey.printJourney(bulletPoint);
                            bulletPoint++;
                        }
                    }
                }
            }
        }
        journeyTextArea.setText(viewAllJourneys);
    }
}
