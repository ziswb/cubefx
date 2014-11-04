package ch.bziswiler.cube.view;

import ch.bziswiler.cube.model.event.Visit;
import javafx.scene.control.Label;

public class LabelCellFactory extends HighlightningLabelCellFactory<Visit, String> {

    @Override
    protected void setLabelText(Label label, String value) {
        label.setText(value);
    }
}
