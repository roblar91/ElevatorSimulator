package knc.simulator.model;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * An {@link ElevatorRequestManager} manages requests sent to an {@link Elevator}.
 * Requests will be processed on a first-in-first-out basis.
 */
public class ElevatorRequestManager implements ElevatorActionListener {
    private final Queue<ElevatorRequest> elevatorRequests = new LinkedBlockingQueue<>();
    private final Elevator elevator;
    /**
     * Constructs a {@link ElevatorRequestManager} with an attached {@link Elevator} that can travel between the stories specified.
     */
    public ElevatorRequestManager(Elevator elevator) throws IllegalArgumentException {
        this.elevator = elevator;
        elevator.registerListener(this);
    }

    /**
     * Send a request to this elevator manager.
     * Duplicate requests will be ignored.
     * @param targetStorey The requested destination storey
     * @throws IllegalArgumentException If target storey is outside of elevator range
     */
    public void createElevatorRequest(int targetStorey) throws IllegalArgumentException {
        if(targetStorey < elevator.getLowestStorey() || targetStorey > elevator.getHighestStorey())
            throw new IllegalArgumentException("Target storey outside elevator range");

        var newRequest = new ElevatorRequest(targetStorey);
        if(!elevatorRequests.contains(newRequest)) {
            elevatorRequests.add(newRequest);
            calculateNextTarget();
        }
    }

    /**
     * Gets the number of requests queued up.
     * @return The number of requests
     */
    public int getElevatorRequestsSize() {
        return elevatorRequests.size();
    }

    /**
     * Returns the attached {@link Elevator}.
     * @return The elevator
     */
    public Elevator getElevator() {
        return elevator;
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
