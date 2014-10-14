package ch.bziswiler.cube.model.event;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Duration {

    private ObjectProperty<LocalDateTime> start;
    private ObjectProperty<LocalDateTime> end;

    public final void setStart(LocalDateTime start) {
        startProperty().set(start);
    }

    public ObjectProperty<LocalDateTime> startProperty() {
        if (start == null) {
            start = new SimpleObjectProperty<>();
        }
        return start;
    }

    public final void setEnd(LocalDateTime end) {
        endProperty().set(end);
    }

    public ObjectProperty<LocalDateTime> endProperty() {
        if (end == null) {
            end = new SimpleObjectProperty<>();
        }
        return end;
    }

    public final long getYears() {
        return startProperty().get().until(endProperty().get(), ChronoUnit.YEARS);
    }

    public final long getMonths() {
        return startProperty().get().until(endProperty().get(), ChronoUnit.MONTHS);
    }

    public final long getWeeks() {
        return startProperty().get().until(endProperty().get(), ChronoUnit.WEEKS);
    }

    public final long getDays() {
        return startProperty().get().until(endProperty().get(), ChronoUnit.DAYS);
    }

    public final long getHours() {
        return startProperty().get().until(endProperty().get(), ChronoUnit.HOURS);
    }

    public final long getMinutes() {
        return startProperty().get().until(endProperty().get(), ChronoUnit.MINUTES);
    }
}
