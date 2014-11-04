package ch.bziswiler.cube.view;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.util.Duration;

public abstract class HighlightningCell<E, T> extends TableCell<E, T> {

    private final static Duration TIME_TO_GET_OLD = Duration.seconds(1.5);
    private final Label label;

    public HighlightningCell(Label label) {
        this.label = label;
    }

    protected abstract void setText(T value);

    protected Label getLabel() {
        return this.label;
    }

    protected void updateItem(T value, boolean empty) {
        super.updateItem(value, empty);
        label.setVisible(!empty);
        if (!empty && value != null) {
            setText(value);
            getStyleClass().add("new");
            PauseTransition agingTime = new PauseTransition(TIME_TO_GET_OLD);
            agingTime.setCycleCount(1);
            agingTime.play();
            agingTime.setOnFinished(event -> getStyleClass().remove("new"));
        }
    }
}
