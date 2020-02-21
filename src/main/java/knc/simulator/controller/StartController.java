package knc.simulator.controller;

import com.sun.javafx.collections.ImmutableObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import knc.simulator.Simulator;

public class StartController {
    private Simulator simulator;

    @FXML
    private ChoiceBox<Integer> storeysChoice;
    @FXML
    private Button createButton;

    public void initialize() {
        var availableFloors = new ImmutableObservableList<Integer>(2, 3, 4, 5, 6);
        storeysChoice.setItems(availableFloors);
        storeysChoice.setValue(availableFloors.get(0));
    }

    public Button getCreateButton() {
        return createButton;
    }

    public int getStoreyCount() {
        return storeysChoice.getValue();
    }
}
