package ch.bziswiler.cube.data;

import ch.bziswiler.cube.controller.CubeEventControllerScaffold;
import ch.bziswiler.cube.model.event.Event;
import ch.bziswiler.cube.model.presentation.CubeEventModel;
import com.google.common.collect.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.List;

public class CubeEventModelSelectionProvider {

    public static final CubeEventModelSelectionProvider INSTANCE = new CubeEventModelSelectionProvider();
    private final ObservableList<CubeEventModel> events;
    private List<CubeEventControllerScaffold> listeners;
    private CubeEventModel selectedEvent;

    private CubeEventModelSelectionProvider() {
        this.listeners = Lists.newArrayList();
        this.events = FXCollections.observableArrayList();
        this.events.addListener(new CubeEventModelListChangeListener());
        loadEvents();
    }

    private void loadEvents() {
    }

    public ObservableList<CubeEventModel> getEvents() {
        return events;
    }

    public void addListener(CubeEventControllerScaffold listener) {
        this.listeners.add(listener);
    }

    public void setSelectedEvent(CubeEventModel selectedEvent) {
        this.selectedEvent = selectedEvent;
        for (CubeEventControllerScaffold listener : this.listeners) {
            listener.setModel(selectedEvent);
        }
    }

    private class CubeEventModelListChangeListener implements ListChangeListener<CubeEventModel> {

        @Override
        public void onChanged(Change<? extends CubeEventModel> c) {
            while (c.next()) {
                if (c.wasAdded()) {
                    System.out.println("c.getAddedSubList() = " + c.getAddedSubList());
                } else if (c.wasPermutated()) {
                    System.out.println("c.getPermutation(0) = " + c.getPermutation(0));
                } else if (c.wasRemoved()) {
                    System.out.println("c.getRemoved() = " + c.getRemoved());
                } else if (c.wasReplaced()) {
                    System.out.println("c.wasReplaced() = " + c.wasReplaced());
                } else if (c.wasUpdated()) {
                    System.out.println("c.wasUpdated() = " + c.wasUpdated());
                }
            }
        }
    }
}
