package no.ntnu.idatg2001.wargame.ui.views;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import no.ntnu.idatg2001.wargame.ui.controllers.Controller;

public class CustomButton extends Button{
    public CustomButton (String name, EventHandler e) {
        super(name);
        this.setOnAction(e);
        this.setFont(Font.font(20));
        this.setPadding(new Insets(5,30,5,30));
    }
}
