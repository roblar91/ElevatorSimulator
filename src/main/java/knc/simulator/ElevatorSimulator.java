package knc.simulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import knc.simulator.controller.SimulationController;
import knc.simulator.controller.StartController;

public class ElevatorSimulator extends Application {
    private Stage stage;
    private StartController startController;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("Elevator Simulator");
        stage.show();

        showStartScene();
    }

    private void showStartScene() {
        try {
            var loader = new FXMLLoader(getClass().getResource("/fxml/start.fxml"));
            startController = new StartController();
            loader.setController(startController);
            stage.setScene(new Scene(loader.load()));

            startController.getCreateButton().setOnAction(event -> showSimulationScene(startController.getStoreyCount()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void showSimulationScene(int value) {
        try {
            var loader = new FXMLLoader(getClass().getResource("/fxml/simulation.fxml"));
            var controller = new SimulationController(value);
            loader.setController(controller);
            stage.setScene(new Scene(loader.load()));
            controller.startSimulation();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
