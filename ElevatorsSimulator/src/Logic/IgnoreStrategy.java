package Logic;

import Interfaces.ElevatorStrategy;
import Models.BaseElevator;
import Models.BaseFloor;
import Models.ElevatorState;
import Models.Floor;

import java.util.Queue;

public class IgnoreStrategy extends BaseStrategy implements ElevatorStrategy {
    public IgnoreStrategy(BaseElevator elevator, Queue<Floor> floorQueue) {
        super(elevator, floorQueue);
    }

    @Override
    public void Move() {
        //may be changed later
        while (true) {
            if(floorQueue.isEmpty()) {
                while (elevator.getState() != ElevatorState.Called) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            Floor firstCalledFloor = floorQueue.poll();
            double step = 0.01;
            while (elevator.getY() - firstCalledFloor.getHeight() > step){
                if(elevator.getY() < firstCalledFloor.getHeight()){
                    elevator.setY(elevator.getY() + step);
                }
                else {
                    elevator.setY(elevator.getY() - step);
                }
            }

            elevator.Stop();
            elevator.OpenDoors();
            elevator.CloseDoors();
        }
    }
}
