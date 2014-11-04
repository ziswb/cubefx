package ch.bziswiler.cube.controller.datepicker;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Function;

public class DateTimePickerController {

    public static final String DISABLED_BACKGROUND_COLOR = "-fx-background-color: lightgray;";
    private Stage dialogStage;
    private boolean ok = false;
    private Model model;

    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Integer> hourPicker;
    @FXML
    private ComboBox<Integer> minutePicker;

    public void setNotBefore(LocalDateTime notBefore) {
        if (notBefore == null) {
            this.model.setNotBefore(LocalDateTime.MIN);
        } else {
            this.model.setNotBefore(notBefore);
        }
    }

    public void setNotAfter(LocalDateTime notAfter) {
        if (notAfter == null) {
            this.model.setNotAfter(LocalDateTime.MAX);
        } else {
            this.model.setNotAfter(notAfter);
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        this.model = new Model();
        initializeDatePicker();
        initializeHourPicker();
        initializeMinutePicker();
    }

    private void initializeDatePicker() {
        this.datePicker.valueProperty().bindBidirectional(model.pickedDateProperty());
        this.datePicker.setDayCellFactory(new DatePickerDateCellFactory(new Function<LocalDate, Boolean>() {
            @Override
            public Boolean apply(LocalDate item) {
                return item.isBefore(model.getNotBefore().toLocalDate()) || item.isAfter(model.getNotAfter().toLocalDate());
            }
        }));
        this.datePicker.valueProperty().addListener(new DatePickerGuard(this.datePicker, this.hourPicker, this.model));
    }

    private void initializeHourPicker() {
        this.hourPicker.valueProperty().bindBidirectional(model.pickedHourProperty());
        this.hourPicker.setConverter(new IntegerStringConverter());
        for (int i = 1; i < 24; i++) {
            this.hourPicker.getItems().add(i);
        }
        this.hourPicker.setCellFactory(new ConditionalListViewCellFactory<Integer>(new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer item) {
                final LocalDateTime notAfter = model.getNotAfter();
                final LocalDateTime notBefore = model.getNotBefore();
                final LocalDate pickedDate = datePicker.getValue();
                if (item == null) {
                    return false;
                }
                if (pickedDate.isEqual(notAfter.toLocalDate()) && item > notAfter.getHour()) {
                    return true;
                }
                if (pickedDate.isEqual(notBefore.toLocalDate()) && item < notBefore.getHour()) {
                    return true;
                }
                return false;
            }
        }));
        final MinutePickerGuard guard = new MinutePickerGuard(datePicker, hourPicker, minutePicker, model);
        this.hourPicker.valueProperty().addListener(guard);
    }

    private void initializeMinutePicker() {
        this.minutePicker.valueProperty().bindBidirectional(model.pickedMinuteProperty());
        this.minutePicker.setConverter(new IntegerStringConverter());
        for (int i = 1; i < 60; i++) {
            this.minutePicker.getItems().add(i);
        }
        this.minutePicker.setCellFactory(new ConditionalListViewCellFactory<Integer>(new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer item) {
                final LocalDateTime notAfter = model.getNotAfter();
                final LocalDateTime notBefore = model.getNotBefore();
                final LocalDate pickedDate = datePicker.getValue();
                final Integer pickedHour = hourPicker.getValue();
                if (item == null) {
                    return false;
                }
                if (pickedDate.isEqual(notAfter.toLocalDate()) && pickedHour == notAfter.getHour() && item > notAfter.getMinute()) {
                    return true;
                }
                if (pickedDate.isEqual(notBefore.toLocalDate()) && pickedHour == notBefore.getHour() && item < notBefore.getMinute()) {
                    return true;
                }
                return false;
            }
        }));
    }

    public LocalDateTime getPickedDateTime() {
        final LocalDate date = this.model.getPickedDate();
        final LocalTime time = LocalTime.of(this.model.getPickedHour(), this.model.getPickedMinute());
        return LocalDateTime.of(date, time);
    }

    @FXML
    private void handleOk() {
        this.ok = true;
        this.dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        this.ok = false;
        this.dialogStage.close();
    }

    public void setInitialDateTime(LocalDateTime initialDateTime) {
        this.model.setPickedDate(initialDateTime.toLocalDate());
        this.model.setPickedHour(initialDateTime.getHour());
        this.model.setPickedMinute(initialDateTime.getMinute());
    }

    public boolean isOk() {
        return this.ok;
    }

}
