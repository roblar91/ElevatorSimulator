package knc.simulator.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorManagerTest {
    private final int defaultLowestStorey = 1;
    private final int defaultHighestStorey = 4;
    private ElevatorManager elevatorManager = new ElevatorManager(defaultLowestStorey, defaultHighestStorey);

    @Test
    void testCreatesElevator() {
        assertNotNull(elevatorManager.getElevator());
    }

    @Test
    void testElevatorStartsAtLowestStorey() {
        assertEquals(defaultLowestStorey, elevatorManager.getElevator().getCurrentStorey());
    }

    @Test
    void testCanCreateNegativeStoreys() {
        assertDoesNotThrow(() -> new ElevatorManager(-44, -21));
    }

    @Test
    void testLowestStoreyEqualToHighestShouldThrow() {
        assertThrows(Exception.class, () -> new ElevatorManager(5, 5));
    }

    @Test
    void testLowestStoreyGreaterThanHighestShouldThrow() {
        assertThrows(Exception.class, () -> new ElevatorManager(3, 2));
    }

    @Test
    void testRequestOutOfRangeShouldThrow() {
        assertThrows(Exception.class, () -> elevatorManager.createElevatorRequest(defaultHighestStorey +1));
        assertThrows(Exception.class, () -> elevatorManager.createElevatorRequest(defaultLowestStorey -1));
    }

    @Test
    void testRequestUpdatesElevatorTargetStorey() {
        elevatorManager.createElevatorRequest(defaultHighestStorey);
        assertEquals(defaultHighestStorey, elevatorManager.getElevator().getTargetStorey());
    }

    @Test
    void testElevatorReachesTarget() {
        elevatorManager.createElevatorRequest(defaultHighestStorey);
        elevatorManager.updateUntilNextHold();
        assertEquals(defaultHighestStorey, elevatorManager.getElevator().getCurrentStorey());
    }

    @Test
    void testElevatorReachesMultipleTargets() {
        var firstTarget = defaultLowestStorey + 2;
        var secondTarget = firstTarget - 1;
        elevatorManager.createElevatorRequest(firstTarget);
        elevatorManager.createElevatorRequest(secondTarget);
        elevatorManager.updateUntilNextHold();
        elevatorManager.updateUntilNextHold();
        assertEquals(secondTarget, elevatorManager.getElevator().getCurrentStorey());
    }
}