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
        Button exitBtn = new Button("Back");
        exitBtn.setOnAction(actionEvent -> Controller.armiesBackBtn());
        exitBtn.setFont(Font.font(20));
        exitBtn.setPadding(new Insets(5,30,5,30));

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(actionEvent -> Controller.updateArmyList());
        refreshBtn.setFont(Font.font(20));
        refreshBtn.setPadding(new Insets(5,30,5,30));

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
        Button viewArmyBtn = new Button("View/Edit");
        viewArmyBtn.setOnAction(actionEvent -> {
            try {
                Controller.viewArmy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        viewArmyBtn.setFont(Font.font(20));
        viewArmyBtn.setPadding(new Insets(5,30,5,30));

        Button deleteArmyBtn = new Button("Delete");
        deleteArmyBtn.setOnAction(actionEvent -> Controller.deleteArmy());
        deleteArmyBtn.setFont(Font.font(20));
        deleteArmyBtn.setPadding(new Insets(5,30,5,30));

        HBox selectionBar = new HBox();
        selectionBar.setAlignment(Pos.CENTER);
        selectionBar.getChildren().addAll(viewArmyBtn, deleteArmyBtn);
        this.setBottom(selectionBar);
    }

}
