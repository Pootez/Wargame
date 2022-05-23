package no.ntnu.idatg2001.wargame.ui.views;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargame.model.*;
import no.ntnu.idatg2001.wargame.ui.controllers.Controller;

import java.io.File;
import java.util.Optional;

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
        ArmyPane armyPane = new ArmyPane(stage);

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

        private Stage stage;
        private TableView<Row> tableView;
        private TextField nameField;

        public ArmyPane (Stage stage) {
            this.stage = stage;
            tableView = new TableView<>();

            TableColumn<Row, String> typeColumn = new TableColumn<>("Unit Type");
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

            TableColumn<Row, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Row, String> hpColumn = new TableColumn<>("Unit Health");
            hpColumn.setCellValueFactory(new PropertyValueFactory<>("hp"));

            tableView.getColumns().addAll(typeColumn, nameColumn, hpColumn);

            editArmy();
        }

        public void editArmy() {
            // Create top buttons
            CustomButton addUnitsBtn = new CustomButton("Add Units", event -> addUnits());
            CustomButton deleteUnitBtn = new CustomButton("Delete Unit", event -> deleteUnit());
            CustomButton copyUnitBtn = new CustomButton("Copy Unit", event -> copyUnit());

            HBox buttonBar = new HBox();
            buttonBar.getChildren().addAll(addUnitsBtn, deleteUnitBtn, copyUnitBtn);

            Label nameLabel = new Label("Army Name: ");
            nameField = new TextField(army.getName());



            Label fileLabel = new Label("Current File: ");
            fileLabel.setFont(Font.font(22));
            fileLabel.setStyle("-fx-font-weight: bold");
            Label currentFile = new Label(fileName);
            currentFile.setFont(Font.font(22));

            TextFlow textFlow = new TextFlow();
            textFlow.getChildren().addAll(fileLabel, currentFile);

            HBox armyNameText = new HBox();
            armyNameText.getChildren().addAll(nameLabel, nameField, textFlow);

            VBox vBox = new VBox();
            vBox.getChildren().addAll(armyNameText, buttonBar);
            this.setBottom(vBox);

            // Create center table
            tableView.getItems().clear();

            for (Unit unit : army.getUnits()) {
                tableView.getItems().add(new Row(unit, army.getUnits().indexOf(unit)));
            }

            tableView.sort();

            this.setCenter(tableView);

            // Create Bottom Buttons
            CustomButton saveBtn = new CustomButton("Save", event -> save());
            CustomButton changeNameBtn = new CustomButton("Change File Name", event -> changeName());
            CustomButton deleteBtn = new CustomButton("Delete File", event -> delete());
            CustomButton cancelBtn = new CustomButton("Cancel", event -> cancel());

            HBox top = new HBox();
            top.setAlignment(Pos.CENTER);
            top.getChildren().addAll(saveBtn, changeNameBtn, deleteBtn, cancelBtn);
            this.setTop(top);
        }

        private void addUnits() {
            //TODO Add dialog
        }

        private void deleteUnit() {
            Row selected = tableView.getSelectionModel().getSelectedItem();
            army.remove(army.getUnits().get(selected.getId()));
            editArmy();
        }

        private void copyUnit() {
            Row selected = tableView.getSelectionModel().getSelectedItem();
            String name = selected.getName();
            int hp = Integer.parseInt(selected.getHp());
            String type = selected.getType() + "Unit";
            Unit temp = type.equals("CommanderUnit") ? new CommanderUnit(name,hp) :
                    type.equals("InfantryUnit") ? new InfantryUnit(name,hp) :
                            type.equals("CavalryUnit") ? new CavalryUnit(name,hp) :
                                    type.equals("RangedUnit") ? new RangedUnit(name,hp) : null;

            army.addUnit(temp);
            editArmy();
        }

        private void save() {
            army.setName(nameField.getText());
            FileHandler.writeArmyCSV(army, Controller.getSaveDir() + "/" + fileName + ".csv");
            Controller.updateArmyList();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save");
            alert.setHeaderText(null);
            alert.setContentText("Your file has been saved!");

            alert.showAndWait();
        }

        private void delete() {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete File");
            alert.setHeaderText("You are about to delete: " + fileName);
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                File f = new File(Controller.getSaveDir() + "/" + fileName + ".csv");
                if (f.exists()) {
                    f.delete();
                }
                else {
                    Alert err = new Alert(Alert.AlertType.ERROR);
                    err.setTitle("Error Dialog");
                    err.setHeaderText("File does not exist!");
                    err.setContentText("Check {HOME}/Documents/WargameArmies for saved files.");
                    return;
                }
                Controller.updateArmyList();
                stage.close();
            }
        }

        public void changeName() {
            TextInputDialog dialog = new TextInputDialog(fileName);
            dialog.setTitle("Changing file name");
            dialog.setHeaderText("Changing file name from: " + fileName);
            dialog.setContentText("Enter new file name: ");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> fileName = result.get());
            editArmy();
        }

        private void cancel() {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel");
            alert.setHeaderText("You are leaving the file: " + fileName);
            alert.setContentText("Unsaved changes will ble lost.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                stage.close();
            }
        }
    }

    public class Row {

        private String type;
        private String name;
        private String hp;
        private int id;

        public Row (Unit unit, int id) {
            type = unit.getClass().getSimpleName().substring(0, unit.getClass().getSimpleName().length() - 4);
            name = unit.getName();
            hp = String.valueOf(unit.getHealth());
            this.id = id;
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

        public int getId() {
            return id;
        }
    }
}
