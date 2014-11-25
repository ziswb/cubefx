package ch.bziswiler.cube.model.address;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class City {

    private StringProperty name;
    private StringProperty zip;
    private StringProperty state;
    private StringProperty country;

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
        this.nameProperty().set(name);
    }

    public final String getZip() {
        return zipProperty().get();
    }

    public StringProperty zipProperty() {
        if (this.zip == null) {
            this.zip = new SimpleStringProperty();
        }
        return this.zip;
    }

    public final void setZip(String zip) {
        this.zipProperty().set(zip);
    }

    public final String getState() {
        return stateProperty().get();
    }

    public StringProperty stateProperty() {
        if (this.state == null) {
            this.state = new SimpleStringProperty();
        }
        return this.state;
    }

    public final void setState(String state) {
        this.stateProperty().set(state);
    }

    public final String getCountry() {
        return countryProperty().get();
    }

    public StringProperty countryProperty() {
        if (this.country == null) {
            this.country = new SimpleStringProperty();
        }
        return this.country;
    }

    public final void setCountry(String country) {
        this.countryProperty().set(country);
    }

    @Override
    public int hashCode() {
        int result = nameProperty().get() != null ? nameProperty().get().hashCode() : 0;
        result = 31 * result + (zipProperty().get() != null ? zipProperty().get().hashCode() : 0);
        result = 31 * result + (stateProperty().get() != null ? stateProperty().get().hashCode() : 0);
        result = 31 * result + (countryProperty().get() != null ? countryProperty().get().hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final City city = (City) o;
        if (countryProperty().get() != null ? !countryProperty().get().equals(city.countryProperty().get()) : city.countryProperty().get() != null) {
            return false;
        }
        if (nameProperty().get() != null ? !nameProperty().get().equals(city.nameProperty().get()) : city.nameProperty().get() != null) {
            return false;
        }
        if (stateProperty().get() != null ? !stateProperty().get().equals(city.stateProperty().get()) : city.stateProperty().get() != null) {
            return false;
        }
        if (zipProperty().get() != null ? !zipProperty().get().equals(city.zipProperty().get()) : city.zipProperty().get() != null) {
            return false;
        }
        return true;
    }
}
