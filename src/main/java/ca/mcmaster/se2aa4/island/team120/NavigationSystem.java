package ca.mcmaster.se2aa4.island.team120;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NavigationSystem implements MissionType{
    //place where we keep steps
    //first call to search for ground
    //then call to search on island to find POI's 
    private final Logger logger = LogManager.getLogger();

    FindIsland island = new FindIsland();
    SimpleAlgo algoRun = new SimpleAlgo();
    InterTurn interlace = new InterTurn();
    
    StartPoint start = new StartPoint(); 
    Data data = new Data(); 
    Coordinates coords = new Coordinates();
    private static boolean interTurn;
    private static boolean turned;
    private static boolean onGround;

    public String run(String currentDirection, String newDirection, boolean onGround, boolean groundFound, int scanned, int range, int rangeCheck, int batteryLevel, int startingBatteryLevel, boolean checkDone){  
        interTurn = data.getInterTurn();
        onGround = data.getOnGround();
        turned= data.getTurned();
        logger.info("COORDINATES: " + "[" + coords.x_coords() + ", " + coords.y_coords() + "]" );

        if(!(data.getTop())){
            logger.info("running top");
            return start.FourCorners(range, groundFound);
        }
        else if (!onGround && !interTurn){
            logger.info("inter hey {}", onGround);
            return island.Finder(); 
        }
        else if(interTurn){
            data.setTurned(true);
            
            return interlace.Turn();
        }
        else if(!(interTurn) && turned){
            return algoRun.search(currentDirection, batteryLevel, startingBatteryLevel, checkDone); 
        }
        else if (!(interTurn) && !(turned)){
            return algoRun.search(currentDirection, batteryLevel, startingBatteryLevel, checkDone); 
        }
        else{
            return algoRun.stop();
        }
    }

}
