package Models;

import java.util.List;

public class PassengerFactory {
    public List<Passenger> passengerList;
    public Passenger getPassenger(int floor){
        return new Passenger(0, 0, 0, null);
    }
}
