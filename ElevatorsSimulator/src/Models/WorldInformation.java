package Models;

public class WorldInformation {
    private static WorldInformation instance;

    private WorldInformation() {
        
    }

    WorldInformation getInstance(){
        return  (instance == null) ? new WorldInformation() : instance;
    }
}
