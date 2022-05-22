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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
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

        // Create StackPane to hold windows
        StackPane stackPane = new StackPane();
        Controller.setMainStackPane(stackPane);

        // Add the battle Pane
        BattlePane battlePane = new BattlePane();
        stackPane.getChildren().add(battlePane);

        // Create a scene for primaryStage and assign stylesheet
        Scene scene = new Scene(stackPane);
        scene.getStylesheets().add(this.getClass().getResource("/stylesheet.css").toExternalForm());
        primaryStage.setScene(scene);

        // Set title
        primaryStage.setTitle("Wargame");

        // Display window
        primaryStage.show();
    }
}