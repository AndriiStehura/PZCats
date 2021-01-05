package Models;

import java.util.List;

public class Floor {
    private List<Passenger> passengerList;
    private double elevatorPoints;
    //public Building buildingReference;
    private double floorHeight;
    private double yCoordinate;

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

    public double getY() {
        return yCoordinate;
    }

    public void setY(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public double getNextPassengerPosition(){
        double pos;
        WorldInformation wi = WorldInformation.getInstance();
        double leftOffset = wi.get_xMargin() + wi.getElevatorWidth();
        leftOffset *= wi.getElevatorsNum();
        if(passengerList.isEmpty()){
            pos = leftOffset + wi.getPassengerMargin();
        }
        else {
            pos = passengerList.get(passengerList.size() - 1).getX()
                    + wi.getPassengerMargin();
        }

        return pos;
    }
}
