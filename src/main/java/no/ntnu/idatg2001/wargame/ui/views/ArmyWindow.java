package no.ntnu.idatg2001.wargame.ui.views;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;
import no.ntnu.idatg2001.wargame.model.*;
import no.ntnu.idatg2001.wargame.ui.controllers.Controller;

import java.io.File;
import java.util.List;
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

    /**
     * Constructor for an existing army file.
     *
     * @param fileName fileName to be viewed
     */
    public ArmyWindow (String fileName) {
        this.fileName = fileName;
        army = FileHandler.readArmyCSV(Controller.getSaveDir() + "/" + fileName + ".csv");

    }

    /**
     * Constructor for an army object and its fileName.
     *
     * @param fileName String fileName
     * @param army Army to be viewed
     */
    public ArmyWindow (String fileName, Army army) {
        this.fileName = fileName;
        this.army = army;
    }

    /**
     * Container for main.
     *
     * @param args String of arguments
     */
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

    /**
     * BorderPane with methods to switch out and manage its elements.
     */
    private class ArmyPane extends BorderPane {

        private Stage stage;
        private TableView<Row> tableView;
        private TextField nameField;

        /**
         * Constructor, takes a stage to later close the window.
         *
         * @param stage Stage of window
         */
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

        /**
         * Sets up the main page for editing an army.
         */
        public void editArmy() {
            // Create top buttons
            CustomButton addUnitsBtn = new CustomButton("Add Units", event -> addUnits());
            CustomButton deleteUnitBtn = new CustomButton("Delete Unit", event -> deleteUnit());
            CustomButton copyUnitBtn = new CustomButton("Copy Unit", event -> copyUnit());

            HBox buttonBar = new HBox();
            buttonBar.getChildren().addAll(addUnitsBtn, deleteUnitBtn, copyUnitBtn);

            Label nameLabel = new Label("Army Name: ");
            nameLabel.setFont(Font.font(22));
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

        /**
         * Gives a dialog for creating a number of units given type, name and health.
         */
        private void addUnits() {
            Dialog<List<Unit>> dialog = new Dialog<>();
            dialog.setTitle("Add Units");
            dialog.setHeaderText("Please fill out the fields below.\n',' may not be used.");
            Label labelType = new Label("Type: ");
            Label labelName = new Label("Name: ");
            Label labelHp = new Label("Health: ");
            Label labelAmount = new Label("Amount: ");

            ChoiceBox<String> inputType = new ChoiceBox<>();
            inputType.getItems().addAll("Cavalry", "Infantry", "Ranged", "Commander");
            inputType.getSelectionModel().select(0);
            TextField inputName = new TextField();
            TextField inputHp = new TextField();
            inputHp.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        inputHp.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
            TextField inputAmount = new TextField();
            inputAmount.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        inputAmount.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });

            GridPane grid = new GridPane();
            grid.add(labelType, 1, 1);
            grid.add(labelName, 1, 2);
            grid.add(labelHp, 1, 3);
            grid.add(labelAmount, 1, 4);

            grid.add(inputType, 2, 1);
            grid.add(inputName, 2, 2);
            grid.add(inputHp, 2, 3);
            grid.add(inputAmount, 2, 4);

            dialog.getDialogPane().setContent(grid);

            ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

            dialog.setResultConverter(new Callback<ButtonType, List<Unit>>() {
                @Override
                public List<Unit> call(ButtonType b) {
                    if (b == buttonTypeOk) {
                        String response = "";
                        if (inputName.getText().equals("")) {
                            response += "A unit name is necessary.\n";
                        }
                        if (inputName.getText().contains(",")) {
                            response += "A unit name may not contain a ','.\n";
                        }
                        try {
                            int hp = Integer.parseInt(inputHp.getText());
                            if (hp <= 0) {
                                response += "A unit must have a positive number as health.\n";
                            }
                        } catch (NumberFormatException nfe) {
                            response += "A unit must have a number as health.\n";
                        }
                        try {
                            int amount = Integer.parseInt(inputAmount.getText());
                            if (amount <= 0) {
                                response += "There must be a positive number of units.\n";
                            }
                        } catch (NumberFormatException nfe) {
                            response += "There must be an number of units.\n";
                        }
                        if (response.equals("")) {
                            return UnitFactory.createUnits(
                                    Integer.parseInt(inputAmount.getText()),
                                    inputType.getValue(),
                                    inputName.getText(),
                                    Integer.parseInt(inputHp.getText())
                                    );
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Add Units");
                            alert.setHeaderText("Invalid input!");
                            alert.setContentText(response);

                            alert.showAndWait();
                        }
                        return null;
                    }
                    else {
                        return null;
                    }
                }
            });

            Optional<List<Unit>> result = dialog.showAndWait();
            if (result.isPresent()) {
                army.addUnits(result.get());
                editArmy();
            }
        }

        /**
         * Method to delete a selected unit.
         */
        private void deleteUnit() {
            Row selected = tableView.getSelectionModel().getSelectedItem();
            army.remove(army.getUnits().get(selected.getId()));
            editArmy();
        }

        /**
         * Method to copy a selected unit.
         */
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

        /**
         * Method to save the current army to the current file.
         */
        private void save() {
            army.setName(nameField.getText());
            FileHandler.writeArmyCSV(army, Controller.getSaveDir() + "/" + fileName + ".csv");
            Controller.updateArmyList();
            Controller.updateMainListViews();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save");
            alert.setHeaderText(null);
            alert.setContentText("Your file has been saved!");

            alert.showAndWait();
        }

        /**
         * Method to delete the current file.
         */
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

                    err.showAndWait();
                    throw new IllegalStateException("File does not exist");
                }
                Controller.updateArmyList();
                stage.close();
            }
        }

        /**
         * Method to change the fileName of the army.
         */
        public void changeName() {
            TextInputDialog dialog = new TextInputDialog(fileName);
            dialog.setTitle("Changing file name");
            dialog.setHeaderText("Changing file name from: " + fileName);
            dialog.setContentText("Enter new file name: ");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                if (name.equals("")) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Changing file name");
                    alert.setHeaderText("File must have a name!");
                    alert.setContentText("Please enter a file name.");

                    alert.showAndWait();
                }
                else {
                    fileName = name;
                }
            });
            editArmy();
        }

        /**
         * Method to exit the window without saving, giving a dialog in case of error.
         */
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

    /**
     * Class with properties specialized for the TableView.
     */
    public class Row {

        private String type;
        private String name;
        private String hp;
        private int id;

        /**
         * Constructor for a TableView Row.
         *
         * @param unit Unit to be entered into table
         * @param id int index of unit within its army
         */
        public Row (Unit unit, int id) {
            type = unit.getClass().getSimpleName().substring(0, unit.getClass().getSimpleName().length() - 4);
            name = unit.getName();
            hp = String.valueOf(unit.getHealth());
            this.id = id;
        }

        /**
         * Returns the type of unit in a TableView row.
         * @return String of type without "Unit"
         */
        public String getType() {
            return type;
        }

        /**
         * Returns the type of name in a TableView row.
         * @return String of name
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the hp of a unit of a TableView row.
         * @return int of hp
         */
        public String getHp() {
            return hp;
        }

        /**
         * Returns the index of a unit of a TableView row.
         * @return int index of the unit in its army
         */
        public int getId() {
            return id;
        }
    }
}
