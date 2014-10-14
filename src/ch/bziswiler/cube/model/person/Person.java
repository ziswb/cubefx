package ch.bziswiler.cube.model.person;

import ch.bziswiler.cube.model.Decoratable;
import ch.bziswiler.cube.model.Decorator;
import ch.bziswiler.cube.model.address.Address;
import com.google.common.collect.Lists;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class Person implements Decoratable<Person> {

    private StringProperty firstName;
    private StringProperty lastName;
    private ObjectProperty<Address> address;
    private ObjectProperty<PersonId> personId;
    private ObjectProperty<Gender> gender;
    private List<Decorator<Person>> decorators;

    public Person() {
        this.decorators = Lists.newArrayList();
    }

    public ObjectProperty<Gender> genderProperty() {
        if (this.gender == null) {
            this.gender = new SimpleObjectProperty<>();
        }
        return this.gender;
    }

    public StringProperty firstNameProperty() {
        if (this.firstName == null) {
            this.firstName = new SimpleStringProperty();
        }
        return this.firstName;
    }

    public ObjectProperty<PersonId> personIdProperty() {
        if (this.personId == null) {
            this.personId = new SimpleObjectProperty<>();
        }
        return this.personId;
    }

    public StringProperty lastNameProperty() {
        if (this.lastName == null) {
            this.lastName = new SimpleStringProperty();
        }
        return this.lastName;
    }

    public ObjectProperty<Address> addressProperty() {
        if (this.address == null) {
            this.address = new SimpleObjectProperty<>();
        }
        return this.address;
    }

    public final PersonId getPersonId() {
        return personIdProperty().get();
    }

    public final void setPersonId(PersonId personId) {
        personIdProperty().set(personId);
    }

    public final Gender getGender() {
        return genderProperty().get();
    }

    public final void setGender(Gender gender) {
        this.genderProperty().set(gender);
    }

    public final String getFirstName() {
        return this.firstNameProperty().get();
    }

    public final void setFirstName(String firstName) {
        this.firstNameProperty().set(firstName);
    }

    public final String getLastName() {
        return this.lastNameProperty().get();
    }

    public final void setLastName(String lastName) {
        this.lastNameProperty().set(lastName);
    }

    public final Address getAddress() {
        return this.addressProperty().get();
    }

    public final void setAddress(Address address) {
        this.addressProperty().set(address);
    }


    @Override
    @SuppressWarnings("unchecked")
    public <E extends Decorator<Person>> E getDecorator(Class<E> decorator) {
        for (Decorator<Person> d : this.decorators) {
            if (d.getClass().equals(decorator)) {
                return (E) d;
            }
        }
        return null;
    }

    @Override
    public <E extends Decorator<Person>> void addDecorator(E decorator) {
        this.decorators.add(decorator);
    }

    public static enum Gender {
        MALE,
        FEMALE
    }
}
