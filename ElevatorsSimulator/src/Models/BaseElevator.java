package Models;

public abstract class BaseElevator {
    public abstract double getY();
    public abstract void setY(double y);
    public abstract ElevatorState getState();

    public abstract void setState(ElevatorState state);

}
