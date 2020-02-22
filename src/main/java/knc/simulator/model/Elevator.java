package knc.simulator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link Elevator} contains very limited logic and should be managed through a {@link ElevatorRequestManager}.
 * Each time {@link #update()} is called the elevator will move towards any storey designated through {@link #setTargetStorey(int)}.
 * When the elevator reaches its destination it will enter {@link ElevatorAction#HOLD} status for a certain time.
 * The speed of the elevator and the hold time can be specified through {@link #setCyclesToTraverseStorey(int)} and {@link #setCyclesToHold(int)}.
 */
public class Elevator {
    private final int lowestStorey;
    private final int highestStorey;
    private List<ElevatorActionListener> listeners = new ArrayList<>();
    private ElevatorAction currentAction = ElevatorAction.IDLE;
    private int cyclesToHold = 60;
    private int currentHoldCycles = 0;
    private int cyclesToTraverseStorey = 60;
    private int currentTraversalCycles = 0;
    private int currentStorey;
    private int targetStorey;

    /**
     * Constructs an {@link Elevator} with the specified lowest, highest, and starting storey.
     * @param lowestStorey The level of the lowest storey
     * @param highestStorey The level of the highest storey
     * @param startingStorey The starting storey of the elevator
     * @throws IllegalArgumentException If lowestStorey >=  highestStorey
     */
    public Elevator(int lowestStorey, int highestStorey, int startingStorey) {
        if(lowestStorey >= highestStorey)
            throw new IllegalArgumentException("The highest storey must be at least one level above the lowest storey");

        this.lowestStorey = lowestStorey;
        this.highestStorey = highestStorey;
        this.currentStorey = startingStorey;
    }

    /**
     * Constructs an {@link Elevator} with the specified lowest and highest storey.
     * The starting storey is set to the lowest storey.
     * @param lowestStorey The level of the lowest storey
     * @param highestStorey The level of the highest storey
     * @throws IllegalArgumentException If lowestStorey >=  highestStorey
     */
    public Elevator(int lowestStorey, int highestStorey) {
        this(lowestStorey, highestStorey, lowestStorey);
    }

    /**
     * Gets the lowest storey this {@link Elevator} can travel to.
     * @return The lowest storey
     */
    public int getLowestStorey() {
        return lowestStorey;
    }

    /**
     * Gets the highest storey this {@link Elevator} can travel to.
     * @return The highest storey
     */
    public int getHighestStorey() {
        return highestStorey;
    }

    /**
     * Gets the storey that the elevator is currently moving towards.
     * @return The target storey
     */
    public int getTargetStorey() {
        return targetStorey;
    }

    /**
     * Sets the storey that this {@link Elevator} should head towards.
     * Setting a target storey will immediately change the elevators current {@link ElevatorAction}.
     * @param targetStorey The target storey
     */
    public void setTargetStorey(int targetStorey) {
        this.targetStorey = targetStorey;

        if(targetStorey > currentStorey) {
            currentAction = ElevatorAction.ASCENDING;
            notifyListeners();
        } else if(targetStorey < currentStorey) {
            currentAction = ElevatorAction.DESCENDING;
            notifyListeners();
        } else {
            startHold();
        }
    }

    /**
     * Gets the number of cycles that the {@link Elevator} will be in {@link ElevatorAction#HOLD} status after reaching
     * a target storey.
     * @return The number of cycles that the elevator will hold
     */
    public int getCyclesToHold() {
        return cyclesToHold;
    }

    /**
     * Sets the number of cycles that the {@link Elevator} will be in {@link ElevatorAction#HOLD} status after reaching
     * a target storey.
     * @param cyclesToHold The number of cycles that the elevator will hold
     * @throws IllegalArgumentException If cyclesToHold < 1
     */
    public void setCyclesToHold(int cyclesToHold) throws IllegalArgumentException {
        if(cyclesToHold < 1)
            throw new IllegalArgumentException("Cycles to hold must be >= 1");

        this.cyclesToHold = cyclesToHold;
    }

    /**
     * Gets the cycles needed for the {@link Elevator} to traverse one storey.
     * @return The cycles required
     */
    public double getCyclesToTraverseStorey() {
        return cyclesToTraverseStorey;
    }

    /**
     * Sets the cycles needed for the {@link Elevator} to traverse one storey.
     * @param cyclesToTraverseStorey The cycles required
     * @throws IllegalArgumentException If cyclesToTraverseStorey < 1
     */
    public void setCyclesToTraverseStorey(int cyclesToTraverseStorey) throws IllegalArgumentException {
        if(cyclesToTraverseStorey < 1)
            throw new IllegalArgumentException("Cycles to traverse a storey must be >= 1");

        this.cyclesToTraverseStorey = cyclesToTraverseStorey;
    }

    /**
     * Gets the cycles spent in {@link ElevatorAction#HOLD} state.
     * @return The current cycle
     */
    public int getCurrentHoldCycles() {
        return currentHoldCycles;
    }

    /**
     * Gets the cycles spent in {@link ElevatorAction#ASCENDING} or {@link ElevatorAction#DESCENDING} state.
     * @return The current cycle
     */
    public int getCurrentTraversalCycles() {
        return currentTraversalCycles;
    }

    /**
     * Gets the {@link ElevatorAction} currently being performed by this {@link Elevator}.
     * @return The current action
     */
    public ElevatorAction getCurrentAction() {
        return currentAction;
    }

    /**
     * Gets the current position of this {@link Elevator}.
     * @return The current position
     */
    public int getCurrentStorey() {
        return currentStorey;
    }

    /**
     * Progresses the movement or hold timer of this {@link Elevator} by one cycle.
     */
    public void update() {
        switch(currentAction) {
            case IDLE:
                break;
            case HOLD:
                hold();
                break;
            case ASCENDING:
            case DESCENDING:
                traverse();
                break;
        }
    }

    /**
     * The status of this {@link Elevator} is progressed until the next
     * {@link ElevatorAction#HOLD} or {@link ElevatorAction#IDLE} state is reached.
     */
    public void updateUntilNextHold() {
        // Update until elevator leaves hold
        while(currentAction == ElevatorAction.HOLD)
            update();

        // Update until next hold
        while(currentAction != ElevatorAction.HOLD && currentAction != ElevatorAction.IDLE)
            update();
    }

    /**
     * Gets the position of the this {@link Elevator} represented as number of storeys from bottom.
     * Useful when making a graphical representation of the elevator.
     * @return The number of stories from bottom
     */
    public double getElevatorPositionAsStoriesFromBottom() {
        var storiesFromBottom = currentStorey - lowestStorey;
        var progressToNextStorey = (double) currentTraversalCycles / cyclesToTraverseStorey;

        if(currentAction == ElevatorAction.DESCENDING)
            progressToNextStorey = -progressToNextStorey;

        return storiesFromBottom + progressToNextStorey;
    }

    /**
     * Registers a listener to be notified each time this {@link Elevator} changes {@link ElevatorAction}.
     * @param listener The listener to register
     */
    public void registerListener(ElevatorActionListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        listeners.forEach(listener -> listener.onChange(currentAction));
    }

    private void hold() {
        if(++currentHoldCycles >= cyclesToHold) {
            exitHold();
        }
    }

    private void traverse() {
        if(++currentTraversalCycles >= cyclesToTraverseStorey) {
            storeyChanged();
        }
    }

    private void storeyChanged() {
        currentTraversalCycles = 0;

        if(currentAction == ElevatorAction.ASCENDING)
            currentStorey++;
        else if(currentAction == ElevatorAction.DESCENDING)
            currentStorey--;

        if(currentStorey == targetStorey)
            startHold();
    }

    private void startHold() {
        currentHoldCycles = 0;
        currentAction = ElevatorAction.HOLD;
        notifyListeners();
    }

    private void exitHold() {
        currentAction = ElevatorAction.IDLE;
        notifyListeners();
    }
}
