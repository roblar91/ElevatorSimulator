package knc.simulator.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class ElevatorController {
    @FXML private Pane root;
    @FXML private Pane elevatorPane;
    @FXML private Pane door;

    public void initialize() {
        door.setVisible(false);
    }

    public void setTranslate(double x, double y) {
        root.setTranslateX(x);
        root.setTranslateY(y);
    }

    public void setDoorVisibility(boolean isOpen) {
        door.setVisible(isOpen);
    }
}
