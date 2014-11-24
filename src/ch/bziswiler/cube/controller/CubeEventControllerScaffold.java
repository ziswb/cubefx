package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.data.CubeEventModelSelectionProvider;
import ch.bziswiler.cube.model.presentation.CubeEventModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

public abstract class CubeEventControllerScaffold implements ChangeListener<CubeEventModel> {

    private ObjectProperty<CubeEventModel> model;

    @Override
    public void changed(ObservableValue<? extends CubeEventModel> observable, CubeEventModel oldValue, CubeEventModel newValue) {
        if (oldValue != null) {
            disposeModel(oldValue);
        }
        if (newValue != null) {
            initializeModel(newValue);
        }
    }

    @FXML
    public void initialize() {
        CubeEventModelSelectionProvider.INSTANCE.addListener(this);
        doInitialize();
    }

    protected abstract void doInitialize();

    protected abstract void disposeModel(CubeEventModel oldValue);

    protected abstract void initializeModel(CubeEventModel newValue);

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
