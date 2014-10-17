package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.model.event.Visit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

import java.time.LocalDateTime;

public class PaneExpander implements ListChangeListener<Visit>, ChangeListener<LocalDateTime> {

    private final TitledPane pane;
    private final Accordion accordion;

    public PaneExpander(TitledPane pane, Accordion accordion) {
        this.pane = pane;
        this.accordion = accordion;
    }


    @Override
    public void changed(ObservableValue<? extends LocalDateTime> observable, LocalDateTime oldValue, LocalDateTime newValue) {
        accordion.setExpandedPane(pane);
    }

    @Override
    public void onChanged(Change<? extends Visit> c) {
        accordion.setExpandedPane(pane);
        while (c.next()) {
            if (c.wasAdded()) {
                c.getAddedSubList().get(0).checkOutProperty().addListener(this);
            } else if (c.wasUpdated()) {
                // nop
            } else if (c.wasRemoved()) {
                c.getRemoved().get(0).checkOutProperty().removeListener(this);
            } else if (c.wasPermutated()) {
                // nop
            } else if (c.wasReplaced()) {
                // nop
            }
        }
    }
}
