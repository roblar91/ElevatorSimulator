package knc.simulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import knc.simulator.controller.StartController;

public class Simulator extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/fxml/start.fxml"));
        loader.setController(new StartController());
        var root = (Parent) loader.load();
        var scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Elevator Simulator");
        stage.show();
    }
}
