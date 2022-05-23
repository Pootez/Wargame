package no.ntnu.idatg2001.wargame.ui.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
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

        // Selection Buttons
        CustomButton viewArmyBtn = new CustomButton("View/Edit", event -> {
            try {
                Controller.viewArmy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        CustomButton deleteArmyBtn = new CustomButton("Delete", event -> Controller.deleteArmy());

        HBox selectionBar = new HBox();
        selectionBar.setAlignment(Pos.CENTER);
        selectionBar.getChildren().addAll(viewArmyBtn, deleteArmyBtn);
        this.setBottom(selectionBar);
    }

}
