package Models;

public class WorldInformation {
    private static WorldInformation instance;
    private int floorsNum;
    private int elevatorsNum;
    private int xMargin;
    private int yMargin;
    private double floorHeight;
    private double elevatorWidth;
    private Building building;
    private double passengerWidth;
    private double passengerMargin;

    public double getPassengerMargin() {
        return passengerMargin;
    }

    public void setPassengerMargin(double passengerMargin) {
        this.passengerMargin = passengerMargin;
    }

    public double getPassengerWidth() {
        return passengerWidth;
    }

    public void setPassengerWidth(double passengerWidth) {
        this.passengerWidth = passengerWidth;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public double getWorldWidth(){
        return (xMargin + elevatorWidth) * elevatorsNum + xMargin * 3;
    }

    public double getWorldHeight(){
        return floorsNum * floorHeight + (floorsNum - 1) * yMargin;
    }

    public int getFloorsNum() {
        return floorsNum;
    }

    public void setFloorsNum(int floorsNum) {
        this.floorsNum = floorsNum;
    }

    public int getElevatorsNum() {
        return elevatorsNum;
    }

    public void setElevatorsNum(int elevatorsNum) {
        this.elevatorsNum = elevatorsNum;
    }

    public int get_xMargin() {
        return xMargin;
    }

    public void set_xMargin(int xMargin) {
        this.xMargin = xMargin;
    }

    public int get_yMargin() {
        return yMargin;
    }

    public void set_yMargin(int yMargin) {
        this.yMargin = yMargin;
    }

    public double getFloorHeight() {
        return floorHeight;
    }

    public void setFloorHeight(double floorHeight) {
        this.floorHeight = floorHeight;
    }

    private WorldInformation() { }

    public void Initialize(int floorsNum, int elevatorsNum, int xMargin, int yMargin, double floorHeight, double elevatorWidth){
        WorldInformation thisInstance = getInstance();
        thisInstance.elevatorsNum = elevatorsNum;
        thisInstance.floorHeight = floorHeight;
        thisInstance.floorsNum = floorsNum;
        thisInstance.xMargin = xMargin;
        thisInstance.yMargin = yMargin;
        thisInstance.elevatorWidth = elevatorWidth;
    }

    public static WorldInformation getInstance() {
        if(instance == null)
            instance = new WorldInformation();
        return instance;
    }

    public double getElevatorWidth() {
        return elevatorWidth;
    }

    public void setElevatorWidth(double elevatorWidth) {
        this.elevatorWidth = elevatorWidth;
    }
}
