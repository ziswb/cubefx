package ch.bziswiler.cube;

import ch.bziswiler.cube.controller.CubeEventModel;
import ch.bziswiler.cube.controller.EventControllerScaffold;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CubeFxApp extends Application {

    public static final CubeEventModel EVENT = new CubeEventModel();

    public CubeFxApp() {
        // nop
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        HBox root = (HBox) FXMLLoader.load(getClass().getResource("view/cubeRootLayout.fxml"));
        VBox scan = (VBox) loadResourceAndEventController("view/cubeScanMemberLayout.fxml");
        VBox overview = (VBox) loadResourceAndEventController("view/cubeEventOverviewLayout.fxml");
        HBox.setHgrow(scan, Priority.NEVER);
        HBox.setHgrow(overview, Priority.ALWAYS);
        overview.setFillWidth(true);

        primaryStage.setTitle("Cube FX");
        primaryStage.getIcons().add(new Image("file:resources/images/cube.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(1024);
        primaryStage.setMinHeight(680);
        primaryStage.show();
        root.getChildren().add(0, scan);
        root.getChildren().add(1, overview);

        AnchorPane duration = (AnchorPane) loadResourceAndEventController("view/cubeEventDurationLayout.fxml");
        AnchorPane numbers = (AnchorPane) loadResourceAndEventController("view/cubeEventNumbersLayout.fxml");
        Accordion tables = (Accordion) loadResourceAndEventController("view/cubeEventTablesLayout.fxml");

        VBox.setVgrow(duration, Priority.NEVER);
        VBox.setVgrow(numbers, Priority.NEVER);
        VBox.setVgrow(tables, Priority.ALWAYS);
        overview.getChildren().add(duration);
        overview.getChildren().add(numbers);
        overview.getChildren().add(tables);
    }

    private Object loadResourceAndEventController(String path) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CubeFxApp.class.getResource(path));
        Object pane = loader.load();
        EventControllerScaffold controller = loader.getController();
        controller.setModel(EVENT);
        return pane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
