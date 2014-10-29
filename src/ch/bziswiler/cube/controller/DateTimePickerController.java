package ch.bziswiler.cube.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Function;

public class DateTimePickerController {

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
        this.datePicker.setDayCellFactory(new DatePickerDateCellFactory());
    }

    private void initializeHourPicker() {
        this.hourPicker.valueProperty().bindBidirectional(model.pickedHourProperty());
        this.hourPicker.setConverter(new IntegerStringConverter());
        for (int i = 1; i <= 24; i++) {
            this.hourPicker.getItems().add(i);
        }
        this.hourPicker.setCellFactory(new ConditionalListViewCellFactory<Integer>(new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer item) {
                final LocalDateTime notAfter = model.getNotAfter();
                final LocalDate pickedDate = datePicker.getValue();
                return item != null && pickedDate.isEqual(notAfter.toLocalDate()) && item > notAfter.getHour();
            }
        }));
    }

    private void initializeMinutePicker() {
        this.minutePicker.valueProperty().bindBidirectional(model.pickedMinuteProperty());
        this.minutePicker.setConverter(new IntegerStringConverter());
        for (int i = 1; i <= 60; i++) {
            this.minutePicker.getItems().add(i);
        }
        this.minutePicker.setCellFactory(new ConditionalListViewCellFactory<Integer>(new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer item) {
                final LocalDateTime notAfter = model.getNotAfter();
                final LocalDate pickedDate = datePicker.getValue();
                return item != null && pickedDate.isEqual(notAfter.toLocalDate()) && item >= notAfter.getMinute();
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

    private static class Model {

        private ObjectProperty<LocalDateTime> notBefore = new SimpleObjectProperty<>(LocalDateTime.MIN);
        private ObjectProperty<LocalDateTime> notAfter = new SimpleObjectProperty<>(LocalDateTime.MAX);
        private ObjectProperty<LocalDate> pickedDate = new SimpleObjectProperty<>(LocalDate.now());
        private ObjectProperty<Integer> pickedHour = new SimpleObjectProperty<>(1);
        private ObjectProperty<Integer> pickedMinute = new SimpleObjectProperty<>(1);

        public final LocalDateTime getNotBefore() {
            return notBeforeProperty().get();
        }

        public final void setNotBefore(LocalDateTime notBefore) {
            notBeforeProperty().set(notBefore);
        }

        public ObjectProperty<LocalDateTime> notBeforeProperty() {
            return this.notBefore;
        }

        public final LocalDateTime getNotAfter() {
            return notAfterProperty().get();
        }

        public final void setNotAfter(LocalDateTime notAfter) {
            notAfterProperty().set(notAfter);
        }

        public ObjectProperty<LocalDateTime> notAfterProperty() {
            return this.notAfter;
        }

        public ObjectProperty<Integer> pickedHourProperty() {
            return this.pickedHour;
        }

        public ObjectProperty<Integer> pickedMinuteProperty() {
            return this.pickedMinute;
        }

        public ObjectProperty<LocalDate> pickedDateProperty() {
            return this.pickedDate;
        }

        public final Integer getPickedHour() {
            return this.pickedHour.get();
        }

        public final void setPickedHour(Integer pickedHour) {
            this.pickedHour.set(pickedHour);
        }

        public final Integer getPickedMinute() {
            return this.pickedMinute.get();
        }

        public final void setPickedMinute(Integer pickedMinute) {
            this.pickedMinute.set(pickedMinute);
        }

        public final LocalDate getPickedDate() {
            return this.pickedDate.get();
        }

        public final void setPickedDate(LocalDate pickedDate) {
            this.pickedDate.set(pickedDate);
        }
    }

    private class ConditionalListViewCellFactory<T> implements Callback<ListView<T>, ListCell<T>> {

        private final Function<T, Boolean> condition;

        private ConditionalListViewCellFactory(Function<T, Boolean> condition) {
            this.condition = condition;
        }

        @Override
        public ListCell<T> call(ListView<T> param) {
            return new ListCell<T>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(false);
                    setText(String.valueOf(item));
                    if (getStyle().contains("-fx-background-color: lightgray;")) {
                        setStyle(getStyle().replace("-fx-background-color: lightgray;", ""));
                    }
                    if (condition.apply(item)) {
                        setDisable(true);
                        setStyle("-fx-background-color: lightgray;");
                    }
                }
            };
        }
    }

    private class DatePickerDateCellFactory implements Callback<DatePicker, DateCell> {
        @Override
        public DateCell call(final DatePicker datePicker) {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item.isBefore(model.getNotBefore().toLocalDate()) || item.isAfter(model.getNotAfter().toLocalDate())) {
                        setDisable(true);
                        setStyle("-fx-background-color: lightgray;");
                    }
                }
            };
        }
    }
}
