package ch.bziswiler.cube;

import ch.bziswiler.cube.controller.CubeEventControllerScaffold;
import ch.bziswiler.cube.controller.CubeRootLayoutController;
import ch.bziswiler.cube.model.presentation.CubeEventModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CubeFxApp extends Application {

    public static final CubeEventModel EVENT = new CubeEventModel();
    private static CubeFxApp INSTANCE;
    private Stage primaryStage;

    public CubeFxApp() {
        INSTANCE = this;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static CubeFxApp getInstance() {
        return INSTANCE;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        VBox root = loadRoot();
        VBox scan = (VBox) loadResourceAndSetEventOnController("view/cubeScanMemberLayout.fxml");
        VBox overview = (VBox) loadResourceAndSetEventOnController("view/cubeEventOverviewLayout.fxml");
        HBox.setHgrow(scan, Priority.NEVER);
        HBox.setHgrow(overview, Priority.ALWAYS);

        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cube FX");
        primaryStage.getIcons().add(new Image("file:resources/images/cube.png"));
        primaryStage.setMinWidth(1024);
        primaryStage.setMinHeight(680);
        primaryStage.show();
        HBox container = (HBox) root.getChildren().get(1);
        container.getChildren().add(0, scan);
        container.getChildren().add(1, overview);

        Region duration = (Region) loadResourceAndSetEventOnController("view/cubeEventDurationLayout.fxml");
        Region numbers = (Region) loadResourceAndSetEventOnController("view/cubeEventNumbersLayout.fxml");
        Region tables = (Region) loadResourceAndSetEventOnController("view/cubeEventTablesLayout.fxml");

        VBox.setVgrow(duration, Priority.NEVER);
        VBox.setVgrow(numbers, Priority.NEVER);
        VBox.setVgrow(tables, Priority.ALWAYS);
        overview.getChildren().add(duration);
        overview.getChildren().add(numbers);
        overview.getChildren().add(tables);
    }

    private VBox loadRoot() throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CubeFxApp.class.getResource("view/cubeRootLayout.fxml"));
        VBox pane = loader.load();
        return pane;
    }

    private Object loadResourceAndSetEventOnController(String path) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CubeFxApp.class.getResource(path));
        Object pane = loader.load();
        CubeEventControllerScaffold controller = loader.getController();
        controller.setModel(EVENT);
        return pane;
    }
}
