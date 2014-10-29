package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.CubeFxApp;
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
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.Callable;

public class CubeEventDurationController extends EventControllerScaffold {

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

    @Override
    protected void dispose(CubeEventModel oldValue) {
        this.endButton.disableProperty().unbind();
        this.startButton.disableProperty().unbind();
        this.startDateTimeLabel.textProperty().unbindBidirectional(oldValue.startProperty());
        this.endDateTimeLabel.textProperty().unbindBidirectional(oldValue.endProperty());
        this.durationLabel.textProperty().unbind();
    }

    @Override
    protected void initialize(CubeEventModel newValue) {

        this.endButton.disableProperty().bind(getModel().endButtonDisabledProperty());
        this.startButton.disableProperty().bind(getModel().startButtonDisabledProperty());

        this.startDateTimeLabel.textProperty().bindBidirectional(newValue.startProperty(), new LocalDateTimeStringConverter());
        this.endDateTimeLabel.textProperty().bindBidirectional(newValue.endProperty(), new LocalDateTimeStringConverter());

        final StringBinding hoursBinding = Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                final Long hours = newValue.hoursOfEventDurationProperty().get();
                String result = "";
                if (hours == null) {
                    result = "0";
                } else {
                    result = hours.toString();
                }
                return result + "h ";
            }
        }, newValue.hoursOfEventDurationProperty());
        final StringBinding minutesBinding = Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                final Long minutes = newValue.minutesOfEventDurationProperty().get();
                String result = "";
                if (minutes == null) {
                    result = "0";
                } else {
                    result = minutes.toString();
                }
                return result + " min";
            }
        }, newValue.minutesOfEventDurationProperty());
        this.durationLabel.textProperty().bind(Bindings.concat(hoursBinding, minutesBinding));
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
        // Show the dialog and wait until the user closes it
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

    private static class LocalDateTimeStringConverter extends StringConverter<LocalDateTime> {

        @Override
        public String toString(LocalDateTime object) {
            if (object == null) {
                return "--.--.---- --:--";
            }
            return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(object);
        }

        @Override
        public LocalDateTime fromString(String string) {
            if (string == null) {
                return null;
            }
            return LocalDateTime.from(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).parse(string));
        }
    }
}
