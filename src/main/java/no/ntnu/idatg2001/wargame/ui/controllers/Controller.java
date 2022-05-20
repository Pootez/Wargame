package no.ntnu.idatg2001.wargame.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import no.ntnu.idatg2001.wargame.model.Army;
import no.ntnu.idatg2001.wargame.model.Battle;
import no.ntnu.idatg2001.wargame.model.FileHandler;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the application.
 *
 * @author sondesp
 * @version 2022-02-20
 * @since 2022-05-19
 */

public class Controller implements Initializable {

    private Battle battle; // Battle for simulation, initializes with empty armies named "temp1" and "temp2"
    private String saveDir; // String to {HOME}/Documents/WargameArmies
    private List<Army> armies; // List of available armies

    /**
     * Constructor for the controller. Initializes variables.
     */

    public Controller() {
        battle = new Battle(new Army("temp1"), new Army("temp2"));
        saveDir = System.getProperty("user.home").replaceAll("\\\\", "/") + "/Documents/WargameArmies";
        File folder = new File(saveDir);
        if (!folder.exists()) {folder.mkdir();}
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Returns an observable list of the available armies' names under {HOME}/Documents/WargameArmies
     *
     * @return ObservableList of strings representing the current available armies.
     */

    public ObservableList<String> getArmiesObservableList() {
        readArmies();
        ObservableList<String> observableList = FXCollections.observableArrayList(armies.stream().map(Army :: getName).toList());
        return observableList;
    }

    /**
     * Updates armies property of the application.
     */

    private void readArmies() {
        armies = FileHandler.readArmies(saveDir);
    }
}
