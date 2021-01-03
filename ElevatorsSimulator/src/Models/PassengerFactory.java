package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PassengerFactory {
    private int maxWeight = 120;
    private int minWeight = 0;
    private int maxCountOfFloors;
    public List<Passenger> passengerList;

    public Passenger getPassenger(int _maxCountOfFloors){
        maxCountOfFloors = _maxCountOfFloors;
        if(passengerList == null){
            passengerList = new ArrayList<>();
            return createNewPerson();
        }else {
            return getExistingOrNewPerson();
        }
    }

    private Passenger createNewPerson() {
        var createdPassenger = createRandomPassenger();
        passengerList.add(createdPassenger);
        return  createdPassenger;
    }


    private Passenger getExistingOrNewPerson() {
        Passenger passengerToReturn = passengerList.stream()
                .filter(e -> e.state == PassengerState.Left)
                .findFirst()
                .orElse(null);

        if(passengerToReturn == null){
            return createNewPerson();
        }else {
            return updatePerson(passengerToReturn);
        }
    }


    private Passenger updatePerson(Passenger passengerToReturn) {
        passengerToReturn.state = PassengerState.Spawned;
        return  passengerToReturn;
    }


    private Passenger createRandomPassenger(){
        int weight = getRandomInteger(minWeight, maxWeight);
        int sourceFloor = getRandomInteger(0, maxCountOfFloors);
        int destinationFloor = getRandomInteger(0, maxCountOfFloors);
        //TODO: Check if source != destination

        var createdPassenger = new Passenger(weight, sourceFloor, destinationFloor, PassengerState.Spawned);
        return createdPassenger;
    }

    private int getRandomInteger(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max) + min;
    }
}
