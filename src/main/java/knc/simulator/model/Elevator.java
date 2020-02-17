package knc.simulator.model;

public class Elevator {
    private ElevatorAction currentAction = ElevatorAction.IDLE;
    private int maxHoldTime = 100;
    private int currentHoldTime = 0;
    private double speed = 0.01;
    private double currentPosition;
    private int targetStorey;

    public int getTargetStorey() {
        return targetStorey;
    }

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

    public ElevatorAction getCurrentAction() {
        return currentAction;
    }

    public double getCurrentPosition() {
        return currentPosition;
    }

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
