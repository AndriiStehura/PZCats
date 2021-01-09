
import Interfaces.ElevatorStrategy;
import Logger.CustomLogger;

import Logic.BaseStrategy;
import Logic.IgnoreStrategy;
import Logic.PickingStrategy;
import Models.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    private static void Initialize(int floorsNum, int elevatorsNum, int elevatorStrategy){
        int xMargin = 200, yMargin = 50, floorHeight = 100, elevatorWidth=50;

        WorldInformation worldInformation = WorldInformation.getInstance();
        worldInformation.Initialize(floorsNum, elevatorsNum, xMargin,
                yMargin, floorHeight, elevatorWidth);

        List<Floor> floors = new ArrayList<>();
        for (int i = 0; i < worldInformation.getFloorsNum(); ++i){
            Floor f = new Floor();
            f.setY(worldInformation.getWorldHeight() - (i + 1) * worldInformation.getFloorHeight()
                    - i * worldInformation.get_yMargin());
            floors.add(f);
        }

        List<Elevator> elevators = new ArrayList<>();
        BlockingQueue<Passenger> passengersQueue = new LinkedBlockingQueue<>();
        String strategyStr = "";
        for (int i = 0; i < worldInformation.getElevatorsNum(); ++i){
            Elevator e = new Elevator(200, floors.get(0));
            e.setX((worldInformation.get_xMargin() + worldInformation.getElevatorWidth()) * (i + 1));
            e.setY(worldInformation.getWorldHeight() - worldInformation.getFloorHeight());
            ElevatorStrategy strategy;
            if(elevatorStrategy == 0)
                strategy = new IgnoreStrategy(e, passengersQueue);
            else
                strategy = new PickingStrategy(e, passengersQueue);

            strategyStr = strategy.getClass().getName();
            e.setStrategy(strategy);
            elevators.add(e);
        }

        Building building = new Building(elevators, floors, passengersQueue);
        worldInformation.setBuilding(building);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame startFrame = new JFrame("Launch Elevator Simulator");
        startFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startFrame.setAlwaysOnTop(true);
        startFrame.setResizable(false);
        startFrame.setSize((int)worldInformation.getWorldWidth(),(int) worldInformation.getWorldHeight());
        startFrame.setVisible(true);
        startFrame.setLocation(dim.width/2-startFrame.getSize().width/2,
                dim.height/2-startFrame.getSize().height/2);

        startFrame.setLocation(dim.width/2-startFrame.getSize().width/2,
                dim.height/2-startFrame.getSize().height/2);

        System.out.println("Created building with " + elevatorsNum + " elevators and "
            + floorsNum + " floors. Strategy - " + strategyStr);
    }

    public static void main(String[] args) {
        InterfaceInitialization();
        WorldInformation.getInstance().getBuilding().runAllThreads();
    }
    
    private static void InterfaceInitialization(){
        /*CustomLogger.log("temp");
        CustomLogger.log("done1");
        CustomLogger.log("done1");*/

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame startFrame = new JFrame("Launch Elevator Simulator");
        JLabel title;
        title = new JLabel("Elevator simulation");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        startFrame.add(title);

        JLabel elevators = new JLabel();
        elevators = new JLabel("Elevators: ");
        elevators.setFont(new Font("Arial", Font.PLAIN, 20));
        elevators.setSize(200, 20);
        elevators.setLocation(100, 100);
        startFrame.add(elevators);

        String[] items = {
                "Ignore Strategy",
                "Picking Strategy"
        };

        JComboBox jComboBox = new JComboBox(items);
        jComboBox.setLocation(300, 95);
        jComboBox.setSize(200, 30);
        jComboBox.setFont(new Font("Arial", Font.PLAIN, 20));
        startFrame.add(jComboBox);

        SpinnerModel sm = new SpinnerNumberModel(1, 1, 3, 1);
        JSpinner elevatorsCount = new JSpinner(sm);
        elevatorsCount.setLocation(200, 95);
        elevatorsCount.setSize(40, 30);
        elevatorsCount.setFont(new Font("Arial", Font.PLAIN, 15));
        startFrame.add(elevatorsCount);

        JLabel floors = new JLabel();
        floors = new JLabel("Floors: ");
        floors.setFont(new Font("Arial", Font.PLAIN, 20));
        floors.setSize(200, 20);
        floors.setLocation(100, 150);
        startFrame.add(floors);

        SpinnerModel smFloors = new SpinnerNumberModel(2, 2, 5, 1);
        JSpinner floorsCount = new JSpinner(smFloors);
        floorsCount.setLocation(200, 145);
        floorsCount.setSize(40, 30);
        floorsCount.setFont(new Font("Arial", Font.PLAIN, 15));
        startFrame.add(floorsCount);

        JButton createWorld = new JButton();
        createWorld = new JButton("Create world");
        createWorld.setBounds(300, 200, 180, 25);
        createWorld.setFocusPainted(false);
        createWorld.addActionListener((e) -> {
            Initialize((Integer)floorsCount.getValue(), (Integer)elevatorsCount.getValue(),
                    jComboBox.getSelectedIndex());
            startFrame.dispose();
        });

        JPanel jPanel = new JPanel();
        jPanel = new JPanel(null);
        jPanel.setPreferredSize(new Dimension(750, 240));
        jPanel.add(createWorld);

        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setAlwaysOnTop(true);
        startFrame.setResizable(false);
        startFrame.add(jPanel);
        startFrame.pack();
        startFrame.setVisible(true);
        startFrame.setLocation(dim.width/2-startFrame.getSize().width/2,
                dim.height/2-startFrame.getSize().height/2);
    }
}
