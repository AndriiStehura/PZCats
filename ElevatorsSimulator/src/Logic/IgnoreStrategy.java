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
                    Passenger firstPassanger;
                    if(elevator.getPassengers().isEmpty()) {
                        while (true) {
                            System.out.println("Elevator waiting");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            synchronized (isEmptyLocker) {
                                if (!floorQueue.isEmpty()) {
                                    firstPassanger = floorQueue.poll();
                                    break;
                                }
                            }
                        }
                    }
                    else {
                        firstPassanger = elevator.getPassengers().get(0);
                    }

                    WorldInformation wi = WorldInformation.getInstance();
                    /*if(firstPassanger.getState() != PassengerState.Waiting) //because we can still have this passenger in
                    {                                                       //our general queue due to our strategy
                        continue;
                    }*/

                    Floor firstCalledFloor = wi.getBuilding().getFloors().get(firstPassanger.getSourceFloor());
                    double step = 0.0000005;
                    //double step = 0.01; //for debug only
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
                    //firstCalledFloor.ElevatorSourceFloorArrivedIgnoreStrategy(elevator, firstPassanger);

                    elevator.Stop(firstCalledFloor);

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
                    //destinationFloor.ElevatorDestinationFloorArrivedIgnoreStrategy(elevator);

                    elevator.setCurrentFloor(destinationFloor);
                    elevator.Stop(destinationFloor);
                } catch (NullPointerException e) {
                    continue;
                }
            }
    }
}
