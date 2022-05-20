package no.ntnu.idatg2001.wargame.ui.views;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

        BorderPane borderPane = new BorderPane();

        VBox leftVBox = new VBox();
        Label leftLabel = new Label("Army 1:");
        leftLabel.setFont(Font.font(20));
        ListView<String> leftList = new ListView<String>(FXCollections.observableList(controller.getArmiesObservableList()));
        leftList.setPrefWidth(150);
        leftVBox.getChildren().addAll(leftLabel,leftList);
        leftVBox.setAlignment(Pos.TOP_CENTER);

        VBox rightVBox = new VBox();
        Label rightLabel = new Label("Army 2:");
        rightLabel.setFont(Font.font(20));
        ListView<String> rightList = new ListView<String>(FXCollections.observableList(controller.getArmiesObservableList()));
        rightList.setPrefWidth(150);
        rightVBox.getChildren().addAll(rightLabel,rightList);
        rightVBox.setAlignment(Pos.TOP_CENTER);

        borderPane.setLeft(leftVBox);
        borderPane.setRight(rightVBox);


        Canvas canvas = new Canvas(400,300);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        VBox vBox = new VBox();
        vBox.getChildren().add(canvas);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.minWidth(700);

        borderPane.setCenter(vBox);

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(this.getClass().getResource("/stylesheet.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.setTitle("Wargame");
        primaryStage.show();
    }
}