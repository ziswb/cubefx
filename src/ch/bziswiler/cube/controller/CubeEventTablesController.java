package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.model.address.City;
import ch.bziswiler.cube.model.event.Visit;
import ch.bziswiler.cube.model.person.Person;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.util.Callback;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;

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
    @FXML
    private TitledPane statsPane;
    @FXML
    private TableView<Person> statsTable;
    @FXML
    private TableColumn<Map<String, Integer>, Integer> statsCityColumn;
    @FXML
    private TableColumn<Map<String, Integer>, Integer> statsNumberColumn;

    private PaneExpander youthMemberPaneExpander;
    private PaneExpander youthStaffPaneExpander;
    private PaneExpander adultStaffPaneExpander;
    private PaneExpander driversPaneExpander;
    private Callback<Void, Void> callback = new Callback<Void, Void>() {
        @Override
        public Void call(Void param) {
            System.out.println("param = " + param);
            computeNumberOfVisitors();
            final FilteredList<Visit> youthMembers = getEvent().youthMemberVisitsProperty().filtered(visit -> {
                return visit.checkOutProperty().isNotNull().get();
            });
            final FilteredList<Visit> youthStaff = getEvent().youthStaffVisitsProperty().filtered(visit -> {
                return visit.checkOutProperty().isNotNull().get();
            });
            final Map<City, Integer> map = new HashMap<>();
            for (Visit visit : youthMembers) {
                if (
                        visit.personProperty().isNull().or(
                                visit.personProperty().get().addressProperty().isNull().or(
                                        visit.personProperty().get().addressProperty().get().cityProperty().isNull()
                                )
                        ).get()
                        ) {
                    continue;
                }
                final City city = visit.personProperty().get().addressProperty().get().getCity();
                if (!map.containsKey(city)) {
                    map.put(city, 1);
                } else {
                    map.put(city, map.get(city) + 1);
                }
            }
            for (Visit visit : youthStaff) {
                if (
                        visit.personProperty().isNull().or(
                                visit.personProperty().get().addressProperty().isNull().or(
                                        visit.personProperty().get().addressProperty().get().cityProperty().isNull()
                                )
                        ).get()
                        ) {
                    continue;
                }
                final City city = visit.personProperty().get().addressProperty().get().getCity();
                if (!map.containsKey(city)) {
                    map.put(city, 1);
                } else {
                    map.put(city, map.get(city) + 1);
                }
            }
            return null;
        }

        private void computeNumberOfVisitors() {
            final int youthMembers = getEvent().youthMemberVisitsProperty().filtered(visit -> {
                return visit.checkOutProperty().isNotNull().get();
            }).size();
            final int youthStaff = getEvent().youthStaffVisitsProperty().filtered(visit -> {
                return visit.checkOutProperty().isNotNull().get();
            }).size();
            final int adultStaff = getEvent().adultStaffVisitsProperty().filtered(visit -> {
                return visit.checkOutProperty().isNotNull().get();
            }).size();
            final int drivers = getEvent().driverVisitsProperty().filtered(visit -> {
                return visit.checkOutProperty().isNotNull().get();
            }).size();
        }
    };

    @FXML
    private void initialize() {
        initializeTable(this.youthMembersTableFirstNameColumn, this.youthMembersTableLastNameColumn, this.youthMembersTableCheckInColumn, this.youthMembersTableCheckOutColumn);
        initializeTable(this.youthStaffTableFirstNameColumn, this.youthStaffTableLastNameColumn, this.youthStaffTableCheckInColumn, this.youthStaffTableCheckOutColumn);
        initializeTable(this.driversTableFirstNameColumn, this.driversTableLastNameColumn, this.adultStaffTableCheckInColumn, this.adultStaffTableCheckOutColumn);
        initializeTable(this.adultStaffTableFirstNameColumn, this.adultStaffTableLastNameColumn, this.driversTableCheckInColumn, this.driversTableCheckOutColumn);
        initializeStatsTable(this.statsCityColumn, this.statsNumberColumn);
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
        youthMemberPaneExpander = new PaneExpander(youthMembersPane, accordion, this.callback);
        youthStaffPaneExpander = new PaneExpander(youthStaffPane, accordion);
        adultStaffPaneExpander = new PaneExpander(adultStaffPane, accordion);
        driversPaneExpander = new PaneExpander(driversPane, accordion);
    }

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

    private void initializeStatsTable(TableColumn<Map<String, Integer>, Integer> statsCityColumn, TableColumn<Map<String, Integer>, Integer> statsNumberColumn) {
//        statsCityColumn.setCellValueFactory(cellData -> cellData.getValue().personProperty().get().firstNameProperty());
//        statsNumberColumn.setCellValueFactory(cellData -> cellData.getValue().personProperty().get().firstNameProperty());
    }

    private void initializeTable(TableColumn<Visit, String> column1, TableColumn<Visit, String> column2, TableColumn<Visit, LocalDateTime> column3, TableColumn<Visit, LocalDateTime> column4) {
        column1.setCellFactory(new LabelCellFactory());
        column2.setCellFactory(new LabelCellFactory());
        column3.setCellFactory(new DateCellFactory());
        column4.setCellFactory(new DateCellFactory());
        column1.setCellValueFactory(cellData -> cellData.getValue().personProperty().get().firstNameProperty());
        column2.setCellValueFactory(cellData -> cellData.getValue().personProperty().get().lastNameProperty());
        column3.setCellValueFactory(cellData -> cellData.getValue().checkInProperty());
        column4.setCellValueFactory(cellData -> cellData.getValue().checkOutProperty());
    }

    @Override
    public void dispose(CubeEventModel event) {
        event.youthMemberVisitsProperty().removeListener(youthMemberPaneExpander);
        event.youthStaffVisitsProperty().removeListener(youthStaffPaneExpander);
        event.adultStaffVisitsProperty().removeListener(adultStaffPaneExpander);
        event.driverVisitsProperty().removeListener(driversPaneExpander);
    }

    private final static Duration timeToGetOld = Duration.seconds(1.5);

    private static class LabelCellFactory implements Callback<TableColumn<Visit, String>, TableCell<Visit, String>> {

        public TableCell<Visit, String> call(TableColumn<Visit, String> column) {

            final Label label = new Label();

            final TableCell<Visit, String> cell = new TableCell<Visit, String>() {

                protected void updateItem(String value, boolean empty) {
                    super.updateItem(value, empty);
                    label.setText(value);
                    label.setVisible(!empty);
                    if (!empty && value != null) {
                        getStyleClass().add("new");
                        PauseTransition agingTime = new PauseTransition(timeToGetOld);
                        agingTime.setCycleCount(1);
                        agingTime.play();
                        agingTime.setOnFinished(event -> {
                            getStyleClass().remove("new");
                        });
                    }
                }
            };

            cell.setGraphic(label);
            return cell;
        }
    }

    private static class DateCellFactory implements Callback<TableColumn<Visit, LocalDateTime>, TableCell<Visit, LocalDateTime>> {

        @Override
        public TableCell<Visit, LocalDateTime> call(TableColumn<Visit, LocalDateTime> param) {

            final Label label = new Label();

            final TableCell<Visit, LocalDateTime> cell = new TableCell<Visit, LocalDateTime>() {

                protected void updateItem(LocalDateTime value, boolean empty) {
                    super.updateItem(value, empty);
                    label.setVisible(!empty);
                    if (!empty && value != null) {
                        final String formattedDateTime = ofLocalizedDateTime(FormatStyle.SHORT).format(value);
                        label.setText(formattedDateTime);
                        getStyleClass().add("new");
                        PauseTransition agingTime = new PauseTransition(timeToGetOld);
                        agingTime.setCycleCount(1);
                        agingTime.play();
                        agingTime.setOnFinished(event -> {
                            getStyleClass().remove("new");
                        });
                    }
                }
            };

            cell.setGraphic(label);
            return cell;
        }
    }
}
