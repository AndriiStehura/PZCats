package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Floor {
    private List<Passenger> passengerList;
    private double elevatorPoints;
    //public Building buildingReference;
    private double floorHeight;
    private double yCoordinate;

    public Floor(){
        passengerList = new ArrayList<>();
    }

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
        var waitingPassengers = passengerList.stream()
                .filter(x -> x.getState() == PassengerState.Waiting)
                .collect(Collectors.toList());
        if(waitingPassengers.isEmpty()){
            pos = leftOffset + wi.getPassengerMargin();
        }
        else {
            pos = waitingPassengers.get(waitingPassengers.size() - 1).getX()
                    + wi.getPassengerMargin();
        }

        return pos;
    }

    public void ElevatorArrived(Elevator elevator){
        int floorIndex = WorldInformation.getInstance().getBuilding().getFloors().indexOf(this);
        for (int i = 0; i < elevator.getPassengers().size(); ++i){
            Passenger p = elevator.getPassengers().get(i);
            if(p.getDestinationFloor() == floorIndex){
                p.setState(PassengerState.Leaving);
                elevator.getPassengers().remove(p);
                --i;
            }
        }

        for (int i = 0; i < passengerList.size(); ++i) {
            Passenger p = passengerList.get(i);
            if(elevator.canEnter(p.getWeight())){
                p.setState(PassengerState.Moving);
                elevator.getPassengers().add(p);
                passengerList.remove(p);
                --i;
            }
        }
        RearrangePassengers();
    }

    public void RearrangePassengers(){
        List<Passenger> backUp = new ArrayList<>(passengerList);
        passengerList.clear();
        for (var p: backUp) {
            p.getStrategy().Move(getNextPassengerPosition());
            passengerList.add(p);
        }
    }
}
