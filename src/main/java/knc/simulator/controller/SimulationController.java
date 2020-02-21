package knc.simulator.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import knc.simulator.model.ElevatorManager;

public class SimulationController {
    @FXML
    private Pane root;
    @FXML
    private ImageView sun;
    @FXML
    private ImageView tree;
    @FXML
    private VBox building;

    private StoreyController[] storeyControllers;
    private ElevatorManager elevatorManager;
    private ElevatorController elevatorController;
    private final int storeys;

    public SimulationController(int storeys) {
        this.storeys = storeys;
        storeyControllers = new StoreyController[storeys];
    }

    public void initialize() {
        sun.setImage(new Image("/images/sun.png"));
        tree.setImage(new Image("/images/tree.png"));

        createSpacer();
        createStoreys();
    }

    public void startSimulation() {
        elevatorManager = new ElevatorManager(1, storeys);
        createElevator();
    }

    private void createSpacer() {
        var spacer = new Pane();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        building.getChildren().add(spacer);
    }

    private void createStoreys() {
        try {
            for(int i = storeys; i > 0; i--) {
                var loader = new FXMLLoader(getClass().getResource("/fxml/storey.fxml"));
                var controller = new StoreyController(i);
                loader.setController(controller);
                building.getChildren().add(loader.load());
                storeyControllers[i-1] = controller;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void createElevator() {
        try {
            var loader = new FXMLLoader(getClass().getResource("/fxml/elevator.fxml"));
            elevatorController = new ElevatorController();
            loader.setController(elevatorController);
            root.getChildren().add(loader.load());

            // Place elevator at bottom shaft
            var bottomShaft = storeyControllers[0].getShaft();
            var boundsInScene = bottomShaft.localToScene(bottomShaft.getBoundsInLocal());
            elevatorController.setTranslate(boundsInScene.getMinX(), boundsInScene.getMinY());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
