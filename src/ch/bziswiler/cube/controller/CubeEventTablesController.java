package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.model.event.Visit;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;

import java.time.LocalDateTime;

public class CubeEventTablesController extends EventControllerScaffold {

    @FXML
    private Accordion accordion;
    @FXML
    private TitledPane youthMembersPane;
    @FXML
    private TableView<Visit> youthMembersTable;
    @FXML
    private TableColumn<Visit, String> youthMembersTableFirstNameColumn;
    @FXML
    private TableColumn<Visit, String> youthMembersTableLastNameColumn;
    @FXML
    private TableColumn<Visit, LocalDateTime> youthMembersTableCheckInColumn;
    @FXML
    private TableColumn<Visit, LocalDateTime> youthMembersTableCheckOutColumn;
    @FXML
    private TitledPane youthStaffPane;
    @FXML
    private TableView<Visit> youthStaffTable;
    @FXML
    private TableColumn<Visit, String> youthStaffTableFirstNameColumn;
    @FXML
    private TableColumn<Visit, String> youthStaffTableLastNameColumn;
    @FXML
    private TableColumn<Visit, LocalDateTime> youthStaffTableCheckInColumn;
    @FXML
    private TableColumn<Visit, LocalDateTime> youthStaffTableCheckOutColumn;
    @FXML
    private TitledPane adultStaffPane;
    @FXML
    private TableView<Visit> adultStaffTable;
    @FXML
    private TableColumn<Visit, String> adultStaffTableFirstNameColumn;
    @FXML
    private TableColumn<Visit, String> adultStaffTableLastNameColumn;
    @FXML
    private TableColumn<Visit, LocalDateTime> adultStaffTableCheckInColumn;
    @FXML
    private TableColumn<Visit, LocalDateTime> adultStaffTableCheckOutColumn;
    @FXML
    private TitledPane driversPane;
    @FXML
    private TableView<Visit> driversTable;
    @FXML
    private TableColumn<Visit, String> driversTableFirstNameColumn;
    @FXML
    private TableColumn<Visit, String> driversTableLastNameColumn;
    @FXML
    private TableColumn<Visit, LocalDateTime> driversTableCheckInColumn;
    @FXML
    private TableColumn<Visit, LocalDateTime> driversTableCheckOutColumn;

    private PaneExpander youthMemberPaneExpander;
    private PaneExpander youthStaffPaneExpander;
    private PaneExpander adultStaffPaneExpander;
    private PaneExpander driversPaneExpander;

    @FXML
    private void initialize() {
        initializeYouthMembersTable();
        initializeYouthStaffTable();
        initializeDriversTable();
        initializeAdultStaffTable();
        initializeAccordion();
    }

    private void initializeYouthMembersTable() {
        setLabelFactory(this.youthMembersTableFirstNameColumn);
        setLabelFactory(this.youthMembersTableLastNameColumn);
        setDateFactory(this.youthMembersTableCheckInColumn);
        setDateFactory(this.youthMembersTableCheckOutColumn);
        setFirstNameCellValueFactory(this.youthMembersTableFirstNameColumn);
        setLastNameCellValueFactory(this.youthMembersTableLastNameColumn);
        setCheckInCellValueFactory(this.youthMembersTableCheckInColumn);
        setCheckOutCellValueFactory(this.youthMembersTableCheckOutColumn);
    }

    private void initializeYouthStaffTable() {
        setLabelFactory(this.youthStaffTableFirstNameColumn);
        setLabelFactory(this.youthStaffTableLastNameColumn);
        setDateFactory(this.youthStaffTableCheckInColumn);
        setDateFactory(this.youthStaffTableCheckOutColumn);
        setFirstNameCellValueFactory(this.youthStaffTableFirstNameColumn);
        setLastNameCellValueFactory(this.youthStaffTableLastNameColumn);
        setCheckInCellValueFactory(this.youthStaffTableCheckInColumn);
        setCheckOutCellValueFactory(this.youthStaffTableCheckOutColumn);
    }

    private void initializeDriversTable() {
        setLabelFactory(this.driversTableFirstNameColumn);
        setLabelFactory(this.driversTableLastNameColumn);
        setDateFactory(this.adultStaffTableCheckInColumn);
        setDateFactory(this.adultStaffTableCheckOutColumn);
        setFirstNameCellValueFactory(this.driversTableFirstNameColumn);
        setLastNameCellValueFactory(this.driversTableLastNameColumn);
        setCheckInCellValueFactory(this.adultStaffTableCheckInColumn);
        setCheckOutCellValueFactory(this.adultStaffTableCheckOutColumn);
    }

    private void initializeAdultStaffTable() {
        setLabelFactory(this.adultStaffTableFirstNameColumn);
        setLabelFactory(this.adultStaffTableLastNameColumn);
        setDateFactory(this.driversTableCheckInColumn);
        setDateFactory(this.driversTableCheckOutColumn);
        setFirstNameCellValueFactory(this.adultStaffTableFirstNameColumn);
        setLastNameCellValueFactory(this.adultStaffTableLastNameColumn);
        setCheckInCellValueFactory(this.driversTableCheckInColumn);
        setCheckOutCellValueFactory(this.driversTableCheckOutColumn);
    }

    private void initializeAccordion() {
        final TitledPane firstPane = this.accordion.getPanes().get(0);
        firstPane.setCollapsible(false);
        firstPane.setExpanded(true);
        this.accordion.setExpandedPane(firstPane);
        this.accordion.expandedPaneProperty().addListener((observable, oldPane, newPane) -> {
            if (oldPane != null) {
                oldPane.setCollapsible(true);
            }
            if (newPane != null) Platform.runLater(() -> newPane.setCollapsible(false));
        });
        youthMemberPaneExpander = new PaneExpander(youthMembersPane, accordion);
        youthStaffPaneExpander = new PaneExpander(youthStaffPane, accordion);
        adultStaffPaneExpander = new PaneExpander(adultStaffPane, accordion);
        driversPaneExpander = new PaneExpander(driversPane, accordion);
    }

    private void setLabelFactory(TableColumn<Visit, String> column) {
        column.setCellFactory(new LabelCellFactory());
    }

    private void setDateFactory(TableColumn<Visit, LocalDateTime> column) {
        column.setCellFactory(new DateCellFactory());
    }

    private void setFirstNameCellValueFactory(TableColumn<Visit, String> column) {
        column.setCellValueFactory(cellData -> cellData.getValue().personProperty().get().firstNameProperty());
    }

    private void setLastNameCellValueFactory(TableColumn<Visit, String> column) {
        column.setCellValueFactory(cellData -> cellData.getValue().personProperty().get().lastNameProperty());
    }

    private void setCheckInCellValueFactory(TableColumn<Visit, LocalDateTime> column) {
        column.setCellValueFactory(cellData -> cellData.getValue().checkInProperty());
    }

    private void setCheckOutCellValueFactory(TableColumn<Visit, LocalDateTime> column) {
        column.setCellValueFactory(cellData -> cellData.getValue().checkOutProperty());
    }

    @Override
    protected void initialize(CubeEventModel event) {
        final ListProperty<Visit> youthMemberVisits = event.youthMemberVisitsProperty();
        final ListProperty<Visit> youthStaffVisits = event.youthStaffVisitsProperty();
        final ListProperty<Visit> adultStaffVisits = event.adultStaffVisitsProperty();
        final ListProperty<Visit> driverVisits = event.driverVisitsProperty();

        youthMemberVisits.addListener(youthMemberPaneExpander);
        youthStaffVisits.addListener(youthStaffPaneExpander);
        adultStaffVisits.addListener(adultStaffPaneExpander);
        driverVisits.addListener(driversPaneExpander);

        this.youthMembersTable.setItems(youthMemberVisits);
        this.youthStaffTable.setItems(youthStaffVisits);
        this.adultStaffTable.setItems(adultStaffVisits);
        this.driversTable.setItems(driverVisits);
    }

    @Override
    public void dispose(CubeEventModel event) {
        event.youthMemberVisitsProperty().removeListener(youthMemberPaneExpander);
        event.youthStaffVisitsProperty().removeListener(youthStaffPaneExpander);
        event.adultStaffVisitsProperty().removeListener(adultStaffPaneExpander);
        event.driverVisitsProperty().removeListener(driversPaneExpander);
    }
}
