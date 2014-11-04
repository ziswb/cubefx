package ch.bziswiler.cube.controller.datepicker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;

class MinutePickerGuard implements ChangeListener<Integer> {

    private final DatePicker datePicker;
    private final ComboBox<Integer> hourPicker;
    private final ComboBox<Integer> minutePicker;
    private final Model model;

    public MinutePickerGuard(DatePicker datePicker, ComboBox<Integer> hourPicker, ComboBox<Integer> minutePicker, Model model) {
        this.datePicker = datePicker;
        this.hourPicker = hourPicker;
        this.minutePicker = minutePicker;
        this.model = model;
    }

    @Override
    public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
        if (isPickedMinuteAfterThreshold()) {
            minutePicker.setValue(model.getNotAfter().getMinute());
            return;
        } else if (isPickedMinuteBeforeThreshold()) {
            minutePicker.setValue(model.getNotBefore().getMinute());
            return;
        }
        model.setPickedMinute(minutePicker.getValue());
    }

    private boolean isPickedMinuteBeforeThreshold() {
        final LocalDate pickedDate = datePicker.getValue();
        final Integer pickedHour = hourPicker.getValue();
        final Integer pickedMinute = minutePicker.getValue();
        final LocalDateTime notBefore = model.getNotBefore();
        return pickedDate.isEqual((notBefore.toLocalDate())) && pickedHour <= notBefore.getHour() && pickedMinute <= notBefore.getMinute();
    }

    private boolean isPickedMinuteAfterThreshold() {
        final LocalDate pickedDate = datePicker.getValue();
        final Integer pickedHour = hourPicker.getValue();
        final Integer pickedMinute = minutePicker.getValue();
        final LocalDateTime notAfter = model.getNotAfter();
        return pickedDate.isEqual(notAfter.toLocalDate()) && pickedHour >= notAfter.getHour() && pickedMinute >= notAfter.getMinute();
    }
}
