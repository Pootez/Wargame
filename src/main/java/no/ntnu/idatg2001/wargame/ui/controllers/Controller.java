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

public class Controller implements Initializable {

    private Battle battle;
    private String saveDir;
    private List<Army> armies;

    public Controller() {
        battle = new Battle(new Army("temp1"), new Army("temp2"));
        saveDir = System.getProperty("user.home").replaceAll("\\\\", "/") + "/Documents/WargameArmies";
        File folder = new File(saveDir);
        if (!folder.exists()) {folder.mkdir();}
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public ObservableList<String> getArmiesObservableList() {
        readArmies();
        ObservableList<String> observableList = FXCollections.observableArrayList(armies.stream().map(Army :: getName).toList());
        return observableList;
    }

    private void readArmies() {
        armies = FileHandler.readArmies(saveDir);
    }
}
