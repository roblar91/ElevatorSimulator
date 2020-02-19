package knc.simulator.model;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * An {@link ElevatorManager} operates an {@link Elevator}.
 * Requests are sent to the elevator manager from both inside the elevator and from outside.
 * Requests will be processed on a first-in-first-out basis.
 */
public class ElevatorManager implements ElevatorActionListener {
    private final Queue<ElevatorRequest> elevatorRequests = new LinkedBlockingQueue<>();
    private final Elevator elevator;
    private final int lowestStorey;
    private final int highestStorey;

    /**
     * Constructs a {@link ElevatorManager} with an attached {@link Elevator} that can travel between the stories specified.
     * The attached elevator will be positioned at the lowest storey.
     * @param lowestStorey The level of the lowest storey
     * @param highestStorey The level of the highest storey
     * @throws IllegalArgumentException If lowestStorey >=  highestStorey
     */
    public ElevatorManager(int lowestStorey, int highestStorey) throws IllegalArgumentException {
        if(lowestStorey >= highestStorey)
            throw new IllegalArgumentException("The highest storey must be at least one level above the lowest storey");

        this.elevator = new Elevator(lowestStorey);
        this.lowestStorey = lowestStorey;
        this.highestStorey = highestStorey;

        elevator.registerListener(this);
    }

    /**
     * Send a request to this elevator manager.
     * Duplicate requests will be ignored.
     * @param targetStorey The requested destination storey
     * @throws IllegalArgumentException If target storey is outside of elevator range
     */
    public void createElevatorRequest(int targetStorey) throws IllegalArgumentException {
        if(targetStorey < lowestStorey || targetStorey > highestStorey)
            throw new IllegalArgumentException("Target storey outside elevator range");

        var newRequest = new ElevatorRequest(targetStorey);
        if(!elevatorRequests.contains(newRequest)) {
            elevatorRequests.add(newRequest);
            calculateNextTarget();
        }
    }

    /**
     * Returns the attached {@link Elevator}.
     * @return The elevator
     */
    public Elevator getElevator() {
        return elevator;
    }

    /**
     * Gets the number of requests queued up.
     * @return The number of requests
     */
    public int getElevatorRequestsSize() {
        return elevatorRequests.size();
    }

    /**
     * The status of the attached {@link Elevator} is progressed one cycle.
     */
    public void updateOneCycle() {
        elevator.update();
    }

    /**
     * The status of the attached {@link Elevator} is progressed until the next
     * {@link ElevatorAction#HOLD} or {@link ElevatorAction#IDLE} state is reached.
     */
    public void updateUntilNextHold() {
        // Update until elevator leaves hold
        while(elevator.getCurrentAction() == ElevatorAction.HOLD)
            elevator.update();

        // Update until next hold
        while(elevator.getCurrentAction() != ElevatorAction.HOLD && elevator.getCurrentAction() != ElevatorAction.IDLE)
            elevator.update();
    }

    @Override
    public void onChange(ElevatorAction newElevatorAction) {
        if(newElevatorAction == ElevatorAction.IDLE)
            calculateNextTarget();
    }

    private void calculateNextTarget() {
        if(!elevatorRequests.isEmpty() && elevator.getCurrentAction() == ElevatorAction.IDLE)
            elevator.setTargetStorey(elevatorRequests.remove().getTargetStorey());
    }
}
