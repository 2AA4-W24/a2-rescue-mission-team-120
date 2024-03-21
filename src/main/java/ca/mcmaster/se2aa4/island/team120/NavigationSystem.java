package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NavigationSystem {
    //place where we keep steps
    //first call to search for ground
    //then call to search on island to find POI's 
    private final Logger logger = LogManager.getLogger();

    FindIsland island = new FindIsland();
    SimpleAlgo run = new SimpleAlgo();
    Top start = new Top(); 
    Data data = new Data(); 

    public String run(String currentDirection, String lastChecked, int fly, int signal, String newDirection, boolean onGround, boolean groundFound, int scanned, int range, int rangeCheck, int batteryLevel, int startingBatteryLevel){  
            
        if(!(data.getTop())){
            logger.info("running top");
            return start.RightOrLeft(range, groundFound);
        }
        else if (!onGround){
            logger.info("running island");
            return island.Finder(newDirection, onGround, groundFound); 
        }else{
            return run.search(onGround, currentDirection, range, batteryLevel, startingBatteryLevel); 
        }
    }

}

/*    
    Actions action= new Actions();
    Coordinates coords = new Coordinates(); 
    Direction dir = new Direction(); 
    Data data = new Data(); 

    int x; 
    int y; 
    
    int range_x_right;
    int range_x_left;
    int range_y_below; 
    int range_y_above; 
    boolean Top = false; 
    boolean turn = false;  */

        //int x = coords.x_coords(); 
        //int y = coords.y_coords(); 
    
        //current position = 0,0 
        //echo up
        //echo down
        //echo left
        //echo right 
        //int turn = 0; 
                // Continue actions based on the current count
             //range in echo up + down = y
        //range in echo left + right = x 

        //from spot keep fly up for range 
        //from spot keep fly left/east for range 
        //update coords 