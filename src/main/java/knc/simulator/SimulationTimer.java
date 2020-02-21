package knc.simulator;

import javafx.animation.AnimationTimer;
import knc.simulator.controller.SimulationController;

public class SimulationTimer extends AnimationTimer {
    private final SimulationController simulationController;

    public SimulationTimer(SimulationController simulationController) {
        this.simulationController = simulationController;
    }

    @Override
    public void handle(long now) {
        simulationController.progressSimulation();
        simulationController.updateElevatorPosition();
    }
}
