package knc.simulator.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {
    private final int startingStorey = 1;
    private Elevator elevator = new Elevator(startingStorey);

    @Test
    void testDirectionIsUpdated() {
        assertEquals(ElevatorAction.IDLE, elevator.getCurrentAction());

        elevator.setTargetStorey(startingStorey + 1);
        assertEquals(ElevatorAction.GOING_UP, elevator.getCurrentAction());

        elevator.setTargetStorey(startingStorey - 1);
        assertEquals(ElevatorAction.GOING_DOWN, elevator.getCurrentAction());

        elevator.setTargetStorey(startingStorey);
        assertEquals(ElevatorAction.IDLE, elevator.getCurrentAction());
    }

    @Test
    void testTraversalSpeed() {
        int cyclesToTraverseOneLevel = 5;
        int targetStorey = startingStorey + 1;
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
        int targetStorey = startingStorey + 1;
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
}