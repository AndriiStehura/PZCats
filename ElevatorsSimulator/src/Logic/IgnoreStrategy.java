package Logic;

import Interfaces.ElevatorStrategy;
import Models.*;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class IgnoreStrategy extends BaseStrategy implements ElevatorStrategy {
    private static Object isEmptyLocker = new Object();

    public IgnoreStrategy(Elevator elevator, BlockingQueue<Passenger> floorQueue) {
        super(elevator, floorQueue);
    }

    @Override
    public void Move() {
        //may be changed later
        System.out.println("Elevator started");
            while(true) {
                try {
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
                    double step = 0.0000005;
                    System.out.println("Elevator moving to " + firstPassanger.getSourceFloor() + " floor");
                    while (Math.abs(elevator.getY() - firstCalledFloor.getY()) > step) {
                        if (elevator.getY() < firstCalledFloor.getY()) {
                            elevator.setY(elevator.getY() + step);
                        } else {
                            elevator.setY(elevator.getY() - step);
                        }
                    }

                    System.out.println("Elevator stopped on " + firstPassanger.getSourceFloor() + " floor");
                    elevator.setCurrentFloor(firstCalledFloor);
                    elevator.Stop();
                    elevator.OpenDoors();
                    elevator.CloseDoors();

                    //deliver
                    Floor destinationFloor = wi.getBuilding().getFloors().get(firstPassanger.getDestinationFloor());
                    System.out.println("Elevator moving to " + firstPassanger.getDestinationFloor() + " floor");
                    while (Math.abs(elevator.getY() - destinationFloor.getY()) > step) {
                        if (elevator.getY() < destinationFloor.getY()) {
                            elevator.setY(elevator.getY() + step);
                        } else {
                            elevator.setY(elevator.getY() - step);
                        }
                    }

                    System.out.println("Elevator stopped on " + firstPassanger.getDestinationFloor() + " floor");
                    elevator.setCurrentFloor(destinationFloor);
                    elevator.Stop();
                    elevator.OpenDoors();
                    elevator.CloseDoors();
                } catch (NullPointerException e) {
                    continue;
                }
            }
    }
}
