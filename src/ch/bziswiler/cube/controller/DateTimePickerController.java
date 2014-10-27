package ch.bziswiler.cube.controller;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimePickerController {

    private Stage dialogStage;
    private LocalDateTime notBefore = LocalDateTime.MIN;
    private LocalDateTime notAfter = LocalDateTime.MAX;
    private boolean ok = false;
    private LocalDate pickedDate = LocalDate.now();
    private LocalTime pickedTime = LocalTime.now();

    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Integer> hourPicker;
    @FXML
    private ComboBox<Integer> minutePicker;

    public void setNotBefore(LocalDateTime notBefore) {
        if (notBefore == null) {
            this.notBefore = LocalDateTime.MIN;
        } else {
            this.notBefore = notBefore;
        }
    }

    public void setNotAfter(LocalDateTime notAfter) {
        if (notAfter == null) {
            this.notAfter = LocalDateTime.MAX;
        } else {
            this.notAfter = notAfter;
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(notBefore.toLocalDate()) || item.isAfter(notAfter.toLocalDate())) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: lightgray;");
                                }
                            }
                        };
                    }
                };
        datePicker.setDayCellFactory(dayCellFactory);
        datePicker.setValue(LocalDate.now());
        if (this.notAfter != null) {
            final LocalTime time = this.notAfter.toLocalTime();
            time.getHour();
        }
        hourPicker.getItems().addAll(new SimpleListProperty<>(FXCollections.observableArrayList()));
        for (int i = 1; i <= 24; i++) {
            hourPicker.getItems().add(i);
        }
        hourPicker.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {
            @Override
            public ListCell<Integer> call(ListView<Integer> param) {
                return new ListCell<Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(false);
                        setText(String.valueOf(item));
                        if (getStyle().contains("-fx-background-color: lightgray;")) {
                            setStyle(getStyle().replace("-fx-background-color: lightgray;", ""));
                        }
                        if (item != null && datePicker.getValue().isEqual(notAfter.toLocalDate()) && item > notAfter.getHour()) {
                            setDisable(true);
                            setStyle("-fx-background-color: lightgray;");
                        }
                    }
                };
            }
        });
        minutePicker.getItems().addAll(new SimpleListProperty<>(FXCollections.observableArrayList()));
        for (int i = 1; i <= 60; i++) {
            minutePicker.getItems().add(i);
        }
        minutePicker.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {
            @Override
            public ListCell<Integer> call(ListView<Integer> param) {
                return new ListCell<Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(false);
                        setText(String.valueOf(item));
                        if (getStyle().contains("-fx-background-color: lightgray;")) {
                            setStyle(getStyle().replace("-fx-background-color: lightgray;", ""));
                        }
                        if (item != null && datePicker.getValue().isEqual(notAfter.toLocalDate()) && item >= notAfter.getMinute()) {
                            setDisable(true);
                            setStyle("-fx-background-color: lightgray;");
                        }
                    }
                };
            }
        });
    }

    public LocalDateTime getPickedDateTime() {
        return LocalDateTime.of(this.pickedDate, this.pickedTime);
    }

    @FXML
    private void handleOk() {
        this.pickedDate = this.datePicker.getValue();
        final Object selectedHours = this.hourPicker.getSelectionModel().getSelectedItem();
        final Object selectedMinutes = this.minutePicker.getSelectionModel().getSelectedItem();
        this.pickedTime = LocalTime.of(Integer.valueOf(selectedHours.toString()), Integer.valueOf(selectedMinutes.toString()));
        this.ok = true;
        this.dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        this.ok = false;
        dialogStage.close();
    }

    public boolean isOk() {
        return ok;
    }
}
