
import Interfaces.ElevatorStrategy;
import Logger.CustomLogger;

import Logic.IgnoreStrategy;
import Models.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {

    private static void Initialize(){
        WorldInformation worldInformation = WorldInformation.getInstance();
        worldInformation.Initialize(5, 2, 50,
                100, 100, 50);

        List<Floor> floors = new ArrayList<>();
        for (int i = 0; i < worldInformation.getFloorsNum(); ++i){
            Floor f = new Floor();
            f.setY(worldInformation.getWorldHeight() - (i + 1) * worldInformation.getFloorHeight()
                    - i * worldInformation.get_yMargin());
            floors.add(f);
        }

        List<Elevator> elevators = new ArrayList<>();
        for (int i = 0; i < worldInformation.getElevatorsNum(); ++i){
            Elevator e = new Elevator(200);
            e.setX((worldInformation.get_xMargin() + worldInformation.getElevatorWidth()) * (i + 1));
            e.setY(worldInformation.getWorldHeight() - worldInformation.getFloorHeight());
            ElevatorStrategy strategy = new IgnoreStrategy(e, new ArrayDeque<>());
            e.setStrategy(strategy);
            elevators.add(e);
        }
        
        Building b = new Building(elevators, floors);
    }

    public static void main(String[] args) {

        /*CustomLogger.log("temp");
        CustomLogger.log("done1");
        CustomLogger.log("done1");*/
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
                "Strategy 1",
                "Strategy 2"
        };

        JComboBox jComboBox = new JComboBox(items);
        jComboBox.setLocation(300, 95);
        jComboBox.setSize(200, 30);
        jComboBox.setFont(new Font("Arial", Font.PLAIN, 20));
        startFrame.add(jComboBox);

        SpinnerModel sm = new SpinnerNumberModel(0, 0, 3, 1);
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

        SpinnerModel smFloors = new SpinnerNumberModel(0, 0, 10, 1);
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
            //Тут викликається вюшка з вже згенерованим білдінгом
//            Main main = new Main(Integer.parseInt(elevatorsCount.getValue().toString()),
//                    Integer.parseInt(floorsCount.getValue().toString()),
//                    (String)jComboBox.getSelectedItem()
//                    );
            //startFrame.dispatchEvent(new WindowEvent(startFrame, WindowEvent.WINDOW_CLOSING));
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
    }
}
