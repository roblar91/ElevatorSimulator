package knc.simulator.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ElevatorController {
    @FXML private AnchorPane root;
    @FXML private Pane elevatorPane;

    public void setTranslate(double x, double y) {
        root.setTranslateX(x);
        root.setTranslateY(y);
    }
}
