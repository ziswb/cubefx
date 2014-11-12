package ch.bziswiler.cube.controller.eventlist;

import ch.bziswiler.cube.model.event.Event;
import ch.bziswiler.cube.model.presentation.CubeEventModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.util.Callback;

import java.io.IOException;

public class CubeEventListController {

    @FXML
    private ListView<Event> eventList;

    private final ObservableList<Event> items = FXCollections.observableArrayList();
    private CubeEventModel eventModel;
    private Event newEvent;

    @FXML
    private void initialize() {
        this.eventModel = new CubeEventModel();
        this.eventList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.eventList.selectionModelProperty().addListener(new ChangeListener<SelectionModel<Event>>() {
            @Override
            public void changed(ObservableValue<? extends SelectionModel<Event>> observable, SelectionModel<Event> oldValue, SelectionModel<Event> newValue) {
                if (newValue != null) {
                    eventModel.setEvent(newValue.getSelectedItem());
                }
            }
        });
        this.eventList.setCellFactory(new Callback<ListView<Event>, ListCell<Event>>() {
            @Override
            public ListCell<Event> call(ListView<Event> param) {
                return new EventDetailsListCell();
            }
        });
        items.add(event("Foo"));
        items.add(event("Bar"));
        items.add(event("Qux"));
        this.eventList.setItems(items);
    }

    private Event event(final String name) {
        return new Event() {
            @Override
            public String toString() {
                return name;
            }
        };
    }

    @FXML
    private void handleAddEvent() {
        this.newEvent = event("New");
        items.add(newEvent);
        eventList.setItems(items);
        eventList.getSelectionModel().select(newEvent);
        eventList.scrollTo(newEvent);
    }

    private class EventDetailsListCell extends ListCell<Event> {

        @Override
        protected void updateItem(Event item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                final Node page;
                final FXMLLoader loader = new FXMLLoader();
                String resource;
                if (newEvent == item) {
                    resource = "../../view/cubeEventEditDetails.fxml";
                } else {
                    resource = "../../view/cubeEventDetails.fxml";
                }
                loader.setLocation(getClass().getResource(resource));
                try {
                    page = loader.load();
                } catch (IOException e) {
                    final RuntimeException ex = new RuntimeException(e.getMessage());
                    ex.initCause(e.getCause());
                    throw ex;
                }
//                final Object controller = loader.getController();
                setGraphic(page);
            }
        }
    }
}
