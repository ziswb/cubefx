package ch.bziswiler.cube.controller.datepicker;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

class DatePickerGuard extends PickerGuardScaffold {

    public DatePickerGuard(DatePicker datePicker, ComboBox<Integer> hourPicker, Model model) {
        super(datePicker, model, hourPicker);
    }

    @Override
    public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
        if (isPickedHourAfterThreshold()) {
            setHourPickerToNotAfterThreshold();
            return;
        } else if (isPickedHourBeforeThreshold()) {
            setHourPickerToNotBeforeThreshold();
            return;
        }
        setModelHourToPickerValue();
    }
}
