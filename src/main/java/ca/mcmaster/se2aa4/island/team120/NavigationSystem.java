package ca.mcmaster.se2aa4.island.team120;

public class NavigationSystem {
    //place where we keep steps
    //first call to search for ground
    //then call to search on island to find POI's 
    FindIsland island = new FindIsland();
    SimpleAlgo run = new SimpleAlgo();
    boolean islandTraversed = false; 

    public String run(String currentDirection, String lastChecked, int fly, int signal, String newDirection, boolean onGround, boolean groundFound, int scanned, boolean lost, int range, int batteryLevel, int startingBatteryLevel){  
        while (!islandTraversed){
            if (!onGround){
                return island.Finder(currentDirection, lastChecked, fly, signal, newDirection, onGround, groundFound, scanned, lost); 
            }else{
                return run.search(onGround, currentDirection, range, batteryLevel, startingBatteryLevel); 
            }
        }
        return null;
    }
}
