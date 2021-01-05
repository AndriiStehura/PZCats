package Logic;

import Models.*;

import java.util.*;

public abstract class BaseStrategy {
    protected Elevator elevator;
    protected Queue<Floor> floorQueue;

    public BaseStrategy(Elevator elevator, Queue<Floor> floorQueue){
        this.elevator = elevator;
        this.floorQueue = floorQueue;
    }

    //TODO: deliver queue
}
