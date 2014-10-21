package ch.bziswiler.cube.model.event;

import ch.bziswiler.cube.model.address.Address;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

import java.util.List;

public class Event {


    private ListProperty<Visit> youthMemberVisits;
    private ListProperty<Visit> youthStaffVisits;
    private ListProperty<Visit> adultStaffVisits;
    private ListProperty<Visit> driverVisits;
    private ObjectProperty<Address> address;

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
}
