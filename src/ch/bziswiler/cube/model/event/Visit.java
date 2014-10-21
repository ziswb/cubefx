package ch.bziswiler.cube.model.event;

import ch.bziswiler.cube.model.person.Person;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDateTime;

public class Visit {

    private ObjectProperty<Person> person;
    private ObjectProperty<LocalDateTime> checkIn;
    private ObjectProperty<LocalDateTime> checkOut;

    public final Person getPerson() {
        return personProperty().get();
    }

    public ObjectProperty<Person> personProperty() {
        if (this.person == null) {
            this.person = new SimpleObjectProperty<>();
        }
        return this.person;
    }

    public final void setPerson(Person person) {
        personProperty().set(person);
    }

    public final LocalDateTime getCheckIn() {
        return checkInProperty().get();
    }

    public ObjectProperty<LocalDateTime> checkInProperty() {
        if (this.checkIn == null) {
            this.checkIn = new SimpleObjectProperty<>();
        }
        return this.checkIn;
    }

    public final void setCheckIn(LocalDateTime dateTime) {
        checkInProperty().set(dateTime);
    }

    public final LocalDateTime getCheckOut() {
        return checkOutProperty().get();
    }

    public ObjectProperty<LocalDateTime> checkOutProperty() {
        if (this.checkOut == null) {
            this.checkOut = new SimpleObjectProperty<>();
        }
        return this.checkOut;
    }

    public final void setCheckOut(LocalDateTime dateTime) {
        checkOutProperty().set(dateTime);
    }
}
