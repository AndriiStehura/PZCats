package Models;

public class Passenger {
    private int weight;
    private int sourceFloor;
    private int destinationFloor;
    private PassengerState state;
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setSourceFloor(int sourceFloor) {
        this.sourceFloor = sourceFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public void setState(PassengerState state) {
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

    public PassengerState getState() {
        return state;
    }

    public Passenger(int weight, int sourceFloor, int destinationFloor, PassengerState state) {
        this.weight = weight;
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        this.state = state;
    }
}
