package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.model.address.Address;
import ch.bziswiler.cube.model.address.City;
import ch.bziswiler.cube.model.event.PersonMetaDataDecorator;
import ch.bziswiler.cube.model.event.PersonRole;
import ch.bziswiler.cube.model.person.IntegerPersonId;
import ch.bziswiler.cube.model.person.Person;
import ch.bziswiler.cube.model.person.PersonId;
import com.google.common.collect.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

public class CubeScanMemberController extends EventControllerScaffold {

    @FXML
    private ComboBox<Person> personCombo;
    @FXML
    private Label nameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private ImageView portrait;
    @FXML
    private Button addYouthMemberButton;
    @FXML
    private Button addYouthStaffButton;
    @FXML
    private Button addAdultStaffButton;
    @FXML
    private Button addDriverButton;
    @FXML
    private Button checkOutButton;

    private ObservableList<Person> personData = FXCollections.observableArrayList();

    protected void initialize(CubeEventModel newValue) {
        createExampleData();
        initializeCombo();
        createBindings();
    }

    @FXML
    private void handleAddYouthMemberButtonClicked() {
        getModel().handleAddYouthMemberButtonClicked();
    }


    @FXML
    private void handleAddYouthStaffButtonClicked() {
        getModel().handleAddYouthStaffButtonClicked();
    }

    @FXML
    private void handleAddAdultStaffButtonClicked() {
        getModel().handleAddAdultStaffButtonClicked();
    }

    @FXML
    private void handleAddDriverButtonClicked() {
        getModel().handleAddDriverButtonClicked();
    }

    @FXML
    private void handleCheckOutClicked() {
        getModel().handleCheckOutClicked();
    }

    private void initializeCombo() {
        this.personCombo.setItems(this.personData);
        this.personCombo.setConverter(new StringConverter<Person>() {
            @Override
            public String toString(Person person) {
                if (person == null) {
                    return null;
                } else {
                    return person.getFirstName() + " " + person.getLastName();
                }
            }

            @Override
            public Person fromString(String personString) {
                final FilteredList<Person> filtered = personData.filtered(person -> {
                    final PersonId personId = person.getPersonId();
                    if (personId != null) {
                        try {
                            return personId.equals(IntegerPersonId.fromString(personString));
                        } catch (Exception e) {
                            // nop
                        }
                    }
                    return false;
                });
                return filtered.size() == 1 ? filtered.get(0) : null;
            }
        });
        this.personCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue == null) {
                personCombo.setValue(oldValue);
            }
        });
    }

    private void createBindings() {
        getModel().selectedPersonProperty().bind(this.personCombo.getSelectionModel().selectedItemProperty());
        addDriverButton.disableProperty().bind(getModel().addDriverButtonDisabledProperty());
        addAdultStaffButton.disableProperty().bind(getModel().addAdultStaffButtonDisabledProperty());
        addYouthMemberButton.disableProperty().bind(getModel().addYouthMemberButtonDisabledProperty());
        addYouthStaffButton.disableProperty().bind(getModel().addYouthStaffButtonDisabledProperty());
        checkOutButton.disableProperty().bind(getModel().checkOutButtonDisabledProperty());
        portrait.imageProperty().bind(getModel().portraitImageProperty());
        nameLabel.textProperty().bind(getModel().selectedPersonNameProperty());
    }

    @Override
    protected void dispose(CubeEventModel oldValue) {
        getModel().selectedPersonProperty().unbind();
        addDriverButton.disableProperty().unbind();
        addAdultStaffButton.disableProperty().unbind();
        addYouthMemberButton.disableProperty().unbind();
        addYouthStaffButton.disableProperty().unbind();
        checkOutButton.disableProperty().unbind();
        portrait.imageProperty().unbind();
        nameLabel.textProperty().unbind();
    }

    private void createExampleData() {
        this.personData.add(createPerson("Hans", "Muster", Person.Gender.MALE, 1, PersonRole.YOUTH_MEMBER));
        this.personData.add(createPerson("Ruth", "Mueller", Person.Gender.FEMALE, 2, PersonRole.YOUTH_STAFF));
        this.personData.add(createPerson("Heinz", "Kurz", Person.Gender.MALE, 3, PersonRole.ADULT_STAFF));
        this.personData.add(createPerson("Cornelia", "Meier", Person.Gender.FEMALE, 4, PersonRole.ADULT_STAFF, PersonRole.DRIVER));
        this.personData.add(createPerson("Werner", "Meyer", Person.Gender.MALE, 5, PersonRole.YOUTH_MEMBER));
        this.personData.add(createPerson("Lydia", "Kunz", Person.Gender.FEMALE, 6, PersonRole.YOUTH_STAFF));
        this.personData.add(createPerson("Anna", "Best", Person.Gender.FEMALE, 7, PersonRole.ADULT_STAFF));
        this.personData.add(createPerson("Stefan", "Meier", Person.Gender.MALE, 8, PersonRole.ADULT_STAFF, PersonRole.DRIVER));
        this.personData.add(createPerson("Martin", "Mueller", Person.Gender.MALE, 9, PersonRole.YOUTH_MEMBER));
    }

    private Person createPerson(String firstName, String lastName, Person.Gender gender, int id, PersonRole... roles) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setGender(gender);
        person.setPersonId(new IntegerPersonId(id));
        final PersonMetaDataDecorator decorator = new PersonMetaDataDecorator();
        decorator.setAssignableRoles(Lists.newArrayList(roles));
        person.addDecorator(decorator);
        final Address address = new Address();
        address.setAddition("21B");
        final City city = new City();
        city.setCountry("CH");
        city.setName("Buechegg");
        city.setState("SG");
        city.setZip("9001");
        address.setCity(city);
        person.setAddress(address);
        return person;
    }
}
