package Models;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WorldInformation extends JPanel {
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
    private ArrayList<Image> passengerImages;

    public ArrayList<Image> getPassengerImages() {
        return passengerImages;
    }

    public void setPassengerImages(ArrayList<Image> passengerImages) {
        this.passengerImages = passengerImages;
    }

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

    public void Initialize(int floorsNum, int elevatorsNum, int xMargin,
                           int yMargin, double floorHeight, double elevatorWidth,
                           int passengerWidth, int passengerMargin){
        WorldInformation thisInstance = getInstance();
        thisInstance.elevatorsNum = elevatorsNum;
        thisInstance.floorHeight = floorHeight;
        thisInstance.floorsNum = floorsNum;
        thisInstance.xMargin = xMargin;
        thisInstance.yMargin = yMargin;
        thisInstance.elevatorWidth = elevatorWidth;
        thisInstance.passengerWidth = passengerWidth;

        passengerImages = new ArrayList<Image>();
        ArrayList<Image> images = new ArrayList<Image>();

        try {
            images.add(ImageIO.read(new File("Images/passenger1.png")));
            images.add(ImageIO.read(new File("Images/passenger2.png")));
            images.add(ImageIO.read(new File("Images/passenger3.png")));
            images.add(ImageIO.read(new File("Images/passenger4.png")));
            images.add(ImageIO.read(new File("Images/passenger5.png")));
            images.add(ImageIO.read(new File("Images/passenger6.png")));
            images.add(ImageIO.read(new File("Images/passenger7.png")));
            images.add(ImageIO.read(new File("Images/passenger8.png")));
            images.add(ImageIO.read(new File("Images/passenger9.png")));
            images.add(ImageIO.read(new File("Images/passenger10.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
        for(Image image: images) {
            passengerImages.add(image.getScaledInstance(90, 90, Image.SCALE_DEFAULT));
        }

        setDoubleBuffered(true);
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

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);

        for (Floor floor : building.getFloors()) {
            drawFloors(floor, g);
            for (Passenger passenger: floor.getPassengerList()) {
                drawPassengers(passenger, g);
            }
        }

        for (Elevator elevator: building.getElevators()) {
            //elevator.setDoorWidth(elevatorWidth/2);
            drawElevators(elevator, g);
        }

        for (Passenger passenger: building.getLeavingList()){
            drawPassengers(passenger, g);
        }
    }

    public void drawPassengers(Passenger passenger, Graphics g)
    {
        PassengerState currentState = passenger.getState();
        if(currentState != PassengerState.Moving &&
                currentState !=  PassengerState.Left)
        g.drawImage(passenger.getImage(), (int)passenger.getX() ,
                (int)passenger.getY() + 10, null);
    }


    public void drawFloors(Floor floor, Graphics g)
    {
        g.setColor(new Color(238,238,238));
        g.fillRect(0, (int)floor.getY(), (int)this.getWorldWidth(), (int)floorHeight);

        g.setColor(Color.BLACK);
        g.drawRect(0, (int)floor.getY() , (int)this.getWorldWidth(), (int)floorHeight);

        g.setColor(new Color(128,128,93));
        g.fillRect(0, (int)floor.getY() + (int)floorHeight, (int)this.getWorldWidth(), yMargin);
    }
    public void drawElevators(Elevator elevator, Graphics g)
    {
        g.drawRect((int)elevator.getX(), (int)elevator.getY(), (int)elevatorWidth, (int)floorHeight);
        g.setColor(new Color(156,156,158));
        g.fillRect((int)elevator.getX(), (int)elevator.getY(), (int)elevator.getDoorWidth(), (int)floorHeight);
        g.fillRect((int)elevator.getX() + (int)elevatorWidth - (int)elevator.getDoorWidth(), (int)elevator.getY(),
                (int)elevator.getDoorWidth(), (int)floorHeight);
        g.setColor(Color.BLACK);
        g.drawRect((int)elevator.getX(), (int)elevator.getY(), (int)elevator.getDoorWidth(), (int)floorHeight);
        g.drawRect((int)elevator.getX() + (int)elevatorWidth - (int)elevator.getDoorWidth(), (int)elevator.getY(),
                (int)elevator.getDoorWidth(), (int)floorHeight);
    }
}
