package Logic;

import Models.Building;
import Models.Passenger;
import Models.WorldInformation;

public class PassengerStrategy {
    private Passenger passenger;

    public PassengerStrategy(Passenger passenger) {
        this.passenger = passenger;
    }

    public void Move(){
        Building building = WorldInformation.getInstance().getBuilding();
        double destX = building.getFloors().get(passenger.getSourceFloor())
                .getNextPassengerPosition();

        while (Math.abs(passenger.getX() - destX) > 0.001){
            passenger.setX(passenger.getX() - 0.01);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        building.NotifyElevators(building.getFloors().get(passenger.getSourceFloor()));
    }
}
