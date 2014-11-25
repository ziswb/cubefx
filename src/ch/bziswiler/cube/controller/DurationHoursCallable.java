package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.model.presentation.CubeEventModel;

import java.util.concurrent.Callable;

public class DurationHoursCallable implements Callable<String> {
    private final CubeEventModel newValue;

    public DurationHoursCallable(CubeEventModel newValue) {
        this.newValue = newValue;
    }

    @Override
    public String call() throws Exception {
        final Long hours = newValue.hoursOfEventDurationProperty().get();
        String result = "";
        if (hours == null) {
            result = "0";
        } else {
            result = hours.toString();
        }
        return result + "h ";
    }
}
