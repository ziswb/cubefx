package ch.bziswiler.cube.controller.datepicker;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.function.Function;

class DatePickerDateCellFactory implements Callback<DatePicker, DateCell> {

    private final Function<LocalDate, Boolean> condition;

    DatePickerDateCellFactory(Function<LocalDate, Boolean> condition) {
        this.condition = condition;
    }

    @Override
    public DateCell call(final DatePicker datePicker) {
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (condition.apply(item)) {
                    setDisable(true);
                    setStyle(DateTimePickerController.DISABLED_BACKGROUND_COLOR);
                }
            }
        };
    }
}
