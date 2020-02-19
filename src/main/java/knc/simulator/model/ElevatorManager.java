package knc.simulator.model;

import com.sun.javafx.collections.ObservableSetWrapper;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

import java.util.*;

/**
 * An {@link ElevatorManager} operates an {@link Elevator}.
 * Requests are sent to the elevator manager from both inside the elevator and from outside.
 */
public class ElevatorManager {
    // Requests originating from outside the elevator;
    private final ObservableSet<ExternalRequest> externalRequests = new ObservableSetWrapper<>(new HashSet<>());
    // Requests originating from the control panel inside the elevator
    private final ObservableSet<InternalRequest> internalRequests = new ObservableSetWrapper<>(new HashSet<>());

    private final Elevator elevator;
    private final int lowestStorey;
    private final int highestStorey;


    /**
     * Constructs a {@link ElevatorManager} with an attached {@link Elevator} that can travel between the stories specified.
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

        SetChangeListener<Object> requestChangeListener = (change) -> calculateNextTarget();
        externalRequests.addListener(requestChangeListener);
        internalRequests.addListener(requestChangeListener);
    }

    /**
     * Send a request to this elevator manager originating from outside of the attached elevator.
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
     * Send a request to this elevator manager originating from inside of the attached elevator.
     * Duplicate requests will be ignored.
     * @param targetStorey The requested destination storey
     * @throws IllegalArgumentException If target storey is outside of elevator range
     */
    public void createInternalRequest(int targetStorey) throws IllegalArgumentException {
        if(targetStorey < lowestStorey || targetStorey > highestStorey)
            throw new IllegalArgumentException("Target storey outside elevator range");

        internalRequests.add(new InternalRequest(targetStorey));
    }

    private void calculateNextTarget() {
        System.out.println("Calculating next target");
    }
}
