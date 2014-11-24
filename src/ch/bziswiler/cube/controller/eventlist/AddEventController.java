package ch.bziswiler.cube.controller.eventlist;

import ch.bziswiler.cube.model.address.Address;
import ch.bziswiler.cube.model.address.City;
import ch.bziswiler.cube.model.presentation.CubeEventModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddEventController {

    @FXML
    private TextField name;
    @FXML
    private TextField street;
    @FXML
    private TextField city;
    @FXML
    private TextField addition;
    @FXML
    private TextField zip;

    private boolean ok;
    private CubeEventModel event;
    private AddDialogEventListener listener;

    @FXML
    private void handleSave() {
        this.ok = true;
        this.event.setEditing(false);
        if (this.listener != null) {
            this.listener.handleEventCreated(event);
        }
    }

    @FXML
    private void handleCancel() {
        this.ok = false;
        if (this.listener != null) {
            this.listener.handleEventCreationCancelled(event);
        }
    }

    public boolean isOk() {
        return ok;
    }

    public CubeEventModel getEvent() {
        return event;
    }

    public void setEvent(CubeEventModel model) {
        this.event = model;
        initialize();
    }

    private void initialize() {
        this.name.textProperty().bindBidirectional(event.nameProperty());
        event.setAddress(new Address());
        event.getAddress().setCity(new City());
        this.street.textProperty().bindBidirectional(event.addressProperty().get().streetNameProperty());
        this.addition.textProperty().bindBidirectional(event.addressProperty().get().additionProperty());
        this.zip.textProperty().bindBidirectional(event.addressProperty().get().cityProperty().get().zipProperty());
        this.city.textProperty().bindBidirectional(event.addressProperty().get().cityProperty().get().nameProperty());
    }

    public void setListener(AddDialogEventListener listener) {
        this.listener = listener;
    }
}
