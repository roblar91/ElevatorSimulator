package knc.simulator.model;

import java.util.*;

/**
 * Represent an elevator in a building.
 * Requests are sent to the elevator from both inside the elevator and from outside.
 */
public class Elevator {
    private final int lowestStorey;
    private final int highestStorey;
    private Direction currentDirection;
    private int currentStorey;
    private int currentTargetStorey;

    // Requests originating from outside the elevator;
    private Set<ExternalRequest> externalRequests = new HashSet<>();
    // Requests originating from the control panel inside the elevator
    private Set<InternalRequest> internalRequests = new HashSet<>();

    /**
     * Constructs a {@link Elevator} that can travel between the stories specified.
     * @param lowestStorey The level of the lowest storey
     * @param highestStorey The level of the highest storey
     * @throws IllegalArgumentException If lowestStorey >=  highestStorey
     */
    public Elevator(int lowestStorey, int highestStorey) throws IllegalArgumentException {
        if(lowestStorey >= highestStorey)
            throw new IllegalArgumentException("The highest storey must be at least one level above the lowest storey");

        this.lowestStorey = lowestStorey;
        this.highestStorey = highestStorey;
    }

    /**
     * Send a request to this elevator originating from outside of the elevator.
     * Duplicate requests will be ignored.
     * @param sourceStorey The storey where the request originates from
     * @param targetDirection The requested travel direction
     * @throws IllegalArgumentException If source storey is outside of elevator range
     */
    public void createExternalRequest(int sourceStorey, Direction targetDirection) throws IllegalArgumentException {
        if(sourceStorey < lowestStorey || sourceStorey > highestStorey)
            throw new IllegalArgumentException("Source storey outside elevator range");

        externalRequests.add(new ExternalRequest(sourceStorey, targetDirection));
    }

    /**
     * Send a request to this elevator originating from inside of the elevator.
     * Duplicate requests will be ignored.
     * @param targetStorey The requested destination storey
     * @throws IllegalArgumentException If target storey is outside of elevator range
     */
    public void createInternalRequest(int targetStorey) throws IllegalArgumentException {
        if(targetStorey < lowestStorey || targetStorey > highestStorey)
            throw new IllegalArgumentException("Target storey outside elevator range");

        internalRequests.add(new InternalRequest(targetStorey));
    }
}
