package ca.mcmaster.se2aa4.island.team120;

public class NavigationSystem {
    //place where we keep steps
    //first call to search for ground
    //then call to search on island to find POI's 
    FindIsland island = new FindIsland();
    SimpleAlgo run = new SimpleAlgo();
    boolean foundIsland = false;
    boolean islandTraversed = false; 

    public String run(String currentDirection, String lastChecked, int fly, int signal, String newDirection, boolean onGround, boolean groundFound, int scanned, boolean lost){  
        while (!islandTraversed){
            if (!foundIsland){
                foundIsland = true; 
                return island.Finder(currentDirection, newDirection, onGround, groundFound, lost, lastChecked); 
            }else{
                islandTraversed = true; 
                return run.search(); 
            }
        }
        return null;
    }
}
