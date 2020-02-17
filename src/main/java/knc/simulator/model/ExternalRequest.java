package knc.simulator.model;

public class ExternalRequest {
    private final int sourceStorey;
    private final Direction targetDirection;

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
