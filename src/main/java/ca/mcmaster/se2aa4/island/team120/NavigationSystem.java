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
    StartPoint start = new StartPoint(); 
    Data data = new Data(); 

    public String run(String currentDirection, String newDirection, boolean onGround, boolean groundFound, int scanned, int range, int rangeCheck, int batteryLevel, int startingBatteryLevel, boolean checkDone){  
            
        if(!(data.getTop())){
            logger.info("running top");
            return start.FourCorners(range, groundFound);
        }
        else if (!onGround){
            logger.info("FIND ISLAND ALGOOO");
            logger.info("Current direction: " + data.getCurrDirection());
            return island.Finder(newDirection, onGround, groundFound); 
        }else{
            logger.info("SIMPLE ALGO");
            return run.search(currentDirection, rangeCheck, batteryLevel, startingBatteryLevel, true, checkDone); 
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
    /*

    int range_x;
    int range_y; 

    public String run(String currentDirection, String newDirection, boolean onGround, boolean groundFound, int scanned, int range, int rangeCheck, int batteryLevel, int startingBatteryLevel, boolean checkDone){ 
        TopLeft();
        logger.info("POOOOOSIITIONNNN: [" + coords.x_coords() + ", " + coords.y_coords() + "]");
        if (!onGround){
            logger.info("FIND ISLAND ALGOOO");
            logger.info("Current direction: " + data.getCurrDirection());
            return island.Finder(newDirection, onGround, groundFound); 
        }else{
            logger.info("SIMPLE ALGO");
            return run.search(currentDirection, rangeCheck, batteryLevel, startingBatteryLevel, true, checkDone); 
        }
    }
*/
    //public String TopLeft(){
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