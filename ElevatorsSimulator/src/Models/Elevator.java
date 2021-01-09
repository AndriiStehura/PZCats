package Models;

import Interfaces.BaseElevatorStrategy;
import Interfaces.ElevatorStrategy;
import Interfaces.IElevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Elevator extends BaseElevator implements Runnable {
    private double x, y;
    private int maxWeight;
    private Floor currentFloor;
    private ElevatorStrategy strategy;
    private List<Passenger> passengers;
    private ElevatorState state;

    public Floor getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(Floor currentFloor) {
        this.currentFloor = currentFloor;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }
    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Elevator(int maxWeight)
    {
        this.maxWeight = maxWeight;
        currentFloor = WorldInformation.getInstance().getBuilding().getFloors().get(0);
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
        OpenDoors();

    }

    public void OpenDoors()
    {
        state = ElevatorState.Stopped;
    }

    public void Called(Passenger passenger) {
        getStrategy().getFloorQueue().add(passenger);
    }

    public void CloseDoors()
    {
        state = ElevatorState.Moving;
    }

    @Override
    public void setStrategy(ElevatorStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public ElevatorStrategy getStrategy() {
        return strategy;
    }

    public boolean canEnter(int weight){
        int passengersWeight = passengers
                .stream()
                .mapToInt(x -> x.getWeight())
                .sum();

        return  passengersWeight + weight <= maxWeight;
    }

    @Override
    public void run() {
        strategy.Move();
    }
}
