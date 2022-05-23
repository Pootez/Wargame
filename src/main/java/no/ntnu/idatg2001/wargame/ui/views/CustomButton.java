package no.ntnu.idatg2001.wargame.ui.views;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

/**
 * Custom button with inbuilt name and setOnAction.
 * Extends Button
 *
 * @author sondesp
 * @version 2022-05-23
 * @since 2022-05-23
 */

public class CustomButton extends Button {

    /**
     * Constructor for the custom button.
     *
     * @param name String, text displayed on button.
     * @param event EventHandler, for using methods (e -> {})
     */
    public CustomButton (String name, EventHandler event) {
        super(name);
        this.setOnAction(event);
        this.setFont(Font.font(20));
        this.setPadding(new Insets(5,30,5,30));
    }
}
