package no.ntnu.idatg2001.wargame.ui.views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatg2001.wargame.ui.controllers.Controller;

import java.io.IOException;

/**
 * Main Window for simulating a battle.
 *
 * @author sondesp
 * @version 2022-02-23
 * @since 2022-05-19
 */

public class MainWindow extends Application {

    private Controller controller; // Mainly for initialization of the controller

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
     * @param stage Primary Stage for the Application
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Initialize controller
        controller = new Controller();

        // Create a scene for primaryStage and assign stylesheet
        Scene scene = new Scene(new BattlePane());
        Controller.setScene(scene);
        scene.getStylesheets().add(this.getClass().getResource("/stylesheet.css").toExternalForm());
        stage.setScene(scene);

        // Set title
        stage.setTitle("Wargame");

        // Display window
        stage.show();
    }
}