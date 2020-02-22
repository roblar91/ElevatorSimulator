package knc.simulator.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import knc.simulator.SimulationTimer;
import knc.simulator.model.Elevator;
import knc.simulator.model.ElevatorAction;
import knc.simulator.model.ElevatorActionListener;
import knc.simulator.model.ElevatorRequestManager;

public class SimulationController implements ElevatorActionListener {
    @FXML
    private Pane root;
    @FXML
    private ImageView sun;
    @FXML
    private ImageView tree;
    @FXML
    private VBox building;
    @FXML
    private Text simulationStatusText;
    @FXML
    private Text currentActionText;
    @FXML
    private Text queueSizeText;
    @FXML
    private Text currentTargetText;

    private final int storeys;
    private Elevator elevator;
    private ElevatorRequestManager elevatorRequestManager;
    private ElevatorController elevatorController;
    private StoreyController[] storeyControllers;
    private SimulationTimer simulationTimer;
    private SimulationStatus simulationStatus;

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

        elevator.registerListener(this);
        simulationStatus = SimulationStatus.RUNNING;
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
        updateStatusBar();
    }

    @Override
    public void onChange(ElevatorAction newElevatorAction) {
        if(newElevatorAction == ElevatorAction.HOLD)
            storeyControllers[elevator.getCurrentStorey()-1].setButtonActiveState(false);
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
                    elevatorRequestManager.createElevatorRequest(controller.getStoreyNumber());
                    controller.setButtonActiveState(true);
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

    private void updateStatusBar() {
        simulationStatusText.setText(simulationStatus.toString());
        currentActionText.setText(elevator.getCurrentAction().toString());
        queueSizeText.setText(String.valueOf(elevatorRequestManager.getElevatorRequestsSize()));
        currentTargetText.setText(String.valueOf(elevator.getTargetStorey()));
    }

    private enum SimulationStatus {
        RUNNING,
        PAUSED;
    }
}
