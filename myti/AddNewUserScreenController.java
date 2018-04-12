package myti;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import datamodel.MyTiCard;
import datamodel.MyTiData;
import datamodel.User;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by AK on 17/10/16.
 * Controll for Adding new user dialog pane
 */
public class AddNewUserScreenController implements Initializable{

    @FXML
    private ChoiceBox<MyTiCard> myTiCardChoiceBox;
    @FXML
    private TextField userIDField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;

    /**
     * Populate the choice box for selection in GUI
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<MyTiCard> cardsList = MyTiData.getInstance().getMyTiCardList();
        ObservableList<MyTiCard> unregisteredCards = FXCollections.observableArrayList();
        for (MyTiCard myTiCard : cardsList){
            if (myTiCard.getUserID().equals("")){
                unregisteredCards.add(myTiCard);
            }
        }
        myTiCardChoiceBox.getItems().setAll(unregisteredCards);
    }

    /**
     * Process Input for creating a new user
     * @return User
     */
    public User processInput(){
        MyTiCard myTiCard = myTiCardChoiceBox.getSelectionModel().getSelectedItem();
        String userID = userIDField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String cardID = myTiCard.getCardID();
        User user = new User(userID,name,email,cardID);
        return user;
    }
}
