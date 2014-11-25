package ch.bziswiler.cube.controller;

import javafx.beans.property.ObjectProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.Callable;

public class LocalDateTimeToStringCallable implements Callable<String> {

    private final ObjectProperty<LocalDateTime> property;

    public LocalDateTimeToStringCallable(ObjectProperty<LocalDateTime> property) {
        this.property = property;
    }

    @Override
    public String call() throws Exception {
        if (property.get() == null) {
            return "--.--.---- --:--";
        }
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(property.get());
    }
}
