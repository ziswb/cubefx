package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.model.VisitsUtil;
import ch.bziswiler.cube.model.address.Address;
import ch.bziswiler.cube.model.event.Duration;
import ch.bziswiler.cube.model.event.Event;
import ch.bziswiler.cube.model.event.PersonMetaDataDecorator;
import ch.bziswiler.cube.model.event.PersonRole;
import ch.bziswiler.cube.model.event.Visit;
import ch.bziswiler.cube.model.person.Person;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

public class CubeEventModel {

    public static final boolean DEFAULT_BUTTON_DISABLED_VALUE = true;
    private final Duration duration = new Duration();
    private final EventStatistics stats;
    private ObjectProperty<LocalDateTime> start;
    private ObjectProperty<LocalDateTime> end;
    private ReadOnlyLongWrapper minutesOfEventDuration;
    private ReadOnlyLongWrapper hoursOfEventDuration;
    private ObjectProperty<Address> address;
    private ReadOnlyBooleanWrapper addYouthMemberButtonDisabled;
    private ReadOnlyBooleanWrapper addYouthStaffButtonDisabled;
    private ReadOnlyBooleanWrapper addAdultStaffButtonDisabled;
    private ReadOnlyBooleanWrapper addDriverButtonDisabled;
    private ReadOnlyBooleanWrapper checkOutButtonDisabled;
    private ReadOnlyBooleanWrapper startButtonDisabled;
    private ReadOnlyBooleanWrapper endButtonDisabled;
    private ListProperty<Person> persons;
    private SimpleObjectProperty<Person> selectedPerson;
    private ReadOnlyStringWrapper selectedPersonName;
    private ReadOnlyStringWrapper selectedPersonStreet;
    private ReadOnlyStringWrapper selectedPersonCity;
    private ReadOnlyStringWrapper selectedPersonBirthDate;
    private ReadOnlyObjectWrapper<Image> portraitImage;
    private ListProperty<Visit> youthMemberVisits;
    private ListProperty<Visit> youthStaffVisits;
    private ListProperty<Visit> adultStaffVisits;
    private ListProperty<Visit> driverVisits;
    private ObjectProperty<Event> event;
    private ChangeListener<Event> eventListChangeListener;

    public CubeEventModel() {
        this.eventListChangeListener = (observable, oldValue, newValue) -> eventChanged(newValue, oldValue);
        // TODO resolve event dependency
        eventProperty().set(new Event());
        this.stats = new EventStatistics(this);
        this.duration.startProperty().bind(startProperty());
        this.duration.endProperty().bind(endProperty());
    }

    protected void eventChanged(Event newValue, Event oldValue) {
        // TODO clean up bindings -> data saved in "Event" is currently incomplete. e.g. start, end, etc.
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

    public ObjectProperty<LocalDateTime> startProperty() {
        if (this.start == null) {
            this.start = new SimpleObjectProperty<>();
            this.start.addListener(new ChangeListener<LocalDateTime>() {
                @Override
                public void changed(ObservableValue<? extends LocalDateTime> observable, LocalDateTime oldValue, LocalDateTime newValue) {
                    updateButtonEnablement(selectedPersonProperty().get());
                }
            });
        }
        return this.start;
    }

    public ObjectProperty<LocalDateTime> endProperty() {
        if (this.end == null) {
            this.end = new SimpleObjectProperty<>();
            this.end.addListener(new ChangeListener<LocalDateTime>() {
                @Override
                public void changed(ObservableValue<? extends LocalDateTime> observable, LocalDateTime oldValue, LocalDateTime newValue) {
                    updateButtonEnablement(selectedPersonProperty().get());
                }
            });
        }
        return this.end;
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

    public ReadOnlyLongProperty hoursOfEventDurationProperty() {
        return hoursOfEventDurationWrapper().getReadOnlyProperty();
    }

    private ReadOnlyLongWrapper hoursOfEventDurationWrapper() {
        if (this.hoursOfEventDuration == null) {
            this.hoursOfEventDuration = new ReadOnlyLongWrapper();
            this.hoursOfEventDuration.bind(this.duration.hoursProperty());
        }
        return this.hoursOfEventDuration;
    }

    public ReadOnlyLongProperty minutesOfEventDurationProperty() {
        return minutesOfEventDurationWrapper().getReadOnlyProperty();
    }

    private ReadOnlyLongWrapper minutesOfEventDurationWrapper() {
        if (this.minutesOfEventDuration == null) {
            this.minutesOfEventDuration = new ReadOnlyLongWrapper();
            this.minutesOfEventDuration.bind(this.duration.minutesProperty());
        }
        return this.minutesOfEventDuration;
    }

    public final LocalDateTime getStart() {
        return startProperty().get();
    }

    public final void setStart(LocalDateTime start) {
        startProperty().set(start);
    }

    public final LocalDateTime getEnd() {
        return endProperty().get();
    }

    public final void setEnd(LocalDateTime end) {
        endProperty().set(end);
    }

    public final Image getPortraitImage() {
        return portraitImageProperty().get();
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

    public final List<Visit> getYouthMemberVisits() {
        return youthMemberVisitsProperty().get();
    }

    public ListProperty<Visit> youthMemberVisitsProperty() {
        if (this.youthMemberVisits == null) {
            this.youthMemberVisits = new SimpleListProperty<>(FXCollections.observableArrayList());
        }
        return this.youthMemberVisits;
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

    public final void addDriverVisit(Visit visit) {
        driverVisitsProperty().add(visit);
    }

    public final Boolean getAddYouthMemberButtonDisabled() {
        return addYouthMemberButtonDisabledProperty().get();
    }

    public ReadOnlyBooleanProperty addYouthMemberButtonDisabledProperty() {
        return addYouthMemberButtonDisabledWrapper().getReadOnlyProperty();
    }

    private ReadOnlyBooleanWrapper addYouthMemberButtonDisabledWrapper() {
        if (this.addYouthMemberButtonDisabled == null) {
            this.addYouthMemberButtonDisabled = new ReadOnlyBooleanWrapper(DEFAULT_BUTTON_DISABLED_VALUE);
        }
        return this.addYouthMemberButtonDisabled;
    }

    private PersonMetaDataDecorator getSelectedPersonsDecoratorIfEventIsOnGoing() {
        if (startProperty().get() == null) {
            return null;
        }
        if (endProperty().get() != null) {
            return null;
        }
        final Person person = selectedPersonProperty().get();
        if (person == null) {
            return null;
        }
        return person.getDecorator(PersonMetaDataDecorator.class);
    }

    public ObjectProperty<Person> selectedPersonProperty() {
        if (this.selectedPerson == null) {
            this.selectedPerson = new SimpleObjectProperty<>();
            this.selectedPerson.addListener((observable, oldValue, newValue) -> updateScanMemberView(newValue));
        }
        return this.selectedPerson;
    }

    private void updateScanMemberView(Person selectedPerson) {
        updateButtonEnablement(selectedPerson);
        if (selectedPerson == null) {
            return;
        }
        updateNameLabel(selectedPerson);
        updatePortrait(selectedPerson);
    }

    private void updateButtonEnablement(Person selectedPerson) {
        disableAllButtons();
        if (selectedPerson == null) {
            return;
        }
        if (endProperty().get() != null) {
            return;
        }
        if (startProperty().get() == null) {
            return;
        }
        final Optional<Visit> visit = VisitsUtil.findNonCheckOutVisit(this, selectedPerson);
        if (visit.isPresent() && visit.get().checkOutProperty().isNull().get()) {
            checkOutButtonDisabledWrapper().set(false);
            return;
        }
        final PersonMetaDataDecorator decorator = selectedPerson.getDecorator(PersonMetaDataDecorator.class);
        if (decorator != null) {
            if (decorator.getAssignableRoles().contains(PersonRole.YOUTH_MEMBER)) {
                addYouthMemberButtonDisabledWrapper().set(false);
            }
            if (decorator.getAssignableRoles().contains(PersonRole.YOUTH_STAFF)) {
                addYouthStaffButtonDisabledWrapper().set(false);
            }
            if (decorator.getAssignableRoles().contains(PersonRole.ADULT_STAFF)) {
                addAdultStaffButtonDisabledWrapper().set(false);
            }
            if (decorator.getAssignableRoles().contains(PersonRole.DRIVER)) {
                addDriverButtonDisabledWrapper().set(false);
            }
        }
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

    private void disableAllButtons() {
        this.addYouthMemberButtonDisabledWrapper().set(true);
        this.addYouthStaffButtonDisabledWrapper().set(true);
        this.addAdultStaffButtonDisabledWrapper().set(true);
        this.addDriverButtonDisabledWrapper().set(true);
        this.checkOutButtonDisabledWrapper().set(true);
    }

    public final Boolean getAddYouthStaffButtonDisabled() {
        return addYouthStaffButtonDisabledProperty().get();
    }

    public ReadOnlyBooleanProperty addYouthStaffButtonDisabledProperty() {
        return addYouthStaffButtonDisabledWrapper().getReadOnlyProperty();
    }

    private ReadOnlyBooleanWrapper addYouthStaffButtonDisabledWrapper() {
        if (this.addYouthStaffButtonDisabled == null) {
            this.addYouthStaffButtonDisabled = new ReadOnlyBooleanWrapper(DEFAULT_BUTTON_DISABLED_VALUE);
        }
        return this.addYouthStaffButtonDisabled;
    }

    public final Boolean getAddAdultStaffButtonDisabled() {
        return addAdultStaffButtonDisabledProperty().get();
    }

    public ReadOnlyBooleanProperty addAdultStaffButtonDisabledProperty() {
        return this.addAdultStaffButtonDisabledWrapper().getReadOnlyProperty();
    }

    private ReadOnlyBooleanWrapper addAdultStaffButtonDisabledWrapper() {
        if (this.addAdultStaffButtonDisabled == null) {
            this.addAdultStaffButtonDisabled = new ReadOnlyBooleanWrapper(DEFAULT_BUTTON_DISABLED_VALUE);
        }
        return this.addAdultStaffButtonDisabled;
    }

    public final Boolean getAddDriverButtonDisabled() {
        return addDriverButtonDisabledProperty().get();
    }

    public ReadOnlyBooleanProperty addDriverButtonDisabledProperty() {
        return this.addDriverButtonDisabledWrapper().getReadOnlyProperty();
    }

    private ReadOnlyBooleanWrapper addDriverButtonDisabledWrapper() {
        if (this.addDriverButtonDisabled == null) {
            this.addDriverButtonDisabled = new ReadOnlyBooleanWrapper(DEFAULT_BUTTON_DISABLED_VALUE);
        }
        return this.addDriverButtonDisabled;
    }

    public final Boolean getCheckOutButtonDisabled() {
        return checkOutButtonDisabledProperty().get();
    }

    public ReadOnlyBooleanProperty checkOutButtonDisabledProperty() {
        return this.checkOutButtonDisabledWrapper().getReadOnlyProperty();
    }

    private ReadOnlyBooleanWrapper checkOutButtonDisabledWrapper() {
        if (this.checkOutButtonDisabled == null) {
            this.checkOutButtonDisabled = new ReadOnlyBooleanWrapper(DEFAULT_BUTTON_DISABLED_VALUE);
        }
        return this.checkOutButtonDisabled;
    }

    public final Boolean getStartButtonDisabled() {
        return startButtonDisabledProperty().get();
    }

    public ReadOnlyBooleanProperty startButtonDisabledProperty() {
        return this.startButtonDisabledWrapper().getReadOnlyProperty();
    }

    private ReadOnlyBooleanWrapper startButtonDisabledWrapper() {
        if (this.startButtonDisabled == null) {
            this.startButtonDisabled = new ReadOnlyBooleanWrapper(DEFAULT_BUTTON_DISABLED_VALUE);
            final BooleanBinding binding = Bindings.createBooleanBinding(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return startProperty().get() != null;
                }
            }, startProperty());
            this.startButtonDisabled.bind(binding);
        }
        return this.startButtonDisabled;
    }

    public final Boolean getEndButtonDisabled() {
        return endButtonDisabledProperty().get();
    }

    public ReadOnlyBooleanProperty endButtonDisabledProperty() {
        return this.endButtonDisabledWrapper().getReadOnlyProperty();
    }

    private ReadOnlyBooleanWrapper endButtonDisabledWrapper() {
        if (this.endButtonDisabled == null) {
            this.endButtonDisabled = new ReadOnlyBooleanWrapper(DEFAULT_BUTTON_DISABLED_VALUE);
            final BooleanBinding binding = Bindings.createBooleanBinding(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    final LocalDateTime end = endProperty().get();
                    final LocalDateTime start = startProperty().get();
                    return end != null || start == null;
                }
            }, endProperty(), startProperty());
            this.endButtonDisabled.bind(binding);
        }
        return this.endButtonDisabled;
    }

    public final Person getSelectedPerson() {
        return selectedPersonProperty().get();
    }

    public final String getSelectedPersonName() {
        return selectedPersonNameProperty().get();
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

    public final String getSelectedPersonStreet() {
        return selectedPersonStreetProperty().get();
    }

    public ReadOnlyStringProperty selectedPersonStreetProperty() {
        if (this.selectedPersonStreet == null) {
            this.selectedPersonStreet = new ReadOnlyStringWrapper();
        }
        return this.selectedPersonStreet.getReadOnlyProperty();
    }

    public final String getSelectedPersonCity() {
        return selectedPersonCityProperty().get();
    }

    public ReadOnlyStringProperty selectedPersonCityProperty() {
        if (this.selectedPersonCity == null) {
            this.selectedPersonCity = new ReadOnlyStringWrapper();
        }
        return this.selectedPersonCity.getReadOnlyProperty();
    }

    public final String getSelectedPersonBirthDate() {
        return selectedPersonBirthDateProperty().get();
    }

    public ReadOnlyStringProperty selectedPersonBirthDateProperty() {
        if (this.selectedPersonBirthDate == null) {
            this.selectedPersonBirthDate = new ReadOnlyStringWrapper();
        }
        return this.selectedPersonBirthDate.getReadOnlyProperty();
    }

    public final List<Person> getPersons() {
        return personsProperty().get();
    }

    public ListProperty<Person> personsProperty() {
        if (this.persons == null) {
            this.persons = new SimpleListProperty<>(FXCollections.observableArrayList());
        }
        return this.persons;
    }

    public final void setPersons(List<Person> persons) {
        personsProperty().addAll(persons);
    }

    public final EventStatistics getStats() {
        return this.stats;
    }

    public void handleAddYouthMemberButtonClicked() {
        addSelectedPersonWrappedInVisit(getEvent().youthMemberVisitsProperty());
    }

    private void addSelectedPersonWrappedInVisit(ListProperty<Visit> visits) {
        Visit visit = new Visit();
        visit.setPerson(selectedPersonProperty().get());
        visit.setCheckIn(LocalDateTime.now());
        visits.add(visit);
        updateButtonEnablement(selectedPersonProperty().get());
        updateStatistics();
    }

    public final Event getEvent() {
        return eventProperty().get();
    }

    public final void setEvent(Event event) {
        eventProperty().set(event);
    }

    public ObjectProperty<Event> eventProperty() {
        if (this.event == null) {
            this.event = new SimpleObjectProperty<>();
            this.event.addListener(eventListChangeListener);
        }
        return this.event;
    }

    private void updateStatistics() {
        this.stats.update();
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
}
