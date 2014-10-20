package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.model.event.KeyValue;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CubeEventNumbersController extends EventControllerScaffold {

    @FXML
    private Label numberOfPresentYouthMembers;
    @FXML
    private Label numberOfAllYouthMembers;
    @FXML
    private Label numberOfPresentAdults;
    @FXML
    private Label totalNumberOfPresentPersons;
    @FXML
    private Label numberOfAllAdults;
    @FXML
    private Label totalNumberOfAllPersons;
    @FXML
    private TableView<KeyValue<String, Integer>> statsTable;
    @FXML
    private TableColumn<KeyValue<String, Integer>, String> statsCityColumn;
    @FXML
    private TableColumn<KeyValue<String, Integer>, Integer> statsNumberColumn;

    private ReadOnlyIntegerWrapper numberOfPresentYouthMembersProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfPresentYouthStaffProperty = new ReadOnlyIntegerWrapper();

    private ReadOnlyIntegerWrapper numberOfAllYouthMembersProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfAllYouthStaffProperty = new ReadOnlyIntegerWrapper();

    private ReadOnlyIntegerWrapper numberOfPresentAdultStaffProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfPresentDriversProperty = new ReadOnlyIntegerWrapper();

    private ReadOnlyIntegerWrapper numberOfAllAdultStaffProperty = new ReadOnlyIntegerWrapper();
    private ReadOnlyIntegerWrapper numberOfAllDriversProperty = new ReadOnlyIntegerWrapper();

    @FXML
    private void initialize() {
        final NumberBinding numberOfPresentYouthMembersBinding = this.numberOfPresentYouthMembersProperty.add(this.numberOfPresentYouthStaffProperty);
        this.numberOfPresentYouthMembers.textProperty().bind(numberOfPresentYouthMembersBinding.asString());

        final NumberBinding numberOfAllYouthMembersBinding = this.numberOfAllYouthMembersProperty.add(this.numberOfAllYouthStaffProperty);
        this.numberOfAllYouthMembers.textProperty().bind(numberOfAllYouthMembersBinding.asString());

        final NumberBinding numberOfPresentAdultsBinding = this.numberOfPresentAdultStaffProperty.add(this.numberOfPresentDriversProperty);
        this.numberOfPresentAdults.textProperty().bind(numberOfPresentAdultsBinding.asString());

        final NumberBinding numberOfAllAdultsBinding = this.numberOfAllAdultStaffProperty.add(this.numberOfAllDriversProperty);
        this.numberOfAllAdults.textProperty().bind(numberOfAllAdultsBinding.asString());

        final NumberBinding totalNumberOfPresentPersonsBinding = numberOfPresentYouthMembersBinding.add(numberOfPresentAdultsBinding);
        this.totalNumberOfPresentPersons.textProperty().bind(totalNumberOfPresentPersonsBinding.asString());

        final NumberBinding totalNumberOfAllPersonsBinding = numberOfAllYouthMembersBinding.add(numberOfAllAdultsBinding);
        this.totalNumberOfAllPersons.textProperty().bind(totalNumberOfAllPersonsBinding.asString());

        initializeStatsTable();
    }

    private void initializeStatsTable() {
        this.statsCityColumn.setCellValueFactory(cellData -> cellData.getValue().keyProperty());
        this.statsNumberColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
    }

    @Override
    protected void initialize(CubeEventModel newValue) {
        final EventStatistics stats = newValue.getStats();
        this.numberOfPresentYouthMembersProperty.bind(stats.numberOfPresentYouthMembersProperty());
        this.numberOfPresentYouthStaffProperty.bind(stats.numberOfPresentYouthStaffProperty());
        this.numberOfAllYouthMembersProperty.bind(stats.numberOfAllYouthMembersProperty());
        this.numberOfAllYouthStaffProperty.bind(stats.numberOfAllYouthStaffProperty());
        this.numberOfPresentAdultStaffProperty.bind(stats.numberOfPresentAdultStaffProperty());
        this.numberOfPresentDriversProperty.bind(stats.numberOfPresentDriversProperty());
        this.numberOfAllAdultStaffProperty.bind(stats.numberOfAllAdultStaffProperty());
        this.numberOfAllDriversProperty.bind(stats.numberOfAllDriversProperty());
        this.statsTable.setItems(stats.visitsDigest());
    }

    @Override
    protected void dispose(CubeEventModel oldValue) {
        this.numberOfPresentYouthMembersProperty.unbind();
        this.numberOfPresentYouthStaffProperty.unbind();
        this.numberOfAllYouthMembersProperty.unbind();
        this.numberOfAllYouthStaffProperty.unbind();
        this.numberOfPresentAdultStaffProperty.unbind();
        this.numberOfPresentDriversProperty.unbind();
        this.numberOfAllAdultStaffProperty.unbind();
        this.numberOfAllDriversProperty.unbind();
    }
}
