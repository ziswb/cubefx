package ch.bziswiler.cube.controller.eventlist;

import ch.bziswiler.cube.model.presentation.CubeEventModel;

public interface AddDialogEventListener {

    void handleEventCreated(CubeEventModel model);

    void handleEventCreationCancelled(CubeEventModel model);
}
