package myti;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import datamodel.MyTiData;

import java.io.IOException;

/**
 * @author AK
 * MyTi GUI Application
 */

public class MyTiSystem extends Application {

    /**
     * Loads the main GUI Stage
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("MyTi");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Load the data from the text files before running the GUI
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        try {
            MyTiData.getInstance().loadStationData();
            MyTiData.getInstance().loadMyTiCards();
            MyTiData.getInstance().loadUserData();
            MyTiData.getInstance().loadPriceData();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Saves the data from the program to the text file
     * Once the program is closed the data is saved to file
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        try {
            MyTiData.getInstance().storeMyTiCardData();
            MyTiData.getInstance().storeUserData();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
