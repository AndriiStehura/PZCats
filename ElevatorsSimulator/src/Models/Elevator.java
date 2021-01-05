package Models;

import Interfaces.BaseElevatorStrategy;
import Interfaces.ElevatorStrategy;
import Interfaces.IElevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Elevator extends BaseElevator {
    private double x, y;
    private int maxWeight;
    private int currentFloor;
    private ElevatorStrategy strategy;
    private List<Passenger> passengers;
    private ElevatorState state;
    private Queue<Floor> floorQueue;

    public List<Passenger> getPassengers() {
        return passengers;
    }
    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Elevator(int maxWeight)
    {
        this.maxWeight = maxWeight;
        currentFloor = 1;
        passengers = new ArrayList<>();
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public ElevatorState getState() {
        return state;
    }

    @Override
    public void setState(ElevatorState state) {
        state = state;
    }

    public void Stop()
    {

    }

    public void OpenDoors()
    {

    }

    public void Called(Floor floor) {
        floorQueue.add(floor);
    }

    public void CloseDoors()
    {

    }

    @Override
    public void setStrategy(ElevatorStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public ElevatorStrategy getStrategy() {
        return strategy;
    }
}
