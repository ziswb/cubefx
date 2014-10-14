package ch.bziswiler.cube.model.address;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Address {

    private StringProperty streetName = new SimpleStringProperty();
    private StringProperty addition = new SimpleStringProperty();
    private ObjectProperty<City> city = new SimpleObjectProperty<City>();

    public final String getStreetName() {
        return streetName.get();
    }

    public final void setStreetName(String streetName) {
        streetNameProperty().set(streetName);
    }

    private StringProperty streetNameProperty() {
        if (this.streetName == null) {
            this.streetName = new SimpleStringProperty();
        }
        return this.streetName;
    }

    public final String getAddition() {
        return addition.get();
    }

    public final void setAddition(String addition) {
        additionProperty().set(addition);
    }

    private StringProperty additionProperty() {
        if (this.addition == null) {
            this.addition = new SimpleStringProperty();
        }
        return this.addition;
    }

    public final City getCity() {
        return cityProperty().get();
    }

    public final void setCity(City city) {
        cityProperty().set(city);
    }

    public ObjectProperty<City> cityProperty() {
        if (this.city == null) {
            this.city = new SimpleObjectProperty<City>();
        }
        return this.city;
    }
}
