package myti;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import datamodel.MyTiData;
import datamodel.User;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable{

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ImageView cardImageView;
    private Image cardImage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardImage = new Image("Resources/card.png");
        cardImageView.setImage(cardImage);
    }

    /**
     * Load Travel Pass Window
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleButton1Action(ActionEvent event) throws IOException{
        Parent travelPassWindow = FXMLLoader.load(getClass().getResource("TravelPassWindow.fxml"));
        Scene purchaseScene = new Scene(travelPassWindow,1000,800);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(purchaseScene);
        app_stage.show();
    }

    /**
     * Load Recharges Window
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleButton2Action(ActionEvent event) throws IOException{
        Parent rechargeWindow = FXMLLoader.load(getClass().getResource("RechargeWindow.fxml"));
        Scene rechargeScene = new Scene(rechargeWindow,1000,800);
        Stage recharge_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        recharge_stage.setScene(rechargeScene);
        recharge_stage.show();
    }

    /**
     * Load Take journey window
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleButton3Action(ActionEvent event) throws IOException{
        Parent TakeJourneyWindow = FXMLLoader.load(getClass().getResource("TakeJourneyWindow.fxml"));
        Scene takeJourneyScene = new Scene(TakeJourneyWindow,1000,800);
        Stage journey_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        journey_stage.setScene(takeJourneyScene);
        journey_stage.show();
    }

    /**
     * Load journey information dialog
     * @throws IOException
     */
    @FXML
    public void handleButton4Action() throws IOException {
        Dialog<ButtonType> journeyDialog = new Dialog<>();
        journeyDialog.initOwner(mainBorderPane.getScene().getWindow());
        journeyDialog.setTitle("View all journeys");
        journeyDialog.setHeaderText("All journeys made using MyTi cards");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("viewAllJourneysDialog.fxml"));
        journeyDialog.getDialogPane().setContent(fxmlLoader.load());

        journeyDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        journeyDialog.showAndWait();
    }

    /**
     * Load Travel Statistics Dialog
     */
    @FXML
    public void handleButton5Action(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Travel Statistics");
        dialog.setHeaderText("Station travel statistics");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("travelStatisticsDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }

    /**
     * Load Add New User Dialog
     * @throws IOException
     */
    @FXML
    public void handleButton6Action() throws IOException{
        Dialog<ButtonType> addNewUserDialog = new Dialog<>();
        addNewUserDialog.initOwner(mainBorderPane.getScene().getWindow());
        addNewUserDialog.setTitle("Add New User");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addNewUserScreen.fxml"));
        addNewUserDialog.getDialogPane().setContent(fxmlLoader.load());

        addNewUserDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        addNewUserDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = addNewUserDialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            AddNewUserScreenController controller = fxmlLoader.getController();
            User user = controller.processInput();
            MyTiData.getInstance().addUser(user);
            MyTiData.getInstance().addUserToCard(user);
        }
    }

    /**
     * Exit the Application
     * @throws IOException
     */
    @FXML
    public void handleExitButton() throws IOException {
        Platform.exit();
    }
}
