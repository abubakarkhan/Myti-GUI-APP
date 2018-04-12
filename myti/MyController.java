package myti;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by AK on 12/10/16.
 * Super class to centralize some common controls
 */
public class MyController {

    private static final int HOUR_CONVERTER = 100;

    /**
     * Handle Back button to retun to main screen
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene mainScene = new Scene(mainWindow,1000,800);
        Stage app_main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_main_stage.setScene(mainScene);
        app_main_stage.show();
    }

    /**
     * Alert for no card selection
     */
    public void noCardSelectedAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid Selection");
        alert.setHeaderText("No Card has Been Selected");
        alert.setContentText("Please select a valid card.");
        alert.showAndWait();
    }

    public static int getHourConverter() {return HOUR_CONVERTER;}
}
