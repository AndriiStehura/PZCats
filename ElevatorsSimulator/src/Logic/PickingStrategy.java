package Logic;

import Interfaces.ElevatorStrategy;
import Models.*;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Predicate;

public class PickingStrategy extends BaseStrategy implements ElevatorStrategy {
    public PickingStrategy(Elevator elevator, BlockingQueue<Passenger> floorQueue) {
        super(elevator, floorQueue);
    }
    private static Object isEmptyLocker = new Object();

    @Override
    public void Move() {
        //may be changed later

        while (true) {
            while (true) {
                System.out.println("Elevator waiting");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (isEmptyLocker) {
                    if (!floorQueue.isEmpty())
                        break;
                }
            }

            WorldInformation wi = WorldInformation.getInstance();
            Passenger firstPassanger = floorQueue.poll();
            Floor firstCalledFloor = wi.getBuilding().getFloors().get(firstPassanger.getSourceFloor());
            double step = 0.01;
            var floorsStream = wi.getBuilding().getFloors().stream();
            Predicate<Floor> predicate = (Floor x) -> elevator.getY() - x.getHeight() > step;
            while (predicate.test(firstCalledFloor)) {
                if (elevator.getY() < firstCalledFloor.getHeight()) {
                    elevator.setY(elevator.getY() + step);
                } else {
                    elevator.setY(elevator.getY() - step);
                }

                if (floorsStream.anyMatch(x -> !predicate.test(x))) {
                    elevator.Stop(null);
                    elevator.OpenDoors();
                    elevator.CloseDoors();
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
                    elevator.Stop(null);
                    elevator.OpenDoors();
                    elevator.CloseDoors();
                }
            }
        }
    }
}
