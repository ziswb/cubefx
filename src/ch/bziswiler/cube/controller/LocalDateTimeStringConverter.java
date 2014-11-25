package ch.bziswiler.cube.controller;

import javafx.util.StringConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class LocalDateTimeStringConverter extends StringConverter<LocalDateTime> {

    @Override
    public String toString(LocalDateTime object) {
        if (object == null) {
            return "--.--.---- --:--";
        }
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(object);
    }

    @Override
    public LocalDateTime fromString(String string) {
        if (string == null) {
            return null;
        }
        return LocalDateTime.from(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).parse(string));
    }
}
