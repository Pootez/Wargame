package no.ntnu.idatg2001.wargame.ui.views;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import no.ntnu.idatg2001.wargame.ui.controllers.Controller;

public class BattlePane extends BorderPane {

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

        // VBox to hold pre-battle elements
        VBox preGameVbox = new VBox();
        preGameVbox.setAlignment(Pos.TOP_CENTER);
        preGameVbox.setSpacing(5);

        Slider speedSlider = new Slider(0,100,50);
        Label speedLabel = new Label("Speed: " + String.valueOf(speedSlider.valueProperty().getValue().intValue()));
        speedLabel.setFont(Font.font(10));

        speedSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                speedLabel.textProperty().setValue("Speed: " + String.valueOf(newValue.intValue()));
            }
        });

        // Create top buttons
        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(actionEvent -> Platform.exit());
        exitBtn.setFont(Font.font(20));
        exitBtn.setPadding(new Insets(5,30,5,30));

        Button viewArmies = new Button("View Armies");
        viewArmies.setOnAction(actionEvent -> Controller.viewArmiesBtnPress());
        viewArmies.setFont(Font.font(20));
        viewArmies.setPadding(new Insets(5,30,5,30));

        HBox buttonBar = new HBox();
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.getChildren().addAll(exitBtn, viewArmies);
        this.setTop(buttonBar);

        Button simulateBtn = new Button("Simulate");
        simulateBtn.setPadding(new Insets(5,30,5,30));

        preGameVbox.getChildren().addAll(speedLabel,speedSlider,simulateBtn);
        vBox.getChildren().add(preGameVbox);
    }

}
