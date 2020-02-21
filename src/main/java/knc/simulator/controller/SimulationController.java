package knc.simulator.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class SimulationController {
    @FXML
    private ImageView sun;
    @FXML
    private ImageView tree;
    @FXML
    private VBox building;

    private List<StoreyController> storeyControllers = new ArrayList<>();
    private final int storeys;

    public SimulationController(int storeys) {
        this.storeys = storeys;
    }

    public void initialize() {
        sun.setImage(new Image("/images/sun.png"));
        tree.setImage(new Image("/images/tree.png"));

        createSpacer();
        createStoreys();
    }

    private void createSpacer() {
        var spacer = new Pane();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        building.getChildren().add(spacer);
    }

    private void createStoreys() {
        try {
            for(int i = storeys; i > 0; i--) {
                var loader = new FXMLLoader(getClass().getResource("/fxml/storey.fxml"));
                var controller = new StoreyController(i);
                loader.setController(controller);
                building.getChildren().add(loader.load());
                storeyControllers.add(controller);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
