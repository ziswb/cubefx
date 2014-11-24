package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.CubeFxApp;
import ch.bziswiler.cube.controller.eventlist.CubeEventListController;
import ch.bziswiler.cube.data.CubeEventModelSelectionProvider;
import javafx.collections.ObservableList;
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

    private String darkThemeUrl = getClass().getResource("../view/DarkTheme.css").toExternalForm();
    private String lightThemeUrl = getClass().getResource("../view/LightTheme.css").toExternalForm();

    @FXML
    private void handleLoadLightTheme() {
        removeTheme(darkThemeUrl);
        setTheme(lightThemeUrl);
    }

    private boolean removeTheme(String themeUrl) {
        return getScene().getStylesheets().remove(themeUrl);
    }

    private void setTheme(String themeUrl) {
        if (!getScene().getStylesheets().contains(themeUrl)) {
            getScene().getStylesheets().add(themeUrl);
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
        final CubeEventListController controller = loader.getController();
        controller.setDialogStage(dialog);
        dialog.showAndWait();
        if (controller.isOK()) {
            CubeEventModelSelectionProvider.INSTANCE.setSelectedEvent(controller.getSelectedEvent());
        }
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
        scene.getStylesheets().addAll(getScene().getStylesheets());
        dialogStage.setScene(scene);
        return dialogStage;
    }

    private Scene getScene() {
        return CubeFxApp.getInstance().getPrimaryStage().getScene();
    }
}
