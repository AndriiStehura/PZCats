package Logic;

import Interfaces.ElevatorStrategy;
import Models.*;

import java.util.ArrayList;
import java.util.Comparator;
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
        var floors = new ArrayList<>(wi.getBuilding().getFloors());
        Predicate<Floor> predicate = (Floor x) -> Math.abs(elevator.getY() - x.getY()) > step;
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

            if(isCalled) {
                Floor firstCalledFloor = floors.get(firstPassanger.getSourceFloor());
                while (predicate.test(firstCalledFloor)) {
                    if (elevator.getY() < firstCalledFloor.getHeight()) {
                        elevator.setY(elevator.getY() + step);
                    } else {
                        elevator.setY(elevator.getY() - step);
                    }
                }

                elevator.Stop(firstCalledFloor);
                if(!elevator.getPassengers().contains(firstPassanger))
                    continue;
            }

            Floor destinationFloor = floors.get(firstPassanger.getDestinationFloor());
            while (predicate.test(destinationFloor)) {
                    if (elevator.getY() < destinationFloor.getHeight()) {
                        elevator.setY(elevator.getY() + step);
                    } else {
                        elevator.setY(elevator.getY() - step);
                    }
            }
            elevator.Stop(destinationFloor);
        }
    }
}
