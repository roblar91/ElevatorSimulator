package knc.simulator.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class StoreyController {
    @FXML
    private ImageView callButton;
    @FXML
    private ImageView door1;
    @FXML
    private ImageView door2;
    @FXML
    private Text storeyText;
    @FXML
    private Pane shaft;

    private final int storeyNumber;
    private Image inactiveButtonImage = new Image("images/button_inactive.png");
    private Image activeButtonImage = new Image("images/button_active.png");
    private Image doorImage = new Image("images/door.png");

    public StoreyController(int storeyNumber) {
        this.storeyNumber = storeyNumber;
    }

    public void initialize() {
        callButton.setImage(inactiveButtonImage);
        door1.setImage(doorImage);
        door2.setImage(doorImage);
        storeyText.setText(Integer.toString(storeyNumber));
    }

    public ImageView getCallButton() {
        return callButton;
    }

    public Pane getShaft() {
        return shaft;
    }

    public int getStoreyNumber() {
        return storeyNumber;
    }

    public void setButtonActiveState(boolean isActive) {
        if(isActive)
            callButton.setImage(activeButtonImage);
        else
            callButton.setImage(inactiveButtonImage);
    }
}
