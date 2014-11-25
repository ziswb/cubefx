package ch.bziswiler.cube.controller.eventlist;

import ch.bziswiler.cube.data.EventDAO;
import ch.bziswiler.cube.model.event.Event;
import ch.bziswiler.cube.model.presentation.CubeEventModel;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.concurrent.Callable;

public class CubeEventListController {

    private final ObservableList<CubeEventModel> items = FXCollections.observableArrayList();
    @FXML
    private ListView<CubeEventModel> eventList;
    @FXML
    private ChoiceBox<CubeEventModelComparator.SortField> sortFieldChoice;
    @FXML
    private Button sortUpButton;
    @FXML
    private Button sortDownButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button okButton;

    private Stage dialogStage;
    private CubeEventModel selectedEvent;
    private boolean ok;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        this.eventList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.eventList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CubeEventModel>() {
            @Override
            public void changed(ObservableValue<? extends CubeEventModel> observable, CubeEventModel oldValue, CubeEventModel newValue) {
                selectedEvent = newValue;
            }
        });
        this.eventList.setCellFactory(new Callback<ListView<CubeEventModel>, ListCell<CubeEventModel>>() {
            @Override
            public ListCell<CubeEventModel> call(ListView<CubeEventModel> param) {
                return new EventDetailsListCell();
            }
        });
        for (Event event : EventDAO.INSTANCE.getEvents()) {
            items.add(event(event));
        }
        this.eventList.setItems(items);
        sortFieldChoice.setItems(FXCollections.observableArrayList(CubeEventModelComparator.SortField.values()));
        sortFieldChoice.getSelectionModel().select(0);
        this.sortUpButton.disableProperty().bind(Bindings.createBooleanBinding(new HasItemsCallable(), this.eventList.getSelectionModel().selectedItemProperty()));
        this.sortDownButton.disableProperty().bind(Bindings.createBooleanBinding(new HasItemsCallable(), this.eventList.getSelectionModel().selectedItemProperty()));
        this.okButton.disableProperty().bind(Bindings.createBooleanBinding(new HasSelectionCallable(), this.eventList.getSelectionModel().selectedItemProperty()));
    }

    private CubeEventModel event(final Event event) {
        return new CubeEventModel(event) {
            @Override
            public String toString() {
                return event.toString();
            }
        };
    }

    @FXML
    private void handleSortAscending() {
        eventList.setItems(
                eventList.getItems().sorted(
                        new CubeEventModelComparator(
                                sortFieldChoice.getSelectionModel().getSelectedItem(),
                                CubeEventModelComparator.SortDirection.ASC)
                )
        );
    }

    @FXML
    private void handleSortDescending() {
        eventList.setItems(
                eventList.getItems().sorted(
                        new CubeEventModelComparator(
                                sortFieldChoice.getSelectionModel().getSelectedItem(),
                                CubeEventModelComparator.SortDirection.DESC
                        )
                )
        );
    }

    @FXML
    private void handleCancel() {
        this.ok = false;
        this.dialogStage.close();
    }

    @FXML
    private void handleOk() {
        this.ok = true;
        this.dialogStage.close();
    }

    @FXML
    private void handleAddEvent() {
        final CubeEventModel newEvent = event(new Event() {
            @Override
            public String toString() {
                return "New";
            }
        });
        newEvent.setEditMode(CubeEventModel.EditMode.EDIT_REQUESTED);
        items.add(0, newEvent);
        eventList.setItems(items);
        eventList.getSelectionModel().select(newEvent);
        eventList.scrollTo(newEvent);
    }

    public CubeEventModel getSelectedEvent() {
        return selectedEvent;
    }

    public boolean isOK() {
        return ok;
    }

    private class EventDetailsListCell extends ListCell<CubeEventModel> {

        private Node node = null;

        @Override
        protected void updateItem(CubeEventModel item, boolean empty) {
            super.updateItem(item, empty);
            if (isEmpty()) {
                setText(null);
                setGraphic(null);
                return;
            }
            if (item == null) {
                return;
            }
            switch (item.getEditMode()) {
                case EDITING:
                case EDIT_REQUESTED:
                    item.setEditMode(CubeEventModel.EditMode.EDITING);
                    startEdit(item);
                    break;
                case CANCELLED:
                    setText(null);
                    setGraphic(null);
                    items.remove(item);
                    eventList.setItems(items);
                    return;
                case COMMITED:
                case DEFAULT:
                default:
                    final FXMLLoader loader = loadResouce("../../view/cubeEventDetails.fxml");
                    final ShowEventDetailsController controller = loader.getController();
                    controller.setEvent(item);
                    break;

            }
            setGraphic(node);
        }

        private void startEdit(CubeEventModel item) {
            if (!isEditable()) {
                return;
            }
            if (!eventList.isEditable()) {
                return;
            }
            super.startEdit();
            if (!isEditing()) {
                return;
            }
            final FXMLLoader loader = loadResouce("../../view/cubeEventEditDetails.fxml");
            final AddEventController controller = loader.getController();
            controller.setEvent(item);
            controller.setCell(this);
        }

        private FXMLLoader loadResouce(String resource) {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(resource));
            try {
                node = loader.load();
            } catch (IOException e) {
                final RuntimeException ex = new RuntimeException(e.getMessage());
                ex.initCause(e.getCause());
                throw ex;
            }
            return loader;
        }

        @Override
        public void commitEdit(CubeEventModel newValue) {
            newValue.setEditMode(CubeEventModel.EditMode.COMMITED);
            EventDAO.INSTANCE.addEvent(newValue.getEvent());
            super.commitEdit(newValue);
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
        }

    }

    private class HasSelectionCallable implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            return eventList.getSelectionModel().selectedItemProperty().get() == null;
        }
    }

    private class HasItemsCallable implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            return items.size() == 0;
        }
    }
}
