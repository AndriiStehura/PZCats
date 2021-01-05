package Logic;

import Interfaces.ElevatorStrategy;
import Models.BaseElevator;
import Models.ElevatorState;
import Models.Floor;

import java.util.Queue;
import java.util.function.Predicate;

public class PickingStrategy extends BaseStrategy implements ElevatorStrategy {
    public PickingStrategy(BaseElevator elevator, Queue<Floor> floorQueue) {
        super(elevator, floorQueue);
    }


    @Override
    public void Move() {
        //may be changed later

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
            var floorsStream = floorQueue.stream();
            Predicate<Floor> predicate = (Floor x) -> elevator.getY() - x.getHeight() > step;
            while (predicate.test(firstCalledFloor)){
                if(elevator.getY() < firstCalledFloor.getHeight()){
                    elevator.setY(elevator.getY() + step);
                }
                else {
                    elevator.setY(elevator.getY() - step);
                }

                if(floorsStream.anyMatch(x -> !predicate.test(x))){
                    elevator.Stop();
                    elevator.OpenDoors();
                    elevator.CloseDoors();
                }
            }
    }
}
