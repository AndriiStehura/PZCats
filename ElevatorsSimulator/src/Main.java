
import Interfaces.ElevatorStrategy;

import Logic.IgnoreStrategy;
import Logic.PickingStrategy;
import Models.*;
import Views.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    private static Font defaultFont14 = new Font("Gill Sans MT",Font.BOLD,14);
    private static Font defaultFont20 = new Font("Gill Sans MT",Font.BOLD,20);
    private static Color textColor = new Color(160,70,104);
    private static Color backgroundColor = new Color(217,208,222);
    private static Color suppColor = new Color(12,23,19);

    private static void Initialize(int floorsNum, int elevatorsNum, int elevatorStrategy){
        int xMargin = 200, yMargin = 50, floorHeight = 100, elevatorWidth=50,
                passengerWidth = 25, passengerMargin = 10;

        WorldInformation worldInformation = WorldInformation.getInstance();
        worldInformation.Initialize(floorsNum, elevatorsNum, xMargin,
                yMargin, floorHeight, elevatorWidth, passengerWidth, passengerMargin);

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
        JFrame mainFrame = new JFrame("Elevator simulator");

        worldInformation.setBounds(0, 0 , (int)worldInformation.getWorldWidth(),
                (int) worldInformation.getWorldHeight());
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension((int)worldInformation.getWorldWidth(),
                (int) worldInformation.getWorldHeight()));
        panel.add(worldInformation);

        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setAlwaysOnTop(true);
        mainFrame.setResizable(false);
        mainFrame.setSize((int)worldInformation.getWorldWidth(),(int) worldInformation.getWorldHeight() + 43);
        mainFrame.add(panel);
        mainFrame.setVisible(true);
        mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2,
                dim.height/2-mainFrame.getSize().height/2);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Thread invalidatingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    panel.repaint();
                }
            }
        });
        invalidatingThread.start();
        WorldInformation.getInstance().getBuilding().runAllThreads();
    }

    public static void main(String[] args) {
        InterfaceInitialization();
    }

    private static void InterfaceInitialization(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame startFrame = new JFrame("Launch Elevator Simulator");
        JLabel title;
        title = new JLabel("Elevator simulation");
        title.setFont(new Font("Gill Sans MT",Font.BOLD,25));
        title.setForeground(textColor);
        title.setSize(300, 30);
        title.setLocation(300, 30);
        startFrame.add(title);

        JLabel elevators = new JLabel();
        elevators = new JLabel("Elevators: ");
        elevators.setFont(defaultFont20);
        elevators.setForeground(textColor);
        elevators.setSize(200, 20);
        elevators.setLocation(100, 100);
        startFrame.add(elevators);

        String[] items = {
                "Ignore Strategy",
                "Picking Strategy"
        };

        JComboBox jComboBox = new JComboBox(items);
        jComboBox.setForeground(textColor);
        jComboBox.setLocation(300, 95);
        jComboBox.setSize(200, 30);
        jComboBox.setFont(defaultFont20);
        startFrame.add(jComboBox);

        SpinnerModel sm = new SpinnerNumberModel(1, 1, 3, 1);
        JSpinner elevatorsCount = new JSpinner(sm);
        elevatorsCount.setLocation(200, 95);
        elevatorsCount.setSize(40, 30);
        elevatorsCount.setFont(defaultFont14);
        startFrame.add(elevatorsCount);

        JLabel floors = new JLabel();
        floors = new JLabel("Floors: ");
        floors.setFont(defaultFont20);
        floors.setForeground(textColor);
        floors.setSize(200, 20);
        floors.setLocation(100, 150);
        startFrame.add(floors);

        SpinnerModel smFloors = new SpinnerNumberModel(2, 2, 5, 1);
        JSpinner floorsCount = new JSpinner(smFloors);
        floorsCount.setLocation(200, 145);
        floorsCount.setSize(40, 30);
        floorsCount.setFont(defaultFont14);
        startFrame.add(floorsCount);

        JButton createWorld = new CustomButton("Create world", suppColor, textColor);
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
        jPanel.setBackground(backgroundColor);

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
