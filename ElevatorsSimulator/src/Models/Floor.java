package Models;

import java.util.List;

public class Floor {
    public List<Passenger> passengerList;
    public double elevatorPoints;
    //public Building buildingReference;
    public double floorHeight;

    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }

    public double getElevatorPoints() {
        return elevatorPoints;
    }

    public void setElevatorPoints(double elevatorPoints) {
        this.elevatorPoints = elevatorPoints;
    }

    public void setHeight(double height){ floorHeight = height;}
    public double getHeight(){ return floorHeight;}

    public Floor(List<Passenger> passengerList, double elevatorPoints) {
        this.passengerList = passengerList;
        this.elevatorPoints = elevatorPoints;
    }
}
