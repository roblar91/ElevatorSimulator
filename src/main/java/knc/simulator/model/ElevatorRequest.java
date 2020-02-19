package knc.simulator.model;

/**
 * An {@link ElevatorRequest} represents a request to travel to a specific storey.
 */
class ElevatorRequest {
    private final int targetStorey;

    /**
     * Constructs an {@link ElevatorRequest} requesting transportation to the specified storey.
     * @param targetStorey The requested storey
     */
    public ElevatorRequest(int targetStorey) {
        this.targetStorey = targetStorey;
    }

    public int getTargetStorey() {
        return targetStorey;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() == ElevatorRequest.class) {
            var other = (ElevatorRequest) obj;
            return targetStorey == other.getTargetStorey();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return targetStorey;
    }
}
