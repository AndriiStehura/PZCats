package Models;

import Interfaces.BaseElevatorStrategy;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
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

    public void CloseDoors()
    {

    }

    public void Called(BaseFloor floor)
    {

    }
}
