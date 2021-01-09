package Logic;

import Interfaces.ElevatorStrategy;
import Models.*;

import java.util.Queue;

public class IgnoreStrategy extends BaseStrategy implements ElevatorStrategy {
    public IgnoreStrategy(Elevator elevator, Queue<Passenger> floorQueue) {
        super(elevator, floorQueue);
    }

    @Override
    public void Move() {
        //may be changed later
            while(true) {
                if (floorQueue.isEmpty()) {
                    while (elevator.getState() != ElevatorState.Called) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                WorldInformation wi = WorldInformation.getInstance();
                Passenger firstPassanger = floorQueue.poll();
                Floor firstCalledFloor = wi.getBuilding().getFloors().get(firstPassanger.getSourceFloor());
                double step = 0.01;
                while (elevator.getY() - firstCalledFloor.getHeight() > step) {
                    if (elevator.getY() < firstCalledFloor.getHeight()) {
                        elevator.setY(elevator.getY() + step);
                    } else {
                        elevator.setY(elevator.getY() - step);
                    }
                }

                elevator.setCurrentFloor(firstCalledFloor);
                elevator.Stop();
                elevator.OpenDoors();
                elevator.CloseDoors();

                //deliver
                Floor destinationFloor = wi.getBuilding().getFloors().get(firstPassanger.getDestinationFloor());
                while (elevator.getY() - destinationFloor.getHeight() > step) {
                    if (elevator.getY() < destinationFloor.getHeight()) {
                        elevator.setY(elevator.getY() + step);
                    } else {
                        elevator.setY(elevator.getY() - step);
                    }

                    elevator.setCurrentFloor(destinationFloor);
                    elevator.Stop();
                    elevator.OpenDoors();
                    elevator.CloseDoors();
                }
            }
    }
}
