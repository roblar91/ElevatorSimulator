package knc.simulator.model;

/**
 * An {@link Elevator} contains very limited logic and should be managed through a {@link ElevatorManager}.
 * Each time {@link #update()} is called the elevator will move towards any storey designated through {@link #setTargetStorey(int)}.
 * When the elevator reaches its destination it will enter {@link ElevatorAction#HOLD} status for a certain time.
 * The speed of the elevator and the hold time can be specified through {@link #setCyclesToTraverseStorey(int)} and {@link #setCyclesToHold(int)}.
 */
class Elevator {
    private ElevatorAction currentAction = ElevatorAction.IDLE;
    private int cyclesToHold = 100;
    private int currentHoldCycles = 0;
    private int cyclesToTraverseStorey = 100;
    private int currentTraversalCycles = 0;
    private int currentStorey;
    private int targetStorey;

    /**
     * Constructs an {@link Elevator} with the specified starting storey.
     * @param startingStorey The starting storey of the elevator
     */
    public Elevator(int startingStorey) {
        this.currentStorey = startingStorey;
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
            currentAction = ElevatorAction.GOING_UP;
        } else if(targetStorey < currentStorey) {
            currentAction = ElevatorAction.GOING_DOWN;
        } else {
            currentAction = ElevatorAction.IDLE;
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
     */
    public void setCyclesToHold(int cyclesToHold) {
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
            throw new IllegalArgumentException("Cycles to traverse a storey has to be >= 1");
        this.cyclesToTraverseStorey = cyclesToTraverseStorey;
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
            case GOING_UP:
            case GOING_DOWN:
                traverse();
                break;
        }
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

        if(currentAction == ElevatorAction.GOING_UP)
            currentStorey++;
        else if(currentAction == ElevatorAction.GOING_DOWN)
            currentStorey--;

        if(currentStorey == targetStorey)
            startHold();
    }

    private void startHold() {
        currentHoldCycles = 0;
        currentAction = ElevatorAction.HOLD;
    }

    private void exitHold() {
        currentAction = ElevatorAction.IDLE;
    }
}
