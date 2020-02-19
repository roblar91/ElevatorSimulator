package knc.simulator.model;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * An {@link ElevatorManager} operates an {@link Elevator}.
 * Requests are sent to the elevator manager from both inside the elevator and from outside.
 */
public class ElevatorManager {
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
    }

    /**
     * Send a request to this elevator manager.
     * Duplicate requests will be ignored.
     * @param targetStorey The requested destination storey
     * @throws IllegalArgumentException If target storey is outside of elevator range
     */
    public void createInternalRequest(int targetStorey) throws IllegalArgumentException {
        if(targetStorey < lowestStorey || targetStorey > highestStorey)
            throw new IllegalArgumentException("Target storey outside elevator range");

        var newRequest = new ElevatorRequest(targetStorey);
        if(!elevatorRequests.contains(newRequest))
            elevatorRequests.add(newRequest);
    }

    public Elevator getElevator() {
        return elevator;
    }

    public int getElevatorRequestsSize() {
        return elevatorRequests.size();
    }

    private void calculateNextTarget() {

    }
}
