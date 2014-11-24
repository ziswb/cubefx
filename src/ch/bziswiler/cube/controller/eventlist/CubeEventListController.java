package ch.bziswiler.cube.controller.eventlist;

import ch.bziswiler.cube.data.EventDAO;
import ch.bziswiler.cube.model.event.Event;
import ch.bziswiler.cube.model.presentation.CubeEventModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class CubeEventListController {

    private final ObservableList<CubeEventModel> items = FXCollections.observableArrayList();
    @FXML
    private ListView<CubeEventModel> eventList;
    @FXML
    private ChoiceBox<String> sortFieldChoice;
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
        sortFieldChoice.setItems(FXCollections.observableArrayList("Start", "End", "Ort"));
        sortFieldChoice.getSelectionModel().select(0);
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
        eventList.getItems().sort(new CubeEventModelComparator(-1));
    }

    @FXML
    private void handleSortDescending() {
        eventList.getItems().sort(new CubeEventModelComparator(1));
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
                    loadResouce("../../view/cubeEventDetails.fxml");
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
}