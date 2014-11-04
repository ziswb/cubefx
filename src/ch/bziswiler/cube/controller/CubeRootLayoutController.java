package ch.bziswiler.cube.controller;

import javafx.fxml.FXML;
import org.controlsfx.dialog.Dialogs;

public class CubeRootLayoutController {

    @FXML
    private void handleAbout() {
        Dialogs.create()
                .title("CubeFX App")
                .masthead("About")
                .message("Author: Bruno Ziswiler\nWebsite: http://www.bziswiler.ch/cube")
                .showInformation();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
