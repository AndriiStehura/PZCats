package Models;

import Interfaces.IBuilding;
import Interfaces.IElevator;

import java.util.List;

public class Building implements IBuilding {
    private List<Elevator> elevators;
    private List<Floor> floors;

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
    public void NotifyElevators(Passenger passenger) {
        for (Elevator elevator: elevators) {
            elevator.Called(passenger);
        }
    }

    public Building(List<Elevator> elevators, List<Floor> floors){
        this.elevators = elevators;
        this.floors = floors;
    }
}
