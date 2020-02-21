package knc.simulator.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import knc.simulator.SimulationTimer;
import knc.simulator.model.Elevator;
import knc.simulator.model.ElevatorRequestManager;

public class SimulationController {
    @FXML
    private Pane root;
    @FXML
    private ImageView sun;
    @FXML
    private ImageView tree;
    @FXML
    private VBox building;

    private final int storeys;
    private Elevator elevator;
    private ElevatorRequestManager elevatorRequestManager;
    private ElevatorController elevatorController;
    private StoreyController[] storeyControllers;
    private SimulationTimer simulationTimer;

    public SimulationController(int storeys) {
        this.storeys = storeys;
    }

    public void startSimulation() {
        elevator = new Elevator(1, storeys);
        elevatorRequestManager = new ElevatorRequestManager(elevator);
        storeyControllers = new StoreyController[storeys];
        simulationTimer = new SimulationTimer(this);

        sun.setImage(new Image("/images/sun.png"));
        tree.setImage(new Image("/images/tree.png"));

        createSpacer();
        createStoreys();
        createElevator();

        simulationTimer.start();
    }

    public void updateElevatorPosition() {
        // Place elevator relative to bottom shaft
        var bottomShaft = storeyControllers[0].getShaft();
        var boundsInScene = bottomShaft.localToScene(bottomShaft.getBoundsInLocal());

        var offsetY = elevator.getElevatorPositionAsStoriesFromBottom() * bottomShaft.getHeight();
        elevatorController.setTranslate(boundsInScene.getMinX(), boundsInScene.getMinY() - offsetY);
    }

    public void progressSimulation() {
        elevator.update();
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

                controller.getCallButton().setOnMouseClicked( e -> {
                    System.out.println("Clicked on " + controller.getStoreyNumber());
                    elevatorRequestManager.createElevatorRequest(controller.getStoreyNumber());
                });
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
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
