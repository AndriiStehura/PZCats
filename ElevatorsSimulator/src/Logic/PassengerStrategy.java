package Logic;

import Models.Building;
import Models.Passenger;
import Models.WorldInformation;

public class PassengerStrategy {
    private Passenger passenger;

    public PassengerStrategy(Passenger passenger) {
        this.passenger = passenger;
    }

    public void Move(double dist){
        Building building = WorldInformation.getInstance().getBuilding();
        /*double distX = building.getFloors().get(passenger.getSourceFloor())
                .getNextPassengerPosition();*/

        System.out.println("Passenger started going");
        while (Math.abs(passenger.getX() - dist) > 0.001){
            passenger.setX(passenger.getX() - 0.01);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Passenger stopped");
        building.updateQueue(passenger);
    }
}
