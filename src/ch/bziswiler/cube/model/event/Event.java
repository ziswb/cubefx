package ch.bziswiler.cube.model.event;

import ch.bziswiler.cube.model.address.Address;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import java.time.LocalDateTime;
import java.util.List;

public class Event implements Comparable<Event> {


    private StringProperty name;
    private ListProperty<Visit> youthMemberVisits;
    private ListProperty<Visit> youthStaffVisits;
    private ListProperty<Visit> adultStaffVisits;
    private ListProperty<Visit> driverVisits;
    private ObjectProperty<Address> address;
    private ObjectProperty<LocalDateTime> start;
    private ObjectProperty<LocalDateTime> end;

    public final String getName() {
        return nameProperty().get();
    }

    public StringProperty nameProperty() {
        if (this.name == null) {
            this.name = new SimpleStringProperty();
        }
        return this.name;
    }

    public final void setName(String name) {
        nameProperty().set(name);
    }

    public final Address geAddress() {
        return addressProperty().get();
    }

    public ObjectProperty<Address> addressProperty() {
        if (this.address == null) {
            this.address = new SimpleObjectProperty<>();
        }
        return this.address;
    }

    public final void setAddress(Address address) {
        addressProperty().set(address);
    }

    public final List<Visit> getYouthMemberVisits() {
        return youthMemberVisitsProperty().get();
    }

    public ListProperty<Visit> youthMemberVisitsProperty() {
        if (this.youthMemberVisits == null) {
            this.youthMemberVisits = new SimpleListProperty<>(FXCollections.observableArrayList());
        }
        return this.youthMemberVisits;
    }

    public final void setYouthMemberVisits(List<Visit> visits) {
        youthMemberVisitsProperty().setAll(visits);
    }

    public final void addYouthMemberVisit(Visit visit) {
        youthMemberVisitsProperty().add(visit);
    }

    public final List<Visit> getYouthStaffVisits() {
        return youthStaffVisitsProperty().get();
    }

    public ListProperty<Visit> youthStaffVisitsProperty() {
        if (this.youthStaffVisits == null) {
            this.youthStaffVisits = new SimpleListProperty<>(FXCollections.observableArrayList());
        }
        return this.youthStaffVisits;
    }

    public final void setYouthStaffVisits(List<Visit> visits) {
        youthStaffVisitsProperty().setAll(visits);
    }

    public final void addYouthStaffVisit(Visit visit) {
        youthStaffVisitsProperty().add(visit);
    }

    public final List<Visit> getAdultStaffVisits() {
        return adultStaffVisitsProperty().get();
    }

    public ListProperty<Visit> adultStaffVisitsProperty() {
        if (this.adultStaffVisits == null) {
            this.adultStaffVisits = new SimpleListProperty<>(FXCollections.observableArrayList());
        }
        return this.adultStaffVisits;
    }

    public final void setAdultStaffVisits(List<Visit> visits) {
        adultStaffVisitsProperty().setAll(visits);
    }

    public final void addAdultStaffVisit(Visit visit) {
        adultStaffVisitsProperty().add(visit);
    }

    public final List<Visit> getDriverVisits() {
        return driverVisitsProperty().get();
    }

    public ListProperty<Visit> driverVisitsProperty() {
        if (this.driverVisits == null) {
            this.driverVisits = new SimpleListProperty<>(FXCollections.observableArrayList());
        }
        return this.driverVisits;
    }

    public final void setDriverVisits(List<Visit> visits) {
        driverVisitsProperty().setAll(visits);
    }

    public final void addDriverVisit(Visit visit) {
        driverVisitsProperty().add(visit);
    }

    @Override
    public String toString() {
        return "From: " + getStart() + ", To: " + getEnd();
    }

    public final LocalDateTime getStart() {
        return startProperty().get();
    }

    public final void setStart(LocalDateTime start) {
        startProperty().set(start);
    }

    public ObjectProperty<LocalDateTime> startProperty() {
        if (this.start == null) {
            this.start = new SimpleObjectProperty<>();
        }
        return this.start;
    }

    public final LocalDateTime getEnd() {
        return endProperty().get();
    }

    public final void setEnd(LocalDateTime end) {
        endProperty().set(end);
    }

    public ObjectProperty<LocalDateTime> endProperty() {
        if (this.end == null) {
            this.end = new SimpleObjectProperty<>();
        }
        return this.end;
    }

    @Override
    public int compareTo(Event event) {
        if (startProperty().get() == null) {
            if (event.startProperty().get() == null) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (event.startProperty().get() == null) {
                return 1;
            }
        }
        return startProperty().get().compareTo(event.startProperty().get());
    }
}
