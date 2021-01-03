package Logic;

import Models.*;

import java.util.*;

public abstract class BaseStrategy {
    protected BaseElevator elevator;
    protected Queue<Floor> floorQueue;

    public BaseStrategy(BaseElevator elevator, Queue<Floor> floorQueue){
        this.elevator = elevator;
        this.floorQueue = floorQueue;
    }

    //TODO: deliver queue
}
