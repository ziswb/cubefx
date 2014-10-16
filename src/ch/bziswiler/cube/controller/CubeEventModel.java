package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.model.VisitsUtil;
import ch.bziswiler.cube.model.address.Address;
import ch.bziswiler.cube.model.event.Event;
import ch.bziswiler.cube.model.event.PersonMetaDataDecorator;
import ch.bziswiler.cube.model.event.PersonRole;
import ch.bziswiler.cube.model.event.Visit;
import ch.bziswiler.cube.model.person.Person;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

public class CubeEventModel {

    public enum Pane {
        STATS, YOUTH_MEMBERS, YOUTH_STAFF, ADULT_STAFF, DRIVERS
    }

    // Event Duration View
    private ObjectProperty<LocalDateTime> start;
    private ObjectProperty<LocalDateTime> end;
    private StringBinding durationBinding;
    private ObjectProperty<Address> address;

    // Event Numbers View
    private ReadOnlyIntegerWrapper numberOfCurrentlyCheckedInAdults;
    private ReadOnlyIntegerWrapper numberOfAdultVisits;
    private ReadOnlyIntegerWrapper numberOfCurrentlyCheckedInYouths;
    private ReadOnlyIntegerWrapper numberOfYouthVisits;
    private ReadOnlyIntegerWrapper totalNumberOfCheckedInPersons;
    private ReadOnlyIntegerWrapper totalNumberOfVisits;

    // Scan Member View
    private ReadOnlyBooleanWrapper addYouthMemberButtonEnabled;
    private ReadOnlyBooleanWrapper addYouthStaffButtonEnabled;
    private ReadOnlyBooleanWrapper addAdultStaffButtonEnabled;
    private ReadOnlyBooleanWrapper addDriverButtonEnabled;
    private ReadOnlyBooleanWrapper checkOutButtonEnabled;
    private ListProperty<Person> persons;
    private SimpleObjectProperty<Person> selectedPerson;
    private ReadOnlyStringWrapper selectedPersonName;
    private ReadOnlyStringWrapper selectedPersonStreet;
    private ReadOnlyStringWrapper selectedPersonCity;
    private ReadOnlyStringWrapper selectedPersonBirthDate;
    private ReadOnlyObjectWrapper<Image> portraitImage;

    // Table View
    private ListProperty<Visit> youthMemberVisits;
    private ListProperty<Visit> youthStaffVisits;
    private ListProperty<Visit> adultStaffVisits;
    private ListProperty<Visit> driverVisits;
    private ListChangeListener<Visit> youthMemberVisitsListener;
    private ReadOnlyObjectWrapper<Pane> expandedPane;

    private ObjectProperty<Event> event;
    private ChangeListener<Event> eventListChangeListener;
    private final EventStatistics stats;

    public CubeEventModel() {
        this.eventListChangeListener = new ChangeListener<Event>() {
            @Override
            public void changed(ObservableValue<? extends Event> observable, Event oldValue, Event newValue) {
                eventChanged(newValue, oldValue);
            }
        };
        // TODO resolve event dependency
        eventProperty().set(new Event());
        this.stats = new EventStatistics(this);
    }

    // Event Duration View

    private Callable<String> durationFunc = new Callable<String>() {
        @Override
        public String call() throws Exception {
            return "Not yet implemented";
        }
    };

    public StringBinding durationBinding() {
        if (durationBinding == null) {
            this.durationBinding = null;
            this.durationBinding = Bindings.createStringBinding(durationFunc, startProperty(), endProperty());
        }
        return this.durationBinding;
    }


    public ObjectProperty<LocalDateTime> startProperty() {
        if (this.start == null) {
            this.start = new SimpleObjectProperty<>();
        }
        return this.start;
    }

    public ObjectProperty<LocalDateTime> endProperty() {
        if (this.end == null) {
            this.end = new SimpleObjectProperty<>();
        }
        return this.end;
    }

    public ObjectProperty<Address> addressProperty() {
        if (this.address == null) {
            this.address = new SimpleObjectProperty<>();
        }
        return this.address;
    }

    public final Address geAddress() {
        return addressProperty().get();
    }

    public final void setAddress(Address address) {
        addressProperty().set(address);
    }

    public final StringBinding getDurationBinding() {
        return durationBinding();
    }

    // Event Numbers View

    // Scan Member View

    public ReadOnlyBooleanProperty addYouthMemberButtonEnabledProperty() {
        return addYouthMemberButtonEnabledWrapper().getReadOnlyProperty();
    }

    private ReadOnlyBooleanWrapper addYouthMemberButtonEnabledWrapper() {
        if (this.addYouthMemberButtonEnabled == null) {
            this.addYouthMemberButtonEnabled = new ReadOnlyBooleanWrapper();
        }
        return this.addYouthMemberButtonEnabled;
    }

    public ReadOnlyBooleanProperty addYouthStaffButtonEnabledProperty() {
        return addYouthStaffButtonEnabledWrapper().getReadOnlyProperty();
    }

    private ReadOnlyBooleanWrapper addYouthStaffButtonEnabledWrapper() {
        if (this.addYouthStaffButtonEnabled == null) {
            this.addYouthStaffButtonEnabled = new ReadOnlyBooleanWrapper();
        }
        return this.addYouthStaffButtonEnabled;
    }

    public ReadOnlyBooleanProperty addAdultStaffButtonEnabledProperty() {
        return this.addAdultStaffButtonEnabledWrapper().getReadOnlyProperty();
    }

    private ReadOnlyBooleanWrapper addAdultStaffButtonEnabledWrapper() {
        if (this.addAdultStaffButtonEnabled == null) {
            this.addAdultStaffButtonEnabled = new ReadOnlyBooleanWrapper();
        }
        return this.addAdultStaffButtonEnabled;
    }

    public ReadOnlyBooleanProperty addDriverButtonEnabledProperty() {
        return this.addDriverButtonEnabledWrapper().getReadOnlyProperty();
    }

    private ReadOnlyBooleanWrapper addDriverButtonEnabledWrapper() {
        if (this.addDriverButtonEnabled == null) {
            this.addDriverButtonEnabled = new ReadOnlyBooleanWrapper();
        }
        return this.addDriverButtonEnabled;
    }

    public ReadOnlyBooleanProperty checkOutButtonEnabledProperty() {
        return this.checkOutButtonEnabledWrapper().getReadOnlyProperty();
    }

    private ReadOnlyBooleanWrapper checkOutButtonEnabledWrapper() {
        if (this.checkOutButtonEnabled == null) {
            this.checkOutButtonEnabled = new ReadOnlyBooleanWrapper();
        }
        return this.checkOutButtonEnabled;
    }

    public ListProperty<Person> personsProperty() {
        if (this.persons == null) {
            this.persons = new SimpleListProperty<>(FXCollections.observableArrayList());
        }
        return this.persons;
    }

    public ObjectProperty<Person> selectedPersonProperty() {
        if (this.selectedPerson == null) {
            this.selectedPerson = new SimpleObjectProperty<>();
            this.selectedPerson.addListener(new ChangeListener<Person>() {
                @Override
                public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
                    System.out.println("observable = " + observable);
                    updatePersonDetails(newValue);
                }
            });
        }
        return this.selectedPerson;
    }

    public ReadOnlyStringProperty selectedPersonNameProperty() {
        return selectedPersonNameWrapper().getReadOnlyProperty();
    }

    private ReadOnlyStringWrapper selectedPersonNameWrapper() {
        if (this.selectedPersonName == null) {
            this.selectedPersonName = new ReadOnlyStringWrapper();
        }
        return this.selectedPersonName;
    }

    public ReadOnlyStringProperty selectedPersonStreetProperty() {
        if (this.selectedPersonStreet == null) {
            this.selectedPersonStreet = new ReadOnlyStringWrapper();
        }
        return this.selectedPersonStreet.getReadOnlyProperty();
    }

    public ReadOnlyStringProperty selectedPersonCityProperty() {
        if (this.selectedPersonCity == null) {
            this.selectedPersonCity = new ReadOnlyStringWrapper();
        }
        return this.selectedPersonCity.getReadOnlyProperty();
    }

    public ReadOnlyStringProperty selectedPersonBirthDateProperty() {
        if (this.selectedPersonBirthDate == null) {
            this.selectedPersonBirthDate = new ReadOnlyStringWrapper();
        }
        return this.selectedPersonBirthDate.getReadOnlyProperty();
    }

    public ReadOnlyObjectProperty<Image> portraitImageProperty() {
        return portraitImageWrapper().getReadOnlyProperty();
    }

    private ReadOnlyObjectWrapper<Image> portraitImageWrapper() {
        if (this.portraitImage == null) {
            this.portraitImage = new ReadOnlyObjectWrapper<>();
        }
        return this.portraitImage;
    }

    public final Image getPortraitImage() {
        return portraitImageProperty().get();
    }

    // Table View

    public ReadOnlyObjectProperty<Pane> expandedPaneProperty() {
        return expandedPaneWrapper().getReadOnlyProperty();
    }

    private ReadOnlyObjectWrapper<Pane> expandedPaneWrapper() {
        if (this.expandedPane == null) {
            this.expandedPane = new ReadOnlyObjectWrapper<>();
        }
        return this.expandedPane;
    }

    public ListProperty<Visit> youthMemberVisitsProperty() {
        if (this.youthMemberVisits == null) {
            this.youthMemberVisits = new SimpleListProperty<>(FXCollections.observableArrayList());
        }
        return this.youthMemberVisits;
    }

    public ListProperty<Visit> youthStaffVisitsProperty() {
        if (this.youthStaffVisits == null) {
            this.youthStaffVisits = new SimpleListProperty<>(FXCollections.observableArrayList());
        }
        return this.youthStaffVisits;
    }

    public ListProperty<Visit> adultStaffVisitsProperty() {
        if (this.adultStaffVisits == null) {
            this.adultStaffVisits = new SimpleListProperty<>(FXCollections.observableArrayList());
        }
        return this.adultStaffVisits;
    }

    public ListProperty<Visit> driverVisitsProperty() {
        if (this.driverVisits == null) {
            this.driverVisits = new SimpleListProperty<>(FXCollections.observableArrayList());
        }
        return this.driverVisits;
    }

    public final Pane getExpandedPane() {
        return expandedPaneProperty().get();
    }

    public final List<Visit> getYouthMemberVisits() {
        return youthMemberVisitsProperty().get();
    }

    public final void addYouthMemberVisit(Visit visit) {
        youthMemberVisitsProperty().add(visit);
    }

    public final List<Visit> getYouthStaffVisits() {
        return youthStaffVisitsProperty().get();
    }

    public final void addYouthStaffVisit(Visit visit) {
        youthStaffVisitsProperty().add(visit);
    }

    public final List<Visit> getAdultStaffVisits() {
        return adultStaffVisitsProperty().get();
    }

    public final void addAdultStaffVisit(Visit visit) {
        adultStaffVisitsProperty().add(visit);
    }

    public final List<Visit> getDriverVisits() {
        return driverVisitsProperty().get();
    }

    public final void addDriverVisit(Visit visit) {
        driverVisitsProperty().add(visit);
    }

    public final Boolean getAddYouthMemberButtonEnabled() {
        return addYouthMemberButtonEnabledProperty().get();
    }

    public final Boolean getAddYouthStaffButtonEnabled() {
        return addYouthStaffButtonEnabledProperty().get();
    }

    public final Boolean getAddAdultStaffButtonEnabled() {
        return addAdultStaffButtonEnabledProperty().get();
    }

    public final Boolean getAddDriverButtonEnabled() {
        return addDriverButtonEnabledProperty().get();
    }

    public final Boolean getCheckOutButtonEnabled() {
        return checkOutButtonEnabledProperty().get();
    }

    public final Person getSelectedPerson() {
        return selectedPersonProperty().get();
    }

    public final String getSelectedPersonName() {
        return selectedPersonNameProperty().get();
    }

    public final String getSelectedPersonStreet() {
        return selectedPersonStreetProperty().get();
    }

    public final String getSelectedPersonCity() {
        return selectedPersonCityProperty().get();
    }

    public final String getSelectedPersonBirthDate() {
        return selectedPersonBirthDateProperty().get();
    }

    public final List<Person> getPersons() {
        return personsProperty().get();
    }

    public final void setPersons(List<Person> persons) {
        personsProperty().addAll(persons);
    }

    public final EventStatistics getStats() {
        return this.stats;
    }

    // Cube Event Model

    public ObjectProperty<Event> eventProperty() {

        if (this.event == null) {
            this.event = new SimpleObjectProperty<>();
            this.event.addListener(eventListChangeListener);
        }
        return this.event;
    }

    public final Event getEvent() {
        return eventProperty().get();
    }

    public final void setEvent(Event event) {
        eventProperty().set(event);
    }

    protected void eventChanged(Event newValue, Event oldValue) {
        if (oldValue != null) {
            Bindings.unbindBidirectional(oldValue.driverVisitsProperty(), driverVisitsProperty());
            Bindings.unbindBidirectional(oldValue.adultStaffVisitsProperty(), adultStaffVisitsProperty());
            Bindings.unbindBidirectional(oldValue.youthMemberVisitsProperty(), youthMemberVisitsProperty());
            Bindings.unbindBidirectional(oldValue.youthStaffVisitsProperty(), youthStaffVisitsProperty());
            Bindings.unbindBidirectional(oldValue.addressProperty(), addressProperty());
        }
        if (newValue != null) {
            Bindings.bindBidirectional(newValue.driverVisitsProperty(), driverVisitsProperty());
            Bindings.bindBidirectional(newValue.adultStaffVisitsProperty(), adultStaffVisitsProperty());
            Bindings.bindBidirectional(newValue.youthMemberVisitsProperty(), youthMemberVisitsProperty());
            Bindings.bindBidirectional(newValue.youthStaffVisitsProperty(), youthStaffVisitsProperty());
            Bindings.bindBidirectional(newValue.addressProperty(), addressProperty());
        }
    }

    // Logic

    private void updatePersonDetails(Person selectedPerson) {
        updateButtonEnablement(selectedPerson);
        if (selectedPerson == null) {
            return;
        }
        updateNameLabel(selectedPerson);
        updatePortrait(selectedPerson);
    }

    private void disableAllButtons() {
        this.addYouthMemberButtonEnabledWrapper().set(true);
        this.addYouthStaffButtonEnabledWrapper().set(true);
        this.addAdultStaffButtonEnabledWrapper().set(true);
        this.addDriverButtonEnabledWrapper().set(true);
        this.checkOutButtonEnabledWrapper().set(true);
    }

    private void updateNameLabel(Person selectedPerson) {
        this.selectedPersonNameWrapper().set(selectedPerson.getFirstName() + " " + selectedPerson.getLastName());
    }

    private void updatePortrait(Person selectedPerson) {
        if ("Muster".equalsIgnoreCase(selectedPerson.getLastName())) {
            portraitImageWrapper().set(new Image("/resources/images/IMG_1056.jpg"));
        } else if (Person.Gender.MALE == selectedPerson.getGender()) {
            portraitImageWrapper().set(new Image("/resources/images/64Px_Portrait_Man.png"));
        } else if (Person.Gender.FEMALE == selectedPerson.getGender()) {
            portraitImageWrapper().set(new Image("/resources/images/64Px_Portrait_Woman.png"));
        }
    }

    private void updateButtonEnablement(Person selectedPerson) {
        disableAllButtons();
        if (selectedPerson == null) {
            return;
        }
        final Optional<Visit> visit = VisitsUtil.findNonCheckOutVisit(this, selectedPerson);
        if (visit.isPresent() && visit.get().checkOutProperty().isNull().get()) {
            checkOutButtonEnabledWrapper().set(false);
            return;
        }
        final PersonMetaDataDecorator decorator = selectedPerson.getDecorator(PersonMetaDataDecorator.class);
        if (decorator != null) {
            if (decorator.getAssignableRoles().contains(PersonRole.YOUTH_MEMBER)) {
                addYouthMemberButtonEnabledWrapper().set(false);
            }
            if (decorator.getAssignableRoles().contains(PersonRole.YOUTH_STAFF)) {
                addYouthStaffButtonEnabledWrapper().set(false);
            }
            if (decorator.getAssignableRoles().contains(PersonRole.ADULT_STAFF)) {
                addAdultStaffButtonEnabledWrapper().set(false);
            }
            if (decorator.getAssignableRoles().contains(PersonRole.DRIVER)) {
                addDriverButtonEnabledWrapper().set(false);
            }
        }
    }

    public void handleAddYouthMemberButtonClicked() {
        addSelectedPersonWrappedInVisit(getEvent().youthMemberVisitsProperty());
    }

    public void handleAddYouthStaffButtonClicked() {
        addSelectedPersonWrappedInVisit(getEvent().youthStaffVisitsProperty());
    }

    public void handleAddAdultStaffButtonClicked() {
        addSelectedPersonWrappedInVisit(getEvent().adultStaffVisitsProperty());
    }

    public void handleAddDriverButtonClicked() {
        addSelectedPersonWrappedInVisit(getEvent().driverVisitsProperty());
    }

    public void handleCheckOutClicked() {
        final Optional<Visit> visit = VisitsUtil.findNonCheckOutVisit(this, selectedPersonProperty().get());
        if (visit.isPresent()) {
            visit.get().setCheckOut(LocalDateTime.now());
        }
        updateButtonEnablement(selectedPersonProperty().get());
        updateStatistics();
    }

    private void addSelectedPersonWrappedInVisit(ListProperty<Visit> visits) {
        Visit visit = new Visit();
        visit.setPerson(selectedPersonProperty().get());
        visit.setCheckIn(LocalDateTime.now());
        visits.add(visit);
        updateButtonEnablement(selectedPersonProperty().get());
        updateStatistics();
    }

    private void updateStatistics() {
        this.stats.update();
    }
}
