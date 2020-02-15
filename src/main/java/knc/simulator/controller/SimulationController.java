package knc.simulator.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import knc.simulator.graphic.Storey;

public class SimulationController {
    @FXML
    private ImageView sun;
    @FXML
    private ImageView tree;
    @FXML
    private VBox building;

    public void initialize() {
        sun.setImage(new Image("/images/sun.png"));
        tree.setImage(new Image("/images/tree.png"));
    }

    public void createFloors(int floors) {
        var spacer = new Pane();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        building.getChildren().add(spacer);

        for(int i=0; i<floors; i++) {
            building.getChildren().add(new Storey());
        }
    }
}
