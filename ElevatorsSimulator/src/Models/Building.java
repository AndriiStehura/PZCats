package Models;

import Interfaces.IBuilding;
import Interfaces.IElevator;

import java.util.List;

public class Building implements IBuilding {
    private List<IElevator> elevators;
    private List<Floor> floors;

    public List<IElevator> getElevators() {
        return elevators;
    }

    public void setElevators(List<IElevator> elevators) {
        this.elevators = elevators;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    @Override
    public void NotifyElevators(Floor floor) {
        for (IElevator elevator:
             elevators) {
            elevator.Called(floor);
        }
    }
}
