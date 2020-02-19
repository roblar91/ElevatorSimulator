package knc.simulator.model;

/**
 * An {@link ExternalRequest} represents a call for an elevator origin from outside of the elevator.
 */
class ExternalRequest {
    private final int sourceStorey;
    private final Direction targetDirection;

    /**
     * Constructs an {@link ExternalRequest} with the specified source storey and target direction.
     * @param sourceStorey The storey where the request is originating from
     * @param targetDirection The intended direction of travel
     */
    public ExternalRequest(int sourceStorey, Direction targetDirection) {
        this.sourceStorey = sourceStorey;
        this.targetDirection = targetDirection;
    }

    public int getSourceStorey() {
        return sourceStorey;
    }

    public Direction getTargetDirection() {
        return targetDirection;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() == ExternalRequest.class) {
            var other = (ExternalRequest) obj;
            return (sourceStorey == other.getSourceStorey()) &&
                    (targetDirection == other.getTargetDirection());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return sourceStorey + targetDirection.ordinal();
    }
}
