package knc.simulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import knc.simulator.controller.SimulationController;
import knc.simulator.controller.StartController;

import java.io.IOException;

public class Simulator extends Application {
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("Elevator Simulator");
        stage.show();

        createStart();
    }

    private void createStart() throws IOException {
        var loader = new FXMLLoader(getClass().getResource("/fxml/start.fxml"));
        loader.setController(new StartController(this));
        var root = (Parent) loader.load();
        stage.setScene(new Scene(root));
    }

    public void createSimulation(Integer value) throws IOException {
        System.out.println("Create sim with " + value + " floors");
        var loader = new FXMLLoader(getClass().getResource("/fxml/simulation.fxml"));
        loader.setController(new SimulationController());
        var root = (Parent) loader.load();
        stage.setScene(new Scene(root));
    }
}
