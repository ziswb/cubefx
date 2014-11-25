package ch.bziswiler.cube.controller.eventlist;

import ch.bziswiler.cube.controller.DurationHoursCallable;
import ch.bziswiler.cube.controller.DurationMinutesCallable;
import ch.bziswiler.cube.controller.LocalDateTimeToStringCallable;
import ch.bziswiler.cube.model.presentation.CubeEventModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ShowEventDetailsController {

    @FXML
    private Label name;
    @FXML
    private Label street;
    @FXML
    private Label city;
    @FXML
    private Label numberOfYouths;
    @FXML
    private Label numberOfAdults;
    @FXML
    private Label start;
    @FXML
    private Label end;
    @FXML
    private Label duration;

    private CubeEventModel event;

    public void setEvent(CubeEventModel model) {
        this.event = model;
        initialize();
    }

    private void initialize() {
        this.name.textProperty().bind(event.nameProperty());
        if (event.getAddress() != null) {
            this.street.textProperty().bind(event.addressProperty().get().streetNameProperty());
            if (event.getAddress().getCity() != null) {
                this.city.textProperty().bind(event.addressProperty().get().cityProperty().get().nameProperty());
            }
        }
        final NumberBinding numberOfAllYouths = event.getStats().numberOfAllYouthMembersProperty().add(event.getStats().numberOfAllYouthStaffProperty());
        final NumberBinding numberOfAllAdults = event.getStats().numberOfAllAdultStaffProperty().add(event.getStats().numberOfAllDriversProperty());
        this.numberOfYouths.textProperty().bind(numberOfAllYouths.asString());
        this.numberOfAdults.textProperty().bind(numberOfAllAdults.asString());
        final StringBinding startBinding = Bindings.createStringBinding(new LocalDateTimeToStringCallable(event.startProperty()));
        final StringBinding endBinding = Bindings.createStringBinding(new LocalDateTimeToStringCallable(event.endProperty()));
        this.start.textProperty().bind(startBinding);
        this.end.textProperty().bind(endBinding);
        final StringBinding hoursBinding = Bindings.createStringBinding(new DurationHoursCallable(event), event.hoursOfEventDurationProperty());
        final StringBinding minutesBinding = Bindings.createStringBinding(new DurationMinutesCallable(event), event.minutesOfEventDurationProperty());
        this.duration.textProperty().bind(Bindings.concat(hoursBinding, minutesBinding));
    }
}
