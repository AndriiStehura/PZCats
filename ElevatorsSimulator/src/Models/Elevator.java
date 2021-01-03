package Models;

import Interfaces.BaseElevatorStrategy;
import Interfaces.IElevator;

import java.util.ArrayList;
import java.util.List;

public class Elevator implements IElevator {
    private double x, y;
    private int maxWeight;
    private int currentFloor;
    private BaseElevatorStrategy strategy;
    public List<Passenger> passengers;

    public List<Passenger> getPassengers() {
        return passengers;
    }
    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Elevator(int maxWeight, BaseElevatorStrategy strategy)
    {
        this.maxWeight = maxWeight;
        this.strategy = strategy;
        currentFloor = 1;
        passengers = new ArrayList<>();
    }
    public void Stop()
    {

    }

    public void OpenDoors()
    {

    }

    public void Called(Floor floor) {

    }

    public void CloseDoors()
    {

    }
}
