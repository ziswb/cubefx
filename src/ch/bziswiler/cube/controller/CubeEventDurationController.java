package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.CubeFxApp;
import ch.bziswiler.cube.controller.datepicker.DateTimePickerController;
import ch.bziswiler.cube.model.presentation.CubeEventModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDateTime;

public class CubeEventDurationController extends CubeEventControllerScaffold {

    @FXML
    private Label startDateTimeLabel;
    @FXML
    private Label endDateTimeLabel;
    @FXML
    private Label durationLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button endButton;
    @FXML
    private Label name;

    @Override
    protected void doInitialize() {
        // nop
    }

    @Override
    protected void disposeModel(CubeEventModel oldValue) {
        this.endButton.disableProperty().unbind();
        this.startButton.disableProperty().unbind();
        this.startDateTimeLabel.textProperty().unbind();
        this.endDateTimeLabel.textProperty().unbindBidirectional(oldValue.endProperty());
        this.durationLabel.textProperty().unbind();
        this.name.textProperty().unbind();
    }

    @Override
    protected void initializeModel(CubeEventModel newValue) {

        this.endButton.disableProperty().bind(getModel().endButtonDisabledProperty());
        this.startButton.disableProperty().bind(getModel().startButtonDisabledProperty());

        final StringBinding startBinding = Bindings.createStringBinding(new LocalDateTimeToStringCallable(newValue.startProperty()), newValue.startProperty());
        final StringBinding endBinding = Bindings.createStringBinding(new LocalDateTimeToStringCallable(newValue.endProperty()), newValue.endProperty());

        this.startDateTimeLabel.textProperty().bind(startBinding);
        this.endDateTimeLabel.textProperty().bind(endBinding);

        final StringBinding hoursBinding = Bindings.createStringBinding(new DurationHoursCallable(newValue), newValue.hoursOfEventDurationProperty());
        final StringBinding minutesBinding = Bindings.createStringBinding(new DurationMinutesCallable(newValue), newValue.minutesOfEventDurationProperty());
        this.durationLabel.textProperty().bind(Bindings.concat(hoursBinding, minutesBinding));

        this.name.textProperty().bind(newValue.nameProperty());
    }

    @FXML
    public void handleStartButtonClicked() {
        getModel().setStart(LocalDateTime.now());
    }

    @FXML
    public void handleEndButtonClicked() {
        getModel().setEnd(LocalDateTime.now());
    }

    @FXML
    public void handleEditStartDateTimeButtonClicked() throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        final Stage dialog = loadModalDateTimePickerDialog(loader);
        final DateTimePickerController controller = loader.getController();
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime end = getModel().getEnd();
        final LocalDateTime start = getModel().getStart();
        controller.setNotAfter(end != null ? end : now); // FIXME use earliest check-in date!
        controller.setNotBefore(LocalDateTime.MIN);
        controller.setDialogStage(dialog);
        controller.setInitialDateTime(start != null ? start : now);
        dialog.showAndWait();
        if (controller.isOk()) {
            getModel().setStart(controller.getPickedDateTime());
        }
    }

    private Stage loadModalDateTimePickerDialog(FXMLLoader loader) throws IOException {
        loader.setLocation(getClass().getResource("../view/dateTimePicker.fxml"));
        final Region page = loader.load();
        final Stage dialogStage = new Stage();
        dialogStage.setTitle("Choose Date and Time");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initStyle(StageStyle.DECORATED);
        dialogStage.initOwner(CubeFxApp.getInstance().getPrimaryStage());
        dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
        final Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        return dialogStage;
    }

    @FXML
    public void handleEditEndDateTimeButtonClicked() throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        final Stage dialog = loadModalDateTimePickerDialog(loader);
        final DateTimePickerController controller = loader.getController();
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime end = getModel().getEnd();
        final LocalDateTime start = getModel().getStart();
        controller.setNotAfter(LocalDateTime.MAX);
        controller.setNotBefore(start != null ? start : now); // FIXME use latest check-out date!
        controller.setDialogStage(dialog);
        controller.setInitialDateTime(end != null ? end : now);
        // Show the dialog and wait until the user closes it
        dialog.showAndWait();
        if (controller.isOk()) {
            getModel().setEnd(controller.getPickedDateTime());
        }
    }

}
