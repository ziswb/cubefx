package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.model.presentation.CubeEventModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public abstract class CubeEventControllerScaffold implements ChangeListener<CubeEventModel> {

    private ObjectProperty<CubeEventModel> model;

    @Override
    public void changed(ObservableValue<? extends CubeEventModel> observable, CubeEventModel oldValue, CubeEventModel newValue) {
        if (oldValue != null) {
            dispose(oldValue);
        }
        if (newValue != null) {
            initialize(newValue);
        }
    }

    protected abstract void dispose(CubeEventModel oldValue);

    protected abstract void initialize(CubeEventModel newValue);

    public final CubeEventModel getModel() {
        return getModelProperty().get();
    }

    public ObjectProperty<CubeEventModel> getModelProperty() {
        if (this.model == null) {
            this.model = new SimpleObjectProperty<>();
            this.model.addListener(this);
        }
        return this.model;
    }

    public final void setModel(CubeEventModel model) {
        getModelProperty().set(model);
    }
}
