package knc.simulator.view;

import javafx.scene.control.Control;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Storey extends VBox {
    public Storey() {
        setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));

        var roof = new Pane();
        roof.setPrefHeight(10);
        roof.setMinHeight(Control.USE_PREF_SIZE);
        roof.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));

        var corridor = new Pane();
        corridor.setPrefHeight(70);
        corridor.setMinHeight(Control.USE_PREF_SIZE);

        getChildren().addAll(roof, corridor);
    }
}
