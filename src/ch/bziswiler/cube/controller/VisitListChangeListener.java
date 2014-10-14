package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.model.event.Visit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;

import java.time.LocalDateTime;

public class VisitListChangeListener implements ListChangeListener<Visit>, ChangeListener<LocalDateTime> {

    @Override
    public void changed(ObservableValue<? extends LocalDateTime> observable, LocalDateTime oldValue, LocalDateTime newValue) {
        System.out.println("observable = " + observable);
    }

    @Override
    public void onChanged(Change<? extends Visit> c) {
        while (c.next()) {
            if (c.wasAdded()) {
                c.getAddedSubList().get(0).checkOutProperty().addListener(this);
            } else if (c.wasUpdated()) {
                System.out.println("updated");
            } else if (c.wasRemoved()) {
                c.getRemoved().get(0).checkOutProperty().removeListener(this);
            } else if (c.wasPermutated()) {
                System.out.println("permutated");
            } else if (c.wasReplaced()) {
                System.out.println("replaced");
            }
        }
    }
}
