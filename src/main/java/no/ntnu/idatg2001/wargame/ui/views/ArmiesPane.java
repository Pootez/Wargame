package no.ntnu.idatg2001.wargame.ui.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import no.ntnu.idatg2001.wargame.ui.controllers.Controller;

public class ArmiesPane extends BorderPane {

    public ArmiesPane () {

        // Create top buttons
        Button exitBtn = new Button("Back");
        exitBtn.setOnAction(actionEvent -> Controller.armiesBackBtn());
        exitBtn.setFont(Font.font(20));
        exitBtn.setPadding(new Insets(5,30,5,30));

        HBox buttonBar = new HBox();
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.getChildren().addAll(exitBtn);
        this.setTop(buttonBar);
    }

}
