package myti;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import datamodel.MyTiData;
import datamodel.Station;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by AK on 16/10/16.
 */
public class TravelStatisticsDialogController implements Initializable {

    @FXML
    private String printStatistics = "";
    @FXML
    private Label statisticsLabel;

    /**
     * Udate Text Area and print travel statistics
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Station> stationArrayList = MyTiData.getInstance().getStationArrayList();
        for (Station station : stationArrayList){
            printStatistics += station.print();
        }
        statisticsLabel.setText(printStatistics);
    }
}
