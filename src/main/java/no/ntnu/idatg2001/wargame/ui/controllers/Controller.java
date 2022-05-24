package no.ntnu.idatg2001.wargame.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargame.model.*;
import no.ntnu.idatg2001.wargame.ui.views.ArmiesPane;
import no.ntnu.idatg2001.wargame.ui.views.ArmyWindow;
import no.ntnu.idatg2001.wargame.ui.views.BattlePane;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the application.
 *
 * @author sondesp
 * @version 2022-05-23
 * @since 2022-05-19
 */

public class Controller {

    private static Battle battle; // Battle for simulation, initializes with empty armies named "temp1" and "temp2"
    private static String saveDir; // String to {HOME}/Documents/WargameArmies
    private static List<Army> armies; // List of available armies
    private static Army previousWinner; // Last simulation winner
    private static boolean simRunning; // If the simulation is currently running

    private static Scene scene;
    private static GraphicsContext gc;
    private static BattlePane.BattleBox battleBox;
    private static List<ListView> mainListViews;
    private static ListView<String> armyList;
    private static Slider speed;
    private static ChoiceBox choiceBox;

    /**
     * Constructor for the controller. Initializes variables.
     */
    public Controller() {
        battle = new Battle(new Army(""), new Army(""));
        previousWinner = null;
        simRunning = false;
        saveDir = System.getProperty("user.home").replaceAll("\\\\", "/") + "/Documents/WargameArmies";

        File folder = new File(saveDir);
        if (!folder.exists()) {folder.mkdir();}
        readArmies();
    }

    /**
     * Button to switch from battle pane to armies pane.
     */
    public static void viewArmiesBtnPress() {
        simRunning = false;
        scene.setRoot(new ArmiesPane());
    }

    /**
     * Button to switch from armies pane to battle pane.
     */
    public static void armiesBackBtn() {
        updateMainListViews();
        clearBattle();
        scene.setRoot(new BattlePane());
    }

    /**
     * Handles the new army buttons when viewing armies. Creates a new army.
     */
    public static void newArmy() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Army");
        dialog.setHeaderText("Create new army.");
        dialog.setContentText("Enter new file name: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(fileName -> {
            if (fileName.equals("")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("New Army");
                alert.setHeaderText("New file must have a name!");
                alert.setContentText("Please enter a file name.");

                alert.showAndWait();
            }
            else {
                try {
                    new ArmyWindow(fileName, new Army("New army")).start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Opens the selected army within ArmiesPane.
     */
    public static void viewArmy() {
        if (armyList.getSelectionModel().getSelectedIndex() < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("View/Edit Army");
            alert.setHeaderText("No army Selected!");
            alert.setContentText("Please select an army to view.");

            alert.showAndWait();
        }
        else {
            Army selected = FileHandler.readArmies(saveDir).get(armyList.getSelectionModel().getSelectedIndex());
            File f = new File(saveDir);
            String fileNameCsv = Arrays.stream(f.list()).toList()
                    .get(armyList.getSelectionModel().getSelectedIndex());
            String fileName = fileNameCsv.substring(0, fileNameCsv.length() - 4);

            try {
                new ArmyWindow(fileName).start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes the selected army within ArmiesPane.
     */
    public static void deleteArmy() {
        if (armyList.getSelectionModel().getSelectedIndex() < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("View/Edit Army");
            alert.setHeaderText("No army Selected!");
            alert.setContentText("Please select an army to view.");

            alert.showAndWait();
        }
        else {
            File f = new File(saveDir);
            String fileName = Arrays.stream(f.list()).map(obj -> obj.substring(0, obj.length() - 4)).toList()
                    .get(armyList.getSelectionModel().getSelectedIndex());
            File file = new File(saveDir + "/" + fileName + ".csv");
            if (file.exists()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete File");
                alert.setHeaderText("You are about to delete: " + fileName);
                alert.setContentText("Are you ok with this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    file.delete();
                }
            }
            else {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setTitle("Error Dialog");
                err.setHeaderText("File does not exist!");
                err.setContentText("Check {HOME}/Documents/WargameArmies for saved files.");

                err.showAndWait();
                throw new IllegalStateException("File does not exist");
            }
            updateArmyList();
        }
    }

    /**
     * Returns an observable list of the available armies' names under {HOME}/Documents/WargameArmies
     *
     * @return ObservableList of strings representing the current available armies.
     */
    public static ObservableList<String> getArmiesObservableList() {
        File f = new File(saveDir);
        List<String> pathnames = Arrays.stream(f.list()).map(obj -> obj.substring(0, obj.length() - 4)).toList();

        ObservableList<String> observableList = FXCollections.observableArrayList(pathnames);
        return observableList;
    }

    /**
     * Method for clearing the selected armies in the BattlePane.
     */
    private static void clearMainViewSelection() {
        for (ListView list : mainListViews) {
            list.getSelectionModel().clearSelection();
        }
    }

    /**
     * Updates armies property of the application.
     */
    private static void readArmies() {
        armies = FileHandler.readArmies(saveDir);
    }

    /**
     * Sets one of the armies for the current battle.
     *
     * @param armyNum int for which army property within battle, 1/2
     * @param fileName filename without ".csv"
     */
    public static void selectBattleArmy (int armyNum, String fileName) {
        if (!(fileName == null)){
            Army temp = FileHandler.readArmyCSV(saveDir + "/" + fileName + ".csv");

            if (armyNum == 1) {
                temp.randomPositions(0,0,100,300);
                battle.setArmyOne(temp);
            } else if (armyNum == 2) {
                temp.randomPositions(300,0,400,300);
                battle.setArmyTwo(temp);
            } else {
                throw new IllegalArgumentException("Invalid army number");
            }
        }
        drawPreBattle();
    }

    /**
     * Updates the list of selectable armies in the battle pane.
     */
    public static void updateMainListViews() {
        for (ListView list : mainListViews) {
            list.itemsProperty().set(getArmiesObservableList());
            clearBattle();
        }
    }

    /**
     * Updates the viewable army list within ArmiesPane.
     */
    public static void updateArmyList() {
        armyList.getItems().clear();
        File f = new File(saveDir);
        List<String> fileNames = Arrays.stream(f.list()).map(obj -> obj.substring(0, obj.length() - 4)).toList();

        for (String fileName : fileNames) {
            Army army = FileHandler.readArmyCSV(saveDir + "/" + fileName + ".csv");
            armyList.getItems().add(
                    fileName + "    ||    " + army.getName() + "    ||    " + army.getUnits().size() + "units"
            );
        }
    }

    /**
     * Clears the current Battle.
     */
    public static void clearBattle() {
        battle = new Battle(new Army(""), new Army(""));
        drawPreBattle();
    }

    /**
     * Handles the simulate button in BattlePane and starts simulation.
     */
    public static void simulate() {
        if (battle.getArmyOne().getName().equals("") || battle.getArmyTwo().getName().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Simulate");
            alert.setHeaderText("Select two armies!");
            alert.setContentText("Both armies need to be selected.");

            alert.showAndWait();
        }
        else {
            clearMainViewSelection();
            simRunning = true;
            battleBox.midBattle();
            new SimulationThread(battle,
                    Terrain.valueOf(choiceBox.getSelectionModel().getSelectedItem().toString())
                    , (int)speed.getValue());
        }
    }

    /**
     * Set the winner army from the simulation.
     *
     * @param army Army winner, null if none
     */
    public static void simWinner(Army army) {
        simRunning = false;
        previousWinner = army;
        if (previousWinner == null) {
            drawPreBattle();
        }
        else {
            drawWinner();
        }
    }

    /**
     * Handles the back button in BattlePane.
     */
    public static void backSimulation() {
        clearMainViewSelection();
        clearBattle();
        simRunning = false;
        if (previousWinner == null) {
            battleBox.preBattle();
        }
        else {
            battleBox.postBattle();
        }
    }

    /**
     * Opens the previous battle's winner, if any.
     */
    public static void viewPreviousWin() {
        if (previousWinner != null) {
            try {
                new ArmyWindow("previousWin", previousWinner).start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Previous Winner");
            alert.setHeaderText("There was no previous winner!");
            alert.setContentText("Assuming nothing went wrong, they might've tied!");

            alert.showAndWait();
        }
    }

    /**
     * Draws the background based on the selected ChoiceBox terrain
     */
    public static void drawBackground() {
        Terrain terrain = choiceBox == null ?
                Terrain.NONE :
                Terrain.valueOf(choiceBox.getSelectionModel().getSelectedItem().toString());

        switch (terrain) {
            case PLAINS:
                gc.setFill(Color.GREEN);
                break;
            case HILLS:
                gc.setFill(Color.GREY);
                break;
            case FOREST:
                gc.setFill(Color.DARKGREEN);
                break;
            case NONE:
                gc.setFill(Color.BLACK);
                break;
        }
        gc.fillRect(0,0,400,300);
    }

    /**
     * Draw pre-battle information to the canvas in battle pane.
     */
    public static void drawPreBattle() {
        drawBackground();

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Tahoma",40));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(battle.getArmyOne().getName(),200,75);
        gc.fillText("vs",200,150);
        gc.fillText(battle.getArmyTwo().getName(),200,225);
    }

    /**
     * Draws the previous winner after a simulation ends, before new armies are picked.
     */
    public static void drawWinner() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,400,300);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Tahoma",40));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("" + previousWinner.getName(),200,100);
        gc.fillText("wins!",200,200);
    }

    /**
     * Sets the graphical context, used to update canvas.
     *
     * @param gc GraphicalContext of the battle canvas.
     */
    public static void setGc(GraphicsContext gc) {
        Controller.gc = gc;
    }

    /**
     * Get the graphical context of the canvas.
     *
     * @return GraphicsContext of canvas
     */
    public static GraphicsContext getGc() {
        return gc;
    }

    /**
     * Adds the listviews of the battle pane to the controller for observer functionality.
     * Used later with updateMainListViews
     *
     * @param leftList ListView
     * @param rightList ListView
     */
    public static void setMainListViews(ListView leftList, ListView rightList) {
        mainListViews = new ArrayList<>();
        mainListViews.add(leftList);
        mainListViews.add(rightList);
    }

    /**
     * Adds the scene from the main window to the controller.
     * Used to update the pane with the viewArmies and armiesBack buttons.
     *
     * @param newScene Scene
     */
    public static void setScene(Scene newScene) {
        scene = newScene;
    }

    /**
     * Adds the list of viewable armies within ArmiesPane to the controller for updates.
     * Used within viewArmy and deleteArmy
     *
     * @param list ListView from ArmiesPane.
     */
    public static void setArmyList(ListView list) {
        armyList = list;
    }

    /**
     * Adds the SpeedSlider in the BattlePane for observer functionality.
     *
     * @param speedSlider SpeedPane from BattlePane
     */
    public static void setSpeed(Slider speedSlider) {
        speed = speedSlider;
    }

    /**
     * Set if the simulation is running
     *
     * @param bool boolean
     */
    public static void setSimRunning (boolean bool) {
        simRunning = bool;
    }

    /**
     * Add the battle window controls for observer functionality.
     *
     * @param box BattleBox from BattlePane
     */
    public static void setBattleBox(BattlePane.BattleBox box) {
        battleBox = box;
    }

    /**
     * Sets the choice box to know what is selected
     *
     * @param box ChoiceBox from BattlePane
     */
    public static void setChoiceBox(ChoiceBox box) {
        choiceBox = box;
    }

    /**
     * Returns if the simulation is currently running.
     *
     * @return boolean
     */
    public static boolean getSimRunning () {
        return simRunning;
    }

    /**
     * Returns the controllers save directory.
     *
     * @return String of save directory
     */
    public static String getSaveDir() {
        return saveDir;
    }
}
