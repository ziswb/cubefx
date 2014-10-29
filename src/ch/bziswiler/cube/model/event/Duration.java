package ch.bziswiler.cube.model.event;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;

public class Duration {

    private static final BigDecimal SIXTY = BigDecimal.valueOf(60);
    private ObjectProperty<LocalDateTime> start;
    private ObjectProperty<LocalDateTime> end;
    private ReadOnlyLongWrapper hours;
    private ReadOnlyLongWrapper minutes;

    public Duration() {
        bindHours();
        bindMinutes();
    }

    private void bindMinutes() {
        bindWrapperToStartAndEndProperties(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                final BigDecimal seconds = getDurationInSeconds();
                final Long result = seconds.divideAndRemainder(SIXTY)[0].divideAndRemainder(SIXTY)[1].longValue();
                return result;
            }
        }, minutesWrapper());
    }

    private void bindWrapperToStartAndEndProperties(Callable<Long> func, ReadOnlyLongWrapper wrapper) {
        wrapper.bind(Bindings.createLongBinding(func, endProperty(), startProperty()));
    }

    private void bindHours() {
        bindWrapperToStartAndEndProperties(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                final BigDecimal seconds = getDurationInSeconds();
                final long result = seconds.divideAndRemainder(SIXTY)[0].divideAndRemainder(SIXTY)[0].longValue();
                return result;
            }
        }, hoursWrapper());
    }

    private BigDecimal getDurationInSeconds() {
        long seconds = 0L;
        if (endProperty().get() != null && startProperty().get() != null) {
            java.time.Duration between = java.time.Duration.between(startProperty().get(), endProperty().get());
            seconds = between.getSeconds();
        }
        return BigDecimal.valueOf(seconds);
    }

    public final Long getHours() {
        return hoursProperty().get();
    }

    public ReadOnlyLongProperty hoursProperty() {
        return hoursWrapper().getReadOnlyProperty();
    }

    private ReadOnlyLongWrapper hoursWrapper() {
        if (this.hours == null) {
            this.hours = new ReadOnlyLongWrapper(0);
        }
        return this.hours;
    }

    public final Long getMinutes() {
        return minutesProperty().get();
    }

    public ReadOnlyLongProperty minutesProperty() {
        return minutesWrapper().getReadOnlyProperty();
    }

    private ReadOnlyLongWrapper minutesWrapper() {
        if (this.minutes == null) {
            this.minutes = new ReadOnlyLongWrapper(0);
        }
        return this.minutes;
    }

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
}
