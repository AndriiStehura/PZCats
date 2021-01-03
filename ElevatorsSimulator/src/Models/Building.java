package Models;

import Interfaces.IBuilding;
import Interfaces.IElevator;

import java.util.List;

public class Building implements IBuilding {
    private List<IElevator> elevators;
    private List<Floor> floors;
    @Override
    public void NotifyElevators(Floor floor) {
        for (IElevator elevator:
             elevators) {
            elevator.Called(floor);
        }
    }
}
