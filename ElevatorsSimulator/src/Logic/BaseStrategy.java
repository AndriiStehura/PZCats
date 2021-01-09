package Logic;

import Models.*;

import java.util.*;

public abstract class BaseStrategy {
    protected Elevator elevator;
    protected Queue<Passenger> floorQueue;

    public BaseStrategy(Elevator elevator, Queue<Passenger> floorQueue){
        this.elevator = elevator;
        this.floorQueue = floorQueue;
    }
}
