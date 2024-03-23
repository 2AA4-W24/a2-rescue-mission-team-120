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

    private static boolean groundFound;
    private static boolean checkDone;
    private static String currentDirection;

    public String run(int batteryLevel, int startingBatteryLevel){ 
        
        //initializes variables
        groundFound = data.getGroundFound();
        checkDone = data.getCheckDone();
        currentDirection = data.getCurrDirection();

        logger.info("navigation");
        logger.info(data.getTop());
        logger.info(onGround);
        logger.info(interTurn);

        if(!(data.getTop())){
            return start.fourCorners(groundFound);
        }
        else if (!onGround && !interTurn){
            logger.info("running finder");
            return island.finder(); 
        }
        else if(interTurn){
            logger.info("interlace");
            data.setHasChangedDir(true);
            
            return interlace.turn();
        }
        else if(!(interTurn) && hasChangedDir){
            logger.info("algo search");
            return algoRun.search(batteryLevel, startingBatteryLevel); 
        }
        else if (!(interTurn) && !(hasChangedDir)){
            logger.info("algo search2");
            return algoRun.search(batteryLevel, startingBatteryLevel); 
        }
        else{
            logger.info("stop");
            return action.stop();
        }
    }
}
