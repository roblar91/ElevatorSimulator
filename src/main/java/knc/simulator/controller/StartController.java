package knc.simulator.controller;

import com.sun.javafx.collections.ImmutableObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import knc.simulator.Simulator;

import java.io.IOException;

public class StartController {
    private Simulator simulator;

    @FXML
    private ChoiceBox<Integer> floorsChoice;
    @FXML
    private Button createButton;

    public StartController(Simulator simulator) {
        this.simulator = simulator;
    }

    public void initialize() {
        var availableFloors = new ImmutableObservableList<Integer>(2, 3, 4, 5, 6, 7, 8, 9, 10);
        floorsChoice.setItems(availableFloors);
        floorsChoice.setValue(availableFloors.get(0));
    }

    public void createSimulation() throws IOException {
        simulator.createSimulation(floorsChoice.getValue());
    }
}
