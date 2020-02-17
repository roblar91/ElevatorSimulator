package knc.simulator.model;

/**
 * An {@link Elevator} contains very limited logic and should be managed through a {@link ElevatorManager}.
 * Each time {@link #update()} is called the elevator will move towards any storey designated through {@link #setTargetStorey(int)}.
 * When the elevator reaches its destination it will enter {@link ElevatorAction#HOLD} status for a certain time.
 * The speed of the elevator and the hold time can be specified through {@link #setSpeed(double)} and {@link #setMaxHoldTime(int)}.
 */
class Elevator {
    private ElevatorAction currentAction = ElevatorAction.IDLE;
    private int maxHoldTime = 100;
    private int currentHoldTime = 0;
    private double speed = 0.01;
    private double currentPosition;
    private int targetStorey;

    /**
     * Constructs an {@link Elevator} with the specified starting position.
     * @param startingPosition The starting position of the elevator
     */
    public Elevator(double startingPosition) {
        this.currentPosition = startingPosition;
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

        if(targetStorey > currentPosition) {
            currentAction = ElevatorAction.GOING_UP;
        } else if(targetStorey < currentPosition) {
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
    public int getMaxHoldTime() {
        return maxHoldTime;
    }

    /**
     * Sets the number of cycles that the {@link Elevator} will be in {@link ElevatorAction#HOLD} status after reaching
     * a target storey.
     * @param maxHoldTime The number of cycles that the elevator will hold
     */
    public void setMaxHoldTime(int maxHoldTime) {
        this.maxHoldTime = maxHoldTime;
    }

    /**
     * Gets the speed that the {@link Elevator} will move towards its target storey.
     * The speed is denoted as ratio of storey traversal / cycle, e.g. a speed of 0.1 indicates that it will take
     * 10 cycles of {@link #update()} to traverse one storey.
     * @return The speed value
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the speed that the {@link Elevator} will move towards its target storey.
     * The speed is denoted as ratio of storey traversal / cycle, e.g. a speed of 0.1 indicates that it will take
     * 10 cycles of {@link #update()} to traverse one storey.
     * @param speed The speed value
     */
    public void setSpeed(double speed) {
        this.speed = speed;
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
    public double getCurrentPosition() {
        return currentPosition;
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
                moveUpwards();
                break;
            case GOING_DOWN:
                moveDownwards();
                break;
        }
    }

    private void hold() {
        if(currentHoldTime++ >= maxHoldTime) {
            exitHold();
        }
    }

    private void moveUpwards() {
        currentPosition += speed;
        if(currentPosition >= targetStorey) {
            destinationReached();
        }
    }

    private void moveDownwards() {
        currentPosition -= speed;
        if(currentPosition <= targetStorey) {
            destinationReached();
        }
    }

    private void destinationReached() {
        currentPosition = targetStorey;
        startHold();
    }

    private void startHold() {
        currentHoldTime = 0;
        currentAction = ElevatorAction.HOLD;
    }

    private void exitHold() {
        currentAction = ElevatorAction.IDLE;
    }
}
