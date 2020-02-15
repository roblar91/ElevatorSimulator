package knc.simulator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Simulator extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var root = new Pane();
        var scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Elevator Simulator");
        stage.show();
    }
}
