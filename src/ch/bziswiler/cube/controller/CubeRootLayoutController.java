package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.CubeFxApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.dialog.Dialogs;

import java.io.IOException;

public class CubeRootLayoutController {

    private Scene scene;
    private String darkThemeUrl = getClass().getResource("../view/DarkTheme.css").toExternalForm();
    private String lightThemeUrl = getClass().getResource("../view/LightTheme.css").toExternalForm();

    @FXML
    private void handleLoadLightTheme() {
        removeTheme(darkThemeUrl);
        setTheme(lightThemeUrl);
    }

    private boolean removeTheme(String themeUrl) {
        return scene.getStylesheets().remove(themeUrl);
    }

    private void setTheme(String themeUrl) {
        if (!scene.getStylesheets().contains(themeUrl)) {
            scene.getStylesheets().add(themeUrl);
        }
    }

    @FXML
    private void handleLoadDarkTheme() {
        removeTheme(lightThemeUrl);
        setTheme(darkThemeUrl);
    }

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

    @FXML
    private void handleShowEventList() throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        final Stage dialog = loadModalEventListDialog(loader);
//        final DateTimePickerController controller = loader.getController();
        dialog.showAndWait();
    }

    private Stage loadModalEventListDialog(FXMLLoader loader) throws IOException {
        loader.setLocation(getClass().getResource("../view/cubeEventList.fxml"));
        final Region page = loader.load();
        final Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initStyle(StageStyle.DECORATED);
        dialogStage.initOwner(CubeFxApp.getInstance().getPrimaryStage());
        dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
        final Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        return dialogStage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
