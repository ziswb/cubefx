package ch.bziswiler.cube.controller;

import ch.bziswiler.cube.model.presentation.CubeEventModel;

import java.util.concurrent.Callable;

public class DurationMinutesCallable implements Callable<String> {
    private final CubeEventModel newValue;

    public DurationMinutesCallable(CubeEventModel newValue) {
        this.newValue = newValue;
    }

    @Override
    public String call() throws Exception {
        final Long minutes = newValue.minutesOfEventDurationProperty().get();
        String result = "";
        if (minutes == null) {
            result = "0";
        } else {
            result = minutes.toString();
        }
        return result + " min";
    }
}
