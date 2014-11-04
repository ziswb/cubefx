package ch.bziswiler.cube.controller.datepicker;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class PickerGuardScaffold implements ChangeListener<LocalDate> {

    protected final DatePicker datePicker;
    protected final ComboBox<Integer> hourPicker;
    protected final Model model;

    public PickerGuardScaffold(DatePicker datePicker, Model model, ComboBox<Integer> hourPicker) {
        this.datePicker = datePicker;
        this.model = model;
        this.hourPicker = hourPicker;
    }

    protected boolean isPickedHourAfterThreshold() {
        final LocalDate pickedDate = datePicker.getValue();
        final Integer pickedHour = hourPicker.getValue();
        final LocalDateTime notAfter = model.getNotAfter();
        return pickedDate.isEqual(notAfter.toLocalDate()) && pickedHour >= notAfter.getHour();
    }

    protected boolean isPickedHourBeforeThreshold() {
        final LocalDate pickedDate = datePicker.getValue();
        final Integer pickedHour = hourPicker.getValue();
        final LocalDateTime notBefore = model.getNotBefore();
        return pickedDate.isEqual(notBefore.toLocalDate()) && pickedHour <= notBefore.getHour();
    }

    protected void setHourPickerToNotBeforeThreshold() {
        hourPicker.setValue(model.getNotBefore().getHour());
    }

    protected void setHourPickerToNotAfterThreshold() {
        hourPicker.setValue(model.getNotAfter().getHour());
    }

    protected void setModelHourToPickerValue() {
        model.setPickedHour(hourPicker.getValue());
    }
}
