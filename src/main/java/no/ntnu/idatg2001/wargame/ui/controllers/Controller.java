package no.ntnu.idatg2001.wargame.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import no.ntnu.idatg2001.wargame.model.Army;
import no.ntnu.idatg2001.wargame.model.Battle;
import no.ntnu.idatg2001.wargame.model.FileHandler;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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

    private static Battle battle; // Battle for simulation, initializes with empty armies named "temp1" and "temp2"
    private static String saveDir; // String to {HOME}/Documents/WargameArmies
    private static List<Army> armies; // List of available armies

    private static GraphicsContext gc;
    private static List<ListView> mainListViews;

    /**
     * Constructor for the controller. Initializes variables.
     */
    public Controller() {
        battle = new Battle(new Army(""), new Army(""));
        saveDir = System.getProperty("user.home").replaceAll("\\\\", "/") + "/Documents/WargameArmies";
        File folder = new File(saveDir);
        if (!folder.exists()) {folder.mkdir();}
        readArmies();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    /**
     * Returns an observable list of the available armies' names under {HOME}/Documents/WargameArmies
     *
     * @return ObservableList of strings representing the current available armies.
     */
    public ObservableList<String> getArmiesObservableList() {
        File f = new File(saveDir);
        List<String> pathnames = Arrays.stream(f.list()).map(obj -> obj.substring(0, obj.length() - 4)).toList();

        ObservableList<String> observableList = FXCollections.observableArrayList(pathnames);
        return observableList;
    }

    /**
     * Updates armies property of the application.
     */
    private void readArmies() {
        armies = FileHandler.readArmies(saveDir);
    }

    /**
     * Sets one of the armies for the current battle.
     *
     * @param armyNum int for which army property within battle, 1/2
     * @param fileName filename without ".csv"
     */
    public void selectBattleArmy (int armyNum, String fileName) {
        Army temp = FileHandler.readArmyCSV(saveDir + "/" + fileName + ".csv");

        if (armyNum == 1) {
            battle.setArmyOne(temp);
        }
        else if (armyNum == 2) {
            battle.setArmyTwo(temp);
        }
        else {throw new IllegalArgumentException("Invalid army number");}
        drawPreBattle();
    }

    public void updateMainListViews() {
        for (ListView list : mainListViews) {
            list.itemsProperty().set(getArmiesObservableList());
        }
    }

    public void drawPreBattle() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,400,300);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Tahoma",40));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(battle.getArmyOne().getName(),200,75);
        gc.fillText("vs",200,150);
        gc.fillText(battle.getArmyTwo().getName(),200,225);
    }

    public static void setGc(GraphicsContext gc) {
        Controller.gc = gc;
    }

    public void setMainListViews(ListView leftList, ListView rightList) {
        mainListViews = new ArrayList<>();
        mainListViews.add(leftList);
        mainListViews.add(rightList);
    }
}
