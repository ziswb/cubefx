package ch.bziswiler.cube.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public abstract class EventControllerScaffold implements ChangeListener<CubeEventModel> {

    private ObjectProperty<CubeEventModel> event;

    public ObjectProperty<CubeEventModel> getEventProperty() {
        if (this.event == null) {
            this.event = new SimpleObjectProperty();
            this.event.addListener(this);
        }
        return this.event;
    }

    @Override
    public void changed(ObservableValue<? extends CubeEventModel> observable, CubeEventModel oldValue, CubeEventModel newValue) {
        if (oldValue != null) {
            dispose(oldValue);
        }
        if (newValue != null) {
            initialize(newValue);
        }
    }

    protected abstract void initialize(CubeEventModel newValue);
    protected abstract void dispose(CubeEventModel oldValue);

    public final CubeEventModel getEvent() {
        return getEventProperty().get();
    }

    public final void setEvent(CubeEventModel event) {
        getEventProperty().set(event);
    }
}
