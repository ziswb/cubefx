package ch.bziswiler.cube.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public abstract class EventControllerScaffold implements ChangeListener<CubeEventModel> {

    private ObjectProperty<CubeEventModel> model;
    private ObjectProperty<EventStatistics> stats;

    public ObjectProperty<CubeEventModel> getModelProperty() {
        if (this.model == null) {
            this.model = new SimpleObjectProperty();
            this.model.addListener(this);
        }
        return this.model;
    }

    public ObjectProperty<EventStatistics> getStatsProperty() {
        if (this.stats == null) {
            this.stats = new SimpleObjectProperty();
        }
        return this.stats;
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

    public final CubeEventModel getModel() {
        return getModelProperty().get();
    }

    public final void setModel(CubeEventModel model) {
        getModelProperty().set(model);
    }
}
