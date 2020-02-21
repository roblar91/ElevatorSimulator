package knc.simulator.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorRequestManagerTest {
    private final int defaultLowestStorey = 1;
    private final int defaultHighestStorey = 4;
    private final int defaultStartingStorey = defaultLowestStorey;
    private Elevator elevator = new Elevator(defaultLowestStorey, defaultHighestStorey, defaultStartingStorey);
    private ElevatorRequestManager elevatorRequestManager = new ElevatorRequestManager(elevator);

    @Test
    void testCreatesElevator() {
        assertNotNull(elevatorRequestManager.getElevator());
    }

    @Test
    void testElevatorStartsAtLowestStorey() {
        assertEquals(defaultLowestStorey, elevatorRequestManager.getElevator().getCurrentStorey());
    }


    @Test
    void testRequestOutOfRangeShouldThrow() {
        assertThrows(Exception.class, () -> elevatorRequestManager.createElevatorRequest(defaultHighestStorey +1));
        assertThrows(Exception.class, () -> elevatorRequestManager.createElevatorRequest(defaultLowestStorey -1));
    }

    @Test
    void testRequestUpdatesElevatorTargetStorey() {
        elevatorRequestManager.createElevatorRequest(defaultHighestStorey);
        assertEquals(defaultHighestStorey, elevatorRequestManager.getElevator().getTargetStorey());
    }

    @Test
    void testElevatorReachesTarget() {
        elevatorRequestManager.createElevatorRequest(defaultHighestStorey);
        elevator.updateUntilNextHold();
        assertEquals(defaultHighestStorey, elevatorRequestManager.getElevator().getCurrentStorey());
    }

    @Test
    void testElevatorReachesMultipleTargets() {
        var firstTarget = defaultLowestStorey + 2;
        var secondTarget = firstTarget - 1;
        elevatorRequestManager.createElevatorRequest(firstTarget);
        elevatorRequestManager.createElevatorRequest(secondTarget);
        elevator.updateUntilNextHold();
        elevator.updateUntilNextHold();
        assertEquals(secondTarget, elevatorRequestManager.getElevator().getCurrentStorey());
    }
}