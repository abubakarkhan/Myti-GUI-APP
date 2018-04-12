package myti;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import datamodel.MyTiCard;
import datamodel.MyTiData;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by AK on 28/9/16.
 */
public class RechargeWindowMyController extends MyController implements Initializable{

    @FXML
    private Label rechargeWindowLabel;
    @FXML
    private ChoiceBox<MyTiCard> myTiCardChoiceBox;
    @FXML
    private ToggleButton fiveDollars;
    @FXML
    private ToggleButton tenDollars;
    @FXML
    private ToggleButton fifteenDollars;
    @FXML
    private ToggleButton twentyDollars;
    @FXML
    private ToggleButton thirtyDollars;
    @FXML
    private ToggleButton fortyDollars;
    @FXML
    private ToggleButton fiftyDollars;

    /**
     * Populate the choice box
     * Listen for selection changes and update Labels
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myTiCardChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MyTiCard>() {
            @Override
            public void changed(ObservableValue<? extends MyTiCard> observable, MyTiCard oldValue, MyTiCard newValue) {
                if (newValue != null){
                    MyTiCard myTiCard = myTiCardChoiceBox.getSelectionModel().getSelectedItem();
                    rechargeWindowLabel.setText("Your Current Balance is : $" + String.format("%.2f",myTiCard.getCredit()));
                }
            }
        });
        myTiCardChoiceBox.getItems().setAll(MyTiData.getInstance().getMyTiCardList());
    }

    /**
     * Label Update Message
     * @param myTiCard
     */
    public void currentBalanceLabelUpdate(MyTiCard myTiCard){
        rechargeWindowLabel.setText("Your Current Balance is : $" + String.format("%.2f",myTiCard.getCredit()));
    }

    /**
     * Maximum Limit Reach Label Message
     * @param myTiCard
     */
    public void maximumAmountLabelUpdate(MyTiCard myTiCard){
        rechargeWindowLabel.setText("Recharge Failed: Maximum Credit limit allowed is $100" +
                "\nYour Current Balance is : $" + String.format("%.2f",myTiCard.getCredit()));
    }

    /**
     * Recharge Function to top up MyTi Cards
     * Check if maximum limit reached
     * @param myTiCard
     * @param currentCredit
     * @param topUpAmount
     */
    public void recharge(MyTiCard myTiCard,double currentCredit, double topUpAmount){
        if (myTiCard.checkBalanceLimit(currentCredit,topUpAmount)){
            myTiCard.setCredit(currentCredit + topUpAmount);
            currentBalanceLabelUpdate(myTiCard);
        }else {
            maximumAmountLabelUpdate(myTiCard);
        }
    }

    /**
     * Handle The top up Button
     * Alert if not card selected
     * I know there are some magic numbers here but they are part of the custom GUI toggle buttons
     * Did not make sense to make variables just to use them once (Wanted to reduce clutter)
     */
    @FXML
    public void handleTopUpButton(){
        if (myTiCardChoiceBox.getSelectionModel().getSelectedItem() == null){
            rechargeWindowLabel.setText("Select a card to Top Up Balance.");
            return;
        }
        MyTiCard myTiCard = myTiCardChoiceBox.getSelectionModel().getSelectedItem();
        double currentCredit = myTiCard.getCredit();

        if (fiveDollars.isSelected()){
            recharge(myTiCard,currentCredit,5);
        }else if (tenDollars.isSelected()){
            recharge(myTiCard,currentCredit,10);
        }else if (fifteenDollars.isSelected()){
            recharge(myTiCard,currentCredit,15);
        }else if (twentyDollars.isSelected()){
            recharge(myTiCard,currentCredit,20);
        }else if (thirtyDollars.isSelected()){
            recharge(myTiCard,currentCredit,30);
        }else if (fortyDollars.isSelected()){
            recharge(myTiCard,currentCredit,40);
        }else if (fiftyDollars.isSelected()){
            recharge(myTiCard,currentCredit,50);
        }else {
            rechargeWindowLabel.setText("Please select an amount to top up");
        }
    }
}
