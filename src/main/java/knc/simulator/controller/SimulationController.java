package knc.simulator.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SimulationController {
    @FXML
    private ImageView sun;
    @FXML
    private ImageView tree;

    public void initialize() {
        sun.setImage(new Image("/images/sun.png"));
        tree.setImage(new Image("/images/tree.png"));
    }
}
