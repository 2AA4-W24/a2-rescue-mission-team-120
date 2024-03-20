package ca.mcmaster.se2aa4.island.team120;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class NavigationSystem {
    //place where we keep steps
    //first call to search for ground
    //then call to search on island to find POI's 
    FindIsland island = new FindIsland();
    SimpleAlgo run = new SimpleAlgo();
    boolean islandTraversed = false; 
    int x; 
    int y; 
    private final Logger logger = LogManager.getLogger();
    

    public String run(String currentDirection, String lastChecked, int fly, int signal, String newDirection, boolean onGround, boolean groundFound, int scanned, boolean lost, int range, int batteryLevel, int startingBatteryLevel){  
        while (!islandTraversed){
            if (!onGround){
                return island.Finder(newDirection, onGround, groundFound, lost); 
            }else{
                logger.info("algorithm hit");
                return run.search(onGround, currentDirection, range, batteryLevel, startingBatteryLevel); 
            }
        }
        return null;
    }

}
