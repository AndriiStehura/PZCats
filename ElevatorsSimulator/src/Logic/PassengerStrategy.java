package Logic;

import Models.Building;
import Models.Passenger;
import Models.WorldInformation;

public class PassengerStrategy {
    private Passenger passenger;

    public PassengerStrategy(Passenger passenger) {
        this.passenger = passenger;
    }

    public void Move(double dest){
        Building building = WorldInformation.getInstance().getBuilding();

        System.out.println("Passenger started going");
        while (Math.abs(passenger.getX() - dest) > 0.001){
                if(passenger.getX() > dest)
                    passenger.setX(passenger.getX() - 0.01);
                else
                    passenger.setX(passenger.getX() + 0.01);
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
