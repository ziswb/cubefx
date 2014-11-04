package ch.bziswiler.cube.view;

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public abstract class HighlightningLabelCellFactory<E, T> implements Callback<TableColumn<E, T>, TableCell<E, T>> {

    public TableCell<E, T> call(TableColumn<E, T> column) {

        final Label label = new Label();

        final TableCell<E, T> cell = new HighlightningCell<E, T>(label) {
            @Override
            protected void setText(T value) {
                setLabelText(getLabel(), value);
            }

        };

        cell.setGraphic(label);
        return cell;
    }

    protected abstract void setLabelText(Label label, T value);
}
