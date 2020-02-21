package knc.simulator.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {
    private final int defaultLowestStorey = 1;
    private final int defaultHighestStorey = 4;
    private final int defaultStartingStorey = defaultLowestStorey;
    private Elevator elevator = new Elevator(defaultLowestStorey, defaultHighestStorey, defaultStartingStorey);

    @Test
    void testCanCreateNegativeStoreys() {
        assertDoesNotThrow(() -> new Elevator(-44, -21));
    }

    @Test
    void testLowestStoreyEqualToHighestShouldThrow() {
        assertThrows(Exception.class, () -> new Elevator(5, 5));
    }

    @Test
    void testLowestStoreyGreaterThanHighestShouldThrow() {
        assertThrows(Exception.class, () -> new Elevator(3, 2));
    }

    @Test
    void testDirectionIsUpdated() {
        assertEquals(ElevatorAction.IDLE, elevator.getCurrentAction());

        elevator.setTargetStorey(defaultStartingStorey + 1);
        assertEquals(ElevatorAction.GOING_UP, elevator.getCurrentAction());

        elevator.setTargetStorey(defaultStartingStorey - 1);
        assertEquals(ElevatorAction.GOING_DOWN, elevator.getCurrentAction());
    }

    @Test
    void testTraversalSpeed() {
        int cyclesToTraverseOneLevel = 5;
        int targetStorey = defaultStartingStorey + 1;
        elevator.setCyclesToTraverseStorey(cyclesToTraverseOneLevel);
        elevator.setTargetStorey(targetStorey);

        for(int i=0; i<cyclesToTraverseOneLevel; i++) {
            assertNotEquals(targetStorey, elevator.getCurrentStorey());
            elevator.update();
        }

        assertEquals(targetStorey, elevator.getCurrentStorey());
    }

    @Test
    void testInstantTraversalSpeed() {
        int cyclesToTraverseOneLevel = 1;
        int targetStorey = defaultStartingStorey + 1;
        elevator.setCyclesToTraverseStorey(cyclesToTraverseOneLevel);
        elevator.setTargetStorey(targetStorey);

        assertNotEquals(targetStorey, elevator.getCurrentStorey());

        elevator.update();

        assertEquals(targetStorey, elevator.getCurrentStorey());
    }

    @Test
    void testTraversalCyclesBelow1ShouldThrowException() {
        assertThrows(Exception.class, () -> elevator.setCyclesToTraverseStorey(0));
        assertThrows(Exception.class, () -> elevator.setCyclesToTraverseStorey(-5));
    }

    @Test
    void testHoldCyclesBelow1ShouldThrowException() {
        assertThrows(Exception.class, () -> elevator.setCyclesToHold(0));
        assertThrows(Exception.class, () -> elevator.setCyclesToHold(-8));
    }

    @Test
    void testHoldWhenTargetStoreyEqualsCurrent() {
        elevator.setTargetStorey(defaultStartingStorey);
        assertEquals(ElevatorAction.HOLD, elevator.getCurrentAction());
    }

    @Test
    void testHoldAfterTargetStoreyReached() {
        int cyclesToTraverseOneLevel = 1;
        int targetStorey = defaultStartingStorey + 1;
        elevator.setCyclesToTraverseStorey(cyclesToTraverseOneLevel);
        elevator.setTargetStorey(targetStorey);
        elevator.update();

        assertEquals(ElevatorAction.HOLD, elevator.getCurrentAction());
    }

    @Test
    void testIdleAfterHold() {
        int cyclesToHold = 1;
        elevator.setCyclesToHold(cyclesToHold);
        elevator.setTargetStorey(defaultStartingStorey);
        elevator.update();

        assertEquals(ElevatorAction.IDLE, elevator.getCurrentAction());
    }
}