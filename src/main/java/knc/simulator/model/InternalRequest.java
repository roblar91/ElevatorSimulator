package knc.simulator.model;

public class InternalRequest {
    private final int targetStorey;

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
            return getTargetStorey() == other.getTargetStorey();
        }

        return false;
    }
}
