package no.ntnu.idatg2001.wargame.ui.views;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import no.ntnu.idatg2001.wargame.ui.controllers.Controller;

/**
 * BorderPane for displaying and opening saved armies.
 *
 * @author sondesp
 * @version 2022-05-23
 * @since 2022-05-23
 */

public class ArmiesPane extends BorderPane {

    /**
     * ArmiesPane constructor.
     */
    public ArmiesPane () {

        // Create top buttons
        CustomButton exitBtn = new CustomButton("Back", event -> Controller.armiesBackBtn());
        CustomButton refreshBtn = new CustomButton("Refresh", event -> Controller.updateArmyList());

        HBox buttonBar = new HBox();
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.getChildren().addAll(exitBtn, refreshBtn);
        this.setTop(buttonBar);

        // Create list of armies
        ListView<String> armyList = new ListView<>();
        Controller.setArmyList(armyList);
        Controller.updateArmyList();
        this.setCenter(armyList);

        // Allow for doubleclick
        armyList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 &&
                        armyList.getItems().size() >= armyList.getSelectionModel().getSelectedIndex() &&
                        armyList.getSelectionModel().getSelectedIndex() >= 0) {
                    try {
                        Controller.viewArmy();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Selection Buttons
        CustomButton newArmyBtn = new CustomButton("New Army", event -> Controller.newArmy());

        CustomButton viewArmyBtn = new CustomButton("View/Edit", event -> {
            try {
                Controller.viewArmy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        CustomButton deleteArmyBtn = new CustomButton("Delete", event -> Controller.deleteArmy());

        HBox selectionBar = new HBox();
        selectionBar.setAlignment(Pos.CENTER_LEFT);
        selectionBar.getChildren().addAll(newArmyBtn, viewArmyBtn, deleteArmyBtn);
        this.setBottom(selectionBar);
    }
}
