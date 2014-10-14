package ch.bziswiler.cube.model.event;

import ch.bziswiler.cube.model.Decorator;
import ch.bziswiler.cube.model.person.Person;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

import java.util.Collection;

public class PersonMetaDataDecorator implements Decorator<Person> {

    private ListProperty<PersonRole> assignableRoles;
    private SimpleObjectProperty<PersonRole> currentRole;

    public ListProperty<PersonRole> assignableRolesProperty() {
        if (this.assignableRoles == null) {
            this.assignableRoles = new SimpleListProperty<>(FXCollections.observableArrayList());
        }
        return this.assignableRoles;
    }

    public SimpleObjectProperty<PersonRole> currentRoleProperty() {
        if (this.currentRole == null) {
            this.currentRole = new SimpleObjectProperty<>();
        }
        return this.currentRole;
    }

    public final PersonRole getCurrentRole() {
        return currentRoleProperty().get();
    }

    public final void setCurrentRole(PersonRole role) {
        currentRoleProperty().set(role);
    }

    public final Collection<PersonRole> getAssignableRoles() {
        return assignableRolesProperty().get();
    }

    public final void setAssignableRoles(Collection<PersonRole> assignableRoles) {
        assignableRolesProperty().setAll(assignableRoles);
    }
}
