package ch.bziswiler.cube.controller.datepicker;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.function.Function;

class ConditionalListViewCellFactory<T> implements Callback<ListView<T>, ListCell<T>> {

    private final Function<T, Boolean> condition;

    ConditionalListViewCellFactory(Function<T, Boolean> condition) {
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
                if (getStyle().contains(DateTimePickerController.DISABLED_BACKGROUND_COLOR)) {
                    setStyle(getStyle().replace(DateTimePickerController.DISABLED_BACKGROUND_COLOR, ""));
                }
                if (condition.apply(item)) {
                    setDisable(true);
                    setStyle(DateTimePickerController.DISABLED_BACKGROUND_COLOR);
                }
            }
        };
    }
}
