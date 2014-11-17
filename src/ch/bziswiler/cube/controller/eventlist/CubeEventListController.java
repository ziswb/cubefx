package ch.bziswiler.cube.controller.eventlist;

import ch.bziswiler.cube.model.event.Event;
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
import javafx.util.Callback;

import java.io.IOException;

public class CubeEventListController {

    @FXML
    private ListView<Event> eventList;

    private final ObservableList<Event> items = FXCollections.observableArrayList();
    private Event newEvent;

    @FXML
    private void initialize() {
        this.eventList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.eventList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Event>() {
            @Override
            public void changed(ObservableValue<? extends Event> observable, Event oldValue, Event newValue) {
                System.out.println("observable = " + observable);
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
        items.add(0, newEvent);
        eventList.setItems(items);
        eventList.getSelectionModel().select(newEvent);
        eventList.scrollTo(newEvent);
    }

    private class EventDetailsListCell extends ListCell<Event> {

        @Override
        public void startEdit() {
            super.startEdit();
            if (!isEditable()) {
                return;
            }
            if (!eventList.isEditable()) {
                return;
            }
            if (isEditing()) {
                System.out.println("true = " + true);
            }
            System.out.println("true = " + true);
        }

        @Override
        protected void updateItem(Event item, boolean empty) {
            super.updateItem(item, empty);
            if (isEmpty()) {
                setText(null);
                setGraphic(null);
                return;
            }
            System.out.println("item = " + item);
            if (item != null) {
                final Node page;
                final FXMLLoader loader = new FXMLLoader();
                String resource;
                if (newEvent == item) {
                    resource = "../../view/cubeEventEditDetails.fxml";
                    startEdit();
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
