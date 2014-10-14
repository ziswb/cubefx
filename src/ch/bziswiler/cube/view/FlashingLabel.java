package ch.bziswiler.cube.view;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class FlashingLabel extends Label {
    private FadeTransition animation;

    public FlashingLabel() {
//        Node walker = this;
//        String indent = "";
//        while (walker != null) {
//            System.out.println(indent + "walker = " + walker);
//            walker = walker.getParent();
//            indent = " " + indent;
//        }
        getStyleClass().add("new");
        animation = new FadeTransition(Duration.millis(1500), this);
        animation.setFromValue(0);
        animation.setToValue(1);
        animation.setCycleCount(1);
        animation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setStyle("-fx-background-color: #4F4F4F; -fx-text-inner-color: white;");
            }
        });
        animation.play();

        visibleProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    animation.playFromStart();
                } else {
                    animation.stop();
                }
            }
        });
    }
}