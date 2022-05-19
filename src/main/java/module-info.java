module no.ntnu.idatg2001 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    // open model to everyone in to be able to run tests from Maven
    opens no.ntnu.idatg2001.wargame.model;
    opens no.ntnu.idatg2001.wargame.ui.views;
    opens no.ntnu.idatg2001.wargame.ui.controllers;

    exports no.ntnu.idatg2001.wargame.ui.controllers;
    exports no.ntnu.idatg2001.wargame.ui.views;
}
