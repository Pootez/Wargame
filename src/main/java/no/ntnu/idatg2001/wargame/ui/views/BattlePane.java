package no.ntnu.idatg2001.wargame.ui.views;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import no.ntnu.idatg2001.wargame.model.Terrain;
import no.ntnu.idatg2001.wargame.ui.controllers.Controller;

/**
 * BorderPane for displaying and simulating a battle.
 *
 * @author sondesp
 * @version 2022-05-23
 * @since 2022-05-23
 */

public class BattlePane extends BorderPane {

    /**
     * BattlePane constructor.
     */
    public BattlePane () {

        // Create a VBoxes to display available armies
        VBox leftVBox = new VBox();

        // Create labels for VBoxes
        Label leftLabel = new Label("Army 1:");
        leftLabel.setFont(Font.font(20));

        VBox rightVBox = new VBox();
        Label rightLabel = new Label("Army 2:");
        rightLabel.setFont(Font.font(20));

        // Create a lists with selectable armies
        ListView<String> leftList = new ListView<String>(FXCollections.observableList(Controller.getArmiesObservableList()));
        leftList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Controller.selectBattleArmy(1, newValue);
            }
        });

        ListView<String> rightList = new ListView<String>(FXCollections.observableList(Controller.getArmiesObservableList()));
        rightList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Controller.selectBattleArmy(2, newValue);
            }
        });

        // Add to controller for updates.
        Controller.setMainListViews(leftList, rightList);

        // Add children to VBoxes
        leftList.setPrefWidth(150);
        leftVBox.getChildren().addAll(leftLabel,leftList);
        leftVBox.setAlignment(Pos.TOP_CENTER);

        rightList.setPrefWidth(150);
        rightVBox.getChildren().addAll(rightLabel,rightList);
        rightVBox.setAlignment(Pos.TOP_CENTER);

        // Add VBoxes to BorderPane
        this.setLeft(leftVBox);
        this.setRight(rightVBox);

        // Create a canvas to hold visuals
        Canvas canvas = new Canvas(400,300);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Controller.setGc(gc);
        Controller.drawPreBattle();

        // Create Center VBox
        VBox vBox = new VBox();
        vBox.getChildren().add(canvas);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.minWidth(700);
        this.setCenter(vBox);

        // Create top buttons
        CustomButton exitBtn = new CustomButton("Exit", event -> Platform.exit());
        CustomButton viewArmies = new CustomButton("View Armies", event -> Controller.viewArmiesBtnPress());

        HBox buttonBar = new HBox();
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.getChildren().addAll(exitBtn, viewArmies);
        this.setTop(buttonBar);

        vBox.getChildren().add(new battleBox());
    }

    /**
     * Vbox to hold controls for the battle, contains methods for changing its own content.
     */
    public class battleBox extends VBox {

        /**
         * Constructor for BattleBox. Sets up format.
         */
        public battleBox() {
            this.setAlignment(Pos.TOP_CENTER);
            this.setSpacing(5);

            preBattle();
        }

        /**
         * Sets its own content to before a battle.
         */
        public void preBattle() {
            this.getChildren().clear();

            Slider speedSlider = new Slider(0,100,50);
            Controller.setSpeed(speedSlider);
            Label speedLabel = new Label("Speed: " + String.valueOf(speedSlider.valueProperty().getValue().intValue()));
            speedLabel.setFont(Font.font(12));

            speedSlider.valueProperty().addListener(new ChangeListener<Number>() {

                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                    speedLabel.textProperty().setValue("Speed: " + String.valueOf(newValue.intValue()));
                }
            });

            ChoiceBox choiceBox = new ChoiceBox();
            choiceBox.getItems().setAll(Terrain.values());
            choiceBox.getSelectionModel().selectFirst();

            CustomButton simulateBtn = new CustomButton("Simulate", event -> Controller.simulate(
                    this, Terrain.valueOf(choiceBox.getSelectionModel().getSelectedItem().toString())));

            this.getChildren().addAll(speedLabel, speedSlider, simulateBtn, choiceBox);

        }

        /**
         * Sets its own content to the middle a battle.
         */
        public void midBattle() {
            this.getChildren().clear();

            CustomButton cancelBtn = new CustomButton("Cancel", event -> Controller.cancelSimulation(this));

            this.getChildren().add(cancelBtn);
        }

        /**
         * Sets its own content to after a battle.
         */
        public void postBattle() {
            Controller.drawWinner();

            this.getChildren().clear();

            Slider speedSlider = new Slider(0,100,50);
            Controller.setSpeed(speedSlider);
            Label speedLabel = new Label("Speed: " + String.valueOf(speedSlider.valueProperty().getValue().intValue()));
            speedLabel.setFont(Font.font(10));

            speedSlider.valueProperty().addListener(new ChangeListener<Number>() {

                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                    speedLabel.textProperty().setValue("Speed: " + String.valueOf(newValue.intValue()));
                }
            });

            ChoiceBox choiceBox = new ChoiceBox();
            choiceBox.getItems().setAll(Terrain.values());
            choiceBox.getSelectionModel().selectFirst();

            CustomButton simulateBtn = new CustomButton("Simulate", event -> Controller.simulate(
                    this, Terrain.valueOf(choiceBox.getSelectionModel().getSelectedItem().toString())));
            CustomButton previousWinnerBtn = new CustomButton("Previous Winner", event -> Controller.viewPreviousWin());

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(simulateBtn, previousWinnerBtn);

            this.getChildren().addAll(speedLabel, speedSlider, hBox, choiceBox);

        }
    }
}
