package no.ntnu.idatg2001.wargame.ui.views;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargame.model.Army;
import no.ntnu.idatg2001.wargame.model.FileHandler;
import no.ntnu.idatg2001.wargame.model.Unit;
import no.ntnu.idatg2001.wargame.ui.controllers.Controller;

/**
 * Window for viewing and editing an army file.
 *
 * @author sondesp
 * @version 2022-05-23
 * @since 2022-05-23
 */

public class ArmyWindow extends Application {

    private String fileName;
    private Army army;

    public ArmyWindow (String fileName) {
        this.fileName = fileName;
        army = FileHandler.readArmyCSV(Controller.getSaveDir() + "/" + fileName + ".csv");

    }

    public ArmyWindow (String fileName, Army army) {
        this.fileName = fileName;
        this.army = army;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the application.
     *
     * @param stage Primary Stage for the Application
     */
    @Override
    public void start(Stage stage) throws Exception {
        ArmyPane armyPane = new ArmyPane();

        // Create a scene for primaryStage and assign stylesheet
        Scene scene = new Scene(armyPane);
        scene.getStylesheets().add(this.getClass().getResource("/stylesheet.css").toExternalForm());
        stage.setScene(scene);

        // Settings
        stage.setTitle("View/Edit Army");
        stage.setWidth(800);
        stage.setHeight(600);

        // Display Window
        stage.show();
    }

    private class ArmyPane extends BorderPane {

        private TableView tableView;

        public ArmyPane () {
            editArmy();
        }

        public void editArmy() {
            // Create top buttons
            CustomButton addUnitsBtn = new CustomButton("Add Units", e -> addUnits());
            CustomButton deleteUnitsBtn = new CustomButton("Delete Unit", e -> deleteUnit());

            HBox buttonBar = new HBox();
            buttonBar.setAlignment(Pos.CENTER);
            buttonBar.getChildren().addAll(addUnitsBtn, deleteUnitsBtn);
            this.setTop(buttonBar);

            // Create center table
            tableView = new TableView();

            TableColumn<Row, String> typeColumn = new TableColumn<>("Unit Type");
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

            TableColumn<Row, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Row, String> hpColumn = new TableColumn<>("Unit Health");
            hpColumn.setCellValueFactory(new PropertyValueFactory<>("hp"));

            tableView.getColumns().addAll(typeColumn, nameColumn, hpColumn);

            for (Unit unit : army.getUnits()) {
                tableView.getItems().add(new Row(unit));
            }

            this.setCenter(tableView);

        }

        private void addUnits() {

        }

        private void deleteUnit() {

        }
    }

    public class Row {

        private String type;
        private String name;
        private String hp;

        public Row (Unit unit) {
            type = unit.getClass().getSimpleName();
            name = unit.getName();
            hp = String.valueOf(unit.getHealth());
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getHp() {
            return hp;
        }
    }
}
