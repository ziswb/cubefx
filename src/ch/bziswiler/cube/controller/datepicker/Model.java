package ch.bziswiler.cube.controller.datepicker;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

class Model {

    private ObjectProperty<LocalDateTime> notBefore = new SimpleObjectProperty<>(LocalDateTime.MIN);
    private ObjectProperty<LocalDateTime> notAfter = new SimpleObjectProperty<>(LocalDateTime.MAX);
    private ObjectProperty<LocalDate> pickedDate = new SimpleObjectProperty<>(LocalDate.now());
    private ObjectProperty<Integer> pickedHour = new SimpleObjectProperty<>(1);
    private ObjectProperty<Integer> pickedMinute = new SimpleObjectProperty<>(1);

    public final LocalDateTime getNotBefore() {
        return notBeforeProperty().get();
    }

    public final void setNotBefore(LocalDateTime notBefore) {
        notBeforeProperty().set(notBefore);
    }

    public ObjectProperty<LocalDateTime> notBeforeProperty() {
        return this.notBefore;
    }

    public final LocalDateTime getNotAfter() {
        return notAfterProperty().get();
    }

    public final void setNotAfter(LocalDateTime notAfter) {
        notAfterProperty().set(notAfter);
    }

    public ObjectProperty<LocalDateTime> notAfterProperty() {
        return this.notAfter;
    }

    public ObjectProperty<Integer> pickedHourProperty() {
        return this.pickedHour;
    }

    public ObjectProperty<Integer> pickedMinuteProperty() {
        return this.pickedMinute;
    }

    public ObjectProperty<LocalDate> pickedDateProperty() {
        return this.pickedDate;
    }

    public final Integer getPickedHour() {
        return this.pickedHour.get();
    }

    public final void setPickedHour(Integer pickedHour) {
        this.pickedHour.set(pickedHour);
    }

    public final Integer getPickedMinute() {
        return this.pickedMinute.get();
    }

    public final void setPickedMinute(Integer pickedMinute) {
        this.pickedMinute.set(pickedMinute);
    }

    public final LocalDate getPickedDate() {
        return this.pickedDate.get();
    }

    public final void setPickedDate(LocalDate pickedDate) {
        this.pickedDate.set(pickedDate);
    }
}
