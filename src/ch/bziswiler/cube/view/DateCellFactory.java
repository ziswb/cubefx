package ch.bziswiler.cube.view;

import ch.bziswiler.cube.model.event.Visit;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.format.FormatStyle;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;

public class DateCellFactory extends HighlightningLabelCellFactory<Visit, LocalDateTime> {

    @Override
    protected void setLabelText(Label label, LocalDateTime value) {
        final String formattedDateTime = ofLocalizedDateTime(FormatStyle.SHORT).format(value);
        label.setText(formattedDateTime);
    }
}
