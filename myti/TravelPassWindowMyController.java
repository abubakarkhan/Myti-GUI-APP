package myti;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import datamodel.MyTiCard;
import datamodel.MyTiData;
import datamodel.TravelPass;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by AK on 28/9/16.
 */
public class TravelPassWindowMyController extends MyController implements Initializable{

    @FXML
    private RadioButton twoHourPassRadioButton;
    @FXML
    private RadioButton allDayPassRadioButton;
    @FXML
    private RadioButton zoneOneRadioButton;
    @FXML
    private RadioButton zoneOneAndTwoRadioButton;
    @FXML
    private ChoiceBox<MyTiCard> myTiCardChoiceBox;
    @FXML
    private Label currentBalanceLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label purchaseSuccessLabel;
    @FXML
    private ToggleGroup passTypeToggleGroup;
    @FXML
    private ToggleGroup zoneToggleGroup;
    @FXML
    private ToggleGroup dayToddleGroup;
    @FXML
    private Spinner hourSpinner;
    @FXML
    private Spinner minutesSpinner;

    /**
     * Populate Choice Box
     * Listen for selection changes and update labels
     * Listen for toggle changes and update labels
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        statusLabel.setText("Current Selection Price: $3.50");
        myTiCardChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null){
                        MyTiCard myTiCard = myTiCardChoiceBox.getSelectionModel().getSelectedItem();
                        currentBalanceLabel.setText("Your Current Balance is : $" +
                                String.format("%.2f",myTiCard.getCredit()));
                        purchaseSuccessLabel.setText("");
                    }
                });
        myTiCardChoiceBox.getItems().setAll(MyTiData.getInstance().getMyTiCardList());

        passTypeToggleGroup.getSelectedToggle().selectedProperty().addListener((observable,oldValue, newValue) -> {
            statusLabelUpdate();
            purchaseSuccessLabel.setText("");
        });

        zoneToggleGroup.getSelectedToggle().selectedProperty().addListener((observable,oldValue, newValue) -> {
            statusLabelUpdate();
            purchaseSuccessLabel.setText("");
        });

        dayToddleGroup.getSelectedToggle().selectedProperty().addListener((observable,oldValue, newValue) ->
                purchaseSuccessLabel.setText(""));

        hourSpinner.setOnMouseClicked(event -> purchaseSuccessLabel.setText(""));
        minutesSpinner.setOnMouseClicked(event -> purchaseSuccessLabel.setText(""));
    }

    /**
     * Handle Purchase Travel Pass Button
     * Get user selection and charge the account
     */
    public void handlePurchaseTravelPassButton(){
        if (myTiCardChoiceBox.getSelectionModel().getSelectedItem() == null){
            noCardSelectedAlert();
            return;
        }
        String dayOfPassPurchase = getDaySelection();
        int timeOfPassPurchase = getTimeSelection();
        MyTiCard myTiCard = myTiCardChoiceBox.getSelectionModel().getSelectedItem();

        if (zoneOneRadioButton.isSelected() && twoHourPassRadioButton.isSelected()){
            checkBalanceAndPurchase(myTiCard,MyTiData.getInstance().getZONE_1_2HR_FARE(),dayOfPassPurchase,timeOfPassPurchase);
        }else if (zoneOneRadioButton.isSelected() && allDayPassRadioButton.isSelected()){
            checkBalanceAndPurchase(myTiCard,MyTiData.getInstance().getZONE_1_All_DAY_FARE(),dayOfPassPurchase,timeOfPassPurchase);
        }else if (zoneOneAndTwoRadioButton.isSelected() && twoHourPassRadioButton.isSelected()){
            checkBalanceAndPurchase(myTiCard,MyTiData.getInstance().getZONE_1_AND_2_2HR_FARE(),dayOfPassPurchase,timeOfPassPurchase);
        }else if (zoneOneAndTwoRadioButton.isSelected() && allDayPassRadioButton.isSelected()){
            checkBalanceAndPurchase(myTiCard,MyTiData.getInstance().getZONE_1_AND_2_All_DAY_FARE(),dayOfPassPurchase,timeOfPassPurchase);
        }
    }

    /**
     * If balance is available purchase pass
     * @param myTiCard
     * @param fare
     * @param dayOfPassPurchase
     * @param timeOfPassPurchase
     */
    public void checkBalanceAndPurchase(MyTiCard myTiCard,double fare,String dayOfPassPurchase,int timeOfPassPurchase){
        if (myTiCard.getCredit() >= fare){
            double credit = myTiCard.getCredit();
            credit -= fare;
            myTiCard.setCredit(credit);
            TravelPass travelPass = new TravelPass(dayOfPassPurchase,timeOfPassPurchase,myTiCard.getCardID(),
                    allDayPassRadioButton.isSelected(),zoneOneAndTwoRadioButton.isSelected());
            myTiCard.addNewTravelPass(travelPass);
            purchaseConfirmationAlert(dayOfPassPurchase);
            currentBalanceLabel.setText("Your Current Balance is : $" + String.format("%.2f",myTiCard.getCredit()));
            purchaseSuccessLabel.setText("Purchase Successful");
        }else {
            insufficientBalanceAlert();
        }
    }

    /**
     * Alert for Purchase Confirmation
     * @param day
     */
    public void purchaseConfirmationAlert(String day) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Purchase Successful");
        alert.setHeaderText("Travel Pass Purchased");

        if (zoneOneRadioButton.isSelected() && twoHourPassRadioButton.isSelected())
            alert.setContentText("Travel Pass Details\n\n" + "Day: " + day + "\nZone: 1" + "\nPass Type: 2 Hour Pass");
        else if (zoneOneRadioButton.isSelected() && allDayPassRadioButton.isSelected())
            alert.setContentText("Pass Details \n" + "Day: " + day + "\nZone: 1" + "\nPass Type: All Day Pass");
        else if (zoneOneAndTwoRadioButton.isSelected() && twoHourPassRadioButton.isSelected())
            alert.setContentText("Pass Details \n" + "Day: " + day + "\nZone: 1 & 2" + "\nPass Type: 2 Hour Pass");
        else if (zoneOneAndTwoRadioButton.isSelected() && allDayPassRadioButton.isSelected())
            alert.setContentText("Pass Details \n" + "Day: " + day + "\nZone: 1 & 2" + "\nPass Type: All Day Pass");

        alert.showAndWait();

    }

    /**
     * Get Day Selection From User
     * @return
     */
    public String getDaySelection(){
        RadioButton selectedDay = (RadioButton) dayToddleGroup.getSelectedToggle();
        return selectedDay.getText();
    }

    /**
     * Get Time selection from user
     * @return
     */
    public int getTimeSelection(){
        int hour, minutes;
        hour = (int) hourSpinner.getValue();
        minutes = (int) minutesSpinner.getValue();

        return ((hour * getHourConverter()) + minutes);
    }

    /**
     * Alert for insufficient balance
     */
    public void insufficientBalanceAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Insufficient Balance");
        alert.setHeaderText("Not enough balance to purchase selected travel pass");
        alert.setContentText("Please top up your card to purchase the travel pass");
        alert.showAndWait();
    }

    /**
     * Update status Label
     */
    public void statusLabelUpdate(){
        if (zoneOneRadioButton.isSelected() && twoHourPassRadioButton.isSelected()){
            statusLabel.setText("Current Selection Price: $" +
                    String.format("%.2f",MyTiData.getInstance().getZONE_1_2HR_FARE()));
        }else if (zoneOneRadioButton.isSelected() && allDayPassRadioButton.isSelected()){
            statusLabel.setText("Current Selection Price: $" +
                    String.format("%.2f",MyTiData.getInstance().getZONE_1_All_DAY_FARE()));
        }else if (zoneOneAndTwoRadioButton.isSelected() && twoHourPassRadioButton.isSelected()){
            statusLabel.setText("Current Selection Price: $" +
                    String.format("%.2f",MyTiData.getInstance().getZONE_1_AND_2_2HR_FARE()));
        }else if (zoneOneAndTwoRadioButton.isSelected() && allDayPassRadioButton.isSelected()){
            statusLabel.setText("Current Selection Price: $" +
                    String.format("%.2f",MyTiData.getInstance().getZONE_1_AND_2_All_DAY_FARE()));
        }
    }
}
