package Models;

public class Passenger {
    public int weight;
    public int sourceFloor;
    public int destinationFloor;
    public PassangerState state;
    //public Point point;

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setSourceFloor(int sourceFloor) {
        this.sourceFloor = sourceFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public void setState(PassangerState state) {
        this.state = state;
    }

    public int getWeight() {
        return weight;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public PassangerState getState() {
        return state;
    }

    public Passenger(int weight, int sourceFloor, int destinationFloor, PassangerState state) {
        this.weight = weight;
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        this.state = state;
    }
}
