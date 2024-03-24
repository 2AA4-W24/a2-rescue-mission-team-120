package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NavigationSystem implements MissionType{
    private final Logger logger = LogManager.getLogger();

    FindIsland island = new FindIsland();
    GridSearch algoRun = new GridSearch();
    InterTurn interlace = new InterTurn();
    
    StartPoint start = new StartPoint(); 
    Data data = new Data(); 
    Coordinates coords = new Coordinates();
    Actions action= new Actions();

    // navigation algorithm system which decides when to initiate which phase of
    // island exploration; begin by checking drones initial position and begins moving to get into the
    // correct starting position. continue running so long as the battery is above threshold level
    public String run(int batteryLevel, int startingBatteryLevel){ 
        while(batteryLevel>= 0.175*startingBatteryLevel){
            if(!(data.getTop())){
                return start.fourCorners();
            }
            // once drone is in position, initiate island locating algorithm until drone is on ground
            else if (!(data.getOnGround()) && !(data.getNoIsland()) && !(data.getInterTurn())){
                return island.finder(); 
            }
            // if the drone has finished the initial interlacing pattern, drone must initiate turn
            // to get into position to begin second interlacing pattern
            else if(data.getInterTurn()){
                data.setHasChangedDir(true);
                return interlace.turn();
            }
            // once drone is on ground after finding the island, begin island traversal algorithm 
            // using grid search and an interlacing pattern
            else if (!(data.getInterTurn()) && !(data.getHasChangedDir())){
                data.setNoIsland(true);
                return algoRun.search(batteryLevel, startingBatteryLevel); 
            }
            else if(!(data.getInterTurn()) && data.getHasChangedDir()){
                return algoRun.search(batteryLevel, startingBatteryLevel); 
            }
            else{
                return action.stop();
            }
        }
        // once battery is below set threshold
        return action.stop();
    }
}
