package knc.simulator.model;

/**
 * An {@link InternalRequest} represents a request to travel to a specific storey.
 */
class InternalRequest {
    private final int targetStorey;

    /**
     * Constructs an {@link InternalRequest} requesting transportation to the specified storey.
     * @param targetStorey The requested storey
     */
    public InternalRequest(int targetStorey) {
        this.targetStorey = targetStorey;
    }

    public int getTargetStorey() {
        return targetStorey;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() == InternalRequest.class) {
            var other = (InternalRequest) obj;
            return targetStorey == other.getTargetStorey();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return targetStorey;
    }
}
