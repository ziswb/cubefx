package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.model.person.Person;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

public class CubeController {

    @FXML
    private ComboBox<Person> personCombo;
    @FXML
    private TableView<Person> youthMembersTable;
    @FXML
    private TableColumn<Person, String> youthMembersTableFirstNameColumn;
    @FXML
    private TableColumn<Person, String> youthMembersTableLastNameColumn;
    @FXML
    private TableView<Person> statsTable;
    @FXML
    private TableView<Person> staffTable;
    @FXML
    private TableView<Person> youthStaffTable;
    @FXML
    private TableView<Person> driversTable;
    @FXML
    private TableColumn<Person, String> youthStaffTableFirstNameColumn;
    @FXML
    private TableColumn<Person, String> youthStaffTableLastNameColumn;
    @FXML
    private TableColumn<Person, String> driversTableFirstNameColumn;
    @FXML
    private TableColumn<Person, String> driversTableLastNameColumn;
    @FXML
    private TableColumn<Person, String> staffTableFirstNameColumn;
    @FXML
    private TableColumn<Person, String> staffTableLastNameColumn;
    @FXML
    private Label nameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private TitledPane statsPane;
    @FXML
    private Accordion accordion;
    @FXML
    private ImageView portrait;

    private ObservableList<Person> personData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        createExampleData();
        initializeCombo(this.personCombo);
        initializeTable(this.youthMembersTable, this.youthMembersTableFirstNameColumn, this.youthMembersTableLastNameColumn);
        initializeTable(this.youthStaffTable, this.youthStaffTableFirstNameColumn, this.youthStaffTableLastNameColumn);
        initializeTable(this.driversTable, this.driversTableFirstNameColumn, this.driversTableLastNameColumn);
        initializeTable(this.staffTable, this.staffTableFirstNameColumn, this.staffTableLastNameColumn);
        this.youthMembersTable.setItems(this.personData);
        this.staffTable.setItems(this.personData);
        this.youthStaffTable.setItems(this.personData);
        this.driversTable.setItems(this.personData);
        this.personCombo.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
        TitledPane firstPane = this.accordion.getPanes().get(0);
        firstPane.setCollapsible(false);
        firstPane.setExpanded(true);
        this.accordion.setExpandedPane(firstPane);
        this.accordion.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
            @Override
            public void changed(ObservableValue<? extends TitledPane> observable, TitledPane oldPane, TitledPane newPane) {
                if (oldPane != null) {
                    oldPane.setCollapsible(true);
                }
                if (newPane != null) Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        newPane.setCollapsible(false);
                    }
                });
            }
        });
    }

    private void initializeCombo(ComboBox<Person> combo) {
        combo.setItems(this.personData);
        combo.setCellFactory((comboBox) -> {
            return new ListCell<Person>() {
                @Override
                protected void updateItem(Person item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item.getFirstName() + " " + item.getLastName());
                    }
                }
            };
        });
        combo.setConverter(new StringConverter<Person>() {
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
                return null; // No conversion fromString needed.
            }
        });
        combo.valueProperty().addListener(new ChangeListener<Person>() {
            @Override
            public void changed(ObservableValue observable, Person oldValue, Person newValue) {
                if (oldValue != null && newValue == null) {
                    combo.setValue(oldValue);
                }
            }
        });
    }

    private void showPersonDetails(Person selectedPerson) {
        if (selectedPerson == null) {
            return;
        }
        this.nameLabel.setText(selectedPerson.getFirstName() + " " + selectedPerson.getLastName());
        if ("Muster".equalsIgnoreCase(selectedPerson.getLastName())) {
            this.portrait.imageProperty().set(new Image("/resources/images/IMG_1056.jpg"));
        } else {
            this.portrait.imageProperty().set(new Image("/resources/images/64Px_Portrait_Man.png"));
        }
    }

    private void initializeTable(TableView<Person> table, TableColumn<Person, String> column1, TableColumn<Person, String> column2) {
        table.setItems(this.personData);
        column1.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        column2.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    }

    private void createExampleData() {
        this.personData.add(createPerson("Hans", "Muster"));
        this.personData.add(createPerson("Ruth", "Mueller"));
        this.personData.add(createPerson("Heinz", "Kurz"));
        this.personData.add(createPerson("Cornelia", "Meier"));
        this.personData.add(createPerson("Werner", "Meyer"));
        this.personData.add(createPerson("Lydia", "Kunz"));
        this.personData.add(createPerson("Anna", "Best"));
        this.personData.add(createPerson("Stefan", "Meier"));
        this.personData.add(createPerson("Martin", "Mueller"));
    }

    private Person createPerson(String firstName, String lastName) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }
}
