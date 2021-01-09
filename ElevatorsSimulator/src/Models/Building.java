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
        for (var elevator:
             elevators) {
            Thread thread = new Thread(elevator);
            thread.start();
        }
    }
}
