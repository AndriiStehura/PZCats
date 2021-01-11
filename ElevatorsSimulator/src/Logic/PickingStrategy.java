package Logic;

import Interfaces.ElevatorStrategy;
import Models.*;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Predicate;

public class PickingStrategy extends BaseStrategy implements ElevatorStrategy {
    private static Object isEmptyLocker = new Object();

    public PickingStrategy(Elevator elevator, BlockingQueue<Passenger> floorQueue) {
        super(elevator, floorQueue);
    }

    @Override
    public void Move() {
        //may be changed later
        WorldInformation wi = WorldInformation.getInstance();
        boolean isCalled = true;
        double step = 0.0000005;
        Predicate<Floor> predicate = (Floor x) -> elevator.getY() - x.getHeight() > step;
        while (true) {
            Passenger firstPassanger = null;
            if (floorQueue.isEmpty()) {
                while (elevator.getState() != ElevatorState.Called) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (isEmptyLocker) {
                        if (!floorQueue.isEmpty()) {
                            firstPassanger = floorQueue.poll();
                            isCalled = true;
                            break;
                        }
                    }
                }
            }
            else {
                firstPassanger = elevator.getPassengers().get(0);
                isCalled = false;
            }

            var floorsStream = wi.getBuilding().getFloors().parallelStream();
            if(isCalled) {
                Floor firstCalledFloor = wi.getBuilding().getFloors().get(firstPassanger.getSourceFloor());
                while (predicate.test(firstCalledFloor)) {
                    if (elevator.getY() < firstCalledFloor.getHeight()) {
                        elevator.setY(elevator.getY() + step);
                    } else {
                        elevator.setY(elevator.getY() - step);
                    }

                    if (floorsStream.anyMatch(x -> !predicate.test(x))) {
                        elevator.Stop(firstCalledFloor);
                    }
                }
            }

            Floor destinationFloor = wi.getBuilding().getFloors().get(firstPassanger.getDestinationFloor());
            while (predicate.test(destinationFloor)) {
                if (elevator.getY() < destinationFloor.getHeight()) {
                    elevator.setY(elevator.getY() + step);
                } else {
                    elevator.setY(elevator.getY() - step);
                }

                if (floorsStream.anyMatch(x -> !predicate.test(x))) {
                    var currentFloor = floorsStream
                            .filter(x -> !predicate.test(x))
                            .findFirst()
                            .get();
                    elevator.Stop(currentFloor);
                }
            }
            floorsStream.close();
        }
    }
}
