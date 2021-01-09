package Models;

import Interfaces.IBuilding;
import Interfaces.IElevator;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Building implements IBuilding {
    private List<Elevator> elevators;
    private List<Floor> floors;
    private BlockingQueue<Passenger> passengersQueue;

    public Building(List<Elevator> elevators, List<Floor> floors, BlockingQueue<Passenger> passengersQueue){
        this.elevators = elevators;
        this.floors = floors;
        this.passengersQueue = passengersQueue;
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public void setElevators(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    @Override
    public void updateQueue(Passenger passenger) {
        passengersQueue.add(passenger);
    }

    public BlockingQueue<Passenger> getPassengersQueue() {
        return passengersQueue;
    }

    public void setPassengersQueue(BlockingQueue<Passenger> passengersQueue) {
        this.passengersQueue = passengersQueue;
    }

    public void runAllThreads(){
        Thread factoryThread = new Thread(new Runnable() {
            @Override
            public void run() {
                PassengerFactory factory = new PassengerFactory(floors.size());
                while (true){
                    Passenger passenger = factory.getPassenger();
                    Floor passangersFloor = floors.get(passenger.getSourceFloor());
                    passenger.setY(passangersFloor.getY());
                    passenger.setX(WorldInformation.getInstance().getWorldWidth());
                    passangersFloor.getPassengerList().add(passenger);
                    Thread passangerThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            passenger.getStrategy().Move(passangersFloor.getNextPassengerPosition());
                        }
                    });
                    passangerThread.start();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        factoryThread.start();
        for (var elevator: elevators) {
            Thread thread = new Thread(elevator);
            thread.start();
        }
    }
}
