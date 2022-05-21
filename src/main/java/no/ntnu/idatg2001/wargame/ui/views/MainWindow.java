package no.ntnu.idatg2001.wargame.ui.views;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargame.ui.controllers.Controller;

import java.io.IOException;

/**
 * Main Window for simulating a battle.
 *
 * @author sondesp
 * @version 2022-02-20
 * @since 2022-05-19
 */

public class MainWindow extends Application {

    private Controller controller;

    /**
     * Container for main.
     *
     * @param args Arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the application.
     *
     * @param primaryStage Primary Stage for the Application
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        controller = new Controller();

        // Create a borderPane to contain elements
        BorderPane borderPane = new BorderPane();

        // Create a VBoxes to display available armies
        VBox leftVBox = new VBox();

        // Create labels for VBoxes
        Label leftLabel = new Label("Army 1:");
        leftLabel.setFont(Font.font(20));

        VBox rightVBox = new VBox();
        Label rightLabel = new Label("Army 2:");
        rightLabel.setFont(Font.font(20));

        // Create a lists with selectable armies
        ListView<String> leftList = new ListView<String>(FXCollections.observableList(controller.getArmiesObservableList()));
        leftList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                controller.selectBattleArmy(1, newValue);
            }
        });

        ListView<String> rightList = new ListView<String>(FXCollections.observableList(controller.getArmiesObservableList()));
        rightList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                controller.selectBattleArmy(2, newValue);
            }
        });

        // Add to controller for updates.
        controller.setMainListViews(leftList, rightList);

        // Add children to VBoxes
        leftList.setPrefWidth(150);
        leftVBox.getChildren().addAll(leftLabel,leftList);
        leftVBox.setAlignment(Pos.TOP_CENTER);

        rightList.setPrefWidth(150);
        rightVBox.getChildren().addAll(rightLabel,rightList);
        rightVBox.setAlignment(Pos.TOP_CENTER);

        // Add VBoxes to BorderPane
        borderPane.setLeft(leftVBox);
        borderPane.setRight(rightVBox);

        // Create a canvas to hold visuals
        Canvas canvas = new Canvas(400,300);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Create Center VBox
        VBox vBox = new VBox();
        vBox.getChildren().add(canvas);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.minWidth(700);
        borderPane.setCenter(vBox);

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

        Button simulateBtn = new Button("Simulate");
        simulateBtn.setPadding(new Insets(5,30,5,30));

        preGameVbox.getChildren().addAll(speedLabel,speedSlider,simulateBtn);
        vBox.getChildren().add(preGameVbox);

        // Create a scene for primaryStage and assign stylesheet
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(this.getClass().getResource("/stylesheet.css").toExternalForm());
        primaryStage.minWidthProperty().setValue(700);
        primaryStage.setScene(scene);

        // Set title
        primaryStage.setTitle("Wargame");

        // Display window
        primaryStage.show();
    }
}